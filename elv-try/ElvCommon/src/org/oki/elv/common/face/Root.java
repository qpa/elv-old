package org.oki.elv.common.face;

import java.io.Serializable;

/**
 * System root.
 * @author Elv
 */
public final class Root implements Serializable{
  /** Serial ID. */
  private static final long serialVersionUID = 3683503953139919345L;
  
  /** The system path. */
  private String systemPath;
  /** The version of the application. */
  private String version;
  /** The maximum number of concurrently executable tasks. */
  private int maxConcurrentTasksNo;
  
  /**
   * Contructor.
   * @param systemPath
   * @param version
   * @param concurrentTaskCount
   */
  public Root(String systemPath, String version, int maxConcurrentTasksNo) {
    this.systemPath = systemPath;
    this.version = version;
    this.maxConcurrentTasksNo = maxConcurrentTasksNo;
  }

  /**
   * Getter of the system file-path.
   * @return the server-side system file-path.
   */
  public String getPath() {
    return systemPath;
  }

  /**
   * Getter of the version.
   * @return the system version.
   */
  public String getVersion(){
    return version;
  }
  
  /**
   * Getter of the maximum number of concurrently executable tasks.
   * @return the maximum value.
   */
  public int getMaxConcurrentTasksNo() {
    return maxConcurrentTasksNo;
  }
}
