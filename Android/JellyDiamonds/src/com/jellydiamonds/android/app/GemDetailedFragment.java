package com.jellydiamonds.android.app;

import com.jellydiamonds.android.metier.GemID;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GemDetailedFragment extends Fragment {
	
	private GemID  mGem = null;
	private static GemDetailedFragment mInstance = null;
	
	public GemDetailedFragment()
	{
		
	}
	
	public void setData( Object gem )
	{
		this.mGem = (GemID)gem;
	}

	public static GemDetailedFragment newInstance()
	{
			GemDetailedFragment.mInstance =  new GemDetailedFragment();

		return GemDetailedFragment.mInstance;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gem_details, container, false);
		
		ImageView l_image = (ImageView)view.findViewById(R.id.gem_details_imageView);
		TextView  l_specie = (TextView)view.findViewById(R.id.gem_details_species);
		TextView  l_mass = (TextView)view.findViewById(R.id.gem_details_mass);
		TextView  l_dimension = (TextView)view.findViewById(R.id.gem_details_dimension);
		TextView  l_shape = (TextView)view.findViewById(R.id.gem_details_shape);
		TextView  l_cutting = (TextView)view.findViewById(R.id.gem_details_cutting);
		TextView  l_clarity = (TextView)view.findViewById(R.id.gem_details_clarity);
		TextView  l_enhancement = (TextView)view.findViewById(R.id.gem_details_enhancement);
		TextView  l_certificate = (TextView)view.findViewById(R.id.gem_details_certificate);
		TextView  l_origin= (TextView)view.findViewById(R.id.gem_details_origin);
		TextView  l_comments = (TextView)view.findViewById(R.id.gem_details_comments);
		
		if( mGem.getPhotoLink() != null )
		{
			l_image.setImageBitmap( BitmapFactory.decodeFile( mGem.getPhotoLink().toString() ) );
		}
		else
		{
			//l_image.setImageDrawable(R.drawable.default_gem_image);
		}
		
		l_specie.setText( mGem.getSpecies().toString() );
		l_mass.setText( mGem.getMass() + " cts");
		l_dimension.setText(mGem.getSizeX() + "x" + mGem.getSizeY() + " mm");
		l_shape.setText( mGem.getShape().toString() );
		l_cutting.setText( mGem.getCut().toString() );
		l_clarity.setText( mGem.getClarity().toString() );
		l_enhancement.setText( mGem.getEnhancement().toString() );
		l_certificate.setText( mGem.getCertificate().toString() );
		l_origin.setText( mGem.getOrigin().toString() );
		l_comments.setText( mGem.getComments());
		
		
		return view;
	}
	
}
