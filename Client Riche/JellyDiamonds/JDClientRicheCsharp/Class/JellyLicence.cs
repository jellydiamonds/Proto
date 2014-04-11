using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JDClientRicheCsharp.Class
{
    class JellyLicence
    {
         /**
	     * 
	     */
	    private static long serialVersionUID = -5354345149625954478L;
        public DateTime mPeremptionDate { get; set; }
        public string mUserReference { get; set; }

	    public JellyLicence()
	    {
		
	    }
	
	    public JellyLicence( String userReference, DateTime peremptionDate )
	    {
            this.mUserReference = userReference;
		    this.mPeremptionDate = peremptionDate;
	    }



	    public bool isValid()
	    {
		    DateTime l_now = System.DateTime.Now;
		
		    return (l_now.CompareTo(this.mPeremptionDate)<0);
	    }


    }
}
