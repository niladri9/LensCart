package com.capg.LensKart.profile.entity;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Glass {

	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String brandName;
	 private double price;
	 private String type[];
	 private String powerRange[];
	public Glass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Glass(int id, String brandName, double price, String[] type, String[] powerRange) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.price = price;
		this.type = type;
		this.powerRange = powerRange;
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
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	public String[] getPowerRange() {
		return powerRange;
	}
	public void setPowerRange(String[] powerRange) {
		this.powerRange = powerRange;
	}
	@Override
	public String toString() {
		return "Glass [id=" + id + ", brandName=" + brandName + ", price=" + price + ", type=" + Arrays.toString(type)
				+ ", powerRange=" + Arrays.toString(powerRange) + "]";
	}
	
	 
	 
}