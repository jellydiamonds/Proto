package com.jellydiamonds.android.app;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class JellyConst {

	private static Context mContext = null;
	private static String mDirectory = null;
	private static String mFileSavePath = null;
	private static String mFileSaveName = null;
	private static String mPicsDirectory = null;
	
	public static void initialize( Context context)
	{
		File l_tmp_dir = null;
		
		mContext = context;
		mDirectory = Environment.getExternalStorageDirectory().toString() + 
						"/" + mContext.getResources().getString(R.string.jellyuser_save_dir);
		mFileSaveName = mContext.getResources().getString(R.string.jellyuser_save_file);
		mFileSavePath = mDirectory + "/" + mFileSaveName;
		mPicsDirectory = mDirectory + "/" +  mContext.getResources().getString(R.string.jellyuser_pic_dir);
		
		l_tmp_dir = new File(mDirectory);
		if( !l_tmp_dir.exists())
			l_tmp_dir.mkdir();
		
		l_tmp_dir = new File(mPicsDirectory);
		if( !l_tmp_dir.exists())
			l_tmp_dir.mkdir();
	}
	
	public static String getSaveDir()
	{
		return mDirectory;
	}
	
	public static String getFileSaveName()
	{
		return mFileSaveName;
	}
	
	public static String getFileSavePath()
	{
		return mFileSavePath;
	}
	
	public static String getPicsDirectory()
	{
		return mPicsDirectory;
	}
	
	public static File getPhotoPath( String name )
	{
		return new File(mPicsDirectory + "/" + name );
	}
	
}
