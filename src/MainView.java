import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainView extends JFrame implements Observer {
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
                   portfolio.addAccount(name, amount, "10/29/15");
                   accFrame.dispose();
                   pane.displayAccountTable(portfolio.allAccount);
               }
            });
         }
      });
      
      menu.accountOption.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            pane.displayAccountTable(portfolio.allAccount);
            remove(sell);
            remove(buy);
            revalidate();
            repaint();
         } 
      });
      menu.holdingOption.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            pane.displayHoldingTable(portfolio.holding);
            remove(buy);
            add(sell, BorderLayout.SOUTH);
            revalidate();
            repaint();
         }
      });

      menu.transactionOption.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            pane.displayTransactionTable(portfolio.allTransaction);
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
               bFrame.setAccountDropList(portfolio.allAccount);
               bFrame.buyConfirm.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                     for(Account a : portfolio.allAccount){
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
                     pane.displayHoldingTable(portfolio.holding);
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
      
      add(buy, BorderLayout.SOUTH);
      setJMenuBar(menu);
      setVisible(true);
   }

   @Override
   public void update(ArrayList<Account> account, HashMap<String, Integer> holding, ArrayList<Transaction> allTransaction){
      portfolio.allAccount = account;
      portfolio.allTransaction = allTransaction;
      portfolio.holding = holding;
   }
}
