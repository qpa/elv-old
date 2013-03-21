/*
 * Smoothing.java
 */

package elv.task.executables;

/**
 * Class for implementing the smoothing methods.
 * @author Elv
 */
public class Smoothing extends Executable
{
  
  // Constants.
  /** The property file name. */
  private final static java.lang.String PROPERTY_FILE = "smoothing.prop";
  /** The execution files. */
  private final static ExecutionFile[] EXECUTION_FILES = {new ExecutionFile("smoothing.csv", "Smoothing")};
  // Indices in the above array.
  /** Index in the <code>EXECUTION_FILES</code> array. */
  public final int SMOOTHING = 0;
  
  // Property names.
  /** The "name" property name.*/
  public final static java.lang.String NAME = "Smoothing";
  /** The "iterations" property name.*/
  public final static java.lang.String ITERATIONS_NAME = NAME + ".Iterations";
  /** The "category" property name.*/
  public final static java.lang.String CATEGORY_NAME = NAME + ".Category";
  
  // Property values.
  /** The array of possible smoothing types. */
  private final static java.lang.String[] SMOOTHINGS = {"IRP", "EmpiricalBayes"};
    // The indices in the above array.
    /** Index in the <code>SMOOTHINGS</code> array. */
    public final static int IRP = 0;
    /** Index in the <code>SMOOTHINGS</code> array. */
    public final static int EMPIRICAL_BAYES = 1;
  /** Default value for the iterations. */
  public final static int DEFAULT_ITERATIONS = 50;
  /** The array of possible category types. */
  public final static java.lang.String[] CATEGORIES = {"Category.Five", "Category.MinMax", "Category.Any"};
    // The indices in the above array.
    /** Index in the <code>CATEGORIES</code> array. */
    public final static int FIVE = 0;
    /** Index in the <code>CATEGORIES</code> array. */
    public final static int MIN_MAX = 1;
    /** Index in the <code>CATEGORIES</code> array. */
    public final static int ANY = 2;
  
  // Sub-property names.
  /** The "partitions" sub-property name.*/
  public final static java.lang.String PARTITIONS_NAME = SMOOTHINGS[IRP] + ".Partitions";
  /** The "distance weighing constant" sub-property name.*/
  public final static java.lang.String DISTANCE_WEIGHING_CONSTANT_NAME = SMOOTHINGS[IRP] + ".DistanceWeighingConstant";
  /** The "SMR is weighed" sub-property name.*/
  public final static java.lang.String SMR_IS_WEIGHED_NAME = SMOOTHINGS[IRP] + ".SMRIsWeighed";
  /** The "count" sub-property name.*/
  public final static java.lang.String COUNT_NAME = CATEGORIES[MIN_MAX] + ".Count";
  /** The "levels" sub-property name.*/
  public final static java.lang.String LEVELS_NAME = CATEGORIES[ANY] + ".Levels";
  
  // Sub-property values.
  /** The default "partitions" sub-property value.*/
  public final static int DEFAULT_PARTITIONS = 200;
  /** The default "distance weighing constant" sub-property value.*/
  public final static double DEFAULT_DISTANCE_WEIGHING_CONSTANT = 2.0;
  /** The default "SMR is weighed" sub-property value.*/
  public final static boolean DEFAULT_SMR_IS_WEIGHED = true;
  /** The default "count" sub-property value.*/
  public final static int DEFAULT_COUNT = 5;
  /** The default "level" sub-property value.*/
  public final static double DEFAULT_LEVEL = 0.5;
  
  /** File header. */
  public final static java.lang.String HEADER = 
    "District" + VS + "Population" + VS + "Observed cases" + VS + "Expected cases" + VS + "Incidence" + VS +
    "SMR" + VS + "SMR significance" + VS + "SMR category" + VS + "Probability" + VS + "Probability category" + VS +
    "Smooth SMR" + VS + "Smooth SMR category" + VS +
    "Trend" + VS + "Trend significance" + VS + "Trend correlation" + VS + "Trend category";
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public Smoothing() throws java.lang.Exception
  {
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }
  
