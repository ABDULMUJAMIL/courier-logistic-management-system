package com.courierandlogistics.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class DeliveryAgent 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(unique = true)
    private long contact;
	
	@Column(unique = true)
	private String vehicleNumber;
	private boolean availabilityStatus;
	private double rating;
	
	
	@OneToMany(mappedBy = "deliveryAgent")
	@JsonIgnore
	private List<Shipment> shipment;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="delivery_agent_id")
	private UserCredentials userCredentials;


	public UserCredentials getUserCredentials() {
		return userCredentials;
	}


	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getContact() {
		return contact;
	}


	public void setContact(long contact) {
		this.contact = contact;
	}


	public String getVehicleNumber() {
		return vehicleNumber;
	}


	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}


	public boolean isAvailabilityStatus() {
		return availabilityStatus;
	}


	public void setAvailabilityStatus(boolean availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public List<Shipment> getShipment() {
		return shipment;
	}


	public void setShipment(List<Shipment> shipment) {
		this.shipment = shipment;
	}


	public Boolean getAvailability() {
		return availabilityStatus;
	}
	
	

}
