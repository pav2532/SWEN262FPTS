package userInterface;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Account;

public class AddWacthList extends JFrame {
	   protected JButton AddConfirm = new JButton("Add to WacthList");
	   JLabel critHigh = new JLabel("High:");
	   JTextField highValue = new JTextField("0");
	   JLabel critLow = new JLabel("Low:");
	   JTextField lowValue = new JTextField("0");
	   
	   public AddWacthList(String name){
	      super(name);
	      setLayout(null);
	      setSize(350, 250);
	      setLocation(500, 250);
	      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      
	      AddConfirm.setSize(AddConfirm.getPreferredSize());
	      AddConfirm.setLocation(240, 100);
	      critHigh.setSize(critHigh.getPreferredSize());
	      critHigh.setLocation(10, 80);
	      highValue.setLocation(119, 80);
	      highValue.setSize(100,20);
	      critLow.setSize(critLow.getPreferredSize());
	      critLow.setLocation(10, 50);
	      lowValue.setColumns(15);
	      lowValue.setSize(lowValue.getPreferredSize());
	      lowValue.setLocation(120, 50);
	      
	      add(AddConfirm);
	      add(critHigh);
	      add(highValue);
	      add(critLow);
	      add(lowValue);
	      
	      setVisible(true);
	   }

	public String getHighValue() {
		return highValue.getText();
	}

	public String getLowValue() {
		return lowValue.getText();
	}
	   
	   

}
