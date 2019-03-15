package com.iup.tp.twitup.controllers;

import java.util.HashSet;
import java.util.Set;

import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.components.ITweetsPanelObserver;

public class TweetsUpdateController implements ITweetsUpdateControllerObservable {
  protected IDatabase mDatabase;
  protected TwitupMainView mView;
  protected Set<ITweetsPanelObserver> observers;
  
  public TweetsUpdateController(IDatabase db, TwitupMainView view) {
    this.mDatabase = db;
    this.mView = view;
    this.observers = new HashSet<>();
  }
  
  public void update(Set<Twit> st) {
    notifyObservers(st);
  }

  @Override
  public void addObserver(ITweetsPanelObserver o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(ITweetsPanelObserver o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers(Set<Twit> st) {
    for (ITweetsPanelObserver o : observers) {
      o.setTweets(st);
    }    
  }
}
