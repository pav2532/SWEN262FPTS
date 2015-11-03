package command;

import model.Account;
import userInterface.MainView;

public class AddAccountCommand extends AbstractCommand {

	Account account;
	
	public AddAccountCommand(Account account){
		this.account = account;
	}
	
	@Override
	public
	void execute() {
		MainView.portfolio.addAccount(account);
		
		
	}

	@Override
	public
	void unexecute() {
		MainView.portfolio.removeAccount(account);
		
		

	}

}
