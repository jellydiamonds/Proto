package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;

@WebServlet( urlPatterns = "/deleteGem" )
public class DeleteGem extends HttpServlet {
    public static final String PARAM_ID     = "gemID";
    public static final String ATT_GEMS_MAP = "sessionGemsMap";
    public static final String VIEW_GEMS    = "/listGems";

    // EJB injection
    @EJB
    private IGemLocal          gemBeanLocal;

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        // The id of a gem is given in the URL: /deleteGem?gemID=132
        Long idGemToDelete = Long.valueOf( request.getParameter( PARAM_ID ) );

        // 1. Deleting the gem from the database.
        gemBeanLocal.deleteGem( idGemToDelete );

        // 2. Deleting the gem from the session gems MAP and saving the map.
        HttpSession session = request.getSession();
        Map<Long, Gem> sessionGemsMap = (Map<Long, Gem>) session.getAttribute( ATT_GEMS_MAP );
        if ( idGemToDelete != null && sessionGemsMap != null ) {
            sessionGemsMap.remove( idGemToDelete );
            session.setAttribute( ATT_GEMS_MAP, sessionGemsMap );
        }

        // Redirecting to the list view, changing the URL
        response.sendRedirect( request.getContextPath() + VIEW_GEMS );
    }
}