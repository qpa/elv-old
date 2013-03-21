package org.oki.elv.common.parameter;

import org.oki.elv.common.util.Paragraph;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.oki.elv.common.util.Name;

/**
 * A tree of Territories.
 * @author Elv
 */
public class Territory extends Paragraph implements Name {
  /** Territory type. */
  public static enum TYPE {
    /** Base territory type. */
    BASE("base-territory.par"),
    /** Benchmark territory type. */
    BENCHMARK("benchmark-territory.par");
    
    /** File name. */
    public final String fileName;
    
    /**
     * Contructor.
     * @param fileName type name.
     */
    private TYPE(String fileName) {
      this.fileName = fileName;
    }
  }
  
  /** Singleton istance of the default base territory root. */
  public static final Territory DEFAULT_BASE_ROOT = new Territory(TYPE.BASE, DEFAULT_LABEL, DEFAULT_TEXT, true);
  /** Singleton istance of the default benchmark territory root. */
  public static final Territory DEFAULT_BENCHMARK_ROOT = new Territory(TYPE.BENCHMARK, DEFAULT_LABEL, DEFAULT_TEXT, true);

  /**
   * Getter of a base territory root instance.
   * @return a base territory root.
   */
  public static final Territory getBaseRoot() {
    return new Territory(TYPE.BASE, DEFAULT_LABEL, DEFAULT_TEXT, true);
  }
  
  /**
   * Getter of a benchmark territory root instance.
   * @return a benchmark territory root.
   */
  public static final Territory getBenchmarkRoot() {
    return new Territory(TYPE.BENCHMARK, DEFAULT_LABEL, DEFAULT_TEXT, true);
  }
  
  /** The type of territory. */
  protected TYPE type;
  /** The name of the territory. */
  private String name;
  /** The codes of the territory. */
  private List<String> codes;
  /** The range type of the territory. */
  private Range.TYPE rangeType;
  /** The area of the territory (in m2). */
  private double area;
  /** The X coordinate of the territory. */
  private double xCoordinate;
  /** The Y coordinate of the territory. */
  private double yCoordinate;

  /**
   * Constructor.
   * @param type the type of diagnosis.
   * @param label the paragraph label.
   * @param text the paragraph text.
   * @param isDefault if true, the default text is provided.
   */
  protected Territory(TYPE type, String label, String text, boolean isDefault) {
    super(label, text);
    this.type = type;
    if(isDefault) {
      //Line:  label0.label1. ... .labelN= name
      //Line:  label0.label1. ... .labelN=code name
      //Line:  label0.label1. ... .labelN=code0, ...codeN name|area|xCoordidate|yCoordinate
      String[] textParts = text.split(Pattern.quote(SPACE), -1);
      codes = Arrays.asList(textParts[0].split(Pattern.quote(COMMA)));
      codes.add(Range.TYPE.CUSTOM.index, "");
      String[] props = textParts[1].split(Pattern.quote(BAR));
      name = props[0];
      if(isSettlement()) {
        rangeType = Range.TYPE.STAT;
        area = Double.parseDouble(props[1]);
        xCoordinate = Double.parseDouble(props[2]);
        yCoordinate = Double.parseDouble(props[3]);
      }
    }
    else {
      //Line:  label0.label1. ... .labelN= name
      //Line:  label0.label1. ... .labelN=code name
      //Line:  label0.label1. ... .labelN=code0, ...codeN,codeCustom name|rangeType|area|xCoordidate|yCoordinate
      String[] textParts = text.split(Pattern.quote(SPACE), -1);
      codes = Arrays.asList(textParts[0].split(Pattern.quote(COMMA)));
      String[] props = textParts[1].split(Pattern.quote(BAR));
      name = props[0];
      if(isSettlement()) {
        rangeType = Range.TYPE.fromString(props[1]);
        area = Double.parseDouble(props[2]);
        xCoordinate = Double.parseDouble(props[3]);
        yCoordinate = Double.parseDouble(props[4]);
      }
    }
  }
  
  /**
   * Getter of the type.
   * @return the type
   */
  public TYPE getType() {
    return type;
  }

  /**
   * Checker of settlement state.
   * A settlement is a leaf in the territory tree (the smallest territory).
   * @return true, if this territory is a settlement, false otherwise.
   */
  public boolean isSettlement() {
    return codes.size() < 1;
//    return isLeaf();
  }
  
  /**
   * Checker of locality state.
   * A locality could be one settlement or a bunch of settlements, which have the same code (POSTAL or STATISTICAL).
   * @return true, if this territory is a locality, false otherwise.
   */
  public boolean isLocality() {
    return getDepth() == 4;
  }
  
