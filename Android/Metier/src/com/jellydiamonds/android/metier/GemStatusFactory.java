package com.jellydiamonds.android.metier;

public class GemStatusFactory {
	
	public static GemStatus create( String status)
	{
		if( status.toLowerCase().equals("default") )
			return new GemStatusDefault();
		else if( status.toLowerCase().equals("forsale") )
			return new GemStatusForSale();
		else if( status.toLowerCase().equals("deleted") )
			return new GemStatusDeleted();
		else if( status.toLowerCase().equals("local") )
			return new GemStatusLocal();
		else if( status.toLowerCase().equals("tradeinprogress") )
				return new GemStatusTradeInProgress();
		else
			return new GemStatusLocal();
	}

	public static GemStatus create( int status )
	{
		switch( status )
		{
		case 1:
			return new GemStatusDefault();
		case 2:
			return new GemStatusLocal();
		case 3:
			return new GemStatusForSale();
		case 4:
			return new GemStatusTradeInProgress();
		case 5:
			return new GemStatusDeleted();
		default:
			return new GemStatusLocal();
		}
	}
}
