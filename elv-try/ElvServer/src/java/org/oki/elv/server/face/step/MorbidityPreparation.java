/**
 * MorbidityPreparation.java
 */
package org.oki.elv.server.face.step;

import java.util.ArrayList;
import java.util.List;

import org.oki.elv.common.face.step.Execution;
import org.oki.elv.common.face.step.preparation.MorbidityPreparationAttribute;
import org.oki.elv.common.face.step.preparation.PopulationPreparationAttribute;
import org.oki.elv.common.face.step.result.PopulationValue;
import org.oki.elv.common.face.step.result.RangeGenderYearAgeIntervalKey;
import org.oki.elv.common.face.step.result.Result;
import org.oki.elv.common.io.ReaderWriter;
import org.oki.elv.common.parameter.Diagnosis;
import org.oki.elv.common.parameter.IntervalList;
import org.oki.elv.common.parameter.RangeList;

/**
 * Morbidity preparation.
 * @author Elv
 */
public class MorbidityPreparation implements Execution<MorbidityPreparationAttribute, Result<RangeGenderYearAgeIntervalKey, PopulationValue>> {
  /** File name. */
  private static final String EXECUTION_FILE = "preparation.csv";
  
  /** File names. */
  private List<String> fileNames;
  /** Attribute. */
  private MorbidityPreparationAttribute attribute;
  /** Year-intervals. */
  private IntervalList yearIntervalList;
  /** Age-intervals. */
  private IntervalList ageIntervalList;
  /** Ranges. */
  private RangeList rangeList;
  /** Addmissions. */
  private Diagnosis addmissions;
  /** Diseases. */
  private Diagnosis diseases;
  /** Dismissals. */
  private Diagnosis dismissals;
  /** Morbidities. */
  private Diagnosis morbidities;
  /** Morfologies. */
  private Diagnosis morfologies;
  
  /**
   * Contructor. 
   * @param attribute 
   * @param parameters
   */
  public MorbidityPreparation(MorbidityPreparationAttribute attribute, Object[] parameters) {
    fileNames = new ArrayList<String>();
    fileNames.add(EXECUTION_FILE);
    this.attribute = attribute;
    for(Object iteratorParameter : parameters) {
      if(iteratorParameter instanceof IntervalList) {
        IntervalList intervalList = (IntervalList)iteratorParameter;
        if(IntervalList.TYPE.YEAR == intervalList.getType()) {
          yearIntervalList = intervalList;
        }
        else {
          ageIntervalList = intervalList;
        }
      }
      else if(iteratorParameter instanceof RangeList) {
        rangeList = (RangeList)iteratorParameter;
      }
      else if(iteratorParameter instanceof Diagnosis) {
        Diagnosis diagnosis = (Diagnosis)iteratorParameter;
        if(Diagnosis.TYPE.ADDMISSION == diagnosis.getType()) {
          addmissions = diagnosis;
        }
        else if(Diagnosis.TYPE.DISEASE == diagnosis.getType()) {
          diseases = diagnosis;
        }
        else if(Diagnosis.TYPE.DISMISSAL == diagnosis.getType()) {
          dismissals = diagnosis;
        }
        else if(Diagnosis.TYPE.MORBIDITY == diagnosis.getType()) {
          morbidities = diagnosis;
        }
        else if(Diagnosis.TYPE.MORFOLOGY == diagnosis.getType()) {
          morfologies = diagnosis;
        }
      }
    }
  }
  
  @Override
  public List<String> getFileNames() {
    return fileNames;
  }


  @Override
  public Result<RangeGenderYearAgeIntervalKey, PopulationValue> execute(ReaderWriter<Result<RangeGenderYearAgeIntervalKey, PopulationValue>>... accessers) {
    execution.setExecutable(this);
    // Initialize.
    java.util.Vector<elv.util.parameters.Gender> genders = (java.util.Vector<elv.util.parameters.Gender>)elv.util.Property.get(GENDERS_NAME, properties).getValue();
    java.lang.String resolution = (java.lang.String)elv.util.Property.get(RESOLUTION_NAME, properties).getValue();
    java.lang.String selection = (java.lang.String)elv.util.Property.get(SELECTION_NAME, properties).getValue();
    final java.lang.String haderLine =
      (resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? "Year Interval" + VS : "") +
      (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? "Year" + VS : "") +
      (resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? "Month" + VS : "") +
      (resolution.equals(RESOLUTIONS[DAILY]) ? "Day" + VS : "") +
      "Gender" + VS + "Age interval" + VS + "District code" + VS + "District name" + VS + "Analyzed cases";
    
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
    
    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    try
    {
      statement = elv.util.server.ServerStub.getDataBaseConnection().createStatement();
        
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
                      java.lang.String[] splitedLine = line.split(java.util.regex.Pattern.quote(VS));
                      java.lang.String observedLine = splitedLine[0];
                      for(int i = 1; i < splitedLine.length - 1; i++) // 1 = data column count: analysedCcases.
                      {
                        observedLine += VS + splitedLine[i];
                      }
                      if(!observedLine.equals(expectedLine))
                      {
                        throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
                      }
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
                          boolean settlementParsed = false;
                          for(elv.util.parameters.Settlement iteratorDistrictSettlement : districtSettlements)
                          {
                            if(iteratorDistrictSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL].equals(iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL]))
                            {
                              settlementParsed = true;
                              break;
                            }
                            // Return, if execution was stopped.
                            if(!execution.isExecuted())
                            {
                              statement.close();
                              return false;
                            }
                          }
                          
                          if(!settlementParsed)
                          {
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
                                          java.lang.String sqlString =
                                          "SELECT COUNT(" + (selection.equals(SELECTIONS[DISTINCT]) ? "DISTINCT" : "") + " insurance_code)" +
                                          " FROM morbidity" +
                                          " WHERE" + 
                                          (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? " addmission_year = " + iteratorYear : " addmission_year BETWEEN " +iteratorYearInterval.getFromValue() + " AND " + iteratorYearInterval.getToValue()) +
                                          (!(resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY])) ? "" : (" AND addmission_month = " + iteratorMonth)) +
                                          (!resolution.equals(RESOLUTIONS[DAILY]) ? "" : (" AND addmission_day = " + iteratorDay)) +
                                          (noGender ? "" : " AND gender = " + iteratorGender.getValue()) +
                                          (noAgeIntervals ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                                          (noSettlements ? "" : " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL]) +
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
                            districtSettlements.add(iteratorSettlement);
                          }
                        }
                      }
                      // Prepare line.
                      line =
                        (resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? iteratorYearInterval + VS : "") +
                        (!resolution.equals(RESOLUTIONS[YEAR_INTERVALY]) ? iteratorYear + VS : "") +
                        (resolution.equals(RESOLUTIONS[MONTHLY]) || resolution.equals(RESOLUTIONS[DAILY]) ? iteratorMonth + VS : "") +
                        (resolution.equals(RESOLUTIONS[DAILY]) ? iteratorDay + VS : "") +
                        iteratorGender + VS + iteratorAgeInterval + VS + iteratorDistrict.getCode() + VS + iteratorDistrict.getName() + VS +
                        analyzedCases;
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
