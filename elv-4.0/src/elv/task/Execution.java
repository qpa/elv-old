/*
 * Execution.java
 */

package elv.task;

/**
 * Class for execute a task.
 * @author Elv
 */
public class Execution extends java.util.TimerTask
{
  
  //Constant.
  /** The dafult file name of the execution. */
  public final static java.lang.String FILE_NAME = "execution.txt";
  
  /**
   * Variables.
   */
  private elv.task.Task task;
  private elv.task.executables.Executable executable;
  private java.util.Stack<elv.util.Progress> progresses;
  private java.util.Timer timer;
  private boolean isExecuted = false;
  
  private java.util.Vector<elv.util.parameters.AgeInterval> ageIntervals;
  private java.util.Vector<elv.util.parameters.YearInterval> yearIntervals;
  private java.util.Vector<elv.util.parameters.BaseSettlement> baseSettlements;
  private java.util.Vector<elv.util.parameters.BenchmarkSettlement> benchmarkSettlements;
  private java.util.Vector<elv.util.parameters.Settlement> allSettlements;
  private java.util.Vector<elv.util.parameters.District> districts;
  private java.util.Vector<elv.util.parameters.DiseaseDiagnosis> diseases;
  private java.util.Vector<elv.util.parameters.MortalityDiagnosis> mortalities;
  private java.util.Vector<elv.util.parameters.MorbidityDiagnosis> morbidities;
  private java.util.Vector<elv.util.parameters.AddmissionDiagnosis> addmissions;
  private java.util.Vector<elv.util.parameters.MorfologyDiagnosis> morfologies;
  private java.util.Vector<elv.util.parameters.DismissalDiagnosis> dismissals;
  private java.util.Vector<java.lang.Integer> years;
  private int benchmarkYear;
  private java.util.Vector<elv.util.parameters.Spot> spots;
  
  /**
   * Constructor.
   * @param task the task which is executed.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public Execution(elv.task.Task task) throws java.lang.Exception
  {
    this.task = task;
    progresses = new java.util.Stack<elv.util.Progress>();
    timer = new java.util.Timer();
    // Create the execution state indicator file.
    java.lang.String fileName = task.getExecutionFolderPath() + "/" + elv.task.Execution.FILE_NAME;
    java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(fileName), elv.util.Util.FILE_ENCODING));
    fileWriter.close();
    
    loadParameters();
  }
  
  /**
   * Method for getting the execution task.
   * @return the task of execution.
   */
  public elv.task.Task getTask()
  {
    return task;
  }
  
  /**
   * Method for getting the current executable.
   * @return the current executable.
   */
  public elv.task.executables.Executable getExecutable()
  {
    return executable;
  }
  
  /**
   * Method for setting the current executable.
   * @param executable the current executable.
   */
  public void setExecutable(elv.task.executables.Executable executable)
  {
    this.executable = executable;
  }
  
  /**
   * Method for getting the execution progresses.
   * @return a stack of progresses.
   */
  public java.util.Stack<elv.util.Progress> getProgresses()
  {
    return progresses;
  }
  
  /**
   * Method for getting the age intervals.
   * @return a vector of age intervals.
   */
  public java.util.Vector<elv.util.parameters.AgeInterval> getAgeIntervals()
  {
    return ageIntervals;
  }
  
  /**
   * Method for getting the year intervals.
   * @return a vector of year intervals.
   */
  public java.util.Vector<elv.util.parameters.YearInterval> getYearIntervals()
  {
    return yearIntervals;
  }
  
  /**
   * Method for getting the years.
   * @return a vector of years.
   */
  public java.util.Vector<java.lang.Integer> getYears()
  {
    return years;
  }
  
  /**
   * Method for getting the base settlements.
   * @return a vector of settlements.
   */
  public java.util.Vector<elv.util.parameters.BaseSettlement> getBaseSettlements()
  {
    return baseSettlements;
  }
  
  /**
   * Method for getting the benchmark settlements.
   * @return a vector of settlements.
   */
  public java.util.Vector<elv.util.parameters.BenchmarkSettlement> getBenchmarkSettlements()
  {
    return benchmarkSettlements;
  }
  
