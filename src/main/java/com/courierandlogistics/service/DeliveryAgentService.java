package com.courierandlogistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.DeliveryAgentDTO;
import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.Status;
import com.courierandlogistics.dto.WareHouseDTO;
import com.courierandlogistics.entity.DeliveryAgent;
import com.courierandlogistics.entity.Shipment;
import com.courierandlogistics.entity.WareHouse;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.InvalidContactNumberException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.DeliveryAgentRepository;
import com.courierandlogistics.repository.ShipmentRepository;

@Service
public class DeliveryAgentService 
{
	@Autowired
	private DeliveryAgentRepository deliveryAgentRepo;
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	private static DeliveryAgentDTO convertDeliveryAgentToDeliveryAgentDTO(DeliveryAgent d) 
	{
		DeliveryAgentDTO dad = new DeliveryAgentDTO();
		dad.setAgentName(d.getName());
		dad.setAvailability(d.isAvailabilityStatus());
		dad.setContact(d.getContact());
		dad.setRating(d.getRating());
		dad.setVehicleNumber(d.getVehicleNumber());
		dad.setId(d.getId());
		return dad;
	}
	
	public ResponseStructure<DeliveryAgentDTO> createDeliveryAgentInDatabase(DeliveryAgent agent){
		if(String.valueOf(agent.getContact()).length()!=10) {
			throw new InvalidContactNumberException("Invalid Contact Number. Contact number must be 10 digits . .");
		}
		DeliveryAgent savedAgent = deliveryAgentRepo.save(agent);
		ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
		res.setData(convertDeliveryAgentToDeliveryAgentDTO(savedAgent));
		res.setMessage("1 agent is saved in database successfully...");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	
	public ResponseStructure<List<DeliveryAgentDTO>> getAllDeliveryAgentFromDatabase(){
		List<DeliveryAgent> agents = deliveryAgentRepo.findAll();
		if(agents.isEmpty()) {
			throw new NoRecordFoundException("No Delivery AGents exist in the Database...");
		}
		List<DeliveryAgentDTO> dtolist = new ArrayList<>();
		for(DeliveryAgent da : agents) {
			dtolist.add(convertDeliveryAgentToDeliveryAgentDTO(da));
		}
		ResponseStructure<List<DeliveryAgentDTO>> res = new ResponseStructure<>();
		res.setData(dtolist);
		res.setMessage(agents.size()+" delivery agents record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgentDTO> getDeliveryAgentById(int id){
		Optional<DeliveryAgent> opt = deliveryAgentRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("record with  delivery agent id "+id+"not exist in database...");
		}
		DeliveryAgent agent = opt.get();
		ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
		res.setData(convertDeliveryAgentToDeliveryAgentDTO(agent));
		res.setMessage("Record with warehouse id "+id+" found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgentDTO> getDeliveryAgentByContact(long contact){
		Optional<DeliveryAgent> opt = deliveryAgentRepo.findByContactNumber(contact);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with ware house location "+contact+" not exist in database...");
		}
		DeliveryAgent agent = opt.get();
		ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
		res.setData(convertDeliveryAgentToDeliveryAgentDTO(agent));
		res.setMessage("Record with Delivey agent contact "+contact+" found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgentDTO> getDeliveyAgentbyVehicleNumber(String vehicleNumber){
		Optional<DeliveryAgent> opt = deliveryAgentRepo.findByVehicleNumber(vehicleNumber);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with ware house location "+vehicleNumber+" not exist in database...");
		}
		DeliveryAgent agent = opt.get();
		ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
		res.setData(convertDeliveryAgentToDeliveryAgentDTO(agent));
		res.setMessage("Record with Delivey agent contact "+vehicleNumber+" found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<DeliveryAgentDTO>> getDeliveryAgentByRatingGreaterThen(double rating){
		List<DeliveryAgent> agent = deliveryAgentRepo.findByRatingGreaterThan(rating);
		if(agent.isEmpty()) {
			throw new NoRecordFoundException("Record with capacity greater then "+rating+" not exis in the database...");
		}
		List<DeliveryAgentDTO> dtoList = new ArrayList<>();
		for(DeliveryAgent da: agent) {
			dtoList.add(convertDeliveryAgentToDeliveryAgentDTO(da));
		}
		ResponseStructure<List<DeliveryAgentDTO>> res = new ResponseStructure<>();
		res.setData(dtoList);
		res.setMessage(agent.size()+" delivery agent records fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgentDTO> updateDeliveryAgentInDatabase(DeliveryAgent agent){
		if(agent.getId()== null) {
			throw new IdNotFoundException("To update the record, id must be passed...");
		}
		Optional<DeliveryAgent> opt = deliveryAgentRepo.findById(agent.getId());
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Id is Not present with "+agent.getId()+" in the database to update the record...");
		}
		DeliveryAgent fetchedDeliveryAgent = opt.get();
		agent.setUserCredentials(fetchedDeliveryAgent.getUserCredentials());
		DeliveryAgent updateAgent = deliveryAgentRepo.save(agent);
		ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
		res.setData(convertDeliveryAgentToDeliveryAgentDTO(updateAgent));
		res.setMessage("Record is updated successfully for warehouse id : "+updateAgent.getId());
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<DeliveryAgentDTO> deleteDeliveryAgentFromDatabaseById(int id){
		Optional<DeliveryAgent> opt = deliveryAgentRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record not found with DeliveryAgent id : "+id+" in the database");
		}
		List<Shipment> li = shipmentRepository.findShipmentByDeliveryAgent(id);
		boolean isAllDelivered = true;
		if(li.size() != 0) {
			for(Shipment s : li) {
				if(s.getStatus() != Status.Delevered) {
					isAllDelivered = false;
				}
			}
		}
		if(isAllDelivered == false) {
			throw new IdNotFoundException("a shipment is active for that delivery agent id :"+id+", can't be deleted...");
		}
		for(Shipment s:li) {
			s.setWareHouse(null);
		}
		deliveryAgentRepo.deleteById(id);
		
		ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
		res.setData(null);
		res.setMessage("Record is deleted succesfully for delivery agent id :"+id);
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	
	public ResponseStructure<DeliveryAgentDTO> updateAvailability(int id, boolean status) {
	    Optional<DeliveryAgent> opt = deliveryAgentRepo.findById(id);
	    if(opt.isEmpty()) {
	    	throw new NoRecordFoundException("No record found for Delivery agent id: " + id);
	    }
	    
	    DeliveryAgent da=opt.get();
	    da.setAvailabilityStatus(status);

	    DeliveryAgent updatedAgent = deliveryAgentRepo.save(da);

	    ResponseStructure<DeliveryAgentDTO> res = new ResponseStructure<>();
	    res.setData(convertDeliveryAgentToDeliveryAgentDTO(updatedAgent));
	    res.setMessage("Agent Availability updated successfully");
	    res.setStatusCode(HttpStatus.OK.value());
	    return res;
	}
	
	public ResponseStructure<List<DeliveryAgentDTO>> getDeliveryAgentsInDescendingOrder(String field){
		List<DeliveryAgent> agents = deliveryAgentRepo.findAll(Sort.by(field).descending());

	    List<DeliveryAgentDTO> dtoList = agents.stream()
	            .map(a -> {
	                DeliveryAgentDTO dto = new DeliveryAgentDTO();
	                dto.setId(a.getId());
	                dto.setAgentName(a.getName());
	                dto.setContact(a.getContact());
	                dto.setVehicleNumber(a.getVehicleNumber());
	                dto.setAvailability(a.getAvailability());
	                dto.setRating(a.getRating());
	                return dto;
	            })
	            .toList();

	    ResponseStructure<List<DeliveryAgentDTO>> res = new ResponseStructure<>();
	    res.setData(dtoList);
	    res.setMessage(dtoList.size() + " records sorted in descending order successfully . .");
	    res.setStatusCode(HttpStatus.OK.value());
	    return res;
	}


}
