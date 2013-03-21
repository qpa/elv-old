/**
 * Dialogs.java
 */
package org.oki.elv.client.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Message dialogs.
 * @author Elv
 */
public class Dialogs {
  /**
   * Exception dialog.
   */
  public static void showException(Component parentComponent, Exception exc) {
    exc.printStackTrace();
    String stackString = exc.toString();
    StackTraceElement[] stackLines = exc.getStackTrace();
    for(int i = 0; i < stackLines.length; i++) {
      stackString += "\n  at " + stackLines[i].toString();
    }
    JTextArea stackPane = new JTextArea(stackString);
    stackPane.setFont(new Font("DialogInput", Font.PLAIN, 11));
    stackPane.setEditable(false);
    JScrollPane exceptionPane = new JScrollPane(stackPane);
    exceptionPane.setPreferredSize(new Dimension(400,200));
    
    String paneTitle = "Exception";
    JOptionPane.showMessageDialog(parentComponent, exceptionPane, paneTitle, JOptionPane.ERROR_MESSAGE);
  }
}
