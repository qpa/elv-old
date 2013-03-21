package org.oki.elv.common.util;

import java.io.File;
import org.oki.elv.common.io.Files;

/**
 * File path. The string representation of a path name of a file.
 * @author Elv
 */
public class Path implements Name {
  /** The file-path string. */
  protected String path;
  
  /**
   * Getter of the file-path on the disk.
   * @return the file path.
   */
  public String getPath() {
    return path;
  }
  
  /**
   * Setter of the file-path on the disk.
   * @param path the path to set.
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Getter of the base file name of this file-path.
   * The name is without extension.
   * @return the base part of the file name.
   */
  @Override
  public String getName() {
    return Files.getFileBase(new File(path).getName());
  }

  /**
   * Setter of the base name of this file-path.
   * @param name the new base name to set.
   */
  @Override
  public void setName(String name) {
    String extension = Files.getFileExtension(new File(path).getName());
    path = new File(path).getParent() + "/" + name + extension;
  }
}
