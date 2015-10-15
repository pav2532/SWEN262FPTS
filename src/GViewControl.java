import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class GViewControl extends JFrame implements Observer{
	
	private Portfolio portfolio;
	private JTextField usernameLogIn = new JTextField();
   private JTextField numShare = new JTextField();
	private JPasswordField passwordLogIn = new JPasswordField();
	private JLabel numberOfShare = new JLabel("Number of Share");
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private JButton signUp = new JButton("Sign Up");
	private JButton signIn = new JButton("Sign In");
	private JButton buy = new JButton("Buy");
	private JButton sell = new JButton("Sell");
	private JButton buyConfirm = new JButton("Confirm");
	private JMenuBar menu = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenu portfolioOption = new JMenu("Portfolio");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem open = new JMenuItem("Import");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem equityOption = new JMenuItem("Equity");
	private JMenuItem accountOption = new JMenuItem("Account");
	private JMenuItem holdingOption = new JMenuItem("Holding");
	private JMenuItem transactionOption = new JMenuItem("Transaction");
	private JComboBox<String> accountDropList;
	private JScrollPane scrollPane;
	private JTable equityTable;
	private JTable accountTable;
	private JTable holdingTable;
	private JFileChooser fileChooser = new JFileChooser("Import");
	private ArrayList<Account> allAccount;
	private String userAccount;
	private String pass;
	private String selectedTickerSymbol;
	private String selectedSharePrice;
	
	public GViewControl(String name){
		super(name);
		//this.portfolio = p;
		//this.portfolio.addObserver(this);
		
		// Just for testing purpose, we need to import the portfolio right away to get
      // the data of the portfolio
      PortfolioParser portfolioParser = new PortfolioParser();
      portfolio = portfolioParser.importFile("src/ExamplePortfolio.txt");
      
		String[] equityColumnName = {"Ticker Symbol", "Equity Name", "Share Price", "Sector"};
		String[] accountColumnName = {"Name", "Balance", "Date Created"};

		String[] holdingColumnName = {"Ticker Symbol", "Number of Share"};
		
		// equity data is going to get the info from equity class
			
		Object[][] equityData = {
				{"3", "1", "2", "3"},
		};
		
		
		
		// Populate account data for the account table
		allAccount = portfolio.getAllAccount();
		Object[][] accountData = new Object[allAccount.size()][];
		for(int i = 0; i < allAccount.size(); i++){
			// Get each account from the account list
			// Get the name, balance, and the created date -> assign to the data list
			// Add the data into the accountData which will be used for the JTable
			for(Account a : allAccount){
				Object[] data = new Object[3];
				data[0] = a.getName();
				data[1] = a.getBalance();
				data[2] = a.getDateCreated();
				accountData[i] = data;
			}
		}
		
		// Create a drop down for all available account
		accountDropList = new JComboBox<String>();
		for(Account a : allAccount){
		   accountDropList.addItem(a.getName());
		}		
		
		// Populate holding data for the holding table
		HashMap<String, Integer> holding = portfolio.getHolding();
		Object[][] holdingData = new Object[holding.size()][];
		for(int i = 0; i < holding.size(); i++){
			for(String key : holding.keySet()){
				Object[] data = new Object[2];
				data[0] = key;
				data[1] = holding.get(key);
				holdingData[i] = data;
			}
		}
		
		setLayout(null);
		setSize(350,250);
		setLocation(500, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		portfolioOption.add(accountOption);
		equityTable = new JTable(equityData, equityColumnName);
		equityTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
		equityTable.setFillsViewportHeight(true);
		
		accountTable = new JTable(accountData, accountColumnName);
		accountTable.setPreferredScrollableViewportSize(new Dimension(100, 100)); 
		accountTable.setFillsViewportHeight(true);
		
		holdingTable = new JTable(holdingData, holdingColumnName);
		holdingTable.setPreferredScrollableViewportSize(new Dimension(100, 100)); 
		holdingTable.setFillsViewportHeight(true);
		
		scrollPane = new JScrollPane(equityTable);
		scrollPane.setSize(scrollPane.getPreferredSize());
		scrollPane.setLocation(100, 200);
		

		
		buyConfirm.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e){
		       for(Account a : allAccount){
		          if(a.getName().equals(selectedTickerSymbol)){
		             try {
                     portfolio.buy(selectedTickerSymbol, Float.valueOf(selectedSharePrice), Integer.valueOf(numShare.getText()), a);
                  } catch (NumberFormatException e1) {
                     e1.printStackTrace();
                  } catch (InsufficentFundsException e1) {
                     e1.printStackTrace();
                  }
		            break;
		          }
		       }
		   }
		});
		buy.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int tickerRow = equityTable.getSelectedRow();
				int tickerCol = equityTable.getSelectedColumn();
				int sharePriceRow = tickerRow;
				int sharePriceCol = tickerCol +2;
				selectedTickerSymbol = equityTable.getModel().getValueAt(tickerRow, tickerCol).toString();
				selectedSharePrice = equityTable.getModel().getValueAt(sharePriceRow, sharePriceCol).toString();
			}
		});
		
		buy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame buyFrame = new JFrame();
				buyFrame.setLayout(null);
				buyFrame.setSize(350, 250);
				buyFrame.setLocation(500, 250);
		      buyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		      buyFrame.setVisible(true);
		      numberOfShare.setSize(numberOfShare.getPreferredSize());
		      numberOfShare.setLocation(10, 50);
		      numShare.setColumns(15);
		      numShare.setToolTipText("Enter the amount you want to buy");
		      numShare.setSize(numShare.getPreferredSize());
		      numShare.setLocation(120, 50);
		      accountDropList.setLocation(10, 80);
		      accountDropList.setSize(accountDropList.getPreferredSize());
		      buyFrame.add(accountDropList);
		      buyFrame.add(numberOfShare);
		      buyFrame.add(numShare);
			}
		});

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

		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					portfolio.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		equityOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(equityTable);
			}
		});

		accountOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(accountTable);
			} 
		});
		
		holdingOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrollPane.setViewportView(holdingTable);
			}
		});
		
		file.add(open);
		file.add(save);
		file.add(equityOption);
		file.add(portfolioOption);
		portfolioOption.add(accountOption);
		portfolioOption.add(holdingOption);
		portfolioOption.add(transactionOption);
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
