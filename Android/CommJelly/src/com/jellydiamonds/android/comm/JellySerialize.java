package com.jellydiamonds.android.comm;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jellydiamonds.android.metier.GemID;


public class JellySerialize {

	public static JSONArray SerializeJellyCollection( Collection<GemID> collection )
	{
		JSONArray l_serializedCollection = new JSONArray();
		JSONObject l_serialized_gemid = null;
		
		for( GemID l_tmp_gemid : collection )
		{
			l_serialized_gemid = new JSONObject();
			
			try {
				l_serialized_gemid.put("reference", l_tmp_gemid.getReference() );					// 1
				l_serialized_gemid.put("color", l_tmp_gemid.getColor() );							// 2
				l_serialized_gemid.put("mass", l_tmp_gemid.getMass().toString() );					// 3
				l_serialized_gemid.put("sizeX", l_tmp_gemid.getSizeX().toString() );				// 4
				l_serialized_gemid.put("sizeY", l_tmp_gemid.getSizeY().toString() );				// 5
				l_serialized_gemid.put("sizeZ", l_tmp_gemid.getSizeZ().toString() );				// 6
				l_serialized_gemid.put("comments", l_tmp_gemid.getComments() );						// 7
				l_serialized_gemid.put("priceCurrency", l_tmp_gemid.getPriceCurrency() );			// 8
				l_serialized_gemid.put("priceValue", l_tmp_gemid.getPriceValue() );					// 9
				l_serialized_gemid.put("supplierID", l_tmp_gemid.getSupplierID().toString() );		// 10
				l_serialized_gemid.put("creationDate", l_tmp_gemid.getCreationDate().toString() );	// 11
				//l_serialized_gemid.put("photoLink", l_tmp_gemid.getPhotoLink().toString() );		// 12
				l_serialized_gemid.put("species", l_tmp_gemid.getSpecies().getValue().toString() );	// 13
				l_serialized_gemid.put("shape", l_tmp_gemid.getShape().getValue().toString() );		// 14
				l_serialized_gemid.put("cut", l_tmp_gemid.getCut().getValue().toString() );			// 15
				l_serialized_gemid.put("clarity", l_tmp_gemid.getClarity().getValue().toString() );	// 16
				l_serialized_gemid.put("light", l_tmp_gemid.getLight().getValue().toString() );		// 17
				l_serialized_gemid.put("enhancement", l_tmp_gemid.getEnhancement().getValue().toString() );	// 18
				l_serialized_gemid.put("certificate", l_tmp_gemid.getCertificate().getValue().toString() );	// 19
				l_serialized_gemid.put("origin", l_tmp_gemid.getOrigin().getValue().toString() );	// 20
				l_serialized_gemid.put("currentStatus", l_tmp_gemid.getCurrentStatus().toString());	// 21
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			l_serializedCollection.put(l_serialized_gemid);
		}
		
		
		return l_serializedCollection;
	}
}
