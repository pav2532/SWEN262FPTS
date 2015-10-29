package command;

public class SellCommand extends AbstractCommand {
	
	String ticker;
	Float price;
	int NumShares;
	Account account;
	sellTransaction t;
	
	public SellCommand(String ticker, Float price, int NumShares, Account account){
		this.account = account;
		this.NumShares = NumShares;
		this.price = price;
		this.ticker = ticker;
		t = new sellTransaction(ticker, account.getName(), price, NumShares);
	}
	
	@Override
	void execute() {
		//PortfolioController.portfolio.sell(this.ticker, this.price, this.NumShares, this.account);
		//PortfolioController.portfolio.addTransaction(t);

	}

	@Override
	void unexecute() {
		//PortfolioController.portfolio.buy(this.ticker, this.price, this.NumShares, this.account);
		//PortfolioController.portfolio.removeTransaction(t);

	}


}
