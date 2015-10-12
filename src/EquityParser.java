import java.io.*;
import java.util.*;

public class EquityParser {
	
	public void importEquity(String filename){
		String line = null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while((line = reader.readLine()) != null){
				
				String[] separatedValue = line.split("\",\"");
				for(String value : separatedValue){
					String stripWord = value.replaceAll("[^a-zA-Z0-9.()]", "");
					
				}
			}
			reader.close();
		}catch(FileNotFoundException e){
			System.out.println("Unable to open " + filename + " file.");
		}catch(IOException e){
			e.printStackTrace();
		} 
	}
}
