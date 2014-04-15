package com.jellydiamonds.android.metier;

import java.io.Serializable;

public abstract class GemStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5815555278193921805L;
	protected String mCurrentStatus;
	protected int    mCurrentStatusNumber;
	
	public GemStatus()
	{
		mCurrentStatus = "NoStatus";
		mCurrentStatusNumber = 0;
	}
	
	
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mCurrentStatus == null) ? 0 : mCurrentStatus.hashCode());
		result = prime * result + mCurrentStatusNumber;
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
		GemStatus other = (GemStatus) obj;
		if (mCurrentStatus == null) {
			if (other.mCurrentStatus != null)
				return false;
		} else if (!mCurrentStatus.equals(other.mCurrentStatus))
			return false;
		if (mCurrentStatusNumber != other.mCurrentStatusNumber)
			return false;
		return true;
	}






	@Override
	public String toString()
	{
		return this.mCurrentStatus;
	}
	
	public Integer toValue()
	{
		return this.mCurrentStatusNumber;
	}

}
