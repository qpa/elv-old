/*
 * District.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a district - an aggregation of settlements.
 * @author Elv
 */
public class District extends Parameter implements java.lang.Cloneable
{
  
  /**
   * Constant.
   */
  private final static java.lang.String FILE = "districts.param";
  public final static java.lang.String TITLE = "Districts";
  
  /**
   * Variables.
   */
  private int code;
  private java.lang.String name;
  private double area;
  private double xCoordinate;
  private double yCoordinate;
  private Aggregation aggregation = new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO);
  private elv.task.executables.DistrictResult result;
  private java.util.Vector<elv.task.executables.DistrictYearResult> yearResults = new java.util.Vector<elv.task.executables.DistrictYearResult>();
  private java.util.Vector<elv.task.executables.DistrictWindowIntervalResult> windowIntervalResults = new java.util.Vector<elv.task.executables.DistrictWindowIntervalResult>();
  
  /**
   * Constructor.
   */
  public District()
  {
  }
  
  /**
   * Constructor.
   * @param code the numeric code of district.
   * @param name the name of district.
   * @param area the area of district.
   * @param xCoordinate the X-coordinate of district.
   * @param yCoordinate the Y-coordinate of district.
   * @param aggregation the aggregation type of district.
   */
  public District(int code, java.lang.String name, double area, double xCoordinate, double yCoordinate, elv.util.parameters.Aggregation aggregation)
  {
    this.code = code;
    this.name = name;
    this.area = area;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.aggregation = aggregation;
  }
  
  /**
   * Method for getting the <CODE>code</CODE> variable.
   * @return the code of this district.
   */
  public int getCode()
  {
    return code;
  }
  
  /**
   * Method for getting the <CODE>name</CODE> variable.
   * @return the name of this district.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for setting the <CODE>name</CODE> variable.
   * @param name the new name of this district.
   */
  public void setName(java.lang.String name)
  {
    this.name = name;
  }
  
  /**
   * Method for getting the <CODE>area</CODE> variable.
   * @return the area of this district.
   */
  public double getArea()
  {
    return area;
  }
  
  /**
   * Method for getting the <CODE>area</CODE> variable.
   * @return the area of this district in km2.
   */
  public double getKm2Area()
  {
    return area / 1000000;
  }
  
  /**
   * Method for getting the <CODE>xCoordinate</CODE> variable.
   * @return the X-coordinate of this district.
   */
  public double getXCoordinate()
  {
    return xCoordinate;
  }
  
  /**
   * Method for getting the <CODE>yCoordinate</CODE> variable.
   * @return the Y-coordinate of this district.
   */
  public double getYCoordinate()
  {
    return yCoordinate;
  }
  
  /**
   * Method for getting the <CODE>aggregation</CODE> variable.
   * @return the aggregation of this district.
   */
  public Aggregation getAggregation()
  {
    return aggregation;
  }
  
  /*
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the clone of this district.
   */
  public java.lang.Object clone()
  {
    return new District(code, name, area, xCoordinate, yCoordinate, aggregation);
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param district an <CODE>elv.util.proprties.District</CODE> object.
   * @return true if the given district equals with this district.
   */
  public boolean equals(java.lang.Object district)
  {
    boolean isEqual = false;
    if(district != null && district instanceof District)
    {
      isEqual = (code == ((District)district).code && aggregation.equals(((District)district).aggregation));
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this district.
   */
  public java.lang.String toString()
  {
    return "[<" + code + "> " + aggregation + "] " + name;
  }
  
  /**
   * Overridden method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the line reprezentation of this district.
   */
  public java.lang.String toLine()
  {
    return code + "|" + name + "|" + area + "|" + xCoordinate + "|" + yCoordinate + "|" + aggregation.getName();
  }
  
  /**
   * Overridden method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @param parametrizable the owner.
   * @return a vector of districts.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static <P extends Parameter> java.util.Vector<P> load(java.lang.String file, P parameterType) throws java.lang.Exception
  {
    java.util.Vector<P> districts = new java.util.Vector<P>();
    java.lang.String line;
    java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file), elv.util.Util.FILE_ENCODING));
    while((line = fileReader.readLine()) != null)
    {
      P district = (P)parameterType.parse(line);
      boolean found = false;
      int insertIndex = 0;
      for(P iteratorP : districts)
      {
        insertIndex++;
        if(iteratorP.equals(district))
        {
          found = true;
          break;
        }
        else if(((District)district).code < ((District)iteratorP).code)
        {
          break;
        }
      }
      if(!found)
      {
        districts.add(insertIndex, district);
      }
    }
    fileReader.close();
    return districts;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public <P extends Parameter> P parse(java.lang.String line) throws java.lang.Exception
  {
    //Line:  code|name|area|xCoordinate|yCoordinate|aggregation
    java.lang.String[] splits = line.split(java.util.regex.Pattern.quote("|"));
    int code = java.lang.Integer.parseInt(splits[0]);
    java.lang.String name = splits[1];
    double area = java.lang.Double.parseDouble(splits[2]);
    double xCoordinate = java.lang.Double.parseDouble(splits[3]);
    double yCoordinate = java.lang.Double.parseDouble(splits[4]);
    elv.util.parameters.Aggregation aggregation = new elv.util.parameters.Aggregation(splits[5]);
    return (P)new District(code, name, area, xCoordinate, yCoordinate, aggregation);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   */
  public <P extends Parameter> P getDefault()
  {
    return (P)new District(0, "Country", 0, 0, 0, new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO));
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
    return null;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the title of parameter.
   */
  public java.lang.String getTitle()
  {
    return elv.util.Util.translate(TITLE);
  }
  
  /**
   * Method for getting the result of this district.
   * @return a result object.
   */
  public elv.task.executables.DistrictResult getResult()
  {
    return result;
  }
  
  /**
   * Method for setting the result of this district.
   * @param result the new result of this district.
   */
  public void setResult(elv.task.executables.DistrictResult result)
  {
    this.result = result;
  }
  
  /**
   * Method for getting the year results of this district.
   * @return a vector of year results.
   */
  public java.util.Vector<elv.task.executables.DistrictYearResult> getYearResults()
  {
    return yearResults;
  }
  
  /**
   * Method for setting the year results of this district.
   * @param result the new year results of this district.
   */
  public void setYearResults(java.util.Vector<elv.task.executables.DistrictYearResult> yearResults)
  {
    this.yearResults = yearResults;
  }
  
  /**
   * Method for getting the window interval results of this district.
   * @return a vector of window interval results.
   */
  public java.util.Vector<elv.task.executables.DistrictWindowIntervalResult> getWindowIntervalResults()
  {
    return windowIntervalResults;
  }
  
  /**
   * Method for setting the window interval results of this district.
   * @param result the new window interval results of this district.
   */
  public void setWindowIntervalResults(java.util.Vector<elv.task.executables.DistrictWindowIntervalResult> windowIntervalResults)
  {
    this.windowIntervalResults = windowIntervalResults;
  }
  
}
