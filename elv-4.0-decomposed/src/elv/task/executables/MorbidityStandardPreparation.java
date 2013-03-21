/*
 * MorbidityStandardPreparation.java
 */
package elv.task.executables;

/**
 * Class for implementing the morbidity preparation method for standardization.
 * @author Elv
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
    // Initialize.
    java.util.Vector<elv.util.parameters.Gender> genders = (java.util.Vector<elv.util.parameters.Gender>)elv.util.Property.get(GENDERS_NAME, properties).getValue();
    java.lang.String selection = (java.lang.String)elv.util.Property.get(SELECTION_NAME, properties).getValue();

    java.lang.String pathName = execution.getTask().getExecutionFolderPath() + "/" + getExecutionFiles().get(PREPARATION);
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

    boolean noAddmissions = false;
    if(execution.getAddmissions().isEmpty())
    {
      elv.util.parameters.AddmissionDiagnosis addmission = new elv.util.parameters.AddmissionDiagnosis().getDefault();
      execution.getAddmissions().add(addmission);
      noAddmissions = true;
    }

    boolean noDismissals = false;
    if(execution.getDismissals().isEmpty())
    {
      elv.util.parameters.DismissalDiagnosis dismissal = new elv.util.parameters.DismissalDiagnosis().getDefault();
      execution.getDismissals().add(dismissal);
      noDismissals = true;
    }

    boolean noDiseases = false;
    if(execution.getDiseases().isEmpty())
    {
      elv.util.parameters.DiseaseDiagnosis disease = new elv.util.parameters.DiseaseDiagnosis().getDefault();
      execution.getDiseases().add(disease);
      noDiseases = true;
    }

    boolean noMorfologies = false;
    if(execution.getMorfologies().isEmpty())
    {
      elv.util.parameters.MorfologyDiagnosis morfology = new elv.util.parameters.MorfologyDiagnosis().getDefault();
      execution.getMorfologies().add(morfology);
      noMorfologies = true;
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
      statement = elv.util.server.ServerStub.getDataBaseConnection().createStatement();
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
          java.util.Vector<elv.util.parameters.Settlement> parsedPlaces = new java.util.Vector<elv.util.parameters.Settlement>();
          java.util.Vector<elv.util.parameters.Settlement> parsedBenchmarkPlaces = new java.util.Vector<elv.util.parameters.Settlement>();
          execution.getProgresses().push(new elv.util.Progress(elv.util.parameters.Settlement.TITLE, 0, execution.getAllSettlements().size()));
          for(int settlementCount = 0; settlementCount < execution.getAllSettlements().size(); settlementCount++)
          {
            elv.util.parameters.Settlement iteratorSettlement = execution.getAllSettlements().get(settlementCount);
            if(settlementListCount == 0)
            {
              parsedCodes.add(new java.util.Vector<java.lang.String>());
            }
            boolean benchmarkPlaceParsed = false;
            if(iteratorSettlement.isPlace())
            {
              if(fileReader != null && (line = fileReader.readLine()) != null)
              {
                lineCount++;
                java.lang.String expectedLine = iteratorYear + VS + iteratorAgeInterval + VS + iteratorSettlement.getCodes()[0];
                java.lang.String[] splitedLine = line.split(java.util.regex.Pattern.quote(VS));
                java.lang.String observedLine = splitedLine[0] + VS + splitedLine[1] + VS + splitedLine[2];
                if(!observedLine.equals(expectedLine))
                {
                  throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
                }
                population = java.lang.Integer.parseInt(splitedLine[3]);
                totalCases = java.lang.Integer.parseInt(splitedLine[4]);
                analyzedCases = java.lang.Integer.parseInt(splitedLine[5]);
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

              // Determine if this place was parsed.
              boolean placeParsed = false;
              for(elv.util.parameters.Settlement iteratorParsedPlace : parsedPlaces)
              {
                if(iteratorParsedPlace.getCodes()[elv.util.parameters.Settlement.PLACE].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.PLACE]))
                {
                  placeParsed = true;
                  // Determine if this benchmark place was added.
                  if(iteratorSettlement instanceof elv.util.parameters.BenchmarkSettlement)
                  {
                    for(elv.util.parameters.Settlement iteratorParsedBenchmarkPlace : parsedBenchmarkPlaces)
                    {
                      if(iteratorParsedBenchmarkPlace.getCodes()[elv.util.parameters.Settlement.PLACE].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.PLACE]))
                      {
                        benchmarkPlaceParsed = true;
                        break;
                      }
                    }
                    if(!benchmarkPlaceParsed)
                    {
                      iteratorSettlement.setYearResults(iteratorParsedPlace.getYearResults());
                      parsedBenchmarkPlaces.add(iteratorSettlement);
                    }
                  }
                  break;
                }
              }


                if(place != null)
                {
                  // Prepare line.
                  line = year + VS + ageInterval + VS + place.getCodes()[0] + VS + population + VS + totalCases + VS + analyzedCases;
                  // Store line.
                  java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName, true), elv.util.Util.FILE_ENCODING));
                  fileWriter.println(line);
                  fileWriter.close();
                  iteratorSettlement.getYearResults().add(new SettlementYearResult(year, ageInterval, population, totalCases, analyzedCases));
                  // Reset counters.
                  population = 0;
                  totalCases = 0;
                  analyzedCases = 0;
                  placeSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
                }
              }
              place = iteratorSettlement;
              parsedPlaces.add(place);
            }

            if(iteratorSettlement.isRealSettlement() && fileReader == null && !benchmarkPlaceParsed)
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
                (iteratorSettlement.equals(new elv.util.parameters.BenchmarkSettlement().getDefault()) ? "" : " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
                resultSet = statement.executeQuery(sqlString);
                resultSet.next();
                population += resultSet.getInt(1);
                resultSet.close();
              }

              if(!postalParsed)
              {
                // Select totalCases.
                java.lang.String sqlString =
                "SELECT COUNT(" + (selection.equals(SELECTIONS[DISTINCT]) ? "DISTINCT" : "") + " insurance_code)" +
                " FROM morbidity" +
                " WHERE addmission_year = " + iteratorYear +
                (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                (iteratorSettlement.equals(new elv.util.parameters.BenchmarkSettlement().getDefault()) ? "" : " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL]);
                resultSet = statement.executeQuery(sqlString);
                resultSet.next();
                totalCases += resultSet.getInt(1);
                resultSet.close();

                // Select analyzedCases.
                for(elv.util.parameters.AddmissionDiagnosis iteratorAddmission : execution.getAddmissions())
                {
                  for(elv.util.parameters.DismissalDiagnosis iteratorDismissal : execution.getDismissals())
                  {
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
                            for(elv.util.parameters.MorbidityDiagnosis iteratorMorbidity : execution.getMorbidities())
                            {
                              java.lang.String iteratorMorbidityCode = iteratorMorbidity.getCodes()[0].toLowerCase();
                              if(!iteratorMorbidityCode.equals("m")) // Not a morfology.
                              {
                                diseasesClause += (diseasesClause.equals("") ?
                                  " AND (diagnosis_" + iteratorMorbidityCode + " LIKE '%" + iteratorDiseaseCode + "%'" :
                                  " OR diagnosis_" + iteratorMorbidityCode + " LIKE '%" + iteratorDiseaseCode + "%'");
                              }
                            }
                          }
                          else
                          {
                            if(iteratorDisease.isRealDiagnosis())
                            {
                              int indexOfH = 3;
                              if(iteratorDiseaseCode.charAt(indexOfH) == 'H') // Codes like W01H0 are not completed properly
                              {
                                java.lang.String code = iteratorDiseaseCode.substring(0, indexOfH);
                                for(elv.util.parameters.MorbidityDiagnosis iteratorMorbidity : execution.getMorbidities())
                                {
                                  java.lang.String iteratorMorbidityCode = iteratorMorbidity.getCodes()[0].toLowerCase();
                                  if(iteratorMorbidityCode.equals("5")) // Accompanying diseases: Code_1+Code_2... (Should be less then 16).
                                  {
                                    int maxAccompDiseases = 16;
                                    int codeLengthWithSeparator = 6;
                                    for(int i = 0; i < maxAccompDiseases; i++)
                                    {
                                      diseasesClause += (diseasesClause.equals("") ?
                                        " AND (SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM 1 FOR " + code.length() + ") = '" + code + "'" :
                                        " OR SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM " + (i * codeLengthWithSeparator + 1) + " FOR " + code.length() + ") = '" + code + "'");
                                    }
                                  }
                                  else if(!iteratorMorbidityCode.equals("m")) // Not a morfology.
                                  {
                                    diseasesClause += (diseasesClause.equals("") ?
                                      " AND (SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM 1 FOR " + code.length() + ") = '" + code + "'" :
                                      " OR SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM 1 FOR " + code.length() + ") = '" + code + "'");
                                  }
                                }
                              }
                              else
                              {
                                for(elv.util.parameters.MorbidityDiagnosis iteratorMorbidity : execution.getMorbidities())
                                {
                                  java.lang.String iteratorMorbidityCode = iteratorMorbidity.getCodes()[0].toLowerCase();
                                  if(!iteratorMorbidityCode.equals("m")) // Not a morfology.
                                  {
                                    diseasesClause += (diseasesClause.equals("") ?
                                      " AND (diagnosis_" + iteratorMorbidityCode + " LIKE '%" + iteratorDiseaseCode + "%'" :
                                      " OR diagnosis_" + iteratorMorbidityCode + " LIKE '%" + iteratorDiseaseCode + "%'");
                                  }
                                }
                              }
                            }
                            else
                            {
                              java.lang.String[] codes = iteratorDiseaseCode.split(java.util.regex.Pattern.quote("-"));
                              java.lang.String fromCode = codes[0];
                              java.lang.String toCode = (codes.length == 1 ? codes[0] : codes[1]);
                              for(elv.util.parameters.MorbidityDiagnosis iteratorMorbidity : execution.getMorbidities())
                              {
                                java.lang.String iteratorMorbidityCode = iteratorMorbidity.getCodes()[0].toLowerCase();
                                if(iteratorMorbidityCode.equals("5")) // Accompanying diseases: Code_1+Code_2... (Should be less then 16).
                                {
                                  int maxAccompDiseases = 16;
                                  int codeLengthWithSeparator = 6;
                                  for(int i = 0; i < maxAccompDiseases; i++)
                                  {
                                    diseasesClause += (diseasesClause.equals("") ?
                                      " AND (SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM 1 FOR " + fromCode.length() + ") BETWEEN '" + fromCode + "' AND '" + toCode + "'" :
                                      " OR SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM " + (i * codeLengthWithSeparator + 1) + " FOR " + fromCode.length() + ") BETWEEN '" + fromCode + "' AND '" + toCode + "'");
                                  }
                                }
                                else if(!iteratorMorbidityCode.equals("m")) // Not a morfology.
                                {
                                  diseasesClause += (diseasesClause.equals("") ?
                                    " AND (SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM 1 FOR " + fromCode.length() + ") BETWEEN '" + fromCode + "' AND '" + toCode + "'" :
                                    " OR SUBSTRING(diagnosis_" + iteratorMorbidityCode + " FROM 1 FOR " + fromCode.length() + ") BETWEEN '" + fromCode + "' AND '" + toCode + "'");
                                }
                              }
                            }
                          }
                          diseasesClause += ")";
                          for(elv.util.parameters.MorfologyDiagnosis iteratorMorfology : execution.getMorfologies())
                          {
                            if(iteratorMorfology.isRealDiagnosis())
                            {
                              // Extract the "M" and the "/" from morfology code.
                              java.lang.String[] splittedCode = iteratorMorfology.getCodes()[0].substring(1).split(java.util.regex.Pattern.quote("/"));
                              java.lang.String iteratorMorfologyCode = splittedCode[0] + splittedCode[1];

                              // Select analyzedCases.
                              sqlString =
                              "SELECT COUNT(" + (selection.equals(SELECTIONS[DISTINCT]) ? "DISTINCT" : "") + " insurance_code)" +
                              " FROM morbidity" +
                              " WHERE addmission_year = " + iteratorYear +
                              (allGenders ? "" : " AND gender = " + genders.get(0).getValue()) +
                              (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                              (iteratorSettlement.equals(new elv.util.parameters.BenchmarkSettlement().getDefault()) ? "" : " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL]) +
                              (noAddmissions ? "" : " AND addmission_type = " + iteratorAddmission.getCodes()[0]) +
                              (noDismissals ? "" : " AND dismissal_type = " + iteratorDismissal.getCodes()[0]) +
                              (noDiseases ? "" : diseasesClause) +
                              (noMorfologies ? "" : " AND diagnosis_m LIKE '%" + iteratorMorfologyCode + "%'");
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
                      }
                    }
                  }
                }
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
        line = year + VS + ageInterval + VS + place.getCodes()[0] + VS + population + VS + totalCases + VS + analyzedCases;
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
