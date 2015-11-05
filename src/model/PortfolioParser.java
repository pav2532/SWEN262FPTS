package model;
import java.util.*;

import transactions.Transaction;
import transactions.buyTransaction;
import transactions.sellTransaction;
import transactions.transferTransaction;

import java.io.*;

public class PortfolioParser {
	
	public Portfolio importFile(String filename){
		String line = null;
		Portfolio portfolio = null;
		ArrayList<Account> allAccount = new ArrayList<Account>();
		ArrayList<Transaction> allTransaction = new ArrayList<Transaction>();
		HashMap<String, Integer> holding = new HashMap<String, Integer>();
		ArrayList<watchListHolding> watchList = new ArrayList<watchListHolding>();
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	
			while((line = bufferedReader.readLine()) != null){
				//System.out.println(line);
				String[] separateValue = line.split("\",\"");
				//System.out.println(separateValue[0]);
				
				// Strip out any unnecessary character around the word
				for(int i = 0; i< separateValue.length; i++){
					separateValue[i] = separateValue[i].replaceAll("[^a-zA-Z0-9/.]", "");
				}
				// Bank account line
				if(separateValue[0].equals("B")){
					Account account = new Account(separateValue[1], Float.valueOf(separateValue[2]), separateValue[3]);
					allAccount.add(account);
				// Stock/Holding
				}else if(separateValue[0].equals("S")){
					int value = Integer.valueOf(separateValue[2]);
					holding.put(separateValue[1], value);
				}else if(separateValue[0].equals("W")){
					//System.out.println(separateValue[5]);
					watchListHolding wLH;
					if(separateValue.length-1>6){
						wLH = new watchListHolding(Float.parseFloat(separateValue[1]), Float.parseFloat(separateValue[2]), 
								new Holding(separateValue[3].replaceAll("[^a-zA-Z0-9.]", ""),separateValue[4], Float.parseFloat(separateValue[5].replaceAll("[^0-9.]", "")), (separateValue[6]+ ", " + separateValue[7].replaceAll("[^a-zA-Z0-9.]", ""))));
					}else{
						wLH = new watchListHolding(Float.parseFloat(separateValue[1]), Float.parseFloat(separateValue[2]), 
							new Holding(separateValue[3].replaceAll("[^a-zA-Z0-9.]", ""),separateValue[4], Float.parseFloat(separateValue[5].replaceAll("[^0-9.]", "")), (separateValue[6]+ ", ")));
					}
					watchList.add(wLH);
				}else{
					if(separateValue[1].equals("S")){
						sellTransaction sell = new sellTransaction(separateValue[2], separateValue[3], Float.valueOf(separateValue[4]), Integer.valueOf(separateValue[5]));
						allTransaction.add(sell);
					}else if(separateValue[1].equals("T")){
						transferTransaction transfer = new transferTransaction(separateValue[2], separateValue[3], Float.valueOf(separateValue[4]));
						allTransaction.add(transfer);
					}else{
						System.out.println(separateValue[3]);
						buyTransaction buy = new buyTransaction(separateValue[2], separateValue[3], Float.valueOf(separateValue[4]), Integer.valueOf(separateValue[5]));
						allTransaction.add(buy);
					}
				}
			}
			String name = filename.replace("Portfolios/", "");
			name = name.replace(".txt", "");
			portfolio = new Portfolio(name, allAccount, holding, allTransaction, watchList);
			bufferedReader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Unable to open " + filename + " file.");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return portfolio;
	}
	
}
