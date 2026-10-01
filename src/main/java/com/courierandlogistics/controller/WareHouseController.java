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

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.WareHouseDTO;
import com.courierandlogistics.entity.WareHouse;
import com.courierandlogistics.service.WareHouseService;

@RestController
@RequestMapping("/warehouse")
public class WareHouseController 
{
	@Autowired
	private WareHouseService warehouseService;
	
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<WareHouseDTO>> createWareHouse(@RequestBody WareHouse house){
		return new ResponseEntity<ResponseStructure<WareHouseDTO>>(warehouseService.createWareHouseInDatabase(house),HttpStatus.CREATED);
	}
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<WareHouseDTO>>> getAllWareHouse(){
		return new ResponseEntity<ResponseStructure<List<WareHouseDTO>>>(warehouseService.getAllWareHousesFromDatabase(), HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<WareHouseDTO>>  getWareHouseById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<WareHouseDTO>>(warehouseService.getWareHouseById(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/location/{location}")
	public ResponseEntity<ResponseStructure<WareHouseDTO>> getWareHouseByLocation(@PathVariable String location){
		return new ResponseEntity<ResponseStructure<WareHouseDTO>>(warehouseService.getWareHouseByLocation(location),HttpStatus.FOUND);
	}
	
	@GetMapping("/capacity/{capacity}")
	public ResponseEntity<ResponseStructure<List<WareHouseDTO>>> getWareHouseByCapacityGreaterThen(@PathVariable int capacity){
		return new ResponseEntity<ResponseStructure<List<WareHouseDTO>>>(warehouseService.getWareHouseByCapacityGreaterThen(capacity),HttpStatus.FOUND);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<WareHouseDTO>> updateWareHouseDetails(@RequestBody WareHouse house){
		return new ResponseEntity<ResponseStructure<WareHouseDTO>>(warehouseService.updateWareHouseInTheDatabase(house), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<WareHouseDTO>> deleteWareHouseById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<WareHouseDTO>>(warehouseService.deleteWareHouseFromDataBase(id),HttpStatus.OK);
	}
}
