package com.banking.application.exception;

public class InSufficientFundException extends RuntimeException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public InSufficientFundException()
  {
	  super();
  }
  public InSufficientFundException(String message)
  {
	  super(message);
  }
  
  public InSufficientFundException(String message, Throwable t)
  {
	  super(message,t);
  }
  
  
}
