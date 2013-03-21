package org.oki.elv.common.io;

import java.io.File;

/**
 * File methods.
 * @author Elv
 */
public class Files {
  /** The file encoding in the system.*/
  public static final String FILE_ENCODING = "UTF-8";
  /** The "CSV" separator character. */
  public static final char CSV_SEPARATOR = ';';
  /** The "CSV" separator string. */
  public static String CSV_SEP = "" + CSV_SEPARATOR;
  
  /**
   * Getter of the base part of a file name.
   * @param fileName the file name.
   * @return the base name.
   */
  public static String getFileBase(String fileName) {
    String baseName = null;
    if(fileName != null) {
      // Get the last path component.
      fileName = new File(fileName).getName();
      // Get base name.
      int i = fileName.lastIndexOf('.');
      if(i > 0 && i < fileName.length() - 1) {
        baseName = fileName.substring(0, i);
      }
      else {
        baseName = fileName;
      }
    }
    return baseName;
  }
  
  /**
   * Getter of the extension part of a file name with the DOT (.).
   * @param fileName the file name.
   * @return the extension.
   */
  public static String getFileExtension(String fileName) {
    String extension = "";
    if(fileName != null) {
      // Get the last path component.
      fileName = new File(fileName).getName();
      int i = fileName.lastIndexOf('.');
      if(i > 0 && i < fileName.length() - 1) {
        extension = fileName.substring(i).toLowerCase();
      }
    }
    return extension;
  }
}
