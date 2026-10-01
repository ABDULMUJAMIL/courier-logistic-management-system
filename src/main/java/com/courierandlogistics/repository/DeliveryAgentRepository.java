package com.courierandlogistics.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.courierandlogistics.entity.DeliveryAgent;


public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Integer>
{
	@Query("select da from DeliveryAgent da where da.vehicleNumber=?1")
	Optional<DeliveryAgent> findByVehicleNumber(String vehicleNumber);
	
	@Query("select da from DeliveryAgent da where da.contact=?1")
	Optional<DeliveryAgent> findByContactNumber(long contact);
	
	@Query("select da from DeliveryAgent da where da.rating>?1")
	List<DeliveryAgent> findByRatingGreaterThan(double rating);
	
	 List<DeliveryAgent> findAllByOrderByRatingDesc();
	 
	 @Modifying
	 @Transactional
     @Query("update DeliveryAgent d set d.availabilityStatus=?1 where d.id=?2")
	 int updateAvailability(boolean status, Integer id);
	
	
	

}
