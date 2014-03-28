package com.jelly.jellyDiamonds.ejb.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.GemBeanException;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;

public final class FormGem {
    private static final String PARAM_ID    = "gemId";
    private static final String PARAM_TYPE  = "gemType";
    private static final String PARAM_COLOR = "gemColor";
    /* Seulement pour stocker les erreurs */
    private static final String PARAM_SQL   = "sql";

    /* Un objet métier qui gère la communication avec la base de données */
    private IGemLocal           gemBeanLocal;

    private String              result;
    private Map<String, String> errors      = new HashMap<String, String>();

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
        String type = getValeurChamp( request, PARAM_TYPE );
        String color = getValeurChamp( request, PARAM_COLOR );
        Gem gem = new Gem();

        /* Traitement et validation des champs */
        processType( type, gem );
        processColor( color, gem );

        /* Si tous les champs sont correctements remplis */
        if ( errors.isEmpty() ) {
            try {
                /* Création d'une gemme avec son enregistrement dans la BDD */
                gemBeanLocal.addGem( gem );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, e.getMessage() );
            }
        }

        /* Initialisation du résultat global de la validation. */
        if ( errors.isEmpty() ) {
            result = "Gem created successfully";
        } else {
            result = "Error while creating a gem. ";
        }
        System.out.println( result );
        return gem;
    }

    /*
     * Recherche d'une gemme en utilisant la méthode 'findGem' de l'objet
     * gemBeanLocal.
     */
    public Gem findGem( HttpServletRequest request ) {
        String gemIdStr = getValeurChamp( request, PARAM_ID );
        Gem gem = new Gem();
        /* Validation des champs */
        processId( gemIdStr );

        /* Si tous les champs sont correctements remplis */
        if ( errors.isEmpty() ) {
            try {
                /* Recherche de la gemme dans la BDD */
                Long gemId = Long.parseLong( gemIdStr );
                gem = gemBeanLocal.findGem( gemId );
            } catch ( GemBeanException e ) {
                setError( PARAM_SQL, "CAUGHT a GemBeanException! Gem #" + gemIdStr + "not found." );
            }
        }

        if ( gem == null ) {
            setError( PARAM_SQL, "ouch! Gem #" + gemIdStr + "not found." );
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
    private void processType( String type, Gem gem ) {
        try {
            validateType( type );
        } catch ( FormValidationException e ) {
            setError( PARAM_TYPE, e.getMessage() );
        }
        gem.setType( type );
    }

    private void processColor( String color, Gem gem ) {
        try {
            validateColor( color );
        } catch ( FormValidationException e ) {
            setError( PARAM_COLOR, e.getMessage() );
        }
        gem.setColor( color );
    }

    private void processId( String gemIdStr ) {
        try {
            validateId( gemIdStr );
        } catch ( FormValidationException e ) {
            setError( PARAM_ID, e.getMessage() );
        }
    }

    private void validateType( String type ) throws FormValidationException {
        if ( type != null ) {
            if ( type.length() < 3 ) {
                throw new FormValidationException( "Le type d'une gemme doît contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir le type d'une gemme." );
        }
    }

    private void validateColor( String color ) throws FormValidationException {
        if ( color != null ) {
            if ( color.length() < 3 ) {
                throw new FormValidationException( "La couleur d'une gemme doît contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir la couleur d'une gemme." );
        }
    }

    private void validateId( String id ) throws FormValidationException {
        if ( id != null ) {
            if ( !id.matches( "[0-9]*" ) ) {
                throw new FormValidationException( "Id must contain only digits." );
            }
        } else {
            throw new FormValidationException( "Please enter an id" );
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
} // end of class FormGem