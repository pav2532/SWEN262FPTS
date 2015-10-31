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
   JMenuItem equityOption = new JMenuItem("Equity");
   JMenuItem accountOption = new JMenuItem("Account");
   JMenuItem holdingOption = new JMenuItem("Holding");
   JMenuItem transactionOption = new JMenuItem("Transaction");
   JMenuItem addAccount = new JMenuItem("New Account");
   JMenuItem logout = new JMenuItem("Logout");
   
   public MenuBar(){
      
      exit.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            System.exit(0);
         }
      });
      
      file.add(open);
      file.add(save);
      portfolioOption.add(equityOption);
      portfolioOption.add(accountOption);
      portfolioOption.add(holdingOption);
      portfolioOption.add(transactionOption);
      file.add(addAccount);
      file.add(logout);
      file.add(exit);
      add(file);
      add(portfolioOption);

   }
}
