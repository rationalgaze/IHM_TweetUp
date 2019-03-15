package com.iup.tp.twitup.ihm.components;

import com.iup.tp.twitup.controllers.ILoginControllerObserver;

public interface ILoginObservable {
  void addObserver(ILoginControllerObserver o);
  void removeObserver(ILoginControllerObserver o);
  void notifyObservers(String uname, String pwd);
  void notifyObserversShowRegister();
}
