package command;

public class AddAccountCommand extends AbstractCommand {

	Account account;
	
	public AddAccountCommand(Account account){
		this.account = account;
	}
	
	@Override
	void execute() {
		//PortfolioController.Portfolio.addAccount(account);
		
		
	}

	@Override
	void unexecute() {
		//PortfolioController.Portfolio.removeAccount(account);
		
		

	}

}