  /**
   * Getter of the codes.
   * @return the codes of this territory.
   */
  public List<String> getCodes() {
    return codes;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Getter of the area.
   * @return the area.
   */
  public double getArea()  {
    return area;
  }
  
  /**
   * Getter of the area in km2.
   * @return the area in km2.
   */
  public double getKm2Area() {
    return area / 1000000;
  }
  
  /**
   * Getter of the xCoordinate.
   * @return the xCoordinate.
   */
  public double getXCoordinate() {
    return xCoordinate;
  }
  
  /**
   * Getter of the yCoordinate.
   * @return the yCoordinate.
   */
  public double getYCoordinate() {
    return yCoordinate;
  }
  
  /**
   * Getter of the range type.
   * @return the range type.
   */
  public Range.TYPE getRangeType() {
    return rangeType;
  }
  
  /**
   * Setter of the range type for the settlements of this territory.
   * @param rangeType the range type to set.
   * @param customCode the custom range code, if the rangeType == Range.TYPE.CUSTOM, omitted otherwise.
   */
  public void setSettlementsRangeType(Range.TYPE rangeType, String customCode) {
    if(isSettlement()) {
      this.rangeType = rangeType;
      if(Range.TYPE.CUSTOM == rangeType) {
        codes.set(rangeType.index, customCode);
      }
    }
    else {
      for(Paragraph iteratorChild : getChildren()) {
        ((Territory)iteratorChild).setSettlementsRangeType(rangeType, customCode);
      }
    }
  }
  
  /**
   * Getter of the range code.
   * @return the range code.
   */
  public String getRangeCode() {
    return codes.get(rangeType.index);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codes == null) ? 0 : codes.hashCode());
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
    final Territory other = (Territory) obj;
    if (codes == null) {
      if (other.codes != null) {
        return false;
      }
    } else if (!codes.equals(other.codes)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    if((isSettlement())) {
      return name + " [" + rangeType + " <" + getRangeCode() + ">]";
    }
    else {
      return name;
    }
  }
  
  @Override
  public String getText() {
    String cs = codes.get(0);
    for(int i = 1; i < codes.size(); i++) {
      cs += COMMA + codes.get(i);
    }
    String attributes = name;
    if(isSettlement()) {
      attributes = name + BAR + area + BAR + xCoordinate + BAR + yCoordinate + BAR + rangeType;
    }
    return cs + SPACE + attributes;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  protected Territory fromLine(String line, boolean isDefault) {
    String[] parts = line.split(SEPARATOR);
    return new Territory(type, parts[0], parts[1], isDefault);
  }

  @Override
  protected String getComparableText() {
    return name;
  }
  
  /**
   * Creator of ranges for a root territory.
   * @param existingRangeList an existing range list for this territory.
   * @return the newly crated range list, keeping the changed name of the nonmodified ranges.
   */
  public RangeList createRangeList(RangeList existingRangeList) {
    assert getParent() == null: "Create not supported for non root territory!";
    assert existingRangeList != null : "RangeList not initialized or not loaded!";

    // Get a new instance of RangeList.
    RangeList ranges = RangeList.getRangeList();
    aggregateRanges(ranges);
    
    for (Range iteratorRange : ranges) {
      // Divide the aggregation.
      iteratorRange.setXCoordinate(iteratorRange.getXCoordinate() / iteratorRange.getSettlementCount());
      iteratorRange.setYCoordinate(iteratorRange.getYCoordinate() / iteratorRange.getSettlementCount());
      
      // Set back manually defined name for nonmodified ranges.
      for (Range iR : existingRangeList) {
        if(iteratorRange.equals(iR)) {
          iteratorRange.setName(iR.getName());
        }
      }
    }
    return ranges;
  }
  
  /**
   * Creator of ranges for a territory.
   */
  private void aggregateRanges(RangeList ranges) {
    if(isSettlement()) {
      for(Range iteratorRange : ranges) {
        if(rangeType == iteratorRange.getType() && iteratorRange.getCode().equals(getRangeCode()) && !iteratorRange.contains(this)) {
          String previousName = iteratorRange.getSettlements().get(iteratorRange.getSettlementCount() - 1).getName();
          iteratorRange.setName(iteratorRange.getName() + (iteratorRange.getSettlementCount() < 3 && !name.equals(previousName) ? "-" + name : (iteratorRange.getSettlementCount() == 3 ? "-..." : "")));

          iteratorRange.setSettlementCount(iteratorRange.getSettlementCount() + 1);
          iteratorRange.setArea(iteratorRange.getArea() + area);
          iteratorRange.setXCoordinate(iteratorRange.getXCoordinate() + xCoordinate);
          iteratorRange.setYCoordinate(iteratorRange.getYCoordinate() + yCoordinate);
          
          iteratorRange.getSettlements().add(this);
          break;
        }
      }
      Range newRange = new Range(rangeType, getRangeCode(), name, 1, Arrays.asList(this), area, xCoordinate, yCoordinate);
      // Create sorted range list.
      int insertIndex = 0;
      for(Range iR : ranges) {
        insertIndex++;
        if(newRange.getCode().compareTo(iR.getCode()) <= 0) {
          break;
        }
      }
      ranges.add(insertIndex, newRange);
    }
    else {
      for(Paragraph iteratorChild : getChildren()) {
        ((Territory)iteratorChild).aggregateRanges(ranges);
      }
    }
  }
}