  /**
   * Method for getting all settlements.
   * @return a vector of settlements.
   */
  public java.util.Vector<elv.util.parameters.Settlement> getAllSettlements()
  {
    return allSettlements;
  }
  
  /**
   * Method for getting the districts.
   * @return a vector of districts.
   */
  public java.util.Vector<elv.util.parameters.District> getDistricts()
  {
    return districts;
  }
  
  /**
   * Method for getting the diseases.
   * @return a vector of diseases.
   */
  public java.util.Vector<elv.util.parameters.DiseaseDiagnosis> getDiseases()
  {
    return diseases;
  }
  
  /**
   * Method for getting the mortalities.
   * @return a vector of mortalities.
   */
  public java.util.Vector<elv.util.parameters.MortalityDiagnosis> getMortalities()
  {
    return mortalities;
  }
  
  /**
   * Method for getting the morbidities.
   * @return a vector of morbidities.
   */
  public java.util.Vector<elv.util.parameters.MorbidityDiagnosis> getMorbidities()
  {
    return morbidities;
  }
  
  /**
   * Method for getting the addmissions.
   * @return a vector of addmissions.
   */
  public java.util.Vector<elv.util.parameters.AddmissionDiagnosis> getAddmissions()
  {
    return addmissions;
  }
  
  /**
   * Method for getting the morfologies.
   * @return a vector of morfologies.
   */
  public java.util.Vector<elv.util.parameters.MorfologyDiagnosis> getMorfologies()
  {
    return morfologies;
  }
  
  /**
   * Method for getting the dismissals.
   * @return a vector of dismissals.
   */
  public java.util.Vector<elv.util.parameters.DismissalDiagnosis> getDismissals()
  {
    return dismissals;
  }
  
  /**
   * Method for getting the benchmark year.
   * @return the benchmark year.
   */
  public int getBenchmarkYear()
  {
    return benchmarkYear;
  }
  
  /**
   * Method for getting the spots.
   * @return a vector of spots.
   */
  public java.util.Vector<elv.util.parameters.Spot> getSpots()
  {
    return spots;
  }
  
  /**
   * Method for setting the spots.
   * @param spots the new spots.
   */
  public void setSpots(java.util.Vector<elv.util.parameters.Spot> spots)
  {
    this.spots = spots;
  }
  
  /**
   * Method for checking the execution state.
   * @return the state of execution.
   */
  public boolean isExecuted()
  {
    return isExecuted;
  }
  
  /**
   * Method for starting a task execution.
   * @throws java.lang.Exception if there is any problem with starting.
   */
  public void start() throws java.lang.Exception
  {
    isExecuted = true;
    timer.schedule(this, new java.util.Date());
  }
  
  /**
   * Method for stopping a task execution.
   */
  public void stop() throws java.lang.Exception
  {
    isExecuted = false;
    timer.cancel();
  }
  
