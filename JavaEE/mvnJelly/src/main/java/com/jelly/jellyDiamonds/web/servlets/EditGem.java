package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jelly.jellyDiamonds.ServerConfig;
import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;
import com.jelly.jellyDiamonds.ejb.forms.FormGem;

@WebServlet( urlPatterns = "/editGem", initParams = @WebInitParam( name = "path", value = ServerConfig.photoFolderPath ) )
@MultipartConfig( location = ServerConfig.photoFolderPath, maxFileSize = ServerConfig.maxFileSize,
        maxRequestSize = ServerConfig.maxRequestSize, fileSizeThreshold = ServerConfig.fileSizeThreshold )
public class EditGem extends HttpServlet {
    public static final String ATT_ACTION   = "action";
    public static final String ATT_GEM      = "currentGem";
    public static final String ATT_FORM     = "form";
    public static final String PARAM_ID     = "gemID";
    public static final String PARAM_PATH   = "path";
    public static final String VIEW_FORM    = "/WEB-INF/add_edit_gem.jsp";
    public static final String VIEW_SUCCESS = "/WEB-INF/gemID.jsp";

    // EJB injection
    @EJB
    private IGemLocal          gemBeanLocal;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        System.out.println( "EditGem doGet()" );
        /*
         * Looking for a gem to edit. Its ID is given as a parameter:
         * /editGem?gemID=132
         */
        Long id = Long.valueOf( request.getParameter( PARAM_ID ) );
        Gem gemToEdit = gemBeanLocal.findGem( id );
        // Saving a gem to edit as an attribute.
        request.setAttribute( ATT_GEM, gemToEdit );
        request.setAttribute( ATT_ACTION, "/editGem?gemID=" + id );
        // Displaying a form
        getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /*
         * When submitting a form, an 'action' attribute of a form is
         * /editGem?gemID=132. Thus, the request will contain: 1) id of a gem to
         * edit; 2) form parameters with all new gem characteristics. We pass
         * this request to a FormGem object, which will find a gem by its id and
         * update its parameters by the provided values.
         */

        // Reading "path" parameter from the servlet annotation
        String path = this.getServletConfig().getInitParameter( PARAM_PATH );
        /*
         * The gem returned by createGem() doesn't contain an id. But our
         * current request does. The creation date and the reference are
         * extracted from database values of the existing gem.
         */
        Long id = Long.valueOf( request.getParameter( PARAM_ID ) );
        Gem oldGem = gemBeanLocal.findGem( id );
        Date date = oldGem.getCreationDate();
        String reference = oldGem.getReference();
        String photoLink = oldGem.getPhotoLink();

        // Preparing the validation object
        FormGem form = new FormGem( gemBeanLocal );
        /*
         * 1) Creating a Gem object from request parameters. If the image is
         * changed, save it on disk.
         */
        Gem editedGem = form.createGem( request, path );
        editedGem.setId( id );
        editedGem.setCreationDate( date );
        editedGem.setReference( reference );
        editedGem.setPhotoLink( photoLink );
        // 2) MERGING with an existing gem in the database
        form.updateGem( editedGem );
        // 3) Saving the attributes
        request.setAttribute( ATT_GEM, editedGem );
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_ACTION, "/editGem?gemID=" + editedGem.getId() );

        if ( form.getErrors().isEmpty() ) {
            // Gem_ID summary page if no errors
            getServletContext().getRequestDispatcher( VIEW_SUCCESS ).forward( request, response );
        } else {
            // Otherwise, the same form, with errors
            getServletContext().getRequestDispatcher( VIEW_FORM ).forward( request, response );
        }

    }
}