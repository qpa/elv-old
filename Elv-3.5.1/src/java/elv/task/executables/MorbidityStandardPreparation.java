/*
 * MorbidityStandardPreparation.java
 */
package elv.task.executables;

/**
 * Class for implementing the morbidity preparation method for standardization.
 * @author Qpa
 */
public class MorbidityStandardPreparation extends StandardPreparation
{
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public MorbidityStandardPreparation() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.StandardPreparation</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.GENDER_ARRAY, elv.util.Property.LIST_BUTTON, true, GENDERS_NAME, elv.util.parameters.Gender.getAllGenders()));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, SELECTION_NAME, SELECTIONS[NOT_DISTINCT]));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.COMBO_BOX, false, BENCHMARK_YEAR_NAME, 0));
  }
  
  /**
   * Overridden method from <CODE>elv.task.executables.StandardPreparation</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(GENDERS_NAME, properties).setDefaultValues(elv.util.parameters.Gender.getAllGenders());
    elv.util.Property.get(SELECTION_NAME, properties).setDefaultValues(elv.util.Util.vectorize(SELECTIONS));
    elv.util.Property.get(BENCHMARK_YEAR_NAME, properties).setDefaultValues(elv.util.parameters.YearInterval.getAllYears());
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
    java.lang.String selection = (java.lang.String)elv.util.Property.get(SELECTION_NAME, properties).getValue();
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
    
    int year = 0;
    elv.util.parameters.Interval ageInterval = null;
    elv.util.parameters.Settlement place = null;
    int population = 0;
    int totalCases = 0;
    int analyzedCases = 0;
    
    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    try
    {
      statement = elv.util.server.Servlet.getDataBaseConnection().createStatement();
      int settlementListCount = -1;
      java.util.Vector<java.util.Vector<java.lang.String>> parsedCodes = new java.util.Vector<java.util.Vector<java.lang.String>>();
       
      // Parse.
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, execution.getYears().size()));
      for(int yearCount = 0; yearCount < execution.getYears().size(); yearCount++)
      {
        int iteratorYear = execution.getYears().get(yearCount);

        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.AgeInterval.TITLE, 0, execution.getAgeIntervals().size()));
        for(int ageIntervalCount = 0; ageIntervalCount < execution.getAgeIntervals().size(); ageIntervalCount++)
        {
          elv.util.parameters.Interval iteratorAgeInterval = execution.getAgeIntervals().get(ageIntervalCount);
          settlementListCount++;
          java.util.Vector<elv.util.parameters.Settlement> placeSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Settlement.TITLE, 0, execution.getAllSettlements().size()));
          for(int settlementCount = 0; settlementCount < execution.getAllSettlements().size(); settlementCount++)
          {
            elv.util.parameters.Settlement iteratorSettlement = execution.getAllSettlements().get(settlementCount);
            if(settlementListCount == 0)
            {
              parsedCodes.add(new java.util.Vector<java.lang.String>());
            }
            if(iteratorSettlement.isPlace())
            {
              if(fileReader != null && (line = fileReader.readLine()) != null)
              {
                lineCount++;
                java.lang.String expectedLine = iteratorYear + VS + iteratorAgeInterval + VS + iteratorSettlement.getCodes()[0];
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
                iteratorSettlement.getYearResults().add(new SettlementYearResult(iteratorYear, iteratorAgeInterval,population, totalCases, analyzedCases));
                continue;
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

                if(place != null)
                {
                  // Prepare line.
                  line = year + VS + ageInterval + VS + place.getCodes()[0] + VS + place.getName() + VS +
                         population + VS + totalCases + VS + analyzedCases;
                  // Store line.
                  java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                  fileWriter.println(line);
                  fileWriter.close();
                  iteratorSettlement.getYearResults().add(new SettlementYearResult(year, ageInterval, population, totalCases, analyzedCases));
                  // Reset counters.
                  year = 0;
                  ageInterval = null;
                  population = 0;
                  totalCases = 0;
                  analyzedCases = 0;
                  placeSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
                }
              }
              place = iteratorSettlement;
            }

            if(iteratorSettlement.isRealSettlement() && fileReader == null)
            {
              // Determine if this settlement was parsed.
              boolean statisticalParsed = false;
              boolean postalParsed = false;
              for(int placeSettlementCount = 0; placeSettlementCount < placeSettlements.size(); placeSettlementCount++)
              {
                elv.util.parameters.Settlement iteratorPlaceSettlement = placeSettlements.get(placeSettlementCount);
                if(iteratorPlaceSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]))
                {
                  statisticalParsed = true;
                }
                if(iteratorPlaceSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL]))
                {
                  postalParsed = true;
                }
                if(statisticalParsed && postalParsed)
                {
                  break;
                }
                // Return, if execution was stopped.
                if(!execution.isExecuted())
                {
                  statement.close();
                  return false;
                }
              }
              if(!statisticalParsed)
              {
                // Select population.
                java.lang.String sqlString =
                "SELECT SUM(population) FROM population" +
                " WHERE year = "  + iteratorYear +
                (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL];
                resultSet = statement.executeQuery(sqlString);
                resultSet.next();
                population += resultSet.getInt(1);
                resultSet.close();
              }

              if(!postalParsed)
              {
                // Select analyzedCases.
                java.lang.String sqlString =
                "SELECT diagnosis_type_1, diagnosis_type_2, diagnosis_type_3, diagnosis_type_4, diagnosis_type_5, diagnosis_type_6, diagnosis_type_7, diagnosis_type_8," +
                " diagnosis_type_9, diagnosis_type_10, diagnosis_type_11, diagnosis_type_12, diagnosis_type_13, diagnosis_type_14, diagnosis_type_15, diagnosis_type_16," +
                " diagnosis_1, diagnosis_2, diagnosis_3, diagnosis_4, diagnosis_5, diagnosis_6, diagnosis_7, diagnosis_8," +
                " diagnosis_9, diagnosis_10, diagnosis_11, diagnosis_12, diagnosis_13, diagnosis_14, diagnosis_15, diagnosis_16," +
                " addmission_type, insurance_code" +
                " FROM morbidity" +
                " WHERE addmission_year = "  + iteratorYear +
                (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL];
                resultSet = statement.executeQuery(sqlString);
                while(resultSet.next())
                {
                  totalCases++;
                  boolean found = false;
                  boolean isParsable = true;
                  java.lang.String parsedCode = "";
                  if(selection.equals(SELECTIONS[NOT_DISTINCT]))
                  {
                    isParsable = true;
                  }
                  else if(selection.equals(SELECTIONS[DISTINCT]))
                  {
                    java.lang.String insuranceCode = resultSet.getString("insurance_code");
                    java.lang.String diseaseCode = resultSet.getString("diagnosis_1");
                    parsedCode = insuranceCode.concat(diseaseCode);
                    for(java.lang.String iteratorCode : parsedCodes.get(settlementCount))
                    {
                      if(iteratorCode.equals(parsedCode))
                      {
                        isParsable = false;
                        break;
                      }
                    }
                  }

                  if(isParsable)
                  {
                    if(execution.getAddmissions().isEmpty())
                    {
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
                      else if(execution.getMorbidities().isEmpty())
                      {
                        for(int i = 1; i <= 16; i++)
                        {
                          java.lang.String type_code = resultSet.getString(i);
                          java.lang.String code = resultSet.getString(i + 16);
                          if(type_code.equals("M")) // Morfology.
                          {
                            for(elv.util.parameters.MorfologyDiagnosis iteratorMorfology : execution.getMorfologies())
                            {
                              if(iteratorMorfology.isRealDiagnosis())
                              {
                                // Extract the "M" and the "/" from morfology code.
                                java.util.StringTokenizer sT = new java.util.StringTokenizer(iteratorMorfology.getCodes()[0].substring(1), "/");
                                java.lang.String iteratorCode = sT.nextToken() + sT.nextToken();
                                if(iteratorCode.equals(code))
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
                          else // Disease.
                          {
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
                          if(found)
                          {
                            break;
                          }
                        }
                      }
                      else
                      {
                        for(elv.util.parameters.MorbidityDiagnosis iteratorMorbidity : execution.getMorbidities())
                        {
                          for(int i = 1; i <= 16; i++)
                          {
                            java.lang.String type_code = resultSet.getString(i);
                            java.lang.String code = resultSet.getString(i + 16);
                            if(iteratorMorbidity.getCodes()[0].equals(type_code))
                            {
                              if(type_code.equals("M")) // Morfology.
                              {
                                for(elv.util.parameters.MorfologyDiagnosis iteratorMorfology : execution.getMorfologies())
                                {
                                  if(iteratorMorfology.isRealDiagnosis())
                                  {
                                    // Extract the "M" and the "/" from morfology code.
                                    java.util.StringTokenizer sT = new java.util.StringTokenizer(iteratorMorfology.getCodes()[0].substring(1), "/");
                                    java.lang.String iteratorCode = sT.nextToken() + sT.nextToken();
                                    if(iteratorCode.equals(code))
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
                              else // Disease.
                              {
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
                            if(found)
                            {
                              break;
                            }
                          }
                          if(found)
                          {
                            break;
                          }
                        }
                      }
                    }
                    else
                    {
                      for(elv.util.parameters.AddmissionDiagnosis iteratorAddmission : execution.getAddmissions())
                      {
                        if(iteratorAddmission.getCodes()[0].equals(resultSet.getString("addmission_type")))
                        {
                          if(execution.getDiseases().isEmpty())
                          {
                            analyzedCases++;
                            found = true;
                            break;
                          }
                          else if(execution.getMorbidities().isEmpty())
                          {
                            for(int i = 1; i <= 16; i++)
                            {
                              java.lang.String type_code = resultSet.getString(i);
                              java.lang.String code = resultSet.getString(i + 16);
                              if(type_code.equals("M")) // Morfology.
                              {
                                for(elv.util.parameters.MorfologyDiagnosis iteratorMorfology : execution.getMorfologies())
                                {
                                  if(iteratorMorfology.isRealDiagnosis())
                                  {
                                    // Extract the "M" and the "/" from morfology code.
                                    java.util.StringTokenizer sT = new java.util.StringTokenizer(iteratorMorfology.getCodes()[0].substring(1), "/");
                                    java.lang.String iteratorCode = sT.nextToken() + sT.nextToken();
                                    if(iteratorCode.equals(code))
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
                              else // Disease.
                              {
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
                              if(found)
                              {
                                break;
                              }
                            }
                          }
                          else
                          {
                            for(elv.util.parameters.MorbidityDiagnosis iteratorMorbidity : execution.getMorbidities())
                            {
                              for(int i = 1; i <= 16; i++)
                              {
                                java.lang.String type_code = resultSet.getString(i);
                                java.lang.String code = resultSet.getString(i + 16);
                                if(iteratorMorbidity.getCodes()[0].equals(type_code))
                                {
                                  if(type_code.equals("M")) // Morfology.
                                  {
                                    for(elv.util.parameters.MorfologyDiagnosis iteratorMorfology : execution.getMorfologies())
                                    {
                                      if(iteratorMorfology.isRealDiagnosis())
                                      {
                                        // Extract the "M" and the "/" from morfology code.
                                        java.util.StringTokenizer sT = new java.util.StringTokenizer(iteratorMorfology.getCodes()[0].substring(1), "/");
                                        java.lang.String iteratorCode = sT.nextToken() + sT.nextToken();
                                        if(iteratorCode.equals(code))
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
                                  else // Disease.
                                  {
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
                                if(found)
                                {
                                  break;
                                }
                              }
                              if(found)
                              {
                                break;
                              }
                            }
                          }
                        }
                        if(found)
                        {
                          if(selection.equals(SELECTIONS[DISTINCT]))
                          {
                            parsedCodes.get(settlementCount).add(parsedCode);
                          }
                          break;
                        }
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
              }
              year = iteratorYear;
              ageInterval = iteratorAgeInterval;
              placeSettlements.add(iteratorSettlement);
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

      // Store the last place.
      if(place != null)
      {
        // Prepare line.
        line = year + VS + ageInterval + VS + place.getCodes()[0] + VS +
               population + VS + totalCases + VS + analyzedCases;
        // Store line.
        java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
        fileWriter.println(line);
        fileWriter.close();
        place.getYearResults().add(new SettlementYearResult(year, ageInterval, population, totalCases, analyzedCases));
      }

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
