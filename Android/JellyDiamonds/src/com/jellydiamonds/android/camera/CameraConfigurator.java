package com.jellydiamonds.android.camera;

import java.util.List;

import android.hardware.Camera;
import android.util.Log;

public class CameraConfigurator {
	
	public static final int DEFAULT_DISPLAY_ORIENTATION_DEGREE = 90;
	public static final int DEFAULT_PICTURE_ORIENTATION_DEGREE = 90;
	public static final String TAG = "[CAMERACONFIGURATOR]";
	
	private static Camera.Parameters smParameter = null;
	private static int				 smWidthArea = 0;
	private static int				 smHeightArea = 0;
	
	
	public static boolean setOptimalDefaultConfiguration( Camera camera, int widthZone, int heightZone )
	{
		Camera.Parameters 		l_tmp_parameter = null;
		Camera.Size				l_tmp_previewSize = null;
		Camera.Size				l_tmp_pictureSize = null;
		float				  	l_aspect_ratio = Float.MAX_VALUE;
		
		if( 	( camera == null ) || 
				( widthZone == 0 ) || 
				( heightZone == 0 ) 	)
			return false;
		
		camera.setDisplayOrientation( DEFAULT_DISPLAY_ORIENTATION_DEGREE );
		
		if( 	( smParameter != null ) &&
				( widthZone == smWidthArea) &&
				( heightZone == smHeightArea) )
		{
			camera.setParameters( smParameter );
			return true;
		}
		
		l_aspect_ratio = ( (float)widthZone ) / ( (float)heightZone );	// Zone aspect ratio
		l_tmp_parameter = camera.getParameters();						// Current Camera parameter
		l_tmp_previewSize = getBestPreviewSize( l_tmp_parameter.getSupportedPreviewSizes(), widthZone, heightZone, l_aspect_ratio); // Compute best ratio
		l_tmp_pictureSize = getBestPictureSize(l_tmp_parameter.getSupportedPictureSizes(), l_tmp_previewSize);
		
		l_tmp_parameter.setPreviewSize(l_tmp_previewSize.width, l_tmp_previewSize.height);
		l_tmp_parameter.setPictureSize(l_tmp_pictureSize.width, l_tmp_pictureSize.height);
		l_tmp_parameter.setFocusMode( Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE );
		l_tmp_parameter.setRotation( DEFAULT_PICTURE_ORIENTATION_DEGREE );
		Log.d(TAG,"Parameters computed : ");
		Log.d(TAG,"Preview size : " + l_tmp_previewSize.width + "x" + l_tmp_previewSize.height);
		Log.d(TAG,"Picture size : " + l_tmp_pictureSize.width + "x" + l_tmp_pictureSize.height);
		Log.d(TAG,"Continuous focus mode enabled");
		camera.setParameters( l_tmp_parameter );
		
		smParameter = l_tmp_parameter;
		smWidthArea = widthZone;
		smHeightArea = heightZone;
		
		return true;
		
	}
	/**
	 * Look for the best resolution to avoid distortion of screen preview (depond on screen resolution)
	 * @param previewSize
	 * @param widthZone
	 * @param heightZone
	 * @param aspectRatio
	 * @return
	 */
	private static Camera.Size getBestPreviewSize( List<Camera.Size> previewSize, int widthZone, int heightZone, float aspectRatio )
	{
		Camera.Size l_result = null;
		float 		l_smallestRatioDifference = Float.MAX_VALUE;
		
		for( Camera.Size l_current_size : previewSize)
		{
			float l_currentRatio = ( (float)l_current_size.width ) / ( (float) l_current_size.height );
			
			if( ( Math.abs( aspectRatio - l_currentRatio) < l_smallestRatioDifference ) 	&& // Retrieveing the smallest difference between screen ratio and resolution ratio
					l_current_size.width <= widthZone 							&& // width of current resolution should be lower or equal zone
					l_current_size.height <= heightZone		)					   // height of current resolution should be lower or equal zone	
			{
				l_smallestRatioDifference = Math.abs( aspectRatio - l_currentRatio );			// Now smallestratiodifference is reevaluated 
				l_result = l_current_size;														// Selected size has better ratio
			}
		}
		
		return l_result;
	}
	
	/**
	 * Look for the higher picture quality resolution with lower aspect ratio difference with preview.
	 * @param pictureSize
	 * @param previewSizeSelected
	 * @return
	 */
	private static Camera.Size getBestPictureSize( List<Camera.Size> pictureSize, Camera.Size previewSizeSelected )
	{
		Camera.Size 	l_result = null;
		float			l_smallestRatioDifference = Float.MAX_VALUE;
		float			l_previewRatio	= ( (float) previewSizeSelected.width) / ( (float) previewSizeSelected.height);
		
		for( Camera.Size l_current_size : pictureSize)
		{
			float l_current_ratio = ( (float)l_current_size.width ) / ( (float) l_current_size.height );
			
			if( Math.abs( l_previewRatio - l_current_ratio ) < l_smallestRatioDifference )
			{
				l_smallestRatioDifference = Math.abs( l_previewRatio - l_current_ratio );
				l_result = l_current_size;
			}
		}
		
		return l_result ;
	}
	
	
	

}
