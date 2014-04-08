package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;
import com.jelly.jellyDiamonds.ejb.forms.FormGem;

/* This servlet will be called by the URL of type /gems?id=281, where 281 is the value of PARAM_GEMID*/
@WebServlet( name = "GemsServlet", urlPatterns = "/gems" )
public class Gems extends HttpServlet {
    public static final String ATT_GEM      = "currentGem";
    public static final String ATT_FORM     = "form";
    public static final String PARAM_GEMID  = "id";
    public static final String VIEW_FORM    = "/WEB-INF/find_gem.jsp";
    public static final String VIEW_SUCCESS = "/WEB-INF/gemID.jsp";

    /* Injection de l'objet métier */
    @EJB
    private IGemLocal          gemBeanLocal;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // Finding a gem with an id stored in parameter.

        /* Préparation de l'objet de traitement. */
        FormGem form = new FormGem( gemBeanLocal );
        /* Recherche de la gemme. */
        Gem currentGem = form.findGem( request );

        /* Ajout des attributs en requête */
        request.setAttribute( ATT_GEM, currentGem );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErrors().isEmpty() ) {
            /* Afficher la fiche récapitulatif */
            getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            /* Sinon, ré-afficher le formulaire de recherche avec des erreurs. */
            getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
        }
    }
}