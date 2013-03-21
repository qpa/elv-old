/*
 * BaseSettlement.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a bease settlement.
 * @author Elv
 */
public class BaseSettlement extends Settlement
{
  
  /**
   * Constants.
   */
  private final static java.lang.String FILE = "base-settlements.param";
  private final static java.lang.String ICON_FILE = "settlements.gif";
  private final static java.lang.String TITLE = "Settlements.Base";
  
  /**
   * Constructor.
   */
  public BaseSettlement()
  {
  }
  
  /**
   * Constructor.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public BaseSettlement(java.lang.String paragraph, boolean isPlace, java.lang.String[] codes, java.lang.String name, double area, double xCoordinate, double yCoordinate, Aggregation aggregation, int[] districtCodes) throws java.lang.Exception
  {
    super(paragraph, isPlace, codes, name, area, xCoordinate, yCoordinate, aggregation, districtCodes);
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this settlement.
   */
  public java.lang.String toString()
  {
    java.lang.String string = "";
    if((isRealSettlement() || isPlace()))
    {
      string = getName() + " [" + getAggregation() + (getDistrictCode() != 0 ? " <" + getDistrictCode() + ">" : "") + "]";
    }
    else
    {
      string = getName();
    }
    return string;
  }
  
  /**
   * Method for getting all existing settlements from a file.
   * @return a vector of settlements.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized <S extends Settlement> java.util.Vector<S> getAllSettlements() throws java.lang.Exception
  {
    java.util.Vector<S> settlements = new java.util.Vector<S>();
    java.lang.String line;
    java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new elv.util.Util().getClass().getResource("/resources/settlement.properties").openStream(), elv.util.Util.FILE_ENCODING));
    while((line = fileReader.readLine()) != null)
    {
      settlements.add((S)parseInit(line));
    }
    fileReader.close();
    return settlements;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Settlement</CODE>.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public <P extends Parameter> P parseInit(java.lang.String line) throws java.lang.Exception
  {
    //Line:  paragraph0.paragraph1. ... .paragraphN= name
    //Line:  paragraph0.paragraph1. ... .paragraphN=placeCode name|area|xCoordidate|yCoordinate|districtCode1, ...districtCodeN
    //Line:  paragraph0.paragraph1. ... .paragraphN=placeCode,statisticalCode,postalCode name|area|xCoordidate|yCoordinate|districtCode1, ...districtCodeN
    boolean isPlace = false;
    java.lang.String paragraph  = line.substring(0, line.indexOf("="));
    int paragraphLevel = paragraph.split(java.util.regex.Pattern.quote(".")).length;
    if(paragraphLevel == 5)
    {
      isPlace = true;
    }
    else
    {
      isPlace = false;
    }
    java.lang.String csnpsds = line.substring(line.indexOf("=") + 1);
    java.lang.String cs = csnpsds.substring(0, csnpsds.indexOf(" "));
    java.lang.String[] codes = cs.split(java.util.regex.Pattern.quote(","));
    java.lang.String npsds = csnpsds.substring(csnpsds.indexOf(" ") + 1);
    java.lang.String[] splits = npsds.split(java.util.regex.Pattern.quote("|"));
    java.lang.String name = splits[0];
    double area = 0;
    double xCoordinate = 0;
    double yCoordinate = 0;
    elv.util.parameters.Aggregation aggregation = new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO);
    int[] districtCodes = null;
    boolean isRealSettlement = (codes.length == 3);
    if(isRealSettlement || isPlace)
    {
      area = java.lang.Double.parseDouble(splits[1]);
      xCoordinate = java.lang.Double.parseDouble(splits[2]);
      yCoordinate = java.lang.Double.parseDouble(splits[3]);
      java.lang.String[] ds = splits[4].split(java.util.regex.Pattern.quote(","));
      districtCodes = new int[Aggregation.getAllAggregations().size()];
      districtCodes[Aggregation.NO] = java.lang.Integer.parseInt(codes[0]); // No aggregation.
      districtCodes[Aggregation.CUSTOM] = 0; // Custom aggregation.
      for(int i = 0; i < ds.length; i++)
      {
        districtCodes[i + Aggregation.CUSTOM + 1] = java.lang.Integer.parseInt(ds[i]);
      }
    }
    return (P)new BaseSettlement(paragraph, isPlace, codes, name, area, xCoordinate, yCoordinate, aggregation, districtCodes);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public <P extends Parameter> P parse(java.lang.String line) throws java.lang.Exception
  {
    //Line:  isPlace|paragraph0.paragraph1. ... .paragraphN= name
    //Line:  isPlace|paragraph0.paragraph1. ... .paragraphN=placeCode name|area|xCoordidate|yCoordinate|aggregation|customDistrictCode, districtCode1, ...districtCodeN
    //Line:  isPlace|paragraph0.paragraph2. ... .paragraphN=placeCode,statisticalCode,postalCode name|area|xCoordidate|yCoordinate|aggregation|customDistrictCode, districtCode1, ...districtCodeN
    java.lang.String tfp = line.substring(0, line.indexOf("="));
    java.lang.String[] splits = tfp.split(java.util.regex.Pattern.quote("|"));
    boolean isPlace = java.lang.Boolean.parseBoolean(splits[0]);
    java.lang.String paragraph = splits[1];
    java.lang.String csnpsds = line.substring(line.indexOf("=") + 1);
    java.lang.String cs = csnpsds.substring(0, csnpsds.indexOf(" "));
    java.lang.String[] codes = cs.split(java.util.regex.Pattern.quote(","));
    java.lang.String npsds = csnpsds.substring(csnpsds.indexOf(" ") + 1);
    splits = npsds.split(java.util.regex.Pattern.quote("|"));
    java.lang.String name = splits[0];
    double area = 0;
    double xCoordinate = 0;
    double yCoordinate = 0;
    elv.util.parameters.Aggregation aggregation = new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO);
    int[] districtCodes = null;
    boolean isRealSettlement = (codes.length == 3);
    if(isRealSettlement || isPlace)
    {
      area = java.lang.Double.parseDouble(splits[1]);
      xCoordinate = java.lang.Double.parseDouble(splits[2]);
      yCoordinate = java.lang.Double.parseDouble(splits[3]);
      aggregation = new elv.util.parameters.Aggregation(splits[4]);
      java.lang.String[] ds = splits[5].split(java.util.regex.Pattern.quote(","));
      districtCodes = new int[Aggregation.getAllAggregations().size()];
      for(int i = 0; i < ds.length; i++)
      {
        districtCodes[i] = java.lang.Integer.parseInt(ds[i]);
      }
    }
    return (P)new BaseSettlement(paragraph, isPlace, codes, name, area, xCoordinate, yCoordinate, aggregation, districtCodes);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public <P extends Parameter> P getDefault() throws java.lang.Exception
  {
    return (P)new BaseSettlement("", true, new java.lang.String[]{"0", "0", "0"}, "All settlements", 0, 0, 0, new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO), new int[]{0});
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
   * @return the icon of parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public  javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/" + ICON_FILE));
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

