package com.iup.tp.twitup.ihm.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.iup.tp.twitup.controllers.ILoginControllerObserver;
import com.iup.tp.twitup.datamodel.Twit;

public class LoginPanel extends JPanel implements ILoginObservable {

  private static final long serialVersionUID = 1L;
  private JTextField tfUsername;
  private JLabel lbUsername;
  private JLabel lbPassword;
  private JLabel lbError;
  private JPasswordField pfPassword;
  private JButton btnLogin;
  private JButton btnSignup;
  private boolean succeeded;
  protected ResourceBundle mBundle;
  protected Set<ILoginControllerObserver> observers;
  protected Set<Twit> tweets;

  public LoginPanel(ResourceBundle mBundle) {
    this.observers = new HashSet<>();
    this.tweets = new HashSet<>();
    this.mBundle = mBundle;
    this.setGUI();
  }

  protected void setGUI() {
    this.setLayout(new GridBagLayout());
    lbUsername = new JLabel(this.mBundle.getString("label.username"));
    this.add(lbUsername, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    tfUsername = new JTextField(20);
    this.add(tfUsername, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    tfUsername.addKeyListener(this.enter());

    lbPassword = new JLabel(this.mBundle.getString("label.password"));
    this.add(lbPassword, new GridBagConstraints(0, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    pfPassword = new JPasswordField(20);
    this.add(pfPassword, new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    pfPassword.addKeyListener(this.enter());
    
    // Label for Error msg
    lbError = new JLabel(" ");
    lbError.setForeground(Color.RED);
    this.add(lbError, new GridBagConstraints(0, 4, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    
    // Login button
    btnLogin = new JButton(this.mBundle.getString("btn.login"));

    btnLogin.addActionListener(e -> { login(); });
    
    // Sign Up Button
    btnSignup = new JButton(this.mBundle.getString("btn.signup"));
    btnSignup.addActionListener(e -> { showRegister(); });
    
    // Buttons Panel
    JPanel bp = new JPanel();
    bp.add(btnLogin, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));

    bp.add(btnSignup, new GridBagConstraints(1, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));

    this.add(bp, new GridBagConstraints(0, 5, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
  }
  
  protected KeyAdapter enter() {
    KeyAdapter kaEnter = new KeyAdapter() {
      public void keyPressed(KeyEvent key) {
        if(key.getKeyChar() == KeyEvent.VK_ENTER) {
          login();
        }
      }
    };
    return kaEnter;
  }
  
  protected void login() {
    if(getUsername().isEmpty() || getPassword().isEmpty()) {
      lbError.setText("You need to complete the form");
    } else {
      notifyObservers(getUsername(), getPassword());
      if (succeeded) {
        this.loginSuccess();
      } else {
        this.loginFail();
      }
    }
  }
  
  public void showRegister() {
    notifyObserversShowRegister();
  }
  
  protected void loginSuccess() {   
    JOptionPane.showMessageDialog(LoginPanel.this, "Hi " + getUsername() + "! You have successfully logged in.",
        "Login", JOptionPane.INFORMATION_MESSAGE);
  }

  protected void loginFail() {
    JOptionPane.showMessageDialog(LoginPanel.this, "Invalid username or password", "Login",
        JOptionPane.ERROR_MESSAGE);
    // reset username and password
    tfUsername.setText("");
    pfPassword.setText("");
  }

  public String getUsername() {
    return tfUsername.getText().trim();
  }

  public String getPassword() {
    return new String(pfPassword.getPassword());
  }

  public boolean isSucceeded() {
    return succeeded;
  }

  @Override
  public void addObserver(ILoginControllerObserver ctrlr) {
    this.observers.add(ctrlr);
  }

  @Override
  public void removeObserver(ILoginControllerObserver o) {
    this.observers.remove(o);
  }

  @Override
  public void notifyObservers(String uname, String pwd) {
    for (ILoginControllerObserver o : observers) {
      succeeded = o.checkUser(uname, pwd);
    }
  }

  @Override
  public void notifyObserversShowRegister() {
    for (ILoginControllerObserver o : observers) {
      o.showRegister();
    }
  }
}
