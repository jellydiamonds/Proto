package com.jellydiamonds.android.metier;

public class GemStatusDeleted extends GemStatus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1641134954149481822L;

	/**
	 * Gem-ID is in Deleted status when it has been published and then removed from JellyDiamonds.
	 */
	public GemStatusDeleted()
	{
		this.mCurrentStatus = "deleted";
		this.mCurrentStatusNumber = 5;
	}

}
