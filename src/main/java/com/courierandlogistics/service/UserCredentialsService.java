package com.courierandlogistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.entity.UserCredentials;
import com.courierandlogistics.repository.UserCredentialsRepository;

@Service
public class UserCredentialsService 
{
	@Autowired
	private UserCredentialsRepository userCredentialsRepo;
	
	public ResponseStructure<UserCredentials> addUserCrd(UserCredentials uc){
		ResponseStructure<UserCredentials> res = new ResponseStructure<>();
		res.setData(userCredentialsRepo.save(uc));
		res.setMessage(" User Credentials addedd");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
		
	}

}
