package com.banking.application.exception;

public class InvalidAccountNoException extends RuntimeException
{
    
	private static final long serialVersionUID = 1L;
	
	public InvalidAccountNoException()
    {
    	super();
    	
    }
    public InvalidAccountNoException(String message)
    {
    	super(message);
    }
    public InvalidAccountNoException(String message,Throwable t)
    {
    	super(message,t);
    }
}
