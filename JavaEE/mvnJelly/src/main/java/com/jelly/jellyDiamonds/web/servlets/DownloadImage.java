package com.jelly.jellyDiamonds.web.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jelly.jellyDiamonds.ServerConfig;

@WebServlet( urlPatterns = "/images/*", initParams = @WebInitParam( name = "path", value = ServerConfig.photoFolderPath ) )
public class DownloadImage extends HttpServlet {
    private static final int   BUFFER_SIZE = 10 * 1024;
    public static final String PARAM_PATH  = "path";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {

        // Reading "path" parameter from the servlet annotation
        String path = this.getServletConfig().getInitParameter( PARAM_PATH );

        // Extracting a filename given in the URL
        String fileRequired = request.getPathInfo();

        // If the file doesn't exist, return the '404' error.
        if ( fileRequired == null || "/".equals( fileRequired ) ) {
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        /*
         * Decoding the name of the file, as it may contain blanks and special
         * characters. Preparing File object.
         */
        fileRequired = URLDecoder.decode( fileRequired, "UTF-8" );
        File file = new File( path, fileRequired );

        if ( !file.exists() ) {
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        // Extracting the type of a file
        String type = getServletContext().getMimeType( file.getName() );
        // If a type is unknown, initializing a default type
        if ( type == null ) {
            type = "application/octet-stream";
        }

        // HTTP response
        response.reset();
        response.setBufferSize( BUFFER_SIZE );
        response.setContentType( type );
        response.setHeader( "Content-Length", String.valueOf( file.length() ) );
        response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

        // Preparing the flux
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            // Open the flux
            input = new BufferedInputStream( new FileInputStream( file ), BUFFER_SIZE );
            output = new BufferedOutputStream( response.getOutputStream(), BUFFER_SIZE );

            // Reading a file and writing its content on HTTP response
            byte[] buffer = new byte[BUFFER_SIZE];
            int length = 0;
            while ( ( length = input.read( buffer ) ) > 0 ) {
                output.write( buffer, 0, length );
            }
        } finally {
            try {
                output.close();
            } catch ( IOException ignore ) {
            }
            try {
                input.close();
            } catch ( IOException ignore ) {
            }
        }

    }
}