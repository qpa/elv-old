/*
 * View.java
 */
package org.oki.elv.client.gui;

import com.javadocking.DockingManager;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.model.DockModel;
import com.javadocking.model.FloatDockModel;
import com.javadocking.model.codec.DockModelPropertiesDecoder;
import com.javadocking.model.codec.DockModelPropertiesEncoder;
import java.awt.BorderLayout;
import java.io.IOException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTree;

/**
 * The application's main frame.
 */
public class View extends FrameView {
  /** Base file name of the docking storage. */
  public static final String DOCK_NAME = "dock";
  
  /** Constructor. */
  public View(SingleFrameApplication app) {
    super(app);

    initComponents();

    initNotGeneratedComponents();
    
    // status bar initialization - message timeout, idle icon and busy animation, etc
    ResourceMap resourceMap = getResourceMap();
    int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
    messageTimer = new Timer(messageTimeout, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        statusMessageLabel.setText("");
      }
    });
    messageTimer.setRepeats(false);
    int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
    for (int i = 0; i < busyIcons.length; i++) {
      busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
    }
    busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
        statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
      }
    });
    idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
    statusAnimationLabel.setIcon(idleIcon);
    progressBar.setVisible(false);

    // connecting action tasks to status bar via TaskMonitor
    TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
    taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      @Override
      public void propertyChange(java.beans.PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("started".equals(propertyName)) {
          if (!busyIconTimer.isRunning()) {
            statusAnimationLabel.setIcon(busyIcons[0]);
            busyIconIndex = 0;
            busyIconTimer.start();
          }
          progressBar.setVisible(true);
          progressBar.setIndeterminate(true);
        } else if ("done".equals(propertyName)) {
          busyIconTimer.stop();
          statusAnimationLabel.setIcon(idleIcon);
          progressBar.setVisible(false);
          progressBar.setValue(0);
        } else if ("message".equals(propertyName)) {
          String text = (String) (evt.getNewValue());
          statusMessageLabel.setText((text == null) ? "" : text);
          messageTimer.restart();
        } else if ("progress".equals(propertyName)) {
          int value = (Integer) (evt.getNewValue());
          progressBar.setVisible(true);
          progressBar.setIndeterminate(false);
          progressBar.setValue(value);
        }
      }
    });
  }

  /**
   * Initializer of non generated components.
   */
  private void initNotGeneratedComponents() {
    browserDockable = new DefaultDockable("browser", new JTree());
    navigatorDockable = new DefaultDockable("navigator", new JTextArea());
    
    try {
      loadDockModel();
      getFrame().add((SplitDock)dockModel.getRootDock("totalSplitDock"), BorderLayout.CENTER);
    }
    catch(Exception ex)  {
      dockModel = new FloatDockModel();
      dockModel.addOwner("frame", getFrame());
      DockingManager.setDockModel(dockModel);
      TabDock topLeftDock = new TabDock();
      topLeftDock.setName("topLeftDock");
      topLeftDock.addDockable(browserDockable, new Position(0));
      TabDock bottomLeftDock = new TabDock();
      bottomLeftDock.setName("bottomLeftDock");
      bottomLeftDock.addDockable(navigatorDockable, new Position(0));
      SplitDock topSplitDock = new SplitDock();
      topSplitDock.addChildDock(topLeftDock, new Position(Position.CENTER));
      SplitDock bottomSplitDock = new SplitDock();
      bottomSplitDock.addChildDock(bottomLeftDock, new Position(Position.CENTER));
  //    dockModel.addRootDock("topSplitDock", topSplitDock, getFrame());
  //    dockModel.addRootDock("bottomSplitDock", bottomSplitDock, getFrame());
      SplitDock totalSplitDock = new SplitDock();
      totalSplitDock.addChildDock(topSplitDock, new Position(Position.TOP));
      totalSplitDock.addChildDock(bottomSplitDock, new Position(Position.BOTTOM));
      dockModel.addRootDock("totalSplitDock", totalSplitDock, getFrame());
      getFrame().add(totalSplitDock, BorderLayout.CENTER);
    }
  }
  
  /** Loader of the dock model. */
  void loadDockModel() throws IOException {
    // Create the map with the dockables, that the decoder needs.
    Map<String, Dockable> dockablesMap = new HashMap<String, Dockable>();
    dockablesMap.put(browserDockable.getID(), browserDockable);
    dockablesMap.put(navigatorDockable.getID(), navigatorDockable);
    // Create the map with the owner windows, that the decoder needs.
    Map ownersMap = new HashMap();
    ownersMap.put("frame", getFrame());
    // Decode the file.
    String exportFilePath = new File(getContext().getLocalStorage().getDirectory(), DOCK_NAME + "." + DockModelPropertiesEncoder.EXTENSION).getPath();
    DockModelPropertiesDecoder decoder = new DockModelPropertiesDecoder();
		dockModel = decoder.decode(exportFilePath, dockablesMap, ownersMap, null);
  }
  
  /** Storer of the dock model. */
  void storeDockModel() throws IOException {
    String exportFilePath = new File(getContext().getLocalStorage().getDirectory(), DOCK_NAME + "." + DockModelPropertiesEncoder.EXTENSION).getPath();
    DockModelPropertiesEncoder encoder = new DockModelPropertiesEncoder();
    encoder.export(dockModel, exportFilePath);
  }
  
  @Action
  public void showAboutDialog() {
    if (aboutDialog == null) {
      JFrame mainFrame = App.getApplication().getMainFrame();
      aboutDialog = new AboutDialog(mainFrame);
      aboutDialog.setLocationRelativeTo(mainFrame);
    }
    App.getApplication().show(aboutDialog);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    mainPanel = new javax.swing.JPanel();
    menuBar = new javax.swing.JMenuBar();
    javax.swing.JMenu fileMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
    javax.swing.JMenu helpMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
    statusPanel = new javax.swing.JPanel();
    javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
    statusMessageLabel = new javax.swing.JLabel();
    statusAnimationLabel = new javax.swing.JLabel();
    progressBar = new javax.swing.JProgressBar();

    mainPanel.setName("mainPanel"); // NOI18N

    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 521, Short.MAX_VALUE)
    );
    mainPanelLayout.setVerticalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 327, Short.MAX_VALUE)
    );

    menuBar.setName("menuBar"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.oki.elv.client.gui.App.class).getContext().getResourceMap(View.class);
    fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
    fileMenu.setName("fileMenu"); // NOI18N

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.oki.elv.client.gui.App.class).getContext().getActionMap(View.class, this);
    exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
    exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
    exitMenuItem.setName("exitMenuItem"); // NOI18N
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
    helpMenu.setName("helpMenu"); // NOI18N

    aboutMenuItem.setAction(actionMap.get("showAboutDialog")); // NOI18N
    aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
    aboutMenuItem.setName("aboutMenuItem"); // NOI18N
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    statusPanel.setName("statusPanel"); // NOI18N

    statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

    statusMessageLabel.setName("statusMessageLabel"); // NOI18N

    statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

    progressBar.setName("progressBar"); // NOI18N

    javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
    statusPanel.setLayout(statusPanelLayout);
    statusPanelLayout.setHorizontalGroup(
      statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
      .addGroup(statusPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(statusMessageLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(statusAnimationLabel)
        .addContainerGap())
    );
    statusPanelLayout.setVerticalGroup(
      statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(statusPanelLayout.createSequentialGroup()
        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(statusMessageLabel)
          .addComponent(statusAnimationLabel)
          .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(3, 3, 3))
    );

    setComponent(mainPanel);
    setMenuBar(menuBar);
    setStatusBar(statusPanel);
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel mainPanel;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JProgressBar progressBar;
  private javax.swing.JLabel statusAnimationLabel;
  private javax.swing.JLabel statusMessageLabel;
  private javax.swing.JPanel statusPanel;
  // End of variables declaration//GEN-END:variables
  private final Timer messageTimer;
  private final Timer busyIconTimer;
  private final Icon idleIcon;
  private final Icon[] busyIcons = new Icon[15];
  private int busyIconIndex = 0;
  private JDialog aboutDialog;
  private Dockable browserDockable;
  private Dockable navigatorDockable;
	private	DockModel dockModel;
}
