/**
 * Files.java
 */
package org.oki.elv.server.io;

import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * Server-side file methods.
 * @author Elv
 */
public final class Files extends org.oki.elv.common.io.Files {
  /** Lock object. */
  public static final Object LOCK = new Object();
  
  /**
   * Creator of the given folder.
   * The creation is made with numbering, if the target already exists.
   * @param folderPath the folder path.
   * @return the created folder path (numbered, if needed).
   * @throws IOException
   */
  public static String createFolder(String folderPath) throws IOException {
    synchronized (LOCK) {
      File folder = new File(folderPath);
      File parentFolder = (File)new File(folderPath).getParentFile();
      String numberedFolderPath = getNumbered(folder, parentFolder.listFiles(), getFileExtension(folderPath));
      folder = new File(numberedFolderPath);
      boolean isOk = folder.mkdir();
      if(!isOk) {
        File.umount();
        throw new IOException("Cannot create <" + folder + ">!");
      }
      File.umount();
      return numberedFolderPath;
    }
  }

  /**
   * Mover of the source folder to the target folder.
   * The move is made with numbering, if the target already exists.
   * @param sourceFolderPath the source folder path.
   * @param targetFolderPath the target folder path.
   * @return the target folder path (numbered, if needed).
   * @throws IOException
   */
  public static String moveFolder(String sourceFolderPath, String targetFolderPath) throws IOException {
    synchronized (LOCK) {
      File sourceFolder = new File(sourceFolderPath);
      File targetFolder = new File(targetFolderPath);
      File parentFolder = (File)new File(targetFolder).getParentFile();
      String numberedFolderPath = getNumbered(targetFolder, parentFolder.listFiles(), getFileExtension(targetFolderPath));
      targetFolder = new File(numberedFolderPath);
      boolean isOk = sourceFolder.renameTo(targetFolder);
      if(!isOk) {
        File.umount();
        throw new IOException("Cannot move <" + sourceFolder + "> to <" + targetFolder + ">!");
      }
      File.umount();
      return numberedFolderPath;
    }
  }
  
  /**
   * Copier of the source folder to the target folder.
   * The copy is made with numbering, if the target already exists.
   * @param sourceFolderPath the source folder path.
   * @param targetFolderPath the target folder path.
   * @return the target folder path (numbered, if needed).
   * @throws IOException
   */
  public static String copyFolder(String sourceFolderPath, String targetFolderPath) throws IOException {
    synchronized (LOCK) {
      File sourceFolder = new File(sourceFolderPath);
      File targetFolder = new File(targetFolderPath);
      File parentFolder = (File)new File(targetFolder).getParentFile();
      String numberedFolderPath = getNumbered(targetFolder, parentFolder.listFiles(), getFileExtension(targetFolderPath));
      targetFolder = new File(numberedFolderPath);
      boolean isOk = sourceFolder.copyAllTo(targetFolder);
      if(!isOk) {
        File.umount();
        throw new IOException("Cannot copy <" + sourceFolder + "> to <" + targetFolder + ">!");
      }
      File.umount();
      return numberedFolderPath;
    }
  }

  /**
   * Deleter of the given folder.
   * @param folderPath the folder path.
   * @throws IOException
   */
  public static void deleteFolder(String folderPath) throws IOException {
    synchronized (LOCK) {
      File folder = new File(folderPath);
      boolean isOk = folder.deleteAll();
      if(!isOk) {
        File.umount();
        throw new IOException("Cannot delete <" + folder + ">!");
      }
      File.umount();
    }
  }
  
  /**
   * Encoder of the bean into the given file.
   * @param bean the bean object.
   * @param filePath the file path.
   * @param persistenceDelegate the encoding delegate.
   * @throws IOException
   */
  public static void encode(Object bean, String filePath, PersistenceDelegate persistenceDelegate) throws IOException {
    synchronized (LOCK) {
      File file = new File(filePath);
      if(file.isDirectory()) {
        throw new IOException("Cannot encode to <" + file + ">!");
      }
      XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)));
      xmlEncoder.setPersistenceDelegate(bean.getClass(), persistenceDelegate);
      try {
        xmlEncoder.writeObject(bean);
      }
      finally {
        xmlEncoder.close();
      }
      File.umount();
    }
  }
  
  /**
   * Decoder of the bean from the given file.
   * @param <B> the type of the bean.
   * @param filePath the file path.
   * @return the decoded bean object.
   * @throws IOException 
   */
  @SuppressWarnings("unchecked")
  public static <B> B decode(String filePath) throws IOException {
    synchronized (LOCK) {
      File file = new File(filePath);
      if(file.isDirectory()) {
        throw new IOException("Cannot decode from <" + file + ">!");
      }
      B bean = null;
      XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
      try {
        bean = (B)xmlDecoder.readObject();
      }
      finally {
        xmlDecoder.close();
      }
      File.umount();
      return bean;
    }
  }
  
  /**
   * Getter of the numbered entry name for the given entry between the array of entries.
   * @param <T> the type of the entry.
   * @param entry the entry.
   * @param entries the already existing entries.
   * @param postfix the postfix string of the entry, the numbering has to be done before the postfix.
   * @return the numbered (if needed) entry name.
   */
  public static <T> String getNumbered(T entry, T[] entries, String postfix) {
    if(entry == null) {
      return null;
    }
    else if("".equals(entry.toString())) {
      return "";
    }
    if(entries == null || entries.length == 0) {
      return entry.toString();
    }
    String newEntryString = entry.toString();
    Object pureEntry = entry;
    if(postfix == null || postfix.equals("")) {
      postfix = "";
    }
    else {
      pureEntry = entry.toString().substring(0, entry.toString().lastIndexOf(postfix));
    }
    for(int i = 0; i < entries.length; i++) {
      boolean found = false;
      for(Object iteratorEntry : entries) {
        if(iteratorEntry.toString().startsWith(newEntryString)) {
          found = true;
          break;
        }
      }
      if(!found) {
        break;
      }
      newEntryString = pureEntry + "_" + Integer.toString(i + 1) + postfix;
    }
    return newEntryString;
  }
}
