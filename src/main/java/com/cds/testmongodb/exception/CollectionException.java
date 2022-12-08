package com.cds.testmongodb.exception;

public class CollectionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollectionException(String message) {
		super(message);
	}

	public static String notFoundException(String exceptionId) {
		return exceptionId;
	}
	
	public static String AlreadyExistsException() {
		return "exists";
	}
	
}
