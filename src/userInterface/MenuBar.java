package userInterface;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class MenuBar extends JMenuBar{
   JMenu file = new JMenu("File");
   JMenu portfolioOption = new JMenu("Portfolio");
   JMenuItem exit = new JMenuItem("Exit");
   JMenuItem open = new JMenuItem("Import");
   JMenuItem save = new JMenuItem("Save");
   JMenuItem equityOption = new JMenuItem("Stock Market");
   JMenuItem accountOption = new JMenuItem("Accounts");
   JMenuItem holdingOption = new JMenuItem("Holdings");
   JMenuItem transactionOption = new JMenuItem("Transaction History");
   JMenuItem wacthList	 = new JMenuItem("WacthList");
   JMenuItem addAccount = new JMenuItem("New Account");
   JMenuItem logout = new JMenuItem("Logout");
   JMenuItem undo = new JMenuItem("Undo");
   JMenuItem redo = new JMenuItem("Redo");
   
   public MenuBar(){
      
      exit.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
        	 int n = JOptionPane.showConfirmDialog(null, "Exit?");
				if(n == 0){
					try {
						MainView.portfolio.save();
						ScrollPane.equities.save();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
            System.exit(0);
         }
         }
      });
      
      file.add(open);
      file.add(save);
      file.add(undo);
      file.add(redo);
      portfolioOption.add(equityOption);
      portfolioOption.add(accountOption);
      portfolioOption.add(holdingOption);
      portfolioOption.add(transactionOption);
      portfolioOption.add(wacthList);
      file.add(addAccount);
      file.add(logout);
      file.add(exit);
      add(file);
      add(portfolioOption);

   }
}
