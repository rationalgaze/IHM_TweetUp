package com.iup.tp.twitup.controllers;

import java.util.Set;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.components.ITweetsPanelObserver;

public interface ITweetsUpdateControllerObservable {
  void addObserver(ITweetsPanelObserver o);
  void removeObserver(ITweetsPanelObserver o);
  void notifyObservers(Set<Twit> st);
}
