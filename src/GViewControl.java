import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class GViewControl extends JFrame implements Observer{
	
	private Portfolio portfolio;
	private JTextField usernameLogIn = new JTextField();
	private JPasswordField passwordLogIn = new JPasswordField();
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private JButton signUp = new JButton("Sign Up");
	private JButton signIn = new JButton("Sign In");
	private String userAccount;
	private String pass;
	
	public GViewControl(String name){
		super(name);
		//this.portfolio = p;
		//this.portfolio.addObserver(this);
		
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
		
		usernameLogIn.setColumns(15);
		usernameLogIn.setSize(usernameLogIn.getPreferredSize());
		usernameLogIn.setToolTipText("Please enter your username");
		usernameLogIn.setLocation(80, 25);
		usernameLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				userAccount = usernameLogIn.getText();
			}
		});
		add(usernameLogIn);
		
		passwordLogIn.setColumns(15);
		passwordLogIn.setSize(passwordLogIn.getPreferredSize());
		passwordLogIn.setToolTipText("Please enter your password");
		passwordLogIn.setLocation(80, 60);
		passwordLogIn.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				pass = passwordLogIn.getText();
			}
			
		});
		add(passwordLogIn);
		
		signUp.setSize(signUp.getPreferredSize());
		signUp.setLocation(10, 100);
		signUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				User newUser = new User(userAccount);
				String encryptedPass = newUser.encrypt(pass);
				newUser.setPassword(encryptedPass);
				try {
					newUser.transferUsernamePassword();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		add(signUp);
		
		signIn.setSize(signIn.getPreferredSize());
		signIn.setLocation(100, 100);
		add(signIn);
		
		
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
