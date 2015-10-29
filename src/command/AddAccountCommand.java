package command;

public class AddAccountCommand extends AbstractCommand {

	Account account;
	
	public AddAccountCommand(Account account){
		this.account = account;
	}
	
	@Override
	void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	void unexecute() {
		// TODO Auto-generated method stub

	}

}
