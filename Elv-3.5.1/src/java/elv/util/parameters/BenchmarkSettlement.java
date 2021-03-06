/*
 * BenchmarkSettlement.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a generic settlement parameter.
 * @author Qpa
 */
public class BenchmarkSettlement extends Settlement
{
  
  /**
   * Constants.
   */
  private final static java.lang.String FILE = "benchmark-settlements.param";
  private final static java.lang.String ICON_FILE = "settlements.gif";
  private final static java.lang.String TITLE = "Settlements.Benchmark";
  
  /**
   * Constructor.
   */
  public BenchmarkSettlement()
  {
  }
  
  /**
   * Constructor.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public BenchmarkSettlement(java.lang.String paragraph, boolean isPlace, java.lang.String[] codes, java.lang.String name, double area, double xCoordinate, double yCoordinate, Aggregation aggregation, int[] districtCodes) throws java.lang.Exception
  {
    super(paragraph, isPlace, codes, name, area, xCoordinate, yCoordinate, aggregation, districtCodes);
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this settlement.
   */
  public java.lang.String toString()
  {
    return getName();
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Settlement</CODE>.
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
    int paragraphLevel = new java.util.StringTokenizer(paragraph, ".").countTokens();
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
    java.util.StringTokenizer sT = new java.util.StringTokenizer(npsds, "|");
    java.lang.String name = sT.nextToken();
    double area = 0;
    double xCoordinate = 0;
    double yCoordinate = 0;
    elv.util.parameters.Aggregation aggregation = new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO);
    int[] districtCodes = null;
    boolean isRealSettlement = (codes.length == 3);
    if(isRealSettlement || isPlace)
    {
      area = java.lang.Double.parseDouble(sT.nextToken());
      xCoordinate = java.lang.Double.parseDouble(sT.nextToken());
      yCoordinate = java.lang.Double.parseDouble(sT.nextToken());
      java.lang.String[] ds = sT.nextToken().split(java.util.regex.Pattern.quote(","));
      districtCodes = new int[Aggregation.getAllAggregations().size()];
      districtCodes[Aggregation.NO] = java.lang.Integer.parseInt(codes[0]); // No aggregation.
      districtCodes[Aggregation.CUSTOM] = 0; // Custom aggregation.
      for(int i = 0; i < ds.length; i++)
      {
        districtCodes[i + Aggregation.CUSTOM + 1] = java.lang.Integer.parseInt(ds[i]);
      }
    }
    return (P)new BenchmarkSettlement(paragraph, isPlace, codes, name, area, xCoordinate, yCoordinate, aggregation, districtCodes);
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
    java.util.StringTokenizer sT = new java.util.StringTokenizer(tfp, "|");
    boolean isPlace = java.lang.Boolean.parseBoolean(sT.nextToken());
    java.lang.String paragraph = sT.nextToken();
    java.lang.String csnpsds = line.substring(line.indexOf("=") + 1);
    java.lang.String cs = csnpsds.substring(0, csnpsds.indexOf(" "));
    java.lang.String[] codes = cs.split(java.util.regex.Pattern.quote(","));
    java.lang.String npsds = csnpsds.substring(csnpsds.indexOf(" ") + 1);
    sT = new java.util.StringTokenizer(npsds, "|");
    java.lang.String name = sT.nextToken();
    double area = 0;
    double xCoordinate = 0;
    double yCoordinate = 0;
    elv.util.parameters.Aggregation aggregation = new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO);
    int[] districtCodes = null;
    boolean isRealSettlement = (codes.length == 3);
    if(isRealSettlement || isPlace)
    {
      area = java.lang.Double.parseDouble(sT.nextToken());
      xCoordinate = java.lang.Double.parseDouble(sT.nextToken());
      yCoordinate = java.lang.Double.parseDouble(sT.nextToken());
      aggregation = new elv.util.parameters.Aggregation(sT.nextToken());
      java.lang.String[] ds = sT.nextToken().split(java.util.regex.Pattern.quote(","));
      districtCodes = new int[Aggregation.getAllAggregations().size()];
      for(int i = 0; i < ds.length; i++)
      {
        districtCodes[i] = java.lang.Integer.parseInt(ds[i]);
      }
    }
    return (P)new BenchmarkSettlement(paragraph, isPlace, codes, name, area, xCoordinate, yCoordinate, aggregation, districtCodes);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public <P extends Parameter> P getDefault() throws java.lang.Exception
  {
    return (P)new BenchmarkSettlement("", true, new java.lang.String[]{"0", "0", "0"}, "All settlements", 0, 0, 0, new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO), new int[]{0});
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

