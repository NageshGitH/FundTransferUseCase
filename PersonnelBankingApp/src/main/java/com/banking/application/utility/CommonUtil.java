package com.banking.application.utility;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil
{
  public LocalDateTime getDateTime()
  {
	  return LocalDateTime.now();
	  
  }
  public long numbGen() 
  {
	  while (true) {
	        long numb = (long)(Math.random() * 100000000 * 100000000); // had to use this as int's are to small for a 13 digit number.
	        if (String.valueOf(numb).length() == 14)
	            return numb;
	    }
  
  }
}
  