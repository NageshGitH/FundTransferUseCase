package com.banking.application.exception;

public class SearchResultsNotFoundException extends RuntimeException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public SearchResultsNotFoundException()
  {
	  super();
  }
  public SearchResultsNotFoundException(String message)
  {
	  super(message);
  }
  
  public SearchResultsNotFoundException(String message, Throwable t)
  {
	  super(message,t);
  }
  
  
}
