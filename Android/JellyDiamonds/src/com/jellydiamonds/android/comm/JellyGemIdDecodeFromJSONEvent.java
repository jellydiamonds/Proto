package com.jellydiamonds.android.comm;

import java.io.InputStream;


import com.jellydiamonds.android.metier.GemID;

public interface JellyGemIdDecodeFromJSONEvent {

	/**
	 * If boolean is fixed to false, this should stop the decoding process
	 * 
	 * @param owner
	 * @return
	 */
	public boolean onUserDecode( long owner ); 
	public boolean onDataDecode( GemID gem );
	
	/**
	 * Picture can be null !
	 * @param picture
	 */
	public void onPictureDecode( InputStream picture, GemID gem );
}
