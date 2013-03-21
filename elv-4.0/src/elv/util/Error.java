/**
 * Error.java
 */
package elv.util;

/**
 *
 * @author Elv
 */
public class Error
{
  
  /**
   * Constants.
   */
  public final static java.lang.String SERVLET_LOG = "elv.log";
  public final static java.lang.String ERROR_LOG = "error.log";
  
  /**
   * Method for displaying error messages.
   */
  public static void showErrorMessage(java.awt.Component parentComponent, java.lang.Exception exc)
  {
    exc.printStackTrace();
    java.lang.String formatedException = format(exc);
    java.lang.String stackString = formatedException;
    java.lang.StackTraceElement[] stackLines = exc.getStackTrace();
    for(int i = 0; i < stackLines.length; i++)
    {
      stackString += "\n  at " + stackLines[i].toString();
    }
    javax.swing.JTextArea stackPane = new javax.swing.JTextArea(stackString);
    stackPane.setFont(new java.awt.Font("DialogInput", java.awt.Font.PLAIN, 11));
    stackPane.setEditable(false);
    javax.swing.JScrollPane exceptionPane = new javax.swing.JScrollPane(stackPane);
    exceptionPane.setPreferredSize(new java.awt.Dimension(400,200));
    
    java.lang.String paneTitle = "Exception";
    javax.swing.JOptionPane.showMessageDialog(parentComponent, exceptionPane, paneTitle, javax.swing.JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * Method for logging server errors.
   */
  public static void logServerError(java.lang.Exception exc)
  {
    try
    {
//      java.lang.String logsFolder = elv.util.Util.getLogFolder();
//      java.io.PrintStream printStream = new java.io.PrintStream(new java.io.FileOutputStream(logsFolder + elv.util.Util.getFS() + SERVLET_LOG, true));
//      printStream.println(new java.util.Date());
//      exc.printStackTrace(printStream);
//      printStream.flush();
//      printStream.close();
      exc.printStackTrace();
    }
    catch(java.lang.Exception e)
    {
    }
  }
  
  /**
   * Method for getting the error message of a task execution.
   */
  public static java.lang.String getExecutionError(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.String file = task.getExecutionFolderPath() + "/" + ERROR_LOG;
    java.lang.String line;
    java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file), elv.util.Util.FILE_ENCODING));
    java.lang.String message = fileReader.readLine();
    while((line = fileReader.readLine()) != null)
    {
      message += "\n" + line;
    }
    fileReader.close();
    return message;
  }
  
  /**
   * Method for setting the error message of a task execution.
   */
  public static void setExecutionError(elv.task.Task task, java.lang.Exception exc)
  {
    try
    {
      java.lang.String file = task.getExecutionFolderPath() + "/" + ERROR_LOG;
      exc.printStackTrace(new java.io.PrintStream(new java.io.FileOutputStream(file)));
    }
    catch(java.lang.Exception e)
    {
      logServerError(e);
    }
  }
  
  /**
   * Method for format the error message.
   */
  private static java.lang.String format(java.lang.Exception exc)
  {
    java.lang.String formatedEx;
    formatedEx = exc.toString(); //Add formating code here
    return formatedEx;
  }
}