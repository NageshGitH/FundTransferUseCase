package com.banking.application.request;

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
public class FundTransferDto 
{
	 @Size(min = 5, max = 13)
	 @NotNull(message="provide from account no ,only digits")
	 @Pattern(regexp = "[0-9]{13}",message = "provide a valid from account no") 	
	 private String fromAccountNo;
	 
	 @Size(min = 5, max = 13)
	 @NotNull(message="provide to account no ,only digits")
	 @Pattern(regexp = "[0-9]{13}",message = "provide a valid to account no") 
	 private String toAccountNo;
	 
	 @NotNull(message="provide transfer amount")
	 @Pattern(regexp = "[0-9.#]+",message = "provide valid Transfer amount")
     private String transferAmount;
	 
	 
	 private String remarks;
}
