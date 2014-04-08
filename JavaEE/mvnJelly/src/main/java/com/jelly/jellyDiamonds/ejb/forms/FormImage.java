package com.jelly.jellyDiamonds.ejb.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.sun.mail.util.MimeUtil;

public final class FormImage {
    private static final String PARAM_IMAGE = "imageGem";
    private static final int    BUFFER_SIZE = 10 * 1024;

    private Map<String, String> errors      = new HashMap<String, String>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public String saveImage( HttpServletRequest request, String path ) throws FormValidationException {
        /* Getting the content of file form field using getPart() method. */
        String imageFilename = null;
        InputStream imageContent = null;
        try {
            Part part = request.getPart( PARAM_IMAGE );
            /*
             * Using static method getFilename() to check whether we're dealing
             * with a field of a type "file".
             */
            imageFilename = getFilename( part );

            /* Not null return means we're dealing with input type="file". */
            if ( imageFilename != null && !imageFilename.isEmpty() ) {
                /*
                 * Antibug for Internet Explorer, which transfers a local path
                 * of the client's machine. Example:
                 * C:/dossier/sous-dossier/fichier.ext
                 * 
                 * We have to select only the file's name and extension.
                 */

                imageFilename = imageFilename.substring( imageFilename.lastIndexOf( "/" )
                        + 1 ).substring( imageFilename.lastIndexOf( "\\" ) + 1 );

                /* Extracting the content of the file */
                imageContent = part.getInputStream();

                /* Extracting the MIME type of the file from the InputStream */
                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
                Collection<?> mimeTypes = MimeUtil.getMimeTypes( imageContent );
                /* If it is an image, the MIME header will start by "image". */
                if ( mimeTypes.toString().startsWith( "image" ) ) {
                    /* Writing the image on the disk */
                    ecrireFichier( imageContent, imageFilename, path );
                } else {
                    throw new FormValidationException( "Selected file must be of an image type." );
                }
            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception produced if the data size is higher than that stated in
             * 
             * @MultipartConfig annotation of servlet declaration.
             */
            e.printStackTrace();
            throw new FormValidationException( "Image size must not be higher than 10 Mb." );
        } catch ( IOException e ) {
            /*
             * Exception produced if an error of folders occurs (non existing
             * folder, not sufficient access rights, etc.)
             */
            e.printStackTrace();
            throw new FormValidationException( "Server configuration error." );
        } catch ( ServletException e ) {
            /*
             * Exception produced if the request is not of multipart/f0rm-data
             * type. This happens only if the user tries to connect the servlet
             * via the form different from one he is supposed to use.. a
             * pirate?!
             */
            e.printStackTrace();
            throw new FormValidationException(
                    "Unsupported request type. Please use the appropriate form to send your file." );
        }

        return imageFilename;
    }

    // Adds a message to a corresponding field of errors map. */
    private void setError( String param, String message ) {
        errors.put( param, message );
    }

    /*
     * The only goal of this utility method is to analyse the
     * "content-disposition" header. If the "filename" parameter is present,
     * then the form field is of type File, and the method returns the filename.
     * Otherwise, we're dealing with a classic form field (text, etc.) and the
     * method returns null.
     */
    private static String getFilename( Part part ) {
        // Loop over parameters of the "content-disposition" header
        for ( String contentDisposition : part.getHeader( "Content-Disposition" ).split( ";" ) ) {
            // If "filename" is present, the name of the file is returned
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( "=" ) + 1 ).trim().replace( "\"", "" );
            }
        }
        // If nothing was found
        return null;
    }

    /* Utility method which writes a given file in a given folder. */
    private void writeFile( InputStream content, String filename, String path ) throws FormValidationException {
        // Preparing the flux
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            // Opening the flux
            input = new BufferedInputStream( content, BUFFER_SIZE );
            output = new BufferedOutputStream( new FileOutputStream( new
                    File( path + filename ) ), BUFFER_SIZE );

            // Reading a given file and writing it content on the disk.
            byte[] buffer = new byte[BUFFER_SIZE];
            int length = 0;
            while ( ( length = input.read( buffer ) ) > 0 ) {
                output.write( buffer, 0, length );
            }
        } catch ( Exception e ) {
            throw new FormValidationException( "Error while writing the file on the disk." );
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