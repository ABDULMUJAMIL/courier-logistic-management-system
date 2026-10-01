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
import com.courierandlogistics.entity.Payment;
import com.courierandlogistics.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments(){
		return new ResponseEntity<ResponseStructure<List<Payment>>>(paymentService.getAllPaymentsFromDatabase(),HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentsById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<Payment>>(paymentService.getPaymentByIdFromDatabase(id),HttpStatus.FOUND);
	}
	
	@PatchMapping("/updatePaymentStatus")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@RequestBody Payment p){
		return new ResponseEntity<ResponseStructure<Payment>>(paymentService.updatePaymentStatusInDatabase(p),HttpStatus.OK);
	}
}
