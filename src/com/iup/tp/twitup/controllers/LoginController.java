package com.iup.tp.twitup.controllers;

import java.util.HashSet;
import java.util.Set;

import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.components.ILoginPanelObserver;

public class LoginController implements ILoginControllerObserver {
  protected IDatabase mDatabase;
  protected TwitupMainView mView;
  protected Set<ILoginPanelObserver> observer;
  
  public LoginController(IDatabase db, TwitupMainView view) {
    this.mDatabase = db;
    this.mView = view;
    this.observer = new HashSet<>();
  }
  
  @Override
  public boolean checkUser(String username, String pwd) {
    for(User u : mDatabase.getUsers()) {
      if(u.getName().equals(username) && u.getUserPassword().equals(pwd)) {
        mView.showTwits(u);
        TweetsUpdateController utc = mView.getUpdateTweetsController();
        utc.update(getTweets(u));
        return true; 
      }
    }
    
    return false;
  }
  
  protected Set<Twit> getTweets(User usr) {
    return this.mDatabase.getUserTwits(usr);
  }

  @Override
  public void showRegister() {
    mView.showRegister();
  }
}
