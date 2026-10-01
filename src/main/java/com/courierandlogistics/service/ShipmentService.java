package com.courierandlogistics.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.ShipmentDTO;
import com.courierandlogistics.entity.DeliveryAgent;
import com.courierandlogistics.entity.Shipment;
import com.courierandlogistics.entity.TrackingHistory;
import com.courierandlogistics.entity.WareHouse;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.InvalidInputException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.CustomerRepository;
import com.courierandlogistics.repository.DeliveryAgentRepository;
import com.courierandlogistics.repository.ShipmentRepository;
import com.courierandlogistics.repository.TrackingHistoryRepository;
import com.courierandlogistics.repository.WareHouseRepository;


@Service
public class ShipmentService 
{
	public static ShipmentDTO convertShipmentToShipmentDTO(Shipment s) {
		ShipmentDTO sdto = new ShipmentDTO();
		sdto.setDeliveryDate(s.getDeliveryDate());
		sdto.setDestination(s.getDestination());
		sdto.setId(s.getId());
		sdto.setStatus(s.getStatus());
		sdto.setSource(s.getSource());
		sdto.setAmount(s.getPayment().getAmount());
		return sdto;
	}
	
	
	
	@Autowired
	private ShipmentRepository shipmentRepo;
	
	@Autowired
	private DeliveryAgentRepository deliveryAgentRepo;
	
	@Autowired
	private WareHouseRepository wareHouseRepo;
	
	@Autowired
	private TrackingHistoryRepository trackingHistoryRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	public ResponseStructure<ShipmentDTO> createShipmentInDatabase(Shipment s){
		if(s.getTrackingHistory() == null || s.getTrackingHistory().isEmpty()) {
			throw new  InvalidInputException("Tracking History Empty");
		}
		if(s.getCustomer() == null || customerRepo.findById(s.getCustomer().getId()).isEmpty()) {
			throw new InvalidInputException("Invalid Customer... Check customer");
		}
		if(s.getdeliveryAgent() == null || deliveryAgentRepo.findById(s.getdeliveryAgent().getId()).isEmpty()) {
			throw new InvalidInputException("Invalid DeliveryAgent... Check Delivery Agent");
		}
		if(deliveryAgentRepo.findById(s.getdeliveryAgent().getId()).get().getAvailability() == false) {
			throw new InvalidInputException("Delivery agent with id : "+s.getdeliveryAgent().getId()+"is currently unavailabe. ");
		}
		if(s.getWareHouse() == null || wareHouseRepo.findById(s.getWareHouse().getId()).isEmpty()) {
			throw new InvalidInputException("Invalid warehouse... check warehouse");
		}
		if(s.getPayment() == null) {
			throw new InvalidInputException("Payment Empty");
		}
		if(s.getPackageEntity() == null) {
			throw new InvalidInputException("Package Entity empty...");
		}
			for(TrackingHistory t:s.getTrackingHistory()) {
				t.setShipment(s);
			}
		//fragile based price 100 extra
		if(s.getPackageEntity().isFragile() == true) {
			double currentPrice = s.getPayment().getAmount();
			s.getPayment().setAmount(currentPrice+100);
		}
		
		//weight based price, for every kg, 10 extra
		if(s.getWeight()>1) {
			int w=(int)Math.ceil(s.getWeight());
			double totalAmount = s.getPayment().getAmount()+w*10;
			s.getPayment().setAmount(totalAmount);
		}
		
		//distance based price for every 100km, 50 extra.
		if(s.getDistance()>100) {
			double totalPrice = s.getPayment().getAmount()+(s.getDistance()/100)*50;
			s.getPayment().setAmount(totalPrice);
		}
		
		Shipment savedShipment = shipmentRepo.save(s);
		ShipmentDTO sdto = convertShipmentToShipmentDTO(savedShipment);
		
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(sdto);
		res.setMessage("Records added to database...");
		res.setStatusCode(HttpStatus.CREATED.value());
		
		return res;		
	}
	

