package com.banking.application.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.application.exception.UserNameAlreadyExistsException;
import com.banking.application.model.CustomerCredentials;
import com.banking.application.request.AccountOpeningRq;
import com.banking.application.request.BeneficiaryDTO;
import com.banking.application.request.FundTransferDto;
import com.banking.application.service.BankOperationsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value ="/bankOperations")
@EnableTransactionManagement
@Slf4j
public class BankOperationsController
{
	private static final Logger logger = LoggerFactory.getLogger(BankOperationsController.class);
	
	@Autowired
	BankOperationsService bankOpertionService;
	
    @PostMapping("/")
	public ResponseEntity<?> customerAccountOpening(@Valid @RequestBody AccountOpeningRq request)  throws UserNameAlreadyExistsException
	{
    	logger.info("inside customerAccountOpening");
		return new ResponseEntity<>(bankOpertionService.saveCustomerDetails(request),HttpStatus.OK);
	}
    
    @PostMapping("/{userName}")
	public ResponseEntity<?> saveBeneficiaryDetails(@Valid @RequestBody BeneficiaryDTO request,@PathVariable("userName") String userName)  
	{
    	logger.info("inside saveBeneficiaryDetails");
		return new ResponseEntity<>(bankOpertionService.saveBeneficiary(request,userName),HttpStatus.OK);
	}
    
    @PostMapping("/checkCredentials")
	public ResponseEntity<?> checkCredentials(@Valid @RequestBody CustomerCredentials custCredentials)  
	{
    	logger.info("inside checkCredentials");
		return new ResponseEntity<>(bankOpertionService.checkLoginCredential(custCredentials),HttpStatus.OK);
	}
    @PostMapping("/fundTransfer/{userName}")
   	public ResponseEntity<?> fundTransfer(@Valid @RequestBody FundTransferDto fundTransferDto,@PathVariable("userName") String userName)  
   	{
       	logger.info("inside checkCredentials");
   		return new ResponseEntity<>(bankOpertionService.fundTransfer(fundTransferDto,userName),HttpStatus.OK);
   	}
    @GetMapping("/{userName}")
   	public ResponseEntity<?> getCustomerDetails(@PathVariable("userName") String userName)  
   	{
       	logger.info("inside checkCredentials");
   		return new ResponseEntity<>(bankOpertionService.getCustomerDetails(userName),HttpStatus.OK);
   	}
    
    
    
}
