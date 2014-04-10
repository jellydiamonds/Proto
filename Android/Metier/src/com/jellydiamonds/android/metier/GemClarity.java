package com.jellydiamonds.android.metier;

public enum GemClarity {
	
	EMPTY(0),
	Loupe_Clean(1),
	Eye_Clean(2),
	Eyes_Clean_to_Slightly_Included(3),
	Slightly_Included(4),
	Moderately_Included(5),
	Heavily_Included(6),
	Translucent(7),
	Opaque(8);
	
	protected int clarity;
	
	GemClarity( int clarity )
	{
		this.clarity = clarity;
	}

	public Integer getValue()
	{
		return this.clarity;
	}
}
