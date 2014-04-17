package com.jellydiamonds.android.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.jellydiamonds.android.app.JellyConst;
import com.jellydiamonds.android.app.Main;
import com.jellydiamonds.android.metier.GemID;
import com.jellydiamonds.android.metier.JellyUser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class JellySynchronize extends AsyncTask<Void, Integer, Boolean> implements JellyGemIdDecodeFromJSONEvent {

	public static final String TAG = "[SYNCHRONIZE]";
	public static final String ERROR_SUBSTRING = "ERROR";
	
	public static final String PROTOCOL = "http://";
	public static final char   SEPARATOR = '/';
	public static final String   LIST_REFERENCE_SEPARATOR = ",";
	
	private HttpClient mClientHttp = null;
	private HttpGet	   mMethodGet   = null;
	private HttpPost   mMethodPost  = null;
	private JellyUser  mJellyUser   = null;
	
	private  String murl_hostname = null;
	private  String murl_webservice_adress = null;
	private  String murl_gems_list = null;
	private  String murl_gems_get = null;
	private  String murl_gems_post = null;
	
	private Main mContext = null;
	
	private int gemsDownloaded = 0;
	private int gemsUploaded = 0;
	
	public JellySynchronize( String hostname, 
							 String serviceAddr, 
							 String listGems, 
							 String getGems,
							 String postGems,
							 JellyUser user ) 
	{
		mClientHttp = new DefaultHttpClient();
		mMethodGet = new HttpGet();
		mMethodPost = new HttpPost();
		mJellyUser = user;
		
		murl_hostname = hostname;
		murl_webservice_adress = serviceAddr;
		murl_gems_list = listGems;
		murl_gems_get = getGems;
		murl_gems_post = postGems;
		
		mContext = null;
		JellySerialize.setJellyGemIdDecodeFromJSONEvent(this);
		
	}
	
	public void setActivity( Main activity )
	{
		this.mContext = activity;
	}
	
	@Override
	public boolean onUserDecode(long owner) {
		// TODO Auto-generated method stub
		
		if( owner != mJellyUser.getUserID()) // Check if GemID as the same user id as current user
			return false;
		
		return true;
	}


	@Override
	public boolean onDataDecode(GemID gem) {
		// TODO Auto-generated method stub

		mJellyUser.getCollection().getSyncedCollection().put( gem.getReference(), gem); 
		gemsDownloaded++;
		Log.d(TAG,"New Gem extracted : " + gem);
		return true;
	}


	@Override
	public void onPictureDecode(InputStream picture, GemID gem) {
		// TODO Auto-generated method stub
		
		FileOutputStream l_endfile;
		byte l_buffer[] = new byte[1024];
		int  l_read = 0;
		File l_pictureFile = JellyConst.getPhotoPath( gem.getReference() );
				
		if( picture != null)
		{
			
			try {
				l_endfile = new FileOutputStream( l_pictureFile );
				while( (l_read = picture.read(l_buffer)) > 0 )
				{
					l_endfile.write(l_buffer, 0, l_read );
				}
				l_endfile.flush();
				l_endfile.close();
				gem.setPhotoLink(l_pictureFile);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		if( ! is_NetReachable())
			return false;
		
		Map<String,GemID> l_syncedCollection = mJellyUser.getCollection().getSyncedCollection(); // Get current synced collection
		String [] l_gemsNotYetSynced = getOnlineGemReferences(); // Get gem reference online
		
		if( l_gemsNotYetSynced == null )
			return false;
		
		for( String l_tmp_ref : l_gemsNotYetSynced)
		{
			if( !l_syncedCollection.containsKey( l_tmp_ref )) // Add Gem only if key doesn't exists
			{
				getOnlineGem( l_tmp_ref ); //returned boolean is not used
			}
			
		}
		
		sendLocalGems(); // returned boolean is not used
		return true;
	}
	
    @Override
    protected void onPreExecute () {
		if( mContext != null)
			this.mContext.setSyncContext(true);
		
		this.gemsDownloaded = 0;
		this.gemsUploaded = 0;
    }
	
    @Override
    protected void onProgressUpdate (Integer... prog) {

    }
	
	@Override
	protected void onPostExecute (Boolean result) 
	{
		Log.d(TAG,"Nbre gems synced : " + mJellyUser.getCollection().getSyncedCollection().size());
		if( mContext != null)
		{
			this.mContext.setSyncContext(false);
			Toast.makeText( 
					this.mContext, 
					gemsDownloaded + " gems downloaded /" + gemsUploaded +" gems uploaded.", 
					Toast.LENGTH_LONG).show();
		}
	}
	
	private boolean sendLocalGems()
	{
		List<GemID> l_localCollection = mJellyUser.getCollection().getLocalCollection();
		GemID l_tmpGem = null;
		
		for ( int l_index = l_localCollection.size()-1 ; l_index >= 0; l_index--)
		{
			l_tmpGem = l_localCollection.get(l_index);
			if ( putLocalGemOnline( l_tmpGem ) ) // If Gem have been successfully uploaded
			{
				l_localCollection.remove( l_index ); // Remove it from localCollection
				mJellyUser.getCollection().getSyncedCollection().put(l_tmpGem.getReference(), l_tmpGem);
				gemsUploaded++;
			}
		}
		return true;
		
	}
	
	private boolean getOnlineGem( String gemReference )
	{
		HttpResponse l_response = null;
		URI l_url = null;
		String l_serverResponse = null;
		
		l_url = getGETGemsFromReferenceURI( gemReference );
		if( l_url == null )
			return false;
		
		Log.d(TAG,"URL get gem : " + l_url.toString() );
		mMethodGet.setURI( l_url );
		
		try {
			l_response = mClientHttp.execute(mMethodGet);
			
			if( l_response.getStatusLine().getStatusCode() != 200 )
				return false;
			
			l_serverResponse = convertStreamToString( l_response.getEntity().getContent() );
			if( l_serverResponse.isEmpty() )
				return false;
			
			if( l_serverResponse.toUpperCase().contains(ERROR_SUBSTRING) )
			{
				Log.d(TAG,l_serverResponse);
				return false;
			}
			
			l_response.getEntity().consumeContent();
			
			return JellySerialize.UnserializeJellyGemID( l_serverResponse) ;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	private String[] getOnlineGemReferences()
	{
		HttpResponse l_response = null;
		URI l_url = null;
		String l_serverResponse = null;
		
		l_url = getGETGemsRefsURI();
		if(l_url == null)
			return null;
		Log.d(TAG,"URL get gem references : " + l_url.toString());
		mMethodGet.setURI( l_url);
		
		try {
			l_response = mClientHttp.execute(mMethodGet);
			
			if( l_response.getStatusLine().getStatusCode() != 200 )
				return null;
			
			l_serverResponse = convertStreamToString( l_response.getEntity().getContent() ).replace("\n", "");
			if( l_serverResponse.isEmpty() )
				return null;
			
			if( l_serverResponse.toUpperCase().contains(ERROR_SUBSTRING) )
			{
				Log.d(TAG,l_serverResponse);
				return null;
			}
			// Remove all '\n' characters 
			l_response.getEntity().consumeContent();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return l_serverResponse.split(LIST_REFERENCE_SEPARATOR);
	}
	
	private boolean putLocalGemOnline( GemID gemid )
	{
		HttpResponse l_response = null;
		String l_serializedGem = null;
		String l_serverResponse = null;
		URI l_url = null;
		
		l_serializedGem = JellySerialize.SerializeJellyGemID( gemid );
		if( l_serializedGem == null )
			return false;
		Log.d(TAG,"JSON : " + l_serializedGem);
		
		l_url = getPOSTGemsURI();
		if(l_url == null)
			return false;
		Log.d(TAG,"URL get gem references : " + l_url.toString());
		mMethodPost.setURI( l_url);
		
		try 
		{
			mMethodPost.setHeader("Accept", "application/json");
			mMethodPost.setHeader("Content-type", "application/json");
			
			mMethodPost.setEntity( new StringEntity( l_serializedGem.toString() ) );
			
			l_response = mClientHttp.execute( mMethodPost);

			if( l_response.getStatusLine().getStatusCode() != 200 )
			{
				Log.d(TAG,"STATUS CODE : " +l_response.getStatusLine().getStatusCode());
	 			return false;
			}
			
			// Retrieve body content and convert as string
			l_serverResponse = convertStreamToString(l_response.getEntity().getContent()); 
			if( ( l_serverResponse.isEmpty() ) ||
				( l_serverResponse.toUpperCase().contains( ERROR_SUBSTRING) ) )
			{
				Log.d(TAG,"ServerResponse : " + l_serverResponse);
				return false;
			}
			else
			{
				Log.d(TAG,"Reference OK : " + l_serverResponse);
				gemid.setReference(l_serverResponse); // Set the reference
			}
			
			l_response.getEntity().consumeContent();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static String convertStreamToString(InputStream is) {
		/*
		* To convert the InputStream to String we use the BufferedReader.readLine()
		* method. We iterate until the BufferedReader return null which means
		* there's no more data to read. Each line will appended to a StringBuilder
		* and returned as String.
		*
		* (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
		*/
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally 
		{	
			try 
			{
				is.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	private URI getPOSTGemsURI( )
	{
		URI l_tmpURI = null;
		
		StringBuilder l_url = new StringBuilder();
		
		l_url.append(PROTOCOL);
		l_url.append(this.murl_hostname);
		l_url.append(SEPARATOR);
		l_url.append(this.murl_webservice_adress);
		l_url.append(SEPARATOR);
		l_url.append(this.mJellyUser.getUserID());
		l_url.append(SEPARATOR);
		l_url.append(this.murl_gems_post);
		
		try {
			l_tmpURI = new URI( l_url.toString() );
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l_tmpURI;
	}
	
	private  URI getGETGemsFromReferenceURI( String reference )
	{
		URI l_tmpURI = null;
		StringBuilder l_url = null;
		
		if( 	( reference == null ) || 
				( reference.isEmpty() ))
			return null;
				
		l_url = new StringBuilder();
		
		l_url.append(PROTOCOL);
		l_url.append(this.murl_hostname);
		l_url.append(SEPARATOR);
		l_url.append(this.murl_webservice_adress);
		l_url.append(SEPARATOR);
		l_url.append(this.mJellyUser.getUserID());
		l_url.append(SEPARATOR);
		l_url.append(this.murl_gems_get);
		l_url.append(SEPARATOR);
		l_url.append( reference );
		
		try {
			l_tmpURI = new URI( l_url.toString() );
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l_tmpURI;
	}
	
	private URI getGETGemsRefsURI()
	{
		URI l_tmpURI = null;
		StringBuilder l_url = new StringBuilder();
		
		l_url.append(PROTOCOL);
		l_url.append(this.murl_hostname);
		l_url.append(SEPARATOR);
		l_url.append(this.murl_webservice_adress);
		l_url.append(SEPARATOR);
		l_url.append(this.mJellyUser.getUserID());
		l_url.append(SEPARATOR);
		l_url.append(this.murl_gems_list);
		
		try {
			l_tmpURI = new URI( l_url.toString() );
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l_tmpURI;
	}
	
	public boolean is_NetReachable()
	{
		ConnectivityManager l_netManager = null;
		NetworkInfo l_netInfo = null;
		
		if( mContext == null )
			return false;
		
		l_netManager = (ConnectivityManager) 
				mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
		l_netInfo = l_netManager.getActiveNetworkInfo();
		
		if( 	( l_netInfo != null ) && 
				( l_netInfo.isConnected() ) )
			return true;
		
		return false;
	}

}
