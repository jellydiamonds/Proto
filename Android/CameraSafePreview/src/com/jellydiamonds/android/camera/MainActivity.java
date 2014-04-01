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
	        
	        mFrameLayout = (FrameLayout)findViewById(R.id.preview);
	    }

	    @Override
	    protected void onDestroy() {
	        // TODO Auto-generated method stub
	        super.onDestroy();
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
	        
	        if( ! safeCameraOpen() )
	        	return;
	        Log.println(Log.INFO, "[onResume()]", "Camera Opened !");
	        
	        /*Camera.Parameters l_param = mCamera.getParameters();
	        l_param.setRotation(270);
	        mCamera.setParameters(l_param);*/
	        mCamera.setDisplayOrientation(90);
	        mSurfaceView = new Preview(this, mCamera);
	        mFrameLayout.addView(mSurfaceView);
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
	    	releasePreview();
	        mCamera = Camera.open();
	        l_opened = (mCamera != null);
	    } catch (Exception e) {
	        Log.e(getString(R.string.app_name), "failed to open Camera");
	        e.printStackTrace();
	    }
	    
	    return l_opened;    
	}

	void releasePreview()
	{
		if( mSurfaceView != null )
			mSurfaceView.getHolder().removeCallback(mSurfaceView);
		
		
	    if (mCamera != null) {
	        mCamera.setPreviewCallback(null);
	        mCamera.release();
	        mCamera = null;
	    }
	    
	    
	}
	
}


