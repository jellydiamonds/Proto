package com.jellydiamonds.android.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private ImageView mImageView = null;
	private Button mButtonTake = null;
	private Button mButtonSave = null;
	private Bitmap mImageBitmap = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if( ! getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
        	Toast.makeText(getApplicationContext(), "There is no camera on your device...", Toast.LENGTH_LONG).show();
        	System.exit(0);
        }
        
        mImageView = (ImageView)findViewById(R.id.imageView1);
        mButtonTake = (Button)findViewById(R.id.Button02);
        
        mButtonTake.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dispatchTakePictureIntent();
				mButtonSave.setEnabled(true);
			}
		});
        
        mButtonSave = (Button)findViewById(R.id.Button03);
        mButtonSave.setEnabled(false);
        mButtonSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				try {
					File image = File.createTempFile("testCamera1", ".png",storage);
					OutputStream fout = new FileOutputStream(image);
					mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
					fout.flush();
					fout.close();
					Toast.makeText(getApplicationContext(), "File successfully saved at " + image, Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
            //mImageBitmap.setConfig(Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mImageBitmap);
        }
    }
    
}
