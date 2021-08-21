package com.banking.application.exception;

public class TransferLimitException extends RuntimeException
{
    
	private static final long serialVersionUID = 1L;
	
	public TransferLimitException()
    {
    	super();
    	
    }
    public TransferLimitException(String message)
    {
    	super(message);
    }
    public TransferLimitException(String message,Throwable t)
    {
    	super(message,t);
    }
}

