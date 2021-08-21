package com.banking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.application.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
