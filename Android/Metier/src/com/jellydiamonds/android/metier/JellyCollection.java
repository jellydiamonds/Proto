package com.jellydiamonds.android.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class JellyCollection implements Serializable {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((mLocalCollection == null) ? 0 : mLocalCollection.hashCode());
		result = prime * result
				+ ((mOwnerID == null) ? 0 : mOwnerID.hashCode());
		result = prime
				* result
				+ ((mSyncedCollection == null) ? 0 : mSyncedCollection
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JellyCollection other = (JellyCollection) obj;
		if (mLocalCollection == null) {
			if (other.mLocalCollection != null)
				return false;
		} else if (!mLocalCollection.equals(other.mLocalCollection))
			return false;
		if (mOwnerID == null) {
			if (other.mOwnerID != null)
				return false;
		} else if (!mOwnerID.equals(other.mOwnerID))
			return false;
		if (mSyncedCollection == null) {
			if (other.mSyncedCollection != null)
				return false;
		} else if (!mSyncedCollection.equals(other.mSyncedCollection))
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3082987100213107689L;
	
	private Long 				mOwnerID = null;
	private List<GemID> 		mLocalCollection = null;
	
	// Key is GemID reference, GemID must be synced
	private Map<String,GemID> 	mSyncedCollection = null;
	

	public JellyCollection( Long ownerID )
	{
		this.mOwnerID = ownerID;
		/**
		 * 	Only Gems which haven't yet their gem reference number
		 */
		this.mLocalCollection = new ArrayList<GemID>();
		
		/**
		 * Gems synced with JellyDiamonds
		 */
		this.mSyncedCollection = new Hashtable<String,GemID>();
		
	}
	
	public Long getOwner()
	{
		return this.mOwnerID;
	}
	
	public void setOwner( Long owner)
	{
		this.mOwnerID = owner;
	}
	
	public int getNumberOfGems()
	{
		return (this.mLocalCollection.size() + this.mSyncedCollection.size());
	}
	
	public Collection<GemID> getAllGems()
	{
		Collection<GemID> l_gems = new ArrayList<GemID>();
		
		for( GemID l_tmp_gem : this.mSyncedCollection.values() )
			l_gems.add(l_tmp_gem);
		
		for( GemID l_tmp_gem : this.mLocalCollection)
			l_gems.add(l_tmp_gem);
		
		return l_gems;
	}
	
	public int getNumberOfSyncedGems()
	{
		return this.mSyncedCollection.size();
	}
	
	public int getNumberOfLocalGems()
	{
		return this.mLocalCollection.size();
	}
	
	public List<GemID> getLocalCollection()
	{
		return this.mLocalCollection;
	}
	
	public Map<String,GemID> getSyncedCollection()
	{
		return this.mSyncedCollection;
	}
	
	public Collection<GemID> getForSaleCollection()
	{
		Collection<GemID> l_gemsForSale = new ArrayList<GemID>();
		
		for( GemID l_tmp : this.mSyncedCollection.values() )
		{
			if( l_tmp.getCurrentStatus().toValue() == 3 ) // Status 3 is ForSale
			{
				l_gemsForSale.add( l_tmp );
			}
		}
		return l_gemsForSale;
	}
	
	public Collection<GemID> getDeletedCollection()
	{
		Collection<GemID> l_gemsDeleted = new ArrayList<GemID>();
		
		for( GemID l_tmp : this.mSyncedCollection.values() )
		{
			if( l_tmp.getCurrentStatus().toValue() == 5 ) // Status 5  is Deleted
			{
				l_gemsDeleted.add( l_tmp );
			}
		}
		return l_gemsDeleted;
	}
}
