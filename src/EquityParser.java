import java.util.*;
import java.io.*;


public class EquityParser {
	
	
	public EquitiesHolder findAccount(String userName, String filename) throws IOException{
	String line = null;
  	Holding newEquity = null;
  	EquitiesHolder temp = new EquitiesHolder();
  	
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			while ((line = br.readLine()) != null) {
				String[] separateValue = line.split(",");
				newEquity = new Holding(separateValue[0],separateValue[1], Float.valueOf(separateValue[3]));	
				String[] indices = new String[separateValue.length-3];
				for(int i = 3; i < separateValue.length; i++){
					indices[i-3] = separateValue[i]; 
				}
				temp.addEquity(newEquity, indices);			
			}
				
			return temp;
				//ticker symbol, equity name, initial per share price
	}
	
}
  


