package com.jellydiamonds.android.comm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Base64;

import com.jellydiamonds.android.metier.GemID;
import com.jellydiamonds.android.metier.GemSpecies;
import com.jellydiamonds.android.metier.GemStatusFactory;


public class JellySerialize {
	
	private static JellyGemIdDecodeFromJSONEvent evGem = null;

	public static JSONObject SerializeJellyCollection( String ownerID, Collection<GemID> collection )
	{
		JSONObject l_collection = new JSONObject();
		JSONArray l_gemid_array = new JSONArray();
		
		
		
		for( GemID l_tmp_gemid : collection )
			l_gemid_array.put( SerializeJellyGemID( l_tmp_gemid ) );
		
		try {
			l_collection.put( "jellyUser", ownerID);
			l_collection.put( "jellyCollection", l_gemid_array );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return l_collection;
	}
	
	public static boolean UnserializeJellyCollection( JSONObject collectionSerialized )
	{
		if( evGem == null )
			return false;
		
		String l_collectionOwner = null;
		JSONArray l_collectionData = null;
		JSONObject l_currentGem = null;
		
		if ( 	( collectionSerialized.has("jellyUser") ) && 
				( collectionSerialized.has("jellyCollection") ) )
			return false;
		
		try {
			l_collectionOwner = collectionSerialized.getString("collectionUser");
			l_collectionData = collectionSerialized.getJSONArray("jellyCollection");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if( JellySerialize.evGem.onUserDecode(l_collectionOwner) == false )
			return false;
		
		for(int l_index = 0; l_index < l_collectionData.length(); l_index++)
		{
			try {
				l_currentGem = l_collectionData.getJSONObject( l_index );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	public static JSONObject SerializeJellyGemID( GemID gem )
	{
		if( gem == null )
			return null;
		
		JSONObject l_json_gemid = new JSONObject();
		try {
			
			/**
			 * Reference
			 */
			if( gem.getReference() != null)  l_json_gemid.put("reference", gem.getReference() );
			else l_json_gemid.put("reference", "" );
			
			/**
			 * Color
			 */
			if( gem.getColor() != null ) l_json_gemid.put("color", gem.getColor() );										
			else l_json_gemid.put("color", "" );
			
			/**
			 * Mass
			 */
			if( gem.getMass() != null ) l_json_gemid.put("mass", gem.getMass().toString() );							
			else l_json_gemid.put("mass", "" );
			
			/**
			 * SizeX
			 */
			if( gem.getSizeX() != null ) l_json_gemid.put("sizeX", gem.getSizeX().toString() );						
			else l_json_gemid.put("sizeX", "" );	
			
			/**
			 * SizeY
			 */
			if( gem.getSizeY() != null ) l_json_gemid.put("sizeY", gem.getSizeY().toString() );						
			else l_json_gemid.put("sizeY", "" );
			
			/**
			 * SizeZ
			 */
			if( gem.getSizeZ() != null ) l_json_gemid.put("sizeZ", gem.getSizeZ().toString() );							
			else l_json_gemid.put("sizeZ", "" );
			
			/**
			 * Comment
			 */
			if( gem.getComments() != null ) l_json_gemid.put("comments", gem.getComments() );								
			else l_json_gemid.put("comments", "" );
			
			/**
			 * PriceCurrency
			 */
			if ( gem.getPriceCurrency() != null ) l_json_gemid.put("priceCurrency", gem.getPriceCurrency() );						
			else l_json_gemid.put("priceCurrency", "" );
			
			/**
			 * PriceValue
			 */
			if ( gem.getPriceValue() != null  )l_json_gemid.put("priceValue", gem.getPriceValue() );							
			else l_json_gemid.put("priceValue", "" );
			
			/**
			 * SupplierId
			 */
			if ( gem.getSupplierID() != null ) l_json_gemid.put("supplierId", gem.getSupplierID().toString() );				
			else l_json_gemid.put("supplierId", "" );
			
			/**
			 * CreationDate
			 */
			if ( gem.getCreationDate() != null ) l_json_gemid.put("creationDate", gem.getCreationDate().toString() );			
			else l_json_gemid.put("creationDate", "" );
									
			/**
			 * Species
			 */
			if ( gem.getSpecies() != null ) l_json_gemid.put("species", gem.getSpecies().getValue().toString() );			
			else l_json_gemid.put("species", "" );
			
			/**
			 * Shape
			 */
			if ( gem.getShape() != null ) l_json_gemid.put("shape", gem.getShape().getValue().toString() );				
			else l_json_gemid.put("shape", "" );
			
			/**
			 * Cut
			 */
			if (  gem.getCut() != null ) l_json_gemid.put("cut", gem.getCut().getValue().toString() );					
			else l_json_gemid.put("cut", "" );
			
			/**
			 * Clarity
			 */
			if ( gem.getClarity() != null ) l_json_gemid.put("clarity", gem.getClarity().getValue().toString() );			
			else l_json_gemid.put("clarity", "" );
			
			/**
			 * Light
			 */
			if ( gem.getLight() != null ) l_json_gemid.put("light", gem.getLight().getValue().toString() );				
			else l_json_gemid.put("light", "" );
			
			/**
			 * Enhancement
			 */
			if ( gem.getEnhancement() != null ) l_json_gemid.put("enhancement", gem.getEnhancement().getValue().toString() );	
			else l_json_gemid.put("enhancement", "" );
			
			/**
			 * Certificate
			 */
			if ( gem.getCertificate() != null ) l_json_gemid.put("certificate", gem.getCertificate().getValue().toString() );	
			else l_json_gemid.put("certificate", "" );
			
			/**
			 * Origin
			 */
			if ( gem.getOrigin() != null ) l_json_gemid.put("origin", gem.getOrigin().getValue().toString() );			
			else l_json_gemid.put("origin", "" );
			
			/**
			 * CurrentStatus
			 */
			if ( gem.getCurrentStatus() != null ) l_json_gemid.put("currentStatus", gem.getCurrentStatus().toString());			
			else l_json_gemid.put("currentStatus", "" );
			
			l_json_gemid.put("photoBinary", jpgToBase64( gem.getPhotoLink() ) );	
				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l_json_gemid;
	}
	
	public static boolean UnserializeJellyGemID( JSONObject gemSerialized ) throws JSONException
	{
		GemID l_extractedGemID = new GemID();
		
		if( gemSerialized.has("reference") )
			l_extractedGemID.setReference( gemSerialized.getString("reference") );
		
		if( gemSerialized.has("color") )
			l_extractedGemID.setColor( gemSerialized.getString("color") );
		
		if( gemSerialized.has("mass") )
			l_extractedGemID.setMass( Float.parseFloat( gemSerialized.getString("mass") ) );
		
		if( gemSerialized.has("sizeX") )
			l_extractedGemID.setSizeX( Float.parseFloat( gemSerialized.getString("sizeX") ) );
		
		if( gemSerialized.has("sizeY") )
			l_extractedGemID.setSizeY( Float.parseFloat( gemSerialized.getString("sizeY") ) );
		
		if( gemSerialized.has("sizeZ") )
			l_extractedGemID.setSizeZ( Float.parseFloat( gemSerialized.getString("sizeZ") ) );
		
		if( gemSerialized.has("comments") )
			l_extractedGemID.setComments( gemSerialized.getString("comments") );
		
		if( gemSerialized.has("priceCurrency") )
			l_extractedGemID.setPriceCurrency( Integer.parseInt( gemSerialized.getString("priceCurrency") ) );
		
		if( gemSerialized.has("priceValue") )
			l_extractedGemID.setPriceValue( Float.parseFloat( gemSerialized.getString("priceValue") ) );
		
		if( gemSerialized.has("supplierId") )
			l_extractedGemID.setSupplierID( Long.parseLong( gemSerialized.getString("supplierId") ) );
		
		if( gemSerialized.has("creationDate") )
			l_extractedGemID.setCreationDate( Long.parseLong( gemSerialized.getString("creationDate") ) );
		
		if( gemSerialized.has("species") )
			l_extractedGemID.setSpecies( GemSpecies.valueOf( gemSerialized.getString("species") ) ) ;
		
		if( gemSerialized.has("shape") )
			gemSerialized.getString("shape");
		
		if( gemSerialized.has("cut") )
			gemSerialized.getString("cut");
		
		if( gemSerialized.has("clarity") )
			gemSerialized.getString("clarity");
		
		if( gemSerialized.has("light") )
			gemSerialized.getString("light");
		
		if( gemSerialized.has("enhancement") )
			gemSerialized.getString("enhancement");
		
		if( gemSerialized.has("certificate") )
			gemSerialized.getString("certificate");
		
		if( gemSerialized.has("origin") )
			gemSerialized.getString("origin");
		
		if( gemSerialized.has("currentStatus") )
			l_extractedGemID.setCurrentStatus( GemStatusFactory.create( gemSerialized.getString("currentStatus") ) );
		
		if( gemSerialized.has("photoBinary") )
			gemSerialized.getString("photoBinary");
		
		return true;
	}
	
	private static String jpgToBase64( File source )
	{
		if( 	( source == null )	 ||
				( !source.exists() ) || 
				( !source.canRead()) )
			return "";
	
		ByteArrayOutputStream l_byte_image = new ByteArrayOutputStream(); 
		Bitmap l_image = BitmapFactory.decodeFile( source.getPath() );
		
		l_image.compress(Bitmap.CompressFormat.JPEG, 100, l_byte_image );
		
		return Base64.encodeToString( l_byte_image.toByteArray(), Base64.DEFAULT);
	}

	public static void setJellyGemIdDecodeFromJSONEvent(JellyGemIdDecodeFromJSONEvent evGem) {
		JellySerialize.evGem = evGem;
	}
	
	
}
