package com.banking.application.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.banking.application.exception.InSufficientFundException;
import com.banking.application.exception.InvalidCredentialsException;
import com.banking.application.exception.ResourceNotFoundException;
import com.banking.application.exception.TransactionFailedException;
import com.banking.application.exception.TransferLimitException;
import com.banking.application.exception.UserNameAlreadyExistsException;
import com.banking.application.model.Account;
import com.banking.application.model.Address;
import com.banking.application.model.Beneficiary;
import com.banking.application.model.Customer;
import com.banking.application.model.CustomerCredentials;
import com.banking.application.model.Transaction;
import com.banking.application.repository.AccountRepository;
import com.banking.application.repository.AddressRepository;
import com.banking.application.repository.BeneficiaryRepository;
import com.banking.application.repository.CustomerCredentialsRepository;
import com.banking.application.repository.CustomerRepository;
import com.banking.application.repository.TransactionRepository;
import com.banking.application.request.AccountOpeningRq;
import com.banking.application.request.BeneficiaryDTO;
import com.banking.application.request.FundTransferDto;
import com.banking.application.response.AccountCreationAcknowledgemnt;
import com.banking.application.utility.CommonUtil;
import com.banking.application.utility.CustomerProjection;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankOperationsService {

	private static final Logger logger = LoggerFactory.getLogger(BankOperationsService.class);
	@Autowired
	CustomerRepository custRepo;
	@Autowired
	AddressRepository addrRepo;
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	CustomerCredentialsRepository custCredRepo;
	@Autowired
	BeneficiaryRepository beneficiaryRepo;
	@Autowired
	TransactionRepository transRepo;

	@Autowired
	CommonUtil commonUtil;

	@Transactional(rollbackFor = TransactionFailedException.class, isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public ResponseEntity<AccountCreationAcknowledgemnt> saveCustomerDetails(AccountOpeningRq request)
			throws UserNameAlreadyExistsException {
		logger.info("inside saveCustomerDetails  method");
		Date date = new Date();
		logger.info("Checking for if User Name exists.");
		if (Optional.ofNullable(custRepo.findByUserName(request.getUserName())).isPresent())
			throw new UserNameAlreadyExistsException("User Name already exists.Please try with another user name");
		Customer cust = new Customer();
		cust.setUserName(request.getUserName());
		cust.setFirstName(request.getFirstName());
		cust.setLastName(request.getLastName());
		cust.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
		cust.setGender(request.getGender());
		cust.setMobileNo(Long.valueOf(request.getMobileNo()));
		cust.setEmailId(request.getEmailId());
		cust.setAadhaarCardNo(request.getAadhaarCardNo());
		cust.setPanCardNo(request.getPanCardNo());
		cust.setCreationDate(date);
		Address addr = new Address();
		addr.setAddress1(request.getAddress1());
		addr.setAddress2(request.getAddress2());
		addr.setCity(request.getCity());
		addr.setState(request.getState());
		addr.setPin(Long.valueOf(request.getPin()));
		addrRepo.save(addr);
		cust.setAddress(addr);
		custRepo.save(cust);
		logger.info("saved address and customer entities");
		String custId= Optional.ofNullable(cust.getCustomerId()).isEmpty() ? "0" :""+cust.getCustomerId();
		Account acct = new Account();
		acct.setAccountType(request.getAccountType());
		acct.setOpeningDeposit(Double.valueOf(request.getOpeningDeposit()));
		long accountNo = Long.valueOf((custId+""+commonUtil.numbGen()).substring(0, 12));
		acct.setAccountNo(accountNo);
		acct.setAvailableBalance(Double.valueOf(request.getOpeningDeposit()));
		acct.setCreationDate(date);
		acct.setBankName(request.getBankName());
		acct.setBranchName(request.getBranchName());
		acct.setIfsCode(request.getIfsCode());
		acct.setCustomer(cust);
		accountRepo.save(acct);
		logger.info("saved account entity");
		CustomerCredentials credentials = new CustomerCredentials();
		credentials.setAccountStatus(1);
		credentials.setCustomer(cust);
		credentials.setUserName(cust.getUserName());
		String strPwd  = acct.getAccountId() == null ? "":acct.getAccountId().toString() +UUID.randomUUID().toString().split("-")[1];
		credentials.setUserPassword(strPwd);
		custCredRepo.save(credentials);
		logger.info("saved customer credentials entity");
		StringBuffer strMsg = new StringBuffer();
		strMsg.append("Account Opened for Customer :: ").append(cust.getFirstName()).append(" " + cust.getLastName());
		strMsg.append("");
		strMsg.append("  Use credentials for Login: ").append("User Name:: " + credentials.getUserName())
				.append(" Password:: " + credentials.getUserPassword());
		return new ResponseEntity<>(new AccountCreationAcknowledgemnt(strMsg.toString()), HttpStatus.OK);
	}

	public ResponseEntity<String> saveBeneficiary(BeneficiaryDTO dto, String userName) {
		logger.info("inside saveBeneficiary  method");
		logger.info("Checking for if customer exists.");
		Customer customer = Optional.ofNullable(custRepo.findByUserName(userName))
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer Name", userName));
		Beneficiary benificary = new Beneficiary();
		benificary.setBeneficiaryAccountNo(Long.valueOf(dto.getBeneficiaryAccountNo()));
		benificary.setBeneficiaryName(dto.getBeneficiaryName());
		benificary.setIfsCode(dto.getIfsCode());
		benificary.setTransferLimit(Double.valueOf(dto.getTransferLimit()));
		benificary.setCustomer(customer);
		beneficiaryRepo.save(benificary);
		return new ResponseEntity<>("Successfully added Beneficiary", HttpStatus.OK);
	}

	public ResponseEntity<String> checkLoginCredential(CustomerCredentials credentials) {
		logger.info("inside checkLoginCredential method");
		logger.info("Checking for if customer credentials");
		
		if (Optional.ofNullable(
				custCredRepo.findByUserNameAndUserPassword(credentials.getUserName(), credentials.getUserPassword()))
				.isEmpty()) {
			throw new InvalidCredentialsException("Authetication Failed! Please provide valid User Name or Password ");
		}
		return new ResponseEntity<>("Authetication Success", HttpStatus.OK);
	}
	@Transactional(rollbackFor = TransactionFailedException.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> fundTransfer(FundTransferDto fundTransDto, String userName) {
		Customer customer = Optional.ofNullable(custRepo.findByUserName(userName))
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer Name", userName));
		// Check is the account number exists, if does not exists return error message
		// else continue .
		Account fromAccDetails = Optional
				.ofNullable(accountRepo.findByAccountNoAndCustomerCustomerId(
						Long.valueOf(fundTransDto.getFromAccountNo()), customer.getCustomerId()))
				.orElseThrow(() -> new ResourceNotFoundException("Account Details", "Account Number",
						fundTransDto.getFromAccountNo()));

		Beneficiary toAccDetails = Optional
				.ofNullable(beneficiaryRepo.findByBeneficiaryAccountNoAndCustomerCustomerId(
						Long.valueOf(fundTransDto.getToAccountNo()), customer.getCustomerId()))
				.orElseThrow(() -> new ResourceNotFoundException("Customers Beneficiary Account Details  ", "Account Number",
						fundTransDto.getToAccountNo()));

		if (Double.valueOf(fundTransDto.getTransferAmount()) > fromAccDetails.getAvailableBalance()) {
			throw new InSufficientFundException(
					"InSufficent balance for the account number::" + fromAccDetails.getAccountNo());
		}

		if (Double.valueOf(fundTransDto.getTransferAmount()) > toAccDetails.getTransferLimit()) {
			throw new TransferLimitException("Transfer Limit Excessed for this benificary account number::"
					+ toAccDetails.getBeneficiaryAccountNo()+" Maximum tranfer Limit: "+toAccDetails.getTransferLimit());
		}

		Date date = new Date();
		Transaction sourceAcc = new Transaction();
		Transaction targetAcc = new Transaction();

		// inserting records to transactions tables for source account transaction
		Timestamp ts = new Timestamp(date.getTime());
		sourceAcc.setAmount(Double.valueOf(fundTransDto.getTransferAmount()));
		sourceAcc.setFromAccount(fromAccDetails.getAccountNo());
		sourceAcc.setTransactionTime(ts);
		sourceAcc.setTransactionType("Debit");
		sourceAcc.setRemarks(fundTransDto.getRemarks());
		transRepo.save(sourceAcc);

		// inserting records to transactions tables for target account transaction
		targetAcc.setAmount(Double.valueOf(fundTransDto.getTransferAmount()));
		targetAcc.setToAccount(toAccDetails.getBeneficiaryAccountNo());
		targetAcc.setTransactionTime(ts);
		targetAcc.setTransactionType("Credit");
		targetAcc.setRemarks(fundTransDto.getRemarks());
		transRepo.save(targetAcc);

		// updating the account details for the given account number
		if (Optional.ofNullable(transRepo).isPresent()) {
			fromAccDetails.setAvailableBalance(
					fromAccDetails.getAvailableBalance() - Double.valueOf(fundTransDto.getTransferAmount()));
			fromAccDetails.setOpeningDeposit(
					fromAccDetails.getOpeningDeposit() - Double.valueOf(fundTransDto.getTransferAmount()));
			accountRepo.save(fromAccDetails);
		}

		return new ResponseEntity<>("Transaction Done Successfully ", HttpStatus.OK);
	}

	public ResponseEntity<CustomerProjection> getCustomerDetails(String userName) {
		logger.info("inside getCustomerDetails method");
		return new ResponseEntity<>(custRepo.findCustomerByUserName(userName), HttpStatus.OK);
	}
	
	
	
}
