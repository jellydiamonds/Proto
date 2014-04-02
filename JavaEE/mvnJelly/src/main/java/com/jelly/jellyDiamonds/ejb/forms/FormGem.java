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
    // Regular expressions
    private static final String REGEX_MASS           = "[0-9]*(\\.[0-9]*)*";
    private static final String REGEX_SIZE           = "[0-9]*(\\.[0-9]*)*";
    private static final String REGEX_PRICECURRENCY  = "[A-Z][A-Z][A-Z]";
    private static final String REGEX_PRICEVALUE     = "[0-9]*(\\.[0-9]*)*";

    /* Un objet métier qui gère la communication avec la base de données */
    private IGemLocal           gemBeanLocal;

    private String              result;
    private Map<String, String> errors               = new HashMap<String, String>();

    /* Constructeur avec un objet métier en paramètre */
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
     * Création d'une gemme en utilisant la méthode 'addGem' de l'objet
     * gemBeanLocal.
     */
    public Gem createGem( HttpServletRequest request ) {
        // String reference = getValeurChamp( request, PARAM_REFERENCE );
        String species = getValeurChamp( request, PARAM_SPECIES );
        String color = getValeurChamp( request, PARAM_COLOR );
        String shape = getValeurChamp( request, PARAM_SHAPE );
        String cut = getValeurChamp( request, PARAM_CUT );
        String massString = getValeurChamp( request, PARAM_MASS );
        String sizeXString = getValeurChamp( request, PARAM_SIZE_X );
        String sizeYString = getValeurChamp( request, PARAM_SIZE_Y );
        String sizeZString = getValeurChamp( request, PARAM_SIZE_Z );
        String clarity = getValeurChamp( request, PARAM_CLARITY );
        String enhancement = getValeurChamp( request, PARAM_ENHANCEMENT );
        String origin = getValeurChamp( request, PARAM_ORIGIN );
        String certificate = getValeurChamp( request, PARAM_CERTIFICATE );
        String comments = getValeurChamp( request, PARAM_COMMENTS );
        String priceCurrency = getValeurChamp( request, PARAM_PRICE_CURRENCY );
        String priceValueString = getValeurChamp( request, PARAM_PRICE_VALUE );
        String supplierIDString = getValeurChamp( request, PARAM_SUPPLIER_ID );
        // String photoLink = getValeurChamp( request, PARAM_PHOTO_LINK );
        String light = getValeurChamp( request, PARAM_LIGHT );

        Gem gem = new Gem();
        // Processing and validation of parameters, entered by user.
        processSpecies( species, gem );
        processColor( color, gem );
        processShape( shape, gem );
        processCut( cut, gem );
        processMass( massString, gem );
        processSize( sizeXString, sizeYString, sizeZString, gem );
        processClarity( clarity, gem );
        processEnhancement( enhancement, gem );
        processOrigin( origin, gem );
        processCertificate( certificate, gem );
        processComments( comments, gem );
        processPrice( priceCurrency, priceValueString, gem );
        processSupplierID( supplierIDString, gem );
        // processPhoto ( ??? );
        processLight( light, gem );

        System.out.println( "After processing all fields. errors.isEmpty() = " + errors.isEmpty() );
        printMap( errors );
        // Si tous les champs sont correctements remplis
        if ( errors.isEmpty() ) {
            try {
                // Automatically assigned fields
                processDateAndReference( species, gem );
                // Création d'une gemme avec son enregistrement dans la BDD
                gemBeanLocal.addGem( gem );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, e.getMessage() );
            }
        }
        // Initialisation du résultat global de la validation.
        if ( errors.isEmpty() ) {
            result = "Gem created successfully.";
        } else {
            result = "Error while creating a gem.";
        }
        System.out.println( result );
        printMap( errors );

        return gem;
    }

    /*
     * Recherche d'une gemme en utilisant la méthode 'findGem' de l'objet
     * gemBeanLocal.
     */
    public Gem findGem( HttpServletRequest request ) {
        String gemIDStr = getValeurChamp( request, PARAM_ID );
        Gem gem = new Gem();
        /* Validation des champs */
        processID( gemIDStr );

        /* Si tous les champs sont correctements remplis */
        if ( errors.isEmpty() ) {
            try {
                /* Recherche de la gemme dans la BDD */
                Long gemID = Long.parseLong( gemIDStr );
                gem = gemBeanLocal.findGem( gemID );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, "CAUGHT a GemBeanException! Gem #" + gemIDStr + "not found." );
            }
        }

        if ( gem == null ) {
            setError( PARAM_SQL, "ouch! Gem #" + gemIDStr + " not found." );
        }

        /* Initialisation du résultat global de la validation. */
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
    private void processSpecies( String species, Gem gem ) {
        try {
            validateSpecies( species );
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

    private void processShape( String shape, Gem gem ) {
        try {
            validateShape( shape );
        } catch ( FormValidationException e ) {
            setError( PARAM_SHAPE, e.getMessage() );
        }
        gem.setShape( shape );
    }

    private void processCut( String cut, Gem gem ) {
        try {
            validateCut( cut );
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

    private void processClarity( String clarity, Gem gem ) {
        try {
            validateClarity( clarity );
        } catch ( FormValidationException e ) {
            setError( PARAM_CLARITY, e.getMessage() );
        }
        gem.setClarity( clarity );
    }

    private void processEnhancement( String enhancement, Gem gem ) {
        try {
            validateEnhancement( enhancement );
        } catch ( FormValidationException e ) {
            setError( PARAM_ENHANCEMENT, e.getMessage() );
        }
        gem.setEnhancement( enhancement );
    }

    private void processOrigin( String origin, Gem gem ) {
        try {
            validateOrigin( origin );
        } catch ( FormValidationException e ) {
            setError( PARAM_ORIGIN, e.getMessage() );
        }
        gem.setOrigin( origin );
    }

    private void processCertificate( String certificate, Gem gem ) {
        try {
            validateCertificate( certificate );
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

    private void processPrice( String priceCurrency, String priceValueString, Gem gem ) {
        Double priceValue = null;
        try {
            priceValue = validatePrice( priceCurrency, priceValueString );
        } catch ( FormValidationException e ) {
            setError( PARAM_PRICE, e.getMessage() );
        }
        gem.setPriceCurrency( priceCurrency );
        gem.setPriceValue( priceValue );
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

    private void processDateAndReference( String species, Gem gem ) {
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
        String speciesCode = species.substring( 0, 3 ).toUpperCase();
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

    private void processLight( String light, Gem gem ) {
        try {
            validateLight( light );
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

    private void validateSpecies( String species ) throws FormValidationException {
        if ( species == null ) {
            throw new FormValidationException( "Please select a gem species." );
        }
    }

    private void validateColor( String color ) throws FormValidationException {
        if ( color == null ) {
            throw new FormValidationException( "Please enter a gem color." );
        }
    }

    private void validateShape( String shape ) throws FormValidationException {
        if ( shape == null ) {
            throw new FormValidationException( "Please select a gem shape." );
        }
    }

    private void validateCut( String cut ) throws FormValidationException {
        if ( cut == null ) {
            throw new FormValidationException( "Please select a gem cut." );
        }
    }

    private Double validateMass( String massString ) throws FormValidationException {
        Double temp = null;
        if ( massString != null ) {
            if ( massString.matches( REGEX_MASS ) ) {
                temp = Double.parseDouble( massString );
            } else {
                throw new FormValidationException( "Mass must be positive decimal" );
            }
        } // else {
          // throw new FormValidationException( "Please enter a mass");
          // }
        return temp;
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
                size = Float.parseFloat( sizeString );
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

    private void validateClarity( String clarity ) throws FormValidationException {
        if ( clarity == null ) {
            throw new FormValidationException( "Please select a gem clarity." );
        }
    }

    private void validateEnhancement( String enhancement ) throws FormValidationException {
        // Nothing for the moment.
    }

    private void validateOrigin( String origin ) throws FormValidationException {
        // Nothing for the moment.
    }

    private void validateCertificate( String certificate ) throws FormValidationException {
        // Nothing for the moment.
    }

    private void validateComments( String comments ) throws FormValidationException {
        // Nothing for the moment.
    }

    private Double validatePrice( String priceCurrency, String priceValueString ) throws FormValidationException {
        Double priceValue = null;
        // If the price value field is empty, we don't check the currency field.
        if ( priceValueString != null ) {
            // If the value is entered, the currency must be selected.
            if ( priceCurrency != null ) {
                if ( !priceCurrency.matches( REGEX_PRICECURRENCY ) ) {
                    throw new FormValidationException( "Currency must contain a 3 letter-code." );
                }
            } else {
                throw new FormValidationException( "Please select a currency." );
            }
            // Now validating the price value field
            if ( priceValueString.matches( REGEX_PRICEVALUE ) ) {
                priceValue = Double.parseDouble( priceValueString );
            }
            if ( priceValue < 0 ) {
                throw new FormValidationException( "Price must be a positive decimal" );
            }
        }
        return priceValue;
    }

    private Long validateSupplierID( String supplierIDString ) throws FormValidationException {
        Long supplierID = -1L;
        if ( supplierIDString != null ) {
            if ( supplierIDString.matches( "[0-9]*" ) ) {
                supplierID = Long.parseLong( supplierIDString );
            } else {
                throw new FormValidationException( "Supplier ID must contain only digits." );
            }
        } else {
            throw new FormValidationException( "Please enter a supplier ID." );
        }
        return supplierID;
    }

    private void validateLight( String light ) throws FormValidationException {
        // Nothing for the moment.
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
    /* Ajoute un message correspondant au champ spécifié à la map des erreurs. */
    private void setError( String paramName, String message ) {
        errors.put( paramName, message );
    }

    /*
     * Méthode utilitaire qui rétourne null si un champ est vide, et son continu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String paramName ) {
        String value = request.getParameter( paramName );
        if ( value == null || value.trim().length() == 0 ) {
            return null;
        } else {
            return value;
        }
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