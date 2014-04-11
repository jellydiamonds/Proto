package com.jelly.jellyDiamonds.web.filters;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
    public static final String ATT_GEMS_LIST = "sessionGemsList";
    public static final String ATT_GEMS_MAP  = "sessionGemsMap";

    // EJB injection
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

        /* R�cup�ration de la session depuis la requ�te */
        HttpSession session = request.getSession();

        /*
         * If the MAP of gems in session is empty, we populate it with the
         * database entries.
         */
        if ( session.getAttribute( ATT_GEMS_MAP ) == null ) {
            Map<Long, Gem> gemsMap = gemBeanLocal.getGemsMap();
            session.setAttribute( ATT_GEMS_MAP, gemsMap );
        }
        /*
         * Old version: if ( session.getAttribute( ATT_GEMS_LIST ) == null ) {
         * List<Gem> listGems = gemBeanLocal.getAllGems(); session.setAttribute(
         * ATT_GEMS_LIST, listGems ); }
         */

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