import java.io.*;
import java.util.*;

public class Portfolio extends Observable {
	private String name;
	private int userID;
	private ArrayList<Account> allAccount;
	private HashMap<String, Integer> holding;
	
	public Portfolio(ArrayList<Account> allAccount, HashMap<String, Integer> holding){
		this.allAccount = allAccount;
		this.holding = holding;
	}
}
