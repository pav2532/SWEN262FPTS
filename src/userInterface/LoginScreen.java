package userInterface;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import model.GetUserData;
import model.Portfolio;
import model.PortfolioParser;
import model.User;
import model.UserParser;
import model.UserProxy;

public class LoginScreen extends JFrame{
   JLabel usernameLabel = new JLabel("Username");
   JLabel passwordLabel = new JLabel("Password");
   JButton signUp = new JButton("Sign Up");
   JButton signIn = new JButton("Sign In");
   JPasswordField passwordLogIn = new JPasswordField();
   JTextField usernameLogIn = new JTextField();
   User currentUser;
   
   public LoginScreen(){
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
      add(usernameLogIn);
      
      passwordLogIn.setColumns(15);
      passwordLogIn.setSize(passwordLogIn.getPreferredSize());
      passwordLogIn.setToolTipText("Please enter your password");
      passwordLogIn.setLocation(80, 60);
      add(passwordLogIn);
      
      signUp.setSize(signUp.getPreferredSize());
      signUp.setLocation(10, 100);
      signUp.addActionListener(new ActionListener(){
         @SuppressWarnings("deprecation")
         public void actionPerformed(ActionEvent e){
            String pass = passwordLogIn.getText();
            String userAccount = usernameLogIn.getText();
            String userLength = userAccount.trim();

            UserParser userParser = new UserParser();
            String existAccount = userParser.findAccount(userAccount, "src/Account.txt");
            if(existAccount == null && userLength.length() != 0 && pass.length() != 0){
               User newUser = new User(userAccount);
               GetUserData userProxy = new UserProxy(newUser);

               String encryptedPass = newUser.encrypt(pass);
               newUser.setPassword(encryptedPass);
               try {
                  File file = new File("Portfolios/"+userProxy.getUsername()+".txt");
                  boolean success = file.createNewFile();
                 if(success){
                     newUser.transferUsernamePassword();
                  }

               } catch (IOException e1) {
                  e1.printStackTrace();
               }
               JOptionPane.showMessageDialog(null, "Sign up successfully");
            }else {
               if (userLength.length() == 0) {
                  JOptionPane.showMessageDialog(null, "Please enter an username");
               }else if(pass.length() == 0){
                  JOptionPane.showMessageDialog(null, "Please enter a password");
               }else{
                  JOptionPane.showMessageDialog(null, "This username is already exist. Please choose another username");

               }
            }
         }
      });

      signIn.setSize(signIn.getPreferredSize());
      signIn.setLocation(100, 100);
      signIn.addActionListener(new ActionListener(){
         @SuppressWarnings("deprecation")
         public void actionPerformed(ActionEvent e){
            String pass = passwordLogIn.getText();
            String userAccount = usernameLogIn.getText();

            UserParser userParser = new UserParser();
            String associatePassword = userParser.findAccount(userAccount, "src/Account.txt");
            if(associatePassword != null){
               currentUser = new User(userAccount);
               GetUserData userProxy = new UserProxy(currentUser);

               currentUser.setPassword(associatePassword);
               if(currentUser.authenticate(currentUser.encrypt(pass))){
                  PortfolioParser parser = new PortfolioParser();
                  Portfolio p = parser.importFile("Portfolios/"+userProxy.getUsername()+".txt");
                  dispose();
                  MainView view = new MainView("FPTS", p);
                  JOptionPane.showMessageDialog(null, "Log in sucessful");
                  
               }else{
                  JOptionPane.showMessageDialog(null, "Invalid username or password");
               }
            }
            else{
               JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
         } 
      });   
      
      add(signUp);
      add(signIn);
      setVisible(true);
   };
}