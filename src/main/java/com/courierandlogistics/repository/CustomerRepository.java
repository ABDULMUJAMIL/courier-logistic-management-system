package com.courierandlogistics.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.courierandlogistics.entity.Customer;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
	@Query("select c from Customer c where c.customerEmail=?1")
	Optional<Customer> findCustomerByEmail(String  customerEmail);
	
	@Query("select c from Customer c where c.contact=?1")
	Optional<Customer> findCustomerWithContact(long contact);
	

}
