import java.util.*;
import java.io.*;

public class PortfolioParser {
	
	public void importFile(String filename){
		String line = null;
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
	
			while((line = bufferedReader.readLine()) != null){
				String[] separateValue = line.split(",");
				for(String value : separateValue){
					System.out.println(value);
				}
			}
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
