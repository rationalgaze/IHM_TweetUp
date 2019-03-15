package com.iup.tp.twitup.ihm;

public interface IMainView {
  void addObserver(IMainViewObserver observer);
  void removeObserver(IMainViewObserver observer);
}
