/*
 * Task.java
 */
package elv.task;

/**
 * Class for reprezenting a task.
 * @author Qpa
 */
public class Task extends elv.util.Propertable implements java.io.Serializable, elv.util.Visualizable
{
  
  // Constants.
  /** The properties folder name. */
  public final static java.lang.String PROPERTY_FOLDER = "properties";
  /** The execution folder name. */
  public final static java.lang.String EXECUTION_FOLDER = "execution";
  /** The property file name. */
  private final static java.lang.String PROPERTY_FILE_NAME = "task.prop";
  
  // Property names.
  /** The "root" property name.*/
  public final static java.lang.String ROOT_NAME = "Task";
  /** The "state" property name.*/
  public final static java.lang.String STATE_NAME = ROOT_NAME + ".State";
  /** The "type" property name.*/
  public final static java.lang.String TYPE_NAME = ROOT_NAME + ".Type";
  /** The "description" property name.*/
  public final static java.lang.String DESCRIPTION_NAME = ROOT_NAME + ".Description";
  /** The "scheduled" property name.*/
  public final static java.lang.String SCHEDULED_NAME = ROOT_NAME + ".Scheduled";
  /** The "created" property name.*/
  public final static java.lang.String CREATED_NAME = ROOT_NAME + ".Created";
  /** The "modified" property name.*/
  public final static java.lang.String MODIFIED_NAME = ROOT_NAME + ".Modified";
  /** The "started" property name.*/
  public final static java.lang.String STARTED_NAME = ROOT_NAME + ".Started";
  /** The "stopped" property name.*/
  public final static java.lang.String STOPPED_NAME = ROOT_NAME + ".Stopped";
  
  // Property values.
  /** The array of possible analysis types. */
  public final static java.lang.String[] ANALYSIS_TYPES = {"Analysis.Selection.Mortality", "Analysis.Selection.Morbidity",
   "Analysis.Preparation.Population", "Analysis.Preparation.Mortality", "Analysis.Preparation.Morbidity",
   "Analysis.Standardization.Mortality", "Analysis.Standardization.Morbidity",
   "Analysis.Smoothing.Mortality", "Analysis.Smoothing.Morbidity",
   "Analysis.Region.Mortality", "Analysis.Region.Morbidity",
   "Analysis.Cluster.Mortality", "Analysis.Cluster.Morbidity",
   "Analysis.PointSource.Mortality", "Analysis.PointSource.Morbidity",
   "Analysis.TwoFactors"};
    // The indices in the above array.
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_SELECTION = 0;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_SELECTION = 1;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int POPULATION_PREPARATION = 2;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_PREPARATION = 3;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_PREPARATION = 4;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_STANDARDIZATION = 5;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_STANDARDIZATION = 6;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_SMOOTHING = 7;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_SMOOTHING = 8;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_REGION_ANALYSIS = 9;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_REGION_ANALYSIS = 10;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_CLUSTER_ANALYSIS = 11;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_CLUSTER_ANALYSIS = 12;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORTALITY_POINT_SOURCE_ANALYSIS = 13;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int MORBIDITY_POINT_SOURCE_ANALYSIS = 14;
    /** Index in the <code>ANALYSIS_TYPES</code> array. */
    public final static int TWO_FACTORS_ANALYSIS = 15;
  
  // Variables.
  /** The name of the task. */
  private java.lang.String name = "";
  /** The container of the task. */
  private Container container;
  /** The analysis type of the task. */
  private int type = -1;
  /** The executables of the task. */
  private java.util.Vector<elv.task.executables.Executable> executables;
  /** The parameter types of the task. */
  private java.util.Vector<elv.util.parameters.Parameter> parameterTypes;
  
  
  /**
   * Contructor for a new task.
   * @param container the container of this task.
   * @param name the name of this task.
   * @param type the analysis type of this task.
   * @throws java.lang.Exception if there is any problem with the creation.
   */
  public Task(Container container, java.lang.String name, int type) throws java.lang.Exception
  {
    if(container == null || name == null)
    {
      throw new java.lang.NullPointerException();
    }
    else if(name.equals(""))
    {
      throw new java.lang.IllegalArgumentException("\"" + name + "\"");
    }
    this.container = container;
    this.name = name;
    this.type = type;
    if(type < 0 || type >= ANALYSIS_TYPES.length)
    {
      this.type = 0;
    }
    setAnalysis();
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }

