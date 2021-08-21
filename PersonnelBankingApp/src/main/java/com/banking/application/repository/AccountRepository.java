package com.banking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.application.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Long findByAccountNo(long accountNo);
	Account findByAccountNoAndCustomerCustomerId(long accountNo,long customerId);

}
