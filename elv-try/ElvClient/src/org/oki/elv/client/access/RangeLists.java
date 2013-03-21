package org.oki.elv.client.access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import org.oki.elv.common.parameter.Range;
import org.oki.elv.common.parameter.RangeList;

/**
 * RangeList access methods.
 * @author Elv
 */
public final class RangeLists {
  /**
   * Loader of a range list.
   * @param rangeList
   * @param folderPath
   * @throws Exception
   */
  public static void load(RangeList rangeList, String folderPath) throws Exception {
    String line;
    HttpURLConnection connection = Requests.openGETConnection(folderPath + "/" + RangeList.FILE_NAME, true);
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    while((line = fileReader.readLine()) != null) {
      rangeList.add(Range.fromLine(line));
    }
    fileReader.close();
    Requests.closeConnection(connection);
  }

  /**
   * Storer of a range list.
   * @param rangeList
   * @param folderPath
   * @throws Exception
   */
  public static void store(RangeList rangeList, String folderPath) throws Exception {
    HttpURLConnection connection = Requests.openPUTConnection(folderPath + "/" + RangeList.FILE_NAME, true);
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
    for(Range iteratorRange : rangeList) {
      fileWriter.println(iteratorRange.toLine());
    }
    fileWriter.close();
    Requests.closeConnection(connection);
  }
}
