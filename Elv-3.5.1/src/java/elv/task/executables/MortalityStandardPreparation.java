/*
 * MortalityStandardPreparation.java
 */
package elv.task.executables;

/**
 * Class for implementing the mortality preparation method for standardization.
 * @author Qpa
 */
public class MortalityStandardPreparation extends StandardPreparation
{
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public MortalityStandardPreparation() throws java.lang.Exception
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
    java.util.Vector<SettlementYearResult> settlementYearResults = new java.util.Vector<SettlementYearResult>();
    
    boolean noAgeIntervals = false;
    if(execution.getAgeIntervals().isEmpty())
    {
      elv.util.parameters.AgeInterval ageInterval = new elv.util.parameters.AgeInterval().getDefault();
      execution.getAgeIntervals().add(ageInterval);
      noAgeIntervals = true;
    }
    boolean allGenders = false;
    if(genders == null || genders.isEmpty() || genders.size() == 2)
    {
      allGenders = true;
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

        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.AgeInterval.TITLE, 0, execution.getAgeIntervals().size()));
        for(int ageIntervalCount = 0; ageIntervalCount < execution.getAgeIntervals().size(); ageIntervalCount++)
        {
          elv.util.parameters.Interval iteratorAgeInterval = execution.getAgeIntervals().get(ageIntervalCount);

          int population = 0;
          int totalCases = 0;
          int analyzedCases = 0;

          java.util.Vector<elv.util.parameters.Settlement> parsedSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Settlement.TITLE, 0, execution.getAllSettlements().size()));
          for(int settlementCount = 0; settlementCount < execution.getAllSettlements().size(); settlementCount++)
          {
            elv.util.parameters.Settlement iteratorSettlement = execution.getAllSettlements().get(settlementCount);

            if(iteratorSettlement.isRealSettlement())
            {
              // Determine if this settlement was parsed.
              boolean parsed = false;
              for(elv.util.parameters.Settlement iteratorParsedSettlement : parsedSettlements)
              {
                if(iteratorParsedSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]))
                {
                  parsed = true;
                  break;
                }
              }
              if(!parsed)
              {
                parsedSettlements.add(iteratorSettlement);
                if(fileReader != null && (line = fileReader.readLine()) != null)
                {
                  lineCount++;
                  java.lang.String expectedLine = iteratorYear + VS + iteratorAgeInterval + VS + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL];
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
                  population = java.lang.Integer.parseInt(sT.nextToken());
                  totalCases = java.lang.Integer.parseInt(sT.nextToken());
                  analyzedCases = java.lang.Integer.parseInt(sT.nextToken());
                  iteratorSettlement.getYearResults().add(new SettlementYearResult(iteratorYear, iteratorAgeInterval, population, totalCases, analyzedCases));
                }
                else
                {
                  if(fileReader != null)
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

                  // Select population.
                  java.lang.String sqlString =
                  "SELECT SUM(population) FROM population" +
                  " WHERE year = " + iteratorYear + 
                  (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                  (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                  "   AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL];
                  resultSet = statement.executeQuery(sqlString);
                  resultSet.next();
                  population = resultSet.getInt(1);
                  resultSet.close();

                  // Select analyzedCases.
                  sqlString =
                  "SELECT diagnosis_1, diagnosis_2, diagnosis_3, diagnosis_4, diagnosis_5" +
                  " FROM mortality" +
                  " WHERE death_year = " + iteratorYear + 
                  (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                  (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                  "   AND effective_residence = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL];
                  resultSet = statement.executeQuery(sqlString);
                  totalCases = 0;
                  analyzedCases = 0;
                  while(resultSet.next())
                  {
                    totalCases++;
                    boolean found = false;
                    if(execution.getDiseases().isEmpty())
                    {
                      analyzedCases++;
                      // Return, if execution was stopped.
                      if(!execution.isExecuted())
                      {
                        resultSet.close();
                        statement.close();
                        return false;
                      }
                    }
                    else if(execution.getMortalities().isEmpty())
                    {
                      for(int i = 1; i <= 5; i++)
                      {
                        java.lang.String code = resultSet.getString(i);
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
                              analyzedCases++;
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
                        java.lang.String code = resultSet.getString(java.lang.Integer.parseInt(iteratorMortality.getCodes()[0]));
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
                              analyzedCases++;
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
                  }
                  resultSet.close();
                  // Prepare line.
                  line = iteratorYear + VS + iteratorAgeInterval + VS + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL] + VS +
                         population + VS + totalCases + VS + analyzedCases;
                  // Store line.
                  java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                  fileWriter.println(line);
                  fileWriter.close();
                  iteratorSettlement.getYearResults().add(new SettlementYearResult(iteratorYear, iteratorAgeInterval, population, totalCases, analyzedCases));
                }
              }
            }
            execution.getProgresses().peek().setValue(settlementCount + 1);
            java.lang.Thread.yield();
          }
          execution.getProgresses().pop();
          execution.getProgresses().peek().setValue(ageIntervalCount + 1);
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
