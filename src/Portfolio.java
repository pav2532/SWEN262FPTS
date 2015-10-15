import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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
	
	public void save() throws IOException{
		FileWriter file = new FileWriter("src/ExamplePortfolio.txt");
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(file);
			for(int i = 0; i < allAccount.size(); i++){
				writer.println("B,"+allAccount.get(i).getName()+","+allAccount.get(i).getBalance()+","+allAccount.get(i).getDateCreated());
			}
			for(int i = 0; i< allTransaction.size(); i++){
				writer.println(allTransaction.get(i).saveTransaction());
			}
			for(Entry<String, Integer> IndexEntry : holding.entrySet()){
				
				writer.println("S,"+IndexEntry.getKey()+","+IndexEntry.getValue());
				
			}
		}
		finally{
			if (writer != null){
				writer.close();
			}
		}
	}
	
	public void buy(String ticker, Float price, int NumShares, Account account) throws InsufficentFundsException{
			int index = allAccount.lastIndexOf(account);
			if(index == -1){
				return;
			}
			
			if( 1 == allAccount.get(index).removeFunds(price*NumShares)){
				if(holding.containsKey(ticker)){
					int oldShares = holding.get(ticker);
					holding.put(ticker, NumShares+oldShares);
				}else{
					holding.put(ticker, NumShares);
				}
			}else{
				throw new InsufficentFundsException();
			}
		
		notifyObservers();
		return;
	}
	
	public void sell(String ticker, Float price, int NumShares, Account account) throws NotEnoughOwnedSharesException{
		int index = allAccount.lastIndexOf(account);
		if(index == -1){
			return;
		}
		
		allAccount.get(index).addFunds(price*NumShares);
		if(holding.get(ticker) == NumShares){
			holding.remove(ticker);
		}else if(holding.get(ticker) > NumShares){
			holding.put(ticker, holding.get(ticker)-NumShares);
		}else{
			throw new NotEnoughOwnedSharesException();
		}
	}
}
