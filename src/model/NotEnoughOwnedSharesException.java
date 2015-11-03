package model;

/**
 * 
 * @author Mitchell
 *
 * Thrown when a portfolio does not own enough shares to complete an operation.
 */
public class NotEnoughOwnedSharesException extends Exception {


	public NotEnoughOwnedSharesException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -557300045795546372L;

}
