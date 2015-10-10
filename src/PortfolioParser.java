import java.util.*;
import java.io.*;

public class PortfolioParser {
	
	public void importFile(String filename){
		String line = null;
		Portfolio portfolio;
		ArrayList<Account> allAccount = new ArrayList<Account>();
		HashMap<String, Integer> holding = new HashMap<String, Integer>();
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	
			while((line = bufferedReader.readLine()) != null){
				String[] separateValue = line.split(",");
				
				// Strip out any unnecessary character around the word
				for(int i = 0; i< separateValue.length; i++){
					separateValue[i] = separateValue[i].replaceAll("[^a-zA-Z0-9]", "");
				}
				// Bank account line
				if(separateValue[0].equals("B")){
					Account account = new Account(separateValue[1], Float.valueOf(separateValue[2]), separateValue[3]);
					allAccount.add(account);
				// Stock/Holding
				}else if(separateValue[0].equals("S")){
					int value = Integer.valueOf(separateValue[2]);
					holding.put(separateValue[1], value);
				}else{
					
				}
			}
			System.out.println(allAccount);
			System.out.println(holding);
			bufferedReader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Unable to open " + filename + " file.");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		PortfolioParser parser = new PortfolioParser();
		parser.importFile("/Users/QuangVu/Desktop/workspace/SE262-FPTS/src/Account.txt");
	}
}
