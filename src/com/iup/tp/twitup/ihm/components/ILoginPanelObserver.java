package com.iup.tp.twitup.ihm.components;

import java.util.Set;

import com.iup.tp.twitup.datamodel.Twit;

public interface ILoginPanelObserver {
  void setTweets(Set<Twit> st);
}
