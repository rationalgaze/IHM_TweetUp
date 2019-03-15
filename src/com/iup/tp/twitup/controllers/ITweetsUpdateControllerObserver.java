package com.iup.tp.twitup.controllers;

import java.util.Set;

import com.iup.tp.twitup.datamodel.Twit;

public interface ITweetsUpdateControllerObserver {
  void setTweets(Set<Twit> st);
}
