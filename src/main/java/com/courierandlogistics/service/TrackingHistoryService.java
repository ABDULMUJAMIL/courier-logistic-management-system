package com.courierandlogistics.service;

import java.util.List;
import java.util.Optional;

import javax.sound.midi.Track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.Status;
import com.courierandlogistics.entity.Payment;
import com.courierandlogistics.entity.TrackingHistory;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.TrackingHistoryRepository;

@Service
public class TrackingHistoryService {

	@Autowired
	private TrackingHistoryRepository trackingHistoryRepo;
	
	public ResponseStructure<List<TrackingHistory>> getAllTrackingHistoryByTrackingNumberFromDatabase(String tn){
		List<TrackingHistory> li = trackingHistoryRepo.getTrackingHistoryByTrackingNumber(tn);
		if(li.size() == 0) {
			throw new NoRecordFoundException("No tracking history record exists for tracking number : " + tn + " in database");
		}
		ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage(li.size() + " Record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<TrackingHistory>> getAllTrackingHistoryFromDatabase(){
		List<TrackingHistory> li = trackingHistoryRepo.findAll();
		if(li.size() == 0) {
			throw new NoRecordFoundException("No tracking history record exists in database");
		}
		ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage(li.size() + " record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<TrackingHistory> getTrackingHistoryById(int id){
		Optional<TrackingHistory> opt = trackingHistoryRepo.findById(id);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No tracking history record exists with id : " + id + "  in database");
		}
		ResponseStructure<TrackingHistory> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Records fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<TrackingHistory>> getTrackinghistoryByShipmentIdfromdatabse(int shipmentId){
		List<TrackingHistory> li = trackingHistoryRepo.findTrackingHistoryByShipmentId(shipmentId);
		if(li.size() == 0 ) {
			throw new NoRecordFoundException(
					"No tracking history record exists for shipment id : " + shipmentId + "  in database");
		}
		ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage("Record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<TrackingHistory>> getTrackingHistoryByTrackingStatusFromDatabse(Status status){
		List<TrackingHistory> li = trackingHistoryRepo.findTrackingHistoryByTrackingStatus(status);
		if(li.size() == 0) {
			throw new NoRecordFoundException(
					"No tracking history record exists for tracking status : " + status + "  in database");
		}
		ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage("Record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<TrackingHistory> updateTrackingHistoryInDatabase(TrackingHistory th){
		if(th.getId() == null ) {
			throw new IdNotFoundException("TrckingHistory id must be passed to update the details...");
		}
		Optional<TrackingHistory> opt = trackingHistoryRepo.findById(th.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException(
					"No record exist with tracking history id : " + th.getId() + " in database . .");
		}
		TrackingHistory fetched = opt.get();
		fetched.setLocation(th.getLocation());
		fetched.setRemarks(th.getRemarks());
		fetched.setStatus(th.getStatus());
		ResponseStructure<TrackingHistory> res = new ResponseStructure<>();
		res.setData(trackingHistoryRepo.save(fetched));
		res.setMessage("Record updated successfully...");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
}
