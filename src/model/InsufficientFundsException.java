package model;

/**
 * 
 * @author Mitchell
 *
 *Exception thrown when an account does not have enough funds for an operation.
 */
public class InsufficientFundsException extends Exception {

	public InsufficientFundsException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -941227887327042131L;

	
}
