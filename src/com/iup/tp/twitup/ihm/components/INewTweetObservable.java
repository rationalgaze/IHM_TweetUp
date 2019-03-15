package com.iup.tp.twitup.ihm.components;

import com.iup.tp.twitup.controllers.ISendTweetControllerObserver;
import com.iup.tp.twitup.datamodel.User;

public interface INewTweetObservable {
  void addObserver(ISendTweetControllerObserver o);
  void removeObserver(ISendTweetControllerObserver o);
  void notifyObservers(String tweet, User user);
}
