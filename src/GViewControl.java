import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class GViewControl extends JFrame implements Observer{
	
	private Portfolio portfolio;
	private JTextField usernameLogIn = new JTextField();
	private JPasswordField passwordLogIn = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private JButton signUp = new JButton("Sign Up");
	private JButton signIn = new JButton("Sign In");
	private JMenuBar menu = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem open = new JMenuItem("Import");
	private JMenuItem equityOption = new JMenuItem("Equity");
	private JMenuItem portfolioOption = new JMenuItem("Portfolio");
	private JMenuItem accountOption = new JMenuItem("Account");
	private JScrollPane scrollPane;
	private JTable equityTable;
	private JTable portfolioTable;
	private JTable accountTable;
	private JFileChooser fileChooser = new JFileChooser("Import");
	private String userAccount;
	private String pass;
	
	public GViewControl(String name){
		super(name);
		//this.portfolio = p;
		//this.portfolio.addObserver(this);
		String[] equityColumnName = {"Ticker Symbol", "Equity Name", "Share Price", "Sector"};
		String[] portfolioColumnName = {"Account", "Holding", "Transaction"};
		String[] accountColumnName = {"Name", "Balance", "Date Created"};
		// Example data
		Object[][] equityData = {
				{"3", "1", "2", "3"},
		};
		Object[][] portfolioData = {
				{"User10", "hlo", "1000"}	
		};
		Object[][] accountData = {
				{"abc123", "10000", "10/10/2010"}
		};
		
		setLayout(null);
		setSize(350,250);
		setLocation(500, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		portfolioTable = new JTable(portfolioData, portfolioColumnName);
		portfolioTable.setPreferredScrollableViewportSize(new Dimension(100, 100)); 
		portfolioTable.setFillsViewportHeight(true);
		
		equityTable = new JTable(equityData, equityColumnName);
		equityTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
		equityTable.setFillsViewportHeight(true);
		
		accountTable = new JTable(accountData, accountColumnName);
		accountTable.setPreferredScrollableViewportSize(new Dimension(100, 100)); 
		accountTable.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(equityTable);
		scrollPane.setSize(scrollPane.getPreferredSize());
		scrollPane.setLocation(100, 200);
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					String file = fileChooser.getSelectedFile().toString();
					PortfolioParser portfolioParser = new PortfolioParser();
					portfolio = portfolioParser.importFile(file);
				}
			}
		});
		
		equityOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(equityTable);
			}
		});
		
		portfolioOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(portfolioTable);
			}
		});
		
		accountOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(accountTable);
			} 
		});
		file.add(open);
		file.add(equityOption);
		file.add(portfolioOption);
		file.add(accountOption);
		file.add(exit);
		menu.add(file);
		
		passwordLabel.setSize(passwordLabel.getPreferredSize());
		passwordLabel.setLocation(10, 60);
		add(passwordLabel);
		
		usernameLabel.setSize(usernameLabel.getPreferredSize());
		usernameLabel.setLocation(10, 30);
		add(usernameLabel);
		
		usernameLogIn.setColumns(15);
		usernameLogIn.setSize(usernameLogIn.getPreferredSize());
		usernameLogIn.setToolTipText("Please enter your username");
		usernameLogIn.setLocation(80, 25);
		add(usernameLogIn);
		
		passwordLogIn.setColumns(15);
		passwordLogIn.setSize(passwordLogIn.getPreferredSize());
		passwordLogIn.setToolTipText("Please enter your password");
		passwordLogIn.setLocation(80, 60);
		add(passwordLogIn);
		
		signUp.setSize(signUp.getPreferredSize());
		signUp.setLocation(10, 100);
		signUp.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				pass = passwordLogIn.getText();
				userAccount = usernameLogIn.getText();
				UserParser userParser = new UserParser();
				String existAccount = userParser.findAccount(userAccount, "src/Account.txt");
				if(existAccount == null){
					User newUser = new User(userAccount);
					String encryptedPass = newUser.encrypt(pass);
					newUser.setPassword(encryptedPass);
					try {
						newUser.transferUsernamePassword();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Sign up successfully");
				}else{
					JOptionPane.showMessageDialog(null, "This username is already exist. Please choose another username");
				}
			}
		});
		add(signUp);
		
		signIn.setSize(signIn.getPreferredSize());
		signIn.setLocation(100, 100);
		signIn.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				pass = passwordLogIn.getText();
				userAccount = usernameLogIn.getText();
				UserParser userParser = new UserParser();
				String associatePassword = userParser.findAccount(userAccount, "src/Account.txt");
				if(associatePassword != null){
					User user = new User(userAccount);
					user.setPassword(associatePassword);
					if(user.authenticate(user.encrypt(pass))){
						getContentPane().removeAll();
						getContentPane().repaint();
						JOptionPane.showMessageDialog(null, "Log in sucessful");
						setSize(1000, 500);
						setLocation(150, 150);
						setJMenuBar(menu);
						setContentPane(scrollPane);
					}else{
						JOptionPane.showMessageDialog(null, "Not a correct password");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please check your username or sign up new account");
				}
			} 
		});
		add(signIn);
		
		setVisible(true);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[]){
		GViewControl view = new GViewControl("FPTS");
	}
}
