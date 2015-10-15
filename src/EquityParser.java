import java.util.*;
import java.io.*;


public class EquityParser {
	public String findAccount(String userName, String filename) throws IOException{
	String line = null;
  	ArrayList<String> input = new ArrayList<String>();
  	String ticker = "";
  	Holding newEquity = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			while ((line = br.readLine()) != null) {
				String[] separateValue = line.split(",");
				for(int i = 0; i< separateValue.length; i++){
					addEquity(newEquity, separateValue[i]);			}
				}
			//return equity;
			
				//ticker symbol, equity name, initial per share price
	}
}
  


