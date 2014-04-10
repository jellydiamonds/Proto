package com.jellydiamonds.android.metier;

public enum GemCut {
	
	EMPTY(0),
	ASSCHER(1),
	BAGUETTE(2),
	BRIOLETTE(3),
	CABASHIAN(4),
	CHECKER_BOARD(5),
	CONCAVE(6),
	DIAMOND(7),
	FANCY(8),
	MILLENIUM(9),
	MIXTE(10),
	PORTUGUEESE(11),
	PRINCESS(12),
	RADIANT(13),
	RUFF(14),
	STEP_CUT(15);
	
	protected int cut;
	
	GemCut( int cut )
	{
		this.cut = cut;
	}

	public Integer getValue()
	{
		return this.cut;
	}
	
	public static GemCut fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
