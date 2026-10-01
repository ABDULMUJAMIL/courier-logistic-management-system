package com.courierandlogistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.entity.PackageEntity;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.PackageEntityRepository;

@Service
public class PackageEntityService 
{
	@Autowired
	private PackageEntityRepository packageEntityRepo;
	
	
	public ResponseStructure<List<PackageEntity>> getAllPackageEntitiesFromDatabase(){
		List<PackageEntity> li = packageEntityRepo.findAll();
		if(li.size() == 0) {
			throw new NoRecordFoundException("No records exist for package entity in database...");
		}
		ResponseStructure<List<PackageEntity>> res = new ResponseStructure<>();
		res.setData(li);
		res.setMessage(li.size()+" records found in database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<PackageEntity> getPackageEntityByIdFromDatabase(int id){
		Optional<PackageEntity> opt = packageEntityRepo.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Records with package id "+id+" not exist in database...");
		}
		ResponseStructure<PackageEntity> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Records fetched successfully from database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<PackageEntity> updatePackageEntityInDatabase(PackageEntity p){
		if(p.getId() == null) {
			throw new IdNotFoundException("Id Must Be Passed To Update Package Entity Record...");
		}
		Optional<PackageEntity> opt = packageEntityRepo.findById(p.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record not found for package id : "+p.getId()+" in database . .");
		}
		ResponseStructure<PackageEntity> res = new ResponseStructure<>();
		res.setData(packageEntityRepo.save(p));
		res.setMessage("Record updated successfully in database...");
		res.setStatusCode(HttpStatus.OK.value());
		
		return res;
	}
	
	public ResponseStructure<PackageEntity> getPackageEntityByShipmentFromdatabase(int shipmentId){
		Optional<PackageEntity> opt = packageEntityRepo.findPackageEntityByShipmentId(shipmentId);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Package Record with shipment id : "+shipmentId+" not exist in database . .");
		}
		ResponseStructure<PackageEntity> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Records fetched successfully from database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		
		return res;
		
	}
		
}