  /**
   * Parser for an existing task.
   * @param container the container of this task.
   * @param name the name of this task.
   * @throws java.lang.Exception if there is any error with parsing.
   */
  public static Task parse(Container container, java.lang.String name) throws java.lang.Exception
  {
    // Create task with dummy analysis type.
    Task task = new Task(container, name, 0);
    task.loadProperties();
    // Set the proper analysis type.
    java.lang.String analysisName = (java.lang.String)elv.util.Property.get(TYPE_NAME, task.properties).getValue();
    for(int i = 0; i < ANALYSIS_TYPES.length; i++)
    {
      if(ANALYSIS_TYPES[i].equals(analysisName))
      {
        task.type = i;
        break;
      }
    }
    task.setAnalysis();
    return task;
  }
  
  /**
   * Parser for an existing task.
   * @param relativePath the relative path of the task (begins with user name) separated with "/".
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public static Task parse(java.lang.String relativePath) throws java.lang.Exception
  {
    return parse(new Container(elv.util.User.parse(
      relativePath.split(java.util.regex.Pattern.quote(elv.util.Util.ZIP_SEPARATOR))[0]),
      relativePath.split(java.util.regex.Pattern.quote(elv.util.Util.ZIP_SEPARATOR))[1]),
      relativePath.split(java.util.regex.Pattern.quote(elv.util.Util.ZIP_SEPARATOR))[2]);
  }
  
  /**
   * Method for setting the executables and parameters of this task.
   * @throws java.lang.Exception if there is any error with creating.
   */
  private void setAnalysis() throws java.lang.Exception
  {
    executables = new java.util.Vector<elv.task.executables.Executable>();
    parameterTypes = new java.util.Vector<elv.util.parameters.Parameter>();
    switch(type)
    {
      case MORTALITY_SELECTION:
        executables.add(new elv.task.executables.MortalitySelection());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        break;
      case MORBIDITY_SELECTION:
        executables.add(new elv.task.executables.MorbiditySelection());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        break;
      case POPULATION_PREPARATION:
        executables.add(new elv.task.executables.PopulationPreparation());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        break;
      case MORTALITY_PREPARATION:
        executables.add(new elv.task.executables.MortalityPreparation());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORBIDITY_PREPARATION:
        executables.add(new elv.task.executables.MorbidityPreparation());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MorbidityDiagnosis());
        parameterTypes.add(new elv.util.parameters.AddmissionDiagnosis());
        parameterTypes.add(new elv.util.parameters.MorfologyDiagnosis());
        break;
      case MORTALITY_STANDARDIZATION:
        executables.add(new elv.task.executables.MortalityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORBIDITY_STANDARDIZATION:
        executables.add(new elv.task.executables.MorbidityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MorbidityDiagnosis());
        parameterTypes.add(new elv.util.parameters.AddmissionDiagnosis());
        parameterTypes.add(new elv.util.parameters.MorfologyDiagnosis());
        break;
        
      case MORTALITY_SMOOTHING:
        executables.add(new elv.task.executables.MortalityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORBIDITY_SMOOTHING:
        executables.add(new elv.task.executables.MorbidityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MorbidityDiagnosis());
        parameterTypes.add(new elv.util.parameters.AddmissionDiagnosis());
        parameterTypes.add(new elv.util.parameters.MorfologyDiagnosis());
        break;
      case MORTALITY_REGION_ANALYSIS:
        executables.add(new elv.task.executables.MortalityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        executables.add(new elv.task.executables.RegionSpotting());
        executables.add(new elv.task.executables.SpotStandardization());
        executables.add(new elv.task.executables.RegionDocumenting());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORBIDITY_REGION_ANALYSIS:
        executables.add(new elv.task.executables.MorbidityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        executables.add(new elv.task.executables.RegionSpotting());
        executables.add(new elv.task.executables.SpotStandardization());
        executables.add(new elv.task.executables.RegionDocumenting());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORTALITY_CLUSTER_ANALYSIS:
        executables.add(new elv.task.executables.MortalityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        executables.add(new elv.task.executables.ClusterSpotting());
        executables.add(new elv.task.executables.SpotStandardization());
        executables.add(new elv.task.executables.ClusterDocumenting());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORBIDITY_CLUSTER_ANALYSIS:
        executables.add(new elv.task.executables.MorbidityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        executables.add(new elv.task.executables.ClusterSpotting());
        executables.add(new elv.task.executables.SpotStandardization());
        executables.add(new elv.task.executables.ClusterDocumenting());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORTALITY_POINT_SOURCE_ANALYSIS:
        executables.add(new elv.task.executables.MortalityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        executables.add(new elv.task.executables.PointSourceSpotting());
        executables.add(new elv.task.executables.SpotStandardization());
        executables.add(new elv.task.executables.PointSourceDocumenting());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case MORBIDITY_POINT_SOURCE_ANALYSIS:
        executables.add(new elv.task.executables.MorbidityStandardPreparation());
        executables.add(new elv.task.executables.Standardization());
        executables.add(new elv.task.executables.Smoothing());
        executables.add(new elv.task.executables.PointSourceSpotting());
        executables.add(new elv.task.executables.SpotStandardization());
        executables.add(new elv.task.executables.PointSourceDocumenting());
        parameterTypes.add(new elv.util.parameters.YearInterval());
        parameterTypes.add(new elv.util.parameters.AgeInterval());
        parameterTypes.add(new elv.util.parameters.BaseSettlement());
        parameterTypes.add(new elv.util.parameters.BenchmarkSettlement());
        parameterTypes.add(new elv.util.parameters.DiseaseDiagnosis());
        parameterTypes.add(new elv.util.parameters.MortalityDiagnosis());
        break;
      case TWO_FACTORS_ANALYSIS:
        executables.add(new elv.task.executables.TwoFactorsSpotting());
        executables.add(new elv.task.executables.TwoFactorsDocumenting());
        break;
    }
  }
  
  /**
   * Method for getting the name of this task.
   * @return the name of this task.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method to setting the name of this task.
   * @param name the new name.
   */
  public void setName(java.lang.String name)
  {
    this.name = name;
  }
  
  /**
   * Method for getting the container of this task.
   * @retun the container of this task.
   */
  public Container getContainer()
  {
    return container;
  }
  
  /**
   * Method for getting the analysis type of task.
   * @retun the type of this task.
   */
  public int getType()
  {
    return type;
  }
  
  /**
   * Method for getting the executables of this task.
   * @return a vector of executables.
   */
  public java.util.Vector<elv.task.executables.Executable> getExecutables()
  {
    return executables;
  }
  
  /**
   * Method for getting the parameter types of this task.
   * @return a vector of null parameters.
   */
  public java.util.Vector<elv.util.parameters.Parameter> getParameterTypes()
  {
    return parameterTypes;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the root name of properties.
   */
  public java.lang.String getRootName()
  {
    return ROOT_NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the propety file name.
   */
  public java.lang.String getPropertyFile()
  {
    return PROPERTY_FILE_NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any problem with setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, true, TYPE_NAME, ANALYSIS_TYPES[type]));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.TEXT_FIELD, false, DESCRIPTION_NAME, ""));
    properties.add(new elv.util.Property(elv.util.Property.STATE, elv.util.Property.LABEL_FIELD, true, STATE_NAME, new elv.util.State(elv.util.State.UNDEFINED)));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, CREATED_NAME, null));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, MODIFIED_NAME, null));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, SCHEDULED_NAME, null));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, STARTED_NAME, null));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, STOPPED_NAME, null));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(STATE_NAME, properties).setDefaultValues(elv.util.State.getAllStates());
  }
  
  /**
   * Method for load the properties.
   * @throws java.lang.Exception if there is any error with the loading.
   */
  public void loadProperties() throws java.lang.Exception
  {
    if(container instanceof Archive)
    {
      properties = elv.util.Property.load(container.getFolderPath(), name + elv.util.Util.ZIP_SEPARATOR + PROPERTY_FOLDER + elv.util.Util.ZIP_SEPARATOR + PROPERTY_FILE_NAME);
    }
    else
    {
      try
      {
        properties = elv.util.Property.load(getPropertyFolderPath(), PROPERTY_FILE_NAME);
      }
      catch(java.io.FileNotFoundException exc)
      {
        elv.util.Property.get(STATE_NAME, properties).setValue(new elv.util.State(elv.util.State.UNDEFINED));
        elv.util.Property.get(MODIFIED_NAME, properties).setValue(new java.util.Date());
        storeProperties();
        throw exc;
      }
    }
  }
  
  /**
   * Method for store the properties.
   * @throws java.lang.Exception if there is any error with the storing.
   */
  public void storeProperties() throws java.lang.Exception
  {
    elv.util.Property.store(getPropertyFolderPath() + elv.util.Util.getFS() + PROPERTY_FILE_NAME, properties);
  }
  
  /**
   * Method for getting the folder of this task.
   * @return the task folder path.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public java.lang.String getFolderPath() throws java.lang.Exception
  {
    return container.getFolderPath() + elv.util.Util.getFS() + name;
  }
  
  /**
   * Method for getting the property folder.
   * @return the propety folder path.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public java.lang.String getPropertyFolderPath() throws java.lang.Exception
  {
    return getFolderPath() + elv.util.Util.getFS() + PROPERTY_FOLDER;
  }
  
  /**
   * Method for getting the execution folder.
   * @return the execution folder path.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public java.lang.String getExecutionFolderPath() throws java.lang.Exception
  {
    return getFolderPath() + elv.util.Util.getFS() + EXECUTION_FOLDER;
  }
  
  /**
   * Method for creating the task folder structure.
   * @throws java.lang.Exception if there is any error with the creation.
   */
  public synchronized void create() throws java.lang.Exception
  {
    // Create task folder.
    boolean done = new java.io.File(getFolderPath()).mkdirs();
    if(!done)
    {
      throw new java.io.InvalidObjectException(getFolderPath());
    }
    // Create task properties folder.
    done = new java.io.File(getPropertyFolderPath()).mkdir();
    if(!done)
    {
      throw new java.io.InvalidObjectException(getPropertyFolderPath());
    }
    // Create task execution folder.
    done = new java.io.File(getExecutionFolderPath()).mkdir();
    if(!done)
    {
      throw new java.io.InvalidObjectException(getExecutionFolderPath());
    }
    elv.util.Property.get(STATE_NAME, properties).setValue(new elv.util.State(elv.util.State.DEFINED));
    elv.util.Property.get(CREATED_NAME, properties).setValue(new java.util.Date());
    storeProperties();
  }
  
  /**
   * Method for moving the task and its folder structure.
   * @param newTask the new task.
   * @throws java.lang.Exception if there is any problem with the moving.
   */
  public synchronized void move(elv.task.Task newTask) throws java.lang.Exception
  {
    if(newTask.getContainer() instanceof Archive)
    {
      if(container instanceof Archive)
      {
        ((Archive)container).get(name, (Archive)newTask.getContainer());
      }
      else
      {
        ((Archive)newTask.getContainer()).add(getFolderPath());
      }
      delete();
    }
    else
    {
      if(container instanceof Archive)
      {
        ((Archive)container).get(name, newTask.getContainer().getFolderPath());
        delete();
      }
      else
      {
        java.lang.String newName = elv.util.Util.getNumbered(newTask.getName(), elv.util.Util.vectorize(new java.io.File(newTask.getContainer().getFolderPath()).list()), null);
        java.lang.String newFolderPath = newTask.getContainer().getFolderPath() + elv.util.Util.getFS() + newName;
        boolean done = new java.io.File(getFolderPath()).renameTo(new java.io.File(newFolderPath));
        if(!done)
        {
          throw new java.io.InvalidObjectException(getFolderPath() + " -> " + newFolderPath);
        }
      }
    }
    name = newTask.getName();
  }
  
  /**
   * Method for deleting the task folder structure.
   * @throws java.lang.Exception if there is any error with the deleting.
   */
  public synchronized void delete() throws java.lang.Exception
  {
    if(container instanceof Archive)
    {
      ((Archive)container).remove(name);
    }
    else
    {
      elv.util.Util.delete(getFolderPath());
    }
  }
  
  /**
   * Method for copying the task folder structure to a new task.
   * @param newTask the new task.
   * @throws java.lang.Exception if there is any error with the copy.
   */
  public synchronized void copy(Task newTask) throws java.lang.Exception
  {
    if(newTask.getContainer() instanceof Archive)
    {
      if(container instanceof Archive)
      {
        ((Archive)container).get(name, (Archive)newTask.getContainer());
      }
      else
      {
        ((Archive)newTask.getContainer()).add(getFolderPath());
      }
    }
    else
    {
      if(container instanceof Archive)
      {
        ((Archive)container).get(name, newTask.getContainer().getFolderPath());
      }
      else
      {
        java.lang.String newName = elv.util.Util.getNumbered(newTask.getName(), elv.util.Util.vectorize(new java.io.File(newTask.getContainer().getFolderPath()).list()), null);
        java.lang.String newFolderPath = newTask.getContainer().getFolderPath() + elv.util.Util.getFS() + newName;
        elv.util.Util.copy(getFolderPath(), newFolderPath);
      }
    }
  }
  
  /**
   * Method for cleaning the execution folder.
   * @throws java.lang.Exception if there is any error with the cleaning.
   */
  public synchronized void clean() throws java.lang.Exception
  {
    java.lang.String path = getExecutionFolderPath();
    java.io.File file = new java.io.File(path);
    java.lang.String[] paths = file.list();
    for(int i = 0; i < paths.length; i++)
    {
      elv.util.Util.delete(path + elv.util.Util.getFS() + paths[i]);
    }
  }
  
  /**
   * Method for getting the icon of the task.
   * @return the icon of task.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/task.gif"));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param task An <CODE>elv.task.Task</CODE> object.
   * @return true, if this task equals with the given task.
   */
  public boolean equals(java.lang.Object task)
  {
    boolean isEqual = false;
    if(task != null && task instanceof Task)
    {
      try
      {
        isEqual = getFolderPath().equals(((Task)task).getFolderPath());
      }
      catch(java.lang.Exception exc)
      {
        isEqual = false;
      }
    }
    return isEqual;
  }
  
  /**
   * Method for getting the title.
   * @return the title of this task.
   */
  public java.lang.String getTitle()
  {
    return container.getTitle() + elv.util.Util.TITLE_SEPARATOR + name;
  }
  
  /**
   * Method for getting the uninternationalized line reprezentation.
   * @return the line reprezentation of this task.
   */
  public java.lang.String toLine()
  {
    return container.getUser() + elv.util.Util.ZIP_SEPARATOR + container + elv.util.Util.ZIP_SEPARATOR + name;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this task.
   */
  public java.lang.String toString()
  {
    return name;
  }

  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return a clone of this task.
   */
  public java.lang.Object clone()
  {
    Task cloneTask = null;
    try
    {
      cloneTask = new Task(container, name, type);
//      cloneTask.setProperties(elv.util.Property.clone(properties));
    }
    catch (java.lang.Exception exc)
    {
      exc.printStackTrace();
    }
    return cloneTask;
  }
  
}