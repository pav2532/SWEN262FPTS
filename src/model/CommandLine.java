package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class CommandLine {
	public String findAccount(String userName, String filename) {
		String line;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				String[] separatedValue = line.split(",");
				if (userName.equals(separatedValue[0])) {
					writer.write(line + System.getProperty("line.separator"));
				}
				else {
					System.out.println("Could not delete user");
				}
			}
			writer.close();
			reader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
