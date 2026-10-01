package com.courierandlogistics.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.courierandlogistics.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer>
{
	@Query("select s from Shipment s where s.trackingNumber=?1")
	Optional<Shipment> findShipmentByTrackingNumber(@Param("trackingNumber") String tn);
	
	@Query("select s from Shipment s where s.customer.id=?1")
	List<Shipment> findShipmentByCustomerId(int id); //findByCustomer()
	
	@Query("select s from Shipment s where s.deliveryAgent.id=?1")
	List<Shipment> findShipmentByDeliveryAgent(int id);  //findByDeliveryAgent()
	
	@Query("select s from Shipment s where s.wareHouse.id=?1")
	List<Shipment> findShipmentByWareHouse(int id);   //findByWareHouse()
	
	@Query("select s from Shipment s where s.deliveryDate=?1")
	List<Shipment> findShipmentsByDeliveryDate(LocalDate deliveryDate);
	
	@Query("select s from Shipment s where s.source=?1 and s.destination=?2")
	List<Shipment> findShipmentsBySourceAndDestination(String source , String destination);
}
