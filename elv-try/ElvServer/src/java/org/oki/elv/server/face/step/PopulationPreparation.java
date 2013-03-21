/**
 * PopulationPreparation.java
 */
package org.oki.elv.server.face.step;

import java.util.ArrayList;
import java.util.List;

import org.oki.elv.common.face.step.Execution;
import org.oki.elv.common.face.step.GENDER;
import org.oki.elv.common.face.step.Progress;
import org.oki.elv.common.face.step.ProgressStack;
import org.oki.elv.common.face.step.preparation.PopulationPreparationAttribute;
import org.oki.elv.common.face.step.result.PopulationValue;
import org.oki.elv.common.face.step.result.RangeGenderYearAgeIntervalKey;
import org.oki.elv.common.face.step.result.Result;
import org.oki.elv.common.io.ReaderWriter;
import org.oki.elv.common.parameter.IntervalList;
import org.oki.elv.common.parameter.RangeList;
import org.oki.elv.common.parameter.Territory;
import org.oki.elv.server.engine.Dispatcher;

/**
 * Population preparation.
 * @author Elv
 */
public class PopulationPreparation implements Execution<PopulationPreparationAttribute, Result<RangeGenderYearAgeIntervalKey, PopulationValue>> {
  /** File name. */
  private static final String EXECUTION_FILE = "preparation.csv";
  
  /** File names. */
  private List<String> fileNames;
  /** Attribute. */
  private PopulationPreparationAttribute attribute;
  /** Year-intervals. */
  private IntervalList yearIntervalList;
  /** Age-intervals. */
  private IntervalList ageIntervalList;
  /** Ranges. */
  private RangeList rangeList;
  
  /**
   * Contructor. 
   * @param attribute 
   * @param parameters
   */
  public PopulationPreparation(PopulationPreparationAttribute attribute, Object[] parameters) {
    fileNames = new ArrayList<String>();
    fileNames.add(EXECUTION_FILE);
    this.attribute = attribute;
    for(Object iteratorParameter : parameters) {
      if(iteratorParameter instanceof IntervalList) {
        IntervalList intervalList = (IntervalList) iteratorParameter;
        if(IntervalList.TYPE.YEAR == intervalList.getType()) {
          yearIntervalList = intervalList;
        }
        else {
          ageIntervalList = intervalList;
        }
      }
      else if (iteratorParameter instanceof RangeList) {
        rangeList = (RangeList) iteratorParameter;
      }
    }
  }

  @Override
  public List<String> getFileNames() {
    return fileNames;
  }

  @Override
  public Result<RangeGenderYearAgeIntervalKey, PopulationValue> execute(ReaderWriter<Result<RangeGenderYearAgeIntervalKey, PopulationValue>>... accessers) {
//TODO    execution.setExecutable(this);
//TODO    if(!execution.isExecuted())
boolean isProgressLoad = false;
ProgressStack progressStack;

    accessers[0].openRead();

    Result<RangeGenderYearAgeIntervalKey, PopulationValue> header = new Result<RangeGenderYearAgeIntervalKey, PopulationValue>(true);
    accessers[0].read(header);
    if(!accessers[0].isReadable()) {
      if(isProgressLoad) {
        return null;
      }
      accessers[0].write(header);
    }
    
    // Initialize.
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
    
    
    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    try {
      statement = Dispatcher.getInstance().getDatabase().getConnection().createStatement();
      // Parse.
      progressStack.push(new Progress(elv.util.parameters.YearInterval.YEARS_TITLE, 0, execution.getYears().size()));
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
                java.lang.String[] splitedLine = line.split(java.util.regex.Pattern.quote(VS));
                java.lang.String observedLine = splitedLine[0] + VS + splitedLine[1] + VS + splitedLine[2] + VS + splitedLine[3] + VS + splitedLine[4];
                if(!observedLine.equals(expectedLine))
                {
                  throw new java.lang.IllegalArgumentException("Observed: " + observedLine + " is different than: " + expectedLine + "\n  at line: " + lineCount);
                }
                population = java.lang.Integer.parseInt(splitedLine[5]);
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
                      (GENDER.ALL == attribute.getGender() ? "" : " AND gender = " + attribute.getGender().code) +
                      (ageIntervalList.isEmpty() ? "" : " AND age BETWEEN " + iteratorAgeInterval.getFromValue() + " AND " + iteratorAgeInterval.getToValue()) +
                      (noSettlements ? "" : " AND settlement = " + iteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
                      resultSet = statement.executeQuery(sqlString);
                      resultSet.next();
                      population += resultSet.getInt(1);
                      resultSet.close();
                      districtSettlements.add(iteratorSettlement);
                      
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
