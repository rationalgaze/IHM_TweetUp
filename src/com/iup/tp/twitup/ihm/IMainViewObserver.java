package com.iup.tp.twitup.ihm;

import java.io.File;

public interface IMainViewObserver {
  void notifyDirectoryChanged(File file);
  void notifyShowTwits();
}
