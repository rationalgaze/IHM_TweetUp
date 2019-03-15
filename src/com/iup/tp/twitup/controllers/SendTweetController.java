package com.iup.tp.twitup.controllers;

import java.util.Set;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;

public class SendTweetController implements ISendTweetControllerObserver {
  protected IDatabase mDatabase;
  protected EntityManager mEntityManager;
  protected TwitupMainView mView;

  public SendTweetController(IDatabase mDatabase, TwitupMainView view) {
    this.mDatabase = mDatabase;
    this.mView = view;
  }

  @Override
  public void sendTweet(String tweet, User user) {

      Twit newTwit = new Twit(user, tweet);
      this.mDatabase.addTwit(newTwit);
    
    TweetsUpdateController tuc = mView.getUpdateTweetsController();
    tuc.update(getTweets(user));
  }
  
  protected Set<Twit> getTweets(User usr) {
    return this.mDatabase.getUserTwits(usr);
  }
}
