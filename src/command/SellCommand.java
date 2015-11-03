package command;

import model.Account;
import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;
import transactions.*;
import userInterface.MainView;

/**
 * 
 * @author Mitchell
 * 
 * Sell command for selling stocks from a portfolio. Funds are placed in selected account.
 */
public class SellCommand extends AbstractCommand {
	
	String ticker;
	Float price;
	int NumShares;
	String account;
	Transaction t;
	
	public SellCommand(String ticker, Float price, int NumShares, String account){
		this.account = account;
		this.NumShares = NumShares;
		this.price = price;
		this.ticker = ticker;
		t = new sellTransaction(ticker, account, price, NumShares);
	}
	
	@Override
	public
	void execute() throws NotEnoughOwnedSharesException {
		MainView.portfolio.sell(this.ticker, this.price, this.NumShares, this.account);
		MainView.portfolio.addTransaction(t);

	}

	@Override
	public
	void unexecute() throws InsufficientFundsException {
		MainView.portfolio.buy(this.ticker, this.price, this.NumShares, this.account);
		MainView.portfolio.removeTransaction(t);

	}


}
