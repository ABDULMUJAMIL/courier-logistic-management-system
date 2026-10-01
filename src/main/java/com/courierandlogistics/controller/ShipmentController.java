package com.courierandlogistics.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.ShipmentDTO;
import com.courierandlogistics.entity.Shipment;
import com.courierandlogistics.service.DeliveryAgentService;
import com.courierandlogistics.service.ShipmentService;

@RestController
@RequestMapping("/shipment")
public class ShipmentController 
{
	@Autowired
	private ShipmentService shipmentService;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> createShipment(@RequestBody Shipment ship){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.createShipmentInDatabase(ship), HttpStatus.CREATED);
	}
	@GetMapping("/")
	public ResponseEntity<ResponseStructure<List<ShipmentDTO>>> getAllShipments(){
		return new ResponseEntity<ResponseStructure<List<ShipmentDTO>>>(shipmentService.getAllShipmentsFromDatabase(),HttpStatus.FOUND);
	}
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> getShipmentById(@PathVariable("id") int shipmentId){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.getShipmentByIdFromDatabase(shipmentId),HttpStatus.FOUND);
	}
	@GetMapping("/trackingNumber/{trackingNumber}")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> getShipmentByTrackingNumber(@PathVariable("trackingNumber") String trackingNo){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.getShipmentByTrackingNumberFromDatabase(trackingNo),HttpStatus.FOUND);
	}
	@PatchMapping("/updateDeliveryAgent")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> updateDeliveryAgentForShipment(@RequestBody Shipment s){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.updateDeliveryAgentForShipmentInDatabase(s),HttpStatus.OK);
	}
	@PatchMapping("/updateWarehose")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> updatesWarehouseForShipment(@RequestBody Shipment s){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.updateWarehouseForShipmentInDatabase(s),HttpStatus.OK);
	}
	@PatchMapping("/shipmentStatus")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> updateShipmentStatusForShipment(@RequestBody Shipment s){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.updateShipmentStatusInDatabase(s),HttpStatus.OK);
	}
	@DeleteMapping("/deleteShipment/{id}")
	public ResponseEntity<ResponseStructure<ShipmentDTO>> deleteShipmentById(@PathVariable("id") int shipmentId){
		return new ResponseEntity<ResponseStructure<ShipmentDTO>>(shipmentService.deleteShipmentByIdFromDatabase(shipmentId),HttpStatus.OK);
	}
	@GetMapping("/shipmentsOfCustomer/{id}")
	public ResponseEntity<ResponseStructure<List<ShipmentDTO>>> getAllShipmentsOfCutomerById(@PathVariable("id") int custId){
		return new ResponseEntity<ResponseStructure<List<ShipmentDTO>>>(shipmentService.getAllShipmentOfCustomerFromDatabase(custId),HttpStatus.FOUND);
	}
	@GetMapping("/shipmentsOfWarehouse/{id}")
	public ResponseEntity<ResponseStructure<List<ShipmentDTO>>> getAllShipmentsOfWarehouseById(@PathVariable("id") int warehouseId){
		return new ResponseEntity<ResponseStructure<List<ShipmentDTO>>>(shipmentService.getAllShipmentOfWareHouseFromDatabase(warehouseId),HttpStatus.FOUND);
	}
	@GetMapping("/shipmentsOfDeliveryAgents/{id}")
	public ResponseEntity<ResponseStructure<List<ShipmentDTO>>> getAllShipmentsOfDeliveryAgentsById(@PathVariable("id") int deliveryAgentId){
		return new ResponseEntity<ResponseStructure<List<ShipmentDTO>>>(shipmentService.getAllShipmentOfDeliveryAgentFromDatabase(deliveryAgentId),HttpStatus.FOUND);
	}
	@GetMapping("/shipmentsBySourceAndDestination/{source}/{destination}")
	public ResponseEntity<ResponseStructure<List<ShipmentDTO>>> getAllShipmentsBySourceAndDestination(@PathVariable("source") String source,@PathVariable("destination") String destinatio){
		return new ResponseEntity<ResponseStructure<List<ShipmentDTO>>>(shipmentService.getAllShipmentBySourceAndDestinationFromDatabase(source, destinatio),HttpStatus.FOUND);
	}
	@GetMapping("/shipmentsByDeliveryDate/{deliveryDate}")
	public ResponseEntity<ResponseStructure<List<ShipmentDTO>>> getAllShipmentsByDaeliveryDate(@PathVariable("deliveryDate") LocalDate deliveryDate){
		return new ResponseEntity<ResponseStructure<List<ShipmentDTO>>>(shipmentService.getSHipmentByDeliveryDateFromDatabse(deliveryDate),HttpStatus.FOUND);
	}
	@GetMapping("/shipmentsByPagination/{pn}/{ps}/{field}")
	public ResponseEntity<ResponseStructure<Page<ShipmentDTO>>> getAllShipmentsByPagination(
	        @PathVariable Integer pn,
	        @PathVariable Integer ps,
	        @PathVariable String field) {

	    return ResponseEntity.ok(
	        shipmentService.getShipmentByPaginationAndSorting(pn, ps, field)
	    );
	}
	
}
