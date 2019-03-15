package com.iup.tp.twitup.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.iup.tp.twitup.controllers.LoginController;
import com.iup.tp.twitup.controllers.RegisterController;
import com.iup.tp.twitup.controllers.SendTweetController;
import com.iup.tp.twitup.controllers.TweetsUpdateController;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.components.LoginPanel;
import com.iup.tp.twitup.ihm.components.RegisterPanel;
import com.iup.tp.twitup.ihm.components.UserTweetsPanel;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView implements IMainView {
  JFrame mFrame;

  protected final Set<IMainViewObserver> mObservers;
  protected ResourceBundle mBundle;
  protected LoginController loginController;
  protected SendTweetController sendTweetController;
  protected TweetsUpdateController updateTweetsController;
  protected RegisterController registerController;
  protected UserTweetsPanel userTwetsPanel;

  private JPanel mMainpanel;
  private JPanel mStatuspanel;

  public TwitupMainView(Locale locale) {
    this.mObservers = new HashSet<>();
    this.mBundle = ResourceBundle.getBundle("resources/local", locale);
    this.initGUI();
  }

  public void showGUI() {
    // Affichage dans l'EDT
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        // Custom de l'affichage
        JFrame frame = TwitupMainView.this.mFrame;

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 4);

        // Affichage
        frame.setVisible(true);
      }
    });
  }

  protected void initGUI() {
    // Création de la fenetre principale
    mFrame = new JFrame(this.mBundle.getString("title"));
    mFrame.setSize(600, 800);
    mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mMainpanel = new JPanel(new GridBagLayout());
    mFrame.getContentPane().add(mMainpanel, BorderLayout.CENTER);

    // Status panel
    mStatuspanel = new JPanel(new GridBagLayout());
    mStatuspanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    JLabel status = new JLabel("Status: ");
    mStatuspanel.add(status, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    mFrame.getContentPane().add(mStatuspanel, BorderLayout.SOUTH);

    // Menu Bar
    JMenuBar menuBar = new JMenuBar();
    mFrame.setJMenuBar(menuBar);

    // File menu section
    JMenu mnFile = new JMenu(this.mBundle.getString("menu.file"));

    // Menu item Choose
    JMenuItem menuItmChoose = new JMenuItem(this.mBundle.getString("menu.file.choose"));
    menuItmChoose.addActionListener(e -> {
      chooseFile();
    });
    mnFile.add(menuItmChoose);

    // Menu item Authentification
    JMenuItem menuItmConnect = new JMenuItem(this.mBundle.getString("menu.file.connect"));
    menuItmConnect.addActionListener(e -> {
      connect();
    });
    mnFile.add(menuItmConnect);
    mnFile.addSeparator();

    // Menu item Exit application
    JMenuItem menuItmExit = new JMenuItem(this.mBundle.getString("menu.file.exit"),
        new ImageIcon("resources/images/exitIcon_20.png"));
    menuItmExit.setToolTipText(this.mBundle.getString("menu.file.tooltip.exit"));
    menuItmExit.addActionListener(e -> {
      exit();
    });
    menuItmExit
        .setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));
    mnFile.add(menuItmExit);

    // Help menu section
    JMenu mnHelp = new JMenu("?");
    menuBar.add(mnFile);
    menuBar.add(mnHelp);

    // Menu item About
    JMenuItem menuItmAbout = new JMenuItem("About");
    menuItmAbout.setToolTipText("About app");
    mnHelp.add(menuItmAbout);
    menuItmAbout.addActionListener(e -> {
      JOptionPane.showMessageDialog(null, "                     UBO M2 \n TIIL Computer Science Departement", "About",
          JOptionPane.INFORMATION_MESSAGE, new ImageIcon("resources/images/logoIUP_50.jpg"));
    });
  }

  public void chooseFile() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setDialogTitle("Choose Directory");
    chooser.setCurrentDirectory(new File("/home/niko/eclipse-workspace/TwitUp/exchange/"));

    int returnVal = chooser.showOpenDialog(this.mFrame);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      System.out.println("You chose to open this file: " + chooser.getSelectedFile());

      // notifier les observer qu'un fichier à été selectionné
      for (IMainViewObserver observer : this.mObservers) {
        observer.notifyDirectoryChanged(chooser.getSelectedFile());
      }
    }
  }

  public void setLoginController(LoginController loginController) {
    this.loginController = loginController;
  }

  public void setSendTweetController(SendTweetController stController) {
    this.sendTweetController = stController;
  }

  public void setUpdateTweetsController(TweetsUpdateController updController) {
    this.updateTweetsController = updController;
  }
  
  public void setRegisterController(RegisterController regController) {
    this.registerController = regController;
  }

  // call to Login view
  public void connect() {
    mMainpanel.removeAll();
    LoginPanel loginDlg = new LoginPanel(mBundle);
    mMainpanel.add(loginDlg, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    loginDlg.addObserver(this.loginController);
    mMainpanel.revalidate();
    mMainpanel.repaint();
  }

  // after login show User Twits
  public void showTwits(User user) {
    mMainpanel.removeAll();
    userTwetsPanel = new UserTweetsPanel(user);
    userTwetsPanel.getNewTweetPanel().addObserver(this.sendTweetController);
    updateTweetsController.addObserver(userTwetsPanel);
    mMainpanel.add(userTwetsPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH,
        GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    mMainpanel.revalidate();
    mMainpanel.repaint();
  }
  
  public void showRegister() {
    mMainpanel.removeAll();
    RegisterPanel rp = new RegisterPanel();
    rp.addObserver(registerController);
    mMainpanel.add(rp, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH,
        GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    mMainpanel.revalidate();
    mMainpanel.repaint();
  }
  
  // after login show User Twits
  public void updateTwits() {
    updateTweetsController.addObserver(userTwetsPanel);
  }

  public TweetsUpdateController getUpdateTweetsController() {
    return updateTweetsController;
  }

  public void exit() {
    System.exit(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void addObserver(IMainViewObserver observer) {
    this.mObservers.add(observer);
  }

  @Override
  public void removeObserver(IMainViewObserver observer) {
    this.mObservers.remove(observer);
  }
}
