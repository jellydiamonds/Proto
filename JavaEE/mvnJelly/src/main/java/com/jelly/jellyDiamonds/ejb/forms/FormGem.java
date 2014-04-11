package com.jelly.jellyDiamonds.ejb.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.GemBeanException;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;

public final class FormGem {
    // private static final String PARAM_REFERENCE = "gemReference";
    private static final String PARAM_ID             = "gemID";
    private static final String PARAM_SPECIES        = "gemSpecies";
    private static final String PARAM_COLOR          = "gemColor";
    private static final String PARAM_SHAPE          = "gemShape";
    private static final String PARAM_CUT            = "gemCut";
    private static final String PARAM_MASS           = "gemMass";
    private static final String PARAM_SIZE_X         = "gemSizeX";
    private static final String PARAM_SIZE_Y         = "gemSizeY";
    private static final String PARAM_SIZE_Z         = "gemSizeZ";
    private static final String PARAM_CLARITY        = "gemClarity";
    private static final String PARAM_ENHANCEMENT    = "gemEnhancement";
    private static final String PARAM_ORIGIN         = "gemOrigin";
    private static final String PARAM_CERTIFICATE    = "gemCertificate";
    private static final String PARAM_COMMENTS       = "gemComments";
    private static final String PARAM_PRICE_CURRENCY = "gemPriceCurrency";
    private static final String PARAM_PRICE_VALUE    = "gemPriceValue";
    private static final String PARAM_SUPPLIER_ID    = "gemSupplierID";
    private static final String PARAM_PHOTO_LINK     = "gemPhotoLink";
    private static final String PARAM_LIGHT          = "gemLight";
    // Just to stock errors
    private static final String PARAM_SIZE           = "gemSize";
    private static final String PARAM_PRICE          = "gemPrice";
    private static final String PARAM_SQL            = "sql";
    public static final String  PARAM_PATH           = "path";
    // Regular expressions
    private static final String REGEX_MASS           = "[0-9]*(\\.[0-9]*)*";
    private static final String REGEX_SIZE           = "[0-9]*(\\.[0-9]*)*";
    private static final String REGEX_PRICECURRENCY  = "[A-Z][A-Z][A-Z]";
    private static final String REGEX_PRICEVALUE     = "[0-9]*(\\.[0-9]*)*";

    // EJB which connects to the database.
    private IGemLocal           gemBeanLocal;

    private String              result;
    private Map<String, String> errors               = new HashMap<String, String>();

    public FormGem( IGemLocal gemLocal ) {
        this.gemBeanLocal = gemLocal;
    }

    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    /*
     * Creates a Gem object from the parameters of HTTP REQUEST object. Stores
     * gem's photo on disk. The 'errors' map is EMPTY for a moment, as each
     * servlet instanciate its own FormGem, and in each operation - AddGem,
     * EditGem - the method createGem() is called before saving to the database.
     */
    public Gem createGem( HttpServletRequest request, String path ) {
        // Getting form fields.
        String speciesStr = getValeurChamp( request, PARAM_SPECIES );
        String color = getValeurChamp( request, PARAM_COLOR );
        String shapeStr = getValeurChamp( request, PARAM_SHAPE );
        String cutStr = getValeurChamp( request, PARAM_CUT );
        String massStr = getValeurChamp( request, PARAM_MASS );
        String sizeXStr = getValeurChamp( request, PARAM_SIZE_X );
        String sizeYStr = getValeurChamp( request, PARAM_SIZE_Y );
        String sizeZStr = getValeurChamp( request, PARAM_SIZE_Z );
        String clarityStr = getValeurChamp( request, PARAM_CLARITY );
        String enhancementStr = getValeurChamp( request, PARAM_ENHANCEMENT );
        String originStr = getValeurChamp( request, PARAM_ORIGIN );
        String certificateStr = getValeurChamp( request, PARAM_CERTIFICATE );
        String comments = getValeurChamp( request, PARAM_COMMENTS );
        String priceCurrencyStr = getValeurChamp( request, PARAM_PRICE_CURRENCY );
        String priceValueStr = getValeurChamp( request, PARAM_PRICE_VALUE );
        String supplierIDStr = getValeurChamp( request, PARAM_SUPPLIER_ID );
        String lightStr = getValeurChamp( request, PARAM_LIGHT );
        // String photoLink = null;

        Gem gem = new Gem();

        // Processing and validating the parameters, entered by user.
        processSpecies( speciesStr, gem );
        processColor( color, gem );
        processShape( shapeStr, gem );
        processCut( cutStr, gem );
        processMass( massStr, gem );
        processSize( sizeXStr, sizeYStr, sizeZStr, gem );
        processClarity( clarityStr, gem );
        processEnhancement( enhancementStr, gem );
        processOrigin( originStr, gem );
        processCertificate( certificateStr, gem );
        processComments( comments, gem );
        processPrice( priceValueStr, priceCurrencyStr, gem );
        processSupplierID( supplierIDStr, gem );
        processLight( lightStr, gem );
        // Image processing and validation, using a FormImage object.
        processImage( request, path, gem );

        System.out.println( "After processing all fields. errors.isEmpty() = " + errors.isEmpty() );
        printMap( errors );
        return gem;
    }

