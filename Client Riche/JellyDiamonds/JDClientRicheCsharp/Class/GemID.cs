using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using JDClientRicheCsharp.Models;

namespace JDClientRicheCsharp.Class
{
    class GemID
    {
	    private static long serialVersionUID = -5138810903060394612L;

        private String  reference{ get; set; }
        private String  color{ get; set; }
        private Double  mass{ get; set; }
        private float   sizeX{ get; set; }
        private float   sizeY{ get; set; }
        private float   sizeZ{ get; set; }
        private String  comments{ get; set; }
        private int priceCurrency{ get; set; }
        private Double  priceValue{ get; set; }
        private long    supplierID{ get; set; }
    
	    private DateTime    creationDate{ get; set; }
        private File photoLink { get; set; }
    
        private JDClientRicheCsharp.Models.GemID.GemSpecies species { get; set; }
        private JDClientRicheCsharp.Models.GemID.GemShape shape{ get; set; }
        private JDClientRicheCsharp.Models.GemID.GemCut cut{ get; set; }
        private JDClientRicheCsharp.Models.GemID.GemClarity clarity{ get; set; }
        private JDClientRicheCsharp.Models.GemID.GemLight light{ get; set; }
        private JDClientRicheCsharp.Models.GemID.GemEnhancements enhancement{ get; set; }
        private JDClientRicheCsharp.Models.GemID.GemCertificate certificate{ get; set; }
        private JDClientRicheCsharp.Models.GemID.GemOrigin origin{ get; set; }
        private GemStatus currentStatus{ get; set; }
    
        public GemID()
        {
    	    // Initial status is Local
    	    this.currentStatus = new GemStatusLocal();
    	    // Set creation date
    	    this.creationDate = System.DateTime.Now ;
        }

	
	    
        
	
	    
	    public override String toString() {
		    return "GemID [creationDate=" + creationDate + ", reference="
				    + reference + ", species=" + species + ", color=" + color
				    + ", shape=" + shape + ", cut=" + cut + ", mass=" + mass
				    + ", sizeX=" + sizeX + ", sizeY=" + sizeY + ", sizeZ=" + sizeZ
				    + ", clarity=" + clarity + ", enhancement=" + enhancement
				    + ", origin=" + origin + ", certificate=" + certificate
				    + ", comments=" + comments + ", priceCurrency=" + priceCurrency
				    + ", priceValue=" + priceValue + ", supplierID=" + supplierID
				    + ", photoLink=" + photoLink + ", light=" + light + "]";
	    }


        public override bool Equals(Object obj)
        {
            var other = obj as GemID;
            if (other == null) return false;
            if (obj == this) return true;

            if (certificate == null)
            {
                if (other.certificate != null)
                    return false;
            }
            else if (!certificate.Equals(other.certificate))
                return false;
            if (clarity == null)
            {
                if (other.clarity != null)
                    return false;
            }
            else if (!clarity.Equals(other.clarity))
                return false;
            if (color == null)
            {
                if (other.color != null)
                    return false;
            }
            else if (!color.Equals(other.color))
                return false;
            if (comments == null)
            {
                if (other.comments != null)
                    return false;
            }
            else if (!comments.Equals(other.comments))
                return false;
            if (creationDate == null)
            {
                if (other.creationDate != null)
                    return false;
            }
            else if (!creationDate.Equals(other.creationDate))
                return false;
            if (cut == null)
            {
                if (other.cut != null)
                    return false;
            }
            else if (!cut.Equals(other.cut))
                return false;
            if (enhancement == null)
            {
                if (other.enhancement != null)
                    return false;
            }
            else if (!enhancement.Equals(other.enhancement))
                return false;
            if (light == null)
            {
                if (other.light != null)
                    return false;
            }
            else if (!light.Equals(other.light))
                return false;
            if (mass == null)
            {
                if (other.mass != null)
                    return false;
            }
            else if (!mass.Equals(other.mass))
                return false;
            if (origin == null)
            {
                if (other.origin != null)
                    return false;
            }
            else if (!origin.Equals(other.origin))
                return false;
            if (photoLink == null)
            {
                if (other.photoLink != null)
                    return false;
            }
            else if (!photoLink.Equals(other.photoLink))
                return false;
            if (priceCurrency == null)
            {
                if (other.priceCurrency != null)
                    return false;
            }
            else if (!priceCurrency.Equals(other.priceCurrency))
                return false;
            if (priceValue == null)
            {
                if (other.priceValue != null)
                    return false;
            }
            else if (!priceValue.Equals(other.priceValue))
                return false;
            if (reference == null)
            {
                if (other.reference != null)
                    return false;
            }
            else if (!reference.Equals(other.reference))
                return false;
            if (shape == null)
            {
                if (other.shape != null)
                    return false;
            }
            else if (!shape.Equals(other.shape))
                return false;
            if (sizeX == null)
            {
                if (other.sizeX != null)
                    return false;
            }
            else if (!sizeX.Equals(other.sizeX))
                return false;
            if (sizeY == null)
            {
                if (other.sizeY != null)
                    return false;
            }
            else if (!sizeY.Equals(other.sizeY))
                return false;
            if (sizeZ == null)
            {
                if (other.sizeZ != null)
                    return false;
            }
            else if (!sizeZ.Equals(other.sizeZ))
                return false;
            if (species == null)
            {
                if (other.species != null)
                    return false;
            }
            else if (!species.Equals(other.species))
                return false;
            if (supplierID == null)
            {
                if (other.supplierID != null)
                    return false;
            }
            else if (!supplierID.Equals(other.supplierID))
                return false;
            return true;
        }
    }
}
