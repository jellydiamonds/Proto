package com.jelly.jellyDiamonds.web.filters;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;

/**
 * Un Filter qui permet de charger toutes les donn�es de la base (table de
 * Gems). Cela se fait lors d'un premier acces � n'importe quelle page du site.
 * Cette proc�dure permet de ne charger toutes les donn�es de la base qu'une
 * seule fois, au d�but de la session.
 */
@WebFilter( urlPatterns = "/*" )
public class LoadDatabaseInSession implements Filter {
    public static final String ATT_GEMS = "sessionGemsList";

    /*
     * Controleur a besoin de la couche METIER. On utilise un interface LOCAL,
     * comme on est sur le meme serveur que l'EJB.
     */
    @EJB
    private IGemLocal          gemBeanLocal;

    /*
     * Au lieu de mettre cette annotation EJB, on peut appeler le constructeur
     * de ControllerServlet et y faire un LOOKUP. Dans ce cas, la servlet
     * contacte l'annuaire, r�cup�re la r�f�rence, cr�e un PROXY pour
     * communiquer avec l'EJB. Mais, l'annotation fait la meme chose!!!
     * (injection). Pour que l'injection marche, il faut que 2 projets soient
     * d�ploy�s sur le meme serveur.
     */

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException,
            ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        /* R�cup�ration de la session depuis la requ�te */
        HttpSession session = request.getSession();

        /*
         * Si la LISTE des gemmes dans session est vide, on lui peuple avec les
         * donn�es contenues dans la BDD.
         */
        if ( session.getAttribute( ATT_GEMS ) == null ) {
            /*
             * R�cup�ration de la LISTE des gemmes existantes, enregistrement en
             * session
             */
            List<Gem> listGems = gemBeanLocal.getAllGems();
            session.setAttribute( ATT_GEMS, listGems );
        }

        /* Pour terminer, poursuite de la requ�te en cours */
        chain.doFilter( req, resp );
    }

    public void destroy() {
        // TODO Auto-generated method stub
    }

    public void init( FilterConfig arg0 ) throws ServletException {
        // TODO Auto-generated method stub
    }
}