  /**
   * Method for cleaning the execution.
   * @param task the task of execution.
   * @throws java.lang.Exception if there is any problem with cleaning.
   */
  public static void clean(elv.task.Task task) throws java.lang.Exception
  {
    // Clean execution. 
    task.clean();
    // Set and store properties.
    elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.DEFINED));
    elv.util.Property.get(elv.task.Task.MODIFIED_NAME, task.getProperties()).setValue(new java.util.Date());
    elv.util.Property.get(elv.task.Task.SCHEDULED_NAME, task.getProperties()).setValue(null);
    elv.util.Property.get(elv.task.Task.STARTED_NAME, task.getProperties()).setValue(null);
    elv.util.Property.get(elv.task.Task.STOPPED_NAME, task.getProperties()).setValue(null);
    task.storeProperties();
  }
  
  /**
   * Implemented method from interface <CODE>java.util.TimerTask</CODE>.
   */
  public void run()
  {
    try
    {
      // Set and store properties.
      elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.EXECUTED));
      elv.util.Property.get(elv.task.Task.STARTED_NAME, task.getProperties()).setValue(new java.util.Date());
      elv.util.Property.get(elv.task.Task.STOPPED_NAME, task.getProperties()).setValue(null);
      task.storeProperties();
      
      // Execute.
      for(elv.task.executables.Executable iteratorExecutable : task.getExecutables())
      {
        iteratorExecutable.execute(this);
        if(!isExecuted)
        {
          break;
        }
      }
      timer.cancel();
      
      // Set and store properties.
      if(isExecuted())
      {
        elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.DONE));
      }
      else
      {
        elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.STOPPED));
      }
      elv.util.Property.get(elv.task.Task.STOPPED_NAME, task.getProperties()).setValue(new java.util.Date());
      task.storeProperties();
      
      
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.setExecutionError(task, exc);
      try
      {
        elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.ERROR));
        elv.util.Property.get(elv.task.Task.STOPPED_NAME, task.getProperties()).setValue(new java.util.Date());
        task.storeProperties();
      }
      catch(java.lang.Exception ex)
      {
        elv.util.Error.logServerError(ex);
      }
    }
    isExecuted = false;
  }
  
  /**
   * Method for loading all parameters of this execution.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  private void loadParameters() throws java.lang.Exception
  {
    // Initialize parameters.
    ageIntervals = new java.util.Vector<elv.util.parameters.AgeInterval>();
    yearIntervals = new java.util.Vector<elv.util.parameters.YearInterval>();
    baseSettlements = new java.util.Vector<elv.util.parameters.BaseSettlement>();
    benchmarkSettlements = new java.util.Vector<elv.util.parameters.BenchmarkSettlement>();
    districts = new java.util.Vector<elv.util.parameters.District>();
    diseases = new java.util.Vector<elv.util.parameters.DiseaseDiagnosis>();
    mortalities = new java.util.Vector<elv.util.parameters.MortalityDiagnosis>();
    morbidities = new java.util.Vector<elv.util.parameters.MorbidityDiagnosis>();
    addmissions = new java.util.Vector<elv.util.parameters.AddmissionDiagnosis>();
    morfologies = new java.util.Vector<elv.util.parameters.MorfologyDiagnosis>();
    dismissals = new java.util.Vector<elv.util.parameters.DismissalDiagnosis>();
    spots = new java.util.Vector<elv.util.parameters.Spot>();
    
    // Load parameters.
    for(elv.util.parameters.Parameter parameterType : task.getParameterTypes())
    {
      java.lang.String containerPath = task.getPropertyFolderPath();
      java.lang.String name = parameterType.getFile();
      if(parameterType instanceof elv.util.parameters.YearInterval)
      {
        yearIntervals = elv.util.parameters.Parameter.load(containerPath, name, (elv.util.parameters.YearInterval)parameterType);
        if(yearIntervals.isEmpty())
        {
          elv.util.parameters.YearInterval yearInterval = parameterType.getDefault();
          yearIntervals.add(yearInterval);
        }
      }
      else if(parameterType instanceof elv.util.parameters.AgeInterval)
      {
        ageIntervals = elv.util.parameters.Parameter.load(containerPath, name, (elv.util.parameters.AgeInterval)parameterType);
      }
      else if(parameterType instanceof elv.util.parameters.BaseSettlement)
      {
        baseSettlements = elv.util.parameters.Parameter.load(containerPath, name, (elv.util.parameters.BaseSettlement)parameterType);
        name = new elv.util.parameters.District().getFile();
        districts = elv.util.parameters.District.load(containerPath, name, new elv.util.parameters.District());
      }
      else if(parameterType instanceof elv.util.parameters.BenchmarkSettlement)
      {
        benchmarkSettlements = elv.util.parameters.Parameter.load(containerPath, name, (elv.util.parameters.BenchmarkSettlement)parameterType);
      }
      else if(parameterType instanceof elv.util.parameters.DiseaseDiagnosis)
      {
        diseases = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.DiseaseDiagnosis)parameterType);
      }
      else if(parameterType instanceof elv.util.parameters.MortalityDiagnosis)
      {
        mortalities = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.MortalityDiagnosis)parameterType);
        if(mortalities.isEmpty())
        {
          mortalities = ((elv.util.parameters.MortalityDiagnosis)parameterType).getAllDiagnosises();
        }
      }
      else if(parameterType instanceof elv.util.parameters.MorbidityDiagnosis)
      {
        morbidities = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.MorbidityDiagnosis)parameterType);
        if(morbidities.isEmpty())
        {
          morbidities = ((elv.util.parameters.MorbidityDiagnosis)parameterType).getAllDiagnosises();
        }
      }
      else if(parameterType instanceof elv.util.parameters.AddmissionDiagnosis)
      {
        addmissions = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.AddmissionDiagnosis)parameterType);
      }
      else if(parameterType instanceof elv.util.parameters.AddmissionDiagnosis)
      {
        addmissions = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.AddmissionDiagnosis)parameterType);
      }
      else if(parameterType instanceof elv.util.parameters.MorfologyDiagnosis)
      {
        morfologies = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.MorfologyDiagnosis)parameterType);
      }
      else if(parameterType instanceof elv.util.parameters.DismissalDiagnosis)
      {
        dismissals = elv.util.parameters.AgeInterval.load(containerPath, name, (elv.util.parameters.DismissalDiagnosis)parameterType);
      }
    }
    // . 
    elv.task.executables.StandardPreparation standardPreparation = null;
    for(elv.task.executables.Executable iteratorExecutable : task.getExecutables())
    {
      iteratorExecutable.setProperties(elv.util.Property.load(task.getPropertyFolderPath(), iteratorExecutable.getPropertyFile()));
      if(iteratorExecutable instanceof elv.task.executables.StandardPreparation)
      {
        standardPreparation = (elv.task.executables.StandardPreparation)iteratorExecutable;
      }
    }
    // Set standardization defaults.
    benchmarkYear = 0;
    if(standardPreparation != null)
    {
      // Get benchmark year.
      benchmarkYear = (java.lang.Integer)elv.util.Property.get(elv.task.executables.Preparation.BENCHMARK_YEAR_NAME, standardPreparation.getProperties()).getValue();
      // Set all base settlements.
      if(baseSettlements.isEmpty())
      {
        baseSettlements = new elv.util.parameters.BaseSettlement().getAllSettlements();
      }
      // Set default benchmark settlement (whole country).
      if(benchmarkSettlements.isEmpty())
      {
        elv.util.parameters.BenchmarkSettlement benchmarkSettlement = new elv.util.parameters.BenchmarkSettlement().getDefault();
        benchmarkSettlements.add(benchmarkSettlement);
      }
    }
    
    // Grab all settlements.
    allSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
    for(elv.util.parameters.Settlement iteratorSettlement : baseSettlements)
    {
      allSettlements.add(iteratorSettlement);
    }
    for(elv.util.parameters.Settlement iteratorSettlement : benchmarkSettlements)
    {
      allSettlements.add(iteratorSettlement);
    }
    
    // Grab all years.
    years = new java.util.Vector<java.lang.Integer>();
    boolean yearFound = false;
    boolean benchmarkYearFound = false;
    for(elv.util.parameters.Interval iteratorYearInterval : yearIntervals)
    {
      for(int i = iteratorYearInterval.getFromValue(); i <= iteratorYearInterval.getToValue(); i++)
      {
        int year = i;
        if(year == benchmarkYear)
        {
          benchmarkYearFound = true;
        }
        for(int iteratorYear : years)
        {
          if(year == iteratorYear)
          {
            yearFound = true;
            break;
          }
        }
        if(!yearFound)
        {
          years.add(year);
        }
      }
    }
    if(benchmarkYear != 0 && !benchmarkYearFound)
    {
      years.add(benchmarkYear);
    }
  }
  
  /**
   * Method for loading the progresses, if the execution is stopped.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public void loadProgresses() throws java.lang.Exception
  {
    for(elv.task.executables.Executable iteratorExecutable : task.getExecutables())
    {
      boolean isFinished = iteratorExecutable.execute(this);
      if(!isFinished || progresses.size() > 0)
      {
        break;
      }
    }
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this execution.
   */
  public java.lang.String toString()
  {
    return task.getTitle();
  }
  
}