  /**
   * Method for getting the name of the smoothing.
   * @return the name of this smoothing.
   */
  public java.lang.String getName()
  {
    return NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the root name of properties.
   */
  public java.lang.String getRootName()
  {
    return NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the propety file name.
   */
  public java.lang.String getPropertyFile()
  {
    return PROPERTY_FILE;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, NAME, SMOOTHINGS[IRP]));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, ITERATIONS_NAME, DEFAULT_ITERATIONS));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, PARTITIONS_NAME, DEFAULT_PARTITIONS));
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.SPINNER, false, DISTANCE_WEIGHING_CONSTANT_NAME, DEFAULT_DISTANCE_WEIGHING_CONSTANT));
    properties.add(new elv.util.Property(elv.util.Property.BOOLEAN, elv.util.Property.COMBO_BOX, true, SMR_IS_WEIGHED_NAME, DEFAULT_SMR_IS_WEIGHED));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, CATEGORY_NAME, CATEGORIES[FIVE]));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, COUNT_NAME, DEFAULT_COUNT));
    java.util.Vector<java.lang.Double> levels = new java.util.Vector<java.lang.Double>();
    levels.add(DEFAULT_LEVEL);
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE_ARRAY, elv.util.Property.TABLE_BUTTON, false, LEVELS_NAME, levels));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(NAME, properties).setDefaultValues(elv.util.Util.vectorize(SMOOTHINGS));
    
    java.util.Vector<java.lang.Boolean> booleans = new java.util.Vector<java.lang.Boolean>();
    booleans.add(true);
    booleans.add(false);
    elv.util.Property.get(SMR_IS_WEIGHED_NAME, properties).setDefaultValues(booleans);
    elv.util.Property.get(CATEGORY_NAME, properties).setDefaultValues(elv.util.Util.vectorize(CATEGORIES));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param smoothing an <CODE>elv.task.executables.Smoothing</CODE> object.
   */
  public boolean equals(java.lang.Object smoothing)
  {
    boolean isEqual = false;
    if(smoothing != null && smoothing instanceof Smoothing)
    {
      isEqual = getPropertyFile().equals(((Smoothing)smoothing).getPropertyFile());
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this smoothing.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(getName());
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return an array with the execution files.
   */
  public ExecutionFile[] getExecutionFiles()
  {
    return EXECUTION_FILES;
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return the icon of executable.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/executable.gif"));
  }
  
  /**
   * Implemented method from <CODE>elv.task.executables.Step</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    // Determine, if it is just progresses loading or it is a real execution.
    boolean isProgressesLoading = false;
    elv.util.State state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, execution.getTask().getProperties()).getValue();
    if(!state.equals(new elv.util.State(elv.util.State.EXECUTED)))
    {
      isProgressesLoading = true;
    }
    java.lang.String pathName = execution.getTask().getExecutionFolderPath() + "/" + EXECUTION_FILES[SMOOTHING].getFileName();
    java.io.BufferedReader fileReader = null;
    try
    {
      fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(pathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      fileReader.readLine();
    }
    catch(java.io.FileNotFoundException exc)
    {
      // Return, if it is just progresses loading.
      if(isProgressesLoading)
      {
        return false;
      }
      fileReader = null;
      // Store header line.
      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName), elv.util.Util.FILE_ENCODING));
      fileWriter.println(HEADER);
      fileWriter.close();
    }
    // Initialize.
    java.lang.String line;
    int lineCount = 1; // Due to the header line;
    
    // Parse.
    if(!isProgressesLoading)
    {
      // Smoothe the SMRs.
      java.lang.String smoothingName = (java.lang.String)elv.util.Property.get(NAME, properties).getValue();
      if(smoothingName.equals(SMOOTHINGS[IRP]))
      {
        smootheIRP(execution);
      }
      else if(smoothingName.equals(SMOOTHINGS[EMPIRICAL_BAYES]))
      {
        smootheEmpiricalBayes(execution);
      }
    }
    // Categorize the smoothed SMRs.
    java.lang.String categoryName = (java.lang.String)elv.util.Property.get(CATEGORY_NAME, properties).getValue();
    
    double minSmr = java.lang.Double.MAX_VALUE;
    double maxSmr = java.lang.Double.MIN_VALUE;
    // Get the minimum and maximum value.
    for(elv.util.parameters.District iteratorDistrict : execution.getDistricts())
    {
      if(iteratorDistrict.getResult().smoothSmr < minSmr)
      {
        minSmr = iteratorDistrict.getResult().smoothSmr;
      }
      if(iteratorDistrict.getResult().smoothSmr > maxSmr)
      {
        maxSmr = iteratorDistrict.getResult().smoothSmr;
      }
    }
    int categoryCount = (java.lang.Integer)elv.util.Property.get(COUNT_NAME, properties).getValue();
    java.util.Vector<java.lang.Double> categoryLevels = (java.util.Vector)elv.util.Property.get(LEVELS_NAME, properties).getValue();
    
    int position = 0;
    execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.District.TITLE, 0, execution.getDistricts().size()));
    for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
    {
      elv.util.parameters.District iteratorDistrict = execution.getDistricts().get(districtCount);
      if(fileReader != null && (line = fileReader.readLine()) != null)
      {
        lineCount++;
        java.lang.String expectedLine = java.lang.Integer.toString(iteratorDistrict.getCode());
        java.lang.String[] splitedLine = line.split(java.util.regex.Pattern.quote(VS));
        java.lang.String observedLine = splitedLine[0];
        if(!observedLine.equals(expectedLine))
        {
          throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
        }
        // Splitted parts between 1-9 are the standardization results.
        iteratorDistrict.getResult().smoothSmr = java.lang.Double.parseDouble(splitedLine[10]);
        iteratorDistrict.getResult().smoothSmrCategory = java.lang.Integer.parseInt(splitedLine[11]);
      }
      else
      {
        if(fileReader != null)
        {
          fileReader.close();
          // Return after load, if it is just progresses loading.
          if(isProgressesLoading)
          {
            return false;
          }
        }
        fileReader = null;

        if(categoryName.equals(CATEGORIES[FIVE]))
        {
          iteratorDistrict.getResult().smoothSmrCategory = Standardization.categorize(iteratorDistrict.getResult().smoothSmr, Standardization.signify(iteratorDistrict.getResult().observedCases, iteratorDistrict.getResult().observedCases / iteratorDistrict.getResult().smoothSmr));
        }
        else if(categoryName.equals(CATEGORIES[MIN_MAX]))
        {
          iteratorDistrict.getResult().smoothSmrCategory = categorizeMinMax(iteratorDistrict.getResult().smoothSmr, categoryCount, minSmr, maxSmr);
        }
        else if(categoryName.equals(CATEGORIES[ANY]))
        {
          iteratorDistrict.getResult().smoothSmrCategory = categorizeAny(iteratorDistrict.getResult().smoothSmr, categoryLevels);
        }
        // Prepare line for year-interval.
        line = iteratorDistrict.getCode() + VS + iteratorDistrict.getResult().population + VS +
          iteratorDistrict.getResult().observedCases + VS + elv.util.Util.toString(iteratorDistrict.getResult().expectedCases) + VS +
          elv.util.Util.toString(iteratorDistrict.getResult().incidence) + VS + elv.util.Util.toString(iteratorDistrict.getResult().smr) + VS +
          elv.util.Util.toString(iteratorDistrict.getResult().smrSignificance) + VS + iteratorDistrict.getResult().smrCategory + VS +
          elv.util.Util.toString(iteratorDistrict.getResult().probability) + VS + iteratorDistrict.getResult().probabilityCategory + VS +
          elv.util.Util.toString(iteratorDistrict.getResult().smoothSmr) + VS + iteratorDistrict.getResult().smoothSmrCategory + VS +
          elv.util.Util.toString(iteratorDistrict.getResult().trend) + VS + iteratorDistrict.getResult().trendSignificance + VS +
          elv.util.Util.toString(iteratorDistrict.getResult().trendCorrelation) + VS + iteratorDistrict.getResult().trendCategory;
        // Store line for year-interval.
        java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
        fileWriter.println(line);
        fileWriter.close();
      }
      execution.getProgresses().peek().setValue(districtCount + 1);
    }
    execution.getProgresses().pop();
    setDone(execution.getTask(), true);
    return true;
  }
  
  /**
   * Method for smoothing.
   * @param execution an execution object.
   * @throws java.lang.Exception if there is any problem with smoothing.
   */
  public void smootheIRP(elv.task.Execution execution) throws java.lang.Exception
  {
    int iterations = (java.lang.Integer)elv.util.Property.get(ITERATIONS_NAME, properties).getValue();
    int partitions = (java.lang.Integer)elv.util.Property.get(PARTITIONS_NAME, properties).getValue();
    double distanceWeighingConstant = (java.lang.Double)elv.util.Property.get(DISTANCE_WEIGHING_CONSTANT_NAME, properties).getValue();
    boolean smrIsWeighed = (java.lang.Boolean)elv.util.Property.get(SMR_IS_WEIGHED_NAME, properties).getValue();
    if(partitions > execution.getDistricts().size())
    {
      partitions = execution.getDistricts().size();
    }
    
    java.util.Vector<java.util.Vector<java.lang.Double>> iterationSmrs = new java.util.Vector<java.util.Vector<java.lang.Double>>();
    execution.getProgresses().push(new elv.util.Progress(ITERATIONS_NAME, 0, iterations));
    for(int iterationCount = 0; iterationCount < iterations; iterationCount++)
    {
      // Generate random partition indices.
      java.util.Vector<java.lang.Integer> partitionIndices = new java.util.Vector<java.lang.Integer>();
      for(int partitionCount = 0; partitionCount < partitions; )
      {
        int index = new java.util.Random().nextInt(execution.getDistricts().size());
        // Verify if is different than the other indices.
        boolean isDifferent = true;
        for(java.lang.Integer iteratorIndex : partitionIndices)
        {
          if(index == iteratorIndex)
          {
            isDifferent = false;
            break;
          }
        }
        if(isDifferent)
        {
          partitionIndices.add(index);
          partitionCount++;
        }
      }
      
      // Attach districts to partitions.
      java.util.Vector<java.lang.Integer> attachedPartitionIndices = new java.util.Vector<java.lang.Integer>();
      int[] partitionPopulations = new int[execution.getDistricts().size()];
      int[] partitionCounts = new int[execution.getDistricts().size()];
      double[] partitionSmrs = new double[execution.getDistricts().size()];
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.District.TITLE, 0, execution.getDistricts().size()));
      for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
      {
        elv.util.parameters.District iteratorDistrict = execution.getDistricts().get(districtCount);
        // Determine the nearest partition to district.
        int partitionIndex = 0;
        double minDistance = java.lang.Double.MAX_VALUE;
        for(java.lang.Integer iteratorIndex : partitionIndices)
        {
          // Deterrmine the distance.
          double districtX = iteratorDistrict.getXCoordinate();
          double districtY = iteratorDistrict.getYCoordinate();
          double partitionX = execution.getDistricts().get(iteratorIndex).getXCoordinate();
          double partitionY = execution.getDistricts().get(iteratorIndex).getYCoordinate();
          double distance = java.lang.Math.sqrt((partitionX - districtX) * (partitionX - districtX) + (partitionY - districtY) * (partitionY - districtY));
          int partitionPopulation = execution.getDistricts().get(iteratorIndex).getResult().population;
          if(partitionPopulation + iteratorDistrict.getResult().population !=0) // Weighed distance.
          {
            distance *= (1 + distanceWeighingConstant *
              (java.lang.Math.abs(((double)partitionPopulation - iteratorDistrict.getResult().population) /
              (partitionPopulation + iteratorDistrict.getResult().population))));
          }
          if(distance < minDistance)
          {
             minDistance = distance;
             partitionIndex = iteratorIndex;
          }
          // Return, if execution was stopped.
          if(!execution.isExecuted())
          {
            return;
          }
        }
        attachedPartitionIndices.add(partitionIndex);

        // Count the number, population and SMR of districts  attached to one partition.
        partitionPopulations[partitionIndex] += iteratorDistrict.getResult().population;
        partitionCounts[partitionIndex]++;
        partitionSmrs[partitionIndex] += (smrIsWeighed ? iteratorDistrict.getResult().smr * iteratorDistrict.getResult().population : iteratorDistrict.getResult().smr);

        // Return, if execution was stopped.
        if(!execution.isExecuted())
        {
          return;
        }
        java.lang.Thread.yield();
        execution.getProgresses().peek().setValue(districtCount + 1);
      }
      execution.getProgresses().pop();

      // Determine SMRs for this iteration.
      java.util.Vector<java.lang.Double> smrs = new java.util.Vector<java.lang.Double>();
      for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
      {
        int partitionIndex = attachedPartitionIndices.get(districtCount);
        int population = partitionPopulations[partitionIndex];
        int count = partitionCounts[partitionIndex];
        double smr = partitionSmrs[partitionIndex];
        smrs.add(smrIsWeighed ? (population != 0 ? smr / population : smr) : (count != 0 ? smr / count : smr));
        // Return, if execution was stopped.
        if(!execution.isExecuted())
        {
          return;
        }
      }
      iterationSmrs.add(smrs);
      
      execution.getProgresses().peek().setValue(iterationCount + 1);
    }
    execution.getProgresses().pop();
    
    // Determine final SMRs.
    execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.District.TITLE, 0, execution.getDistricts().size()));
    for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
    {
      DistrictResult iteratorDistrictResult = execution.getDistricts().get(districtCount).getResult();
      double smrSum = 0;
      for(int iterationCount = 0; iterationCount < iterations; iterationCount++)
      {
        smrSum += iterationSmrs.get(iterationCount).get(districtCount);
      }
      iteratorDistrictResult.smoothSmr = smrSum / iterations;
      
      // Return, if execution was stopped.
      if(!execution.isExecuted())
      {
        return;
      }
      execution.getProgresses().peek().setValue(districtCount + 1);
      java.lang.Thread.yield();
    }
    execution.getProgresses().pop();
    
    return;
  }
  
  /**
   * Method for smoothing.
   * @param execution an execution object.
   * @throws java.lang.Exception if there is any problem with smoothing.
   */
  public void smootheEmpiricalBayes(elv.task.Execution execution) throws java.lang.Exception
  {
    int iterations = (java.lang.Integer)elv.util.Property.get(ITERATIONS_NAME, properties).getValue();
    // Determine the correction values.
    double alpha = 0;
    double NU = 0;
    for(int iterationCount = 0; iterationCount < iterations; iterationCount++)
    {
      double betha = 0;
      java.util.Vector<java.lang.Double> thetas = new java.util.Vector<java.lang.Double>();
      for(elv.util.parameters.District iteratorDistrict : execution.getDistricts())
      {
        double theta = (iteratorDistrict.getResult().expectedCases + alpha != 0 ? ((double)iteratorDistrict.getResult().observedCases + NU) / (iteratorDistrict.getResult().expectedCases + alpha) : iteratorDistrict.getResult().smr);
        betha += theta;
        thetas.add(theta);
      }
      betha /= execution.getDistricts().size();

      double A = 0;
      double B = 0;
      int count = 0;
      for(java.lang.Double iteratorTheta : thetas)
      {
        double gamma = (iteratorTheta - betha) * (iteratorTheta - betha);
        B += gamma;
        A += (execution.getDistricts().get(count).getResult().expectedCases != 0 ? gamma / execution.getDistricts().get(count).getResult().expectedCases : 0);
        count++;
      }
      double C = (-1) * ((double)execution.getDistricts().size() - 1) * betha;
      alpha = (A != 0 ? ((-1) * B + java.lang.Math.sqrt(B * B - 4 * A * C)) / (2 * A) : 0);
      NU = alpha * betha;
      
      // Return, if execution was stopped.
      if(!execution.isExecuted())
      {
        return;
      }
      java.lang.Thread.yield();
    }
    // Smoothe the SMRs.
    execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.District.TITLE, 0, execution.getDistricts().size()));
    for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
    {
      DistrictResult iteratorDistrictResult = execution.getDistricts().get(districtCount).getResult();
      iteratorDistrictResult.smoothSmr = (iteratorDistrictResult.expectedCases + alpha != 0 ? ((double)iteratorDistrictResult.observedCases + NU) / (iteratorDistrictResult.expectedCases + alpha) : iteratorDistrictResult.smr);
      
      // Return, if execution was stopped.
      if(!execution.isExecuted())
      {
        return;
      }
      execution.getProgresses().peek().setValue(districtCount + 1);
      java.lang.Thread.yield();
    }
    execution.getProgresses().pop();
    
    return;
  }
  /**
   * Method for calculating categories between minimum and maximum.
   * @param smr the standard mortality/morbiditi ratio.
   * @param categoryCount the number of categories.
   * @param minSmr the minimal standard mortality/morbiditi ratio.
   * @param maxSmr the maximal standard mortality/morbiditi ratio.
   * @return the category of standardization.
   */
  protected static int categorizeMinMax(double smr, int categoryCount, double minSmr, double maxSmr)
  {
    double interval = (maxSmr - minSmr) / categoryCount;
    double level = minSmr + interval;
    int category;
    for(category = 1; category <= categoryCount; category++)
    {
      if(level >= smr)
      {
        break;
      }
      else
      {
        level = level + interval;
      }
    }
    return category;
  }
  
  /**
   * Method for calculating categories between minimum and maximum.
   * @param smr the standard mortality/morbiditi ratio.
   * @param categoryLevels a vector of category levels.
   * @return the category of standardization.
   */
  protected static int categorizeAny(double smr, java.util.Vector<java.lang.Double> categoryLevels)
  {
    int category;
    for(category = 0; category < categoryLevels.size(); category++)
    {
      if(categoryLevels.get(category) > smr)
      {
        break;
      }
    }
    return category + 1;
  }
  
}
