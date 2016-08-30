package com.maaz.lms.exception;

public class LmsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LmsException()
	{
	}

	public LmsException(String message)
	{
		super(message);
	}

	public LmsException(Throwable cause)
	{
		super(cause);
	}

	public LmsException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public LmsException(String message, Throwable cause, 
                                       boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
