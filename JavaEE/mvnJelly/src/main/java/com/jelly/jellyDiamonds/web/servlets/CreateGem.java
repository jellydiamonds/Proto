package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;
import com.jelly.jellyDiamonds.ejb.forms.FormGem;

@WebServlet( name = "createGemServlet", urlPatterns = "/createGem" )
public class CreateGem extends HttpServlet {
    public static final String ATT_GEM      = "currentGem";
    public static final String ATT_GEMS     = "sessionGemsList";
    public static final String ATT_FORM     = "form";

    public static final String VIEW_FORM    = "/WEB-INF/create_gem.jsp";
    public static final String VIEW_SUCCESS = "/WEB-INF/gem_created.jsp";

    /* Injection de l'objet m�tier */
    @EJB
    private IGemLocal          gemBeanLocal;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /* Affichage de la page de la cr�ation d'une gemme. */
        getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        /* Pr�paration de l'objet de traitement. */
        FormGem form = new FormGem( gemBeanLocal );
        /*
         * La m�thode createGem: .............................................
         * 1) ajoute la gemme A LA MAP DE LA SESSION ..........................
         * 2) ajoute la gemme A LA BASE DE DONNEES ...........................
         */
        Gem currentGem = form.createGem( request );
        /* Ajout des attributs en requ�te */
        request.setAttribute( ATT_GEM, currentGem );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErrors().isEmpty() ) {
            /* R�cup�ration d'une Liste des gemmes */
            HttpSession session = request.getSession();
            List<Gem> sessionGemsList = (List<Gem>) session.getAttribute( ATT_GEMS );
            /* Si la Liste est nulle, on cr�e une nouvelle */
            if ( sessionGemsList == null ) {
                sessionGemsList = new ArrayList<Gem>();
            }
            /* Ajout du client dans la Liste */
            sessionGemsList.add( currentGem );
            /* Enregistrer la Liste, afficher la fiche r�capitulatif */
            session.setAttribute( ATT_GEMS, sessionGemsList );
            /* Affichage d'une JSP */
            getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            /* Sinon, r�-afficher le formulaire de cr�ation avec des erreurs. */
            getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
        }
    }
}
