package com.capg.LensKart.profile.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cart {


	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int catrId;

	private double totalPrice;
	
	private String username;

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(int catrId, double totalPrice, String username) {
		super();
		this.catrId = catrId;
		this.totalPrice = totalPrice;
		this.username = username;
	}

	public int getCatrId() {
		return catrId;
	}

	public void setCatrId(int catrId) {
		this.catrId = catrId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	@Override
	public String toString() {
		return "Cart [catrId=" + catrId + ", totalPrice=" + totalPrice + ", username=" + username + "]";
	}
	
	
}
