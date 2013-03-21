/*
 * SpotStandardization.java
 */

package elv.task.executables;

/**
 * Class for implementing the spot standardization method.
 * @author Elv
 */
public class SpotStandardization extends Standardization
{
  
  /**
   * Constants.
   */
  /** The property file name. */
  private final static java.lang.String PROPERTY_FILE = "standardization_spot.prop";
  /** The execution files. */
  private final static ExecutionFile[] EXECUTION_FILES = {new ExecutionFile("standardization_spot.csv", "Standardization.Spots"), new ExecutionFile("standardization_spot_years.csv", "Standardization.Spots.Years")};
  // Indices in the above array.
  /** Index in the <code>EXECUTION_FILES</code> array. */
  public final static int SPOTS = 0;
  /** Index in the <code>EXECUTION_FILES</code> array. */
  public final static int SPOTS_YEARS = 1;
  
  // Property names.
  /** The "name" property name.*/
  public final static java.lang.String NAME = "Standardization.Spot";
  /** The "significance" property name.*/
  public final static java.lang.String SIGNIFICANCE_NAME = NAME + ".Significance";
  /** The "significance level" property name.*/
  public final static java.lang.String SIGNIFICANCE_LEVEL_NAME = NAME + ".Significance.Level";
  
  // Property values.
  /** The array of possible significance types. */
  public final static java.lang.String[] SIGNIFICANCES = {"Significance.Poisson", "Significance.Pearson", "Significance.Likelyhood"};
    // The indices in the above array.
  /** Index in the <code>SIGNIFICANCES</code> array. */
    public final static int POISSON = 0;
  /** Index in the <code>SIGNIFICANCES</code> array. */
    public final static int PEARSON = 1;
  /** Index in the <code>SIGNIFICANCES</code> array. */
    public final static int LIKELYHOOD = 2;
  /** The array of possible significance levels. */
  public final static double[] SIGNIFICANCE_LEVELS = {0.1, 0.01, 0.001};
    // Indices in the above array.
  /** Index in the <code>SIGNIFICANCE_LEVELS</code> array. */
    public final static int SL1 = 0;
  /** Index in the <code>SIGNIFICANCE_LEVELS</code> array. */
    public final static int SL2 = 1;
  /** Index in the <code>SIGNIFICANCE_LEVELS</code> array. */
    public final static int SL3 = 2;
    
