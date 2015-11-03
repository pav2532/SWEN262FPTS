package command;

import model.InsufficientFundsException;
import transactions.*;
import userInterface.MainView;

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
