package org.oki.elv.client.access;

import static org.oki.elv.common.parameter.Territory.DEFAULT_BASE_ROOT;
import static org.oki.elv.common.parameter.Territory.DEFAULT_BENCHMARK_ROOT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import org.oki.elv.client.gui.App;
import org.oki.elv.common.io.Files;
import org.oki.elv.common.parameter.Territory;

/**
 * Territory acces methods.
 * @author Elv
 *
 */
public final class Territories {
  /**
   * Loader of a territory.
   * @param territory
   * @param folderPath
   * @throws Exception
   */
  public static void load(Territory territory, String folderPath) throws Exception {
    if(folderPath == null) {
      if(territory == DEFAULT_BASE_ROOT || territory == DEFAULT_BENCHMARK_ROOT) {
        String resourcePath = "resources/" + Files.getFileBase(territory.getType().fileName) + "_" + App.getApplication().getLocale().getLanguage() + ".properties";
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(Territories.class.getResource(resourcePath).openStream(), Files.FILE_ENCODING));
        territory.load(fileReader, true, App.getApplication().getCollator());
        fileReader.close();
      }
    }
    else {
      HttpURLConnection connection = Requests.openGETConnection(folderPath + "/" + territory.getType().fileName, true);
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      territory.load(fileReader, false, App.getApplication().getCollator());
      fileReader.close();
      Requests.closeConnection(connection);
    }
  }

  /**
   * Storer of a territory.
   * @param territory
   * @param folderPath
   * @throws Exception
   */
  public static void store(Territory territory, String folderPath) throws Exception {
    HttpURLConnection connection = Requests.openPUTConnection(folderPath + "/" + territory.getType().fileName, true);
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
    territory.store(fileWriter);
    fileWriter.close();
    Requests.closeConnection(connection);
  }
}
