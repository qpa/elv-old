/*
 * Diagnosis.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a generic diagnosis parameter.
 * @author Qpa
 */
public abstract class Diagnosis extends Parameter
{
  
  /**
   * Constant.
   */
  public final static java.lang.String TITLE = "Diagnosises";
  protected final static java.lang.String DEFAULT_LINE = "0= <All>";
  
  /**
   * Variables.
   */
  private java.lang.String paragraph;
  private java.lang.String[] codes;
  private java.lang.String name;
  private int paragraphLevel;
  
  /**
   * Constructor.
   */
  public Diagnosis()
  {
  }
  
  /**
   * Constructor.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public Diagnosis(java.lang.String line) throws java.lang.Exception
  {
    //Line:  paragraph1.paragraph2. ... .paragraphN=code1,code2,...,codeN name
    paragraph = line.substring(0, line.indexOf("="));
    java.lang.String cd = line.substring(line.indexOf("=") + 1);
    java.lang.String cs = cd.substring(0, cd.indexOf(" "));
    name = cd.substring(cd.indexOf(" ") + 1);
    codes = cs.split(java.util.regex.Pattern.quote(","));
    paragraphLevel = new java.util.StringTokenizer(paragraph, ".").countTokens();
  }

  /**
   * Method for determining if this diagnosis is a real diagnosis, or just a grouping-one.
   * @return true, if this diagnosis is a real diagnosis, false otherwise.
   */
  public boolean isRealDiagnosis()
  {
    return true;
  }
  
  /**
   * Method for getting the <CODE>paragraph</CODE> variable.
   * @return the paragraph of this diagnosis.
   */
  public java.lang.String getParagraph()
  {
    return paragraph;
  }
  
  /**
   * Method for setting the <CODE>paragraph</CODE> variable.
   * @param paragraph the new paragraph.
   */
  public void setParagraph(java.lang.String paragraph)
  {
    this.paragraph = paragraph;
  }
  
  /**
   * Method for getting the <CODE>codes</CODE> variable.
   * @return the codes of this diagnosis.
   */
  public java.lang.String[] getCodes()
  {
    return codes;
  }
  
  /**
   * Method for getting the <CODE>name</CODE> variable.
   * @return the name of this diagnosis.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for getting the <CODE>paragraphLevel</CODE> variable.
   * @return the paragraph level. 
   */
  public int getParagraphLevel()
  {
    return paragraphLevel;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this settlement.
   */
  public java.lang.String toString()
  {
    return codes[0] + " " + name;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the line reprezentation of this settlement.
   */
  public java.lang.String toLine()
  {
    java.lang.String cs = codes[0];
    for(int i = 1; i < codes.length; i++)
    {
      cs = cs + "," + codes[i];
    }
    return paragraph + "=" + cs + " " + name;
  }
  
  /**
   * Method for getting all existing diagnosises from a file.
   * @return a vector of diagnosises.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public abstract <D extends Diagnosis> java.util.Vector<D> getAllDiagnosises() throws java.lang.Exception;
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the icon of parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public  javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new elv.util.Action(elv.util.Action.DIAGNOSISES).getIcon();
  }
  
}
