package com.banking.application.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BeneficiaryDTO implements Serializable
{
	 private static final long serialVersionUID = 354675822400537614L;
	 @NotEmpty(message="provide beneficiary name")
	 @Size(min = 5, max = 50) 
	 @Pattern(regexp = "^[a-zA-Z0-9_ ]*$",message = "provide valid beneficiary name")
	 private String beneficiaryName;
	 
	 @Size(min = 5, max = 13)
	 @NotNull(message="provide account no ,only digits")
	 @Pattern(regexp = "[0-9]{13}",message = "provide a valid account no") 
	 private String beneficiaryAccountNo;
	 
	 @NotNull(message="provide transfer limit ,only digits")
	 @Size(min = 3, max = 7,message = "maximum transfer limit allowed is 10 lakhs ")
	 @Pattern(regexp = "[0-9.#]+",message = "provide valid Transfer Limit amount")
	 private String transferLimit;
	 
	 @NotEmpty(message="provide IFSC Code")
	 @Size(min = 5, max = 15) 
	 @Pattern(regexp = "[a-zA-Z0-9]+",message = "provide valid ifsCode")
	 private String ifsCode;
}
	 
	