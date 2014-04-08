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

@WebServlet( name = "editGemServlet", urlPatterns = "/editGem" )
public class EditGem extends HttpServlet {
    public static final String ATT_ACTION   = "action";
    public static final String ATT_GEM      = "currentGem";
    public static final String ATT_FORM     = "form";
    public static final String ATT_ID       = "gemID";
    public static final String VIEW_FORM    = "/WEB-INF/create_edit_gem.jsp";
    public static final String VIEW_SUCCESS = "/WEB-INF/gemID.jsp";

    // EJB injection
    @EJB
    private IGemLocal          gemBeanLocal;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /*
         * Looking for a gem to edit. Its ID is given in the URL:
         * /editGem?gemID=132
         */
        FormGem form = new FormGem( gemBeanLocal );
        Gem currentGem = form.findGem( request );

        request.setAttribute( ATT_GEM, currentGem );
        request.setAttribute( ATT_ACTION, "/editGem?gemID=" + currentGem.getId() );
        // Passing the request to the display form
        getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /*
         * Looking for a gem to edit. Its ID is given in request
         */
        FormGem form = new FormGem( gemBeanLocal );
        Gem oldGem = form.findGem( request );
        request.setAttribute( ATT_ID, oldGem.getId() );
        // Changing gem's parameters, without creating a new gem in the database
        Gem editedGem = form.editGem( request, oldGem );

        request.setAttribute( ATT_GEM, editedGem );
        request.setAttribute( ATT_FORM, form );

        // Selecting a page to display
        if ( form.getErrors().isEmpty() ) {
            // Gem_ID summary page if no errors
            getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            // The same form, with errors
            getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
        }
    }
}