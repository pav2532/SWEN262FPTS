package model;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import transactions.Transaction;

public class Portfolio implements Subject{
   String name;
	ArrayList<Observer> observers;
	ArrayList<Account> allAccount;
	HashMap<String, Integer> holding;
	ArrayList<Transaction> allTransaction;
	
	public Portfolio(String name, ArrayList<Account> allAccount, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction){
		this.allAccount = allAccount;
		this.holding = holding;
		this.allTransaction = allTransaction;
		this.name = name;
		this.observers = new ArrayList<Observer>();
	}
	
   @Override
   public void register(Observer o) {
      observers.add(o);
   }

   @Override
   public void unregister(Observer o) {
      int observerIndex = observers.indexOf(o);
      observers.remove(observerIndex);
   }

   @Override
   public void notifyObserver() {
      for(Observer observer : observers){
         observer.update(allAccount, holding, allTransaction);
      }
   }
   public void addAccount(String name, Float balance, String dateCreated){
      Account newAccount = new Account(name, balance, dateCreated);
      allAccount.add(newAccount);
      notifyObserver();
   }
   
	public void save() throws IOException{
		FileWriter file = new FileWriter("Portfolios/"+name+".txt");
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
	
	public void buy(String ticker, Float price, int NumShares, Account account) throws InsufficientFundsException{
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
				throw new InsufficientFundsException();
			}
			notifyObserver();
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
   		
   		notifyObserver();
	}

    public void transfer(int transferAmount, Account sender, Account receiver){

    }
}