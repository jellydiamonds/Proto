using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JDClientRicheCsharp.Class
{
    public abstract class GemStatus  {	

	    private static long serialVersionUID = -5815555278193921805L;
	    protected String mCurrentStatus;
	
	
	    public GemStatus()
	    {
		    mCurrentStatus = "NoStatus";
	    }
	
	
	    public override String toString()
	    {
		    return this.mCurrentStatus;
	    }
    }
//_____________________________________________________________________________________
    public class GemStatusTradeInProgress : GemStatus{

	    private static  long serialVersionUID = 4466307033365404297L;

		/**
		    *  Gem-ID is in TradeInProgress status when a transaction have been started.
		    */
	
		public GemStatusTradeInProgress()
		{
			this.mCurrentStatus = "TradeInProgress";
		}
    }
//_____________________________________________________________________________________
    public class GemStatusLocal : GemStatus{

	    private static  long serialVersionUID = -7996474409875887610L;

	    /**
	     * Gem-ID is not synchronized with JellyDiamonds (not published...)
	     */
	    public GemStatusLocal()
	    {
		    this.mCurrentStatus = "Local";
	    }
    }
//_____________________________________________________________________________________
    public class GemStatusForSale : GemStatus{

		private static long serialVersionUID = 3686868522362429214L;

		public GemStatusForSale()
		{
			this.mCurrentStatus = "ForSale";
		}
    }
//_____________________________________________________________________________________
    public class GemStatusDeleted : GemStatus {
	
	/**
	 * 
	 */
	private static : long serialVersionUID = 1641134954149481822L;

	/**
	 * Gem-ID is in Deleted status when it has been published an d then removed from JellyDiamonds.
	 */
	public GemStatusDeleted()
	{
		this.mCurrentStatus = "Deleted";
	}

}
}
