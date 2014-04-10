package com.jellydiamonds.android.metier;

public enum GemClarity {
	
	EMPTY(0),
	LOUPE_CLEAN(1),
	EYE_CLEAN(2),
	EYES_CLEAN_TO_SLIGHTLY_INCLUDED(3),
	SLIGHTLY_INCLUDED(4),
	MODERATELY_INCLUDED(5),
	HEAVILY_INCLUDED(6),
	TRANSLUCENT(7),
	OPAQUE(8);
	
	protected int clarity;
	
	GemClarity( int clarity )
	{
		this.clarity = clarity;
	}

	public Integer getValue()
	{
		return this.clarity;
	}
	
	public static GemClarity fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
