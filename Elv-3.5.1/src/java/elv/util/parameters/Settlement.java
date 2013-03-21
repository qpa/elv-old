/*
 * Settlement.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a generic settlement parameter.
 * @author Qpa
 */
public abstract class Settlement extends Parameter
{
  
  /**
   * Constant.
   */
  public final static java.lang.String TITLE = "Settlements";
  public final static java.lang.String DEFAULT_LINE = "0= <*>";
  
  // The indices in the codes array.
  public final static int PLACE = 0;
  public final static int STATISTICAL = 1;
  public final static int POSTAL = 2;
  
  /**
   * Variables.
   */
  private java.lang.String paragraph;
  private boolean isPlace;
  private java.lang.String[] codes;
  private java.lang.String name;
  private double area = 0;
  private double xCoordinate = 0;
  private double yCoordinate = 0;
  private Aggregation aggregation = new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO);
  private int[] districtCodes;
  private java.util.Vector<elv.task.executables.SettlementYearResult> yearResults = new java.util.Vector<elv.task.executables.SettlementYearResult>();
  
  /**
   * Constructor.
   */
  public Settlement()
  {
  }
  
  /**
   * Constructor.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public Settlement(java.lang.String paragraph, boolean isPlace, java.lang.String[] codes, java.lang.String name, double area, double xCoordinate, double yCoordinate, Aggregation aggregation, int[] districtCodes) throws java.lang.Exception
  {
    this.paragraph = paragraph;
    this.isPlace = isPlace;
    this.codes = codes;
    this.name = name;
    this.area = area;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.aggregation = aggregation;
    this.districtCodes = districtCodes;
  }
  
  /**
   * Method for determining if this settlement is a real settlement, or just a grouping-one (country, territory, county, ...).
   * @return true, if this settlement is a real settlement, false otherwise.
   */
  public boolean isRealSettlement()
  {
    return (codes.length == 3);
  }
  
  /**
   * Method for determining if this settlement is a place.
   * A place could be one settlement or a bunch of settlements, wich have the same code (POSTAL or STATISTICAL).
   * @return true, if this settlement is a real settlement, false otherwise.
   */
  public boolean isPlace()
  {
    return isPlace;
  }
  
  /**
   * Method for getting the <CODE>paragraph</CODE> variable.
   * @return the paragraph of this settlement.
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
   * Method for getting the paragraph level.
   * @return the paragraph level. 
   */
  public int getParagraphLevel()
  {
    return new java.util.StringTokenizer(paragraph, ".").countTokens();
  }
  
  /**
   * Method for getting the <CODE>codes</CODE> variable.
   * @return the codes of this settlement.
   */
  public java.lang.String[] getCodes()
  {
    return codes;
  }
  
  /**
   * Method for getting the <CODE>name</CODE> variable.
   * @return the name of this settlement.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for getting the <CODE>area</CODE> variable.
   * @return the area of this settlement.
   */
  public double getArea()
  {
    double area = 0;
    if(isRealSettlement() || isPlace())
    {
      area = this.area;
    }
    return area;
  }
  
  /**
   * Method for getting the <CODE>area</CODE> variable.
   * @return the area of this settlement in km2.
   */
  public double getKm2Area()
  {
    double area = 0;
    if(isRealSettlement() || isPlace())
    {
      area = this.area / 1000000;
    }
    return area;
  }
  
  /**
   * Method for getting the <CODE>xCoordinate</CODE> variable.
   * @return the xCoordinate of this settlement.
   */
  public double getXCoordinate()
  {
    double xCoordinate = 0;
    if(isRealSettlement() || isPlace())
    {
      xCoordinate = this.xCoordinate;
    }
    return xCoordinate;
  }
  
  /**
   * Method for getting the <CODE>yCoordinate</CODE> variable.
   * @return the yCoordinate of this settlement.
   */
  public double getYCoordinate()
  {
    double yCoordinate = 0;
    if(isRealSettlement() || isPlace())
    {
      yCoordinate = this.yCoordinate;
    }
    return yCoordinate;
  }
  
  /**
   * Method for getting the <CODE>aggregation</CODE> variable.
   * @return the aggregation type of this settlement.
   */
  public Aggregation getAggregation()
  {
    Aggregation aggregation = null;
    if(isRealSettlement() || isPlace())
    {
      aggregation = this.aggregation;
    }
    return aggregation;
  }
  
  /**
   * Method for setting the <CODE>aggregation</CODE> variable.
   * @param aggregation the new aggregation type.
   */
  public void setAggregation(Aggregation aggregation)
  {
    this.aggregation = aggregation;
  }
  
  /**
   * Method for getting the selected district code.
   * @return the district code to which belongs this settlement.
   */
  public int getDistrictCode()
  {
    int districtCode = 0;
    if(isRealSettlement() || isPlace())
    {
      districtCode = districtCodes[aggregation.getIndex()];
    }
    return districtCode;
  }
  
  /**
   * Method for setting the "no aggregation" district code.
   * @param districtCode the new "no aggregation" district code to which belongs this settlement.
   */
  public void setNoDistrictCode(int districtCode)
  {
    if(isRealSettlement() || isPlace())
    {
      districtCodes[Aggregation.NO] = districtCode;
    }
  }
  
  /**
   * Method for setting the custom district code.
   * @param districtCode the new custom district code to which belongs this settlement.
   */
  public void setCustomDistrictCode(int districtCode)
  {
    if(isRealSettlement() || isPlace())
    {
      districtCodes[Aggregation.CUSTOM] = districtCode;
    }
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param settlement an <CODE>elv.util.proprties.Settlement</CODE> object.
   * @return true if the given settlement equals with this settlement.
   */
  public boolean equals(java.lang.Object settlement)
  {
    boolean isEqual = false;
    if(settlement != null && settlement instanceof Settlement)
    {
      if(isRealSettlement() && ((Settlement)settlement).isRealSettlement())
      {
        isEqual = (codes[STATISTICAL] == ((Settlement)settlement).codes[STATISTICAL] && codes[POSTAL] == ((Settlement)settlement).codes[POSTAL]);
      }
      else if(isPlace() && ((Settlement)settlement).isPlace())
      {
        isEqual = (codes[PLACE] == ((Settlement)settlement).codes[PLACE]);
      }
      else
      {
        isEqual = name.equals(((Settlement)settlement).name);
      }
    }
    return isEqual;
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
      string = name + " [" + aggregation + (getDistrictCode() != 0 ? " <" + getDistrictCode() + ">" : "") + "]";
    }
    else
    {
      string = name;
    }
    return string;
  }
  
  /**
   * Method for initial parsing of settlements.
   * @param line the stored line reprezentation.
   * @return a vector of settlements.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public abstract <P extends Parameter> P parseInit(java.lang.String line) throws java.lang.Exception;
  
  /**
   * Method for getting all existing settlements from a file.
   * @return a vector of settlements.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public abstract <S extends Settlement> java.util.Vector<S> getAllSettlements() throws java.lang.Exception;
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the line reprezentation of this settlement.
   */
  public java.lang.String toLine()
  {
    java.lang.String cs = codes[0];
    for(int i = 1; i < codes.length; i++)
    {
      cs += "," + codes[i];
    }
    java.lang.String ds = "";
    if(isRealSettlement() || isPlace())
    {
      for(int i = 0; i < districtCodes.length; i++)
      {
        ds += (i == 0 ? districtCodes[i] : "," + districtCodes[i]);
      }
    }
    return isPlace + "|" + paragraph + "=" + 
           cs + " " + name + (isRealSettlement() || isPlace() ? "|" + area + "|" + xCoordinate + "|" + yCoordinate + "|" +
           aggregation.getName() + "|" + ds : "");
  }
  
  /**
   * Method for getting the year results of this spot.
   * @return a vector of year results.
   */
  public java.util.Vector<elv.task.executables.SettlementYearResult> getYearResults()
  {
    return yearResults;
  }
  
  /**
   * Method for setting the year results of this spot.
   * @param result the new year results of this spot.
   */
  public void setYearResults(java.util.Vector<elv.task.executables.SettlementYearResult> yearResults)
  {
    this.yearResults = yearResults;
  }
  
}
