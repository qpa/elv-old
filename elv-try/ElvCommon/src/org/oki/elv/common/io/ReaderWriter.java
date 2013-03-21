package org.oki.elv.common.io;

/**
 * ReaderWriter.
 * @author Elv
 * @param <O> the type of readable and writeable object.
 */
public interface ReaderWriter<O> {
  /**
   * Opener of the read. 
   * @throws Exception
   */
  public void openRead() throws Exception;
  
  /**
   * Changer of the direction. 
   * @throws Exception
   */
  public void changeReadToWrite() throws Exception;
  
  /**
   * Getter of the isReadable.
   * @return the state of read.
   */
  public boolean isReadable();
  
  /**
   * Closer. 
   * @throws Exception
   */
  public void closeWrite() throws Exception;
  
  /** 
   * Reader. 
   * @param object the comparable object.
   * @return the read object.
   */
  public O read(O object);
  
  /** 
   * Writer. 
   * @param object the object to write.
   */
  public void write(O object);
}
