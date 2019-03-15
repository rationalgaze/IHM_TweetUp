package com.iup.tp.twitup.ihm.components;

import com.iup.tp.twitup.controllers.IRegisterControllerObserver;
import com.iup.tp.twitup.datamodel.User;

public interface IRegisterObservable {
  void addObserver(IRegisterControllerObserver o);
  void removeObserver(IRegisterControllerObserver o);
  void notifyObservers(User user);
  void notifyObserversLogin();
}
