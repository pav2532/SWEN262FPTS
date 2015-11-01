import java.util.*;
import java.io.*;

/*
 * User class represent each user using this system
 */
public class User implements GetUserData{
	private int ID;
	private String userName;
	private String encryptedPassword;
	private static int numberOfUser = 0;

	// Constructor
	public User(String userName) {
		this.ID = ++numberOfUser;
		this.userName = userName;
	}

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
	public int getID() {
        return ID;
    }

	public void setPassword(String pass){
		this.encryptedPassword = pass;
	}

	//Reverse the password
	public String encrypt(String password){
		String reverse = "";
		char[] pass = password.toCharArray();
		for(int i = (pass.length -1); i >= 0; i--){
			reverse += pass[i];
		}
		return reverse;
	}

	public boolean authenticate(String password){
		if(encryptedPassword.equals(password)){
			return true;
		}
		return false;
	}


	//Record new account in the account file
	public void transferUsernamePassword() throws IOException{

		FileWriter file = new FileWriter("src/Account.txt", true);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.println(userName + "," + encryptedPassword);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