	public ResponseStructure<List<ShipmentDTO>> getAllShipmentsFromDatabase(){
		List<Shipment> li = shipmentRepo.findAll();
		ArrayList<ShipmentDTO> convertedArray = new ArrayList<>();
		for(Shipment s : li) {
			convertedArray.add(convertShipmentToShipmentDTO(s));
		}
		ResponseStructure<List<ShipmentDTO>> res = new ResponseStructure<List<ShipmentDTO>>();
		res.setData(convertedArray);
		res.setMessage(li.isEmpty()+" records found in database");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<ShipmentDTO> getShipmentByIdFromDatabase(int id){
		Optional<Shipment> opt = shipmentRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record does not exist with shipment id :"+id+" in database");
		}
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(ShipmentService.convertShipmentToShipmentDTO(opt.get()));
		res.setMessage("Record found in database");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<ShipmentDTO> getShipmentByTrackingNumberFromDatabase(String tn){
		Optional<Shipment> opt = shipmentRepo.findShipmentByTrackingNumber(tn);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record doesn't exist with shipment tracking number :"+tn+" in database");
		}
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(ShipmentService.convertShipmentToShipmentDTO(opt.get()));
		res.setMessage("Record found in database");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<ShipmentDTO> updateDeliveryAgentForShipmentInDatabase(Shipment s){
		if(s.getId() == null) {
			throw new IdNotFoundException("Shipment Id must be passed to assign delivery  agent for shipment .");
		}
		if(s.getdeliveryAgent().getId() == null) {
			throw new IdNotFoundException("delivery agent Id must be passed to update in shipment details .");
		}
		Optional<Shipment> opt = shipmentRepo.findById(s.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record doesn't exist with shipment id :"+s.getId()+" in database");
		}
		Shipment fetchedShipment = opt.get();
		
		Optional<DeliveryAgent> opt2 = deliveryAgentRepo.findById(s.getdeliveryAgent().getId());
		if(opt2.isEmpty()) {
			throw new NoRecordFoundException("Record doesn't exist with Delivery agent id :"+s.getDeliveryAgent().getId()+" in database");
		}
		DeliveryAgent fetchedDeliveryAgent = opt2.get();
		if(fetchedDeliveryAgent.getAvailability() == false) {
			throw new InvalidInputException("Delivery agent with id:"+opt2.get().getId()+" is currently unavialbale . ");
		}
		fetchedShipment.setDeliveryAgent(fetchedDeliveryAgent);
		
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(convertShipmentToShipmentDTO(shipmentRepo.save(fetchedShipment)));
		res.setMessage("Record updated in database...");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<ShipmentDTO> updateWarehouseForShipmentInDatabase(Shipment s){
		if(s.getId() == null) {
			throw new IdNotFoundException("Id must be passed to update shipment details . ");
		}
		Optional<Shipment> opt1 = shipmentRepo.findById(s.getId());
		if(opt1.isEmpty()) {
			throw new NoRecordFoundException("Record doesn't exist with shipment id :"+s.getId()+" in database");
		}
		
		Shipment fetchedShipment = opt1.get();
		
		if(s.getWareHouse().getId() == null) {
			throw new IdNotFoundException("Warehouse Id must be passed to update in shipment. ");
		}
		Optional<WareHouse> opt2 = wareHouseRepo.findById(s.getWareHouse().getId());
		if(opt2.isEmpty()) {
			throw new NoRecordFoundException("Record doesn't exist with WareHouse id :"+s.getWareHouse().getId()+" in database");
		}
		
		WareHouse w = opt2.get();
		fetchedShipment.setWareHouse(w);
		
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(convertShipmentToShipmentDTO(shipmentRepo.save(fetchedShipment)));
		res.setMessage("Recoed updated in database...");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<ShipmentDTO> updateShipmentStatusInDatabase(Shipment s){
		if(s.getId() == null) {
			throw new IdNotFoundException("Id must be passed to update shipment details . ");
		}
		if(s.getStatus() == null) {
			throw new InvalidInputException("shipment statis must be passes along with shipment id");
		}
		if(s.getTrackingHistory() == null || s.getTrackingHistory().size() == 0) {
			throw new InvalidInputException("shipment tracking history must be passes along with shipment id and status");
		}
		Optional<Shipment> opt = shipmentRepo.findById(s.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record doesn't exist with shipment id :"+s.getId()+" in database");
		}
		Shipment fetchedShipment = opt.get();
		for(TrackingHistory th : s.getTrackingHistory()) {
			th.setShipment(fetchedShipment);
		}
		fetchedShipment.setStatus(s.getStatus());
		fetchedShipment.getTrackingHistory().addAll(s.getTrackingHistory());
		fetchedShipment.getPayment().setPaymentStatus(s.getPayment().getPaymentStatus());
		
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(convertShipmentToShipmentDTO(shipmentRepo.save(fetchedShipment)));
		res.setMessage("Record updated in database...");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<ShipmentDTO> deleteShipmentByIdFromDatabase(int id){
		if(shipmentRepo.findById(id).isEmpty()) {
			throw new InvalidInputException("shipment with id : " + id + " doesnt exist in database .");
		}
		List<TrackingHistory> li = trackingHistoryRepo.findByShipmentId(id);
		if(li.size() != 0) {
			for(TrackingHistory th : li) {
				th.setShipment(null);
			}
		}
		shipmentRepo.deleteById(id);
		ResponseStructure<ShipmentDTO> res = new ResponseStructure<>();
		res.setData(null);
		res.setMessage("Record with shipment id :"+id+", deleted from database...");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<List<ShipmentDTO>> getAllShipmentOfCustomerFromDatabase(int customerId){
		List<Shipment> li = shipmentRepo.findShipmentByCustomerId(customerId);
		if(li.size() == 0) {
			throw new NoRecordFoundException("No shipments exist for customer id : "+ customerId+" in database .");
		}
		ArrayList<ShipmentDTO> convertedArray = new ArrayList<>();
		for(Shipment s : li) {
			convertedArray.add(convertShipmentToShipmentDTO(s));
		}
		ResponseStructure<List<ShipmentDTO>> res = new ResponseStructure<List<ShipmentDTO>>();
		res.setData(convertedArray);
		res.setMessage(li.size()+" records found in database");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	public ResponseStructure<List<ShipmentDTO>> getAllShipmentOfWareHouseFromDatabase(int wareHouseId){
		List<Shipment> li = shipmentRepo.findShipmentByWareHouse(wareHouseId);
		if(li.size() == 0) {
			throw new NoRecordFoundException("No shipments exist for warehouse id : "+ wareHouseId+" in database .");
		}
		ArrayList<ShipmentDTO> concertedArray = new ArrayList<>();
		for(Shipment s : li) {
			concertedArray.add(convertShipmentToShipmentDTO(s));
		}
		ResponseStructure<List<ShipmentDTO>> res = new ResponseStructure<List<ShipmentDTO>>();
		res.setData(concertedArray);
		res.setMessage(li.size()+" records found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<ShipmentDTO>> getAllShipmentOfDeliveryAgentFromDatabase(int id){
		List<Shipment> li = shipmentRepo.findShipmentByDeliveryAgent(id);
		if(li.size() == 0) {
			throw new NoRecordFoundException("No shipments exist for Delivery agent id : "+ id+" in database .");
		}
		ArrayList<ShipmentDTO> convertedArray = new ArrayList<>();
		for(Shipment s : li) {
			convertedArray.add(convertShipmentToShipmentDTO(s));
		}
		ResponseStructure<List<ShipmentDTO>> res = new ResponseStructure<List<ShipmentDTO>>();
		res.setData(convertedArray);
		res.setMessage(li.size()+" records found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<ShipmentDTO>> getAllShipmentBySourceAndDestinationFromDatabase(String source, String dest){
		List<Shipment> li = shipmentRepo.findShipmentsBySourceAndDestination(source, dest);
		if(li.size() == 0) {
			throw new NoRecordFoundException("No shipments exist from source : "+ source +" to "+dest+" in database .");
		}
		ArrayList<ShipmentDTO> convertedArray = new ArrayList<>();
		for(Shipment s : li) {
			convertedArray.add(convertShipmentToShipmentDTO(s));
		}
		ResponseStructure<List<ShipmentDTO>>  res = new ResponseStructure<List<ShipmentDTO>>();
		res.setData(convertedArray);
		res.setMessage(li.size()+" records found in databse...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<ShipmentDTO>> getSHipmentByDeliveryDateFromDatabse(LocalDate deliveryDate){
		List<Shipment> li = shipmentRepo.findShipmentsByDeliveryDate(deliveryDate);
		if(li.size() == 0) {
			throw new NoRecordFoundException("No shipments exist for delivery date : "+deliveryDate+" in database.");
		}
		ArrayList<ShipmentDTO> convertedArray = new ArrayList<>();
		for(Shipment s : li) {
			convertedArray.add(convertShipmentToShipmentDTO(s));
		}
		ResponseStructure<List<ShipmentDTO>> res = new ResponseStructure<List<ShipmentDTO>>();
		res.setData(convertedArray);
		res.setMessage(li.size()+" records found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Page<ShipmentDTO>> getShipmentByPaginationAndSorting(int pn, int ps, String field){
		
		Page<Shipment> pages = shipmentRepo.findAll(PageRequest.of(pn, ps, Sort.by(field).ascending()));
		
		Page<ShipmentDTO> dtoPage = pages.map(s ->{
			ShipmentDTO dto = new ShipmentDTO();
			dto.setId(s.getId());
			dto.setSource(s.getSource());
			dto.setDestination(s.getDestination());
			dto.setDeliveryDate(s.getDeliveryDate());
			dto.setStatus(s.getStatus());
			dto.setAmount(s.getPayment().getAmount());
			return dto;
		});
		ResponseStructure<Page<ShipmentDTO>> res = new ResponseStructure<>();
		res.setData(dtoPage);
		res.setMessage(dtoPage.getNumberOfElements() + " records found ");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
}
