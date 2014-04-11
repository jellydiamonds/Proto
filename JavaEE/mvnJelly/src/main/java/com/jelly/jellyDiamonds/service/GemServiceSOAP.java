package com.jelly.jellyDiamonds.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;

/* Stateless, comme il y'aura une seule instance pour tous les clients */
@Stateless
/*
 * WebService de type Jax-WS. Il sera publié dans une adresse
 * "http://localhost:8080/GemMarket_EJB/GemWS". Pour voir son WSDL, il faut
 * ajouter "?WSDL" à la fin de cette adresse dans un navigateur.
 */
@WebService
public class GemServiceSOAP {
    /*
     * Injection d'un EJB. C'est cette annotation qui indique que 'gemBeanLocal'
     * est un EJB. Le conténeur va chercher dans annuaire, quel EJB implémente
     * l'interface IGemLocal. Donc, quand on est en local, on n'a pas besoin de
     * connaitre le nom JNDI (comme
     * "java:global/GemMarket_EJB/GEM_EJB!com.marmelade.ejb.bean.session.IGemLocal"
     * ).
     */
    @EJB
    private IGemLocal gemBeanLocal;

    /* La couche 'service' fait appel au couche 'metier' */

    @WebMethod
    public void ws_addGem( Gem gem ) {
        gemBeanLocal.addGem( gem );
    }

    @WebMethod
    public void ws_deleteGem( Long id ) {
        gemBeanLocal.deleteGem( id );
    }

    @WebMethod
    public Gem ws_findGem( Long id ) {
        return gemBeanLocal.findGem( id );
    }

    /*
     * @WebMethod public List<Gem> ws_getAllGems() { return
     * gemBeanLocal.getAllGems(); }
     */
}