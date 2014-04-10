package com.jellydiamonds.android.metier;

public enum GemEnhancement {
	
	EMPTY(0),
	HIGH_PRESSURE(1),
	HIGH_TEMPERATURE(2);
	//Unknown(3);
	
	protected int enhancement;
	
	GemEnhancement ( int enhancement )
	{
		this.enhancement = enhancement;
	}
	
	public Integer getValue()
	{
		return this.enhancement;
	}
	
	public static GemEnhancement fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
