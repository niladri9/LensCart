package com.capg.LensKart.profile.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lenses {

	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 
	
	private String brandName;
	 private String shape;
	 private String color;
	 private double price;
	 private String qtyInBox;
	public Lenses() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Lenses(int id, String brandName, String shape, String color, double price, String qtyInBox) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.shape = shape;
		this.color = color;
		this.price = price;
		this.qtyInBox = qtyInBox;
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
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getQtyInBox() {
		return qtyInBox;
	}
	public void setQtyInBox(String qtyInBox) {
		this.qtyInBox = qtyInBox;
	}
	@Override
	public String toString() {
		return "Lenses [id=" + id + ", brandName=" + brandName + ", shape=" + shape + ", color=" + color + ", price="
				+ price + ", qtyInBox=" + qtyInBox + "]";
	}
	
	 
	 
	 
}