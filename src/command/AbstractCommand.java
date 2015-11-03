package command;

import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;

public abstract class AbstractCommand {

	public abstract void execute() throws InsufficientFundsException, NotEnoughOwnedSharesException;
	
	public abstract void unexecute() throws NotEnoughOwnedSharesException, InsufficientFundsException;
	
}
