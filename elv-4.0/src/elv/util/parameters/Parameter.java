/*
 * Parameter.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a generic parameter, overridden by the real parameters.
 * @author Elv
 */
public abstract class Parameter implements elv.util.Visualizable, java.io.Serializable
{
  
  /**
   * Constants.
   */
  public final static java.lang.String TITLE = "Parameters";
  
  /**
   * Constructor.
   */
  public Parameter()
  {
  }
  
  /**
   * Method for parsing a string into a parameter.
   * @param line the stored line reprezentation.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public abstract <P extends Parameter> P parse(java.lang.String line) throws java.lang.Exception;
  
  /**
   * Method for stored line reprezentation.
   * @return the line reprezentation of this parameter.
   */
  public abstract java.lang.String toLine();
  
  /**
   * Method for getting the default parameter.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public abstract <P extends Parameter> P getDefault() throws java.lang.Exception;
  
  /**
   * Method for loading the parameter elements from a file.
   * @param containerPath the full path name of the parameter container (which could be a directory or an archive file).
   * @param name the parameter file name (the name of the file in case of a directory, or the zip entry name in case if an archive).
   * @param parameterType the type of parameter, which has to be loaded.
   * @return a vector of parameters.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static <P extends Parameter> java.util.Vector<P> load(java.lang.String containerPath, java.lang.String name, P parameterType) throws java.lang.Exception
  {
    java.util.Vector<P> parameters = new java.util.Vector<P>();
    java.lang.String line;
    java.io.File container = new java.io.File(containerPath);
    if(!container.exists())
    {
      throw new java.io.FileNotFoundException(containerPath);
    }
    if(container.isDirectory()) // Directory.
    {
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(containerPath + "/" + name), elv.util.Util.FILE_ENCODING));
      while((line = fileReader.readLine()) != null)
      {
        parameters.add((P)parameterType.parse(line));
      }
      fileReader.close();
    }
    else // Archive.
    {
      java.util.zip.ZipFile archiveZipFile = new java.util.zip.ZipFile(containerPath);
      for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
      {
        java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
        if(!iteratorEntry.isDirectory())
        {
          if(iteratorEntry.getName().equals(name))
          {
            java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(archiveZipFile.getInputStream(iteratorEntry), elv.util.Util.FILE_ENCODING));
            while((line = fileReader.readLine()) != null)
            {
              parameters.add((P)parameterType.parse(line));
            }
            fileReader.close();
            archiveZipFile.close();
            break;
          }
        }
      }
      archiveZipFile.close();
    }
    return parameters;
  }
  
  /**
   * Method for storing the parameter elements in a file.
   * @param file the parameter file.
   * @param parameters a vector of parameters.
   * @throws java.lang.Exception if there is any problem with storing.
   */
  public synchronized static void store(java.lang.String file, java.util.Vector<? extends Parameter> parameters) throws java.lang.Exception
  {
    java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(file), elv.util.Util.FILE_ENCODING));
    for(Parameter iteratorParameter : parameters)
    {
      fileWriter.println(iteratorParameter.toLine());
    }
    fileWriter.close();
  }
  
  /**
   * Method for getting the file name.
   * @return the file name of parameter.
   */
  public abstract java.lang.String getFile();
  
  /**
   * Method for getting the title.
   * @return the title of parameter.
   */
  public abstract java.lang.String getTitle();
  
}
