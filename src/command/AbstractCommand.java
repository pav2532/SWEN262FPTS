package command;

import model.AlreadyContainsException;
import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;

/**
 * 
 * @author Mitchell
 * Abstract class for Command pattern
 *
 */
public abstract class AbstractCommand {

	/**
	 * executes the command
	 * @throws InsufficientFundsException
	 * @throws NotEnoughOwnedSharesException
	 * @throws AlreadyContainsException 
	 */
	public abstract void execute() throws InsufficientFundsException, NotEnoughOwnedSharesException, AlreadyContainsException;
	
	/**
	 * undoes the command (for undo, redo functionality)
	 * @throws NotEnoughOwnedSharesException
	 * @throws InsufficientFundsException
	 */
	public abstract void unexecute() throws NotEnoughOwnedSharesException, InsufficientFundsException;
	
}
