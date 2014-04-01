package com.jellydiamonds.android.camera;



import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.widget.FrameLayout;
import com.jellydiamonds.android.logger.JDLogger;

public class MainActivity extends Activity  {


	private Camera mCameraDevice = null;
	private CameraPreview mPreviewCamera = null;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(com.example.camerapreview.R.layout.activity_main);
        
        if ( !this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) )
        	System.exit(0);
        
        JDLogger.setAppName("CameraPreview");
        JDLogger.INFO("onCreate()");

    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	//cameraSetUp();
    	//mBackCameraInstance.startPreview()
    	JDLogger.INFO("onStart()");
    	
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	JDLogger.INFO("onResume()");
        this.mPreviewCamera = new CameraPreview( this, getCameraInstance());
        
        FrameLayout l_fl = (FrameLayout)findViewById(com.example.camerapreview.R.id.camera_preview);
        l_fl.addView( this.mPreviewCamera );
    	//this.mCameraDevice.startPreview();
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	//this.mCameraDevice.stopPreview();
    	super.onPause();
    	JDLogger.INFO("onPause()");
    	this.mCameraDevice.stopPreview();
    	releaseCamera();
    	

    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	JDLogger.INFO("onStop()");
    }
    
    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    	JDLogger.INFO("onRestart()");

    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	JDLogger.INFO("onDestroy()");
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.example.camerapreview.R.menu.main, menu);
        return true;
    }
    
    private void releaseCamera(){
        if (mCameraDevice != null){
            mCameraDevice.release();        // release the camera for other applications
            mCameraDevice = null;
        }
    }

    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD) private Camera getCameraInstance()
    {
    	/*Camera.CameraInfo l_infoCam = new Camera.CameraInfo();
    	int l_nbreCamera = Camera.getNumberOfCameras();
    	int l_backCamera = -1;
    	Camera l_camera = null;
    	
    	for(int l_id = 0; l_id < l_nbreCamera ; l_id++)
    	{
    		Camera.getCameraInfo(l_id, l_infoCam);
    		if(l_infoCam.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
    		{
    			Log.e("[CAMERAPREVIEW]", String.format("Camera n¡%d is the BACK CAMERA (%d)",l_id,l_infoCam.orientation));
    			l_backCamera = l_id;
    		}
    		else
    		{
    			Log.e("[CAMERAPREVIEW]", String.format("Camera n¡%d is the FRONT CAMERA (%d)",l_id,l_infoCam.orientation));
    		}
    		
    	}
    	if ( l_backCamera == -1 )
    		return null;
    	
    	try
    	{
    		l_camera = Camera.open(l_backCamera);
    		l_camera.setDisplayOrientation(90);
    	}
    	catch( Exception e)
    	{
    		e.printStackTrace();
    	}*/
    	
    	Camera l_camera = null;
    	
    	try
    	{
    		l_camera = Camera.open();
    	}
    	catch( Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return l_camera;
    }
}
