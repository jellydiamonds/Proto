package com.jelly.jellyDiamonds.ejb.beans.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "Gem_ID" )
public class Gem implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long    id;
    @Column( name = "creation_date" )
    private Date    creationDate;
    @Column
    private String  reference;
    @Column
    private Integer species;
    @Column
    private String  color;
    @Column
    private Integer shape;
    @Column
    private Integer cut;
    @Column
    private Double  mass;
    @Column( name = "size_x" )
    private Float   sizeX;
    @Column( name = "size_y" )
    private Float   sizeY;
    @Column( name = "size_z" )
    private Float   sizeZ;
    @Column
    private Integer clarity;
    @Column
    private Integer enhancement;
    @Column
    private Integer origin;
    @Column
    private Integer certificate;
    @Column
    private String  comments;
    @Column( name = "price_currency" )
    private Integer priceCurrency;
    @Column( name = "price_value" )
    private Double  priceValue;
    @Column( name = "supplier_id" )
    private Long    supplierID;
    @Column( name = "photo_link" )
    private String  photoLink;
    @Column
    private Integer light;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference( String reference ) {
        this.reference = reference;
    }

    public Integer getSpecies() {
        return species;
    }

    public void setSpecies( Integer species ) {
        this.species = species;
    }

    public String getColor() {
        return color;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    public Integer getShape() {
        return shape;
    }

    public void setShape( Integer shape ) {
        this.shape = shape;
    }

    public Integer getCut() {
        return cut;
    }

    public void setCut( Integer cut ) {
        this.cut = cut;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass( Double mass ) {
        this.mass = mass;
    }

    public Float getSizeX() {
        return sizeX;
    }

    public void setSizeX( Float sizeX ) {
        this.sizeX = sizeX;
    }

    public Float getSizeY() {
        return sizeY;
    }

    public void setSizeY( Float sizeY ) {
        this.sizeY = sizeY;
    }

    public Float getSizeZ() {
        return sizeZ;
    }

    public void setSizeZ( Float sizeZ ) {
        this.sizeZ = sizeZ;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity( Integer clarity ) {
        this.clarity = clarity;
    }

    public Integer getEnhancement() {
        return enhancement;
    }

    public void setEnhancement( Integer enhancement ) {
        this.enhancement = enhancement;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin( Integer origin ) {
        this.origin = origin;
    }

    public Integer getCertificate() {
        return certificate;
    }

    public void setCertificate( Integer certificate ) {
        this.certificate = certificate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments( String comments ) {
        this.comments = comments;
    }

    public Integer getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency( Integer priceCurrency ) {
        this.priceCurrency = priceCurrency;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue( Double priceValue ) {
        this.priceValue = priceValue;
    }

    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID( Long supplierID ) {
        this.supplierID = supplierID;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink( String photoLink ) {
        this.photoLink = photoLink;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight( Integer light ) {
        this.light = light;
    }

    public String toString() {
        return "gem #" + this.id + ", type: " + this.species + ", color: " + this.color + " ..... (20 parameters)";
    }
}