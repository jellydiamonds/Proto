package com.jelly.jellyDiamonds.web.servlets;

import java.io.IOException;
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

@WebServlet( name = "listGemsServlet", urlPatterns = { "/start", "/listGems" } )
public class ListGems extends HttpServlet {
    public static final String VIEW_GEMS = "/WEB-INF/list_gems.jsp";
    public static final String ATT_GEMS  = "sessionGemsList";

    @EJB
    IGemLocal                  gemLocalBean;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        /* !!! A MODIFIER!!! Ne pas efficace */
        /* Récupération d'une Liste des gemmes de la BASE DE DONNEES */
        List<Gem> sessionGemsList = gemLocalBean.getAllGems();

        /* Enregistrer la Liste, afficher la liste */
        HttpSession session = request.getSession();
        session.setAttribute( ATT_GEMS, sessionGemsList );
        request.getRequestDispatcher( VIEW_GEMS ).forward( request, response );
    }
}