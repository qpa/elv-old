/*
 * ExecutionFile.java
 */

package elv.task.executables;

/**
 * Class for representing an execution file.
 * @author Elv
 */
public class ExecutionFile implements elv.util.Visualizable
{
  
  // Variables.
  /** The file name. */
  private java.lang.String fileName;
  /** The file title. */
  private java.lang.String fileTitle;
  
  
  /**
   * Constructor.
   * @param fileName the file name.
   * @param fileTitle the file title.
   */
  public ExecutionFile(java.lang.String fileName, java.lang.String fileTitle)
  {
    this.fileName = fileName;
    this.fileTitle = fileTitle;
  }
  
  /**
   * Method for getting the execution file name.
   * @return the file name.
   */
  public java.lang.String getFileName()
  {
    return fileName;
  }
  
  /**
   * Method for getting the execution file title.
   * @return the file title.
   */
  public java.lang.String getFileTitle()
  {
    return fileTitle;
  }
  
  /**
   * Method for getting the execution file icon.
   * @return the file icon.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    javax.swing.ImageIcon icon;
      java.lang.String fileExtension = elv.util.Util.getFileExtension(fileName);
      if(fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg") ||
        fileExtension.equalsIgnoreCase("gif") || fileExtension.equalsIgnoreCase("png"))
      {
        icon = new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/image.gif"));
      }
      else if(fileExtension.equalsIgnoreCase("pdf"))
      {
        icon = new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/pdf.gif"));
      }
      else if(fileExtension.equalsIgnoreCase("htm") || fileExtension.equalsIgnoreCase("html"))
      {
        icon = new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/html.gif"));
      }
      else
      {
        icon = new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/plain.gif"));
      }
    return icon;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this executable.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(fileTitle);
  }
  
}
