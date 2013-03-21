/*
* MortalitySelection.java
*/
package elv.task.executables;

/**
* Class for implementing the selection of mortality rough data.
* @author Elv
*/
public class MortalitySelection extends Selection
{

  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public MortalitySelection() throws java.lang.Exception
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
                java.lang.String settlementCode = iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL];
                if(!settlementCode.equals(previousCode))
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
                    " FROM mortality" +
                    " WHERE death_year = " + iteratorYear + 
                    (noGender ? "" : " AND gender = " + iteratorGender.getValue()) +
                    (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                    (noSettlements ? "" : "   AND effective_residence = " + settlementCode);
                    resultSet = statement.executeQuery(sqlString);
                    if(lineCount == 0)
                    {
                      // Prepare header.
                      java.lang.String header = "";
                      for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                      {
                        header += (i != 1 ? VS : "") + resultSet.getMetaData().getColumnName(i);
                        // Return, if execution was stopped.
                        if(!execution.isExecuted())
                        {
                          resultSet.close();
                          statement.close();
                          return false;
                        }
                      }
                      // Store header.
                      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                      fileWriter.println(header);
                      fileWriter.close();
                    }

                    while(resultSet.next())
                    {
                      lineCount++;
                      boolean found = false;
                      if(execution.getDiseases().isEmpty())
                      {
                        found = true;
                      }
                      else if(execution.getMortalities().isEmpty())
                      {
                        for(int i = 1; i <= 5; i++)
                        {
                          java.lang.String code = resultSet.getString("diagnosis_" + i);
                          for(elv.util.parameters.DiseaseDiagnosis iteratorDisease : execution.getDiseases())
                          {
                            if(iteratorDisease.isRealDiagnosis())
                            {
                              java.lang.String iteratorCode = null;
                              // Until 1995 the diseases were coded in the 9-th system.
                              if(iteratorYear <= 1995)
                              {
                                iteratorCode = iteratorDisease.getCodes()[1];
                              }
                              else
                              {
                                // Replace last caracter with "0", because this is right.
                                if(code.length() == 5)
                                {
                                  code = code.substring(0, 4) + "0";
                                }
                                // Replace the "not begening" "H" to ".", to match any caracter.
                                iteratorCode = iteratorDisease.getCodes()[0].substring(0,1) + iteratorDisease.getCodes()[0].substring(1).replace('H', '.');
                              }
                              if(code.matches(iteratorCode))
                              {
                                found = true;
                                break;
                              }
                            }
                            // Return, if execution was stopped.
                            if(!execution.isExecuted())
                            {
                              resultSet.close();
                              statement.close();
                              return false;
                            }
                          }
                        }
                      }                            
                      else
                      {
                        for(elv.util.parameters.MortalityDiagnosis iteratorMortality : execution.getMortalities())
                        {
                          java.lang.String code = resultSet.getString("diagnosis_" + iteratorMortality.getCodes()[0]);
                          for(elv.util.parameters.DiseaseDiagnosis iteratorDisease : execution.getDiseases())
                          {
                            if(iteratorDisease.isRealDiagnosis())
                            {
                              java.lang.String iteratorCode = null;
                              // Until 1995 the diseases were coded in the 9-th system.
                              if(iteratorYear <= 1995)
                              {
                                iteratorCode = iteratorDisease.getCodes()[1];
                              }
                              else
                              {
                                // Replace last caracter with "0", because this is right.
                                if(code.length() == 5)
                                {
                                  code = code.substring(0, 4) + "0";
                                }
                                // Replace the "not begening" "H" to ".", to match any caracter.
                                iteratorCode = iteratorDisease.getCodes()[0].substring(0,1) + iteratorDisease.getCodes()[0].substring(1).replace('H', '.');
                              }
                              if(code.matches(iteratorCode))
                              {
                                found = true;
                                break;
                              }
                            }
                            // Return, if execution was stopped.
                            if(!execution.isExecuted())
                            {
                              resultSet.close();
                              statement.close();
                              return false;
                            }
                          }
                          if(found)
                          {
                            break;
                          }
                        }
                      }
                      // Return, if execution was stopped.
                      if(!execution.isExecuted())
                      {
                        resultSet.close();
                        statement.close();
                        return false;
                      }
                      if(found)
                      {
                        // Prepare line.
                        java.lang.String line = "";
                        for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                        {
                          line += (i != 1 ? VS : "") + resultSet.getObject(i);
                          // Return, if execution was stopped.
                          if(!execution.isExecuted())
                          {
                            resultSet.close();
                            statement.close();
                            return false;
                          }
                        }
                        // Store line.
                        java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                        fileWriter.println(line);
                        fileWriter.close();
                      }
                    }
                    resultSet.close();
                  }
                }
                previousCode = settlementCode;
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
        } catch (java.sql.SQLException e)
        {
        }
        resultSet = null;
      }
      if (statement != null)
      {
        try
        {
          statement.close();
        } catch (java.sql.SQLException e)
        {
        }
        statement = null;
      }
    }
    return true;
  }

}
