package com.jellydiamonds.android.app;


import com.jellydiamonds.android.camera.MainActivity;
import com.jellydiamonds.android.comm.JellySynchronize;
import com.jellydiamonds.android.metier.JellyUser;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class Main extends Activity {
	
	public static final String TAG = "[JellyDiamonds-DEBUG]";
	
	
	private DrawerLayout mDrawerLayout = null;
	private LinearLayout mDrawerLinearLayout = null;
	private ListView mDrawerLocalOptionList = null;
	private ListView mDrawerDistantOptionList = null;
	private ActionBarDrawerToggle mDrawerToggle = null;
	
	private CharSequence mAppTitle = null;
	
	private String[]	mDrawerLoalOptionsTitle = null;
	private String[]	mDrawerDistantOptionsTitle = null;
	
	boolean mOnSync = false;
	
	/**
	 * Code Metier
	 */
	
	private JellyUser 			mJellyUser = null;
	
	/**
	 * Camera activity
	 */

	private Intent mCameraActivity = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		mAppTitle = getTitle();
		JellyConst.initialize(this);
        
		mDrawerLayout = (DrawerLayout) findViewById(R.id.root_drawer_layout);
        mDrawerLinearLayout = (LinearLayout)findViewById(R.id.drawer_Linearlayout);
        mDrawerLocalOptionList = (ListView) findViewById(R.id.local_options_list);
        mDrawerDistantOptionList = (ListView) findViewById(R.id.distant_options_list);

        mDrawerLoalOptionsTitle = getResources().getStringArray(R.array.local_actions);
        mDrawerDistantOptionsTitle = getResources().getStringArray(R.array.distant_actions);
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // set up the left drawer's list view with items and click listener
        mDrawerLocalOptionList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerLoalOptionsTitle ));
        mDrawerLocalOptionList.setOnItemClickListener(new DrawerLocalItemClickListener());
        
        mDrawerDistantOptionList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerDistantOptionsTitle ));
        mDrawerDistantOptionList.setOnItemClickListener(new DrawerDistantItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mAppTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            	//getActionBar().setTitle(mAppTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        mJellyUser = getUser();

        
        /**
         * SetDisplayName in title
         */
        setApplicationTitle( mJellyUser.getDisplayName() );
        
        if (savedInstanceState == null) {
        	selectLocalAction( 0 );
        }
        
        mCameraActivity = new Intent(this, MainActivity.class);

	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		if( JellyUser.saveJellyUserContext(JellyConst.getFileSaveName(), JellyConst.getSaveDir(), mJellyUser ) == true )
			Log.d(TAG, "Success JellyUser saved!");
		else
			Log.d(TAG,"Error while saving JellyUser...");
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_sync).setVisible(true);
        menu.findItem(R.id.action_new).setVisible(true);
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
        
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
        //menu.findItem(R.id.).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_sync:
        	
        	if( !mOnSync )
        	{
        		JellySynchronize l_jellysync = new JellySynchronize(
					getResources().getString(R.string.url_hostname), // hostname
					getResources().getString(R.string.url_webservice_adress), // serviceAddr
					getResources().getString(R.string.url_gems_list), // listGem
					getResources().getString(R.string.url_gems_get), // getGem
					getResources().getString(R.string.url_gems_post), // postGem
    				mJellyUser);
        		l_jellysync.setActivity(this);
        		l_jellysync.execute((Void)null);
        	}
        	return true;
        case R.id.action_new:
        	setAddGemContext(true);
        	return true;
        /*case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;*/
        case R.id.action_remove_jellyuser:
        	deleteUser();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    private void setApplicationTitle( String ext )
    {
    	getActionBar().setTitle( this.mAppTitle + " : " + ext);
    }
    
    /* Click listener for local actions */
    private class DrawerLocalItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	selectLocalAction( position );
        }
    }
    
    /* Click listener for distant actions */
    private class DrawerDistantItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	selectDistantAction( position );
        }
    }
    

    
    private void selectLocalAction( int position )
    {	
    	android.app.FragmentManager l_fragment_manager = getFragmentManager();
    	ItemFragment l_listFragment = ItemFragment.newInstance();
    	JellyCollectionAdapter l_listAdapter = null;
    	Log.d(TAG,"position : " + position);
    	switch( position )
    	{
    	case 0:
    		// All Gems
    		l_listAdapter = new JellyCollectionAdapter(this,this.mJellyUser.getCollection().getAllGems());
    		break;
    	case 1:
    		// Local Gems
    		l_listAdapter = new JellyCollectionAdapter(this,this.mJellyUser.getCollection().getLocalCollection());
    		break;
    	case 2:
    		// Sunced Gems
    		l_listAdapter = new JellyCollectionAdapter(this, this.mJellyUser.getCollection().getSyncedCollection().values() );
    		break;
    	case 3:
    		// Forsalegems
    		l_listAdapter = new JellyCollectionAdapter(this, this.mJellyUser.getCollection().getForSaleCollection() );
    		break;
    	case 4:
    		// Deleted Gems
    		l_listAdapter = new JellyCollectionAdapter(this, this.mJellyUser.getCollection().getDeletedCollection() ); 
    		break;
    	default:
    		l_listAdapter = new JellyCollectionAdapter(this,this.mJellyUser.getCollection().getAllGems());
    	}
    		
    	l_listFragment.setAdapter(l_listAdapter);
    	l_fragment_manager.beginTransaction().replace(R.id.content_frame, l_listFragment).commit();
    	
    	resetListChoice(mDrawerDistantOptionList);
    }
    
    private void selectDistantAction( int position )
    {
    	
    	resetListChoice(mDrawerLocalOptionList);  	
    }
    
    
    private void resetListChoice( ListView list )
    {
    	// Reset list
    	list.clearChoices();
    	list.invalidateViews();
    	// Close Drawer
    	mDrawerLayout.closeDrawer( mDrawerLinearLayout );
    }
   

	/*@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub
		
	}*/
    
    public void startCameraActivity()
    {
    	startActivityForResult(mCameraActivity,1);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	  if (requestCode == 1) {

    	     if(resultCode == RESULT_OK){      
    	         String result=data.getStringExtra("photoLink");   
    	         Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    	     }
    	     if (resultCode == RESULT_CANCELED) {    
    	         //Write your code if there's no result
    	    	 Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show();
    	     }
    	  }
    	}
	
	public void setSyncContext( boolean enabled )
	{
		android.app.FragmentManager l_fragment_manager = null;
		PendingFragment l_pendingFragment = null;
				
		if( enabled)
		{
			l_fragment_manager = getFragmentManager();
			l_pendingFragment = PendingFragment.newInstance();
			l_fragment_manager.beginTransaction().replace(R.id.content_frame, l_pendingFragment).commit();
			mOnSync = true;
		}
		else
		{
			// Return to default view
			selectLocalAction( 0 );
			mOnSync = false;
		}
	}
	
	public void setAddGemContext( boolean enabled )
	{
		android.app.FragmentManager l_fragment_manager = null;
		GemCreateFragment l_createFragment = null;
		
		if( enabled )
		{
			l_fragment_manager = getFragmentManager();
			l_createFragment = GemCreateFragment.newInstance();
			l_fragment_manager.beginTransaction().replace(R.id.content_frame, l_createFragment).commit();
		}
		else
		{
			selectLocalAction( 0 );
		}
	}
	
	public void deleteUser()
	{
		
		if ( this.deleteFile(JellyConst.getFileSavePath()))
			Toast.makeText(this, "JellyUser save have been deleted...", Toast.LENGTH_LONG).show();
	}
	
	public JellyUser getUser()
	{
		JellyUser l_tmp = null;

		
		if( ( l_tmp = JellyUser.recoverJellyUserContext( JellyConst.getFileSaveName(), JellyConst.getSaveDir() ) ) == null )
		{
			Log.d(TAG,"No previous session retrieved : new JellyUser created.");
			l_tmp =  new JellyUser();
			l_tmp.setAddress("6 rue Emile Verhaeren");
			l_tmp.setCity("Wormhout");
			l_tmp.setCountry("France");
			l_tmp.setDisplayName("JohnFox");
			l_tmp.setEmail("moise.roussel@gmail.com");
			l_tmp.setFirstName("Mo�se");
			l_tmp.setLastName("ROUSSEL");
			l_tmp.setPhone("0677355678");
			l_tmp.setUserID(3L);
		}
		else
		{
			Log.d(TAG,"Previous session retrieved : JellyUser recovered from file." + l_tmp.getFirstName());
		}
		
		return l_tmp;
	}
}

