package userInterface;
import javax.swing.*;

import model.Account;
import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;
import model.Observer;
import model.Portfolio;
import model.PortfolioParser;
import transactions.Transaction;
import command.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainView extends JFrame implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Portfolio portfolio;
	private JFileChooser fileChooser = new JFileChooser();
	private String selectedTickerSymbol;
	private String selectedSharePrice;
	private boolean access;
	private Stack<AbstractCommand> undo;
	private Stack<AbstractCommand> redo;
	final ScrollPane pane;

	public MainView(String name, Portfolio p){
		super(name);
		this.portfolio = p;
		this.portfolio.register(this); 
		undo = new Stack<AbstractCommand>();
		redo = new Stack<AbstractCommand>();

		setLayout(new BorderLayout());
		setSize(1000,500);
		setLocation(150, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JButton buy = new JButton("Buy Stock");
		final JButton sell = new JButton("Sell Stock");
		final JButton transfer = new JButton("Transfer Money");
		final JButton notify = new JButton("Check notification");
		final JButton remove = new JButton("Remove");
		final JButton add = new JButton("Add");
		final JFrame wacthlistButtons = new JFrame();



		pane = new ScrollPane();
		add(pane, BorderLayout.CENTER);
		pane.displayEquityTable();

		MenuBar menu = new MenuBar();

		menu.open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					String file = fileChooser.getSelectedFile().toString();
					PortfolioParser portfolioParser = new PortfolioParser();
					portfolio = portfolioParser.importFile(file);
				}
			}
		});

		menu.logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int n = JOptionPane.showConfirmDialog(pane, "Logout?");
				if(n == 0){
					try {
						portfolio.save();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dispose();
					LoginScreen firstScreen = new LoginScreen();
					JOptionPane.showMessageDialog(null, "Log out sucessful");
				}
			}
		});

		menu.save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					portfolio.save();
					JOptionPane.showMessageDialog(pane, "Save Successful");
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(pane, "Save Unsuccessful: IO Error encountered");
				}

			}
		});

		menu.undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				try {
					AbstractCommand undoneCommand = undo.pop();
					undoneCommand.unexecute();
					redo.push(undoneCommand);
				} catch (NotEnoughOwnedSharesException | InsufficientFundsException e1) {
					JOptionPane.showMessageDialog(pane, e1.getMessage());
					e1.printStackTrace();
				} catch (EmptyStackException e2){
					JOptionPane.showMessageDialog(pane, "Nothing to undo");
				}

				revalidate();
				repaint();
			}
		});

		menu.redo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					AbstractCommand undoneCommand = redo.pop();
					undoneCommand.execute();
					undo.push(undoneCommand);
				} catch (NotEnoughOwnedSharesException | InsufficientFundsException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}catch (EmptyStackException e2){
					JOptionPane.showMessageDialog(pane, "Nothing to redo");
				}

				revalidate();
				repaint();
			}

		});

		menu.equityOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pane.displayEquityTable();
				remove(sell);
				remove(transfer);
				add(buy, BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
		});

		menu.addAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final AddAccountFrame accFrame = new AddAccountFrame("New Account");
				accFrame.create.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String name = accFrame.getAccName();
						Float amount = Float.valueOf(accFrame.getAccAmount());
						Account temp = new Account(name, amount, "10/29/15");
						AbstractCommand addAccount = new AddAccountCommand(temp);
						try {
							addAccount.execute();
						} catch (InsufficientFundsException | NotEnoughOwnedSharesException e1) {
							e1.printStackTrace();
						}
						undo.push(addAccount);
						accFrame.dispose();
						pane.displayAccountTable(portfolio.getAllAccount());
					}
				});
			}
		});

		menu.accountOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pane.displayAccountTable(portfolio.getAllAccount());
				remove(sell);
				remove(buy);
				add(transfer, BorderLayout.SOUTH);
				revalidate();
				repaint();
			} 
		});

		menu.holdingOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pane.displayHoldingTable(portfolio.getHolding());
				remove(buy);
				remove(transfer);
				add(sell, BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
		});

		menu.transactionOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pane.displayTransactionTable(portfolio.getAllTransaction());
				remove(buy);
				remove(sell);
				revalidate();
				repaint();
			}
		});

		buy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(access){
					final BuyFrame bFrame = new BuyFrame("Stock purchase");
					bFrame.setAccountDropList(portfolio.getAllAccount());
					bFrame.buyConfirm.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							String a = String.valueOf(bFrame.accountDropList.getSelectedItem());
							int share = Integer.valueOf(bFrame.getNumShare());
							Float price = Float.valueOf(selectedSharePrice);
							try {
								AbstractCommand buy = new BuyCommand(selectedTickerSymbol, price, share, a);
								buy.execute();
								undo.push(buy);
								//portfolio.buy(selectedTickerSymbol, price, share, a);
							} catch (InsufficientFundsException e1) {
								e1.printStackTrace();
							} catch (NumberFormatException e1){
								e1.printStackTrace();
							} catch (NotEnoughOwnedSharesException e1) {
								e1.printStackTrace();

							}



							bFrame.dispose();
							pane.displayHoldingTable(portfolio.getHolding());
							remove(buy);
							remove(transfer);
							add(sell, BorderLayout.SOUTH);
							revalidate();
							repaint();
						}
					});
				}
			}
		});

		//Sell stock
		sell.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(access){
					final SellFrame bFrame = new SellFrame("Stock purchase");
					bFrame.setAccountDropList(portfolio.getAllAccount());
					bFrame.sellConfirm.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							String a = String.valueOf(bFrame.accountDropList.getSelectedItem());
							int share = Integer.valueOf(bFrame.getNumShare());
							Float price = Float.valueOf(selectedSharePrice);
							try {
								AbstractCommand sell = new SellCommand(selectedTickerSymbol, price, share, a);
								sell.execute();
								undo.push(sell);
								//portfolio.sell(selectedTickerSymbol, price, share, a);
							} catch (NumberFormatException e1){
								e1.printStackTrace();
							} catch (NotEnoughOwnedSharesException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InsufficientFundsException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}



							bFrame.dispose();
							pane.displayHoldingTable(portfolio.getHolding());
						}
					});
				}
			}
		});

		buy.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if(pane.equityTable.getSelectedRow() != -1){
					int tickerRow = pane.equityTable.getSelectedRow();
					int sharePriceRow = tickerRow;
					selectedTickerSymbol = pane.equityTable.getModel().getValueAt(tickerRow, 0).toString();
					selectedSharePrice = pane.equityTable.getModel().getValueAt(sharePriceRow, 2).toString();
					access = true;
				}else{
					access = false;
					JOptionPane.showMessageDialog(null, "Please select a stock");
				}
			}
		});

		//selected sell command
		sell.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if(pane.holdingTable.getSelectedRow() != -1){
					int tickerRow = pane.holdingTable.getSelectedRow();
					int sharePriceRow = tickerRow;
					selectedTickerSymbol = pane.holdingTable.getModel().getValueAt(tickerRow, 0).toString();
					selectedSharePrice = ScrollPane.equities.getHolding(selectedTickerSymbol).getPrice().toString();
					access = true;
				}else{
					access = false;
					JOptionPane.showMessageDialog(null, "Please select a stock");
				}
			}
		});

		transfer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TransferFrame tFrame = new TransferFrame("Transfer Money");
			}
		});

		transfer.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){

			}
		});

		add(buy, BorderLayout.SOUTH);
		setJMenuBar(menu);
		setVisible(true);
	}

	@Override
	public void update(ArrayList<Account> account, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction){
		pane.updateTables(account, holding, allTransaction);
	}
}
