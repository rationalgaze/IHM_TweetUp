package com.iup.tp.twitup.controllers;

public interface ILoginControllerObserver {
  boolean checkUser(String uname, String pwd);
  void showRegister();
}
