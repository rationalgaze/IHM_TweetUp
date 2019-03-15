package com.iup.tp.twitup.core;

import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import javax.swing.UIManager;

import com.iup.tp.twitup.common.PropertiesManager;
import com.iup.tp.twitup.controllers.LoginController;
import com.iup.tp.twitup.controllers.RegisterController;
import com.iup.tp.twitup.controllers.SendTweetController;
import com.iup.tp.twitup.controllers.TweetsUpdateController;
import com.iup.tp.twitup.datamodel.Database;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.TwitupMock;
import com.iup.tp.twitup.ihm.components.ILoginObservable;

/**
 * Classe principale l'application.
 * 
 * @author S.Lucas
 */
public class Twitup {
  /**
   * Base de données.
   */
  protected IDatabase mDatabase;

  /**
   * Gestionnaire des entités contenu de la base de données.
   */
  protected EntityManager mEntityManager;

  /**
   * Vue principale de l'application.
   */
  protected TwitupMainView mMainView;

  /**
   * Classe de surveillance de répertoire
   */
  protected IWatchableDirectory mWatchableDirectory;

  /**
   * Répertoire d'échange de l'application.
   */
  protected String mExchangeDirectoryPath;

  /**
   * Idnique si le mode bouchoné est activé.
   */
  protected boolean mIsMockEnabled = false;

  /**
   * Nom de la classe de l'UI.
   */
  protected String mGuiClassName;

  /**
   * Properties de l'application
   */
  Properties mProperties;

  /**
   * Langage de l'application
   */
  Locale mLocale;

  ILoginObservable mLoginPanel;

  /**
   * Constructeur.
   */
  public Twitup() {
    this.initProperties();

    // Init du look and feel de l'application
    this.initLookAndFeel();

    // Initialisation de la base de données
    this.initDatabase();

    if (this.mIsMockEnabled) {
      // Initialisation du bouchon de travail
      this.initMock();
    }

    // Initialisation de l'IHM
    this.initGui();

    // Generation d'un utilisateur pour tester
    this.addUserInDatabase();

    // Initialisation du répertoire d'échange
    this.initDirectory();
  }

  private void initLocal() {
    String langue = this.mProperties.getProperty("LOCAL");

    if (!langue.isEmpty()) {
      this.mLocale = new Locale(langue, langue.toUpperCase(), "");
    }
  }

  protected void initProperties() {
    File url = new File("/home/niko/eclipse-workspace/TwitUp/src/resources/configuration.properties");
    String path = url.getPath();
    this.mProperties = PropertiesManager.loadProperties(path);
    // Initialisation du langage de l'application
    this.initLocal();
    this.mIsMockEnabled = Boolean.parseBoolean(this.mProperties.getProperty("MOCK_ENABLED"));
    this.mGuiClassName = mProperties.getProperty("LOOK_METAL");
  }

  /**
   * Initialisation du look and feel de l'application.
   */
  protected void initLookAndFeel() {
    try {
      if (mGuiClassName == null || mGuiClassName.isEmpty()) {
        this.mGuiClassName = UIManager.getSystemLookAndFeelClassName();
      }
      UIManager.setLookAndFeel(this.mGuiClassName);
    } catch (Exception e) {
      System.out.println("Look and Feel not set");
    }
  }

  /**
   * Initialisation de l'interface graphique.
   */
  protected void initGui() {
    mMainView = new TwitupMainView(this.mLocale);
    mMainView.setLoginController(new LoginController(mDatabase, mMainView));
    mMainView.setSendTweetController(new SendTweetController(this.mDatabase, mMainView));
    mMainView.setUpdateTweetsController(new TweetsUpdateController(mDatabase, mMainView));
    mMainView.setRegisterController(new RegisterController(mDatabase, mMainView));
//    mMainView.connect();
    // mMainView.showTwits();
    mMainView.showRegister();
  }

  /**
   * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
   * chooser). <br/>
   * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
   * pouvoir utiliser l'application</b>
   */
  protected void initDirectory() {

    String path = "";
    if (this.mProperties.containsKey("EXCHANGE_DIRECTORY")) {
      path = this.mProperties.getProperty("EXCHANGE_DIRECTORY");
    }

    if (!path.isEmpty()) {
      this.initDirectory(path);
    }

    if (this.mExchangeDirectoryPath == null || this.mExchangeDirectoryPath.isEmpty()) {
      this.mMainView.chooseFile();
    }
  }

  /**
   * Indique si le fichier donné est valide pour servire de répertoire d'échange
   * 
   * @param directory
   *          , Répertoire à tester.
   */
  protected boolean isValideExchangeDirectory(File directory) {
    // Valide si répertoire disponible en lecture et écriture
    return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
        && directory.canWrite();
  }

  /**
   * Initialisation du mode bouchoné de l'application
   */
  protected void initMock() {
    TwitupMock mock = new TwitupMock(this.mDatabase, this.mEntityManager);
    mock.showGUI();
  }

  /**
   * Initialisation de la base de données
   */
  protected void initDatabase() {
    mDatabase = new Database();
    mEntityManager = new EntityManager(mDatabase);
  }

  /**
   * Initialisation du répertoire d'échange.
   * 
   * @param directoryPath
   */
  public void initDirectory(String directoryPath) {
    mExchangeDirectoryPath = directoryPath;
    mWatchableDirectory = new WatchableDirectory(directoryPath);
    mEntityManager.setExchangeDirectory(directoryPath);

    mWatchableDirectory.initWatching();
    mWatchableDirectory.addObserver(mEntityManager);
  }

  /**
   * Ajoute un utilisateur fictif à la base de donnée.
   */
  protected void addUserInDatabase() {
    // Création d'un utilisateur fictif
    User newUser = this.generateUser();

    // Ajout de l'utilisateur à la base
    this.mDatabase.addUser(newUser);
  }

  /**
   * Génération et envoi d'un fichier utilisateur
   */
  protected void sendUser() {
    // Création d'un utilisateur fictif
    User newUser = this.generateUser();

    // Génération du fichier utilisateur
    this.mEntityManager.sendUser(newUser);
  }

  /**
   * Génération d'un utilisateur fictif.
   */
  protected User generateUser() {
    // int randomInt = new Random().nextInt(99999);
    String userName = "usr";
    User newUser = new User(UUID.randomUUID(), userName, "--", userName, new HashSet<String>(), "");
    this.addTwitInDatabase(newUser);
    return newUser;
  }

  /**
   * Ajoute un twit fictif à la base de données.
   */
  public void addTwitInDatabase(User usr) {
    // Création 'un twit fictif
    Twit newTwit = new Twit(usr, "Twit par defaut");

    // Ajout du twit
    this.mDatabase.addTwit(newTwit);
  }

  public void show() {
    mMainView.showGUI();
  }
}
