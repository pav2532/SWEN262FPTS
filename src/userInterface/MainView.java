package userInterface;
import javax.swing.*;

import model.Account;
import model.EquitiesHolder;
import model.InsufficientFundsException;
import model.NotEnoughOwnedSharesException;
import model.Observer;
import model.Portfolio;
import model.PortfolioParser;
import transactions.Transaction;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainView extends JFrame implements Observer {
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   private Portfolio portfolio;
   private JFileChooser fileChooser = new JFileChooser();
   private String selectedTickerSymbol;
   private String selectedSharePrice;
   private boolean access;
   
   public MainView(String name, Portfolio p){
      super(name);
      this.portfolio = p;
      this.portfolio.register(this); 
      
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
      
      final ScrollPane pane = new ScrollPane();
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
            dispose();
            LoginScreen firstScreen = new LoginScreen();
            JOptionPane.showMessageDialog(null, "Log out sucessful");
         }
      });
      
      menu.save.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            try {
               portfolio.save();
            } catch (IOException e1) {
               e1.printStackTrace();
            }
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
                   portfolio.addAccount(temp);
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
                     for(Account a : portfolio.getAllAccount()){
                        if(a.getName().equals(bFrame.accountDropList.getSelectedItem())){
                           int share = Integer.valueOf(bFrame.getNumShare());
                           float price = Float.valueOf(selectedSharePrice);
                           try {
                              portfolio.buy(selectedTickerSymbol, price, share, a);
                           } catch (InsufficientFundsException e1) {
                              e1.printStackTrace();
                           } catch (NumberFormatException e1){
                              e1.printStackTrace();
                           }
                           break;
                        }
                     }
                     bFrame.dispose();
                     pane.displayHoldingTable(portfolio.getHolding());
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
                      for(Account a : portfolio.getAllAccount()){
                         if(a.getName().equals(bFrame.accountDropList.getSelectedItem())){
                            int share = Integer.valueOf(bFrame.getNumShare());
                            float price = Float.valueOf(selectedSharePrice);
                            try {
                               portfolio.sell(selectedTickerSymbol, price, share, a);
                            } catch (NumberFormatException e1){
                               e1.printStackTrace();
                            } catch (NotEnoughOwnedSharesException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            break;
                         }
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
                 selectedSharePrice = pane.equities.getHolding(selectedTickerSymbol).getPrice().toString();
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
      portfolio.setAllAccount(account);
      portfolio.setAllTransaction(allTransaction);
      portfolio.setHolding(holding);
   }
}
