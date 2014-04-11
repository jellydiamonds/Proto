using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JDClientRicheCsharp.Class
{
    class GemCollection
    {
    private static long serialVersionUID = -3082987100213107689L;
	
	private String 				mOwnerID {get; set;}
	private List<GemID> 		mLocalCollection{get; set;}
	
	// Key is GemID reference, GemID must be synced
	private Dictionary<int,GemID> 	LocalCollection = null;


    public GemCollection(String ownerID)
	{
		this.mOwnerID = ownerID;
		/**
		 * 	Only Gems which haven't yet their gem reference number
		*/
        this.mLocalCollection = new Dictionary<GemID>();

	}
	
	public String getOwner()
	{
		return this.mOwnerID;
	}
	
	public void setOwner( String owner)
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
    }
}
