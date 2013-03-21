/*
 * MortalityPreparation.java
 */
package elv.task.executables;

/**
 * Class for implementing the mortality preparation.
 * @author Qpa
 */
public class MortalityPreparation extends Preparation
{
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public MortalityPreparation() throws java.lang.Exception
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
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, RESOLUTION_NAME, RESOLUTIONS[YEAR_INTERVALY]));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(GENDERS_NAME, properties).setDefaultValues(elv.util.parameters.Gender.getAllGenders());
    elv.util.Property.get(RESOLUTION_NAME, properties).setDefaultValues(elv.util.Util.vectorize(RESOLUTIONS));
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
    // Initialize.
    java.util.Vector<elv.util.parameters.Gender> genders = (java.util.Vector<elv.util.parameters.Gender>)elv.util.Property.get(GENDERS_NAME, properties).getValue();
    java.lang.String resolution = (java.lang.String)elv.util.Property.get(RESOLUTION_NAME, properties).getValue();
    final java.lang.String haderLine =
      (resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? "Year Interval" + VS : "") +
      (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? "Year" + VS : "") +
      (resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? "Month" + VS : "") +
      (resolution.equals(RESOLUTIONS[DAILY]) ? "Day" + VS : "") +
      "Gender" + VS + "Age interval" + VS + "District code" + VS + "District name" + VS + "Total cases" + VS + "Analyzed cases";
    
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
      fileWriter.println(haderLine);
      fileWriter.close();
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
      execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.TITLE, 0, execution.getYearIntervals().size()));
      for(int yearIntervalCount = 0; yearIntervalCount < execution.getYearIntervals().size(); yearIntervalCount++)
      {
        elv.util.parameters.Interval iteratorYearInterval = execution.getYearIntervals().get(yearIntervalCount);

        int years = resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? 1 : iteratorYearInterval.getToValue() - iteratorYearInterval.getFromValue() + 1;
        execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, years));
        for(int yearCount = 0; yearCount < years; yearCount++)
        {
          int iteratorYear = iteratorYearInterval.getFromValue() + yearCount;

          java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
          calendar.set(java.util.Calendar.YEAR, iteratorYear);

          int months = resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? 12 : 1;
          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.MONTHS_TITLE, 0, months));
          for(int monthCount = 0; monthCount < months; monthCount++)
          {
            int iteratorMonth = resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? monthCount + 1 : monthCount;
            calendar.set(java.util.Calendar.MONTH, monthCount);

            int days = resolution.equals(RESOLUTIONS[DAILY]) ? calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH) : 1;
            execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.YearInterval.DAYS_TITLE, 0, days));
            for(int dayCount = 0; dayCount < days; dayCount++)
            {
              int iteratorDay = resolution.equals(RESOLUTIONS[DAILY]) ? dayCount + 1 : dayCount;

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
                    int totalCases = 0;
                    int analyzedCases = 0;

                    if(fileReader != null && (line = fileReader.readLine()) != null)
                    {
                      lineCount++;
                      java.lang.String expectedLine =
                        (resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? iteratorYearInterval + VS : "") +
                        (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? iteratorYear + VS : "") +
                        (resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? iteratorMonth + VS : "") +
                        (resolution.equals(RESOLUTIONS[DAILY]) ? iteratorDay + VS : "") +
                        iteratorGender + VS + iteratorAgeInterval + VS + iteratorDistrict.getCode() + VS + iteratorDistrict.getName();
                      java.lang.String observedLine = "";
                      java.lang.String[] splitedLine = line.split(java.util.regex.Pattern.quote(VS));
                      observedLine = splitedLine[0];
                      for(int i = 1; i < splitedLine.length - 2; i++) // 2 = data column count: totalCases + analysedCcases.
                      {
                        observedLine += VS + splitedLine[i];
                      }
                      if(!observedLine.equals(expectedLine))
                      {
                        throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
                      }
                      totalCases = java.lang.Integer.parseInt(splitedLine[splitedLine.length - 2]);
                      analyzedCases = java.lang.Integer.parseInt(splitedLine[splitedLine.length - 1]);
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
                            // Select analyzedCases.
                            java.lang.String sqlString =
                            "SELECT diagnosis_1, diagnosis_2, diagnosis_3, diagnosis_4, diagnosis_5" +
                            " FROM mortality" +
                            " WHERE" +
                            (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? " death_year = " + iteratorYear : " death_year BETWEEN " +iteratorYearInterval.getFromValue() + " AND " + iteratorYearInterval.getToValue()) +
                            (!(resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY])) ? "" : (" AND death_month = " + iteratorMonth)) +
                            (!resolution.equals(RESOLUTIONS[DAILY]) ? "" : (" AND death_day = " + iteratorDay)) + 
                            (noGender ? "" : " AND gender = " + iteratorGender.getValue()) +
                            (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                            (noSettlements ? "" : " AND effective_residence = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
                            resultSet = statement.executeQuery(sqlString);
                            while(resultSet.next())
                            {
                              totalCases++;
                              boolean found = false;
                              if(execution.getDiseases().isEmpty())
                              {
                                analyzedCases++;
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
                            districtSettlements.add(iteratorSettlement);
                          }
                        }
                        java.lang.Thread.yield();
                      }
                      line =
                        (resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? iteratorYearInterval + VS : "") +
                        (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? iteratorYear + VS : "") +
                        (resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? iteratorMonth + VS : "") +
                        (resolution.equals(RESOLUTIONS[DAILY]) ? iteratorDay + VS : "") +
                        iteratorGender + VS + iteratorAgeInterval + VS + iteratorDistrict.getCode() + VS +
                        iteratorDistrict.getName() + VS + totalCases + VS + analyzedCases;
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
              execution.getProgresses().peek().setValue(dayCount + 1);
            }
            execution.getProgresses().pop();
            execution.getProgresses().peek().setValue(monthCount + 1);
          }
          execution.getProgresses().pop();
          execution.getProgresses().peek().setValue(yearCount + 1);
        }
        execution.getProgresses().pop();
        execution.getProgresses().peek().setValue(yearIntervalCount + 1);
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
