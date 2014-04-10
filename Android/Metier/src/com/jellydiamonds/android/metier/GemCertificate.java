package com.jellydiamonds.android.metier;

public enum GemCertificate {
	
	EMPTY(0),
	GIA(1),
	GUBLIN(2),
	AIGS(3),
	HRD(4);
	
	protected int certificate;
	
	GemCertificate( int certificate )
	{
		this.certificate = certificate;
	}
	
	public Integer getValue()
	{
		return this.certificate;
	}
	
	public static GemCertificate fromValue( int value )
	{
		if( 	(value <= 0 ) || 
				( value >= values().length ) )
			return values()[0];
		else
			return values()[value];
	}
}
