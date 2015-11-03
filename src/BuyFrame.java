import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BuyFrame extends JFrame {
   JButton buyConfirm = new JButton("Buy");
   JLabel accLabel = new JLabel("Account:");
   JComboBox<String> accountDropList = new JComboBox<String>();
   JLabel numberOfShare = new JLabel("Number of share:");
   JTextField numShare = new JTextField();
   
   public BuyFrame(String name){
      super(name);
      setLayout(null);
      setSize(350, 250);
      setLocation(500, 250);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      buyConfirm.setSize(buyConfirm.getPreferredSize());
      buyConfirm.setLocation(240, 120);
      accLabel.setSize(accLabel.getPreferredSize());
      accLabel.setLocation(10, 80);
      accountDropList.setLocation(119, 80);
      accountDropList.setSize(100,20);
      numberOfShare.setSize(numberOfShare.getPreferredSize());
      numberOfShare.setLocation(10, 50);
      numShare.setColumns(15);
      numShare.setToolTipText("Enter the amount you want to buy");
      numShare.setSize(numShare.getPreferredSize());
      numShare.setLocation(120, 45);
      
      add(buyConfirm);
      add(accLabel);
      add(accountDropList);
      add(numberOfShare);
      add(numShare);
      
      setVisible(true);
   } 
   
   public void setAccountDropList(ArrayList<Account> allAccount){
      for(Account a : allAccount){
         accountDropList.addItem(a.getName());
      }
   }
   
   public String getNumShare(){
      return numShare.getText();
   }
}
