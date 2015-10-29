package command;

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
	void execute() {
		//PortfolioController.Portfolio.transfer(accountTo, accountFrom, amount);
		//PortfolioController.Portfolio.addTransaction(t);

	}

	@Override
	void unexecute() {
		//PortfolioController.Portfolio.transfer(accountFrom, accountTo, amount);
		//PortfolioController.Portfolio.removeTransaction(t);

	}

}
