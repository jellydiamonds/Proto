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

@WebServlet( urlPatterns = { "/start", "/listGems" } )
public class ListGems extends HttpServlet {
    public static final String VIEW_GEMS    = "/WEB-INF/list_gems.jsp";

    // public static final String ATT_GEMS_LIST = "sessionGemsList";
    public static final String ATT_GEMS_MAP = "sessionGemsMap";

    @EJB
    IGemLocal                  gemBeanLocal;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // Reloading gems Map, as there can be new entries in the database
        HttpSession session = request.getSession();
        Map<Long, Gem> gemsMap = gemBeanLocal.getGemsMap();
        session.setAttribute( ATT_GEMS_MAP, gemsMap );

        /*
         * !!! A MODIFIER!!! Ne pas efficace (afficher par 10,20..., mais pas
         * TOUTES les gemmes)
         */

        request.getRequestDispatcher( VIEW_GEMS ).forward( request, response );
    }
}