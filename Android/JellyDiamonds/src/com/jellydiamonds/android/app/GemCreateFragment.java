package com.jellydiamonds.android.app;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class GemCreateFragment extends Fragment {

	private static GemCreateFragment mInstance = null;
	
	private ImageButton mPicture = null;
	private Button		mButton = null;
	
	
	public GemCreateFragment()
	{
		
	}
	
	public static GemCreateFragment newInstance() {
		
		if( GemCreateFragment.mInstance == null)
			GemCreateFragment.mInstance =  new GemCreateFragment();
		return GemCreateFragment.mInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gem_edit, container, false);
		
		
		mButton = (Button)view.findViewById(R.id.gem_edit_validate);
		mPicture = (ImageButton)view.findViewById(R.id.gem_edit_imageButton);
		
		mPicture.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Main)getActivity()).startCameraActivity();
			}
			
		});
		
		mButton.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return view;
	}
}
