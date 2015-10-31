import java.util.*;
import java.io.*;


public class EquityParser {
	
	
	public EquitiesHolder findAccount(String filename) throws IOException{
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
				String[] separateValue = line.split("\",\"");
				if(separateValue.length == 5)
					newEquity = new Holding(separateValue[0].replaceAll("[^a-zA-Z0-9.]", ""),separateValue[1], Float.valueOf(separateValue[2].replaceAll("[^0-9.]", "")), (separateValue[3]+ ", " + separateValue[4].replaceAll("[^a-zA-Z0-9.]", "")));
				else
					newEquity = new Holding(separateValue[0].replaceAll("[^a-zA-Z0-9.]", ""),separateValue[1], Float.valueOf(separateValue[2].replaceAll("[^0-9.]", "")), separateValue[3].replaceAll("[^a-zA-Z0-9.]", ""));	
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
  


