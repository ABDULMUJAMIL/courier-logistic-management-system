package com.courierandlogistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.entity.Payment;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.InvalidInputException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.PaymentRepository;

@Service
public class PaymentService 
{
	@Autowired
	private PaymentRepository paymentRepo;
	
	public ResponseStructure<List<Payment>> getAllPaymentsFromDatabase(){
		List<Payment> payments = paymentRepo.findAll();
		if(payments.isEmpty()) {
			throw new NoRecordFoundException("No records found in database...");
		}
		ResponseStructure<List<Payment>> res = new ResponseStructure<>();
		res.setData(payments);
		res.setMessage(payments.size()+"Records found successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Payment> getPaymentByIdFromDatabase(int id){
		Optional<Payment> opt = paymentRepo.findById(id);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No record exist with payment id : "+id+" in database");
		}
		ResponseStructure<Payment> res = new ResponseStructure<>();
		res.setData(opt.get());
		res.setMessage("Record fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<Payment> updatePaymentStatusInDatabase(Payment p){
		if(p.getId() == null ) {
			throw new IdNotFoundException("Id must be passed to update the record in database...");
		}
		if (p.getPaymentStatus() == null) {
		    throw new InvalidInputException("Payment status cannot be null");
		}
		Optional<Payment> opt = paymentRepo.findById(p.getId());
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("No record exist with payment id :" +p.getId()+" in database");
		}
		Payment fetchedPayment = opt.get();
		fetchedPayment.setPaymentStatus(p.getPaymentStatus());
		ResponseStructure<Payment> res = new ResponseStructure<>();
		res.setData(paymentRepo.save(fetchedPayment));
		res.setMessage("Record updated successfully...");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
}
