package com.jellydiamonds.android.metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class JellyUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3998293232567716497L;
	
	private Long mUserID = null;
	private String mDisplayName = null;
	private String mFirstName = null;
	private String mLastName = null;
	private String mEmail = null;
	private String mPhone = null;
	private String mAddress = null;
	private String mCity = null;
	private String mCountry = null;
	
	private Date   			mSignupDate = null;
	private JellyLicence 	mLicence = null;
	private JellyCollection mCollection = null;

	
	public JellyUser()
	{
		// User creation date
		this.mSignupDate = new Date( System.currentTimeMillis() );
		this.mCollection = new JellyCollection( this.mUserID );
		this.mFirstName = "Gest";
	}
	
	public JellyCollection getCollection()
	{
		return this.mCollection;
	}
	
	public void setCollection( JellyCollection collection )
	{
		this.mCollection = collection;
	}
	
	public JellyLicence getLicence() {
		return mLicence;
	}

	public void setLicence(JellyLicence mLicence) {
		this.mLicence = mLicence;
	}

	public Long getUserID() {
		return mUserID;
	}

	public void setUserID(Long mUserID) {
		this.mUserID = mUserID;
	}


	public String getDisplayName() {
		return mDisplayName;
	}


	public void setDisplayName(String mDisplayName) {
		this.mDisplayName = mDisplayName;
	}


	public String getFirstName() {
		return mFirstName;
	}


	public void setFirstName(String mFirstName) {
		this.mFirstName = mFirstName;
	}


	public String getLastName() {
		return mLastName;
	}


	public void setLastName(String mLastName) {
		this.mLastName = mLastName;
	}


	public String getEmail() {
		return mEmail;
	}


	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}


	public String getPhone() {
		return mPhone;
	}


	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}


	public String getAddress() {
		return mAddress;
	}


	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}


	public String getCity() {
		return mCity;
	}


	public void setCity(String mCity) {
		this.mCity = mCity;
	}


	public String getCountry() {
		return mCountry;
	}


	public void setCountry(String mCountry) {
		this.mCountry = mCountry;
	}


	public Date getSignupDate() {
		return mSignupDate;
	}


	public void setSignupDate(Date mSignupDate) {
		this.mSignupDate = mSignupDate;
	}
	
	public static boolean saveJellyUserContext( String nameFile, String directory, JellyUser user )
	{
		File l_saveDir = null;
		ObjectOutputStream l_outStream = null;
		
		if( 	( nameFile == null ) ||
				( directory == null ) || 
				( user == null ) )
			return false;
		
		l_saveDir = new File( directory );
		
		if(		( !l_saveDir.isDirectory() ) || 
				( !l_saveDir.canWrite() ) )
			return false;
		
		try {
			
			l_outStream = new ObjectOutputStream( new FileOutputStream( l_saveDir.toString() + File.separator + nameFile + ".jelly" ) );
			l_outStream.writeObject( user );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally
		{
			if( l_outStream !=  null)
			{
				try {
					l_outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
	
		
		return true;
	}
	
	public static JellyUser recoverJellyUserContext( String nameFile, String directory )
	{
		File l_saveDir = null;
		File l_saveFile = null;
		ObjectInputStream l_inStream = null;
		JellyUser l_return = null;
		
		if( 	( nameFile == null ) ||
				( directory == null ) )
			return null;
		
		l_saveDir = new File( directory );
		
		if( ! l_saveDir.isDirectory() )
			return null;
		
		l_saveFile = new File( l_saveDir, nameFile+".jelly" );
		
		if( ( !l_saveFile.isFile() ) ||
			( !l_saveFile.canRead() ) )
			return null;
		
		try {
			l_inStream = new ObjectInputStream( new FileInputStream( l_saveFile ) );
			l_return = (JellyUser)l_inStream.readObject();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally
		{
			if( l_inStream !=  null)
			{
				try {
					l_inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		
		return l_return;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mAddress == null) ? 0 : mAddress.hashCode());
		result = prime * result + ((mCity == null) ? 0 : mCity.hashCode());
		result = prime * result
				+ ((mCollection == null) ? 0 : mCollection.hashCode());
		result = prime * result
				+ ((mCountry == null) ? 0 : mCountry.hashCode());
		result = prime * result
				+ ((mDisplayName == null) ? 0 : mDisplayName.hashCode());
		result = prime * result + ((mEmail == null) ? 0 : mEmail.hashCode());
		result = prime * result
				+ ((mFirstName == null) ? 0 : mFirstName.hashCode());
		result = prime * result
				+ ((mLastName == null) ? 0 : mLastName.hashCode());
		result = prime * result
				+ ((mLicence == null) ? 0 : mLicence.hashCode());
		result = prime * result + ((mPhone == null) ? 0 : mPhone.hashCode());
		result = prime * result
				+ ((mSignupDate == null) ? 0 : mSignupDate.hashCode());
		result = prime * result + ((mUserID == null) ? 0 : mUserID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JellyUser other = (JellyUser) obj;
		if (mAddress == null) {
			if (other.mAddress != null)
				return false;
		} else if (!mAddress.equals(other.mAddress))
			return false;
		if (mCity == null) {
			if (other.mCity != null)
				return false;
		} else if (!mCity.equals(other.mCity))
			return false;
		if (mCollection == null) {
			if (other.mCollection != null)
				return false;
		} else if (!mCollection.equals(other.mCollection))
			return false;
		if (mCountry == null) {
			if (other.mCountry != null)
				return false;
		} else if (!mCountry.equals(other.mCountry))
			return false;
		if (mDisplayName == null) {
			if (other.mDisplayName != null)
				return false;
		} else if (!mDisplayName.equals(other.mDisplayName))
			return false;
		if (mEmail == null) {
			if (other.mEmail != null)
				return false;
		} else if (!mEmail.equals(other.mEmail))
			return false;
		if (mFirstName == null) {
			if (other.mFirstName != null)
				return false;
		} else if (!mFirstName.equals(other.mFirstName))
			return false;
		if (mLastName == null) {
			if (other.mLastName != null)
				return false;
		} else if (!mLastName.equals(other.mLastName))
			return false;
		if (mLicence == null) {
			if (other.mLicence != null)
				return false;
		} else if (!mLicence.equals(other.mLicence))
			return false;
		if (mPhone == null) {
			if (other.mPhone != null)
				return false;
		} else if (!mPhone.equals(other.mPhone))
			return false;
		if (mSignupDate == null) {
			if (other.mSignupDate != null)
				return false;
		} else if (!mSignupDate.equals(other.mSignupDate))
			return false;
		if (mUserID == null) {
			if (other.mUserID != null)
				return false;
		} else if (!mUserID.equals(other.mUserID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JellyUser [mUserID=" + mUserID + ", mDisplayName="
				+ mDisplayName + ", mFirstName=" + mFirstName + ", mLastName="
				+ mLastName + ", mEmail=" + mEmail + ", mPhone=" + mPhone
				+ ", mAddress=" + mAddress + ", mCity=" + mCity + ", mCountry="
				+ mCountry + ", mSignupDate=" + mSignupDate + ", mLicence="
				+ mLicence + ", mCollection=" + mCollection + "]";
	}
}
