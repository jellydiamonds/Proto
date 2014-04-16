package com.jellydiamonds.android.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PendingFragment extends Fragment {

	private static PendingFragment mInstance = null;
	
	public PendingFragment()
	{
		
	}
	
	public static PendingFragment newInstance() {
		
		if( PendingFragment.mInstance == null)
			PendingFragment.mInstance =  new PendingFragment();
		return PendingFragment.mInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pending_task, container, false);

		return view;
	}
}
