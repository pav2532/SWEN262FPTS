import java.util.*;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

public class GViewControl extends JFrame {
	private User currentUser;
	private Portfolio portfolio;
	private Transaction transaction;
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
	private JComboBox<String> accountDropList = new JComboBox<String>();
	private JScrollPane scrollPane;
	private JTable equityTable;
	private JTable accountTable;
	private JTable holdingTable;
	private JTextArea transactionList;
	private JFileChooser fileChooser = new JFileChooser("Import");
	private ArrayList<Account> allAccount;
	private String userAccount;
	private String pass;
	private String selectedTickerSymbol;
	private String selectedSharePrice;
	private JFrame buyFrame = new JFrame();
	HashMap<String, Integer> holding;
	Object[][] holdingData;
	private Object[][] accountData;
	private String[] holdingColumnName = { "Ticker Symbol", "Number of Share" };
	private String[] accountColumnName = { "Name", "Balance", "Date Created" };
	private static EquitiesHolder equities;

	public GViewControl(String name) {
		super(name);

		String[] equityColumnName = { "Ticker Symbol", "Equity Name", "Share Price", "Sector" };

		// equity data is going to get the info from equity class

		ArrayList list = new ArrayList();
		EquityParser parser = new EquityParser();
		try {
			equities = parser.findAccount("src/equities.txt");
		} catch (IOException e2) {

			e2.printStackTrace();
		}
		HashMap<String, Holding> results = new HashMap<String, Holding>();
		for (Entry<String, HashMap<String, Holding>> IndexEntry : equities.Indices.entrySet()) {
			HashMap<String, Holding> value = IndexEntry.getValue();
			for (Entry<String, Holding> entry : value.entrySet()) {
				String key = entry.getKey();
				results.put(key, entry.getValue());
			}
		}
		Object[][] equityData = new Object[results.size()][4];
		int row = 0;
		for (Entry<String, Holding> entry : results.entrySet()) {
			ArrayList tempList = new ArrayList();
			equityData[row][0] = entry.getValue().getTickerSymbol();
			equityData[row][1] = entry.getValue().getName();
			equityData[row][2] = entry.getValue().getPrice();
			equityData[row][3] = entry.getValue().getSectors();
			row++;
		}

		setLayout(null);
		setSize(350, 250);
		setLocation(500, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		portfolioOption.add(accountOption);
		equityTable = new JTable(equityData, equityColumnName);
		equityTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
		equityTable.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(equityTable);
		scrollPane.setSize(scrollPane.getPreferredSize());
		scrollPane.setLocation(100, 200);

		buyConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Account a : allAccount) {
					if (a.getName().equals(accountDropList.getSelectedItem())) {
						try {
							portfolio.buy(selectedTickerSymbol, Float.valueOf(selectedSharePrice),
									Integer.valueOf(numShare.getText()), a);
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} catch (InsufficientFundsException e1) {
							e1.printStackTrace();
						}
						break;
					}
				}

				holding = portfolio.getHolding();
				holdingData = new Object[holding.size()][];

				int index = 0;
				for (Entry<String, Integer> entry : portfolio.getHolding().entrySet()) {
					Object[] data = new Object[2];
					data[0] = entry.getKey();
					data[1] = entry.getValue();
					holdingData[index] = data;
					index++;
				}

				allAccount = portfolio.getAllAccount();
				accountData = new Object[allAccount.size()][];
				int j = 0;
				for (Account a : allAccount) {
					Object[] data = new Object[3];
					data[0] = a.getName();
					data[1] = a.getBalance();
					data[2] = a.getDateCreated();
					accountData[j] = data;
					j++;
				}

				holdingTable = new JTable(holdingData, holdingColumnName);
				accountTable = new JTable(accountData, accountColumnName);
				buyFrame.dispose();
				scrollPane.setViewportView(holdingTable);

			}
		});
		buy.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (equityTable.getSelectedRow() != -1) {
					int tickerRow = equityTable.getSelectedRow();
					int sharePriceRow = tickerRow;
					selectedTickerSymbol = equityTable.getModel().getValueAt(tickerRow, 0).toString();
					selectedSharePrice = equityTable.getModel().getValueAt(sharePriceRow, 2).toString();
				} else {
					JOptionPane.showMessageDialog(buyFrame, "Please select a stock");
				}
			}
		});

		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				buyConfirm.setSize(buyConfirm.getPreferredSize());
				buyConfirm.setLocation(150, 100);
				buyFrame.add(buyConfirm);
				buyFrame.add(accountDropList);
				buyFrame.add(numberOfShare);
				buyFrame.add(numShare);
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String file = fileChooser.getSelectedFile().toString();
					PortfolioParser portfolioParser = new PortfolioParser();
					portfolio = portfolioParser.importFile(file);
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					portfolio.save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		equityOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(equityTable);
				remove(sell);
				add(buy, BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
		});

		accountOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(accountTable);
			}
		});

		holdingOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(buy);
				add(sell, BorderLayout.SOUTH);
				scrollPane.setViewportView(holdingTable);
				revalidate();
				repaint();
			}
		});

		transactionOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(transactionList);
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
		signUp.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				pass = passwordLogIn.getText();
				userAccount = usernameLogIn.getText();
				UserParser userParser = new UserParser();
				String existAccount = userParser.findAccount(userAccount, "src/Account.txt");
				if (existAccount == null) {
					User newUser = new User(userAccount);
					String encryptedPass = newUser.encrypt(pass);
					newUser.setPassword(encryptedPass);
					try {
						File file = new File("Portfolios/" + newUser.getUserName() + ".txt");

						boolean success = file.createNewFile();
						if (success) {
							newUser.transferUsernamePassword();
						}

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Sign up successfully");
				} else {
					JOptionPane.showMessageDialog(null,
							"This username is already exist. Please choose another username");
				}
			}
		});
		add(signUp);

		signIn.setSize(signIn.getPreferredSize());
		signIn.setLocation(100, 100);
		signIn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				pass = passwordLogIn.getText();
				userAccount = usernameLogIn.getText();
				UserParser userParser = new UserParser();
				String associatePassword = userParser.findAccount(userAccount, "src/Account.txt");
				if (associatePassword != null) {
					currentUser = new User(userAccount);
					currentUser.setPassword(associatePassword);
					if (currentUser.authenticate(currentUser.encrypt(pass))) {
						getContentPane().removeAll();
						getContentPane().repaint();
						JOptionPane.showMessageDialog(null, "Log in sucessful");
						setSize(1000, 500);
						setLocation(150, 150);
						setJMenuBar(menu);
						setLayout(new BorderLayout());
						add(scrollPane, BorderLayout.CENTER);
						add(buy, BorderLayout.SOUTH);
					} else {
						JOptionPane.showMessageDialog(null, "Not a correct password");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please check your username or sign up new account");
				}
			}
		});
		add(signIn);

		setVisible(true);

	}

	public void populate() {
		// Populate account data for the account table
		allAccount = portfolio.getAllAccount();
		accountData = new Object[allAccount.size()][];
		int index = 0;
		for (Account a : allAccount) {
			Object[] data = new Object[3];
			data[0] = a.getName();
			data[1] = a.getBalance();
			data[2] = a.getDateCreated();
			accountData[index] = data;
			index++;
		}

		// Create a drop down for all available account
		accountDropList = new JComboBox<String>();
		for (Account a : allAccount) {
			accountDropList.addItem(a.getName());
		}

		// Populate holding data for the holding table
		holding = portfolio.getHolding();
		holdingData = new Object[holding.size()][];
		index = 0;
		for (String key : holding.keySet()) {
			Object[] data = new Object[2];
			data[0] = key;
			data[1] = holding.get(key);
			holdingData[index] = data;
			index++;
		}

		accountTable = new JTable(accountData, accountColumnName);
		accountTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
		accountTable.setFillsViewportHeight(true);

		holdingTable = new JTable(holdingData, holdingColumnName);
		holdingTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
		holdingTable.setFillsViewportHeight(true);
		return;
	}

	public static void main(String args[]) {
		GViewControl view = new GViewControl("FPTS");
		while (view.currentUser == null) {

		}
		// Just for testing purpose, we need to import the portfolio right away
		// to get
		// the data of the portfolio
		PortfolioParser portfolioParser = new PortfolioParser();
		view.portfolio = portfolioParser.importFile("Portfolios/" + view.currentUser.getUserName() + ".txt");

		view.populate();
	}
}
