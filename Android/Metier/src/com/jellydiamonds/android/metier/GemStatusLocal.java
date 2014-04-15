package com.jellydiamonds.android.metier;

public class GemStatusLocal extends GemStatus{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7996474409875887610L;

	/**
	 * Gem-ID is not synchronized with JellyDiamonds (not published...)
	 */
	public GemStatusLocal()
	{
		this.mCurrentStatus = "local";
		this.mCurrentStatusNumber = 2;
	}

}
