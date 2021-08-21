package com.banking.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.application.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
