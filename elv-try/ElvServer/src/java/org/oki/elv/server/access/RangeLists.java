package org.oki.elv.server.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.oki.elv.common.parameter.Range;
import org.oki.elv.common.parameter.RangeList;
import org.oki.elv.server.face.parameter.ServerRange;
import org.oki.elv.server.io.Files;

import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * RangeList access methods.
 * @author Elv
 */
public class RangeLists {
  /**
   * Loader of a range list.
   * @param rangeList
   * @param folderPath
   * @throws IOException
   */
  public void load(RangeList rangeList, String folderPath) throws IOException {
    String line;
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(folderPath + "/" + RangeList.FILE_NAME), Files.FILE_ENCODING));
    while((line = fileReader.readLine()) != null) {
      rangeList.add(ServerRange.fromLine(line));
    }
    fileReader.close();
  }

  /**
   * Storer of a range list.
   * @param rangeList
   * @param folderPath
   * @throws IOException
   */
  public void store(RangeList rangeList, String folderPath) throws IOException {
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(folderPath + "/" + RangeList.FILE_NAME), Files.FILE_ENCODING));
    for(Range iteratorRange : rangeList) {
      fileWriter.println(iteratorRange.toLine());
    }
    fileWriter.close();
  }
}
