package org.oki.elv.server.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.oki.elv.server.face.parameter.ServerTerritory;
import org.oki.elv.server.io.Files;

import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * Territory acces methods.
 * @author Elv
 */
public class Territories {
  /**
   * Loader of a territory.
   * @param territory
   * @param folderPath
   * @throws IOException
   */
  public void load(ServerTerritory territory, String folderPath) throws IOException {
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(folderPath + "/" + territory.getType().fileName), Files.FILE_ENCODING));
    territory.load(fileReader, false, null);
    fileReader.close();
  }

  /**
   * Storer of a territory.
   * @param territory
   * @param folderPath
   * @throws IOException
   */
  public void store(ServerTerritory territory, String folderPath) throws IOException {
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(folderPath + "/" + territory.getType().fileName), Files.FILE_ENCODING));
    territory.store(fileWriter);
    fileWriter.close();
  }
}
