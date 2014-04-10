package com.jellydiamonds.android.metier;

public enum GemLight {
	
	EMPTY(0),
	DAYLIGHT(1),
	FLUORESCENT_LIGHT(2),
	INCANDESCENT_LIGHT(3);
	
	protected int light;
	
	GemLight( int light )
	{
		this.light = light;
	}
	
	public Integer getValue()
	{
		return this.light;
	}
	
	public static GemLight fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
