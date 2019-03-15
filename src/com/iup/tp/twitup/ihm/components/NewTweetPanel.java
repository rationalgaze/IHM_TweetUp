package com.iup.tp.twitup.ihm.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;

import com.iup.tp.twitup.controllers.ISendTweetControllerObserver;
import com.iup.tp.twitup.datamodel.User;

public class NewTweetPanel extends JPanel implements INewTweetObservable {
  private static final long serialVersionUID = 5L;
  private JTextArea taNewTwit;
  private JLabel lbUsername;
  private Set<ISendTweetControllerObserver> stCtrlr;
  private User user;
  
  public NewTweetPanel(User usr) {
    this.user = usr;
    stCtrlr = new HashSet<>();
    this.setGUI();
  }
  
  protected void setGUI() {
    this.setLayout(new GridBagLayout());
    
    // Label Username
    this.lbUsername = new JLabel(this.user.getUserTag());
    lbUsername.setForeground(new Color(102,102,102));
    
    // Text Area
    this.taNewTwit = new JTextArea(5,10);
    AbstractDocument pDoc = (AbstractDocument) taNewTwit.getDocument();
    pDoc.setDocumentFilter(new DocumentSizeFilter(150));
    this.taNewTwit.setBorder(BorderFactory.createLineBorder(new Color(164,217,249), 3, false));
    
    // Tweet button
    JButton btnSendTwit = new JButton("Twit");
    btnSendTwit.setBackground(new Color(164,217,249));
    btnSendTwit.setForeground(new Color(255,255,255));
    btnSendTwit.addActionListener(e -> { tweet(); });
    
    // Setting up elements
    this.add(lbUsername, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(taNewTwit, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(5, 5, 10, 5), 0, 0));
    this.add(btnSendTwit, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));
  }
  
  protected void tweet() {
    this.notifyObservers(this.taNewTwit.getText(), this.user);
  }

  @Override
  public void addObserver(ISendTweetControllerObserver o) {
    this.stCtrlr.add(o);
  }

  @Override
  public void removeObserver(ISendTweetControllerObserver o) {
    this.stCtrlr.remove(o);
  }

  @Override
  public void notifyObservers(String tweet, User user) {
    for (ISendTweetControllerObserver o : stCtrlr) {
      o.sendTweet(tweet, user);
    }
  }
}
