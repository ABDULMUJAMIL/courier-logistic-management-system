package com.courierandlogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.courierandlogistics.dto.ResponseStructure;
import com.courierandlogistics.entity.UserCredentials;
import com.courierandlogistics.service.UserCredentialsService;

@RestController
@RequestMapping("/userCred")
public class UserCredentialsController 
{
	@Autowired
	private UserCredentialsService userCredentialsService;
	
	@PatchMapping("/add")
	public ResponseEntity<ResponseStructure<UserCredentials>> addUserCred(@RequestBody UserCredentials uc){
		return new ResponseEntity<ResponseStructure<UserCredentials>>(userCredentialsService.addUserCrd(uc),HttpStatus.CREATED);
	}

}
