package com.iup.tp.twitup.ihm.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.iup.tp.twitup.controllers.ITweetsUpdateControllerObserver;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

public class UserTweetsPanel extends JPanel implements ITweetsPanelObserver {

  private static final long serialVersionUID = 2L;
  protected List<TweetPanel> twitsAList;
  protected JScrollPane scrollPane;
  protected JPanel twitList;
  protected NewTweetPanel jpNewTweet;
  protected Set<Twit> tweets;
  protected Set<ITweetsUpdateControllerObserver> observers;
  protected User user;

  public UserTweetsPanel(User user) {
    this.user = user;
    this.twitsAList = new ArrayList<>();
    this.tweets = new HashSet<>();
    this.observers = new HashSet<>();
    this.setGUI();
  }

  public void setGUI() {
    this.setLayout(new BorderLayout(0,0));
    twitList = new JPanel(new GridBagLayout());
    
    this.scrollPane = new JScrollPane(twitList);
    scrollPane.getVerticalScrollBar().setUnitIncrement(5);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
    // Main panel
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.add(scrollPane, new GridBagConstraints(0, 0, 1, 2, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    
    this.jpNewTweet = new NewTweetPanel(this.user);
    this.add(jpNewTweet, BorderLayout.NORTH);
    this.add(mainPanel, BorderLayout.CENTER);

    this.revalidate();
    this.repaint();
  }

  public void addTweetInfo(TweetPanel twitPane) {
    this.twitsAList.add(twitPane);
  }

  public void deleteTweetInfo(TweetPanel twitPane) {
    this.twitsAList.remove(twitPane);
  }
  
  public NewTweetPanel getNewTweetPanel() {
    return this.jpNewTweet;
  }
  
  public void addTweetsToPanel() {
    // We add twits to the List of Twits
    this.twitsAList.clear();
    for (Twit t: tweets) {
      TweetPanel tp = new TweetPanel();
      tp.setTweetText(t.getText());
      tp.setUsername(t.getTwiter().getName());
      this.addTweetInfo(tp);
    }

    // We add twits to the main panel
    int y = 0;
    for (TweetPanel tp : twitsAList) {
      twitList.add(tp, new GridBagConstraints(0, y++, 1, 1, 1, 1, GridBagConstraints.NORTH,
          GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
    twitList.revalidate();
    twitList.repaint();
  }

  @Override
  public void setTweets(Set<Twit> st) {
    this.tweets.addAll(st);
    
    addTweetsToPanel();
  }
}
