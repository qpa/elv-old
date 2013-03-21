/*
 * MortalityStandardPreparation.java
 */
package elv.task.executables;

/**
 * Class for implementing the mortality preparation method for standardization.
 * @author Elv
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
    // Initialize.
    java.util.Vector<elv.util.parameters.Gender> genders = (java.util.Vector<elv.util.parameters.Gender>)elv.util.Property.get(GENDERS_NAME, properties).getValue();
    
    java.lang.String pathName = execution.getTask().getExecutionFolderPath() + "/" + getExecutionFiles()[PREPARATION].getFileName();
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
    
    boolean allGenders = false;
    if(genders == null || genders.isEmpty() || genders.size() == 2)
    {
      allGenders = true;
    }
    
    boolean noAgeIntervals = false;
    if(execution.getAgeIntervals().isEmpty())
    {
      elv.util.parameters.AgeInterval ageInterval = new elv.util.parameters.AgeInterval().getDefault();
      execution.getAgeIntervals().add(ageInterval);
      noAgeIntervals = true;
    }
    
    boolean noDiseases = false;
    if(execution.getDiseases().isEmpty())
    {
      elv.util.parameters.DiseaseDiagnosis disease = new elv.util.parameters.DiseaseDiagnosis().getDefault();
      execution.getDiseases().add(disease);
      noDiseases = true;
    }
    
    java.lang.String line;
    int lineCount = 1; // Due to the header line;
    
    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    try
    {
      statement = elv.util.server.ServerStub.getDataBaseConnection().createStatement();
      // Parse.
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, execution.getYears().size()));
      for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
      {
        int iteratorYear = execution.getYears().get(yearCount);

        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.AgeInterval.TITLE, 0, execution.getAgeIntervals().size()));
        for(int ageIntervalCount = 0; ageIntervalCount < execution.getAgeIntervals().size(); ageIntervalCount++)
        {
          elv.util.parameters.Interval iteratorAgeInterval = execution.getAgeIntervals().get(ageIntervalCount);

          java.util.Vector<elv.util.parameters.Settlement> parsedSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
          java.util.Vector<elv.util.parameters.Settlement> parsedBenchmarkSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Settlement.TITLE, 0, execution.getAllSettlements().size()));
          for(int settlementCount = 0; settlementCount < execution.getAllSettlements().size(); settlementCount++)
          {
            elv.util.parameters.Settlement iteratorSettlement = execution.getAllSettlements().get(settlementCount);

            int population = 0;
            int totalCases = 0;
            int analyzedCases = 0;

            if(iteratorSettlement.isRealSettlement())
            {
              // Determine if this settlement was parsed.
              boolean settlementParsed = false;
              for(elv.util.parameters.Settlement iteratorParsedSettlement : parsedSettlements)
              {
                if(iteratorParsedSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]))
                {
                  settlementParsed = true;
                  // Determine if this benchmark settlement was added.
                  if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                  {
                    boolean benchmarkSettlementParsed = false;
                    for(elv.util.parameters.Settlement iteratorParsedBenchmarkSettlement : parsedBenchmarkSettlements)
                    {
                      if(iteratorParsedBenchmarkSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]))
                      {
                        benchmarkSettlementParsed = true;
                        break;
                      }
                    }
                    if(!benchmarkSettlementParsed)
                    {
                      iteratorSettlement.setYearResults(iteratorParsedSettlement.getYearResults());
                      parsedBenchmarkSettlements.add(iteratorSettlement);
                    }
                  }
                  break;
                }
              }
              if(!settlementParsed)
              {
                parsedSettlements.add(iteratorSettlement);
                if(fileReader != null && (line = fileReader.readLine()) != null)
                {
                  lineCount++;
                  java.lang.String expectedLine = iteratorYear + VS + iteratorAgeInterval + VS + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL];
                  java.lang.String[] splitedLine = line.split(java.util.regex.Pattern.quote(VS));
                  java.lang.String observedLine = splitedLine[0] + VS + splitedLine[1] + VS + splitedLine[2];
                  if(!observedLine.equals(expectedLine))
                  {
                    throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
                  }
                  population = java.lang.Integer.parseInt(splitedLine[3]);
                  totalCases = java.lang.Integer.parseInt(splitedLine[4]);
                  analyzedCases = java.lang.Integer.parseInt(splitedLine[5]);
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
                  "SELECT SUM(population)" +
                  " FROM population" +
                  " WHERE year = " + iteratorYear + 
                  (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                  (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                  (iteratorSettlement.equals(new elv.util.parameters.BenchmarkSettlement().getDefault()) ? "" : "   AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
                  resultSet = statement.executeQuery(sqlString);
                  resultSet.next();
                  population = resultSet.getInt(1);
                  resultSet.close();

                  // Select totalCases.
                  sqlString =
                  "SELECT COUNT(*)" +
                  " FROM mortality" +
                  " WHERE death_year = " + iteratorYear +
                  (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                  (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                  (iteratorSettlement.equals(new elv.util.parameters.BenchmarkSettlement().getDefault()) ? "" : " AND effective_residence = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
                  resultSet = statement.executeQuery(sqlString);
                  resultSet.next();
                  totalCases = resultSet.getInt(1);
                  resultSet.close();
                  
                  // Select analyzedCases.
                  java.util.Vector<java.lang.String> parsedDiseaseCodes = new java.util.Vector<java.lang.String>();
                  int firstLevel = execution.getDiseases().get(0).getParagraphLevel();
                  for(elv.util.parameters.DiseaseDiagnosis iteratorDisease : execution.getDiseases())
                  {
                    if((iteratorYear <= 1995 && iteratorDisease.isRealDiagnosis()) ||
                      (iteratorYear > 1995 && iteratorDisease.getParagraphLevel() == firstLevel))
                    {
                      java.lang.String iteratorDiseaseCode = null;
                      // Until 1995 the diseases were coded in the 9-th system.
                      if(iteratorYear <= 1995)
                      {
                        iteratorDiseaseCode = iteratorDisease.getCodes()[1];
                      }
                      else
                      {
                        iteratorDiseaseCode = iteratorDisease.getCodes()[0];
                      }
                      
                      // Determine if this disease code was parsed.
                      boolean diseaseParsed = false;
                      for(java.lang.String iteratorParsedDiseaseCode : parsedDiseaseCodes)
                      {
                        if(iteratorParsedDiseaseCode.equals(iteratorDiseaseCode))
                        {
                          diseaseParsed = true;
                          break;
                        }
                      }
                      if(!diseaseParsed)
                      {
                        parsedDiseaseCodes.add(iteratorDiseaseCode);
                        
                        java.lang.String diseasesClause = "";
                        if(iteratorYear <= 1995)
                        {
                          for(elv.util.parameters.MortalityDiagnosis iteratorMortality : execution.getMortalities())
                          {
                            java.lang.String iteratorMortalityCode = iteratorMortality.getCodes()[0];
                            diseasesClause += (diseasesClause.equals("") ?
                              " AND ('" + iteratorDiseaseCode + "' LIKE diagnosis_" + iteratorMortalityCode :
                              " OR '" + iteratorDiseaseCode + "' LIKE diagnosis_" + iteratorMortalityCode);
                          }
                        }
                        else
                        {
                          if(iteratorDisease.isRealDiagnosis())
                          {
                            int indexOfH = 3;
                            if(iteratorDiseaseCode.charAt(indexOfH) == 'H') // Codes like W01H0 are not completed properly
                            {
                              for(elv.util.parameters.MortalityDiagnosis iteratorMortality : execution.getMortalities())
                              {
                                java.lang.String code = iteratorDiseaseCode.substring(0, indexOfH);
                                java.lang.String iteratorMortalityCode = iteratorMortality.getCodes()[0];
                                diseasesClause += (diseasesClause.equals("") ?
                                  " AND (SUBSTRING(diagnosis_" + iteratorMortalityCode + " FROM 1 FOR " + code.length() + ") = '" + code + "'" :
                                  " OR SUBSTRING(diagnosis_" + iteratorMortalityCode + " FROM 1 FOR " + code.length() + ") = '" + code + "'");
                              }
                            }
                            else
                            {
                              for(elv.util.parameters.MortalityDiagnosis iteratorMortality : execution.getMortalities())
                              {
                                java.lang.String iteratorMortalityCode = iteratorMortality.getCodes()[0];
                                diseasesClause += (diseasesClause.equals("") ?
                                  " AND ('" + iteratorDiseaseCode + "' LIKE diagnosis_" + iteratorMortalityCode :
                                  " OR '" + iteratorDiseaseCode + "' LIKE diagnosis_" + iteratorMortalityCode);
                              }
                            }
                          }
                          else
                          {
                            java.lang.String[] codes = iteratorDiseaseCode.split(java.util.regex.Pattern.quote("-"));
                            java.lang.String fromCode = codes[0];
                            java.lang.String toCode = (codes.length == 1 ? codes[0] : codes[1]);
                            for(elv.util.parameters.MortalityDiagnosis iteratorMortality : execution.getMortalities())
                            {
                              java.lang.String iteratorMortalityCode = iteratorMortality.getCodes()[0];
                              diseasesClause += (diseasesClause.equals("") ?
                                " AND (SUBSTRING(diagnosis_" + iteratorMortalityCode + " FROM 1 FOR " + fromCode.length() + ") BETWEEN '" + fromCode +"' AND '" + toCode + "'" :
                                " OR SUBSTRING(diagnosis_" + iteratorMortalityCode + " FROM 1 FOR " + fromCode.length() + ") BETWEEN '" + fromCode +"' AND '" + toCode + "'");
                            }
                          }
                        }
                        diseasesClause += ")";
                        // Select analyzedCases.
                        sqlString =
                        "SELECT COUNT(*)" +
                        " FROM mortality" +
                        " WHERE death_year = " + iteratorYear +
                        (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                        (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                        (iteratorSettlement.equals(new elv.util.parameters.BenchmarkSettlement().getDefault()) ? "" : " AND effective_residence = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]) +
                        (noDiseases ? "" : diseasesClause);
                        resultSet = statement.executeQuery(sqlString);
                        resultSet.next();
                        analyzedCases += resultSet.getInt(1);
                        resultSet.close();

                        java.lang.Thread.yield();
                        // Return, if execution was stopped.
                        if(!execution.isExecuted())
                        {
                          statement.close();
                          return false;
                        }
                      }
                    }
                  }
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
