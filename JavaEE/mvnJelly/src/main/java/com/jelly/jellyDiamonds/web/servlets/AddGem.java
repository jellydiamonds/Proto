package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jelly.jellyDiamonds.ServerConfig;
import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;
import com.jelly.jellyDiamonds.ejb.forms.FormGem;

@WebServlet( urlPatterns = "/addGem", initParams = @WebInitParam( name = "path", value = ServerConfig.photoFolderPath ) )
@MultipartConfig( location = ServerConfig.photoFolderPath, maxFileSize = ServerConfig.maxFileSize,
        maxRequestSize = ServerConfig.maxRequestSize, fileSizeThreshold = ServerConfig.fileSizeThreshold )
public class AddGem extends HttpServlet {
    public static final String ATT_ACTION   = "action";
    public static final String ATT_GEM      = "currentGem";
    // public static final String ATT_GEMS_LIST = "sessionGemsList";
    public static final String ATT_GEMS_MAP = "sessionGemsMap";
    public static final String ATT_FORM     = "form";
    public static final String PARAM_PATH   = "path";
    public static final String VIEW_FORM    = "/WEB-INF/add_edit_gem.jsp";
    public static final String VIEW_SUCCESS = "/WEB-INF/gemID.jsp";

    // EJB injection
    @EJB
    private IGemLocal          gemBeanLocal;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        request.setAttribute( ATT_ACTION, "/addGem" );
        // Displaying the form
        getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // Reading "path" parameter from the servlet annotation
        String path = this.getServletConfig().getInitParameter( PARAM_PATH );

        // Preparing the validation object
        FormGem form = new FormGem( gemBeanLocal );
        // 1) Creating a Gem object from request parameters, saving the image on
        // disk.
        Gem addedGem = form.createGem( request, path );
        // 2) Adding a new gem in the database
        form.addNewGem( addedGem );
        // 3) Saving the attributes
        request.setAttribute( ATT_GEM, addedGem );
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_ACTION, "/addGem" );

        // 4) Adding a gemID TO THE SESSION, displaying result
        if ( form.getErrors().isEmpty() ) {
            // Getting gems MAP
            HttpSession session = request.getSession();
            Map<Long, Gem> sessionGemsMap = (Map<Long, Gem>) session.getAttribute( ATT_GEMS_MAP );
            // If the MAP is empty, creating a new one
            if ( sessionGemsMap == null ) {
                sessionGemsMap = new HashMap<Long, Gem>();
            }
            // Adding a new gem to the map, saving the map
            sessionGemsMap.put( addedGem.getId(), addedGem );
            session.setAttribute( ATT_GEMS_MAP, sessionGemsMap );
            // Displaying the resulting gemID summary
            getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            // Display the form with errors included
            getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
        }
    }
}