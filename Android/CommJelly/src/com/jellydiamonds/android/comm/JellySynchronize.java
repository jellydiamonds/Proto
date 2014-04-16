package com.jellydiamonds.android.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.jellydiamonds.android.metier.GemID;
import com.jellydiamonds.android.metier.JellyUser;

import android.os.AsyncTask;
import android.util.Log;

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
	
	public JellySynchronize( 
							 String hostname, 
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
		
		JellySerialize.setJellyGemIdDecodeFromJSONEvent(this);
		
	}
	
	
	public void TESTDEBUG()
	{

			sendGem(mJellyUser.getCollection().getLocalCollection().get(0));

		/*boolean is_ok = false;
		
		sendGem(mJellyUser.getCollection().getLocalCollection().get(0));
		String[] result = retrieveDistantGemReferences();
		
		if( result == null )
		{
			Log.d(TAG,"no response from server ...");
			return;
		}
		
		for(String l_tmp : result )
		{
			Log.d(TAG,"Reference gem : |" + l_tmp +"| Retrieving...");
			is_ok = retrieveDistantGem( l_tmp );
			if(is_ok)
				Log.d(TAG,l_tmp + " : OK !");
			else
				Log.d(TAG,l_tmp + " : Error...");
		}*/
	}


	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		Log.d(TAG,"TESTDEBUG begin");
		TESTDEBUG();
		Log.d(TAG,"TESTDEBUG end");
		return null;
	}
	
	private boolean sendLocalGems()
	{
		return false;
		
	}
	
	private boolean retrieveDistantGem( String gemReference )
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
			
			return JellySerialize.UnserializeJellyGemID( new JSONObject(l_serverResponse) );
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	private String[] retrieveDistantGemReferences()
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
	
	private boolean sendGem( GemID gemid )
	{
		HttpResponse l_response = null;
		JSONObject l_serializedGem = null;
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
	 			//return false;
			}
			
			// Retrieve body content and convert as string
			l_serverResponse = convertStreamToString(l_response.getEntity().getContent()); 
			if( l_serverResponse.toUpperCase().contains( ERROR_SUBSTRING) )
			{
				Log.d(TAG,"ServerResponse : " + l_serverResponse);
				return false;
			}
			else
			{
				Log.d(TAG,"Reference OK : " + l_serverResponse);
				//gemid.setReference(l_serverResponse);
			}
			
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
	
	public URI getPOSTGemsURI( )
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
	
	public  URI getGETGemsFromReferenceURI( String reference )
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
	
	public URI getGETGemsRefsURI()
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


	@Override
	public boolean onUserDecode(String owner) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean onDataDecode(GemID gem) {
		// TODO Auto-generated method stub
		
		mJellyUser.getCollection().getSyncedCollection().put( gem.getReference(), gem);
		Log.d(TAG,"New Gem extracted : " + gem);
		return true;
	}


	@Override
	public void onPictureDecode(InputStream picture) {
		// TODO Auto-generated method stub
		
	}

}
