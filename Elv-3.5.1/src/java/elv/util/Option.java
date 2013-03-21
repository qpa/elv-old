/*
 * Option.java
 */

package elv.util;

/**
 * Class for displying Option dialogs.
 * @author Qpa
 */
public class Option
{
  /**
   * Method for displaying a Yes-No-Cancel dialog.
   * @param message the message object.
   * @param title the tile of dialog.
   * @return the choosen button action.
   */
  public static int yesNoCancel(java.lang.Object message, java.lang.String title)
  {
    java.lang.String[] buttons = new java.lang.String[3];
    buttons[0] = new elv.util.Action(elv.util.Action.YES).toString();
    buttons[1] = new elv.util.Action(elv.util.Action.NO).toString();
    buttons[2] = new elv.util.Action(elv.util.Action.CANCEL).toString();
    return javax.swing.JOptionPane.showOptionDialog(elv.util.Util.getMainFrame(), message, title, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
  }
  
  /**
   * Method for displaying a Ok-Cancel dialog.
   * @param message the message object.
   * @param title the tile of dialog.
   * @return the choosen button action.
   */
  public static int okCancel(java.lang.Object message, java.lang.String title)
  {
    java.lang.String[] buttons = new java.lang.String[2];
    buttons[0] = new elv.util.Action(elv.util.Action.OK).toString();
    buttons[1] = new elv.util.Action(elv.util.Action.CANCEL).toString();
    return javax.swing.JOptionPane.showOptionDialog(elv.util.Util.getMainFrame(), message, title, javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
  }
  
  /**
   * Method for displaying an Ok dialog.
   * @param message the message object.
   * @param title the tile of dialog.
   * @return the choosen button action.
   */
  public static int ok(java.lang.Object message, java.lang.String title)
  {
    java.lang.String[] buttons = new java.lang.String[1];
    buttons[0] = new elv.util.Action(elv.util.Action.OK).toString();
    return javax.swing.JOptionPane.showOptionDialog(elv.util.Util.getMainFrame(), message, title, javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
  }
  
}
