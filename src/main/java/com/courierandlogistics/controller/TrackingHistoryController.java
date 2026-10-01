package com.courierandlogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.Status;
import com.courierandlogistics.entity.TrackingHistory;
import com.courierandlogistics.service.TrackingHistoryService;

@RestController
@RequestMapping("/trackingHistory")
public class TrackingHistoryController {

	@Autowired
	private TrackingHistoryService trackingHistoryservice;
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getAllTrackingHistory(){
		return new ResponseEntity<ResponseStructure<List<TrackingHistory>>>(trackingHistoryservice.getAllTrackingHistoryFromDatabase(),HttpStatus.FOUND);
	}
	
	@GetMapping("/trackingNumber/{tn}")
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getAllTrackingHistorybytrackingNumber(@PathVariable String tn){
		return new ResponseEntity<ResponseStructure<List<TrackingHistory>>>(trackingHistoryservice.getAllTrackingHistoryByTrackingNumberFromDatabase(tn),HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<TrackingHistory>> getTrackingHistoryById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<TrackingHistory>>(trackingHistoryservice.getTrackingHistoryById(id),HttpStatus.FOUND);
	}
	
	@GetMapping("/shipmentId/{shipmentId}")
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getTrackingHistoryByShipmentId(@PathVariable int shipmentId){
		return new ResponseEntity<ResponseStructure<List<TrackingHistory>>>(trackingHistoryservice.getTrackinghistoryByShipmentIdfromdatabse(shipmentId),HttpStatus.FOUND);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getTrackingHistoryByStatus(@PathVariable Status status){
		return new ResponseEntity<ResponseStructure<List<TrackingHistory>>>(trackingHistoryservice.getTrackingHistoryByTrackingStatusFromDatabse(status),HttpStatus.FOUND);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<TrackingHistory>> updateTrackingHistory(@RequestBody TrackingHistory th){
		return new ResponseEntity<ResponseStructure<TrackingHistory>>(trackingHistoryservice.updateTrackingHistoryInDatabase(th),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
}
