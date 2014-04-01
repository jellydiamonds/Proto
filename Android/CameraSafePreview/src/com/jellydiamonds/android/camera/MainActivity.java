package com.jellydiamonds.android.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends Activity {

	 	private Camera mCamera = null;
	    private Preview mSurfaceView = null;
	    private FrameLayout mFrameLayout = null;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	    }

	    @Override
	    protected void onDestroy() {
	        // TODO Auto-generated method stub
	        super.onDestroy();
	        releaseCamera();
	    }

	    @Override
	    protected void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	        releaseCamera();
	    }

	    @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();
	        
	        if( ! safeCameraOpen() )
	        	return;
	        Log.println(Log.INFO, "[onResume()]", "Camera Opened !");
//	        Camera.Parameters p = mCamera.getParameters();
//	        Size size = p.getPreviewSize();
//	        int width = size.width;
//	        int height = size.height;
	        //p.setPreviewFormat(ImageFormat.JPEG);
	        //mCamera.setParameters(p);


//	        mSurfaceView = new Preview(this);
//
//	        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//	        mSurfaceView.setLayoutParams(layoutParams);
//
//	        mFrameLayout = (FrameLayout)findViewById(R.id.preview);
//
//	        mFrameLayout.addView(mSurfaceView);

//	        for(int t_preview : p.getSupportedPreviewFormats() )
//	        {
//	        	Log.d("[CAMERASAFEPREVIEW]", "SupportedPreviewFormat : " + t_preview);
//	        }
	    }
	    
	private boolean safeCameraOpen() {
	    boolean l_opened = false;
	  
	    if( ! this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) )
	    	return l_opened;
	    
	    try {
	    	releaseCamera();
	        mCamera = Camera.open();
	        l_opened = (mCamera != null);
	    } catch (Exception e) {
	        Log.e(getString(R.string.app_name), "failed to open Camera");
	        e.printStackTrace();
	    }
	    
	    return l_opened;    
	}

	void releaseCamera()
	{
		mSurfaceView = null;
	    if (mCamera != null) {
	        mCamera.release();
	        mCamera = null;
	    }
	}
	
}

//	private Camera mCamera = null;
//	private Preview mPreview = null;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		mPreview = new Preview( getApplicationContext() );
//		
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		safeCameraOpen();
//		if( mCamera != null )
//			mPreview.setCamera(mCamera);
//	}
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		releaseCameraAndPreview();
//	}
//	
//	private boolean safeCameraOpen() {
//	    boolean qOpened = false;
//	  
//	    try {
//	        releaseCameraAndPreview();
//	        mCamera = Camera.open();
//	        qOpened = (mCamera != null);
//	    } catch (Exception e) {
//	        Log.e(getString(R.string.app_name), "failed to open Camera");
//	        e.printStackTrace();
//	    }
//	    
//	    return qOpened;    
//	}
//
//	private void releaseCameraAndPreview() {
//	    mPreview.setCamera(null);
//	    if (mCamera != null) {
//	        mCamera.release();
//	        mCamera = null;
//	    }
//	}


