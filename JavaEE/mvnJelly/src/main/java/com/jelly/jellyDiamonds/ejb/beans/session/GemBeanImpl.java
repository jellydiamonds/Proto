package com.jelly.jellyDiamonds.ejb.beans.session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;

@Stateless( name = "GEM_EJB" )
public class GemBeanImpl implements IGemLocal, IGemRemote {

    private static final String JPQL_getAllGems = "SELECT g FROM Gem g";

    @PersistenceContext( unitName = "PU_JELLY" )
    private EntityManager       entityManager;

    /* =============== METHODS IMPLEMENTATIONS ============== */

    /* Adds a gem to the database. */
    @Override
    public void addGem( Gem gem ) throws GemBeanException {
        try {
            entityManager.persist( gem );
        } catch ( Exception e ) {
            throw new GemBeanException( e );
        }
    }

    /* Updates an existing gem after editing. */
    @Override
    public void updateGem( Gem gem ) throws GemBeanException {
        try {
            entityManager.merge( gem );
        } catch ( Exception e ) {
            throw new GemBeanException( e );
        }
    }

    /* Deletes a gem from the database. */
    @Override
    public void deleteGem( Long id ) throws GemBeanException {
        try {
            Gem gem = entityManager.find( Gem.class, id );
            entityManager.remove( gem );
        } catch ( Exception e ) {
            throw new GemBeanException( e );
        }
    }

    /* Looks for a gem in the database. */
    @Override
    public Gem findGem( Long id ) throws GemBeanException {
        Gem gem = new Gem();
        try {
            gem = entityManager.find( Gem.class, id );
        } catch ( Exception e ) {
            throw new GemBeanException( e.getMessage() );
        }
        return gem;
    }

    /* Returns the LIST of all the gems stored in the database. */
    @Override
    public List<Gem> getGemsList() throws GemBeanException {
        try {
            Query query = entityManager.createQuery( JPQL_getAllGems );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new GemBeanException( e );
        }
    }

    /* Returns the MAP of all the gems stored in the database (id as the key). */
    @Override
    public Map<Long, Gem> getGemsMap() throws GemBeanException {
        List<Gem> gemsList;
        Map<Long, Gem> gemsMap = new HashMap<Long, Gem>();
        // Getting all gems from the database as a list.
        try {
            Query query = entityManager.createQuery( JPQL_getAllGems );
            gemsList = query.getResultList();
        } catch ( Exception e ) {
            throw new GemBeanException( e );
        }
        // Looping through all elements in the list, populating the map.
        Iterator<Gem> it = gemsList.iterator();
        while ( it.hasNext() ) {
            Gem gem = (Gem) it.next();
            gemsMap.put( gem.getId(), gem );
        }
        return gemsMap;
    }

    /* Returns 3 first letters of gem species */
    // TO DO

    /* Returns the total number of DB entries. */
    /*
     * public int getNumberGems() throws GemBeanException { try { Query query =
     * entityManager.createQuery( JPQL_getAllGems ); return
     * query.getResultList().size(); } catch ( Exception e ) { throw new
     * GemBeanException( e ); } }
     */
}