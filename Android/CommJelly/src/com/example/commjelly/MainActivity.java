package com.example.commjelly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.jellydiamonds.android.comm.JellyGemIdDecodeFromJSONEvent;
import com.jellydiamonds.android.comm.JellySerialize;
import com.jellydiamonds.android.metier.GemID;



public class MainActivity extends Activity {

	private static final String TAG = "[JellyCommTest]";
	
	private TextView infobox = null;
	private ArrayList<GemID> gemCollection = null;
	private ArrayList<GemID> gemCollectionResult = null;
	private String ownerCollection = "JohnFox";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		infobox = (TextView) findViewById(R.id.viewjson);
		infobox.setMovementMethod(new ScrollingMovementMethod());
		
		File l_photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.jpg");
		Log.d(TAG,"File : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.jpg" );
		
		if( l_photo.exists() && l_photo.canRead())
			Log.d(TAG,l_photo.getName() + " Will be encoded !");
		else
			Log.d(TAG,l_photo.getName() + " Will not be encoded...");
		
		gemCollection = new ArrayList<GemID>();
		gemCollectionResult = new ArrayList<GemID>();
		/*GemID gem1 = new GemID();
		
		gem1.setReference("20140409EMER001");
		gem1.setColor("#123456");
		gem1.setMass(2.05f);
		gem1.setSizeX(11.05f);
		gem1.setSizeY(7.01f);
		gem1.setSizeZ(4.37f);
		gem1.setComments("This gem is perfect it's my best one");
		gem1.setPriceCurrency(0);
		gem1.setPriceValue(300.5);
		gem1.setSupplierID(6666L);
		gem1.setPhotoLink(l_photo);
		gem1.setSpecies(GemSpecies.EMERALD);
		gem1.setShape(GemShape.Rectangle);
		gem1.setCut(GemCut.Diamond);
		gem1.setClarity(GemClarity.Eyes_Clean_to_Slightly_Included);
		gem1.setLight(GemLight.Fluorescent_light);
		gem1.setEnhancement(GemEnhancement.High_Pressure);
		gem1.setCertificate(GemCertificate.GIA);
		gem1.setOrigin(GemOrigin.Mozambique);
		gemCollection.add(gem1); 
		*/
		
		gemCollection.add(new GemID());
		gemCollection.add(new GemID());
		gemCollection.add(new GemID());
		gemCollection.add(new GemID());
		
		gemCollection.get(0).setPhotoLink(l_photo);
		
		JellySerialize.setJellyGemIdDecodeFromJSONEvent(eventDecode);
		String jsonString = "";
		try {
			jsonString = JellySerialize.SerializeJellyCollection(ownerCollection, gemCollection ).toString(2);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//infobox.setText(jsonString);
		try {
			JellySerialize.UnserializeJellyCollection(new JSONObject(jsonString));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d(TAG, "---------------------SOURCE-----------------------");
		for( GemID l_tmp : gemCollection)
		{
			Log.d(TAG, l_tmp.toString());
		}
		
		Log.d(TAG, "---------------------DESTINATION-----------------------");
		for( GemID l_tmp : gemCollectionResult)
		{
			Log.d(TAG,l_tmp.toString());
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private JellyGemIdDecodeFromJSONEvent eventDecode = new JellyGemIdDecodeFromJSONEvent() {
		
		@Override
		public boolean onUserDecode(String owner) {
			// TODO Auto-generated method stub
			Log.d(TAG,"Owner of current collection being decoded is " + owner);
			Log.d(TAG,"Owner of previous collection encoded is " + ownerCollection);
			if( !owner.equals( ownerCollection ) )
				return false;
			return true;
		}
		
		@Override
		public boolean onDataDecode(GemID gem) {
			// TODO Auto-generated method stub
			gemCollectionResult.add(gem);
			return true;
		}

		@Override
		public void onPictureDecode(InputStream picture) {
			// TODO Auto-generated method stub
			FileOutputStream l_endfile;
			byte l_buffer[] = new byte[1024];
			int 	l_read = 0;
			
			if( picture != null)
			{
				
				try {
					l_endfile = new FileOutputStream( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/fichier_sortie.jpg");
					
					while( (l_read = picture.read(l_buffer)) > 0 )
					{
						l_endfile.write(l_buffer, 0, l_read );
					}
					l_endfile.flush();
					l_endfile.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

}
