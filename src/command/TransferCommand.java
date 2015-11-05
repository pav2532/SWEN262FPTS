package command;
import transactions.*;

import model.InsufficientFundsException;
import transactions.*;
import userInterface.MainView;

/**
 * 
 * @author Mitchell
 * 
 * Transfer Command for transfering money between two selected accounts in a portfolio.
 */
public class TransferCommand extends AbstractCommand {
	
	String accountTo;
	String accountFrom;
	Float amount;
	Transaction t;
	
	public TransferCommand(String accountTo, String accountFrom, Float amount){
		this.accountFrom = accountFrom;
		this.accountTo =  accountTo;
		this.amount =  amount;
		t = new transferTransaction(accountFrom, accountTo, amount);
	}

	@Override
	public
	void execute() throws InsufficientFundsException {
		MainView.portfolio.transfer(accountTo, accountFrom, amount);
		MainView.portfolio.addTransaction(t);

	}

	@Override
	public
	void unexecute() throws InsufficientFundsException {
		MainView.portfolio.transfer(accountFrom, accountTo, amount);
		MainView.portfolio.removeTransaction(t);

	}

}
