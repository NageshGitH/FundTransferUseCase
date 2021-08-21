package com.banking.application.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOpeningRq implements Serializable {
	private static final long serialVersionUID = 3994192272098493497L;

	@NotEmpty(message = "provide user name for login")
	@Size(min = 5, max = 50)
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String userName;

	@NotEmpty(message = "provide first name")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
	private String firstName;

	@NotEmpty(message = "provide last name ")
	@Size(min = 2, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
	private String lastName;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	@NotEmpty(message = "Provide date of birth ")
	@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$",message = "Provide date of birth (yyyy-MM-dd) format" )
	private String dateOfBirth;

	@NotEmpty(message = "provide gender")
	private String gender;

	@NotNull(message = "provide mobile no ,only digits")
	@Pattern(regexp = "[0-9]{10}", message = "provide valid mobile no")
	private String mobileNo;

	@NotEmpty(message = "Provide email id")
	@Email
	private String emailId;

	@NotEmpty(message = "provide pan number")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "provide valid pan number")
	private String panCardNo;

	@NotNull(message = "provide aadhaar number,only digits ")
	@Pattern(regexp = "[0-9]{12}", message = "provide valid aadhaar number")
	private String aadhaarCardNo;

	@NotEmpty(message = "Provide Customer Address1")
	@Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters")
	private String address1;

	@NotEmpty(message = "Provide Customer Address2")
	@Size(min = 10, max = 150, message = "Address must be between 10 and 150 characters")
	private String address2;

	@Column(name = "city")
	@NotEmpty(message = "provide city")
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid city")
	private String city;

	@NotEmpty(message = "provide state")
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid state")
	private String state;

	@NotNull(message = "provide zipCode no ,only digits")
	@Pattern(regexp = "[0-9]{6}", message = "provide valid zipCode")
	private String pin;

	@NotEmpty(message = "provide account type")
	@Size(min = 5, max = 10) // Value must contain at least 5 characters and a maximum of 10 characters
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid account type")
	private String accountType;

	@NotNull(message = "provide opening deposit ,only digits")
	@Pattern(regexp = "([^.\\d]+|[\\d+\\.]{2,})", message = "provide valid amount ")
	private String openingDeposit;

	@Size(min = 2, max = 25)
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid  Bank Name")
	@NotEmpty(message = "provide Bank Name")
	private String bankName;

	@Size(min = 5, max = 25)
	@Pattern(regexp = "[a-zA-Z]+", message = "provide valid  Branch Name")
	@NotEmpty(message = "provide Branch Name")
	private String branchName;
	
	@NotEmpty(message="provide IFSC Code")
	 @Size(min = 5, max = 15)
	 @Pattern(regexp = "[a-zA-Z0-9]+",message = "provide ifsCode")
	 private String ifsCode;

}
