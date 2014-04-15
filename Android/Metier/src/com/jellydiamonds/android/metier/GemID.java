package com.jellydiamonds.android.metier;

import java.io.File;
import java.io.Serializable;

public class GemID implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5138810903060394612L;

    private String  reference = null;
    private String  color = null;
    private Float  mass = null;
    private Float   sizeX = null;
    private Float   sizeY = null;
    private Float   sizeZ = null;
    private String  comments = null;
    private Float  priceValue = null;
    private Long    supplierID = null;
    
	private Long    creationDate = null;
    private File    photoLink = null;
    
    private GemSpecies species = null;
    private GemShape shape = null;
    private GemCut cut = null;
    private GemClarity clarity = null;
    private GemLight light = null;
    private GemEnhancement enhancement = null;
    private GemCertificate certificate = null;
    private GemOrigin origin = null;
    private GemCurrency priceCurrency = null;
    
    private GemStatus currentStatus = null;
    
    public GemID()
    {
    	// Initial status is Local
    	this.currentStatus = new GemStatusLocal();
    	// Set creation date
    	this.creationDate = System.currentTimeMillis();
    	
    	this.origin = GemOrigin.EMPTY;
    	this.shape = GemShape.EMPTY;
    	this.cut = GemCut.EMPTY;
    	this.clarity = GemClarity.EMPTY;
    	this.light = GemLight.EMPTY;
    	this.enhancement = GemEnhancement.EMPTY;
    	this.certificate = GemCertificate.EMPTY;
    	this.species = GemSpecies.EMPTY;
    	this.supplierID = 0L;
    	this.priceValue = 0.0f;
    	this.priceCurrency = GemCurrency.EMPTY;
    	this.comments = "";
    	this.sizeX = 0.0f;
    	this.sizeY = 0.0f;
    	this.sizeZ = 0.0f;
    	this.mass = 0.0f;
    	this.color = "";
    	this.reference = "";
    	
    }
    
    public  String getTitleDisplay()
    {
    	if( this.reference.equals("") )
    		return "No Reference";
    	
    	return new String("Ref : " + this.reference );
    }
    
    public GemStatus getCurrentStatus() {
		return currentStatus;
	}
    
	public void setCurrentStatus(GemStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public Long getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public GemSpecies getSpecies() {
		return species;
	}
	public void setSpecies(GemSpecies species) {
		this.species = species;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public GemShape getShape() {
		return shape;
	}
	public void setShape(GemShape shape) {
		this.shape = shape;
	}
	public GemCut getCut() {
		return cut;
	}
	public void setCut(GemCut cut) {
		this.cut = cut;
	}
	public Float getMass() {
		return mass;
	}
	public void setMass(Float mass) {
		this.mass = mass;
	}
	public Float getSizeX() {
		return sizeX;
	}
	public void setSizeX(Float sizeX) {
		this.sizeX = sizeX;
	}
	public Float getSizeY() {
		return sizeY;
	}
	public void setSizeY(Float sizeY) {
		this.sizeY = sizeY;
	}
	public Float getSizeZ() {
		return sizeZ;
	}
	public void setSizeZ(Float sizeZ) {
		this.sizeZ = sizeZ;
	}
	public GemClarity getClarity() {
		return clarity;
	}
	public void setClarity(GemClarity clarity) {
		this.clarity = clarity;
	}
	public GemEnhancement getEnhancement() {
		return enhancement;
	}
	public void setEnhancement(GemEnhancement enhancement) {
		this.enhancement = enhancement;
	}
	public GemOrigin getOrigin() {
		return origin;
	}
	public void setOrigin(GemOrigin origin) {
		this.origin = origin;
	}
	public GemCertificate getCertificate() {
		return certificate;
	}
	public void setCertificate(GemCertificate certificate) {
		this.certificate = certificate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public GemCurrency getPriceCurrency() {
		return priceCurrency;
	}
	public void setPriceCurrency(GemCurrency priceCurrency) {
		this.priceCurrency = priceCurrency;
	}
	public Float getPriceValue() {
		return priceValue;
	}
	public void setPriceValue(Float priceValue) {
		this.priceValue = priceValue;
	}
	public Long getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(Long supplierID) {
		this.supplierID = supplierID;
	}
	public File getPhotoLink() {
		return photoLink;
	}
	public void setPhotoLink(File photoLink) {
		
		if ( photoLink.exists() && photoLink.canRead() )
		{
			this.photoLink = photoLink;
		}
		else
		{
			this.photoLink = null;
		}
	}
	public GemLight getLight() {
		return light;
	}
	public void setLight(GemLight light) {
		this.light = light;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((certificate == null) ? 0 : certificate.hashCode());
		result = prime * result + ((clarity == null) ? 0 : clarity.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((cut == null) ? 0 : cut.hashCode());
		result = prime * result
				+ ((enhancement == null) ? 0 : enhancement.hashCode());
		result = prime * result + ((light == null) ? 0 : light.hashCode());
		result = prime * result + ((mass == null) ? 0 : mass.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result
				+ ((photoLink == null) ? 0 : photoLink.hashCode());
		result = prime * result
				+ ((priceCurrency == null) ? 0 : priceCurrency.hashCode());
		result = prime * result
				+ ((priceValue == null) ? 0 : priceValue.hashCode());
		result = prime * result
				+ ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
		result = prime * result + ((sizeX == null) ? 0 : sizeX.hashCode());
		result = prime * result + ((sizeY == null) ? 0 : sizeY.hashCode());
		result = prime * result + ((sizeZ == null) ? 0 : sizeZ.hashCode());
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		result = prime * result
				+ ((supplierID == null) ? 0 : supplierID.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return    "  shape :" + shape + ", cut :" + cut + ", mass : " + mass
				+ ", specie :" + species + ", color :" + color
				+ ", sizeX : " + sizeX + ", sizeY: " + sizeY + ", sizeZ : " + sizeZ
				+ ", clarity : " + clarity + ", enhancement : " + enhancement
				+ ", origin : " + origin + ", certificate : " + certificate
				+ ", comments :" + comments + ", priceCurrency :" + priceCurrency
				+ ", priceValue :" + priceValue + ", supplierID :" + supplierID
				+ ", photoLink :" + photoLink + ", light :" + light + "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GemID other = (GemID) obj;
		if (certificate == null) {
			if (other.certificate != null)
				return false;
		} else if (!certificate.equals(other.certificate))
			return false;
		if (clarity == null) {
			if (other.clarity != null)
				return false;
		} else if (!clarity.equals(other.clarity))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (cut == null) {
			if (other.cut != null)
				return false;
		} else if (!cut.equals(other.cut))
			return false;
		if (enhancement == null) {
			if (other.enhancement != null)
				return false;
		} else if (!enhancement.equals(other.enhancement))
			return false;
		if (light == null) {
			if (other.light != null)
				return false;
		} else if (!light.equals(other.light))
			return false;
		if (mass == null) {
			if (other.mass != null)
				return false;
		} else if (!mass.equals(other.mass))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (photoLink == null) {
			if (other.photoLink != null)
				return false;
		} else if (!photoLink.equals(other.photoLink))
			return false;
		if (priceCurrency == null) {
			if (other.priceCurrency != null)
				return false;
		} else if (!priceCurrency.equals(other.priceCurrency))
			return false;
		if (priceValue == null) {
			if (other.priceValue != null)
				return false;
		} else if (!priceValue.equals(other.priceValue))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (shape == null) {
			if (other.shape != null)
				return false;
		} else if (!shape.equals(other.shape))
			return false;
		if (sizeX == null) {
			if (other.sizeX != null)
				return false;
		} else if (!sizeX.equals(other.sizeX))
			return false;
		if (sizeY == null) {
			if (other.sizeY != null)
				return false;
		} else if (!sizeY.equals(other.sizeY))
			return false;
		if (sizeZ == null) {
			if (other.sizeZ != null)
				return false;
		} else if (!sizeZ.equals(other.sizeZ))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		if (supplierID == null) {
			if (other.supplierID != null)
				return false;
		} else if (!supplierID.equals(other.supplierID))
			return false;
		return true;
	}
	
}
