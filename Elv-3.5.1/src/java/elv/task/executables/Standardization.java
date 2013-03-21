/*
 * Standardization.java
 */
package elv.task.executables;

/**
 * Class for implementing the standardization methods.
 * @author Qpa
 */
public class Standardization extends Executable
{
  
  /**
   * Constants.
   */
  private final static java.lang.String PROPERTY_FILE = "standardization.prop";
  private final static java.lang.String[] EXECUTION_FILES = {"standardization.csv", "standardization_years.csv", "standardization_windows_intervals.csv"};
  private final static java.lang.String[] EXECUTION_FILE_TITLES = {"Standardization.Districts", "Standardization.Districts.Years", "Standardization.Districts.Windows-Intervals"};
  // Indices in the above arrays.
  public final int DISTRICTS = 0;
  public final int DISTRICTS_YEARS = 1;
  public final int DISTRICTS_WINDOWS_INTERVALS = 2;
  
  // Property names.
  public final static java.lang.String NAME = "Standardization";
  public final static java.lang.String YEAR_WINDOWS_NAME = NAME + ".YearWindows";
  
  // Property values.
  private final static java.lang.String[] STANDARDIZATIONS = {"Standardization.Direct", "Standardization.Indirect"};
    // The indices in the above array.
    public final static int DIRECT = 0;
    public final static int INDIRECT = 1;
  public final static int DEFAULT_YEAR_WINDOW = 1;
  
  public final static java.lang.String HEADER = 
    "District" + VS + "Population" + VS + "Observed cases" + VS +
    "Expected cases" + VS + "Incidence" + VS + "SMR" + VS + "SMR significance" + VS +
    "SMR category" + VS + "Probability" + VS + "Probability category" + VS +
    "TrendY" + VS + "Trend significance" + VS + "Trend correlation" + VS + "Trend category";
  public final static java.lang.String YEARS_HEADER = "Year" + VS +
    "District" + VS + "Population" + VS + "Observed cases" + VS + "Expected cases" + VS + "Incidence" + VS +
    "SMR" + VS + "SMR significance" + VS + "SMR category" + VS + "Probability" + VS + "Probability category";
  public final static java.lang.String WINDOWS_INTERVALS_HEADER = "Year window" + VS + "Year interval" + VS + 
    "District" + VS + "Population" + VS + "Observed cases" + VS + "Expected cases" + VS + "Incidence" + VS +
    "SMR" + VS + "SMR significance" + VS + "SMR category" + VS + "Probability" + VS + "Probability category";
  
  /**
   * Inner class for standardization probability reprezentation.
   */
  public static class StandardizationProbability implements java.io.Serializable
  {
    
    /**
     * Variables.
     */
    protected double probability;
    protected double hi2;
    
    /**
     * Constructor.
     * @throws java.lang.Exception if there is any problem with parsing.
     */
    public StandardizationProbability(java.lang.String line) throws java.lang.Exception
    {
      java.util.StringTokenizer sT = new java.util.StringTokenizer(line, "=");
      probability = java.lang.Double.parseDouble(sT.nextToken());
      hi2 = java.lang.Double.parseDouble(sT.nextToken());
    }
    
