package com.banking.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.banking.application.exception.InSufficientFundException;
import com.banking.application.exception.InvalidCredentialsException;
import com.banking.application.exception.ResourceNotFoundException;
import com.banking.application.exception.UserNameAlreadyExistsException;
import com.banking.application.model.Account;
import com.banking.application.model.Beneficiary;
import com.banking.application.model.Customer;
import com.banking.application.model.CustomerCredentials;
import com.banking.application.repository.AccountRepository;
import com.banking.application.repository.BeneficiaryRepository;
import com.banking.application.repository.CustomerCredentialsRepository;
import com.banking.application.repository.CustomerRepository;
import com.banking.application.request.AccountOpeningRq;
import com.banking.application.request.BeneficiaryDTO;
import com.banking.application.request.FundTransferDto;
import com.banking.application.service.BankOperationsService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonnelBankingAppApplicationTests {

	@Autowired
	BankOperationsService bankServiceImpl;
	
	@MockBean
	CustomerCredentialsRepository custCredRepo;

	@MockBean
	CustomerRepository custRepo;
	
	@MockBean
	AccountRepository accRepo;

	@MockBean
	BeneficiaryRepository beneficiaryRepo;


	Customer customer = new Customer();
	Beneficiary benificary = new Beneficiary();
	Account account = new Account();
	CustomerCredentials custCredentials=new CustomerCredentials();
	BeneficiaryDTO dto = new BeneficiaryDTO();
	FundTransferDto fundTranferDto = new FundTransferDto();
	AccountOpeningRq accountOpeningDto = new AccountOpeningRq();

	String name = "AI0326";

	@BeforeAll
	public void setUp() {
		// mock customer credentials as in database
		custCredentials.setUserName("Nagesh");
		custCredentials.setUserPassword("sri");
		
		
		// mock customer details as in database
		customer.setCustomerId(1l);
		customer.setUserName("AI0326");
		customer.setFirstName("Devi");
		customer.setLastName("Konatham");
		customer.setDateOfBirth(LocalDate.now());
		customer.setGender("male");
		customer.setMobileNo(7680092889l);
		customer.setEmailId("skonatham@gmail.com");
		customer.setPanCardNo("AULPK1507H");
		customer.setAadhaarCardNo("400607192799");
				
		// mock account details as in database
		account.setAccountId(1l);
		account.setAvailableBalance(4500);
		account.setOpeningDeposit(4500);
		account.setBankName("HDFC");
		account.setBranchName("Kukatpally");
		account.setIfsCode("HDFC00314");
		account.setCreationDate(new Date());
		account.setCustomer(customer);
		account.setAccountType("Checking");
		account.setAccountNo(123456l);
				
				
		// mock benificary details as in database
		benificary.setBeneficiaryAccountNo(4567);
		benificary.setBeneficiaryId(1l);
		benificary.setBeneficiaryName("mohan");
		benificary.setCustomer(customer);
		benificary.setIfsCode("HDFC00314");
		benificary.setTransferLimit(6000);
				
		//set values for fund transfer dto
		
		fundTranferDto.setFromAccountNo("123456");
		fundTranferDto.setToAccountNo("4567");
		fundTranferDto.setRemarks("Expenses");
		fundTranferDto.setTransferAmount("1000");
		
		
		//set values for BeneficiaryDTO
		dto.setBeneficiaryAccountNo("4567");
		dto.setBeneficiaryName("Nagesh");
		dto.setIfsCode("HDF00314");
		dto.setTransferLimit("1000");
		
		//set values for accountopening dto
		accountOpeningDto.setUserName("AI0327");
		accountOpeningDto.setFirstName("Devi");
		accountOpeningDto.setLastName("Konatham");
		accountOpeningDto.setDateOfBirth("2018-01-18");
		accountOpeningDto.setGender("male");
		accountOpeningDto.setMobileNo("7680092889");
		accountOpeningDto.setEmailId("skonatham@gmail.com");
		accountOpeningDto.setPanCardNo("AULPK1507H");
		accountOpeningDto.setAadhaarCardNo("400607192799");
		accountOpeningDto.setAddress1("Adress1");
		accountOpeningDto.setAddress2("Address2");
		accountOpeningDto.setCity("KP");
		accountOpeningDto.setState("Hyd");
		accountOpeningDto.setPin("500072");
		accountOpeningDto.setOpeningDeposit("4500");
		accountOpeningDto.setBankName("HDFC");
		accountOpeningDto.setBranchName("Kukatpally");
		accountOpeningDto.setIfsCode("HDFC00314");
		accountOpeningDto.setAccountType("Checking");
		
		
	}
	
	@Test
	@DisplayName("Account Opening")
	@Order(1)
	public void testCreateAccountOpening() throws UserNameAlreadyExistsException {
		Mockito.when(custRepo.findByUserName(customer.getUserName())).thenReturn(customer);
		assertEquals(HttpStatus.OK, bankServiceImpl.saveCustomerDetails(accountOpeningDto).getStatusCode());
	}
	

	@Test
	@DisplayName("Check Customer Login Credentials")
	@Order(2)
	public void testAuthenticateUser() throws InvalidCredentialsException {
		Mockito.when(custCredRepo.findByUserNameAndUserPassword("Nagesh", "sri")).thenReturn(custCredentials);
		assertEquals(HttpStatus.OK, bankServiceImpl.checkLoginCredential(custCredentials).getStatusCode());
	}

	@Test
	@DisplayName("Save Beneficiary")
	@Order(3)
	public void testSaveBenificary() throws ResourceNotFoundException, MethodArgumentNotValidException {
		Mockito.when(custRepo.findByUserName(customer.getUserName())).thenReturn(customer);
		assertEquals(HttpStatus.OK, bankServiceImpl.saveBeneficiary(dto, name).getStatusCode());
	}
	
	@Test
	@DisplayName("Test FundTransfer Transaction")
	@Order(4)
	public void testFundTransfer() throws ResourceNotFoundException, InSufficientFundException {
		Mockito.when(custRepo.findByUserName(customer.getUserName())).thenReturn(customer);
		Mockito.when(accRepo.findByAccountNoAndCustomerCustomerId(account.getAccountNo(), 1l)).thenReturn(account);
		Mockito.when(beneficiaryRepo.findByBeneficiaryAccountNoAndCustomerCustomerId(Long.valueOf(benificary.getBeneficiaryAccountNo()),1l)).thenReturn(benificary);
		
		assertEquals(HttpStatus.OK, bankServiceImpl.fundTransfer(fundTranferDto, name).getStatusCode());
	}
	

}
