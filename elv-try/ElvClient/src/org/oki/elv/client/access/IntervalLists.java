package org.oki.elv.client.access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import org.oki.elv.common.net.Request;
import org.oki.elv.common.parameter.Interval;
import org.oki.elv.common.parameter.IntervalList;
import static org.oki.elv.common.parameter.IntervalList.*;

/**
 * Interval list acces methods.
 * @author Elv
 */
public final class IntervalLists {
  /**
   * Loader of an interval list.
   * @param intervalList
   * @param folderPath
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static void load(IntervalList intervalList, String folderPath) throws Exception {
    if(folderPath == null) {
      if(intervalList == DEFAULT_YEAR_INTERVAL_LIST) {
        intervalList = (IntervalList) Requests.post(new Request(Request.TYPE.GET_EXISTING_YEAR_INTERVALS))[0];
      }
      else if(intervalList == DEFAULT_AGE_INTERVAL_LIST) {
        intervalList.add(new Interval(TYPE.AGE.min, TYPE.AGE.max));
      }
      else if(intervalList == FIVE_YEARLY_AGE_INTERVAL_LIST) {
        for(int i = TYPE.AGE.min; i <= TYPE.AGE.max; i += 5) {
          int from = i;
          int to = i + 4 < TYPE.AGE.max ? i + 4 : TYPE.AGE.max;
          intervalList.add(new Interval(from, to));
        }
      }
      else if(intervalList == YEARLY_AGE_INTERVAL_LIST) {
        for(int i = TYPE.AGE.min; i <= TYPE.AGE.max; i++) {
          intervalList.add(new Interval(i, i));
        }
      }
    }
    else {
      String line;
      HttpURLConnection connection = Requests.openGETConnection(folderPath + "/" + intervalList.getType().fileName, true);
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while((line = fileReader.readLine()) != null) {
        intervalList.add(Interval.fromString(line));
      }
      fileReader.close();
      Requests.closeConnection(connection);
    }
  }

  
  /**
   * Storer of an interval list
   * @param intervalList
   * @param folderPath
   * @throws Exception
   */
  public static void store(IntervalList intervalList, String folderPath) throws Exception {
    HttpURLConnection connection = Requests.openPUTConnection(folderPath + "/" + intervalList.getType().fileName, true);
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
    for(Interval iteratorInterval : intervalList) {
      fileWriter.println(iteratorInterval.toString());
    }
    fileWriter.close();
    Requests.closeConnection(connection);
  }
}