    /*
     * Creates a new gem in the database, if the 'errors' map is empty. Before
     * saving, adds automatically assigned fields (reference and creation date).
     */
    public void addNewGem( Gem gem ) {
        if ( this.errors.isEmpty() ) {
            try {
                processDateAndReference( gem );
                gemBeanLocal.addGem( gem );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, e.getMessage() );
            }
        }
        // Global result
        if ( errors.isEmpty() ) {
            result = "Gem created successfully.";
        } else {
            result = "Error while creating a gem.";
        }
        System.out.println( "Result: " + result );
        printMap( this.errors );
    }

    /* Updates gem in the database, doesn't create a new one. */
    public void updateGem( Gem gem ) {
        if ( this.errors.isEmpty() ) {
            try {
                gemBeanLocal.updateGem( gem );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, e.getMessage() );
            }
        }
        // Global result
        if ( errors.isEmpty() ) {
            result = "Gem edited successfully.";
        } else {
            result = "Error while editing a gem.";
        }
        System.out.println( "Result: " + result );
        printMap( this.errors );
    }

    /* Searches for a gem with given id in the database. */
    public Gem findGem( HttpServletRequest request ) {
        Gem gem = new Gem();
        // Validating an ID field
        String gemIDStr = getValeurChamp( request, PARAM_ID );
        processID( gemIDStr );

        // If the search form has no errors, looking for a gem in the database.
        if ( errors.isEmpty() ) {
            try {
                Long gemID = Long.valueOf( gemIDStr );
                gem = gemBeanLocal.findGem( gemID );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, "Caught a GemBeanException! Gem #" + gemIDStr + "not found." );
            }
        }

        if ( gem == null ) {
            setError( PARAM_SQL, "ouch! Gem #" + gemIDStr + " not found." );
        }
        // Global resurt of a search
        if ( errors.isEmpty() ) {
            result = "Gem found";
        } else {
            result = "Search error";
        }
        return gem;
    }

    /*
     * TRAITEMENT (PROCESSING): ...............................................
     * 1) Validation des champs................................................
     * 2) Initialisation des propriétés correspondantes du bean .............
     */
    private void processSpecies( String speciesStr, Gem gem ) {
        System.out.println( "EditGem.processSpecies: speciesStr = " + speciesStr );
        Integer species = null;
        try {
            species = validateSpecies( speciesStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_SPECIES, e.getMessage() );
        }
        gem.setSpecies( species );
    }

    private void processColor( String color, Gem gem ) {
        try {
            validateColor( color );
        } catch ( FormValidationException e ) {
            setError( PARAM_COLOR, e.getMessage() );
        }
        gem.setColor( color );
    }

    private void processShape( String shapeStr, Gem gem ) {
        Integer shape = null;
        try {
            shape = validateShape( shapeStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_SHAPE, e.getMessage() );
        }
        gem.setShape( shape );
    }

    private void processCut( String cutStr, Gem gem ) {
        Integer cut = null;
        try {
            cut = validateCut( cutStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_CUT, e.getMessage() );
        }
        gem.setCut( cut );
    }

    private void processMass( String massString, Gem gem ) {
        Double mass = null;
        try {
            mass = validateMass( massString );
        } catch ( FormValidationException e ) {
            setError( PARAM_MASS, e.getMessage() );
        }
        gem.setMass( mass );
    }

    private void processSize( String sizeXString, String sizeYString, String sizeZString, Gem gem ) {
        Float sizeX = null;
        Float sizeY = null;
        Float sizeZ = null;
        try {
            sizeX = validateSize( sizeXString, 'x' );
        } catch ( FormValidationException e ) {
            setError( PARAM_SIZE_X, e.getMessage() );
        }
        try {
            sizeY = validateSize( sizeYString, 'y' );
        } catch ( FormValidationException e ) {
            setError( PARAM_SIZE_Y, e.getMessage() );
        }
        try {
            sizeZ = validateSize( sizeZString, 'z' );
        } catch ( FormValidationException e ) {
            setError( PARAM_SIZE_Z, e.getMessage() );
        }
        gem.setSizeX( sizeX );
        gem.setSizeY( sizeY );
        gem.setSizeZ( sizeZ );
    }

    private void processClarity( String clarityStr, Gem gem ) {
        Integer clarity = null;
        try {
            clarity = validateClarity( clarityStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_CLARITY, e.getMessage() );
        }
        gem.setClarity( clarity );
    }

    private void processEnhancement( String enhancementStr, Gem gem ) {
        Integer enhancement = null;
        try {
            enhancement = validateEnhancement( enhancementStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_ENHANCEMENT, e.getMessage() );
        }
        gem.setEnhancement( enhancement );
    }

    private void processOrigin( String originStr, Gem gem ) {
        Integer origin = null;
        try {
            origin = validateOrigin( originStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_ORIGIN, e.getMessage() );
        }
        gem.setOrigin( origin );
    }

    private void processCertificate( String certificateStr, Gem gem ) {
        Integer certificate = null;
        try {
            certificate = validateCertificate( certificateStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_CERTIFICATE, e.getMessage() );
        }
        gem.setCertificate( certificate );
    }

    private void processComments( String comments, Gem gem ) {
        try {
            validateComments( comments );
        } catch ( FormValidationException e ) {
            setError( PARAM_COMMENTS, e.getMessage() );
        }
        gem.setComments( comments );
    }

    private void processPrice( String priceValueStr, String priceCurrencyStr, Gem gem ) {
        Double priceValue = null;
        Integer priceCurrency = null;
        try {
            priceValue = validatePriceValue( priceValueStr );
            priceCurrency = validatePriceCurrency( priceValue, priceCurrencyStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_PRICE, e.getMessage() );
        }
        gem.setPriceValue( priceValue );
        gem.setPriceCurrency( priceCurrency );
    }

    private void processSupplierID( String supplierIDString, Gem gem ) {
        Long supplierID = 0L;
        try {
            supplierID = validateSupplierID( supplierIDString );
        } catch ( FormValidationException e ) {
            setError( PARAM_SUPPLIER_ID, e.getMessage() );
        }
        gem.setSupplierID( supplierID );
    }

    private void processLight( String lightStr, Gem gem ) {
        Integer light = null;
        try {
            light = validateLight( lightStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_LIGHT, e.getMessage() );
        }
        gem.setLight( light );
    }

    private void processID( String gemIDStr ) {
        try {
            validateID( gemIDStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_ID, e.getMessage() );
        }
    }

    /*
     * ========== IMAGE PROCESSING AND VALIDATION =========================
     * Extracting an image file from the request, saving it to the given
     * directory on the disk.
     */
    private void processImage( HttpServletRequest request, String path, Gem gem ) {
        String photoLink = null;
        FormImage formImage = new FormImage();
        try {
            photoLink = formImage.saveImage( request, path );
        } catch ( FormValidationException e ) {
            setError( PARAM_PHOTO_LINK, e.getMessage() );
        }
        // 'gemPhotoLink' field has already been validated by FormImage
        gem.setPhotoLink( photoLink );
    }

    private void processDateAndReference( Gem gem ) {
        // Setting the current date
        Date now = new Date();
        gem.setCreationDate( now );
        /*
         * TO DO: design the reference structure. Current mechanism: takes the
         * yyyyMMMdd from the string representation of Date, adds "GEM" and a
         * 4-digit random number
         */
        String dateStr = now.toString();
        String year = dateStr.substring( 24 );
        String month = dateStr.substring( 4, 7 );
        String day = dateStr.substring( 8, 10 );
        String speciesCode = "GEM"; // TO DO: species.substring( 0, 3
                                    // ).toUpperCase();
        int min = 1000;
        int max = 9999;
        int randomNum = min + (int) ( Math.random() * ( ( max - min ) + 1 ) );
        String finalRef = year + month + day + speciesCode + String.valueOf( randomNum );

        /*
         * try { MessageDigest md = MessageDigest.getInstance( "SHA-512" );
         * byte[] bytesRef = ref.getBytes( "UTF-8" ); byte[] finalBytesRef =
         * md.digest( bytesRef ); finalRef = finalBytesRef.toString(); } catch (
         * NoSuchAlgorithmException e ) { e.printStackTrace(); } catch (
         * UnsupportedEncodingException e1 ) { e1.printStackTrace(); }
         */

        gem.setReference( finalRef );
    }

    private Integer validateSpecies( String speciesStr ) throws FormValidationException {
        if ( speciesStr == null || speciesStr.equals( "0" ) ) {
            throw new FormValidationException( "Please select a gem species." );
        } else {
            return Integer.valueOf( speciesStr );
        }
    }

    private void validateColor( String color ) throws FormValidationException {
        if ( color == null ) {
            throw new FormValidationException( "Please enter a gem color." );
        }
    }

    private Integer validateShape( String shapeStr ) throws FormValidationException {
        if ( shapeStr == null || shapeStr.equals( "0" ) ) {
            throw new FormValidationException( "Please select a gem shape." );
        } else {
            return Integer.valueOf( shapeStr );
        }
    }

    private Integer validateCut( String cutStr ) throws FormValidationException {
        if ( cutStr == null || cutStr.equals( "0" ) ) {
            throw new FormValidationException( "Please select a gem cut." );
        } else {
            return Integer.valueOf( cutStr );
        }
    }

    private Double validateMass( String massStr ) throws FormValidationException {
        Double mass = null;
        if ( massStr != null ) {
            if ( massStr.matches( REGEX_MASS ) ) {
                mass = Double.valueOf( massStr );
            } else {
                throw new FormValidationException( "Mass must be positive decimal" );
            }
        } else {
            throw new FormValidationException( "Please enter a mass" );
        }
        return mass;
    }

    private Float validateSize( String sizeString, char dimensionLetter )
            throws FormValidationException {
        Float size = null;
        String dimensionWord = "";

        switch ( dimensionLetter ) {
        case 'x':
            dimensionWord = "width";
            break;
        case 'y':
            dimensionWord = "height";
            break;
        case 'z':
            dimensionWord = "depth";
            break;
        default:
            throw new FormValidationException( "Program error. Unknown dimension. " );
        }

        if ( sizeString != null ) {
            if ( sizeString.matches( REGEX_SIZE ) ) {
                size = Float.valueOf( sizeString );
            } else {
                throw new FormValidationException( dimensionWord + " must be a positive decimal. " );
            }
        } else {
            // Width (x) and height (y) must be not null.
            if ( dimensionLetter == 'x' || dimensionLetter == 'y' ) {
                throw new FormValidationException( "Please enter a " + dimensionWord + ". " );
            }
        }
        return size;
    }

    private Integer validateClarity( String clarityStr ) throws FormValidationException {
        if ( clarityStr == null || clarityStr.equals( "0" ) ) {
            throw new FormValidationException( "Please select a gem clarity." );
        } else {
            return Integer.valueOf( clarityStr );
        }
    }

    private Integer validateEnhancement( String enhancementStr ) throws FormValidationException {
        if ( enhancementStr == null || enhancementStr.equals( "0" ) ) {
            return null;
        } else {
            return Integer.valueOf( enhancementStr );
        }
    }

    private Integer validateOrigin( String originStr ) throws FormValidationException {
        if ( originStr == null || originStr.equals( "0" ) ) {
            return null;
        } else {
            return Integer.valueOf( originStr );
        }
    }

    private Integer validateCertificate( String certificateStr ) throws FormValidationException {
        if ( certificateStr == null || certificateStr.equals( "0" ) ) {
            return null;
        } else {
            return Integer.valueOf( certificateStr );
        }
    }

    private void validateComments( String comments ) throws FormValidationException {
        // Nothing for the moment.
    }

    private Double validatePriceValue( String priceValueStr ) throws FormValidationException {
        Double priceValue = null;
        if ( priceValueStr != null ) {
            // Validating the price value field
            if ( priceValueStr.matches( REGEX_PRICEVALUE ) ) {
                priceValue = Double.valueOf( priceValueStr );
            } else {
                throw new FormValidationException( "Price must be a positive decimal" );
            }
        }
        return priceValue;
    }

    private Integer validatePriceCurrency( Double priceValue, String priceCurrencyStr ) throws FormValidationException {
        Integer priceCurrency = null;
        // If the value is entered, the currency must be selected.
        if ( priceValue != null ) {
            if ( priceCurrencyStr == null ) {
                throw new FormValidationException( "Please select a currency." );
            } else {
                priceCurrency = Integer.valueOf( priceCurrencyStr );
            }
        }
        return priceCurrency;
    }

    private Long validateSupplierID( String supplierIDString ) throws FormValidationException {
        Long supplierID = -1L;
        if ( supplierIDString != null ) {
            if ( supplierIDString.matches( "[0-9]*" ) ) {
                supplierID = Long.valueOf( supplierIDString );
            } else {
                throw new FormValidationException( "Supplier ID must contain only digits." );
            }
        } else {
            throw new FormValidationException( "Please enter a supplier ID." );
        }
        return supplierID;
    }

    private Integer validateLight( String lightStr ) throws FormValidationException {
        if ( lightStr == null || lightStr.equals( "0" ) ) {
            return null;
        } else {
            return Integer.valueOf( lightStr );
        }
    }

    private void validateID( String id ) throws FormValidationException {
        if ( id != null ) {
            if ( !id.matches( "[0-9]*" ) ) {
                throw new FormValidationException( "ID must contain only digits." );
            }
        } else {
            throw new FormValidationException( "Please enter an ID." );
        }
    }

    /* METHODES UTILITAIRES */
    /* If the field is not empty, returns its content, otherwise null. */
    private static String getValeurChamp( HttpServletRequest request, String paramName ) {
        String value = request.getParameter( paramName );
        if ( value == null || value.trim().length() == 0 ) {
            return null;
        } else {
            return value;
        }
    }

    /* Adds a corresponding message to the 'errors' map. */
    private void setError( String paramName, String message ) {
        errors.put( paramName, message );
    }

    /* Copies all the entries of the first map to the second one. */
    private void copyErrors( Map source, Map destination ) {
        destination.putAll( source );
    }

    /* Prints the error map line by line. */
    private static void printMap( Map map ) {
        Iterator it = map.entrySet().iterator();
        while ( it.hasNext() ) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println( pairs.getKey() + ": " + pairs.getValue() );
            // !!! Don't do remove, it will clear the 'errors' map and fake the
            // validation results.
            // it.remove(); // avoids a ConcurrentModificationException
        }
    }
} // end of class FormGem