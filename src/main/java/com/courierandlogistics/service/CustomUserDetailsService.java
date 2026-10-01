package com.courierandlogistics.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.courierandlogistics.configuration.CustomUserDetails;
import com.courierandlogistics.entity.UserCredentials;
import com.courierandlogistics.repository.UserCredentialsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserCredentialsRepository userCredentialsRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Optional<UserCredentials> opt = userCredentialsRepo.findByUserName(username);
		if (opt.isPresent()) {
			return new CustomUserDetails(opt.get());
		}
		return null;
		
	}
}

