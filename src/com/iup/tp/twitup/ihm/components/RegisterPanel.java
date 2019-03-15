package com.iup.tp.twitup.ihm.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.iup.tp.twitup.controllers.IRegisterControllerObserver;
import com.iup.tp.twitup.datamodel.User;

public class RegisterPanel extends JPanel implements IRegisterObservable {
  private static final long serialVersionUID = 6L;
  private JLabel lbTag;
  private JLabel lbName;
  private JLabel lbPassword;
  private JLabel lbConfirmPassword;
  private JLabel lbError;
  private JTextField tfTag;
  private JTextField tfName;
  private JPasswordField pfPassword;
  private JPasswordField pfConfirmPassword;
  private JButton btnRegister;
  private JButton btnSignin;
  protected ResourceBundle mBundle;
  protected Set<IRegisterControllerObserver> observers;
  private boolean succeeded;

  public RegisterPanel() {
    this.observers = new HashSet<>();
    succeeded = false;
    this.setGUI();
  }
  
  protected void setGUI() {
    this.setLayout(new GridBagLayout());
    
    // Tag Label and TextField
    lbTag = new JLabel("Tag");
    this.add(lbTag, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    tfTag = new JTextField(20);
    this.add(tfTag, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    
    // Name Label and TextField
    lbName = new JLabel("Name");
    this.add(lbName, new GridBagConstraints(0, 2, 2, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    tfName = new JTextField(20);
    this.add(tfName, new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    
    // Password Label and TextField
    lbPassword = new JLabel("Password");
    this.add(lbPassword, new GridBagConstraints(0, 4, 2, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    pfPassword = new JPasswordField(20);
    this.add(pfPassword, new GridBagConstraints(0, 5, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    
    // Password Confirmation Label and TextField
    lbConfirmPassword = new JLabel("Confirm Password");
    this.add(lbConfirmPassword, new GridBagConstraints(0, 6, 2, 1, 0, 0, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
    pfConfirmPassword = new JPasswordField(20);
    this.add(pfConfirmPassword, new GridBagConstraints(0, 7, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
//  tfUsername.addKeyListener(this.enter());
    
    // Label for Error msg
    lbError = new JLabel(" ");
    lbError.setForeground(Color.RED);
    this.add(lbError, new GridBagConstraints(0, 8, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(5, 0, 0, 0), 0, 0));
    
    // Login button
    btnRegister = new JButton("Register");
    btnRegister.addActionListener(e -> { register(); });
    
    // Sign Up Button
    btnSignin = new JButton("Login");
    btnSignin.addActionListener(e -> { login(); });
    
    // Buttons Panel
    JPanel bp = new JPanel();
    bp.add(btnRegister, new GridBagConstraints(0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));

    bp.add(btnSignin, new GridBagConstraints(1, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));

    this.add(bp, new GridBagConstraints(0, 9, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
  }
  
  protected void register() {
    String error ="You need to enter your ";
    if(!tagExist()) {
      error += "tag, ";
    } 
    
    if(!nameExist()) {
      error += "name, ";
    }
    
    lbError.setText(error);

    if (passwordsMatch()) {
      User newUsr = new User(UUID.randomUUID(), tfTag.getText(), getPassword(), tfName.getText(), new HashSet<String>(), "");
      this.notifyObservers(newUsr);
    }
  }
  
  protected String getPassword() {
    return new String(pfPassword.getPassword());
  }
  
  protected String getConfirmPassword() {
    return new String(pfConfirmPassword.getPassword());
  }
  
  protected boolean tagExist() {
    if(!tfTag.getText().isEmpty()) {
      return true;
    }
    return false;
  }
  
  protected boolean nameExist() {
    if(!tfName.getText().isEmpty()) {
      return true;
    }
    return false;
  }
  
  protected boolean passwordsMatch() {
    if(this.getPassword().equals(this.getConfirmPassword())) {
      return true;
    } else {
      lbError.setText("Passwords do not match");
    }
    return false;
  }
  
  protected void login() {
    notifyObserversLogin();
  }

  @Override
  public void addObserver(IRegisterControllerObserver o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(IRegisterControllerObserver o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers(User user) {
    for (IRegisterControllerObserver o : observers) {
      succeeded = o.addUser(user);
    }    
  }

  @Override
  public void notifyObserversLogin() {
    for (IRegisterControllerObserver o : observers) {
      o.showLoginView();
    }   
  }
}
