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

}
