package com.jellydiamonds.android.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class GemDetailedFragment extends Fragment {
	
	
	private static GemDetailedFragment mInstance = null;
	
	public GemDetailedFragment()
	{
		
	}

	public static GemDetailedFragment newInstance()
	{
		if( GemDetailedFragment.mInstance == null )
			GemDetailedFragment.mInstance =  new GemDetailedFragment();

		return GemDetailedFragment.mInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gem_details, container, false);

		return view;
	}
	
}
