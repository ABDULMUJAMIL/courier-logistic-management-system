
package com.courierandlogistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.CustomerDTO;
import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.dto.Status;
import com.courierandlogistics.entity.Customer;
import com.courierandlogistics.entity.Shipment;
import com.courierandlogistics.exception.IdNotFoundException;
import com.courierandlogistics.exception.InvalidContactNumberException;
import com.courierandlogistics.exception.NoRecordFoundException;
import com.courierandlogistics.repository.CustomerRepository;
import com.courierandlogistics.repository.ShipmentRepository;

@Service
public class CustomerService 
{
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	private static CustomerDTO convertCustomerToCustomerDTO(Customer c) 
	{
		CustomerDTO cd = new CustomerDTO();
		cd.setAddress(c.getAddress());
		cd.setContact(c.getContact());
		cd.setEmail(c.getCustomerEmail());
		cd.setName(c.getCustomerName());
		cd.setId(c.getId());
		return cd;	
	}
	
	public ResponseStructure<CustomerDTO> createCutomerInDataBase(Customer cust){
		if(String.valueOf(cust.getContact()).length()!=10) {
			throw new InvalidContactNumberException("Invalid Contact Number. Contact number must be 10 digits . .");
		}
		Customer savedCustomer = customerRepository.save(cust);
		ResponseStructure<CustomerDTO> res = new ResponseStructure<>();
		res.setData(convertCustomerToCustomerDTO(savedCustomer));
		res.setMessage("1 Record inserted into Customer Table..");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	
	public ResponseStructure<List<CustomerDTO>> getAllCustomerFromDataBases(){
		List<Customer> li = customerRepository.findAll();
		List<CustomerDTO> dtoList = new ArrayList<>();
		for(Customer c:li) {
			dtoList.add(convertCustomerToCustomerDTO(c));
		}
		ResponseStructure<List<CustomerDTO>> res = new ResponseStructure<>();
		res.setData(dtoList);
		res.setMessage(dtoList.size()+"Records found in customer table..");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<CustomerDTO> getCustomerByIdFromDatabase(int id){
		Optional<Customer> opt = customerRepository.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record with id "+id+" is Not present in the database..");
		}
		ResponseStructure<CustomerDTO> res = new ResponseStructure<>();
		res.setData(convertCustomerToCustomerDTO(opt.get()));
		res.setMessage("Record found in customer table with the id "+id+"...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<CustomerDTO> getCustomerByEmailFromDatabase(String customerEmail){
		Optional<Customer> opt = customerRepository.findCustomerByEmail(customerEmail);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with email "+customerEmail+" is not present in the database");
		}
		ResponseStructure<CustomerDTO> res = new ResponseStructure<>();
		res.setData(convertCustomerToCustomerDTO(opt.get()));
		res.setMessage("Record present with "+customerEmail+" in the database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<CustomerDTO> getCustomerByContactFromDatabase(long contact){
		Optional<Customer> opt = customerRepository.findCustomerWithContact(contact);
		if(opt.isEmpty()) {
			throw new NoRecordFoundException("Record with contact "+contact+" is not present in database");
		}
		ResponseStructure<CustomerDTO> res = new ResponseStructure<>();
		res.setData(convertCustomerToCustomerDTO(opt.get()));
		res.setMessage("Record present with "+contact+" in the database...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}
	
	public ResponseStructure<CustomerDTO> updateCustomerInTheDatabase(Customer cust){
		if(cust.getId()==null) {
			throw new IdNotFoundException("To update the record, id must be passed...");
		}
		Optional<Customer> opt = customerRepository.findById(cust.getId());
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Id is Not present with "+cust.getId()+" in the database to update the record...");
		}
		Customer fetchedCustomer = opt.get();
		cust.setUserCredentials(fetchedCustomer.getUserCredentials());
		Customer updatedCustomer = customerRepository.save(cust);
		ResponseStructure<CustomerDTO> res = new ResponseStructure<>();
		res.setData(convertCustomerToCustomerDTO(updatedCustomer));
		res.setMessage("Record is updated successfully for customer id : "+updatedCustomer.getId());
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<CustomerDTO> deleteCustomerFromDatabaseById(int id){
		Optional<Customer> opt = customerRepository.findById(id);
		if(opt.isEmpty()) {
			throw new IdNotFoundException("Record not found with customer id : "+id+" in the database...");
		}
		List<Shipment> li=shipmentRepository.findShipmentByCustomerId(id);
		boolean isAllDelivered = true;
		if(li.size() != 0) {
			for(Shipment s:li) {
				if(s.getStatus() != Status.Delevered) {
					isAllDelivered = false;
				}
			}
		}
		if(isAllDelivered == false) {
			throw new IdNotFoundException("a shipment is active for that customer id :"+id+", can't be deleted...");
		}
		for(Shipment s:li) {
			s.setCustomer(null);
		}
		customerRepository.deleteById(id);
		
		ResponseStructure<CustomerDTO> res = new ResponseStructure<>();
		res.setData(null);
		res.setMessage("Record is deleted succesfully for customer id :"+id);
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	
	public ResponseStructure<org.springframework.data.domain.Page<CustomerDTO>> getCustomerByPaginationAndSorting(int pageNum, int pageSize, String field){
		org.springframework.data.domain.Page<Customer> pages = customerRepository.findAll(PageRequest.of(pageNum, pageSize,Sort.by(field).ascending()));
		org.springframework.data.domain.Page<CustomerDTO> dtoPage = pages.map(c ->{
			CustomerDTO dto = new CustomerDTO();
			dto.setId(c.getId());
			dto.setName(c.getCustomerName());
			dto.setEmail(c.getCustomerEmail());
			dto.setContact(c.getContact());
			dto.setAddress(c.getAddress());
			return dto;
		});
		ResponseStructure<org.springframework.data.domain.Page<CustomerDTO>> res = new ResponseStructure<>();
		res.setData(dtoPage);
		res.setMessage("Page fetched successfully...");
		res.setStatusCode(HttpStatus.FOUND.value());
		return res;
	}

}











