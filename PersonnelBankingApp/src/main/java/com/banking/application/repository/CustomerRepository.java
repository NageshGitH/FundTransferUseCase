package com.banking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.application.model.Customer;
import com.banking.application.utility.CustomerProjection;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
	Customer findByUserName(String userName);
	
	Customer findByCustomerId(long customerId);
	
	CustomerProjection findCustomerByUserName(String userName);
}