    /**
     * Method for getting all trend significances.
     * @throws java.lang.Exception if there is any problem with loading.
     */
    public static java.util.Vector<StandardizationProbability> getAllStandardizationProbabilityies() throws java.lang.Exception
    {
      java.util.Vector<StandardizationProbability> standardizationProbabilities = new java.util.Vector<StandardizationProbability>();
      java.lang.String line;
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new elv.util.Util().getClass().getResource("/resources/probability.properties").openStream(), elv.util.Util.FILE_ENCODING));
      while((line = fileReader.readLine()) != null)
      {
        standardizationProbabilities.add(new StandardizationProbability(line));
      }
      fileReader.close();
      return standardizationProbabilities;
    }
    
  }
  
  /**
   * Inner class for trend significance reprezentation.
   */
  public static class TrendSignificance
  {
    
    /**
     * Variables.
     */
    protected int year;
    protected double trend0_1;
    protected double trend0_05;
    protected double trend0_01;
    protected double trend0_001;
    
    /**
     * Constructor.
     * @throws java.lang.Exception if there is any problem with parsing.
     */
    public TrendSignificance(java.lang.String line) throws java.lang.Exception
    {
      year = java.lang.Integer.parseInt(line.substring(0, line.indexOf("=")));
      java.lang.String ts = line.substring(line.indexOf("=") + 1);
      java.util.StringTokenizer sT = new java.util.StringTokenizer(ts, ",");
      trend0_1 = java.lang.Double.parseDouble(sT.nextToken());
      trend0_05 = java.lang.Double.parseDouble(sT.nextToken());
      trend0_01 = java.lang.Double.parseDouble(sT.nextToken());
      trend0_001 = java.lang.Double.parseDouble(sT.nextToken());
    }
    
    /**
     * Method for getting all trend significances.
     * @throws java.lang.Exception if there is any problem with loading.
     */
    public static java.util.Vector<TrendSignificance> getAllTrendSignificances() throws java.lang.Exception
    {
      java.util.Vector<TrendSignificance> trendSignificances = new java.util.Vector<TrendSignificance>();
      java.lang.String line;
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new elv.util.Util().getClass().getResource("/resources/trend_significance.properties").openStream(), elv.util.Util.FILE_ENCODING));
      while((line = fileReader.readLine()) != null)
      {
        trendSignificances.add(new TrendSignificance(line));
      }
      fileReader.close();
      return trendSignificances;
    }
    
  }
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public Standardization() throws java.lang.Exception
  {
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }
  
  /**
   * Method for getting the name of the standardization.
   * @return the name of this standardization.
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
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, NAME, STANDARDIZATIONS[INDIRECT]));
    java.util.Vector<java.lang.Integer> yearWindows = new java.util.Vector<java.lang.Integer>();
    yearWindows.add(DEFAULT_YEAR_WINDOW);
    properties.add(new elv.util.Property(elv.util.Property.INTEGER_ARRAY, elv.util.Property.LIST_BUTTON, true, YEAR_WINDOWS_NAME, yearWindows));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(NAME, properties).setDefaultValues(elv.util.Util.vectorize(STANDARDIZATIONS));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param standardization an <CODE>elv.task.executables.Standardization</CODE> object.
   */
  public boolean equals(java.lang.Object standardization)
  {
    boolean isEqual = false;
    if(standardization != null && standardization instanceof Standardization)
    {
      isEqual = getPropertyFile().equals(((Standardization)standardization).getPropertyFile());
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this standardization.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(getName());
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return a vector with the execution file names.
   * @throws java.lang.Exception if there is any error with getting.
   */
  public java.util.Vector<java.lang.String> getExecutionFiles() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> executionFiles = new java.util.Vector<java.lang.String>();
    for(java.lang.String iteratorFileName : EXECUTION_FILES)
    {
      executionFiles.add(iteratorFileName);
    }
    return executionFiles;
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return a vector with the execution file titless.
   * @throws java.lang.Exception if there is any error with getting.
   */
  public java.util.Vector<java.lang.String> getExecutionFileTitles() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> executionFileTitles = new java.util.Vector<java.lang.String>();
    for(java.lang.String iteratorFileTitle : EXECUTION_FILE_TITLES)
    {
      executionFileTitles.add(elv.util.Util.translate(iteratorFileTitle));
    }
    return executionFileTitles;
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
   * Implemented method from <CODE>elv.task.executables.Executable</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    // Initialize the file readers.
    java.lang.String pathName = execution.getTask().getExecutionFolderPath() + elv.util.Util.getFS() + getExecutionFiles().get(DISTRICTS);
    java.io.BufferedReader fileReader = null;
    java.lang.String yearsPathName = execution.getTask().getExecutionFolderPath() + elv.util.Util.getFS() + getExecutionFiles().get(DISTRICTS_YEARS);
    java.io.BufferedReader yearsFileReader = null;
    java.lang.String windowsIntervalsPathName = execution.getTask().getExecutionFolderPath() + elv.util.Util.getFS() + getExecutionFiles().get(DISTRICTS_WINDOWS_INTERVALS);
    java.io.BufferedReader windowsIntervalsFileReader = null;
    try
    {
      fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(pathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      fileReader.readLine();
      yearsFileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(yearsPathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      yearsFileReader.readLine();
      windowsIntervalsFileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(windowsIntervalsPathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      windowsIntervalsFileReader.readLine();
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
      // Store header line.
      java.io.PrintWriter yearsFileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(yearsPathName, true), elv.util.Util.FILE_ENCODING));
      yearsFileWriter.println(YEARS_HEADER);
      yearsFileWriter.close();
      windowsIntervalsFileReader = null;
      // Store header line.
      java.io.PrintWriter windowsIntervalsFileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(windowsIntervalsPathName, true), elv.util.Util.FILE_ENCODING));
      windowsIntervalsFileWriter.println(WINDOWS_INTERVALS_HEADER);
      windowsIntervalsFileWriter.close();
    }
    
    // Initialize.
    // Get all standardization probabilities.
    java.util.Vector<StandardizationProbability> standardizationProbabilities = StandardizationProbability.getAllStandardizationProbabilityies();
    // Get all trend significance values.
    java.util.Vector<TrendSignificance> trendSignificances = TrendSignificance.getAllTrendSignificances();
    
    java.lang.String standardizationMethod = (java.lang.String)elv.util.Property.get(NAME, properties).getValue();
    // Determine the standardization year-window intervals.
    java.util.Vector<java.lang.Integer> yearWindows = (java.util.Vector)elv.util.Property.get(YEAR_WINDOWS_NAME, properties).getValue();
    java.util.Vector<java.util.Vector<elv.util.parameters.YearInterval>> yearWindowIntervals = new java.util.Vector<java.util.Vector<elv.util.parameters.YearInterval>>();
    for(int yearWindowCount = 0; yearWindowCount < yearWindows.size(); yearWindowCount++)
    {
      int iteratorYearWindow = yearWindows.get(yearWindowCount);
      java.util.Vector<elv.util.parameters.YearInterval> yearIntervals = new java.util.Vector<elv.util.parameters.YearInterval>();
      for(int yearIntervalCount = 0; yearIntervalCount < execution.getYearIntervals().size(); yearIntervalCount++)
      {
        elv.util.parameters.Interval iteratorYearInterval = execution.getYearIntervals().get(yearIntervalCount);
        int from = iteratorYearInterval.getFromValue();
        int to = ((yearIntervalCount + iteratorYearWindow - 1 < execution.getYearIntervals().size()) ?
                 execution.getYearIntervals().get(yearIntervalCount + iteratorYearWindow - 1).getToValue() :
                 execution.getYearIntervals().get(execution.getYearIntervals().size() - 1).getToValue());
        yearIntervals.add(new elv.util.parameters.YearInterval(from, to));
      }
      yearWindowIntervals.add(yearIntervals);
    }
    int meanYearCountPeriod = 1;
    int fromYearPeriod = execution.getYears().get(0);
    int toYearPeriod = execution.getYears().get(execution.getYears().size() - 1);
    int meanYearPeriod = (fromYearPeriod + toYearPeriod) / 2;
    if(meanYearPeriod - fromYearPeriod < toYearPeriod - meanYearPeriod)
    {
      meanYearCountPeriod = 2;
    }

    
    java.lang.String line;
    int lineCount = 1; // Due to the header line;
    
    // Parse.
    execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.District.TITLE, 0, execution.getDistricts().size()));
    for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
    {
      elv.util.parameters.District iteratorDistrict = execution.getDistricts().get(districtCount);
      double populationPeriod = 0;
      int observedCasesPeriod = 0;
      double expectedCasesPeriod = 0;
      double benchPopulationPeriod = 0;
      int benchCasesPeriod = 0;
      double basePopulationPeriod = 0;
      int baseCasesPeriod = 0;
      int sumX = 0;
      double sumY = 0;
      int sumX2 = 0;
      double sumY2 = 0;
      double sumXY = 0;
      
      execution.getProgresses().push(new elv.util.Progress(YEAR_WINDOWS_NAME, 0, yearWindowIntervals.size()));
      for(int yearWindowCount = 0; yearWindowCount < yearWindowIntervals.size(); yearWindowCount++)
      {
        int iteratorYearWindow = yearWindows.get(yearWindowCount);
        
        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.TITLE, 0, yearWindowIntervals.get(yearWindowCount).size()));
        for(int yearIntervalCount = 0; yearIntervalCount < yearWindowIntervals.get(yearWindowCount).size(); yearIntervalCount++)
        {
          elv.util.parameters.Interval iteratorYearInterval = yearWindowIntervals.get(yearWindowCount).get(yearIntervalCount);
          int meanYearCount = 1;
          int meanYear = (iteratorYearInterval.getFromValue() + iteratorYearInterval.getToValue()) / 2;
          if(meanYear - iteratorYearInterval.getFromValue() < iteratorYearInterval.getToValue() - meanYear)
          {
            meanYearCount = 2;
          }
          
          if(windowsIntervalsFileReader != null && (line = windowsIntervalsFileReader.readLine()) != null)
          {
            lineCount++;
            java.lang.String expectedLine = iteratorYearWindow + VS + iteratorYearInterval + VS + iteratorDistrict.getCode();
            java.lang.String observedLine = "";
            java.util.StringTokenizer sT = new java.util.StringTokenizer(line, VS);
            observedLine = sT.nextToken();
            for(int i = 0; i < 2; i++)
            {
              observedLine += VS + sT.nextToken();
            }
            if(!observedLine.equals(expectedLine))
            {
              throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
            }
            int populationYearInterval = java.lang.Integer.parseInt(sT.nextToken());
            int observedCasesYearInterval = java.lang.Integer.parseInt(sT.nextToken());
            double expectedCasesYearInterval = java.lang.Double.parseDouble(sT.nextToken());
            double incidenceYearInterval = java.lang.Double.parseDouble(sT.nextToken());
            double smrYearInterval = java.lang.Double.parseDouble(sT.nextToken());
            int smrSignificanceYearInterval = java.lang.Integer.parseInt(sT.nextToken());
            int smrCategoryYearInterval = java.lang.Integer.parseInt(sT.nextToken());
            double probabilityYearInterval = java.lang.Double.parseDouble(sT.nextToken());
            int probabilityCategoryYearInterval = java.lang.Integer.parseInt(sT.nextToken());
            iteratorDistrict.getWindowIntervalResults().add(new DistrictWindowIntervalResult(iteratorYearWindow, iteratorYearInterval, populationYearInterval, observedCasesYearInterval, expectedCasesYearInterval, incidenceYearInterval, smrYearInterval, smrSignificanceYearInterval, smrCategoryYearInterval, probabilityYearInterval, probabilityCategoryYearInterval));
          }
          else
          {
            if(windowsIntervalsFileReader!= null)
            {
              windowsIntervalsFileReader.close();
              // Return after load, if it is just progresses loading.
              if(!execution.isExecuted())
              {
                return false;
              }
            }
            windowsIntervalsFileReader = null;
            
            double basePopulationYearInterval = 0;
            int benchPopulationYearInterval = 0;
            int baseCasesYearInterval = 0;
            int benchCasesYearInterval = 0;
            double populationYearInterval = 0;
            int observedCasesYearInterval = 0;
            double expectedCasesYearInterval = 0;
            
            execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, iteratorYearInterval.getToValue() - iteratorYearInterval.getFromValue() + 1));
            for(int iteratorYear = iteratorYearInterval.getFromValue(); iteratorYear <= iteratorYearInterval.getToValue(); iteratorYear++)
            {
              // Assigning the benchmark year to the standardization benchmark, if there is.
              int benchYear;
              if(execution.getBenchmarkYear() != 0)
              {
                benchYear = execution.getBenchmarkYear();
                meanYear = benchYear;
                meanYearCount = 1;
                meanYearPeriod = benchYear;
                meanYearCountPeriod = 1;
              }
              else
              {
                benchYear = iteratorYear;
              }
              
              boolean yearIsParsed = false;
              for(DistrictYearResult iteratorYearResult : iteratorDistrict.getYearResults())
              {
                if(iteratorYearResult.year == iteratorYear)
                {
                  yearIsParsed = true;
                  break;
                }
                // Return, if execution was stopped.
                if(!execution.isExecuted())
                {
                  return false;
                }
              }
              
              populationPeriod = 0;
              observedCasesPeriod = 0;
              expectedCasesPeriod = 0;
              benchPopulationPeriod = 0;
              benchCasesPeriod = 0;
              basePopulationPeriod = 0;
              baseCasesPeriod = 0;
      
              basePopulationYearInterval = 0;
              benchPopulationYearInterval = 0;
              baseCasesYearInterval = 0;
              benchCasesYearInterval = 0;
              populationYearInterval = 0;
              observedCasesYearInterval = 0;
              expectedCasesYearInterval = 0;
              
              int basePopulationYear = 0;
              int benchPopulationYear = 0;
              int baseCasesYear = 0;
              int benchCasesYear = 0;
              int populationYear = 0;
              int observedCasesYear = 0;
              double expectedCasesYear = 0;
              
              for(elv.util.parameters.Interval iteratorAgeInterval : execution.getAgeIntervals())
              {
                double basePopulationPeriodAgeInterval = 0;
                double benchPopulationPeriodAgeInterval = 0;
                double populationPeriodAgeInterval = 0;
                int baseCasesPeriodAgeInterval = 0;
                int benchCasesPeriodAgeInterval = 0;
                int observedCasesPeriodAgeInterval = 0;
                
                double basePopulationYearIntervalAgeInterval = 0;
                double benchPopulationYearIntervalAgeInterval = 0;
                double populationYearIntervalAgeInterval = 0;
                int baseCasesYearIntervalAgeInterval = 0;
                int benchCasesYearIntervalAgeInterval = 0;
                int observedCasesYearIntervalAgeInterval = 0;
                
                int basePopulationYearAgeInterval = 0;
                int benchPopulationYearAgeInterval = 0;
                int populationYearAgeInterval = 0;
                int baseCasesYearAgeInterval = 0;
                int benchCasesYearAgeInterval = 0;
                int observedCasesYearAgeInterval = 0;
                
                boolean isBenchmarkTerritory = false;
                for(elv.util.parameters.Settlement iteratorSettlement: execution.getAllSettlements())
                {
                  if(iteratorSettlement.getYearResults().size() > 0)
                  {
                    for(SettlementYearResult iteratorYearResult : iteratorSettlement.getYearResults())
                    {
                      // Set the benchmark territory indicator flag.
                      isBenchmarkTerritory = (!isBenchmarkTerritory ? iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement : isBenchmarkTerritory);

                      if(iteratorYearResult.year == benchYear)
                      {
                        if(iteratorYearResult.ageInterval.equals(iteratorAgeInterval))
                        {
                          // Count district population and cases for this year and age-interval.
                          if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() &&
                             iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()))
                          {
                            populationYearAgeInterval += iteratorYearResult.population;
                            observedCasesYearAgeInterval += iteratorYearResult.analyzedCases;
                          }
                          // Count benchmark population and cases for this year and age-interval.
                          if(iteratorSettlement instanceof elv.util.parameters.BaseSettlement)
                          {
                            basePopulationYearAgeInterval += iteratorYearResult.population;
                            baseCasesYearAgeInterval += iteratorYearResult.analyzedCases;

                            basePopulationYear += iteratorYearResult.population;
                            baseCasesYear += iteratorYearResult.analyzedCases;
                          }
                          else if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                          {
                            benchPopulationYearAgeInterval += iteratorYearResult.population;
                            benchCasesYearAgeInterval += iteratorYearResult.analyzedCases;

                            benchPopulationYear += iteratorYearResult.population;
                            benchCasesYear += iteratorYearResult.analyzedCases;
                          }
                        }
                      }
                      if(iteratorYearInterval.getFromValue() <= iteratorYearResult.year && iteratorYearResult.year <= iteratorYearInterval.getToValue())
                      {
                        if(iteratorYearResult.ageInterval.equals(iteratorAgeInterval))
                        {
                          // Count district cases for this year-interval and age-interval.
                          if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() &&
                             iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()))
                          {
                            observedCasesYearIntervalAgeInterval += iteratorYearResult.analyzedCases;
                          }
                          // Count benchmark cases for this year-interval and age-interval.
                          if(iteratorSettlement instanceof elv.util.parameters.BaseSettlement)
                          {
                            baseCasesYearIntervalAgeInterval += iteratorYearResult.analyzedCases;
                          }
                          else if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                          {
                            benchCasesYearIntervalAgeInterval += iteratorYearResult.analyzedCases;
                          }
                        }
                      }
                      if(iteratorYearResult.year == meanYear || iteratorYearResult.year == (meanYear + meanYearCount - 1))
                      {
                        if(iteratorYearResult.ageInterval.equals(iteratorAgeInterval))
                        {
                          // Count district population for this year-interval and age-interval.
                          if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() &&
                             iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()))
                          {
                            populationYearIntervalAgeInterval += (double)iteratorYearResult.population / meanYearCount;
                          }
                          // Count benchmark population for this year-interval and age-interval.
                          if(iteratorSettlement instanceof elv.util.parameters.BaseSettlement)
                          {
                            basePopulationYearIntervalAgeInterval += (double)iteratorYearResult.population / meanYearCount;
                          }
                          else if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                          {
                            benchPopulationYearIntervalAgeInterval += (double)iteratorYearResult.population / meanYearCount;
                          }
                        }
                      }
                      if(iteratorYearResult.ageInterval.equals(iteratorAgeInterval))
                      {
                        // Count district population and cases for this year and age-interval.
                        if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() &&
                           iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()))
                        {
                          observedCasesPeriodAgeInterval += iteratorYearResult.analyzedCases;
                        }
                        // Count benchmark population and cases for this period and age-interval.
                        if(iteratorSettlement instanceof elv.util.parameters.BaseSettlement)
                        {
                          baseCasesPeriodAgeInterval += iteratorYearResult.analyzedCases;

                          baseCasesPeriod += iteratorYearResult.analyzedCases;
                        }
                        else if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                        {
                          benchCasesPeriodAgeInterval += iteratorYearResult.analyzedCases;

                          benchCasesPeriod += iteratorYearResult.analyzedCases;
                        }
                      }
                      if(iteratorYearResult.year == meanYearPeriod || iteratorYearResult.year == (meanYearPeriod + meanYearCountPeriod - 1))
                      {
                        if(iteratorYearResult.ageInterval.equals(iteratorAgeInterval))
                        {
                          // Count district population for this district and age-interval.
                          if(iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode() &&
                             iteratorSettlement.getAggregation().equals(iteratorDistrict.getAggregation()))
                          {
                            populationPeriodAgeInterval += (double)iteratorYearResult.population / meanYearCountPeriod;
                          }
                          // Count benchmark population for this perion and age-interval.
                          if(iteratorSettlement instanceof elv.util.parameters.BaseSettlement)
                          {
                            basePopulationPeriodAgeInterval += (double)iteratorYearResult.population / meanYearCountPeriod;
                          }
                          else if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                          {
                            benchPopulationPeriodAgeInterval += (double)iteratorYearResult.population / meanYearCountPeriod;
                          }
                        }
                      }
                      // Return, if execution was stopped.
                      if(!execution.isExecuted())
                      {
                        return false;
                      }
                    }
                  }
                }
                // There was no benchmark territory selected.
                if(!isBenchmarkTerritory)
                {
                  benchPopulationYearAgeInterval = basePopulationYearAgeInterval;
                  benchCasesYearAgeInterval = baseCasesYearAgeInterval;
                  benchPopulationYear = basePopulationYear;
                  benchCasesYear = baseCasesYear;
                  benchPopulationYearIntervalAgeInterval = basePopulationYearIntervalAgeInterval;
                  benchCasesYearIntervalAgeInterval = baseCasesYearIntervalAgeInterval;
                  benchPopulationYearInterval += basePopulationYearIntervalAgeInterval;
                  benchCasesYearInterval += baseCasesYearIntervalAgeInterval;
                  benchPopulationPeriodAgeInterval = basePopulationPeriodAgeInterval;
                  benchCasesPeriodAgeInterval = baseCasesPeriodAgeInterval;
                  benchPopulationPeriod += basePopulationPeriodAgeInterval;
                  benchCasesPeriod += baseCasesPeriodAgeInterval;
                }
                else
                {
                  benchPopulationYearInterval += benchPopulationYearIntervalAgeInterval;
                  benchCasesYearInterval += benchCasesYearIntervalAgeInterval;
                }
                // Observed and expected.
                populationYear += populationYearAgeInterval;
                observedCasesYear += observedCasesYearAgeInterval;
                populationYearInterval += populationYearIntervalAgeInterval;
                observedCasesYearInterval += observedCasesYearIntervalAgeInterval;
                populationPeriod += populationPeriodAgeInterval;
                observedCasesPeriod += observedCasesPeriodAgeInterval;
                if(standardizationMethod.equals(STANDARDIZATIONS[DIRECT]))
                {
                  expectedCasesYear += (populationYearAgeInterval == 0 ? 0 : (double)observedCasesYearAgeInterval / populationYearAgeInterval * benchPopulationYearAgeInterval);
                  expectedCasesYearInterval += (populationYearIntervalAgeInterval == 0 ? 0 : (double)observedCasesYearIntervalAgeInterval / populationYearIntervalAgeInterval * benchPopulationYearIntervalAgeInterval);
                  expectedCasesPeriod += (populationPeriodAgeInterval == 0 ? 0 : (double)observedCasesPeriodAgeInterval / populationPeriodAgeInterval * benchPopulationPeriodAgeInterval);
                }
                else if(standardizationMethod.equals(STANDARDIZATIONS[INDIRECT]))
                {
                  expectedCasesYear += (benchPopulationYearAgeInterval == 0 ? 0 : (double)benchCasesYearAgeInterval / benchPopulationYearAgeInterval * populationYearAgeInterval);
                  expectedCasesYearInterval += (benchPopulationYearIntervalAgeInterval == 0 ? 0 : (double)benchCasesYearIntervalAgeInterval / benchPopulationYearIntervalAgeInterval * populationYearIntervalAgeInterval);
                  expectedCasesPeriod += (benchPopulationPeriodAgeInterval == 0 ? 0 : (double)benchCasesPeriodAgeInterval / benchPopulationPeriodAgeInterval * populationPeriodAgeInterval);
                }
              }
              // Determine smr and incidence for year.
              double smrYear = 0;
              if(standardizationMethod.equals(STANDARDIZATIONS[DIRECT]))
              {
                smrYear = (benchCasesYear == 0 ? 0 : expectedCasesYear / benchCasesYear);
                expectedCasesYear = (smrYear == 0 ? 0 : observedCasesYear / smrYear);
              } 
              else if(standardizationMethod.equals(STANDARDIZATIONS[INDIRECT]))
              {
                smrYear = (expectedCasesYear == 0 ? 0 : observedCasesYear / expectedCasesYear);
              }
              double incidenceYear = (benchPopulationYear == 0 ? 0 : smrYear * benchCasesYear / benchPopulationYear * 1000);

              // Determine the significance, category and probability for year.
              int smrSignificanceYear = signify(observedCasesYear, expectedCasesYear);
              int smrCategoryYear = categorize(smrYear, smrSignificanceYear);
              double probabilityYear = computeProbability(signifyPoisson(observedCasesYear, expectedCasesYear), standardizationProbabilities);
              int probabilityCategoryYear = categorizeProbability(probabilityYear);

              DistrictYearResult districtYearResult = new DistrictYearResult(iteratorYear, populationYear, observedCasesYear, expectedCasesYear, incidenceYear, smrYear, smrSignificanceYear, smrCategoryYear, probabilityYear, probabilityCategoryYear);
              if(!yearIsParsed)
              {
                // Summarize.
                sumX += iteratorYear;
                sumY += incidenceYear;
                sumX2 += iteratorYear * iteratorYear;
                sumY2 += incidenceYear * incidenceYear;
                sumXY += iteratorYear * incidenceYear;
                
                // Prepare line for year.
                line = iteratorYear + VS + iteratorDistrict.getCode() + VS +
                  populationYear + VS + observedCasesYear + VS + elv.util.Util.toString(expectedCasesYear) + VS +
                  elv.util.Util.toString(incidenceYear) + VS + elv.util.Util.toString(smrYear) + VS +
                  elv.util.Util.toString(smrSignificanceYear) + VS + smrCategoryYear + VS +
                  elv.util.Util.toString(probabilityYear) + VS + probabilityCategoryYear;
                // Store line for year.
                java.io.PrintWriter yearsFileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(yearsPathName, true), elv.util.Util.FILE_ENCODING));
                yearsFileWriter.println(line);
                yearsFileWriter.close();
                iteratorDistrict.getYearResults().add(districtYearResult);
              }

              // Return, if execution was stopped.
              if(!execution.isExecuted())
              {
                return false;
              }
              execution.getProgresses().peek().setValue(iteratorYear - iteratorYearInterval.getFromValue() + 1);
              java.lang.Thread.yield();
            }
            execution.getProgresses().pop();
            // Determine smr and incidence for year-interval.
            double smrYearInterval = 0;
            if(standardizationMethod.equals(STANDARDIZATIONS[DIRECT]))
            {
              smrYearInterval = (benchCasesYearInterval == 0 ? 0 : expectedCasesYearInterval / benchCasesYearInterval);
              expectedCasesYearInterval = (smrYearInterval == 0 ? 0 : observedCasesYearInterval / smrYearInterval);
            }
            else if(standardizationMethod.equals(STANDARDIZATIONS[INDIRECT]))
            {
              smrYearInterval = (expectedCasesYearInterval == 0 ? 0 : observedCasesYearInterval / expectedCasesYearInterval);
            }
            double incidenceYearInterval = (benchPopulationYearInterval == 0 ? 0 : smrYearInterval * benchCasesYearInterval / benchPopulationYearInterval * 1000);
            // Determine the significance, category and probability for year.
            int smrSignificanceYearInterval = signify(observedCasesYearInterval, expectedCasesYearInterval);
            int smrCategoryYearInterval = categorize(smrYearInterval, smrSignificanceYearInterval);
            double probabilityYearInterval = computeProbability(signifyPoisson(observedCasesYearInterval, expectedCasesYearInterval),standardizationProbabilities);
            int probabilityCategoryYearInterval = categorizeProbability(probabilityYearInterval);
            
            iteratorDistrict.getWindowIntervalResults().add(new DistrictWindowIntervalResult(iteratorYearWindow, iteratorYearInterval, (int)populationYearInterval, observedCasesYearInterval, expectedCasesYearInterval, incidenceYearInterval, smrYearInterval, smrSignificanceYearInterval, smrCategoryYearInterval, probabilityYearInterval, probabilityCategoryYearInterval));
            // Prepare line for year-interval.
            line = iteratorYearWindow + VS + iteratorYearInterval + VS + iteratorDistrict.getCode() + VS +
              (int)populationYearInterval + VS + observedCasesYearInterval + VS +
              elv.util.Util.toString(expectedCasesYearInterval) + VS + elv.util.Util.toString(incidenceYearInterval) + VS +
              elv.util.Util.toString(smrYearInterval) + VS + elv.util.Util.toString(smrSignificanceYearInterval) + VS +
              smrCategoryYearInterval + VS + elv.util.Util.toString(probabilityYearInterval) + VS + probabilityCategoryYearInterval;
            // Store line for year-interval.
            java.io.PrintWriter windowsIntervalsFileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(windowsIntervalsPathName, true), elv.util.Util.FILE_ENCODING));
            windowsIntervalsFileWriter.println(line);
            windowsIntervalsFileWriter.close();
          }
          execution.getProgresses().peek().setValue(yearIntervalCount + 1);
        }
        execution.getProgresses().pop();
        execution.getProgresses().peek().setValue(yearWindowCount + 1);
      }
      execution.getProgresses().pop();
      
      double smrPeriod = 0;
      if(standardizationMethod.equals(STANDARDIZATIONS[DIRECT]))
      {
        smrPeriod = (benchCasesPeriod == 0 ? 0 : expectedCasesPeriod / benchCasesPeriod);
        expectedCasesPeriod = (smrPeriod == 0 ? 0 : observedCasesPeriod / smrPeriod);
      }
      else if(standardizationMethod.equals(STANDARDIZATIONS[INDIRECT]))
      {
        smrPeriod = (expectedCasesPeriod == 0 ? 0 : observedCasesPeriod / expectedCasesPeriod);
      }
      double incidencePeriod = (benchPopulationPeriod == 0 ? 0 : smrPeriod * benchCasesPeriod / benchPopulationPeriod * 1000);
      // Determine the significance, category and probability for year.
      int smrSignificancePeriod = signify(observedCasesPeriod, expectedCasesPeriod);
      int smrCategoryPeriod = categorize(smrPeriod, smrSignificancePeriod);
      double probabilityPeriod = computeProbability(signifyPoisson(observedCasesPeriod, expectedCasesPeriod),standardizationProbabilities);
      int probabilityCategoryPeriod = categorizeProbability(probabilityPeriod);
      double sQx = (double)sumX2 - (double)sumX * sumX / execution.getYears().size();
      double sQy = sumY2 - sumY * sumY / execution.getYears().size();
      double sP = sumXY - (double)sumX * sumY / execution.getYears().size();
      double trendPeriod = sP / sQx;
      java.lang.String trendSignificancePeriod = signify(sQx, sQy, sP, execution.getYears(), trendSignificances);
      double trendCorrelationPeriod = java.lang.Math.abs(sP / java.lang.Math.sqrt(java.lang.Math.abs(sQx * sQy)));
      int trendCategoryPeriod = categorizeTrend(trendPeriod);
      iteratorDistrict.setResult(new DistrictResult((int)populationPeriod, observedCasesPeriod, expectedCasesPeriod, incidencePeriod,
        smrPeriod, smrSignificancePeriod, smrCategoryPeriod, probabilityPeriod, probabilityCategoryPeriod, trendPeriod,
        trendSignificancePeriod, trendCorrelationPeriod, trendCategoryPeriod));
      // Prepare line for district.
      line = iteratorDistrict.getCode() + VS + (int)populationPeriod + VS +
        observedCasesPeriod + VS + elv.util.Util.toString(expectedCasesPeriod) + VS +
        elv.util.Util.toString(incidencePeriod) + VS + elv.util.Util.toString(smrPeriod) + VS +
        elv.util.Util.toString(smrSignificancePeriod) + VS + smrCategoryPeriod + VS +
        elv.util.Util.toString(probabilityPeriod) + VS + probabilityCategoryPeriod + VS +
        elv.util.Util.toString(trendPeriod) + VS + trendSignificancePeriod + VS +
        elv.util.Util.toString(trendCorrelationPeriod) + VS + trendCategoryPeriod;
      // Store line for year-interval.
      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
      fileWriter.println(line);
      fileWriter.close();
      
      execution.getProgresses().peek().setValue(districtCount + 1);
    }
    execution.getProgresses().pop();
    setDone(execution.getTask(), true);
    return true;
  }
 
  /**
   * Method for Poisson SMR significance calculating.
   * @param observedCases the analysed cases.
   * @param expectedCases the expected cases.
   * @return the significance of standardization.
   */
  protected static double signifyPoisson(int observedCases, double expectedCases)
  {
    return (observedCases == 0 || expectedCases == 0 ? 0 : ((double)observedCases - expectedCases)*((double)observedCases - expectedCases) / expectedCases);
  }
  
  /**
   * Method for Pearson SMR significance calculating.
   * @param observedCases the analysed cases.
   * @param totalCases the total cases.
   * @param population the analysed population.
   * @param population the total population.
   * @return the significance of standardization.
   */
  protected static double signifyPearson(int observedCases, int totalCases, int population, int totalPopulation)
  {
    double e1 = ((double)observedCases + population) * totalCases / (totalCases + totalPopulation);
    double e2 = ((double)observedCases + population) * totalPopulation / (totalCases + totalPopulation);
    double e3 = ((double)totalCases - observedCases + totalPopulation - population) *totalCases / (totalCases + totalPopulation);
    double e4 = ((double)totalCases - observedCases + totalPopulation - population) *totalPopulation / (totalCases + totalPopulation);
    
    return ((double)observedCases - e1) * ((double)observedCases - e1) / e1 +
      ((double)population - e2) * ((double)population - e2) / e2 +
      ((double)totalCases - observedCases - e3) * ((double)totalCases - observedCases - e3) / e3 +
      ((double)totalPopulation - population - e4) * ((double)totalPopulation - population - e4) / e4;
   }
  
  /**
   * Method for Likelyhood SMR significance calculating.
   * @param observedCases the analysed cases.
   * @param totalCases the total cases.
   * @param population the analysed population.
   * @param population the total population.
   * @return the significance of standardization.
   */
  protected static double signifyLikelyhood(int observedCases, int totalCases, int population, int totalPopulation)
  {
    double e1 = ((double)observedCases + population) * totalCases / (totalCases + totalPopulation);
    double e2 = ((double)observedCases + population) * totalPopulation / (totalCases + totalPopulation);
    double e3 = ((double)totalCases - observedCases + totalPopulation - population) *totalCases / (totalCases + totalPopulation);
    double e4 = ((double)totalCases - observedCases + totalPopulation - population) *totalPopulation / (totalCases + totalPopulation);
    
    return 2 * (observedCases * java.lang.Math.log(observedCases / e1) +
      population * java.lang.Math.log(population / e2) +
      (totalCases - observedCases) * java.lang.Math.log((totalCases - observedCases) / e3) +
      (totalPopulation - population) * java.lang.Math.log((totalPopulation - population) / e4));
    }
  
  /**
   * Method for calculating SMR significance.
   * @param observedCases the analysed cases.
   * @param expectedCases the expected cases.
   * @return the significance of standardization.
   */
  protected static int signify(int observedCases, double expectedCases)
  {
    double hi2 = signifyPoisson(observedCases, expectedCases);
    return (hi2 > 3.84 ? 1 : -1);
  }
  
  /**
   * Method for calculating SMR category.
   * @param smr the standard mortality/morbiditi ratio.
   * @param significance the significance of standardization.
   * @return the category of standardization.
   */
  protected static int categorize(double smr, int significance)
  {
    int category = 0;
    if(significance == 1) // Significant.
    {
      category = (smr > 1 ? 1 : 5); // High or low.
    }
    else // Non significant.
    {
      category = (smr > 1.1 ? 2 : (smr < 0.9 ? 4 : 3)); // High, low or medium.
    }
    return category;
  }
  
  /**
   * Method for calculating probability.
   * @param hi2 the significance of standardization.
   * @param sP a vector of standardization probabilities.
   * @return the probability of standardization.
   */
  protected static double computeProbability(double hi2, java.util.Vector<StandardizationProbability> sP)
  {
    double probability = 0;
    if(hi2 < sP.get(0).hi2) // The firt element is the minimum in hi2s.
    {
      probability = 1;
    }
    else if(hi2 > sP.get(sP.size() - 1).hi2) // The last element is the maximum in hi2s.
    {
      probability = 0;
    }
    else
    {
      int i = -1;
      for(StandardizationProbability iteratorSP : sP)
      {
        i++;
        if(hi2 < iteratorSP.hi2)
        {
          break;
        }
      }
      if(i == sP.size() - 1 && hi2 == sP.get(i).hi2)
      {
        probability = 1 - sP.get(i).probability;
      }
      else if(hi2 == sP.get(i - 1).hi2)
      {
        probability = 1 - sP.get(i - 1).probability;
      }
      else
      {
        probability = 1 - (sP.get(i - 1).probability + (sP.get(i).probability - sP.get(i - 1).probability) /
          (sP.get(i).hi2 - sP.get(i - 1).hi2) * (hi2 - sP.get(i - 1).hi2));
      }
    }
    return probability;
  }
  
  /**
   * Method for calculating probability.
   * @param hi2 the significance of standardization.
   * @param sP a vector of standardization probabilities.
   * @return the probability of standardization.
   */
  protected static java.lang.String computeProbabilityAsString(double hi2,  java.util.Vector<Standardization.StandardizationProbability> sP)
  {
    double probability = Standardization.computeProbability(hi2, sP);
    java.lang.String probabilityString = "= 0";
    if(probability == 1)
    {
      probabilityString = "= 1";
    }
    else if(probability == 0)
    {
      probabilityString = "= 0";
    }
    if(probability <= 0.01)
    {
      probabilityString = "<= 0.01";
    }
    else if(probability <= 0.05)
    {
      probabilityString = "<= 0.05";
    }
    else if(probability <= 0.1)
    {
      probabilityString = "> 0.05 <= 0.1";
    }
    else
    {
      probabilityString = "> 0.1";
    }
    return probabilityString;
  }
  
  /**
   * Method for calculating the category of probability.
   * @param probability the probability of standardization.
   * @return the category of probability.
   */
  protected static int categorizeProbability(double probability)
  {
    int category = 0;
    if(probability <= 0.01)
    {
      category = 1;
    }
    else if(probability <= 0.05)
    {
      category = 2;
    }
    else if(probability <= 0.1)
    {
      category = 3;
    }
    else if(probability <= 0.2)
    {
      category = 4;
    }
    else
    {
      category = 5;
    }
    return category;
  }
  
  /**
   * Method for calculating trend significance.
   * @param sQx the year correlation.
   * @param sQy the incidence correlation.
   * @param sP the mixed correlation.
   * @param years a vector of years.
   * @param trendSignificances a vector of trend significances.
   * @return the significance of standardization.
   */
  protected static java.lang.String signify(double sQx, double sQy, double sP, java.util.Vector<java.lang.Integer> years, java.util.Vector<TrendSignificance> trendSignificances)
  {
    java.lang.String significance = "= 0";
    if(years.size() > 2)	// Otherwise trend significance has no meaning.
    {
      int index = -1;
      for(TrendSignificance tS : trendSignificances)
      {
        index++;
        if(years.size() - 2 >= tS.year)
        {
          break;
        }
      }
      double a = sQy - sP * sP / sQx;
      double b = a / (years.size() - 2);
      double trend = sP / sQx;
      double T = java.lang.Math.abs(trend / java.lang.Math.sqrt(java.lang.Math.abs(b / sQx)));

      if(trend == 0)
      {
        significance = "= 0";
      }
      else if(T < trendSignificances.get(index).trend0_1)
      {
        significance = "> 0.1";
      }
      else if(T >= trendSignificances.get(index).trend0_1 && T < trendSignificances.get(index).trend0_05)
      {
        significance = "> 0.05 <= 0.1";
      }
      else if(T >= trendSignificances.get(index).trend0_05 && T < trendSignificances.get(index).trend0_01)
      {
        significance = "<= 0.05";
      }
      else if(T >= trendSignificances.get(index).trend0_01 && T < trendSignificances.get(index).trend0_001)
      {
        significance = "<= 0.01";
      }
      else
      {
        significance = "<= 0.001";
      }
    }
    return significance;
  }
  
  /**
   * Method for calculating the category of the trend.
   * @param trend the trend of standardization.
   * @return the category of trend.
   */
  protected static int categorizeTrend(double trend)
  {
    int category = 0;
    if(trend >= 0.25)
    {
      category = 1;
    }
    else if(trend < 0.25 && trend > 0.06)
    {
      category = 2;
    }
    else if(trend < 0.06 && trend > -0.06)
    {
      category=3;
    }
    else if(trend < -0.06 && trend >= -0.25)
    {
      category = 4;
    }
    else if(trend < -0.25)
    {
      category = 5;
    } 
    return category;
  }
  
}
