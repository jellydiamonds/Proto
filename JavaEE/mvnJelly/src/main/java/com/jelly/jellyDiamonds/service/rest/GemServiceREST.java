package com.jelly.jellyDiamonds.service.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;
import com.jelly.jellyDiamonds.ejb.beans.session.IGemLocal;

@Stateless
@Path( "/gems" )
public class GemServiceREST {
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

    // La couche 'service' fait appel au couche 'metier'

    @GET
    @POST
    @Path( "/addGem/{species}/{color}" )
    public void ws_addGem(
            @PathParam( value = "species" ) String species,
            @PathParam( value = "color" ) String color ) {
        Gem gem = new Gem();
        gem.setSpecies( species );
        gem.setColor( color );
        gemBeanLocal.addGem( gem );
    }

    @GET
    @Path( "/findGem/{id}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Gem ws_findGem(
            @PathParam( value = "id" ) Long id ) {
        return gemBeanLocal.findGem( id );
    }

    @GET
    @Path( "/getAllGems" )
    public List<Gem> ws_getAllGems() {
        return gemBeanLocal.getAllGems();
    }
}