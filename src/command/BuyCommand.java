package command;
import transactions.*;
import userInterface.MainView;
import model.Account;
import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;

public class BuyCommand extends AbstractCommand {

	String ticker;
	Float price;
	int NumShares;
	String account;

	Transaction t;

	public BuyCommand(String ticker, Float price, int NumShares, String account){
		this.account = account;
		this.NumShares = NumShares;
		this.price = price;
		this.ticker = ticker;
		t = new buyTransaction(ticker, account, price, NumShares);
	}
	
	

	@Override
	public void execute() throws InsufficientFundsException {
		
		MainView.portfolio.buy(this.ticker, this.price, this.NumShares, this.account);
		MainView.portfolio.addTransaction(t);

	}

	@Override
	public void unexecute() throws NotEnoughOwnedSharesException {
		MainView.portfolio.sell(this.ticker, this.price, this.NumShares, this.account);
		MainView.portfolio.removeTransaction(t);

	}

}

