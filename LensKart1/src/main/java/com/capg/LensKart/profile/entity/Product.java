package com.capg.LensKart.profile.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
// @AllArgsConstructor
// @NoArgsConstructor
@Entity
public class Product {
	
	 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	
	
	
	private String category;
	
	@OneToOne(cascade = { CascadeType.ALL })
	private Frame frame;
	
	@OneToOne(cascade = { CascadeType.ALL })
	private Glass glass;
	
	@OneToOne(cascade = { CascadeType.ALL })
	private Lenses lenses;
	
	@OneToOne(cascade = { CascadeType.ALL })
	private SunGlasses sunGlasses;
	
	
	private int stockQuantity;
	
	
	private String productImg;
	
	
	private LocalDateTime arrivalDate;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(int productId, String category, Frame frame, Glass glass, Lenses lenses, SunGlasses sunGlasses,
			int stockQuantity, String productImg, LocalDateTime arrivalDate) {
		super();
		this.productId = productId;
		this.category = category;
		this.frame = frame;
		this.glass = glass;
		this.lenses = lenses;
		this.sunGlasses = sunGlasses;
		this.stockQuantity = stockQuantity;
		this.productImg = productImg;
		this.arrivalDate = arrivalDate;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public Glass getGlass() {
		return glass;
	}

	public void setGlass(Glass glass) {
		this.glass = glass;
	}

	public Lenses getLenses() {
		return lenses;
	}

	public void setLenses(Lenses lenses) {
		this.lenses = lenses;
	}

	public SunGlasses getSunGlasses() {
		return sunGlasses;
	}

	public void setSunGlasses(SunGlasses sunGlasses) {
		this.sunGlasses = sunGlasses;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", category=" + category + ", frame=" + frame + ", glass=" + glass
				+ ", lenses=" + lenses + ", sunGlasses=" + sunGlasses + ", stockQuantity=" + stockQuantity
				+ ", productImg=" + productImg + ", arrivalDate=" + arrivalDate + "]";
	}

	
	

	
	
	
	
	
}
