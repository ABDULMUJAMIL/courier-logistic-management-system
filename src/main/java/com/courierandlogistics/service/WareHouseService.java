package com.courierandlogistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.Status;
import com.courierandlogistics.dto.WareHouseDTO;
import com.courierandlogistics.entity.Shipment;
import com.courierandlogistics.entity.WareHouse;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.InvalidContactNumberException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.ShipmentRepository;
import com.courierandlogistics.repository.WareHouseRepository;

@Service
public class WareHouseService 
{
	@Autowired
	private WareHouseRepository wareHouseRepository;
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	
	private static WareHouseDTO convertWareHouseToWareHouseDTO(WareHouse w) 
	{
		WareHouseDTO wd = new WareHouseDTO();
		wd.setCapacity(w.getCapacity());
		wd.setContact(w.getContact());
		wd.setLocation(w.getLocation());
		wd.setWareHouseName(w.getWareHouseName());
		wd.setId(w.getId());
		return wd;
	}
	
	public ResponseStructure<WareHouseDTO> createWareHouseInDatabase(WareHouse ware){
		if(String.valueOf(ware.getContact()).length()!=10) {
			throw new InvalidContactNumberException("Invalid Contact Number. Contact number must be 10 digits . .");
		}
		
		WareHouse savedWareHouse = wareHouseRepository.save(ware);
		ResponseStructure<WareHouseDTO> res = new ResponseStructure<>();
		res.setData(convertWareHouseToWareHouseDTO(savedWareHouse));
		res.setMessage("1 Ware house saved successfully in the database...");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	
	
	public ResponseStructure<List<WareHouseDTO>> getAllWareHousesFromDatabase(){
		List<WareHouse> warehouses = wareHouseRepository.findAll();
		if(warehouses.isEmpty()) {
			throw new NoRecordFoundException("No warehouses exist in the database...");
		}
		List<WareHouseDTO> dtoList = new ArrayList<>();
		for(WareHouse w : warehouses) {
			dtoList.add(convertWareHouseToWareHouseDTO(w));
		}
		ResponseStructure<List<WareHouseDTO>> res = new ResponseStructure<>();
		res.setData(dtoList);
		res.setMessage(warehouses.size()+" warehouses record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<WareHouseDTO> getWareHouseById(int id){
		Optional<WareHouse> opt = wareHouseRepository.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("record with warehouse id "+id+" not exist in database...");
		}
		WareHouse warehouse = opt.get();
		ResponseStructure<WareHouseDTO> res = new ResponseStructure<>();
		res.setData(convertWareHouseToWareHouseDTO(warehouse));
		res.setMessage("Record with warehouse id "+id+" found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<WareHouseDTO> getWareHouseByLocation(String location){
		Optional<WareHouse> opt = wareHouseRepository.findWareHouseByLocation(location);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with ware house location "+location+" not exist in database...");
		}
		WareHouse warehouse = opt.get();
		ResponseStructure<WareHouseDTO> res = new ResponseStructure<>();
		res.setData(convertWareHouseToWareHouseDTO(warehouse));
		res.setMessage("Record with warehouse location "+location+" found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<List<WareHouseDTO>> getWareHouseByCapacityGreaterThen(int capacity){
		List<WareHouse> warehouses = wareHouseRepository.findWareHousesByCapacityGreaterThan(capacity);
		if(warehouses.isEmpty()) {
			throw new NoRecordFoundException("Record with capacity greater then "+capacity+" not exis in the database...");
		}
		List<WareHouseDTO> dtoList = new ArrayList<>();
		for(WareHouse w : warehouses) {
			dtoList.add(convertWareHouseToWareHouseDTO(w));
		}
		ResponseStructure<List<WareHouseDTO>> res = new ResponseStructure<>();
		res.setData(dtoList);
		res.setMessage(warehouses.size()+" ware house records fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<WareHouseDTO> updateWareHouseInTheDatabase(WareHouse house){
		if(house.getId()== null) {
			throw new IdNotFoundException("To update the record, id must be passed...");
		}
		Optional<WareHouse> opt = wareHouseRepository.findById(house.getId());
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Id is Not present with "+house.getId()+" in the database to update the record...");
		}
		WareHouse fetchedWarehouse = opt.get();
		house.setUserCredentials(fetchedWarehouse.getUserCredentials());
		WareHouse updateWareHouse = wareHouseRepository.save(house);
		ResponseStructure<WareHouseDTO> res = new ResponseStructure<>();
		res.setData(convertWareHouseToWareHouseDTO(updateWareHouse));
		res.setMessage("Record is updated successfully for warehouse id : "+updateWareHouse.getId());
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<WareHouseDTO> deleteWareHouseFromDataBase(int id){
		Optional<WareHouse> opt = wareHouseRepository.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record not found with warehouse id : "+id+" in the database");
		}
		List<Shipment> li = shipmentRepository.findShipmentByWareHouse(id);
		boolean isAllDelivered = true;
		if(li.size() != 0) {
			for(Shipment s : li) {
				if(s.getStatus() != Status.Delevered) {
					isAllDelivered = false;
				}
			}
		}
		if(isAllDelivered == false) {
			throw new IdNotFoundException("a shipment is active for that warehouse id :"+id+", can't be deleted...");
		}
		for(Shipment s:li) {
			s.setWareHouse(null);
		}
		wareHouseRepository.deleteById(id);
		
		ResponseStructure<WareHouseDTO> res = new ResponseStructure<>();
		res.setData(null);
		res.setMessage("Record is deleted succesfully for warehouse id :"+id);
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}

}







