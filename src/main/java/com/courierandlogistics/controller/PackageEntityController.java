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
import com.courierandlogistics.entity.PackageEntity;
import com.courierandlogistics.service.PackageEntityService;

@RestController
@RequestMapping("/packageEntity")
public class PackageEntityController 
{
	@Autowired
	private PackageEntityService packageEntityService;
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<PackageEntity>>> getAllPackageEntities(){
		return new ResponseEntity<ResponseStructure<List<PackageEntity>>>(packageEntityService.getAllPackageEntitiesFromDatabase(),HttpStatus.FOUND);
	}
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<PackageEntity>> getPackageEntityById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<PackageEntity>>(packageEntityService.getPackageEntityByIdFromDatabase(id),HttpStatus.FOUND);
	}
	@GetMapping("/byShipment/{shipmentId}")
	public ResponseEntity<ResponseStructure<PackageEntity>> getPackageEntityByShipmentId(@PathVariable int shipmentId){
		return new ResponseEntity<ResponseStructure<PackageEntity>>(packageEntityService.getPackageEntityByShipmentFromdatabase(shipmentId),HttpStatus.FOUND);
	}
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<PackageEntity>> updatePackageEntity(@RequestBody PackageEntity p){
		return new ResponseEntity<ResponseStructure<PackageEntity>>(packageEntityService.updatePackageEntityInDatabase(p),HttpStatus.OK);
	}
}
