package com.courierandlogistics.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.courierandlogistics.dto.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Shipment 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String trackingNumber;
    private String source;
    private String destination;
    private double weight;
    @CreationTimestamp
    private LocalDateTime shipmentDateTime;
    private LocalDate deliveryDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int distance;
    
    @ManyToOne
	@JoinColumn(name="deliveryAgent_id" , nullable = true)
    @JsonIgnoreProperties({"shipment"})
    private DeliveryAgent deliveryAgent;
    
    @ManyToOne
	@JoinColumn(name = "customer_id",nullable = true)
    @JsonIgnoreProperties({"shipment"})
    private Customer customer;
    
    @ManyToOne
	@JoinColumn(name = "wareHouse_id",nullable = true)
    @JsonIgnoreProperties({"shipment"})
    private WareHouse wareHouse;
    
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="payment_id",nullable = true)
    private Payment payment;
    
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "packageEntity_id",nullable = true)
    private PackageEntity packageEntity;
    
    @OneToMany(mappedBy = "shipment" , cascade = CascadeType.ALL)
	private List<TrackingHistory> trackingHistory = new ArrayList<>();
    
    
    // getters and setters
    
    

    
	public Integer getId() {
		return id;
	}
	public DeliveryAgent getDeliveryAgent() {
		return deliveryAgent;
	}
	public void setDeliveryAgent(DeliveryAgent deliveryAgent) {
		this.deliveryAgent = deliveryAgent;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public LocalDateTime getShipmentDateTime() {
		return shipmentDateTime;
	}
	public void setShipmentDateTime(LocalDateTime shipmentDateTime) {
		this.shipmentDateTime = shipmentDateTime;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public DeliveryAgent getdeliveryAgent() {
		return deliveryAgent;
	}
	public void setdeliveryAgent(DeliveryAgent agent) {
		this.deliveryAgent = agent;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public WareHouse getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(WareHouse wareHous) {
		this.wareHouse = wareHous;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public PackageEntity getPackageEntity() {
		return packageEntity;
	}
	public void setPackageEntity(PackageEntity packageEntity) {
		this.packageEntity = packageEntity;
	}
	public List<TrackingHistory> getTrackingHistory() {
		return trackingHistory;
	}
	public void setTrackingHistory(List<TrackingHistory> trackingHistory) {
		this.trackingHistory = trackingHistory;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getDistance() {
		return distance;
	}
	
	

    
    
}