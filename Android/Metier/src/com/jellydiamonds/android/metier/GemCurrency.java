package com.jellydiamonds.android.metier;

public enum GemCurrency {
	
	EMPTY(0),
	EUR(1),
	USD(2),
	CAD(3),
	RUR(4),
	UAH(5);
	
	protected int currency;
	
	GemCurrency( int currency )
	{
		this.currency = currency;
	}
	
	public Integer getValue()
	{
		return this.currency;
	}
	
	public static GemCurrency fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
