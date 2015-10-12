import java.io.*;
import java.util.*;

public class Portfolio extends Observable {
	private String name;
	private int userID;
	private ArrayList<Account> allAccount;
	private HashMap<String, Integer> holding;
	private ArrayList<Transaction> allTransaction;
	
	public Portfolio(ArrayList<Account> allAccount, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction){
		this.allAccount = allAccount;
		this.holding = holding;
		this.allTransaction = allTransaction;
	}
	
	public ArrayList<Account> getAllAccount(){
		return allAccount;
	}
	
	public ArrayList<Transaction> getAllTransaction(){
		return allTransaction;
	}
	
	public HashMap<String, Integer> getHolding(){
		return holding;
	} 
}
