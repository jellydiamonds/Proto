package com.jellydiamonds.android.comm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Base64InputStream;

import com.jellydiamonds.android.metier.GemCertificate;
import com.jellydiamonds.android.metier.GemClarity;
import com.jellydiamonds.android.metier.GemCurrency;
import com.jellydiamonds.android.metier.GemCut;
import com.jellydiamonds.android.metier.GemEnhancement;
import com.jellydiamonds.android.metier.GemID;
import com.jellydiamonds.android.metier.GemLight;
import com.jellydiamonds.android.metier.GemOrigin;
import com.jellydiamonds.android.metier.GemShape;
import com.jellydiamonds.android.metier.GemSpecies;
import com.jellydiamonds.android.metier.GemStatusFactory;


public class JellySerialize {
	
	private static JellyGemIdDecodeFromJSONEvent evGem = null;
	
	public static String SerializeJellyGemID( GemID gem )
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
			if ( gem.getPriceCurrency() != null ) l_json_gemid.put("priceCurrency", gem.getPriceCurrency().getValue().toString() );						
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
			if ( gem.getCurrentStatus() != null ) l_json_gemid.put("currentStatus", gem.getCurrentStatus().toValue().toString());			
			else l_json_gemid.put("currentStatus", "" );
			
			l_json_gemid.put("photoBinary", jpgToBase64( gem.getPhotoLink() ) );	
				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l_json_gemid.toString();
	}
	
	public static boolean UnserializeJellyGemID( String gemSerialized )
	{
		GemID l_extractedGemID = null; 
		JSONObject l_jsonObject = null; 
		
		if( gemSerialized == null )
			return false;
		
		l_extractedGemID = new GemID();
		try {
			l_jsonObject = new JSONObject( gemSerialized );
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		if( l_jsonObject.has("supplierId") )
		{
			try {
				long supplier = l_jsonObject.getLong("supplierId");
				/**
				 * Let user check himself
				 */
				if( JellySerialize.evGem.onUserDecode( supplier) == false )
					return false;
				
				l_extractedGemID.setSupplierID( supplier );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("reference") )
		{
			try {
				l_extractedGemID.setReference( l_jsonObject.getString("reference") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		if( l_jsonObject.has("color") )
		{
			try {
				l_extractedGemID.setColor( l_jsonObject.getString("color") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("mass") )
		{
			try {
				l_extractedGemID.setMass( (float) l_jsonObject.getDouble("mass") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("sizeX") )
		{
			try {
				l_extractedGemID.setSizeX( (float) l_jsonObject.getDouble("sizeX") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		if( l_jsonObject.has("sizeY") )
		{
			try {
				l_extractedGemID.setSizeY( (float) l_jsonObject.getDouble("sizeY") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("sizeZ") )
		{
			try {
				l_extractedGemID.setSizeZ( (float) l_jsonObject.getDouble("sizeZ") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("comments") )
		{
			try {
				l_extractedGemID.setComments( l_jsonObject.getString("comments") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("priceCurrency") )
		{
			try {
				l_extractedGemID.setPriceCurrency( GemCurrency.fromValue( l_jsonObject.getInt("priceCurrency") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("priceValue") )
		{
			try {
				l_extractedGemID.setPriceValue( (float) l_jsonObject.getDouble("priceValue") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("creationDate") )
		{
			try {
				l_extractedGemID.setCreationDate( l_jsonObject.getLong("creationDate") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("species") )
		{
			try {
				l_extractedGemID.setSpecies( GemSpecies.fromValue( l_jsonObject.getInt("species") ) ) ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("shape") )
		{
			try {
				l_extractedGemID.setShape( GemShape.fromValue( l_jsonObject.getInt("shape") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("cut") )
		{
			try {
				l_extractedGemID.setCut( GemCut.fromValue( l_jsonObject.getInt("cut") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("clarity") )
		{
			try {
				l_extractedGemID.setClarity( GemClarity.fromValue( l_jsonObject.getInt("clarity") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("light") )
		{
			try {
				l_extractedGemID.setLight( GemLight.fromValue( l_jsonObject.getInt("light") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("enhancement") )
		{
			try {
				l_extractedGemID.setEnhancement( GemEnhancement.fromValue( l_jsonObject.getInt("enhancement") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("certificate") )
		{
			try {
				l_extractedGemID.setCertificate( GemCertificate.fromValue( l_jsonObject.getInt("certificate") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("origin") )
		{
			try {
				l_extractedGemID.setOrigin( GemOrigin.fromValue( l_jsonObject.getInt("origin") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( l_jsonObject.has("currentStatus") )
		{
			try {
				l_extractedGemID.setCurrentStatus( GemStatusFactory.create( l_jsonObject.getInt("currentStatus") ) );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Photolink is already null
		
		/**
		 * Now call delegate : let user do whatever with GemID
		 * If false is returned, processing is stopped
		 */
		
		if( JellySerialize.evGem.onDataDecode( l_extractedGemID ) == false )
			return false;
		
		/**
		 * We can now decode Image from base64
		 */
		if( l_jsonObject.has("photoBinary") )
		{
			try {
				JellySerialize.evGem.onPictureDecode(
						streamFromBase64( 
								l_jsonObject.getString("photoBinary") ), l_extractedGemID );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	private static String jpgToBase64( File source )
	{
		if( 	( source == null )	 ||
				( !source.exists() ) || 
				( !source.canRead()) )
			return "";
	
		ByteArrayOutputStream l_byte_image = new ByteArrayOutputStream();
		/**
		 * Reducing image size
		 */
		BitmapFactory.Options l_opt = new BitmapFactory.Options();
		l_opt.inSampleSize = 2;
		
		Bitmap l_image = BitmapFactory.decodeFile( source.getPath(), l_opt );
		
		l_image.compress(Bitmap.CompressFormat.JPEG, 100, l_byte_image );
		
		return Base64.encodeToString( l_byte_image.toByteArray(), Base64.DEFAULT);
	}

	private static InputStream streamFromBase64( String pictureBase64 )
	{
		
		if( pictureBase64.isEmpty() )
			return null;
		
		return new Base64InputStream( new ByteArrayInputStream( pictureBase64.getBytes() ), Base64.DEFAULT ); 
	}
	
	public static void setJellyGemIdDecodeFromJSONEvent(JellyGemIdDecodeFromJSONEvent evGem) {
		JellySerialize.evGem = evGem;
	}
		
}

/*public static JSONObject SerializeJellyCollection( String ownerID, Collection<GemID> collection )
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
}*/

/*public static boolean UnserializeJellyCollection( JSONObject collectionSerialized )
{
	if( evGem == null )
		return false;
	
	String l_collectionOwner = null;
	JSONArray l_collectionData = null;
	//JSONObject l_currentGem = null;
	
	if ( 	( !collectionSerialized.has("jellyUser") ) || 
			( !collectionSerialized.has("jellyCollection") ) )
		return false;
	
	try {
		l_collectionOwner = collectionSerialized.getString("jellyUser");
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
			if( UnserializeJellyGemID(l_collectionData.getJSONObject(l_index)) == false )
				return false;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	return true;
}*/
