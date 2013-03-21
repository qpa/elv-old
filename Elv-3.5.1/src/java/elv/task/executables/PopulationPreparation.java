/*
 * PopulationPreparation.java
 */
package elv.task.executables;

/**
 * Class for implementing the population preparation.
 * @author Qpa
 */
public class PopulationPreparation extends Preparation
{
  
  /**
   * Constant.
   */
  public final static java.lang.String HEADER = "Year" + VS + 
    "Gender" + VS + "Age interval" + VS + "District code" + VS + "District name" + VS + "Population";
    
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public PopulationPreparation() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.GENDER_ARRAY, elv.util.Property.LIST_BUTTON, true, GENDERS_NAME, elv.util.parameters.Gender.getAllGenders()));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(GENDERS_NAME, properties).setDefaultValues(elv.util.parameters.Gender.getAllGenders());
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    java.lang.String pathName = execution.getTask().getExecutionFolderPath() + elv.util.Util.getFS() + getExecutionFiles().get(PREPARATION);
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
      if(!execution.isExecuted())
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
    java.util.Vector<elv.util.parameters.Gender> genders = (java.util.Vector<elv.util.parameters.Gender>)elv.util.Property.get(GENDERS_NAME, properties).getValue();
    
    boolean noGender = false;
    if(genders == null || genders.isEmpty())
    {
      genders = new java.util.Vector<elv.util.parameters.Gender>();
      genders.add(elv.util.parameters.Gender.getDefault());
      noGender = true;
    }
    boolean noAgeIntervals = false;
    if(execution.getAgeIntervals().isEmpty())
    {
      elv.util.parameters.AgeInterval ageInterval = new elv.util.parameters.AgeInterval().getDefault();
      execution.getAgeIntervals().add(ageInterval);
      noAgeIntervals = true;
    }
    boolean noSettlements = false;
    if(execution.getBaseSettlements().isEmpty())
    {
      elv.util.parameters.BaseSettlement settlement = new elv.util.parameters.BaseSettlement().getDefault();
      execution.getBaseSettlements().add(settlement);
      elv.util.parameters.District district = new elv.util.parameters.District().getDefault();
      execution.getDistricts().add(district);
      noSettlements = true;
    }
    
    java.lang.String line;
    int lineCount = 1; // Due to the header line;
    
    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    try
    {
      statement = elv.util.server.Servlet.getDataBaseConnection().createStatement();
      // Parse.
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, execution.getYears().size()));
      for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
      {
        int iteratorYear = execution.getYears().get(yearCount);

        execution.getProgresses().push(new elv.util.Progress(GENDERS_NAME, 0, genders.size()));
        for(int genderCount = 0; genderCount < genders.size(); genderCount ++)
        {
          elv.util.parameters.Gender iteratorGender = genders.get(genderCount);

          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.AgeInterval.TITLE, 0, execution.getAgeIntervals().size()));
          for(int ageIntervalCount = 0; ageIntervalCount < execution.getAgeIntervals().size(); ageIntervalCount++)
          {
            elv.util.parameters.Interval iteratorAgeInterval = execution.getAgeIntervals().get(ageIntervalCount);

            execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.District.TITLE, 0, execution.getDistricts().size()));
            for(int districtCount = 0; districtCount < execution.getDistricts().size(); districtCount++)
            {
              elv.util.parameters.District iteratorDistrict = execution.getDistricts().get(districtCount);
              int population = 0;

              if(fileReader != null && (line = fileReader.readLine()) != null)
              {
                lineCount++;
                java.lang.String expectedLine = iteratorYear + VS + iteratorGender + VS + iteratorAgeInterval + VS + iteratorDistrict.getCode() + VS + iteratorDistrict.getName();
                java.lang.String observedLine = "";
                java.util.StringTokenizer sT = new java.util.StringTokenizer(line, VS);
                observedLine = sT.nextToken();
                for(int i = 0; i < 4; i++)
                {
                  observedLine += VS + sT.nextToken();
                }
                if(!observedLine.equals(expectedLine))
                {
                  throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
                }
                population = java.lang.Integer.parseInt(sT.nextToken());
              }
              else
              {
                if(fileReader!= null)
                {
                  fileReader.close();
                  // Return after load, if it is just progresses loading.
                  if(!execution.isExecuted())
                  {
                    statement.close();
                    return false;
                  }
                }
                fileReader = null;

                java.util.Vector<elv.util.parameters.Settlement> districtSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
                for(int settlementCount = 0; settlementCount < execution.getBaseSettlements().size(); settlementCount++)
                {
                  elv.util.parameters.Settlement iteratorSettlement = execution.getBaseSettlements().get(settlementCount);

                  if(iteratorSettlement.isRealSettlement() && iteratorSettlement.getDistrictCode() == iteratorDistrict.getCode())
                  {
                    // Determine if this settlement was parsed.
                    boolean parsed = false;
                    for(elv.util.parameters.Settlement iteratorDistrictSettlement : districtSettlements)
                    {
                      if(iteratorDistrictSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]))
                      {
                        parsed = true;
                        break;
                      }
                      // Return, if execution was stopped.
                      if(!execution.isExecuted())
                      {
                        statement.close();
                        return false;
                      }
                    }
                    if(!parsed)
                    {
                      // Select population.
                      java.lang.String sqlString =
                      "SELECT SUM(population) FROM population" + 
                      " WHERE year = " + iteratorYear +
                      (noGender ? "" : " AND gender = " + iteratorGender.getValue()) +
                      (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                      (noSettlements ? "" : " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
                      resultSet = statement.executeQuery(sqlString);
                      resultSet.next();
                      population += resultSet.getInt(1);
                      resultSet.close();
                      districtSettlements.add(iteratorSettlement);
                      // Return, if execution was stopped.
                      if(!execution.isExecuted())
                      {
                        statement.close();
                        return false;
                      }
                    }
                  }
                  java.lang.Thread.yield();
                }
                // Prepare line.
                line = iteratorYear + VS + iteratorGender + VS + iteratorAgeInterval + VS + iteratorDistrict.getCode() + VS + iteratorDistrict.getName() + VS + population;
                // Store line.
                java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                fileWriter.println(line);
                fileWriter.close();
              }
              execution.getProgresses().peek().setValue(districtCount + 1);
            }
            execution.getProgresses().pop();
            execution.getProgresses().peek().setValue(ageIntervalCount + 1);
          }
          execution.getProgresses().pop();
          execution.getProgresses().peek().setValue(genderCount + 1);
        }
        execution.getProgresses().pop();
        execution.getProgresses().peek().setValue(yearCount + 1);
      }
      execution.getProgresses().pop();

      statement.close();
      setDone(execution.getTask(), true);
    }
    finally
    {
      // Always make sure result sets and statements are closed.
      if (resultSet != null)
      {
        try
        {
          resultSet.close();
        }
        catch (java.sql.SQLException e)
        {
        }
        resultSet = null;
      }
      if (statement != null)
      {
        try
        {
          statement.close();
        }
        catch (java.sql.SQLException e)
        {
        }
        statement = null;
      }
    }
    return true;
  }
  
}
