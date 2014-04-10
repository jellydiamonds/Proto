package com.jellydiamonds.android.metier;

public enum GemShape {
	
	EMPTY(0),
	CAMER(1),
	CUSHION(2),
	FANCY(3),
	HEART(4),
	MARQUISE(5),
	OCTAGON(6),
	OVAL(7),
	PEAR(8),
	RECTANGLE(9),
	ROUND(10),
	RUFF(11),
	SQUARE(12),
	TRILLION(13);
	
	protected int shape;
	
	GemShape( int shape )
	{
		this.shape = shape;
	}

	public Integer getValue()
	{
		return this.shape;
	}
	
	public static GemShape fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
