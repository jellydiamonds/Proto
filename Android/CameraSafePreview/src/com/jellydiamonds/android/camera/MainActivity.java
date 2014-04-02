package com.jellydiamonds.android.camera;

import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends Activity {
	
		private static final String TAG = "[CAMERASAFEPREVIEW: MainActivity]";

		
		/**
		 * UI
		 */
		
	    private FrameLayout 		mFrameLayout = null;
	    private RelativeLayout		mRelativeLayout = null;
	    private Button				mButtonSnap = null;
	    
		/**
		 * 
		 */
	 	private Camera 				mCamera = null;
	    private Preview 			mSurfaceView = null;
	    private DisplayMetrics		mScreenInfo = null;
	    private int					mLandscapeWidth = 0;
	    private int					mLandscapeHeight = 0;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.preview);
	        //setContentView(R.layout.activity_main);
	        
	        mFrameLayout = (FrameLayout)findViewById(R.id.camera_preview);
	        mRelativeLayout = (RelativeLayout)findViewById(R.id.controls_layout);
	        mButtonSnap = (Button)findViewById(R.id.button_photo);
	        
	        mScreenInfo = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(mScreenInfo);
	        
	        // Define the landscape size of the screen
	        if( mScreenInfo.widthPixels < mScreenInfo.heightPixels )
	        {
	        	mLandscapeHeight = mScreenInfo.widthPixels;
	        	mLandscapeWidth = mScreenInfo.heightPixels;
	        }
	        else
	        {
	        	mLandscapeHeight = mScreenInfo.heightPixels;
	        	mLandscapeWidth = mScreenInfo.widthPixels;
	        }
	        
	        Log.d(TAG,"Info : Screen landscape size is : " + mLandscapeWidth + "x" + mLandscapeHeight);
	    }
	    
	    

	    @Override
	    protected void onDestroy() {
	        // TODO Auto-generated method stub
	        super.onDestroy();
	        releasePreview();
	    }
	    
	    @Override
	    protected void onStop() {
	    	super.onStop();
	    	releasePreview();
	    }
	    
	    @Override
	    protected void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	        releasePreview();
	    }

	    @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();
	        
	        if( safeCameraOpen() )
	        {
	        	Log.d(TAG,"Info : Camera successfully opened.");
	        
	        	mSurfaceView = new Preview(this, mCamera);
	        	mFrameLayout.addView(mSurfaceView); // Display Preview
		        mRelativeLayout.bringToFront();		// Display control on Top
	        }
	    }
	    
	/**
	 * CallBacks
	 * 
	 */
	    
	private boolean safeCameraOpen() {
		
	    Camera  			l_camera = null;   
	    Camera.Parameters 	l_param = null;
	    boolean 		  	l_found = false;		
	  
	    /**
	     * Check if camera device is available on system.
	     */
	    if( ! this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) )
	    	return false;
	    
	    try 
	    {
	    	releasePreview();
	        l_camera = Camera.open();
	        
	        /**
	         * Set the optimal preview size ( according to the screen size )
	         */
	        
	        l_param = l_camera.getParameters();
	        
	        for( Camera.Size l_size : l_param.getSupportedPreviewSizes())
	        {
	        	Log.d(TAG,"Info : Preiview size available : " + l_size.width + "x" + l_size.height);
	        	if(  l_size.width == mLandscapeWidth )
	        	{
	        		l_found = true;
	        		Log.d(TAG,"Info : Landscape size available in supported preview and picture size.");
	        		break;
	        	}
	        }
	        
	        if( l_found )
        		l_param.setPreviewSize(mLandscapeWidth, mLandscapeHeight);
	        else
	        	l_param.setPreviewSize(	l_param.getSupportedPreviewSizes().get(0).width, 
	        							l_param.getSupportedPreviewSizes().get(0).height );
	        
	        l_camera.setParameters(l_param);
	        
	        /**
	         * Set the normal orientation
	         */
	        l_camera.setDisplayOrientation(90);
	        mCamera = l_camera;
	        
	    } 
	    catch (Exception e) 
	    {
	        Log.e(TAG, "ERROR : failed to open Camera.");
	        e.printStackTrace();
	    }
	    
	    return (mCamera != null);   
	}

	private void releasePreview()
	{
		
		if( mSurfaceView != null )
		{
			mFrameLayout.removeView(mSurfaceView);
			mSurfaceView.getHolder().removeCallback(mSurfaceView);
		}
		
		
	    if (mCamera != null) {
	        mCamera.setPreviewCallback(null);
	        mCamera.release();
	        mCamera = null;
	    }
	    
	    
	}
	
}



//--------------------------------------------------------------------
//Camera.Parameters p = mCamera.getParameters();
//Size size = p.getPreviewSize();
//int width = size.width;
//int height = size.height;
//p.setPreviewFormat(ImageFormat.JPEG);
//mCamera.setParameters(p);


//mSurfaceView = new Preview(this);
//
//ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//mSurfaceView.setLayoutParams(layoutParams);
//
//mFrameLayout = (FrameLayout)findViewById(R.id.preview);
//
//mFrameLayout.addView(mSurfaceView);

//for(int t_preview : p.getSupportedPreviewFormats() )
//{
//	Log.d("[CAMERASAFEPREVIEW]", "SupportedPreviewFormat : " + t_preview);
//}

