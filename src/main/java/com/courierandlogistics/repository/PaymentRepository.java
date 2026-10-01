package com.courierandlogistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.courierandlogistics.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> 
{
	

}
