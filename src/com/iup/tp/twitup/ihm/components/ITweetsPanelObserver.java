package com.iup.tp.twitup.ihm.components;

import java.util.Set;

import com.iup.tp.twitup.datamodel.Twit;

public interface ITweetsPanelObserver {
  void setTweets(Set<Twit> st);

}
