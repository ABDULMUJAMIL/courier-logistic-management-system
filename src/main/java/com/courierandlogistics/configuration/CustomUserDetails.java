package com.courierandlogistics.configuration;


import java.util.Arrays;
import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.courierandlogistics.entity.UserCredentials;

public class CustomUserDetails implements UserDetails
{
	private UserCredentials userCredentials;
	public CustomUserDetails(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(String.valueOf(userCredentials.getRole())));
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return userCredentials.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userCredentials.getUserName();
	}
	
}
