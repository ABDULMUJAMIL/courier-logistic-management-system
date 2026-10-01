package com.courierandlogistics.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.courierandlogistics.dto.DeliveryAgentDTO;
import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.entity.DeliveryAgent;
import com.courierandlogistics.service.DeliveryAgentService;

@RestController
@RequestMapping("/deliveryAgent")
public class DeliveryAgentController 
{
	@Autowired
	private DeliveryAgentService deliveryAgentService;

	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> createDeliveryAgent(@RequestBody DeliveryAgent agent){
		return new ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.createDeliveryAgentInDatabase(agent), HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<DeliveryAgentDTO>>> getAllAgents(){
		return new ResponseEntity<ResponseStructure<List<DeliveryAgentDTO>>>(deliveryAgentService.getAllDeliveryAgentFromDatabase(),HttpStatus.CREATED);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> getAgentById(@PathVariable int id) {
		return new ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.getDeliveryAgentById(id),HttpStatus.FOUND);
	}
	
	@GetMapping("/vehicleNumber/{vehicleNumber}")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> getAgentByVehicleNumber(@PathVariable("vehicleNumber") String vn){
		return new ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.getDeliveyAgentbyVehicleNumber(vn),HttpStatus.FOUND);
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> getAgentByContact(@PathVariable long contact){
		return new  ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.getDeliveryAgentByContact(contact), HttpStatus.FOUND);
	}
	
	@GetMapping("/rating/{rating}")
	public ResponseEntity<ResponseStructure<List<DeliveryAgentDTO>>> getAgentByRatingGreterThan(@PathVariable double rating){
		return new ResponseEntity<ResponseStructure<List<DeliveryAgentDTO>>>(deliveryAgentService.getDeliveryAgentByRatingGreaterThen(rating),HttpStatus.FOUND);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> updateDEliveryAgent(@RequestBody DeliveryAgent da){
		return new ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.updateDeliveryAgentInDatabase(da), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> deleteAgentById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.deleteDeliveryAgentFromDatabaseById(id),HttpStatus.FOUND);
	}
	
	@PatchMapping("/updateAgentAvailability/{id}/{status}")
	public ResponseEntity<ResponseStructure<DeliveryAgentDTO>> updateAvailability(@PathVariable int id, @PathVariable("status") boolean availabilityStatus){
		return new ResponseEntity<ResponseStructure<DeliveryAgentDTO>>(deliveryAgentService.updateAvailability(id, availabilityStatus), HttpStatus.OK);
	}
	
	@GetMapping("/sortBy/{field}")
	public ResponseEntity<ResponseStructure<List<DeliveryAgentDTO>>> getDeliveryAgentsInSortedOrder(@PathVariable String field){
		return new ResponseEntity<ResponseStructure<List<DeliveryAgentDTO>>>(deliveryAgentService.getDeliveryAgentsInDescendingOrder(field), HttpStatus.FOUND);
	}
	
}
