/*
 * MorfologyDiagnosis.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a morfology parameter.
 * @author Elv
 */
public class MorfologyDiagnosis extends Diagnosis
{
  
  /**
   * Constants.
   */
  private final static java.lang.String FILE = "morfologies.param";
  private final static java.lang.String FILE_BASE = "morfology";
  public final static java.lang.String TITLE = "Diagnosis.Morfologies";
  /** The line reprezentation of the default diagnosis. */
  private final static java.lang.String DEFAULT_LINE = "0=M0000/0 <All>";
  
  /**
   * Constructor.
   */
  public MorfologyDiagnosis()
  {
  }
  
  /**
   * Constructor.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public MorfologyDiagnosis(java.lang.String line) throws java.lang.Exception
  {
    super(line);
  }

  /**
   * Overridden method from <CODE>elv.util.parameters.Diagnosis</CODE>.
   * @return true, if this diagnosis is a real diagnosis, false otherwise.
   */
  public boolean isRealDiagnosis()
  {
    return (getCodes()[0].length() == 7);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public <P extends Parameter> P getDefault() throws java.lang.Exception
  {
    return (P)new MorfologyDiagnosis(DEFAULT_LINE);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public <P extends Parameter> P parse(java.lang.String line) throws java.lang.Exception
  {
    return (P)new MorfologyDiagnosis(line);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Diagnosis</CODE>.
   * @return a vector of diagnosises.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized <D extends Diagnosis> java.util.Vector<D> getAllDiagnosises() throws java.lang.Exception
  {
    java.util.Vector<D> diagnosises = new java.util.Vector<D>();
    java.lang.String line;
    java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new elv.util.Util().getClass().getResource("/resources/diagnosises/" + FILE_BASE + "_" +  elv.util.Util.getActualLocale().getLanguage() + ".properties").openStream(), elv.util.Util.FILE_ENCODING));
    while((line = fileReader.readLine()) != null)
    {
      diagnosises.add((D)parse(line));
    }
    fileReader.close();
    return diagnosises;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the file name of parameter.
   */
  public java.lang.String getFile()
  {
    return FILE;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the title of parameter.
   */
  public java.lang.String getTitle()
  {
    return elv.util.Util.translate(TITLE);
  }
  
}
