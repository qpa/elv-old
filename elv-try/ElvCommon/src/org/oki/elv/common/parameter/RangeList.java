package org.oki.elv.common.parameter;

import java.util.ArrayList;

/**
 * List of ranges. 
 * @author Elv
 */
public final class RangeList extends ArrayList<Range> {
  /** File name. */
  public static final String FILE_NAME = "range.par";
  
  /**
   * Getter of a range list.
   * @return an empty range list.
   */
  public static final RangeList getRangeList() {
    return new RangeList();
  }
  
  /** Contructor. */
  private RangeList() {
    super();
  }

  /**
   * Creator of a new custom range code.
   * @return the newly created code.
   */
  String createNewCustomCode() {
    int newCode = 0;
    while(newCode <= size()) {
      newCode++;
      boolean isCreated = false;
      for(int i = 0; i < size(); i++) {
        Range iteratorRange = get(i);
        if(Range.TYPE.CUSTOM == iteratorRange.getType()) {
          int iteratorCode = Integer.valueOf(iteratorRange.getCode());
          if(newCode == iteratorCode) {
            isCreated = true;
            break;
          }
        }
      }
      if(!isCreated) {
        return String.valueOf(newCode);
      }
    }
    return "";
  }
}
