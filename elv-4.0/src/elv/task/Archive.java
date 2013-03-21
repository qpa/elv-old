/*
 * Archive.java
 */
package elv.task;

/**
 * Class for reprezenting an archive of tasks.
 * @author Elv
 */
public class Archive<P> extends Container
{
 
  //Constants.
  /** The title of the archive. */
  public final static java.lang.String TITLE = "Archive";
  /** The extension of the archive file name. */
  public final static java.lang.String EXTENSION = "zip";
  /** The dummy entry the archive. */
  public final static java.lang.String DUMMY_ENTRY = "_dummy";
  /** The not word character in the regular expression. */
  public final static java.lang.String NOT_WORD_CHARACTER = "\\b";   
  /**
   * Constructor for a dafult archive on the server.
   * @param user the user of the archive.
   * @param name the name of the archive.
   * @throws java.lang.Exception if there is any problem with creating.
   */
  public Archive(elv.util.User user, java.lang.String name)
  {
    super(user, name + "." + EXTENSION);
  }
  
  /**
   * Method to setting the name of this archive.
   * @param name the new name.
   */
  public void setName(java.lang.String name)
  {
    super.setName(name + "." + EXTENSION);
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any problem with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, true, TYPE_NAME, TITLE));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, MODIFIED_NAME, new java.util.Date(new java.io.File(getFolderPath()).lastModified())));
  }
  
  /**
   * Method for creating the archive.
   * @throws java.lang.Exception if there is any creation error.
   */
  public synchronized void create() throws java.lang.Exception
  {
    java.util.zip.ZipOutputStream zipStream = new java.util.zip.ZipOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(getFolderPath())));
    java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(DUMMY_ENTRY);
    zipStream.putNextEntry(zipEntry);
    zipStream.close();
  }
  
  /**
   * Method for moving the container.
   * @param newContainer the new container.
   * @throws java.lang.Exception if there is any moving error.
   */
  public synchronized void move(Container newContainer) throws java.lang.Exception
  {
    java.lang.String newFolderPath = newContainer.getFolderPath();
    if(!newContainer.getUser().equals(getUser()))
    {
      java.lang.String newName = elv.util.Util.getNumbered(newContainer.getName(), elv.util.Util.vectorize(new java.io.File(newContainer.getUser().getFolderPath()).list()), "." + EXTENSION);
      newFolderPath = newContainer.getUser().getFolderPath() + "/" + newName;
    }
    boolean done = new java.io.File(getFolderPath()).renameTo(new java.io.File(newFolderPath));
    if(!done)
    {
      throw new java.io.IOException("Cannot rename " + getFolderPath() + " to " + newFolderPath);
    }
    super.setName(newContainer.getName());
  }
  
  /**
   * Method for creating a temporary archive container.
   * @throws java.lang.Exception if there is any creation error.
   */
  private java.io.File createTemp() throws java.lang.Exception
  {
    return java.io.File.createTempFile(elv.util.Root.NAME + "_" + TITLE + "_", "." + EXTENSION + ".tmp" , new java.io.File(getUser().getFolderPath()));
  }
  
  /**
   * Method for adding the given path structure to the archive.
   * @param path the path of a file or a directory.
   * @throws java.lang.Exception if there is any error with adding.
   */
  public synchronized void add(java.lang.String path) throws java.lang.Exception
  {
    java.io.File archiveFile = new java.io.File(getFolderPath());
    java.io.File tmpFile = createTemp();
    java.util.zip.ZipOutputStream zipStream = new java.util.zip.ZipOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(tmpFile)));
    java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(archiveFile);
    java.util.Vector<java.lang.String> entryNames = new java.util.Vector<java.lang.String>();
    java.util.Enumeration existingEntries = archiveZipFile.entries();
    // Copy existing archive.
    while(existingEntries.hasMoreElements())
    {
      java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)existingEntries.nextElement();
      entryNames.add(iteratorEntry.getName());
      zipStream.putNextEntry(iteratorEntry);
      if(!iteratorEntry.isDirectory())
      {
        int count;
        byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
        java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.BUFFER_SIZE);
        while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
        {
          zipStream.write(buffer, 0,  count);
        }
        inputStream.close();
      }
    }
    archiveZipFile.close();
    try
    {
      add(path, "", zipStream, entryNames);
    }
    finally
    {
      zipStream.flush();
      zipStream.close();
      archiveFile.delete();
      tmpFile.renameTo(archiveFile);
    }
  }
  
  /**
   * Method for recursive adding.
   * @param path the path of a file or a directory.
   * @param the archive zip stream.
   * @param parentEntryName the name of the parent of this entry in the archive.
   * @param entryNames the allready existing entries in this archive.
   * @throws java.lang.Exception if there is any error with adding.
   */
  private synchronized void add(java.lang.String path, java.lang.String parentEntryName, java.util.zip.ZipOutputStream zipStream, java.util.Vector<java.lang.String> entryNames) throws java.lang.Exception
  {
    java.io.File file = new java.io.File(path);
    if(file.isFile())
    {
      java.lang.String entryName = elv.util.Util.getNumbered(parentEntryName + file.getName(), entryNames, "/");
      entryNames.add(entryName);
      java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(entryName);
      zipStream.putNextEntry(zipEntry);
      int count;
      byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
      java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file), elv.util.Util.BUFFER_SIZE);
      while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
      {
        zipStream.write(buffer, 0,  count);
      }
      inputStream.close();
    }
    else if(file.isDirectory())
    {
      java.lang.String entryName = elv.util.Util.getNumbered(parentEntryName + file.getName() + "/", entryNames, "/");
      entryNames.add(entryName);
      java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(entryName);
      zipStream.putNextEntry(zipEntry);
      java.lang.String[] files = file.list();
      for(java.lang.String iteratorFile : files)
      {
        add(path + "/" + iteratorFile , zipEntry.getName(), zipStream, entryNames);
      }
    }
  }
  
  /**
   * Method for getting an entry and all it's subentries from the archive.
   * @param entry the zip archive entry.
   * @param folderPath the directory where to extract the entry.
   * @throws java.lang.Exception if there is any error with the deleting.
   */
  public synchronized void get(java.lang.String entry, java.lang.String folderPath) throws java.lang.Exception
  {
    java.lang.String newName = elv.util.Util.getNumbered(entry, elv.util.Util.vectorize(new java.io.File(folderPath).list()), null);
    java.io.File archiveFile = new java.io.File(getFolderPath());
    java.io.File extractFolder = new java.io.File(folderPath);
    java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(archiveFile);
    for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
    {
      java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
      if(iteratorEntry.getName().matches(".*" + NOT_WORD_CHARACTER + java.util.regex.Pattern.quote(entry) + NOT_WORD_CHARACTER + ".*"))
      {
        // Construct the file or directory name from the entry.
        java.lang.String pathName = "";
        java.lang.String[] splits = iteratorEntry.getName().split(java.util.regex.Pattern.quote("/"));
        for(java.lang.String iteratorSplit : splits)
        {
          if(iteratorSplit.equals(entry))
          {
            pathName += newName;
          }
          else if(!pathName.equals(""))
          {
            pathName += "/" + iteratorSplit;
          }
        }
        pathName = folderPath + "/" + pathName;
        // Extract the directories and files
        if(iteratorEntry.isDirectory())
        {
          new java.io.File(pathName).mkdir();
        }
        else
        {
          int count;
          byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
          java.io.BufferedOutputStream outputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(pathName));
          java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.BUFFER_SIZE);
          while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
          {
            outputStream.write(buffer, 0,  count);
          }
          inputStream.close();
          outputStream.close();
        }
      }
    }
    archiveZipFile.close();
  }
  
  /**
   * Method for getting an entry and all it's subentries from the archive and to put in the given archive.
   * @param entry the zip archive entry.
   * @param targetArchive the archive where to put the entry.
   * @throws java.lang.Exception if there is any error with the deleting.
   */
  public synchronized void get(java.lang.String entry, Archive targetArchive) throws java.lang.Exception
  {
    java.io.File archiveFile = new java.io.File(getFolderPath());
    java.io.File targetArchiveFile = new java.io.File(targetArchive.getFolderPath());
    java.io.File tmpFile = createTemp();
    java.util.zip.ZipOutputStream zipStream = new java.util.zip.ZipOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(tmpFile)));
    java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(targetArchiveFile);
    java.util.Vector<java.lang.String> entryNames = new java.util.Vector<java.lang.String>();
    // Copy existing archive.
    for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
    {
      java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
      entryNames.add(iteratorEntry.getName());
      zipStream.putNextEntry(iteratorEntry);
      if(!iteratorEntry.isDirectory())
      {
        int count;
        byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
        java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.BUFFER_SIZE);
        while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
        {
          zipStream.write(buffer, 0,  count);
        }
        inputStream.close();
      }
    }
    archiveZipFile.close();
    // Copy entries.
    archiveZipFile = new java.util.zip.ZipFile(archiveFile);
    for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
    {
      java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
      if(iteratorEntry.getName().matches(".*" + NOT_WORD_CHARACTER + java.util.regex.Pattern.quote(entry) + NOT_WORD_CHARACTER + ".*"))
      {
        // Construct the new entry, which can be shorter than the iterator entry.
        java.lang.String entryName = iteratorEntry.getName().substring(iteratorEntry.getName().indexOf(entry));
        if(!(this.equals(targetArchive)))
        {
          entryName = elv.util.Util.getNumbered(iteratorEntry.getName().substring(iteratorEntry.getName().indexOf(entry)), entryNames, "/");
        }
        entryNames.add(entryName);
        java.util.zip.ZipEntry newEntry = new java.util.zip.ZipEntry(entryName);
        // Extract the directories and files
        zipStream.putNextEntry(newEntry);
        if(!iteratorEntry.isDirectory())
        {
          int count;
          byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
          java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.BUFFER_SIZE);
          while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
          {
            zipStream.write(buffer, 0,  count);
          }
          inputStream.close();
        }
      }
    }
    archiveZipFile.close();
    zipStream.flush();
    zipStream.close();
    targetArchiveFile.delete();
    tmpFile.renameTo(targetArchiveFile);
  }
  
  /**
   * Method for removing an entry and all it's subentries from the archive.
   * @param entry the zip archive entry.
   * @throws java.lang.Exception if there is any error with the deleting.
   */
  public synchronized void remove(java.lang.String entry) throws java.lang.Exception
  {
    java.io.File archiveFile = new java.io.File(getFolderPath());
    java.io.File tmpFile = createTemp();
    java.util.zip.ZipOutputStream zipStream = new java.util.zip.ZipOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(tmpFile)));
    java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(archiveFile);
    for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
    {
      java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
      if(!iteratorEntry.getName().matches(".*" + NOT_WORD_CHARACTER + java.util.regex.Pattern.quote(entry) + NOT_WORD_CHARACTER + ".*"))
      {
        zipStream.putNextEntry(iteratorEntry);
        if(!iteratorEntry.isDirectory())
        {
          int count;
          byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
          java.io.BufferedInputStream inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.BUFFER_SIZE);
          while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
          {
            zipStream.write(buffer, 0,  count);
          }
          inputStream.close();
        }
      }
    }
    archiveZipFile.close();
    zipStream.flush();
    zipStream.close();
    archiveFile.delete();
    tmpFile.renameTo(archiveFile);
  }
  
  /**
   * Method for getting the icon of the archive.
   * @return the icon of archive.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/archive.gif"));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this container.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.getFileBase(getName());
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return a clone of this archive.
   */
  public java.lang.Object clone()
  {
    return new Archive(getUser(), getName());
  }
  
  /**
   * Method for loading the children of a parentable.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized void loadChildren() throws java.lang.Exception
  {
    children = new java.util.Vector<P>();
    java.io.File archiveFile = new java.io.File(getFolderPath());
    if(archiveFile.length() == 0)
    {
      return;
    }
    java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(getFolderPath());
    for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
    {
      java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
      if(iteratorEntry.isDirectory())
      {
        java.lang.String[] splits = iteratorEntry.getName().split(java.util.regex.Pattern.quote("/"));
        if(splits.length == 1)
        {
          Task child = Task.parse(this, splits[0]);
          children.add((P)child);
        }
      }
    }
    archiveZipFile.close();
  }
  
}
