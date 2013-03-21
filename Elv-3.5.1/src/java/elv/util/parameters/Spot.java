/*
 * Spot.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a spot - an agglomaration of districts.
 * @author Qpa
 */
public class Spot implements java.lang.Cloneable
{
  
  /**
   * Constants.
   */
  public final static java.lang.String TITLE = "Spots";
  public final static java.lang.String AGGREGATE_TITLE = "Spots.Aggregate";
  public final static int UNFAVORABLE_CODE = -1;
  public final static int FAVORABLE_CODE = -2;
  
  /**
   * Variables.
   */
  private int code;
  private java.lang.String name = "";
  private double area = 0;
  private double xCoordinate = 0;
  private double yCoordinate = 0;
  private elv.task.executables.SpotResult result;
  private java.util.Vector<elv.task.executables.SpotYearResult> yearResults = new java.util.Vector<elv.task.executables.SpotYearResult>();
  
  /**
   * Constructor.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public Spot(java.lang.String line) throws java.lang.Exception
  {
    //Line:  code|name
    java.util.StringTokenizer sT = new java.util.StringTokenizer(line, "|");
    code = java.lang.Integer.parseInt(sT.nextToken());
    name = sT.nextToken();
  }
  
  /**
   * Constructor.
   * @param code the numeric code of spot.
   * @param name the name of spot.
   * @param result the result of spot.
   */
  public Spot(int code, java.lang.String name, elv.task.executables.SpotResult result)
  {
    this.code = code;
    this.name = name;
    this.result = result;
  }
  
  /**
   * Constructor.
   * @param code the numeric code of spot.
   * @param name the name of spot.
   * @param area the area of spot.
   * @param xCoordinate the X-coordinate of spot.
   * @param yCoordinate the Y-coordinate of spot.
   * @param result the result of spot.
   */
  public Spot(int code, java.lang.String name, double area, double xCoordinate, double yCoordinate, elv.task.executables.SpotResult result)
  {
    this.code = code;
    this.name = name;
    this.area = area;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.result = result;
  }
  
  /**
   * Method for getting the <CODE>code</CODE> variable.
   * @return the code of this spot.
   */
  public int getCode()
  {
    return code;
  }
  
  /**
   * Method for getting the <CODE>name</CODE> variable.
   * @return the name of this spot.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for setting the <CODE>name</CODE> variable.
   * @param name the new name of this spot.
   */
  public void setName(java.lang.String name)
  {
    this.name = name;
  }
  
  /**
   * Method for getting the <CODE>area</CODE> variable.
   * @return the area of this spot.
   */
  public double getArea()
  {
    return area;
  }
  
  /**
   * Method for getting the <CODE>area</CODE> variable.
   * @return the area of this spot in km2.
   */
  public double getKm2Area()
  {
    return area / 1000000;
  }
  
  /**
   * Method for setting the <CODE>area</CODE> variable.
   * @param area the new area of this spot.
   */
  public void setArea(double area)
  {
    this.area = area;
  }
  
  /**
   * Method for getting the <CODE>xCoordinate</CODE> variable.
   * @return the X-coordinate of this spot.
   */
  public double getXCoordinate()
  {
    return xCoordinate;
  }
  
  /**
   * Method for setting the <CODE>xCoordinate</CODE> variable.
   * @param xCoordinate the new X-coordinate of this spot.
   */
  public void setXCoordinate(double xCoordinate)
  {
    this.xCoordinate = xCoordinate;
  }
  
  /**
   * Method for getting the <CODE>yCoordinate</CODE> variable.
   * @return the Y-coordinate of this spot.
   */
  public double getYCoordinate()
  {
    return yCoordinate;
  }
  
  /**
   * Method for setting the <CODE>yCoordinate</CODE> variable.
   * @param yCoordinate the new Y-coordinate of this spot.
   */
  public void setYCoordinate(double yCoordinate)
  {
    this.yCoordinate = yCoordinate;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param spot an <CODE>elv.util.propeties.Spot</CODE> object.
   * @return true if the given spot equals with this spot.
   */
  public boolean equals(java.lang.Object spot)
  {
    boolean isEqual = false;
    if(spot != null && spot instanceof Spot)
    {
      isEqual = (code == ((Spot)spot).code);
    }
    return isEqual;
  }
  
  /**
   * Method for getting the result of this spot.
   * @return a result object.
   */
  public elv.task.executables.SpotResult getResult()
  {
    return result;
  }
  
  /**
   * Method for setting the result of this spot.
   * @param result the new result of this spot.
   */
  public void setResult(elv.task.executables.SpotResult result)
  {
    this.result = result;
  }
  
  /**
   * Method for getting the year results of this spot.
   * @return a vector of year results.
   */
  public java.util.Vector<elv.task.executables.SpotYearResult> getYearResults()
  {
    return yearResults;
  }
  
  /**
   * Method for setting the year results of this spot.
   * @param result the new year results this of spot.
   */
  public void setYearResults(java.util.Vector<elv.task.executables.SpotYearResult> yearResults)
  {
    this.yearResults = yearResults;
  }
  
}
