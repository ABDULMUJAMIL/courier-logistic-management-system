package com.courierandlogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.courierandlogistics.dto.CustomerDTO;
import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.entity.Customer;
import com.courierandlogistics.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController 
{
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/test")
	public String testAPI() {
		return "Welcome to app...";
	}
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<CustomerDTO>> createCustomer(@RequestBody Customer cust){
		return new ResponseEntity<ResponseStructure<CustomerDTO>>(customerService.createCutomerInDataBase(cust),HttpStatus.CREATED);
	}
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<CustomerDTO>>> getAllCustomer(){
		return new ResponseEntity<ResponseStructure<List<CustomerDTO>>>(customerService.getAllCustomerFromDataBases(), HttpStatus.FOUND);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<CustomerDTO>>  getCustomerById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<CustomerDTO>>(customerService.getCustomerByIdFromDatabase(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ResponseStructure<CustomerDTO>> getCUstomerByEmail(@PathVariable String email){
		return new ResponseEntity<ResponseStructure<CustomerDTO>>(customerService.getCustomerByEmailFromDatabase(email),HttpStatus.FOUND);
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<CustomerDTO>> getCutomerByContact(@PathVariable long contact){
		return new ResponseEntity<ResponseStructure<CustomerDTO>>(customerService.getCustomerByContactFromDatabase(contact),HttpStatus.FOUND);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<ResponseStructure<CustomerDTO>> updateCustomerDetails(@RequestBody Customer cust){
		return new ResponseEntity<ResponseStructure<CustomerDTO>>(customerService.updateCustomerInTheDatabase(cust), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<CustomerDTO>> deleteCustomerById(@PathVariable int id){
		return new ResponseEntity<ResponseStructure<CustomerDTO>>(customerService.deleteCustomerFromDatabaseById(id),HttpStatus.OK);
	}
	
	@GetMapping("/{pageNum}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<CustomerDTO>>> getCustomersByPagination(@PathVariable int pageNum, @PathVariable int  pageSize, @PathVariable String field){
		return new ResponseEntity<ResponseStructure<Page<CustomerDTO>>>(customerService.getCustomerByPaginationAndSorting(pageNum, pageSize, field), HttpStatus.FOUND);
	}
	
}
