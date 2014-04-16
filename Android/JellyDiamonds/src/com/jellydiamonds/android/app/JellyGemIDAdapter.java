package com.jellydiamonds.android.app;

import com.jellydiamonds.android.metier.GemID;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class JellyGemIDAdapter extends BaseAdapter {
	
	private Context mContext = null;
	private GemID mGemID = null;
	private int	mLayout = -1;
	private LayoutInflater mLayoutInflater = null;

	public JellyGemIDAdapter( Context appContext, GemID data )
	{
		this.mContext = appContext;
		this.mGemID = data;
		this.mLayout = R.layout.fragment_gem_details;
		this.mLayoutInflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public GemID getGemID() {
		return mGemID;
	}

	public void setGemID(GemID mGemID) {
		this.mGemID = mGemID;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
