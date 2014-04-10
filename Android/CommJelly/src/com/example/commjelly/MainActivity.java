package com.example.commjelly;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.jellydiamonds.android.comm.JellySerialize;
import com.jellydiamonds.android.metier.GemCertificate;
import com.jellydiamonds.android.metier.GemClarity;
import com.jellydiamonds.android.metier.GemCut;
import com.jellydiamonds.android.metier.GemEnhancement;
import com.jellydiamonds.android.metier.GemID;
import com.jellydiamonds.android.metier.GemLight;
import com.jellydiamonds.android.metier.GemOrigin;
import com.jellydiamonds.android.metier.GemShape;
import com.jellydiamonds.android.metier.GemSpecies;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// File l_photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.jpg");
		// Log.d("[COMMJELLY]","File : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.jpg" );
		
		ArrayList<GemID> gemCollection = new ArrayList<GemID>();
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
		
		String jsonString = "";
		try {
			jsonString = JellySerialize.SerializeJellyCollection("dude", gemCollection ).toString(2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView view = (TextView) findViewById(R.id.viewjson);
		view.setMovementMethod(new ScrollingMovementMethod());
		view.setText(jsonString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
