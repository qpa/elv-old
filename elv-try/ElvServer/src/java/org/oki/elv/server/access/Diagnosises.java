package org.oki.elv.server.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.oki.elv.common.parameter.Diagnosis;
import org.oki.elv.server.io.Files;

import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * Diagnosis acces methods.
 * @author Elv
 */
public final class Diagnosises {
  /**
   * Loader of a diagnosis.
   * @param diagnosis
   * @param folderPath
   * @throws IOException
   */
  public static void load(Diagnosis diagnosis, String folderPath) throws IOException {
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(folderPath + "/" + diagnosis.getType().fileName), Files.FILE_ENCODING));
    diagnosis.load(fileReader, false, null);
    fileReader.close();
  }

  /**
   * Storer of a diagnosis.
   * @param diagnosis
   * @param folderPath
   * @throws IOException
   */
  public static void store(Diagnosis diagnosis, String folderPath) throws IOException {
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(folderPath + "/" + diagnosis.getType().fileName), Files.FILE_ENCODING));
    diagnosis.store(fileWriter);
    fileWriter.close();
  }
}
