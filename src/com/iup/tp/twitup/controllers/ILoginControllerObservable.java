package com.iup.tp.twitup.controllers;

import java.util.Set;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.components.ILoginPanelObserver;

public interface ILoginControllerObservable {
  void addObserver(ILoginPanelObserver o);
  void removeObserver(ILoginPanelObserver o);
  void notifyObservers(Set<Twit> st);
}
