package com.iup.tp.twitup.controllers;

import com.iup.tp.twitup.datamodel.User;

public interface ISendTweetControllerObserver {
  void sendTweet(String tweet, User user);
}