  /** File header. */
  public final static java.lang.String HEADER =
    "Spot" + VS + "Population" + VS + "Observed cases" + VS +
    "Expected cases" + VS + "Incidence" + VS + "SMR" + VS +
    "Trend" + VS + "Trend significance" + VS + "Trend correlation" + VS + "Trend category"+ VS + "Trend constant" ;
  /** File header. */
  public final static java.lang.String YEAR_HEADER =
    "Spot" + VS + "Year" + VS + "Observed cases" + VS + "Expected cases" + VS + "Incidence" + VS + "SMR";
  /** File header. */
  public final static java.lang.String DISTRICT_HEADER =
    "District" + VS + "Trend:" + VS + "Trend significance" + VS + "Trend correlation" + VS + "Trend category";
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public SpotStandardization() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @return the name of this standardization.
   */
  public java.lang.String getName()
  {
    return NAME;
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @return the root name of properties.
   */
  public java.lang.String getRootName()
  {
    return NAME;
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @return the propety file name.
   */
  public java.lang.String getPropertyFile()
  {
    return PROPERTY_FILE;
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @throws java.lang.Exception if there is any error with setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    elv.util.Property prop = new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, SIGNIFICANCE_NAME, SIGNIFICANCES[POISSON]);
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, SIGNIFICANCE_NAME, SIGNIFICANCES[POISSON]));
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.COMBO_BOX, false, SIGNIFICANCE_LEVEL_NAME, SIGNIFICANCE_LEVELS[SL2]));
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(SIGNIFICANCE_NAME, properties).setDefaultValues(elv.util.Util.vectorize(SIGNIFICANCES));
    
    java.util.Vector<java.lang.Double> significanceLevels = new java.util.Vector<java.lang.Double>();
    for(java.lang.Double iteratorSignificanceLevel : SIGNIFICANCE_LEVELS)
    {
      significanceLevels.add(iteratorSignificanceLevel);
    }
    elv.util.Property.get(SIGNIFICANCE_LEVEL_NAME, properties).setDefaultValues(significanceLevels);
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @return the string reprezentation of this standardization.
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
   * Overridden method from <CODE>elv.task.executables.Standardization</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    // Initialize the file readers.
    java.lang.String pathName =  execution.getTask().getExecutionFolderPath() + "/" + EXECUTION_FILES[SPOTS];
    java.io.BufferedReader fileReader = null;
    java.lang.String yearsPathName =  execution.getTask().getExecutionFolderPath() + "/" + EXECUTION_FILES[SPOTS_YEARS];
    java.io.BufferedReader yearsFileReader = null;
    try
    {
      fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(pathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      fileReader.readLine();
      yearsFileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(yearsPathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      yearsFileReader.readLine();
    }
    catch(java.io.FileNotFoundException exc)
    {
      // Return, if it is just progresses loading.
      if(!execution.isExecuted())
      {
        return false;
      }
      fileReader = null;
      // Store header line.
      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
      fileWriter.println(HEADER);
      fileWriter.close();
      yearsFileReader = null;
      // Store header year line.
      java.io.PrintWriter yearsFileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(yearsPathName, true), elv.util.Util.FILE_ENCODING));
      yearsFileWriter.println(YEAR_HEADER);
      yearsFileWriter.close();
    }
    
    // Initialize.
    java.lang.String signifyingMethod = (java.lang.String)elv.util.Property.get(SIGNIFICANCE_NAME, properties).getValue();
    java.util.Vector<SpotYearResult> spotYearResults = new java.util.Vector<SpotYearResult>();
    // Get all standardization probabilities.
    java.util.Vector<StandardizationProbability> standardizationProbabilities = StandardizationProbability.getAllStandardizationProbabilityies();
    // Get all trend significance values.
    java.util.Vector<TrendSignificance> trendSignificances = TrendSignificance.getAllTrendSignificances();
    
    // Determine total and yearly population and cases.
    int totalPopulation = 0;
    int totalCases = 0;
    int[] yearPopulations = new int[execution.getYears().size()];
    int[] yearObservedCases = new int[execution.getYears().size()];
    for(elv.util.parameters.District iteratorDistrict : execution.getDistricts())
    {
      totalCases += iteratorDistrict.getResult().observedCases;
      totalPopulation += iteratorDistrict.getResult().population;
      for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
      {
        DistrictYearResult iteratorYearResult = iteratorDistrict.getYearResults().get(yearCount);
        yearObservedCases[yearCount] += iteratorYearResult.observedCases;
        yearPopulations[yearCount] += iteratorYearResult.population;
      }
    }
    
    java.lang.String line;
    int lineCount = 1; // Due to the header line;
    
    // Parse.
    execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Spot.TITLE, 0, execution.getSpots().size()));
    for(int spotCount = 0; spotCount < execution.getSpots().size(); spotCount++)
    {
      elv.util.parameters.Spot iteratorSpot = execution.getSpots().get(spotCount);
      if(iteratorSpot.getResult().isSelected)
      {
        double spotArea = 0;
        double spotXCoordinate = 0;
        double spotYCoordinate = 0;
        for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
        {
          spotArea += iteratorDistrict.getArea();
          spotXCoordinate += iteratorDistrict.getXCoordinate();
          spotYCoordinate += iteratorDistrict.getYCoordinate();
          iteratorSpot.getResult().meanSMR += iteratorDistrict.getResult().smr;
          iteratorSpot.getResult().population += iteratorDistrict.getResult().population;
          iteratorSpot.getResult().observedCases += iteratorDistrict.getResult().observedCases;
          iteratorSpot.getResult().expectedCases += iteratorDistrict.getResult().expectedCases;
        }
        iteratorSpot.setArea(spotArea);
        iteratorSpot.setXCoordinate(spotXCoordinate / iteratorSpot.getResult().districts.size());
        iteratorSpot.setYCoordinate(spotYCoordinate / iteratorSpot.getResult().districts.size());
        iteratorSpot.getResult().meanSMR /= iteratorSpot.getResult().districts.size();
        iteratorSpot.getResult().smr = iteratorSpot.getResult().observedCases / iteratorSpot.getResult().expectedCases;
        iteratorSpot.getResult().incidence = (totalPopulation == 0 ? 0 : iteratorSpot.getResult().smr * totalCases / totalPopulation * 1000);
        if(signifyingMethod.equals(SIGNIFICANCES[POISSON]))
        {
          iteratorSpot.getResult().smrSignificance = signifyPoisson(iteratorSpot.getResult().observedCases, iteratorSpot.getResult().expectedCases);
        }
        else if(signifyingMethod.equals(SIGNIFICANCES[PEARSON]))
        {
          iteratorSpot.getResult().smrSignificance = signifyPearson(iteratorSpot.getResult().observedCases, totalCases, iteratorSpot.getResult().population, totalPopulation);
        }
        else if(signifyingMethod.equals(SIGNIFICANCES[LIKELYHOOD]))
        {
          iteratorSpot.getResult().smrSignificance = signifyLikelyhood(iteratorSpot.getResult().observedCases, totalCases, iteratorSpot.getResult().population, totalPopulation);
        }
        iteratorSpot.getResult().smrProbability = computeProbabilityAsString(signifyPoisson(iteratorSpot.getResult().observedCases, iteratorSpot.getResult().expectedCases), standardizationProbabilities);
        
        int sumX = 0;
        double sumY = 0;
        int sumX2 = 0;
        double sumY2 = 0;
        double sumXY = 0;
        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, execution.getYears().size()));
        for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
        {
          int iteratorYear = execution.getYears().get(yearCount);
          int observedCasesSpotYear = 0;
          double expectedCasesSpotYear = 0;
          for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
          {
            for(DistrictYearResult iteratorYearResult : iteratorDistrict.getYearResults())
            {
              if(iteratorYearResult.year == iteratorYear)
              {
                observedCasesSpotYear += iteratorYearResult.observedCases;
                expectedCasesSpotYear += iteratorYearResult.expectedCases;
              }
            }
          }
          double smrSpotYear = (expectedCasesSpotYear == 0 ? 0 : observedCasesSpotYear / expectedCasesSpotYear);
          double incidenceSpotYear = (yearPopulations[yearCount] == 0 ? 0 : smrSpotYear * yearObservedCases[yearCount] / yearPopulations[yearCount] * 1000);
          sumX += iteratorYear;
          sumY += incidenceSpotYear;
          sumX2 += iteratorYear * iteratorYear;
          sumY2 += incidenceSpotYear * incidenceSpotYear;
          sumXY += iteratorYear * incidenceSpotYear;
          iteratorSpot.getYearResults().add(new SpotYearResult(iteratorYear, observedCasesSpotYear, expectedCasesSpotYear, incidenceSpotYear, smrSpotYear));
          // Prepare line for spot-year.
          line = iteratorSpot.getCode() + VS + iteratorYear + VS + observedCasesSpotYear + VS +
            elv.util.Util.toString(expectedCasesSpotYear) + VS + elv.util.Util.toString(incidenceSpotYear) + VS +
            elv.util.Util.toString(smrSpotYear);
          // Store line for spot-year.
          java.io.PrintWriter yearsFileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(yearsPathName, true), elv.util.Util.FILE_ENCODING));
          yearsFileWriter.println(line);
          yearsFileWriter.close();
          
          execution.getProgresses().peek().setValue(yearCount + 1);
        }
        execution.getProgresses().pop();
      
        double sQx = (double)sumX2 - (double)sumX * sumX / execution.getYears().size();
        double sQy = sumY2 - sumY * sumY / execution.getYears().size();
        double sP = sumXY - (double)sumX * sumY / execution.getYears().size();
        iteratorSpot.getResult().trend = sP / sQx;
        iteratorSpot.getResult().trendCorrelation = java.lang.Math.abs(sP / java.lang.Math.sqrt(java.lang.Math.abs(sQx * sQy)));
        iteratorSpot.getResult().trendSignificance = signify(sQx, sQy, sP, execution.getYears(), trendSignificances);
        iteratorSpot.getResult().trendCategory = categorizeTrend(iteratorSpot.getResult().trend);
        iteratorSpot.getResult().trendConstant = sumY / execution.getYears().size() - iteratorSpot.getResult().trend * sumX / execution.getYears().size();
        // Prepare line for spot.
        line = iteratorSpot.getCode() + VS + iteratorSpot.getResult().population + VS + 
          iteratorSpot.getResult().observedCases + VS + elv.util.Util.toString(iteratorSpot.getResult().expectedCases) + VS +
          elv.util.Util.toString(iteratorSpot.getResult().incidence) + VS + elv.util.Util.toString(iteratorSpot.getResult().smr) + VS +
          elv.util.Util.toString(iteratorSpot.getResult().trend) + VS + iteratorSpot.getResult().trendSignificance + VS +
          elv.util.Util.toString(iteratorSpot.getResult().trendCorrelation) + VS + elv.util.Util.toString(iteratorSpot.getResult().trendCategory) + VS +
          elv.util.Util.toString(iteratorSpot.getResult().trendConstant);
        // Store line for spot.
        java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
        fileWriter.println(line);
        fileWriter.close();
      }
      execution.getProgresses().peek().setValue(spotCount + 1);
    }
    execution.getProgresses().pop();
    setDone(execution.getTask(), true);
    return true;
  }
  
}
