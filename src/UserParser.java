import java.io.*;
import java.util.*;

public class UserParser {
	public String findAccount(String userName, String filename) {
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				String[] separatedValue = line.split(",");
				if (userName.equals(separatedValue[0])) {
					return separatedValue[1];
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
