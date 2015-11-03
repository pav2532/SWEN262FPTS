package command;

import model.Account;
import userInterface.MainView;

/**
 * 
 * @author Mitchell
 * Command for adding accounts to a portfolio
 */
public class AddAccountCommand extends AbstractCommand {

	Account account;
	
	public AddAccountCommand(Account account){
		this.account = account;
	}
	

	/**
	 * adds account
	 */
	@Override
	public
	void execute() {
		MainView.portfolio.addAccount(account);
		
		
	}

	/**
	 * removes account
	 */
	@Override
	public
	void unexecute() {
		MainView.portfolio.removeAccount(account);
		
		

	}

}
