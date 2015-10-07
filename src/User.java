import java.util.*;
import java.io.*;

/*
 * User class represent each user using this system
 */
public class User {
	private int ID;
	private String userName;
	private String encryptedPassword;
	private static int numberOfUser = 0;
	
	// Constructor
	public User(String userName){
		this.ID = ++numberOfUser;
		this.userName = userName;
	}
	
	// get the User's ID
	public int getID(){
		return ID;
	}
	
	//get the User's name
	public String getUserName(){
		return userName;
	}
	
	// get the User's encrypted password
	public String getEncryptedPassword(){
		return encryptedPassword;
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
	
	// Compare the encrypted codes
	public boolean authenticate(String password){
		String pass = new String(this.getEncryptedPassword());
		if(pass.equals(password)){
			return true;
		}
		return false;
	}
	
	public void transferUsernamePassword() throws IOException{
		FileWriter file = new FileWriter("/Users/QuangVu/Desktop/workspace/SE262-FPTS/src/Account.txt", true);
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(file);
			writer.println(userName + "," + encryptedPassword);
		}
		finally{
			if (writer != null){
				writer.close();
			}
		}
	}
	
	// Main function for testing input and encrypted password
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter your username");
		String username = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();
		String username2 = scanner.nextLine();
		String password2 = scanner.nextLine();
		scanner.close();
		
		User newUser = new User(username);
		newUser.encryptedPassword = newUser.encrypt(password);
		try {
			newUser.transferUsernamePassword();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User newUser2 = new User(username2);
		newUser2.encryptedPassword = newUser2.encrypt(password2);
		try {
			newUser2.transferUsernamePassword();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
