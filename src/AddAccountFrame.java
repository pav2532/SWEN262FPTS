import javax.swing.*;

public class AddAccountFrame extends JFrame {
   JLabel accNameLabel = new JLabel("Account Name:");
   JTextField accNameField = new JTextField();
   JLabel amountLabel = new JLabel("Amount:");
   JTextField amountField = new JTextField();
   JButton create = new JButton("Create account");
   
   public AddAccountFrame(String name){
      super(name);
      setLayout(null);
      setSize(350, 250);
      setLocation(500, 250);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
      accNameField.setColumns(15);
      accNameField.setToolTipText("Please enter your bank name");
      accNameField.setSize(accNameField.getPreferredSize());
      accNameField.setLocation(120, 50);
      accNameLabel.setSize(accNameLabel.getPreferredSize());
      accNameLabel.setLocation(10, 50);
      
      amountLabel.setSize(amountLabel.getPreferredSize());
      amountLabel.setLocation(10, 80);
      amountField.setColumns(15);
      amountField.setToolTipText("Please enter your bank amount");
      amountField.setSize(amountField.getPreferredSize());
      amountField.setLocation(120, 80);
      
      create.setSize(create.getPreferredSize());
      create.setLocation(150, 150);
      
      add(accNameField);
      add(accNameLabel);
      add(amountLabel);
      add(amountField);
      add(create);
      setVisible(true);
   }
   
   public String getAccName(){

      return accNameField.getText();
   }
   
   public String getAccAmount(){

      return amountField.getText();
   }
}
