package com.jelly.jellyDiamonds.ejb.beans.session;

import java.util.List;

import javax.ejb.Remote;

import com.jelly.jellyDiamonds.ejb.beans.entity.Gem;

@Remote
public interface IGemRemote {
    /* Adds a gem to the database. */
    public void addGem( Gem gem ) throws GemBeanException;

    /* Deletes a gem from the database. */
    public void deleteGem( Long id ) throws GemBeanException;

    /* Looks for a gem in the database. */
    public Gem findGem( Long id ) throws GemBeanException;

    /* Returns the list of all the gems stored in the database. */
    public List<Gem> getAllGems() throws GemBeanException;

    /* Returns the total number of DB entries. */
    // public int getNumberGems() throws GemBeanException;
}
