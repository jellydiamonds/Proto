package com.jellydiamonds.android.metier;

import java.io.Serializable;
import java.util.Date;

public class JellyLicence implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5354345149625954478L;
	
	private String mUserReference = null;
	private Date   mPeremptionDate = null;
	
	public JellyLicence()
	{
		
	}
	
	public JellyLicence( String userReference, Date peremptionDate )
	{
		this.mUserReference = userReference;
		this.mPeremptionDate = peremptionDate;
	}

	public String getUserReference() {
		return mUserReference;
	}

	public void setUserReference(String mUserReference) {
		this.mUserReference = mUserReference;
	}

	public Date getPeremptionDate() {
		return mPeremptionDate;
	}

	public void setPeremptionDate(Date mPeremptionDate) {
		this.mPeremptionDate = mPeremptionDate;
	}
	
	public boolean isValid()
	{
		Date l_now = new Date( System.currentTimeMillis() );
		
		return( l_now.compareTo(this.mPeremptionDate) < 0  );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mPeremptionDate == null) ? 0 : mPeremptionDate.hashCode());
		result = prime * result
				+ ((mUserReference == null) ? 0 : mUserReference.hashCode());
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
		JellyLicence other = (JellyLicence) obj;
		if (mPeremptionDate == null) {
			if (other.mPeremptionDate != null)
				return false;
		} else if (!mPeremptionDate.equals(other.mPeremptionDate))
			return false;
		if (mUserReference == null) {
			if (other.mUserReference != null)
				return false;
		} else if (!mUserReference.equals(other.mUserReference))
			return false;
		return true;
	}
	
	
}
