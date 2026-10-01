package com.courierandlogistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.courierandlogistics.entity.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer>
{
	Optional<UserCredentials> findByUserName(String userName);

}
