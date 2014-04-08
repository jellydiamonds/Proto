package com.jellydiamonds.android.metier;

public enum GemEnhancement {

	High_Pressure(1),
	High_Temperature(2),
	Unknown(3);
	
	protected int enhancement;
	
	GemEnhancement ( int enhancement )
	{
		this.enhancement = enhancement;
	}
	
	public Integer getValue()
	{
		return this.enhancement;
	}
	
}
