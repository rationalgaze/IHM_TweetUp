package com.iup.tp.twitup.ihm.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TweetPanel extends JPanel {

  private static final long serialVersionUID = 3L;
  private JLabel lbUsername;
  private JLabel lbTwitText;
  
  public TweetPanel() {
    this.setGUI();
  }
  
  public void setGUI() {
    this.setBorder(BorderFactory.createTitledBorder(null, "@twit", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial",Font.ITALIC,13), Color.GRAY));
    this.setLayout(new GridBagLayout());
    this.setPreferredSize(new Dimension(400, 70));
    lbUsername = new JLabel("Username");
    lbTwitText = new JLabel();
    // format date
    Date d = new Date();
    SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    String dateText = df2.format(d);
    JLabel date = new JLabel(dateText);
    date.setFont(new Font("Arial", Font.ITALIC, 12));
    
    this.add(lbUsername, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
    this.add(date, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
    this.add(lbTwitText, new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 0, 0, 0), 0, 0));
  }
  
  public void setUsername(String uname) {
    this.lbUsername.setText(uname);
  }
  
  public void setTweetText(String txt) {
    this.lbTwitText.setText(txt);
  }
}
