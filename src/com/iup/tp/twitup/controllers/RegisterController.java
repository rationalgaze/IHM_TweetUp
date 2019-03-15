package com.iup.tp.twitup.controllers;

import java.util.HashSet;
import java.util.Set;

import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.components.ILoginPanelObserver;

public class RegisterController implements IRegisterControllerObserver {
  protected IDatabase mDatabase;
  protected TwitupMainView mView;
  protected Set<ILoginPanelObserver> observer;
  
  public RegisterController(IDatabase db, TwitupMainView view) {
    this.mDatabase = db;
    this.mView = view;
    this.observer = new HashSet<>();
  }
  
  @Override
  public boolean addUser(User user) {
    System.out.println("Usr in ctrl" + user);
    if(!mDatabase.getUsers().contains(user)) {
      mDatabase.addUser(user);
      if(mDatabase.getUsers().contains(user)) {
        for(User u : mDatabase.getUsers()) {
          if(u.getName().equals(user.getName()) && u.getUserPassword().equals(user.getUserPassword())) {
            mView.showTwits(u);
            TweetsUpdateController utc = mView.getUpdateTweetsController();
            utc.update(getTweets(u));
            return true; 
          }
        }
        return true;
      }
    }
    return false;
  }
  
  protected Set<Twit> getTweets(User usr) {
    return this.mDatabase.getUserTwits(usr);
  }

  @Override
  public void showLoginView() {
    mView.connect();    
  }
}
