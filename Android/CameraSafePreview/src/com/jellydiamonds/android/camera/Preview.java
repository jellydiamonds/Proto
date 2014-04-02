package com.jellydiamonds.android.camera;

import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class Preview extends SurfaceView implements SurfaceHolder.Callback {
   
	private static final String TAG = "[CAMERASAFEPREVIEW: Preview]";
    private Camera mCamera = null;
    private SurfaceHolder mHolder = null;

    public Preview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = this.getHolder();
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "ERROR :  setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        
//        this.mHolder.removeCallback(this);
//        this.getHolder().removeCallback(this);
//        mCamera.stopPreview();
//        mCamera.release();
//        mCamera = null;
    	if(mCamera != null)
    		mCamera.stopPreview();
    	
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "ERROR :  starting camera preview: " + e.getMessage());
        }
    }
}
//public class Preview extends SurfaceView implements SurfaceHolder.Callback {
//    private static final String TAG = "Preview";
//
//    SurfaceHolder mHolder;
//    public Camera camera;
//
//    Preview(Context context) {
//        super(context);
//
//        // Install a SurfaceHolder.Callback so we get notified when the
//        // underlying surface is created and destroyed.
//        mHolder = getHolder();
//        mHolder.addCallback(this);
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//
//    public void surfaceCreated(SurfaceHolder holder) {
//        // The Surface has been created, acquire the camera and tell it where
//        // to draw.
//
//        if(camera == null){
//        camera = Camera.open();
//
//
//        camera.setDisplayOrientation(90);
//        try {
//            camera.setPreviewDisplay(holder);
//
//
//            camera.setPreviewCallback(new PreviewCallback() {
//
//                public void onPreviewFrame(byte[] data, Camera arg1) {
//
//                        Preview.this.invalidate();
//                }
//            });
//        } catch (IOException e) {
//           camera.release();
//           camera = null;
//        }
//        }
//    }
//
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        // Surface will be destroyed when we return, so stop the preview.
//        // Because the CameraDevice object is not a shared resource, it's very
//        // important to release it when the activity is paused.
//        if(camera!=null){
//            camera.stopPreview();
//            camera.setPreviewCallback(null);
//
//            camera.release();
//            camera = null;
//        }
//
//    }
//
//    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        // Now that the size is known, set up the camera parameters and begin
//        // the preview.
//        Camera.Parameters parameters = camera.getParameters();
////        parameters.setPreviewSize(w, h);
//        camera.setParameters(parameters);
//        camera.startPreview();
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//            super.draw(canvas);
//            Paint p= new Paint(Color.RED);
//            Log.d(TAG,"draw");
//            canvas.drawText("PREVIEW", canvas.getWidth()/2, canvas.getHeight()/2, p );
//    }
//
//    public void releaseCameraAndPreview() {
//         if (camera != null) {
//            camera.release();
//            camera = null;
//        }
//    }
//}
//
//public class Preview extends ViewGroup implements SurfaceHolder.Callback {
//
//    private SurfaceView mSurfaceView = null;
//    private SurfaceHolder mHolder = null;
//    private Camera mCamera = null;
//    private List<Size> mSupportedPreviewSizes = null;
//
//    Preview(Context context) {
//        super(context);
//
//        mSurfaceView = new SurfaceView(context);
//        addView(mSurfaceView);
//
//        // Install a SurfaceHolder.Callback so we get notified when the
//        // underlying surface is created and destroyed.
//        mHolder = mSurfaceView.getHolder();
//        mHolder.addCallback(this);
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        
//    }
//    
//    public void setCamera(Camera camera) {
//        if (mCamera == camera) { return; }
//        
//        stopPreviewAndFreeCamera();
//        
//        mCamera = camera;
//        
//        if (mCamera != null) {
//            List<Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
//            mSupportedPreviewSizes = localSizes;
//            requestLayout();
//          
//            try {
//                mCamera.setPreviewDisplay(mHolder);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//          
//            // Important: Call startPreview() to start updating the preview
//            // surface. Preview must be started before you can take a picture.
//            mCamera.startPreview();
//        }
//    }
//
//	/**
//	 * When this function returns, mCamera will be null.
//	 */
//	private void stopPreviewAndFreeCamera() {
//
//	    if (mCamera != null) {
//	        // Call stopPreview() to stop updating the preview surface.
//	        mCamera.stopPreview();
//	    
//	        // Important: Call release() to release the camera for use by other
//	        // applications. Applications should release the camera immediately
//	        // during onPause() and re-open() it during onResume()).
//	        mCamera.release();
//	    
//	        mCamera = null;
//	    }
//	}
//	
//	@Override
//	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
//		// TODO Auto-generated method stub
//	    // Now that the size is known, set up the camera parameters and begin
//	    // the preview.
//	    Camera.Parameters parameters = mCamera.getParameters();
//	    parameters.setPreviewSize(mSupportedPreviewSizes.get(0).width, mSupportedPreviewSizes.get(0).height);
//	    requestLayout();
//	    mCamera.setParameters(parameters);
//
//	    // Important: Call startPreview() to start updating the preview surface.
//	    // Preview must be started before you can take a picture.
//	    mCamera.startPreview();
//
//		
//	}
//	
//
//	@Override
//	public void surfaceCreated(SurfaceHolder arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder arg0) {
//		// TODO Auto-generated method stub
//	    // Surface will be destroyed when we return, so stop the preview.
//	    if (mCamera != null) {
//	        // Call stopPreview() to stop updating the preview surface.
//	        mCamera.stopPreview();
//	    }
//
//		
//	}
//
//	@Override
//	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
