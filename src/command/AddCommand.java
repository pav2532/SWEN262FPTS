package command;

import model.AlreadyContainsException;
import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;
import userInterface.MainView;

public class AddCommand extends AbstractCommand {
	String ticker;
	Float highValue;
	Float lowValue;
	
	public AddCommand(String ticker, Float highValue, Float lowValue){
		this.ticker = ticker;
		this.highValue = highValue;
		this.lowValue = lowValue;
	}
	public void execute() throws AlreadyContainsException {
		// TODO Auto-generated method stub
		MainView.portfolio.add(this.ticker, this.highValue, this.lowValue);
		

	}

	@Override
	public void unexecute() throws NotEnoughOwnedSharesException, InsufficientFundsException {

	}

}
