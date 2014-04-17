package com.jellydiamonds.android.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jellydiamonds.android.app.R;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
	
		private static final String TAG = "[CAMERASAFEPREVIEW: MainActivity]";
		
		/**
		 * UI
		 */
		
	    private FrameLayout 		mFrameLayout = null;
	    //private RelativeLayout		mRelativeLayoutSnap = null;
	    private FrameLayout  		mFrameLayoutSelection = null;
	    private Button				mButtonSnap = null;
	    private LinearLayout		mConsignZone = null;
	    
		/**
		 * Camera Members
		 */
	 	private Camera 				mCamera = null;
	    private Preview 			mSurfaceView = null;
	    private DisplayMetrics		mScreenInfo = null;
	    private int					mLandscapeWidth = 0;
	    private int					mLandscapeHeight = 0;
	    
	    /**
	     * Audio functionalities
	     */	    
	    private AudioManager		mAudioManager = null;
	    private MediaPlayer			mCameraShutterSound = null;
	    
	    /**
	     * Activity entry point function 
	     */
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.preview);
	        
	        // Orientation should be still in portrait mode
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	        // Get Widgets
	        mFrameLayout = (FrameLayout)findViewById(R.id.camera_preview);
	        //mRelativeLayoutSnap = (RelativeLayout)findViewById(R.id.controls_layout);
	        mFrameLayoutSelection = (FrameLayout)findViewById(R.id.selection_layout);
	        mButtonSnap = (Button)findViewById(R.id.button_selection);
	        //mButtonSnap = (Button)findViewById(R.id.button_photo);
	        mConsignZone = (LinearLayout)findViewById(R.id.consigne_zone_layout);
	        
	        // Get audio manager and camera sound
	        mAudioManager = (AudioManager) getApplicationContext().getSystemService( Context.AUDIO_SERVICE );
	        mCameraShutterSound = MediaPlayer.create( 	getApplicationContext(), 
	        											Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
	        
	        // Set control callbacks
	        setControlCallbacks();
	        
	        // Get information about screen
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
	        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)mFrameLayoutSelection.getLayoutParams();
	        lp.height = mScreenInfo.heightPixels*50/100;
	        lp.width = mScreenInfo.widthPixels*75/100;
	        
	        mFrameLayoutSelection.setLayoutParams(lp);
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
	        	mFrameLayout.addView(mSurfaceView); 		// Display Preview
		        //mRelativeLayoutSnap.bringToFront();			// Display control on Top
		        mFrameLayoutSelection.bringToFront(); 	// Display selection Frame on top
		        mConsignZone.bringToFront();
	        }
	    }
	    
	/**
	 * CallBacks
	 * 
	 */
	    
	    private Camera.ShutterCallback mCALLBACK_Shutter = new Camera.ShutterCallback() {
			
			@Override
			public void onShutter() {
				// TODO Auto-generated method stub
				int l_volume = mAudioManager.getStreamVolume( AudioManager.STREAM_NOTIFICATION );
				
				if( ( mCameraShutterSound != null ) && (l_volume != 0) )
				{
					mCameraShutterSound.start();
				}
				
			}
		};
		
	    private Camera.PictureCallback mCALLBACK_Picture = new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				Bitmap l_origin_data = BitmapFactory.decodeByteArray(data, 0,data.length );
				
				int l_NewWidth = ( l_origin_data.getWidth()*3 )/4;
				int l_NewHeight = ( l_origin_data.getHeight()/2 );
				int l_NewTopLeftEdgeX = (l_origin_data.getWidth()/2) - ( l_NewWidth/2 );
				int l_NewTopLeftEdgeY = (l_origin_data.getHeight()/2) - ( l_NewHeight/2 );
				
				Bitmap l_final_data = Bitmap.createBitmap( 
						l_origin_data, 
						l_NewTopLeftEdgeX, 
						l_NewTopLeftEdgeY, 
						l_NewWidth,
						l_NewHeight);
				File l_pictureFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),System.currentTimeMillis() + "-test.jpg");	
				
		        try {
		            FileOutputStream l_fos = new FileOutputStream( l_pictureFile );
		            l_final_data.compress(CompressFormat.JPEG, 100, l_fos);
		            //fos.write(data);
		            l_fos.flush();
		            l_fos.close();
		            Log.d(TAG,l_pictureFile.getAbsolutePath());
		        } catch (FileNotFoundException e) {
		            Log.d(TAG, "File not found: " + e.getMessage());
		        } catch (IOException e) {
		            Log.d(TAG, "Error accessing file: " + e.getMessage());
		        }
		        mCamera.startPreview();

			}
	    	
	    };
	    
	/**
	 * Control utilities
	 */
	    
	 private void setControlCallbacks()
	 {
		 /*
		  * Snapshot button
		  */
		 this.mButtonSnap.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*
				 * Check if camera is operational
				 */
				if( mCamera != null )
				{
					/*
					 *  shutter : 	the callback for image capture moment, or null
					 *	raw 	:   the callback for raw (uncompressed) image data, or null
					 *	postview: 	callback with postview image data, may be null
				 	 *	jpeg 	:   the callback for JPEG image data, or null 
					 */
					Log.d(TAG,"Saving picture to : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));

					mButtonSnap.setVisibility(View.GONE);
					mCamera.takePicture( mCALLBACK_Shutter, null, mCALLBACK_Picture );		// Take Picture
					mButtonSnap.setVisibility(View.VISIBLE);
					//Toast.makeText( getApplicationContext(), 				
					//				"Picture have been saved into : " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), 
					//				Toast.LENGTH_LONG)
					//				.show();								// Inform user
					
					Intent returnIntent = new Intent();
					 returnIntent.putExtra("photoLink","TEEEEST");
					 setResult(RESULT_OK,returnIntent);     
					 finish();
				}
			}
		});
		 
	 }
	    
	/**
	 *  Camera utilities   
	 * 
	 */
	private boolean safeCameraOpen() {
		
	    Camera  			l_camera = null;   	
	  
	    /**
	     * Check if camera device is available on system.
	     */
	    if( ! this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) )
	    	return false;
	    
	    try 
	    {
	    	/**
	    	 * Releasing previous camera instanciation
	    	 */
	    	releasePreview();
	    	
	    	/**
	    	 * Opening device
	    	 */
	        l_camera = Camera.open();
	        
	        /**
	         * Set the optimal preview and picture size ( according to the screen size )
	         */
	        //setCameraConfig( l_camera );
	        CameraConfigurator.setOptimalDefaultConfiguration( l_camera, mLandscapeWidth, mLandscapeHeight);
	        
	        /**
	         * Setting the camera
	         */
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

//private void setCameraConfig( Camera camera )
//{
//	Camera.Parameters l_param = camera.getParameters();
//	List<Camera.Size> l_preview_sizes = l_param.getSupportedPreviewSizes();
//	List<Camera.Size> l_picture_sizes = l_param.getSupportedPictureSizes();
//	
//	Camera.Size l_tmp_size	   = null;
//	Camera.Size l_optimal_size = null;
//	Camera.Size l_maximal_size = null;
//	
//	for( int l_index = 0 ; l_index < l_preview_sizes.size() ; l_index++ )
//	{
//		l_tmp_size = l_preview_sizes.get( l_index );
//		Log.i(TAG, "Preview size available : " + l_tmp_size.width + "x" + l_tmp_size.height);
//		if( l_tmp_size.width == mLandscapeWidth )
//		{
//			l_optimal_size = l_tmp_size;
//			if( l_tmp_size.height == mLandscapeHeight )
//			{
//				// Exact size have been found !
//				break;
//			}
//		}
//		
//		if( 	( l_maximal_size == null ) ||  
//				( 	( l_maximal_size.height*l_maximal_size.width ) < 
//					( l_tmp_size.height * l_tmp_size.width) ) 			)
//		{
//			l_maximal_size = l_tmp_size;
//		}
//	}
//	
//	if( l_optimal_size != null )
//	{
//		l_param.setPreviewSize( l_optimal_size.width, l_optimal_size.height);
//	}
//	else
//	{
//		l_param.setPreviewSize( l_maximal_size.width, l_maximal_size.height);
//	}
//	l_maximal_size = null;
//	l_optimal_size = null;
//	l_tmp_size = null;
//	
//	
//	for( int l_index = 0 ; l_index < l_picture_sizes.size() ; l_index++ )
//	{
//		l_tmp_size = l_picture_sizes.get( l_index );
//		Log.i(TAG, "Picture size available : " + l_tmp_size.width + "x" + l_tmp_size.height);
//		if( l_tmp_size.width == mLandscapeWidth )
//		{
//			l_optimal_size = l_tmp_size;
//			if( l_tmp_size.height == mLandscapeHeight )
//			{
//				// Exact size have been found !
//				break;
//			}
//		}
//		
//		if( 	( l_maximal_size == null ) ||  
//				( 	( l_maximal_size.height*l_maximal_size.width ) < 
//					( l_tmp_size.height * l_tmp_size.width) ) 			)
//		{
//			l_maximal_size = l_tmp_size;
//		}
//	}
//	
//	if( l_optimal_size != null )
//	{
//		l_param.setPictureSize( l_optimal_size.width, l_optimal_size.height);
//	}
//	else
//	{
//		l_param.setPictureSize( l_maximal_size.width, l_maximal_size.height);
//	}
//	l_param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//	l_param.setRotation( DISPLAY_AND_PICTURE_ORIENTATION );
//	camera.setDisplayOrientation( DISPLAY_AND_PICTURE_ORIENTATION );
//	camera.setParameters( l_param );
//	
//	Log.d(TAG,"Info : Preview size selected : " + l_param.getPreviewSize().width + "x" +  l_param.getPreviewSize().height);
//	Log.d(TAG,"Info : Picture size selected : " + l_param.getPictureSize().width + "x" + l_param.getPictureSize().height);
//	Log.d(TAG,"Info : Camera orientation set to : " + DISPLAY_AND_PICTURE_ORIENTATION + " degrees.");
//	
//	
//}
