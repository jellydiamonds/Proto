package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jelly.jellyDiamonds.service.rest.JellyJSON;

@WebServlet( urlPatterns = "/api/gems/addGem" )
public class AddGemREST extends HttpServlet {
    public static final String PARAM_GEM_JSON = "jsonTextarea";

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        // Getting a string from a HTTP request
        String gemString = request.getParameter( PARAM_GEM_JSON );
        JsonObject gemJson = JellyJSON.parseToJson( gemString );
        JellyJSON.printJsonObject( gemJson );
    }
}