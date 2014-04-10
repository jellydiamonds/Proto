package com.jellydiamonds.android.metier;

public enum GemOrigin {
	
	EMPTY(0),
	AFGHANISTAN(1),
	AUSTRALIA(2),
	BIRMANIA(3),
	BRAZIL(4),
	COLOMBIA(5),
	INDIA(6),
	KENYA(7),
	MADAGASCAR(8),
	MOZAMBIQUE(9),
	NAMIBIA(10),
	NIGERIA(11),
	PAKISTAN(12),
	RUSSIA(13),
	SRI_LANKA(14),
	TANZANIA(15),
	THAILAND(16),
	USA(17),
	ZAMBIA(18);
	
	protected int origin;
	
	GemOrigin( int origin )
	{
		this.origin = origin;
	}
	
	public Integer getValue() 
	{
		return this.origin;
	}
	
	public static GemOrigin fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
