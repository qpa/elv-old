package org.oki.elv.common.parameter;

import org.oki.elv.common.util.Paragraph;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.oki.elv.common.util.Name;

/**
 * A tree of Diagnosises.
 * @author Elv
 */
public class Diagnosis extends Paragraph implements Name {
  /** Diagnosis type. */
  public static enum TYPE {
    /** Addmission type. */
    ADDMISSION("addmission.par"),
    /** Disease type. */
    DISEASE("disease.par"),
    /** Dismissal type. */
    DISMISSAL("dismissal.par"),
    /** Morbidity type. */
    MORBIDITY("morbidity.par"),
    /** Morfology type. */
    MORFOLOGY("morfology.par"),
    /** Mortality type. */
    MORTALITY("mortality.par");
    
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
  
  /** Singleton istance of the default addmission root. */
  public static final Diagnosis DEFAULT_ADDMISSION_ROOT = new Diagnosis(TYPE.ADDMISSION, DEFAULT_LABEL, DEFAULT_TEXT);
  /** Singleton istance of the default disease root. */
  public static final Diagnosis DEFAULT_DISEASE_ROOT = new Diagnosis(TYPE.DISEASE, DEFAULT_LABEL, DEFAULT_TEXT);
  /** Singleton istance of the default dismissal root. */
  public static final Diagnosis DEFAULT_DISMISSAL_ROOT = new Diagnosis(TYPE.DISMISSAL, DEFAULT_LABEL, DEFAULT_TEXT);
  /** Singleton istance of the default morbidity root. */
  public static final Diagnosis DEFAULT_MORBIDITY_ROOT = new Diagnosis(TYPE.MORBIDITY, DEFAULT_LABEL, DEFAULT_TEXT);
  /** Singleton istance of the default morfology root. */
  public static final Diagnosis DEFAULT_MORFOLOGY_ROOT = new Diagnosis(TYPE.MORFOLOGY, DEFAULT_LABEL, DEFAULT_TEXT);
  /** Singleton istance of the default mortality root. */
  public static final Diagnosis DEFAULT_MORTALITY_ROOT = new Diagnosis(TYPE.MORTALITY, DEFAULT_LABEL, DEFAULT_TEXT);

  /**
   * Getter of an addmission root instance.
   * @return an addmission diagnosis.
   */
  public static final Diagnosis getAddmissionRoot() {
    return new Diagnosis(TYPE.ADDMISSION, DEFAULT_LABEL, DEFAULT_TEXT);
  }
  
  /**
   * Getter of an disease root instance.
   * @return an disease diagnosis.
   */
  public static final Diagnosis getDiseaseRoot() {
    return new Diagnosis(TYPE.DISEASE, DEFAULT_LABEL, DEFAULT_TEXT);
  }
  
  /**
   * Getter of an dismissal root instance.
   * @return an dismissal diagnosis.
   */
  public static final Diagnosis getDismissalRoot() {
    return new Diagnosis(TYPE.DISMISSAL, DEFAULT_LABEL, DEFAULT_TEXT);
  }
  
  /**
   * Getter of an morbidity root instance.
   * @return an morbidity diagnosis.
   */
  public static final Diagnosis getMorbidityRoot() {
    return new Diagnosis(TYPE.MORBIDITY, DEFAULT_LABEL, DEFAULT_TEXT);
  }
  
  /**
   * Getter of a morfology root instance.
   * @return a morfology diagnosis.
   */
  public static final Diagnosis getMorfologyRoot() {
    return new Diagnosis(TYPE.MORFOLOGY, DEFAULT_LABEL, DEFAULT_TEXT);
  }
  
  /**
   * Getter of an mortality root instance.
   * @return an mortality diagnosis.
   */
  public static final Diagnosis getMortalityRoot() {
    return new Diagnosis(TYPE.MORTALITY, DEFAULT_LABEL, DEFAULT_TEXT);
  }
  
  /** The type. */
  protected TYPE type;
  /** The codes array. */
  protected String[] codes;
  /** The name. */
  protected String name;

  /**
   * Constructor.
   * @param type the type of diagnosis.
   * @param label the paragraph label.
   * @param text the paragraph text.
   */
  protected Diagnosis(TYPE type, String label, String text) {
    super(label, text);
    this.type = type;
    String[] textParts = text.split(Pattern.quote(SPACE));
    codes = textParts[0].split(Pattern.quote(COMMA));
    name = textParts[1];
  }

  /**
   * Determines if this diagnosis is a real diagnosis, or just a grouping-one.
   * @return true, if this diagnosis is a real diagnosis, false otherwise.
   */
  public final boolean isRealDiagnosis() {
    return isLeaf();
  }
  
  /**
   * Getter of the type.
   * @return the type.
   */
  public final TYPE getType() {
    return type;
  }

  /**
   * Getter of the codes.
   * @return the codes.
   */
  public final List<String> getCodes() {
    return Collections.unmodifiableList(Arrays.asList(codes));
  }
  
  @Override
  public final String getName() {
    return name;
  }
  
  @Override
  public final void setName(String name) {
    this.name = name;
  }
  

  @Override
  public String getText() {
    StringBuilder textBuilder = new StringBuilder(codes.length * codes[0].length());
    textBuilder.append(codes[0]);
    for(int i = 1; i < codes.length; i++) {
      textBuilder.append(COMMA).append(codes[i]);
    }
    textBuilder.append(SPACE).append(name);
    return textBuilder.toString();
  }

  @Override
  public String toString() {
    return codes[0] + SPACE + name;
  }
  
  @Override
  protected String getComparableText() {
    return "";
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Diagnosis fromLine(String line, boolean isDefault) {
    String[] parts = line.split(SEPARATOR);
    return new Diagnosis(type, parts[0], parts[1]);
  }
}
