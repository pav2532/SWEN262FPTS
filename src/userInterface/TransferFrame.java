package userInterface;
/**
 * Created by Quang on 11/1/15.
 */
import javax.swing.*;

import model.Account;

import java.util.ArrayList;

public class TransferFrame extends JFrame{
    JButton transferConfirm = new JButton("Transfer");
    JLabel accLabel = new JLabel("From:");
    JComboBox<String> accountDropList = new JComboBox<String>();
    JLabel amountLabel = new JLabel("Amount:");
    JTextField amountField = new JTextField();

    public TransferFrame(String name){
        super(name);
        setLayout(null);
        setSize(350, 250);
        setLocation(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        transferConfirm.setSize(transferConfirm.getPreferredSize());
        transferConfirm.setLocation(210, 150);
        accLabel.setSize(accLabel.getPreferredSize());
        accLabel.setLocation(15, 90);
        accountDropList.setLocation(110, 90);
        accountDropList.setSize(100,20);
        amountLabel.setSize(amountLabel.getPreferredSize());
        amountLabel.setLocation(15, 50);
        amountField.setColumns(15);
        amountField.setToolTipText("Enter the amount you want to transfer");
        amountField.setSize(amountField.getPreferredSize());
        amountField.setLocation(110, 50);

        add(transferConfirm);
        add(accLabel);
        add(accountDropList);
        add(amountField);
        add(amountLabel);

        setVisible(true);
    }

    public void setAccountDropList(ArrayList<Account> allAccount){
        for(Account a : allAccount){
            accountDropList.addItem(a.getName());
        }
    }

    public String getAmount(){
        return amountField.getText();
    }
}
