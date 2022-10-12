package com.capg.LensKart.profile.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	 private int flatNumber;
	 private int houseNumber;
	 private String streetName;
	 private String colonyName;
	 private String city;
	 private int pincode;
	 private String state;
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Address(int id, int flatNumber, int houseNumber, String streetName, String colonyName, String city,
			int pincode, String state) {
		super();
		this.id = id;
		this.flatNumber = flatNumber;
		this.houseNumber = houseNumber;
		this.streetName = streetName;
		this.colonyName = colonyName;
		this.city = city;
		this.pincode = pincode;
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}
	public int getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getColonyName() {
		return colonyName;
	}
	public void setColonyName(String colonyName) {
		this.colonyName = colonyName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", flatNumber=" + flatNumber + ", houseNumber=" + houseNumber + ", streetName="
				+ streetName + ", colonyName=" + colonyName + ", city=" + city + ", pincode=" + pincode + ", state="
				+ state + "]";
	}
	
	
}
