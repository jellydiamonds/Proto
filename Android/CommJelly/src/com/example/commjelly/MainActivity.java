package com.example.commjelly;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;

import android.app.Activity;
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
		
		ArrayList<GemID> gemCollection = new ArrayList<GemID>();
		GemID gem1 = new GemID();
		
		gem1.setReference("20140409EMER001");
		gem1.setColor("#123456");
		gem1.setMass(2.05);
		gem1.setSizeX(11.05f);
		gem1.setSizeY(7.01f);
		gem1.setSizeZ(4.37f);
		gem1.setComments("This gem is perfect it's my best one");
		gem1.setPriceCurrency(0);
		gem1.setPriceValue(300.5);
		gem1.setSupplierID(6666L);
		//gem1.setCreationDate(new Date());
		//gem1.setPhotoLink(null);
		gem1.setSpecies(GemSpecies.EMERALD);
		gem1.setShape(GemShape.Rectangle);
		gem1.setCut(GemCut.Diamond);
		gem1.setClarity(GemClarity.Eyes_Clean_to_Slightly_Included);
		gem1.setLight(GemLight.Fluorescent_light);
		gem1.setEnhancement(GemEnhancement.High_Pressure);
		gem1.setCertificate(GemCertificate.GIA);
		gem1.setOrigin(GemOrigin.Mozambique);
		gemCollection.add(gem1); 
		
		String jsonString = JellySerialize.SerializeJellyCollection( gemCollection ).toString();
		TextView view = (TextView) findViewById(R.id.viewjson);
		view.setText(jsonString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
