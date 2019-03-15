package com.iup.tp.twitup.ihm.components;

import java.util.Set;

import com.iup.tp.twitup.controllers.ITweetsUpdateControllerObserver;
import com.iup.tp.twitup.datamodel.Twit;

public interface ITweetsPanelObservable {
    void addObserver(ITweetsUpdateControllerObserver o);
    void removeObserver(ITweetsUpdateControllerObserver o);
    void notifyObservers(Set<Twit> tweets);
}
