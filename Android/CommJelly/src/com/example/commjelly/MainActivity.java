package com.example.commjelly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jellydiamonds.android.comm.JellyGemIdDecodeFromJSONEvent;
import com.jellydiamonds.android.comm.JellySerialize;
import com.jellydiamonds.android.comm.JellySynchronize;
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
import com.jellydiamonds.android.metier.JellyUser;



public class MainActivity extends Activity {

	private static final String TAG = "[JellyCommTest]";
	public static final File DOCUMENT_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	public static final File PICTURE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	
	private TextView infobox = null;
	private Button	 decodeButton = null;
	private EditText fileGetter = null;
	
	private String ownerCollection = "JohnFox";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fileGetter = (EditText)findViewById(R.id.fileGetter);
		decodeButton = (Button) findViewById(R.id.decodeButton);
		infobox = (TextView) findViewById(R.id.viewjson);
		infobox.setMovementMethod(new ScrollingMovementMethod());
		
		decodeButton.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String l_filename = fileGetter.getText().toString();
				File l_tmp = new File( DOCUMENT_DIR + "/" + l_filename);
				infobox.setText("");
				if( l_tmp.exists() && l_tmp.canRead() )
				{
					String jsonString = fileToString(l_tmp);
					if(jsonString != null)
						JellySerialize.UnserializeJellyGemID( jsonString );
					else
						infobox.setText("jsonstring is empty...");
				}
				else
				{
					infobox.setText("File " + l_tmp + " cannot be opened...");
				}
			}
			
		});
		
		JellySerialize.setJellyGemIdDecodeFromJSONEvent(eventDecode);

		
		JellyUser test = new JellyUser();
		test.setUserID(10L);
		GemID gem1 = new GemID();
		gem1.setSupplierID(10L);
		gem1.setColor("#123456");
		gem1.setMass(2.05f);
		gem1.setSizeX(11.05f);
		gem1.setSizeY(7.01f);
		gem1.setSizeZ(4.37f);
		gem1.setComments("This gem is perfect it's my best one");
		gem1.setPriceCurrency(GemCurrency.USD);
		gem1.setPriceValue(300.5f);
		//gem1.setPhotoLink(l_photo);
		gem1.setSpecies(GemSpecies.EMERALD);
		gem1.setShape(GemShape.RECTANGLE);
		gem1.setCut(GemCut.DIAMOND);
		gem1.setClarity(GemClarity.EYES_CLEAN_TO_SLIGHTLY_INCLUDED);
		gem1.setLight(GemLight.FLUORESCENT_LIGHT);
		gem1.setEnhancement(GemEnhancement.HIGH_PRESSURE);
		gem1.setCertificate(GemCertificate.GIA);
		gem1.setOrigin(GemOrigin.MOZAMBIQUE);
		test.getCollection().getLocalCollection().add(gem1);
		
		JellySynchronize sync;

		sync = new JellySynchronize( 	this,
										getResources().getString(R.string.url_hostname), // hostname
										getResources().getString(R.string.url_webservice_adress), // serviceAddr
										getResources().getString(R.string.url_gems_list), // listGem
										getResources().getString(R.string.url_gems_get), // getGem
										getResources().getString(R.string.url_gems_post), // postGem
										test);
		
		//Log.d(TAG,sync.getGETGemsRefsURI().toString());
		sync.execute((Void) null);

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void stringToFile( String data, String path ) 
	{
		
		FileOutputStream l_file = null;
		ByteArrayInputStream l_data_byte = new ByteArrayInputStream ( data.getBytes() );
		byte [] l_buffer = new byte[ 1024 ];
		int l_read = 0;
		
		try {
			l_file = new FileOutputStream( path );
			
			while( ( l_read = l_data_byte.read( l_buffer ) ) > 0 )
			{
				l_file.write( l_buffer, 0 , l_read );
			}
			
			l_data_byte.close();
			l_file.flush();
			l_file.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String fileToString( File file )
	{
		FileInputStream l_file = null;
		ByteArrayOutputStream l_data_byte = new ByteArrayOutputStream();
		byte [] l_buffer = new byte[ 1024 ];
		int l_read = 0;
		
		try {
			l_file = new FileInputStream(file);
			
			while( ( l_read = l_file.read(l_buffer) ) > 0 )
			{
				l_data_byte.write(l_buffer, 0, l_read);
			}
			l_data_byte.flush();
			
			l_file.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( l_data_byte == null )
			return null;
		
		return l_data_byte.toString();
	}
	
	private JellyGemIdDecodeFromJSONEvent eventDecode = new JellyGemIdDecodeFromJSONEvent() {
		
		@Override
		public boolean onUserDecode(long owner) {
			// TODO Auto-generated method stub
			Log.d(TAG,"Owner of current collection being decoded is " + owner);
			Log.d(TAG,"Owner of previous collection encoded is " + ownerCollection);
			
			infobox.append( "Owner is : ' " + owner + " '.\n");
			return true;
		}
		
		@Override
		public boolean onDataDecode(GemID gem) {
			// TODO Auto-generated method stub
			infobox.append( gem.toString() + "\n");
			return true;
		}

		@Override
		public void onPictureDecode(InputStream picture, GemID gem) {
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


//gemCollectionResult = new ArrayList<GemID>();

/*File l_photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.jpg");
Log.d(TAG,"File : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/test.jpg" );

if( l_photo.exists() && l_photo.canRead())
	Log.d(TAG,l_photo.getName() + " Will be encoded !");
else
	Log.d(TAG,l_photo.getName() + " Will not be encoded...");*/

//gemCollection = new ArrayList<GemID>();

/*GemID gem1 = new GemID();

gem1.setReference("20140409EMER001");
gem1.setColor("#123456");
gem1.setMass(2.05f);
gem1.setSizeX(11.05f);
gem1.setSizeY(7.01f);
gem1.setSizeZ(4.37f);
gem1.setComments("This gem is perfect it's my best one");
gem1.setPriceCurrency(0);
gem1.setPriceValue(300.5f);
gem1.setSupplierID(6666L);
//gem1.setPhotoLink(l_photo);
gem1.setSpecies(GemSpecies.EMERALD);
gem1.setShape(GemShape.RECTANGLE);
gem1.setCut(GemCut.DIAMOND);
gem1.setClarity(GemClarity.EYES_CLEAN_TO_SLIGHTLY_INCLUDED);
gem1.setLight(GemLight.FLUORESCENT_LIGHT);
gem1.setEnhancement(GemEnhancement.HIGH_PRESSURE);
gem1.setCertificate(GemCertificate.GIA);
gem1.setOrigin(GemOrigin.MOZAMBIQUE);
gemCollection.add(gem1); 


gemCollection.add(new GemID());
gemCollection.add(new GemID());
gemCollection.add(new GemID());
gemCollection.add(new GemID());


JellySerialize.setJellyGemIdDecodeFromJSONEvent(eventDecode);
String jsonString = "";
try {
	jsonString = JellySerialize.SerializeJellyCollection(ownerCollection, gemCollection ).toString(2);
} catch (JSONException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
infobox.setText(jsonString);*/
//stringToFile( jsonString, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/JSONFormatGemID.txt" );
//Toast.makeText(getApplicationContext(), 
//		"JSON text file is saved at ' " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/JSONFormatGemID.txt '", 
//		Toast.LENGTH_LONG).show();
/*
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
}*/
