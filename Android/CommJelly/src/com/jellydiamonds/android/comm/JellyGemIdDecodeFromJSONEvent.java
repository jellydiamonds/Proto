package com.jellydiamonds.android.comm;

import android.graphics.Bitmap;

import com.jellydiamonds.android.metier.GemID;

public interface JellyGemIdDecodeFromJSONEvent {

	/**
	 * If boolean is fixed to false, this should stop the decoding process
	 * @param owner
	 * @return
	 */
	public boolean onUserDecode( String owner ); 
	public boolean onDataDecode( GemID gem );
	public boolean onPictureDecode( Bitmap picture );
}
