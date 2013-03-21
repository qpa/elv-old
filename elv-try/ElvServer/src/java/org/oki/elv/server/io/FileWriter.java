package org.oki.elv.server.io;

import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.oki.elv.common.io.ReaderWriter;

import de.schlichtherle.io.FileOutputStream;

/**
 * File writer. Writes the objects by <code>toString()</code> method.
 * @author Elv
 */
public class FileWriter implements ReaderWriter {
  /** File path. */
  private String filePath;
  /** Output writer. */
  private PrintWriter fileWriter;

  /**
   * Contructor.
   * @param filePath
   */
  public FileWriter(String filePath) {
    this.filePath = filePath;
  }
  

  @Override
  public Object read(Object object) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void openRead() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void changeReadToWrite() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isReadable() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  
  @Override
  public void write(Object object) {
    fileWriter.println(object);
  }
  @Override
  public void closeWrite() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
