package com.courierandlogistics.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.courierandlogistics.dto.Status;
import com.courierandlogistics.entity.TrackingHistory;

public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory, Integer>
{
		//@Query("select t from TrackingHistory t where t.shipment.id=?1")
		List<TrackingHistory> findByShipmentId(int id);
		
		@Query("select t from TrackingHistory t where t.shipment.trackingNumber=?1")
		List<TrackingHistory> getTrackingHistoryByTrackingNumber(String TrackingNumber);
		
		@Query("select t from TrackingHistory t where t.status=?1")
		List<TrackingHistory> findTrackingHistoryByTrackingStatus(Status status);
		
		@Query("select t from TrackingHistory t where t.shipment.id=?1")
		List<TrackingHistory> findTrackingHistoryByShipmentId(int shipmentId);

}
