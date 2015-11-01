package command;

public class BuyCommand extends AbstractCommand {

	String ticker;
	Float price;
	int NumShares;
	Account account;

	public BuyCommand(String ticker, Float price, int NumShares, Account account){
		this.account = account;
		this.NumShares = NumShares;
		this.price = price;
		this.ticker = ticker;
	}
	
	@Override
	void execute() {
		

	}

	@Override
	void unexecute() {
		// TODO Auto-generated method stub

	}

}
