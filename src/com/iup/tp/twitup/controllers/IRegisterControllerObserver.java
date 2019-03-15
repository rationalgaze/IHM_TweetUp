package com.iup.tp.twitup.controllers;

import com.iup.tp.twitup.datamodel.User;

public interface IRegisterControllerObserver {
  boolean addUser(User user);
  void showLoginView();
}
