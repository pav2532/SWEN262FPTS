package userInterface;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

import model.Account;
import model.EquitiesHolder;
import model.EquityParser;
import model.Holding;
import transactions.Transaction;

public class ScrollPane extends JScrollPane{
	JTable equityTable;
	JTable accountTable;
	JTable holdingTable;
	JTable wacthListTable;
	JTextPane transactionPane;
	protected static EquitiesHolder equities;

	public ScrollPane(){
		setSize(this.getPreferredSize());
		setLocation(100, 200);
	}

	public void displayEquityTable(){
		String[] equityColumnName = {"Ticker Symbol", "Equity Name", "Share Price", "Sector"};
		// equity data is going to get the info from equity class    
		EquityParser parser = new EquityParser();  
		try {  
			equities = parser.findAccount("src/equities.txt");  
		}catch (IOException e2) {   
			e2.printStackTrace();  
		}  
		HashMap<String,Holding> results = new HashMap<String,Holding>();  
		for(Entry<String, HashMap<String,Holding>> IndexEntry : EquitiesHolder.Indices.entrySet()){  
			HashMap<String,Holding> value = IndexEntry.getValue();  
			for(Entry<String,Holding> entry : value.entrySet()){  
				String key = entry.getKey();  
				results.put(key, entry.getValue());  
			}  
		}  
		Object[][] equityData = new Object [results.size()][4];  
		int row = 0;  
		for(Entry<String,Holding> entry : results.entrySet()){  
			equityData[row][0] = entry.getValue().getTickerSymbol();  
			equityData[row][1] = entry.getValue().getName();  
			equityData[row][2] = entry.getValue().getPrice();  
			equityData[row][3] = entry.getValue().getSectors();  
			row ++;  
		}  
		equityTable = new JTable(equityData, equityColumnName);
		equityTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
		equityTable.setFillsViewportHeight(true);

		setViewportView(equityTable);
		equities.updateThread();
	}

	public void displayAccountTable(ArrayList<Account> account){
		String[] accountColumnName = {"Name", "Balance", "Date Created"};
		Object[][] accountData = new Object[account.size()][];
		int index = 0;
		for(Account a : account){
			Object[] data = new Object[3];
			data[0] = a.getName();
			data[1] = a.getBalance();
			data[2] = a.getDateCreated();
			accountData[index] = data;
			index++;
		}
		accountTable = new JTable(accountData, accountColumnName);
		setViewportView(accountTable);
	}

	public void displayHoldingTable(HashMap<String, Integer> holding){
		String[] holdingColumnName = {"Ticker Symbol", "Number of Share"};
		Object[][] holdingData = new Object[holding.size()][];
		int index = 0;
		for(String key : holding.keySet()){
			Object[] data = new Object[2];
			data[0] = key;
			data[1] = holding.get(key);
			holdingData[index] = data;
			index++;
		}
		holdingTable = new JTable(holdingData, holdingColumnName);
		setViewportView(holdingTable);
	}

	public void displayTransactionTable(ArrayList<Transaction> transaction){
		transactionPane = new JTextPane();
		transactionPane.setEditable(false);
		StyledDocument doc = transactionPane.getStyledDocument();

		for(Transaction t : transaction){
			try {
				doc.insertString(0, t.getTransaction() + "\n", null);
			} catch(Exception e){
				System.out.println(e);
			}
		}

		transactionPane.setDocument(doc);
		setViewportView(transactionPane);
	}
	
	public void displayWacthListTable(ArrayList w){
		//Need code wacthlist implemented to do it
		setViewportView(wacthListTable);
	}

	/**
	 * @author Mitchell
	 * 
	 * recreates all data tables when given the updated data. Data for all tables is passed in regardless of which table changed.
	 * This is used to update all tables that need to be changed without switching away from your current view.
	 * 
	 * @param account 	account data
	 * @param holding 	holding data
	 * @param transaction 	transaction data
	 */
	public void updateTables(ArrayList<Account> account, HashMap<String, Integer> holding, ArrayList<Transaction> transaction){
		//account table
		String[] accountColumnName = {"Name", "Balance", "Date Created"};
		Object[][] accountData = new Object[account.size()][];
		int index = 0;
		for(Account a : account){
			Object[] data = new Object[3];
			data[0] = a.getName();
			data[1] = a.getBalance();
			data[2] = a.getDateCreated();
			accountData[index] = data;
			index++;
		}
		accountTable = new JTable(accountData, accountColumnName);
		
		//holding table
		String[] holdingColumnName = {"Ticker Symbol", "Number of Share"};
		Object[][] holdingData = new Object[holding.size()][];
		index = 0;
		for(String key : holding.keySet()){
			Object[] data = new Object[2];
			data[0] = key;
			data[1] = holding.get(key);
			holdingData[index] = data;
			index++;
		}
		holdingTable = new JTable(holdingData, holdingColumnName);
		
		//transactions table
		transactionPane = new JTextPane();
		transactionPane.setEditable(false);
		StyledDocument doc = transactionPane.getStyledDocument();

		for(Transaction t : transaction){
			try {
				doc.insertString(0, t.getTransaction() + "\n", null);
			} catch(Exception e){
				System.out.println(e);
			}
		}

		transactionPane.setDocument(doc);
		setViewportView(holdingTable);

	}
}
