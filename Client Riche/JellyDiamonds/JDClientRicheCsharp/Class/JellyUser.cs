using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JDClientRicheCsharp
{
    class JellyUser
    {

        /**
         * 
         */
        private static long serialVersionUID = 3998293232567716497L;

        public String mUserID { get; set; }
        public String mDisplayName { get; set; }
        public String mFirstName { get; set; }
        public String mLastName { get; set; }
        public String mEmail { get; set; }
        public String mPhone { get; set; }
        public String mAdress { get; set; }
        public String mCity { get; set; }
        public String mCountry { get; set; }

        private DateTime mSignupDate;
        private JellyLicence 	mLicence = null;
        private JellyCollection mCollection = null;


        public JellyUser()
        {
            // User creation date
            this.mSignupDate = System.DateTime.Now;
            //this.mCollection = new JellyCollection( this.mUserID );
        }


    }
}
