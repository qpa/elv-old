package org.oki.elv.common.face;

import org.oki.elv.common.util.Persistence;
import org.oki.elv.common.util.Path;
import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.io.Serializable;

import org.oki.elv.common.parameter.Diagnosis;
import org.oki.elv.common.parameter.IntervalList;
import org.oki.elv.common.parameter.Territory;

/**
 * Defines an analysis.
 * @author Elv
 */
public class Analysis extends Path implements Persistence, Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = 4948358258239737125L;

  /** Analysis types. */
  public static enum TYPE {
    /** An analysis type. */
    MORTALITY_SELECTION,
    /** An analysis type. */
    MORBIDITY_SELECTION,
    /** An analysis type. */
    POPULATION_PREPARATION,
    /** An analysis type. */
    MORTALITY_PREPARATION,
    /** An analysis type. */
    MORBIDITY_PREPARATION,
    /** An analysis type. */
    MORTALITY_STANDARDIZATION,
    /** An analysis type. */
    MORBIDITY_STANDARDIZATION,
    /** An analysis type. */
    MORTALITY_SMOOTHING,
    /** An analysis type. */
    MORBIDITY_SMOOTHING,
    /** An analysis type. */
    MORTALITY_REGION_ANALYSIS,
    /** An analysis type. */
    MORBIDITY_REGION_ANALYSIS,
    /** An analysis type. */
    MORTALITY_CLUSTER_ANALYSIS,
    /** An analysis type. */
    MORBIDITY_CLUSTER_ANALYSIS,
    /** An analysis type. */
    MORTALITY_POINT_SOURCE_ANALYSIS,
    /** An analysis type. */
    MORBIDITY_POINT_SOURCE_ANALYSIS,
    /** An analysis type. */
    TWO_FACTORS_ANALYSIS
  };
  
  /** The properties folder name. */
  public final static String PROPERTY_FOLDER = "properties";
  /** The execution folder name. */
  public final static String EXECUTION_FOLDER = "execution";
  /** The property file name. */
  public final static String PROPERTY_FILE_NAME = "task.xml";
  
  /** The type of the analysis. */
  private TYPE type;
  /** The steps of the task. */
  protected transient String[] steps;
  /** The parameter types of the task. */
  protected transient Object[] parameters;
  
  /**
   * Contructor.
   * @param path the path of the analysis.
   * @param type the type of the analysis.
   */
  public Analysis(String path, TYPE type) {
    if(path == null) {
      throw new NullPointerException("Null path!");
    }
    else if(path.equals("")) {
      throw new IllegalArgumentException("Empty path!");
    }
    this.path = path;
    this.type = type;
  }
  /**
   * Getter of the type.
   * @return the type.
   */
  public TYPE getType() {
    return type;
  }

  /**
   * Getter of steps.
   * @return an array with steps. 
   */
  public String[] getSteps() {
    return steps;
  }
  
  /**
   * Setter of steps.
   * @param steps an array of steps to set. 
   */
  public void setSteps(String steps[]) {
    this.steps = steps;
  }

  /**
   * Getter of parameters.
   * @return an array with parameters. 
   */
  public Object[] getParameters() {
    return parameters;
  }
  
  /**
   * Setter of parameters.
   * @param parameters an array of parameters to set. 
   */
  public void setParameters(Object[] parameters) {
    this.parameters = parameters;
  }
  
  /**
   * Getter of the property folder.
   * @return the property folder.
   */
  public String getPropertyFolderPath() {
    return getPath() + "/" + PROPERTY_FOLDER;
  }
  
  /**
   * Getter of the execution folder.
   * @return the execution folder.
   */
  public String getExecutionFolderPath() {
    return getPath() + "/" + EXECUTION_FOLDER;
  }
  
  @Override
  public PersistenceDelegate getPersistenceDelegate() {
    return new DefaultPersistenceDelegate(new String[]{"path", "type"});
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((path == null) ? 0 : path.hashCode());
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
    final Analysis other = (Analysis) obj;
    if (path == null) {
      if (other.path != null) {
        return false;
      }
    } else if (!path.equals(other.path)) {
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
    return getName();
  }
  
  protected void setAttributes() {
    switch(getType()) {
      case MORTALITY_SELECTION:
        steps = new String[]{"MortalitySelection"};
        parameters = new Object[3];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        break;
      case MORBIDITY_SELECTION:
        steps = new String[]{"MorbiditySelection"};
        parameters = new Object[3];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        break;
      case POPULATION_PREPARATION:
        steps = new String[]{"elv.task.executables.PopulationPreparation"};
        parameters = new Object[3];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        break;
      case MORTALITY_PREPARATION:
        steps = new String[]{"elv.task.executables.MortalityPreparation"};
        parameters = new Object[5];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Diagnosis.getDiseaseRoot();
        parameters[4] = Diagnosis.getMortalityRoot();
        break;
      case MORBIDITY_PREPARATION:
        steps = new String[]{"elv.task.executables.MorbidityPreparation"};
        parameters = new Object[7];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Diagnosis.getDiseaseRoot();
        parameters[4] = Diagnosis.getMorbidityRoot();
        parameters[5] = Diagnosis.getAddmissionRoot();
        parameters[6] = Diagnosis.getMorfologyRoot();
        break;
      case MORTALITY_STANDARDIZATION:
        steps = new String[]{"elv.task.executables.MortalityStandardPreparation",
        "elv.task.executables.Standardization"};
        parameters = new Object[6];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMortalityRoot();
        break;
      case MORBIDITY_STANDARDIZATION:
        steps = new String[]{"elv.task.executables.MorbidityStandardPreparation",
          "steps.add(new elv.task.executables.Standardization"};
        parameters = new Object[8];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMorbidityRoot();
        parameters[6] = Diagnosis.getAddmissionRoot();
        parameters[7] = Diagnosis.getMorfologyRoot();
        break;
      case MORTALITY_SMOOTHING:
        steps = new String[]{"elv.task.executables.MortalityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing"};
        parameters = new Object[6];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMortalityRoot();
        break;
      case MORBIDITY_SMOOTHING:
        steps = new String[]{"elv.task.executables.MorbidityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing"};
        parameters = new Object[8];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMorbidityRoot();
        parameters[6] = Diagnosis.getAddmissionRoot();
        parameters[7] = Diagnosis.getMorfologyRoot();
        break;
      case MORTALITY_REGION_ANALYSIS:
        steps = new String[]{"elv.task.executables.MortalityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing",
          "elv.task.executables.RegionSpotting",
          "elv.task.executables.SpotStandardization",
          "elv.task.executables.RegionDocumenting"};
        parameters = new Object[6];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMortalityRoot();
        break;
      case MORBIDITY_REGION_ANALYSIS:
        steps = new String[]{"elv.task.executables.MorbidityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing",
          "elv.task.executables.RegionSpotting",
          "elv.task.executables.SpotStandardization",
          "elv.task.executables.RegionDocumenting"};
        parameters = new Object[8];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMorbidityRoot();
        parameters[6] = Diagnosis.getAddmissionRoot();
        parameters[7] = Diagnosis.getMorfologyRoot();
        break;
      case MORTALITY_CLUSTER_ANALYSIS:
        steps = new String[]{"elv.task.executables.MortalityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing",
          "elv.task.executables.ClusterSpotting",
          "elv.task.executables.SpotStandardization",
          "elv.task.executables.ClusterDocumenting"};
        parameters = new Object[6];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMortalityRoot();
        break;
      case MORBIDITY_CLUSTER_ANALYSIS:
        steps = new String[]{"elv.task.executables.MorbidityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing",
          "elv.task.executables.ClusterSpotting",
          "elv.task.executables.SpotStandardization",
          "elv.task.executables.ClusterDocumenting"};
        parameters = new Object[8];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMorbidityRoot();
        parameters[6] = Diagnosis.getAddmissionRoot();
        parameters[7] = Diagnosis.getMorfologyRoot();
        break;
      case MORTALITY_POINT_SOURCE_ANALYSIS:
        steps = new String[]{"elv.task.executables.MortalityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing",
          "elv.task.executables.PointSourceSpotting",
          "elv.task.executables.SpotStandardization",
          "elv.task.executables.PointSourceDocumenting"};
        parameters = new Object[6];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMortalityRoot();
        break;
      case MORBIDITY_POINT_SOURCE_ANALYSIS:
        steps = new String[]{"elv.task.executables.MorbidityStandardPreparation",
          "elv.task.executables.Standardization",
          "elv.task.executables.Smoothing",
          "elv.task.executables.PointSourceSpotting",
          "elv.task.executables.SpotStandardization",
          "elv.task.executables.PointSourceDocumenting"};
        parameters = new Object[8];
        parameters[0] = IntervalList.getYearIntervalList();
        parameters[1] = IntervalList.getAgeIntervalList();
        parameters[2] = Territory.getBaseRoot();
        parameters[3] = Territory.getBenchmarkRoot();
        parameters[4] = Diagnosis.getDiseaseRoot();
        parameters[5] = Diagnosis.getMorbidityRoot();
        parameters[6] = Diagnosis.getAddmissionRoot();
        parameters[7] = Diagnosis.getMorfologyRoot();
        break;
      case TWO_FACTORS_ANALYSIS:
        steps = new String[]{"TwoFactorsSpotting()",
          "TwoFactorsDocumenting"};
        break;
    }
  }
}
