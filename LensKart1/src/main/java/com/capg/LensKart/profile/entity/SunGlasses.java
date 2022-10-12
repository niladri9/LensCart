package com.capg.LensKart.profile.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SunGlasses {

	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 
	private String brandName;
	 private double price;
	 private String frameColor;
	 private String frameShape;
	 private String glassColor;
	 private String weight;
	public SunGlasses() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SunGlasses(int id, String brandName, double price, String frameColor, String frameShape, String glassColor,
			String weight) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.price = price;
		this.frameColor = frameColor;
		this.frameShape = frameShape;
		this.glassColor = glassColor;
		this.weight = weight;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getFrameColor() {
		return frameColor;
	}
	public void setFrameColor(String frameColor) {
		this.frameColor = frameColor;
	}
	public String getFrameShape() {
		return frameShape;
	}
	public void setFrameShape(String frameShape) {
		this.frameShape = frameShape;
	}
	public String getGlassColor() {
		return glassColor;
	}
	public void setGlassColor(String glassColor) {
		this.glassColor = glassColor;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "SunGlasses [id=" + id + ", brandName=" + brandName + ", price=" + price + ", frameColor=" + frameColor
				+ ", frameShape=" + frameShape + ", glassColor=" + glassColor + ", weight=" + weight + "]";
	}
	
	
	 
	 
	 
}