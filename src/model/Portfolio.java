package model;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import transactions.Transaction;

public class Portfolio implements Subject{
	ArrayList<Observer> observers;
	private String name;
	private ArrayList<Account> allAccount;
	private HashMap<String, Integer> holding;
	private ArrayList<Transaction> allTransaction;
	private ArrayList<watchListHolding> watchList;
	
	public Portfolio(String name, ArrayList<Account> allAccount, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction, ArrayList<watchListHolding> watchList){
		this.allAccount = allAccount;
		this.holding = holding;
		this.allTransaction = allTransaction;
		this.name = name;
		this.observers = new ArrayList<Observer>();
		this.watchList = watchList;
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
         observer.update(allAccount, holding, allTransaction, watchList);
      }
   }
   
   /**
    *  @author Mitchell
	 * Getter for the account list
	 * 
	 * @return The account list for the portfolio. 
	 */
	public ArrayList<Account> getAllAccount() {
		return allAccount;
	}
	
	/**
	 *  @author Mitchell
	 * Setter for the account list
	 * 
	 * @param a new Account list
	 */
	public void setAllAccount(ArrayList<Account> a) {
		this.allAccount = a;
	}

	/**
	 *  @author Mitchell
	 * Getter for the Transaction list
	 * 
	 * @return  The Transaction list.
	 */
	public ArrayList<Transaction> getAllTransaction() {
		return allTransaction;
	}
	
	/**
	 *  @author Mitchell
	 * Setter for the Transaction list
	 * 
	 * @param t new Transaction list
	 */
	public void setAllTransaction(ArrayList<Transaction>  t) {
		this.allTransaction = t;
	}

	/**
	 *  @author Mitchell
	 * Getter for the holding hashmap
	 * 
	 * @return	the hashmap of holdings the portfolio owns.
	 */
	public HashMap<String, Integer> getHolding() {
		return holding;
	}

	/**
	 *  @author Mitchell
	 * setter for the Holding Hashmap
	 * 
	 * @param h new Holding HashMap
	 */
	public void setHolding(HashMap<String, Integer> h) {
		this.holding = h;
	}
	
	/**
	 *  @author Mitchell
	 * adds the provided Transaction to the Transaction list
	 * 
	 * @param t 	The Transaction to be added to the portfolio's list.
	 */
	public void addTransaction(Transaction t){
		allTransaction.add(t);
		notifyObserver();
	}
	
	/**
	 *  @author Mitchell
	 * removes a Transaction that matches the provided Transaction
	 * 
	 * @param t 	The Transaction to be removed from the Transaction list.
	 */
	public void removeTransaction(Transaction t){
		allTransaction.remove(t);
		notifyObserver();
	}
	
	/**
	 *  @author Mitchell
	 * adds the given account to the portfolio's account list.
	 * 
	 * @param a 	The account to be added to the portfolio's account list.
	 */
	public void addAccount(Account a){
		allAccount.add(a);
		notifyObserver();
	}
	
	/**
	 *  @author Mitchell
	 * removes the given account from the portfolio's account list.
	 * 
	 * @param a 	The account to be removed from the portfolio's account list.
	 */
	public void removeAccount(Account a){
		allAccount.remove(a);
		notifyObserver();
	}
	
	/**
	 * @author Mitchell
	 * 
	 * getter for the watchList
	 * @return the portfolio's watchList.
	 */
	public ArrayList<watchListHolding> getWatchList(){
		return watchList;
	}
	
	/**
	 * @author Mitchell
	 * 
	 * sets the portfolio's watchList to the parameter.
	 * @param watchList the new watchList.
	 */
	public void setWatchList(ArrayList<watchListHolding> watchList){
		this.watchList = watchList;
	}
	
	/**
	 * @author Mitchell
	 * 
	 * given a ticker, returns a watchListHolding if the watchList contains a holding with a matching ticker
	 *
	 * @param ticker ticker used to find the watchListHolding in the watchList.
	 * @return returns a watchListHolding or null if non match the given ticker.
	 */
	public watchListHolding watchListContains(String ticker){
		for(watchListHolding h : watchList){
			if(h.getHolding().getTickerSymbol().equals(ticker)){
				return h;
			}
		}
		return null;
	}
	
	/**
	 *  @author Mitchell
	 * transfers the given amount from accountFrom to account To
	 * 
	 * @param accountTo 	The account to transfer funds from.
	 * @param accountFrom 	The account to transfer funds to.
	 * @param amount 		The amount to be transfered between accounts.
	 * @throws InsufficientFundsException 	Exception thrown when accountFrom does not have enough funds to transfer the given amount.
	 */
	public void transfer(String accountTo, String accountFrom, Float amount) throws InsufficientFundsException{
		int result = 0;
		for(int i = 0; i < allAccount.size(); i++){
			if(allAccount.get(i).getName().equals(accountFrom)){
				 result = allAccount.get(i).removeFunds(amount);
				if(result == 0){
					throw new InsufficientFundsException("There are not enough funds to complete the transfer");
					
				}
			}
			
			if(allAccount.get(i).getName().equals(accountTo) && result != 0){
				allAccount.get(i).addFunds(amount);
			}
		}
		notifyObserver();
	}
   
	public void save() throws IOException{
		FileWriter file = new FileWriter("Portfolios/"+name+".txt");
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(file);
			for(int i = 0; i < allAccount.size(); i++){
				writer.println("\"B\",\""+allAccount.get(i).getName()+"\",\""+allAccount.get(i).getBalance()+"\",\""+allAccount.get(i).getDateCreated()+"\"");
			}
			for(int i = 0; i< allTransaction.size(); i++){
				writer.println(allTransaction.get(i).saveTransaction());
			}
			for(Entry<String, Integer> IndexEntry : holding.entrySet()){
				writer.println("\"S,\"" + IndexEntry.getKey() + "\",\"" + IndexEntry.getValue() + "\"");
			}
			for(watchListHolding h : watchList){
				writer.println("\"W\","+h.save());
			}
		}
		finally{
			if (writer != null){
				writer.close();
			}
		}
	}
	
	/**
	 *  @author Mitchell
	 * Simulates buying 'NumShares' of the stock associated with 'ticker'. This is done by
	 * adding the ticker and amount to the portfolios holding hashmap and removing the 
	 * funds needed (price*NumShares) from the given account.
	 * 
	 * @param ticker 	ticker symbol of the stock being purchased.
	 * @param price 	price of the stock being purchased.
	 * @param NumShares 	Number of shares of the stock being purchased.
	 * @param account 	account used to pay for the purchase of the stock.
	 * @throws InsufficientFundsException 	Exception thrown when accountFrom does not have enough funds to transfer the given amount.
	 */
	public void buy(String ticker, Float price, int NumShares, String account) throws InsufficientFundsException {
		int index = 0;
		
		for(Account a : allAccount){
			if(a.getName().equals(account)){
				index = allAccount.indexOf(a);
			}
		 }
		if (index == -1) {
			return;
		}

		if (1 == allAccount.get(index).removeFunds(price * NumShares)) {
			if (holding.containsKey(ticker)) {
				int oldShares = holding.get(ticker);
				holding.put(ticker, NumShares + oldShares);
			} else {
				holding.put(ticker, NumShares);
			}
		} else {
			throw new InsufficientFundsException("That account does not have enough funds for your purchase");
		}
		notifyObserver();
		return;
	}
	public void add(String ticker, Float high, Float low) throws AlreadyContainsException{
		if(watchList.size()==0){
			watchList.add(new watchListHolding(high, low, EquitiesHolder.getHolding(ticker)));
		}else{
			for(watchListHolding w : watchList){
				if (w.getHolding().getTickerSymbol().equals(ticker)){
					throw new AlreadyContainsException("Ticker Symbol is already in WacthList");
					
				}else{
					System.out.println("It added");
					watchList.add(new watchListHolding(high, low, EquitiesHolder.getHolding(ticker)));
					break;
				}
				
			}
			notifyObserver();
		}
	}
	public void remove(String ticker){
		int index = 0;
		for(watchListHolding w: watchList){
			if(w.getHolding().getTickerSymbol().equals(ticker))
				break;
			index++;
		}
		watchList.remove(index);
		
		notifyObserver();
	
	}
	public watchListHolding getWacthListHolding(String ticker){
		for(watchListHolding w : watchList){
			if (w.getHolding().getTickerSymbol().equals(ticker)){
				return w;
			}	
		}
		return null;
	}

	/**
	 * @author Mitchell
	 * 
	 * Simulates selling a stock the portfolio owns. This is done by reducing the value (which represents number of shares)
	 * of the k,v pair in the holding hashmap equal to the NumShares param. Then funds are added to the specified account
	 * equal to the price of the stock * the number of shares being sold. If all of the shares are sold, the holding is 
	 * removed from the hashmap.
	 * 
	 * @param ticker 	ticker symbol of the stock being sold.
	 * @param price 	price of the stock being sold.
	 * @param NumShares 	Number of shares of the stock being sold.
	 * @param account 	account the profits are transfered into.
	 * @throws NotEnoughOwnedSharesException 	Thrown when there are less shares owned than trying to be sold.
	 */
	public void sell(String ticker, Float price, int NumShares, String account) throws NotEnoughOwnedSharesException {
		int index = 0;
		
		for(Account a : allAccount){
			if(a.getName().equals(account)){
				index = allAccount.indexOf(a);
			}
		 }		if (index == -1) {
			return;
		}

		allAccount.get(index).addFunds(price * NumShares);
		if (holding.get(ticker) == NumShares) {
			holding.remove(ticker);
		} else if (holding.get(ticker) > NumShares) {
			holding.put(ticker, holding.get(ticker) - NumShares);
		} else {
			throw new NotEnoughOwnedSharesException("You do not own that many shares of this stock");
		}
		
		notifyObserver();
	}
}