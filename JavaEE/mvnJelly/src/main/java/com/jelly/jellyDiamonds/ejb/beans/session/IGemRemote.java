package com.jelly.jellyDiamonds.ejb.beans.session;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;

@Remote
public interface IGemRemote {
    /* Adds a gem to the database. */
    public void addGem( Gem gem ) throws GemBeanException;

    /* Updates an existing gem after editing. */
    public void updateGem( Gem gem ) throws GemBeanException;

    /* Deletes a gem from the database. */
    public void deleteGem( Long id ) throws GemBeanException;

    /* Looks for a gem in the database. */
    public Gem findGem( Long id ) throws GemBeanException;

    /* Returns the LIST of all the gems stored in the database. */
    public List<Gem> getGemsList() throws GemBeanException;

    /* Returns the MAP of all the gems stored in the database (id as the key). */
    public Map<Long, Gem> getGemsMap() throws GemBeanException;

    /* Returns the total number of DB entries. */
    // public int getNumberGems() throws GemBeanException;
}
