package com.jellydiamonds.android.app;

import java.util.ArrayList;
import java.util.Collection;

import com.jellydiamonds.android.metier.GemID;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class JellyCollectionAdapter implements ListAdapter{

	private Context mContext = null;
	private ArrayList<GemID> mData = null;
	private int	mLayout = -1;
	
	private LayoutInflater mLayoutInflater = null;
	
	public JellyCollectionAdapter( Context context, Collection<GemID> data )
	{
		this.mContext = context;
		this.mData = new ArrayList<GemID>(data);
		this.mLayout = R.layout.fragment_item_row;
		this.mLayoutInflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View rowView = convertView;
		GemID currentGem = null;
		
		if( convertView == null)
		{
			rowView = mLayoutInflater.inflate(mLayout, null);
		}
		else
		{
			rowView = convertView;
		}
		currentGem = mData.get( position );
		
		ImageView l_picture = (ImageView) rowView.findViewById(R.id.icon);
		TextView  l_title = (TextView) rowView.findViewById(R.id.title);
		TextView  l_summary = (TextView) rowView.findViewById(R.id.desc);
		
		
		l_title.setText( "Ref : " + currentGem.getReference() ); // Temporary
		l_summary.setText( currentGem.toString() );
		//l_picture.setImageBitmap(BitmapFactory.decodeFile( currentGem.getPhotoLink().getPath()));
		l_picture.setImageDrawable( this.mContext.getResources().getDrawable(R.drawable.ic_rubik));
		
		return rowView;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return mData.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

}
