package com.capg.LensKart.profile.entity;


import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Frame {

	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 private String brandName;
	 private String color;
	 private String description;
	 private double price;
	 private String shape[];
	 private String size[];
	public Frame() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Frame(int id, String brandName, String color, String description, double price, String[] shape,
			String[] size) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.color = color;
		this.description = description;
		this.price = price;
		this.shape = shape;
		this.size = size;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String[] getShape() {
		return shape;
	}
	public void setShape(String[] shape) {
		this.shape = shape;
	}
	public String[] getSize() {
		return size;
	}
	public void setSize(String[] size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "Frame [id=" + id + ", brandName=" + brandName + ", color=" + color + ", description=" + description
				+ ", price=" + price + ", shape=" + Arrays.toString(shape) + ", size=" + Arrays.toString(size) + "]";
	}
	
	
	 
}
	 