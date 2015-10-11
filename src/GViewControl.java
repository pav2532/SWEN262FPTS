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
	private JMenuBar menu = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenuItem exit = new JMenuItem("Exit");
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
		
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		file.add(exit);
		menu.add(file);
		
		
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
			public void actionPerformed(ActionEvent e) {
				pass = passwordLogIn.getPassword().toString();
				System.out.println(pass);
			}
			
		});
		add(passwordLogIn);
		
		signUp.setSize(signUp.getPreferredSize());
		signUp.setLocation(10, 100);
		signUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				UserParser userParser = new UserParser();
				String existAccount = userParser.findAccount(userAccount, "src/Account.txt");
				if(existAccount == null){
					User newUser = new User(userAccount);
					String encryptedPass = newUser.encrypt(pass);
					newUser.setPassword(encryptedPass);
					try {
						newUser.transferUsernamePassword();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "This username is already exist. Please choose another username");
				}
			}
		});
		add(signUp);
		
		signIn.setSize(signIn.getPreferredSize());
		signIn.setLocation(100, 100);
		signIn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Read the file -> compare all the existing accounts with 
				// the one that user just typed in
				// if not found one, tell them to sign up
				// if you found one, allow them to log in and load their Portfolio
				UserParser userParser = new UserParser();
				String associatePassword = userParser.findAccount(userAccount, "src/Account.txt");
				if(associatePassword != null){
					User user = new User(userAccount);
					user.setPassword(associatePassword);
					if(user.authenticate(user.encrypt(pass))){
						getContentPane().removeAll();
						getContentPane().repaint();
						JOptionPane.showMessageDialog(null, "Log in sucessful");
						setJMenuBar(menu);
					}else{
						JOptionPane.showMessageDialog(null, "Not a correct password");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please check your username or sign up new account");
				}
			} 
		});
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
