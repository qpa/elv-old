/*
 * MorbiditySelection.java
 */
package elv.task.executables;

/**
 * Class for implementing the selection of morbidity rough data.
 * @author Elv
 */
public class MorbiditySelection extends Selection
{
  
   /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public MorbiditySelection() throws java.lang.Exception
  {
    super();
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
    java.util.Vector<elv.util.parameters.Gender> genders = (java.util.Vector<elv.util.parameters.Gender>)elv.util.Property.get(GENDERS_NAME, properties).getValue();
  
    int lineCount = 0;
    java.lang.String previousCode = "";
    java.lang.String pathName = execution.getTask().getExecutionFolderPath() + "/" + getExecutionFiles()[SELECTION].getFileName();
    java.io.BufferedReader fileReader = null;
    try
    {
      fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(pathName), elv.util.Util.FILE_ENCODING));
      // Throw header line.
      fileReader.readLine();
    }
    catch(java.io.FileNotFoundException exc)
    {
      fileReader = null;
      
      // Return, if this is not an execution.
      elv.util.State state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, execution.getTask().getProperties()).getValue();
      if(!state.equals(new elv.util.State(elv.util.State.EXECUTED)))
      {
        return false;
      }
    }
    
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
      noSettlements = true;
    }

    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    try
    {
      statement = elv.util.server.ServerStub.getDataBaseConnection().createStatement();

      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, execution.getYears().size()));
      for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
      {
        int iteratorYear = execution.getYears().get(yearCount);
        execution.getProgresses().push(new elv.util.Progress(GENDERS_NAME, 0, genders.size()));
        for(int genderCount = 0; genderCount < genders.size(); genderCount++)
        {
          elv.util.parameters.Gender iteratorGender = genders.get(genderCount);

          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.AgeInterval.TITLE, 0, execution.getAgeIntervals().size()));
          for(int ageIntervalCount = 0; ageIntervalCount < execution.getAgeIntervals().size(); ageIntervalCount++)
          {
            elv.util.parameters.Interval iteratorAgeInterval = execution.getAgeIntervals().get(ageIntervalCount);

            execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Settlement.TITLE, 0, execution.getBaseSettlements().size()));
            for(int settlementCount = 0; settlementCount < execution.getBaseSettlements().size(); settlementCount++)
            {
              elv.util.parameters.Settlement iteratorSettlement = execution.getBaseSettlements().get(settlementCount);
              if(iteratorSettlement.isRealSettlement())
              {
                java.lang.String code = iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL];
                if(!code.equals(previousCode))
                {
                  if(fileReader != null && fileReader.readLine() != null)
                  {
                    continue;
                  }
                  else
                  {
                    if(fileReader!= null)
                    {
                      fileReader.close();
                      // Return after load, if this is not an execution.
                      elv.util.State state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, execution.getTask().getProperties()).getValue();
                      if(!state.equals(new elv.util.State(elv.util.State.EXECUTED)))
                      {
                        statement.close();
                        return false;
                      }
                    }
                    fileReader = null;

                    java.lang.String sqlString =
                    "SELECT *" +
                    " FROM morbidity" +
                    " WHERE death_year = " + iteratorYear + 
                    (noGender ? "" : " AND gender = " + iteratorGender.getValue()) +
                    (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                    (noSettlements ? "" : " AND settlement = " + code);
                    resultSet = statement.executeQuery(sqlString);
                    while(resultSet.next())
                    {
                      lineCount++;
                      java.lang.String header = "";
                      java.lang.String line = "";
                      // Prepare line and header.
                      for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                      {
                        if(lineCount == 1)
                        {
                         header += (i != 1 ? VS : "") + resultSet.getMetaData().getColumnName(i);
                        }
                        line += (i != 1 ? VS : "") + resultSet.getObject(i);
                        // Return, if execution was stopped.
                        if(!execution.isExecuted())
                        {
                          resultSet.close();
                          statement.close();
                          return false;
                        }
                      }
                      if(lineCount == 1)
                      {
                        // Store header.
                        java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                        fileWriter.println(header);
                        fileWriter.close();
                      }
                      // Store line.
                      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                      fileWriter.println(line);
                      fileWriter.close();
                    }
                    resultSet.close();
                  }
                }
                previousCode = code;
              }
              // Return, if execution was stopped.
              if(!execution.isExecuted())
              {
                statement.close();
                return false;
              }
              execution.getProgresses().peek().setValue(settlementCount + 1);
              java.lang.Thread.yield();
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
