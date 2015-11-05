package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Holds all of the Holding objects for the 'world'. Has search functionality and update method for prices
 * @author Mitchell
 *
 */
public class EquitiesHolder {

	public static HashMap<String, HashMap<String, Holding>> Indices = new HashMap<String, HashMap<String, Holding>>();
	private int updateTime = 1;

	public int getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}

	public void updateThread(){
		new Thread (){
			public void run(){
				do{
				try {
					sleep(updateTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					updatePrices();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(true);
			}
		};
	}
	/**
	 * Adds the given holding to the EquitiesHolder (the 'world')
	 * 
	 * @param newEquity
	 *            Holding object to add to the 'world'
	 * @param Index
	 *            Array of Indices the holding exists in
	 */
	public void addEquity(Holding newEquity, String[] Index) {
		for (int i = 0; i < Index.length; i++) {
			if (Indices.containsKey(Index[i])) {

				Indices.get(Index[i]).put(newEquity.getTickerSymbol(), newEquity);

			} else {

				HashMap<String, Holding> newIndex = new HashMap<String, Holding>();
				Indices.put(Index[i], newIndex);
				Indices.get(Index[i]).put(newEquity.getTickerSymbol(), newEquity);

			}
		}
	}// end addEquity

	/**
	 * uses the TickerSearch method to get a specific holding using 
	 * the provided ticker symbol
	 * 
	 * @param ticker ticker symbol of the Holding to be retrieved
	 * @return returns the holding that matches the given ticker symbol
	 */
	public static Holding getHolding(String ticker) {
		HashMap<String, Holding> temp = new HashMap<String, Holding>();
		temp = TickerSearch(temp, ticker);
		System.out.println(temp.get(ticker).getPrice());
		return temp.get(ticker);
	}

	/**
	 * using the search terms, calls the needed search functions and returns a
	 * Hashmap of the Holdings that match the search terms.
	 * 
	 * @param searchTerms
	 *            - Array of terms to search with
	 * @return Hashmap of search results
	 */
	public HashMap<String, Holding> Search(String[] searchTerms) {
		HashMap<String, Holding> results = new HashMap<String, Holding>();

		if (searchTerms[0] != null) {
			results = IndexSearch(results, searchTerms[0]);
		}

		if (searchTerms[1] != null) {
			results = TickerSearch(results, searchTerms[1]);
		}

		if (searchTerms[2] != null) {
			results = NameSearch(results, searchTerms[3]);
		}

		return results;
	}// end Search()

	/**
	 * Searches through the Indices HashMap and adds any Indices that contain
	 * the search term.
	 * 
	 * @param results
	 *            HashMap to store results in
	 * @param term
	 *            Term to search with. Uses pattern matches
	 * @return returns a HashMap of results
	 */
	private HashMap<String, Holding> IndexSearch(HashMap<String, Holding> results, String term) {
		// search the index HashMaps for the search term
		for (Entry<String, HashMap<String, Holding>> IndexEntry : Indices.entrySet()) {
			String key = IndexEntry.getKey();
			HashMap<String, Holding> value = IndexEntry.getValue();

			if (key.contains(term)) {
				results.putAll(value);
			}
		}

		return results;
	}// end IndexSearch()

	/**
	 * If the results @param is empty, every Index HashMap is searched for keys
	 * that contain the term @param, otherwise the given results HashMap is
	 * searched and elements that cannot be matched to the search term are
	 * removed.
	 * 
	 * @param results
	 *            HashMap to store results in
	 * @param term
	 *            Term to search against Holding Ticker Symbols. Uses pattern
	 *            matching
	 * @return returns a HashMap of results
	 */
	private static HashMap<String, Holding> TickerSearch(HashMap<String, Holding> results, String term) {
		// if no results have been provided, search the entire world
		// if there are provided results, narrow them using the search term
		if (results.isEmpty()) {
			for (Entry<String, HashMap<String, Holding>> IndexEntry : Indices.entrySet()) {
				HashMap<String, Holding> value = IndexEntry.getValue();

				for (Entry<String, Holding> entry : value.entrySet()) {
					String key = entry.getKey();
					if (key.contains(term)) {
						results.put(key, entry.getValue());
					}
				}
			}

		} else {
			for (Entry<String, Holding> entry : results.entrySet()) {
				String key = entry.getKey();

				if (!key.contains(term)) {
					results.remove(key);
				}
			}
		}
		return results;
	}// end TickerSearch()

	/**
	 * If the results @param is empty, every Index HashMap is searched for
	 * Holding names that contain the term @param, otherwise the given results
	 * HashMap is searched and elements that cannot be matched to the search
	 * term are removed.
	 * 
	 * @param results
	 *            HashMap to store results in
	 * @param term
	 *            Term to search against holding name with. Uses pattern
	 *            matching
	 * @return returns a HashMap of results
	 */
	private HashMap<String, Holding> NameSearch(HashMap<String, Holding> results, String term) {
		// if no results have been provided, search the entire world
		// if there are provided results, narrow them using the search term
		if (results.isEmpty()) {
			for (Entry<String, HashMap<String, Holding>> IndexEntry : Indices.entrySet()) {
				HashMap<String, Holding> value = IndexEntry.getValue();

				for (Entry<String, Holding> entry : value.entrySet()) {
					String key = entry.getKey();

					if (entry.getValue().getName().contains(term)) {
						results.put(key, entry.getValue());
					}
				}
			}
		} else {
			for (Entry<String, Holding> entry : results.entrySet()) {
				String key = entry.getKey();

				if (!key.contains(term)) {
					results.remove(key);
				}
			}
		}

		return results;
	}// end NameSearch()

	public static void save() throws IOException{
		FileWriter file = new FileWriter("src/equities.txt");
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(file);
			for (Entry<String, HashMap<String, Holding>> IndexEntry : Indices.entrySet()) {
				HashMap<String, Holding> value = IndexEntry.getValue();

				for (Entry<String, Holding> entry : value.entrySet()) {
					Holding holding = entry.getValue();
					writer.println("\""+holding.getTickerSymbol()+"\",\""+holding.getName()+"\",\""+holding.getPrice()+"\",\""+holding.getSectors()+"\"");
				}
			}
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}

	public static void updatePrices() throws IOException{
		HashMap<String, Float> data;
		ArrayList<String> tickers = new ArrayList<String>();
		for (Entry<String, HashMap<String, Holding>> IndexEntry : Indices.entrySet()) {
			HashMap<String, Holding> value = IndexEntry.getValue();

			for (Entry<String, Holding> entry : value.entrySet()) {
				tickers.add(entry.getKey());
			}
		}

		data = (new YahooAPI()).update(tickers);
		for(Entry<String, Float> entry : data.entrySet()){
			getHolding(entry.getKey()).setPrice(entry.getValue());
			System.out.println("I'm Updating prices!"+entry.getKey()+entry.getValue());
			System.out.println(getHolding(entry.getKey()).getPrice());
		}
		
		save();
	}
}
