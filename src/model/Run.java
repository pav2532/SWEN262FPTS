package model;
import userInterface.LoginScreen;

public class Run {
   
   public static void main(String[] args) {
	  if(args.length != 0){
		  CommandLine removeAccount = new CommandLine();
		  removeAccount.findAccount(args[0], "src/Account.txt");
	  }
      LoginScreen firstScreen = new LoginScreen();
   }
}

