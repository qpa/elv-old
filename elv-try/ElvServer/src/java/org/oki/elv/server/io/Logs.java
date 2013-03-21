/**
 * Logs.java
 */
package org.oki.elv.server.io;

import java.text.DateFormat;
import java.util.Date;

/**
 * Logs of the server events. 
 * @author Elv
 */
public class Logs {
  /** Output label. */
  private static final String OUT = "ELV-OUT";
  /** Exception label. */
  private static final String EXC = "ELV-EXC";
  /** Date format. */
  private static DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
  
  /**
   * Output logger (tomcat output).
   * @param message the message to log.
   */
  public static void out(String message) {
    try {
      System.out.println(OUT + ": " + dateFormat.format(new Date()) + message);
    }
    catch(Exception e) {}
  }
  
  /**
   * Exception logger (tomcat output).
   * @param exc the exception to log.
   */
  public static void exc(Exception exc) {
    try {
      System.err.println(EXC + ": " + dateFormat.format(new Date()));
      exc.printStackTrace();
    }
    catch(Exception e) {}
  }
}