import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GViewControl extends JFrame implements Observer{
	
	private Portfolio portfolio;
	
	public GViewControl(String name){
		super(name);
		//this.portfolio = p;
		//this.portfolio.addObserver(this);
		JTextField usernameLogIn = new JTextField();
		JPasswordField passwordLogIn = new JPasswordField();
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		JButton signUp = new JButton("Sign Up");
		JButton signIn = new JButton("Sign In");
		
		setLayout(null);
		setSize(350,250);
		setLocation(500, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		passwordLabel.setSize(passwordLabel.getPreferredSize());
		passwordLabel.setLocation(10, 60);
		add(passwordLabel);
		
		usernameLabel.setSize(usernameLabel.getPreferredSize());
		usernameLabel.setLocation(10, 30);
		add(usernameLabel);
		
		signUp.setSize(signUp.getPreferredSize());
		signUp.setLocation(10, 100);
		add(signUp);
		
		signIn.setSize(signIn.getPreferredSize());
		signIn.setLocation(100, 100);
		add(signIn);
		
		usernameLogIn.setColumns(15);
		usernameLogIn.setSize(usernameLogIn.getPreferredSize());
		usernameLogIn.setToolTipText("Please enter your username");
		usernameLogIn.setLocation(80, 25);
		usernameLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
			}
		});
		add(usernameLogIn);
		
		passwordLogIn.setColumns(15);
		passwordLogIn.setSize(passwordLogIn.getPreferredSize());
		passwordLogIn.setToolTipText("Please enter your password");
		passwordLogIn.setLocation(80, 60);
		add(passwordLogIn);
		
		setVisible(true);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[]){
		GViewControl view = new GViewControl("FPTS");
	}
}
