package com.capg.LensKart.profile.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order1 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	private LocalDateTime orderDate;
	
	private int customerId;
	
	private double amountPaid;
	
	private String modeOfPayment;
	
	private String orderStatus;

	public Order1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order1(int orderId, LocalDateTime orderDate, int customerId, double amountPaid, String modeOfPayment,
			String orderStatus) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.customerId = customerId;
		this.amountPaid = amountPaid;
		this.modeOfPayment = modeOfPayment;
		this.orderStatus = orderStatus;
	}

	public Order1(LocalDateTime orderDate, int customerId, double amountPaid, String modeOfPayment,
			String orderStatus) {
		super();
		this.orderDate = orderDate;
		this.customerId = customerId;
		this.amountPaid = amountPaid;
		this.modeOfPayment = modeOfPayment;
		this.orderStatus = orderStatus;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "Order1 [orderId=" + orderId + ", orderDate=" + orderDate + ", customerId=" + customerId
				+ ", amountPaid=" + amountPaid + ", modeOfPayment=" + modeOfPayment + ", orderStatus=" + orderStatus
				+ "]";
	}
	
	

}
