package userInterface;
/**
 * Created by Quang on 11/1/15.
 */
import javax.swing.*;

import model.Account;

import java.util.ArrayList;

public class TransferFrame extends JFrame{
    JButton transferConfirm = new JButton("Transfer");
    JLabel fromLabel = new JLabel("From:");
    JLabel toLabel = new JLabel("To:");
    JComboBox<String> accountDropListFrom = new JComboBox<String>();
    JComboBox<String> accountDropListTo = new JComboBox<String>();
    JLabel amountLabel = new JLabel("Amount:");
    JTextField amountField = new JTextField();

    public TransferFrame(String name){
        super(name);
        setLayout(null);
        setSize(350, 250);
        setLocation(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        transferConfirm.setSize(transferConfirm.getPreferredSize());
        transferConfirm.setLocation(210, 120);
        fromLabel.setSize(fromLabel.getPreferredSize());
        fromLabel.setLocation(10, 90);
        accountDropListFrom.setLocation(50, 90);
        accountDropListFrom.setSize(100,20);
        accountDropListTo.setLocation(50, 120);
        accountDropListTo.setSize(100,20);
        toLabel.setSize(toLabel.getPreferredSize());
        toLabel.setLocation(10, 120);
        amountLabel.setSize(amountLabel.getPreferredSize());
        amountLabel.setLocation(15, 50);
        amountField.setColumns(15);
        amountField.setToolTipText("Enter the amount you want to transfer");
        amountField.setSize(amountField.getPreferredSize());
        amountField.setLocation(90, 50);

        add(transferConfirm);
        add(fromLabel);
        add(toLabel);
        add(accountDropListFrom);
        add(accountDropListTo);
        add(amountField);
        add(amountLabel);

        setVisible(true);
    }

    public void setAccountDropLists(ArrayList<Account> allAccount){
        for(Account a : allAccount){
            accountDropListFrom.addItem(a.getName());
            accountDropListTo.addItem(a.getName());
        }
    }

    public String getAmountField(){
    	return amountField.getText();
    }
    
    public String getToField(){
    	return (String) accountDropListTo.getSelectedItem();
    }
    
    public String getFromField(){
    	return (String) accountDropListFrom.getSelectedItem();
    }
    
    public Float getAmount(){
    	Float value = 0.00f;
    	try{
    	value =Float.valueOf(amountField.getText());
    	}catch(NumberFormatException e1){
    		JOptionPane.showMessageDialog(this, "Please enter a valid amount");
    	}
    	return value;
    	
    }
}
