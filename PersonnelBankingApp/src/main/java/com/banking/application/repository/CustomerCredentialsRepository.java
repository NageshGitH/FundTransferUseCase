package com.banking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.application.model.CustomerCredentials;

public interface CustomerCredentialsRepository extends JpaRepository<CustomerCredentials, Long>{

	String findByUserPassword(String password);
	
	CustomerCredentials findByUserNameAndUserPassword(String userName,String userPassword);
}
