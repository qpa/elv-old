package org.oki.elv.common.parameter;

import java.util.ArrayList;
import java.util.List;
import org.oki.elv.common.util.Name;

/**
 * Range.
 * @author Elv
 */
public final class Range implements Name {
  /** Type of range. */
  public enum TYPE {
    /** By statistical code. */
    STAT("Stat", 0),
    /** By postal code. */
    POST("Post", 1),
    /** By locality code. */
    LOC("Loc", 2),
    /** By a predefined "MM" code. */
    MM("Mm", 3),
    /** By a predefined "small-range" code. */
    SMALL("Small", 4),
    /** By a custom code.*/
    CUSTOM("Cust", 5);
    
    /** Type name. */
    public final String name;
    /** Type index (in the range codes). */
    public final int index;
    
    /**
     * Creates a <code>TYPE</code> from the given string.
     * @param name the name of a range type.
     * @return the converted range type.
     */
    public static TYPE fromString(String name) {
      for(TYPE iteratorT : TYPE.values()) {
        if(iteratorT.name.equals(name)) {
          return iteratorT;
        }
      }
      throw new IllegalArgumentException("Not a valid range type name: \"" + name + "\"");
    }
    
    /**
     * Contructor.
     * @param name range name.
     */
    private TYPE(String name, int index) {
      this.name = name;
      this.index = index;
    }
    
    @Override
    public String toString() {
      return name;
    }
  }
  
  /** Bar. */
  public static final String BAR = "|";
  
  /** The type of the range. */
  private TYPE type;
  /** The code of the range. */
  private String code;
  /** The name of the range. */
  private String name;
  /** The settlements in the range. */
  private int settlementCount;
  /** The area of the range. */
  private double area;
  /** The X coordinate of the range. */
  private double xCoordinate;
  /** The Y coordinate of the range. */
  private double yCoordinate;
  /** The settlements in the range. */
  private List<Territory> settlements;
  
  /**
   * Constructor.
   * @param type the type of range.
   * @param code the numeric code of range.
   * @param name the name of range.
   * @param settlementCount the count of settlements of range.
   * @param settlements the settlements of range.
   * @param area the area of range.
   * @param xCoordinate the X-coordinate of range.
   * @param yCoordinate the Y-coordinate of range.
   */
  public Range(TYPE type, String code, String name, int settlementCount, List<Territory> settlements, double area, double xCoordinate, double yCoordinate) {
    this.type = type;
    this.code = code;
    this.name = name;
    this.settlementCount = settlementCount;
    this.settlements = settlements;
    this.area = area;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
  }
  
  /**
   * Getter of the type.
   * @return the type.
   */
  public final TYPE getType() {
    return type;
  }
  
  /**
   * Getter of the code.
   * @return the code.
   */
  public final String getCode() {
    return code;
  }
  
  @Override
  public final String getName() {
    return name;
  }
  
  @Override
  public final void setName(String name) {
    this.name = name;
  }
  
  /**
   * Getter of the settlementCount.
   * @return the settlementCount.
   */
  public final int getSettlementCount() {
    return settlementCount;
  }

  /**
   * Setter of the settlementCount.
   * @param settlementCount the settlementCount to set.
   */
  final void setSettlementCount(int settlementCount) {
    this.settlementCount = settlementCount;
  }

  /**
   * Getter of the area.
   * @return the area.
   */
  public final double getArea() {
    return area;
  }
  
  /**
   * Setter of the area.
   * @param area the area to set.
   */
  final void setArea(double area) {
    this.area = area;
  }

  /**
   * Getter of the area in km2.
   * @return the area in km2.
   */
  public final double getKm2Area() {
    return area / 1000000;
  }
  
  /**
   * Getter of the xCoordinate.
   * @return the xCoordinate.
   */
  public final double getXCoordinate() {
    return xCoordinate;
  }
  
  /**
   * Setter of the xCoordinate.
   * @param coordinate the xCoordinate to set.
   */
  final void setXCoordinate(double coordinate) {
    xCoordinate = coordinate;
  }

  /**
   * Getter of the yCoordinate.
   * @return the yCoordinate.
   */
  public final double getYCoordinate() {
    return yCoordinate;
  }

  /**
   * Setter of the yCoordinate.
   * @param coordinate the yCoordinate to set.
   */
  final void setYCoordinate(double coordinate) {
    yCoordinate = coordinate;
  }

  /**
   * Getter of the settlements.
   * @return the settlements.
   */
  final List<Territory> getSettlements() {
    return settlements;
  }

  /**
   * Tester.
   * @param settlement 
   * @return true, if the settlements of this range contains the given settlement.
   */
  final boolean contains(Territory settlement) {
    return settlements.contains(settlement);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(area);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Range other = (Range) obj;
    if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area)) {
      return false;
    }
    if (code == null) {
      if (other.code != null) {
        return false;
      }
    } else if (!code.equals(other.code)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "[<" + code + "> " + type + "] " + name;
  }
  
  /**
   * Overridden method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the line reprezentation of this range.
   */
  public String toLine() {
    return type + BAR + code + BAR + name + BAR + settlementCount + BAR + area + BAR + xCoordinate + BAR + yCoordinate;
  }
  
  /**
   * Creator of a <code>Range</code> from a string.
   * @param string the string representation of a range.
   * @return the constructed range.
   */
  public static Range fromLine(String string) {
    //Line: type|code|name|settlementCount|area|xCoordinate|yCoordinate
    String[] splits = string.split(BAR);
    TYPE type = TYPE.fromString(splits[0]);
    String code = splits[1];
    String name = splits[2];
    int settlementCount = Integer.parseInt(splits[3]);
    double area = Double.parseDouble(splits[4]);
    double xCoordinate = Double.parseDouble(splits[5]);
    double yCoordinate = Double.parseDouble(splits[6]);
    // The settlements are not saved.
    List<Territory> settlements = new ArrayList<Territory>();
    return new Range(type, code, name, settlementCount, settlements, area, xCoordinate, yCoordinate);
  }
}
