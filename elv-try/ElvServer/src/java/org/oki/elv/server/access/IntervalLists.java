package org.oki.elv.server.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.oki.elv.common.parameter.Interval;
import org.oki.elv.common.parameter.IntervalList;
import org.oki.elv.server.io.Files;

import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * Interval list acces methods.
 * @author Elv
 */
public final class IntervalLists {
  /**
   * Loader of an interval list.
   * @param intervalList
   * @param folderPath
   * @throws IOException
   */
  public static void load(IntervalList intervalList, String folderPath) throws IOException {
    String line;
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(folderPath + "/" + intervalList.getType().fileName), Files.FILE_ENCODING));
    while((line = fileReader.readLine()) != null) {
      intervalList.add(Interval.fromString(line));
    }
    fileReader.close();
  }

  /**
   * Storer of an interval list
   * @param intervalList
   * @param folderPath
   * @throws IOException
   */
  public static void store(IntervalList intervalList, String folderPath) throws IOException {
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(folderPath + "/" + intervalList.getType().fileName), Files.FILE_ENCODING));
    for(Interval iteratorInterval : intervalList) {
      fileWriter.println(iteratorInterval.toString());
    }
    fileWriter.close();
  }
}
