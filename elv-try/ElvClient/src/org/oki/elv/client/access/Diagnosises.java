package org.oki.elv.client.access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import org.oki.elv.client.gui.App;
import org.oki.elv.common.io.Files;
import org.oki.elv.common.parameter.Diagnosis;
import static org.oki.elv.common.parameter.Diagnosis.*;

/**
 * Diagnosis acces methods.
 * @author Elv
 */
public final class Diagnosises {
  /**
   * Loader of a diagnosis.
   * @param diagnosis
   * @param folderPath
   * @throws Exception
   */
  public static void load(Diagnosis diagnosis, String folderPath) throws Exception {
    if(folderPath == null) {
      if(diagnosis == DEFAULT_ADDMISSION_ROOT || diagnosis == DEFAULT_DISEASE_ROOT || diagnosis == DEFAULT_DISMISSAL_ROOT ||
          diagnosis == DEFAULT_MORBIDITY_ROOT || diagnosis == DEFAULT_MORFOLOGY_ROOT || diagnosis == DEFAULT_MORTALITY_ROOT) {
        String resourcePath = "resources/" + Files.getFileBase(diagnosis.getType().fileName) + "_" + App.getApplication().getLocale().getLanguage() + ".properties";
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(Diagnosises.class.getResource(resourcePath).openStream(), Files.FILE_ENCODING));
        diagnosis.load(fileReader, true, null);
        fileReader.close();
      }
    }
    else {
      HttpURLConnection connection = Requests.openGETConnection(folderPath + "/" + diagnosis.getType().fileName, true);
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      diagnosis.load(fileReader, false, null);
      fileReader.close();
      Requests.closeConnection(connection);
    }
  }
  
  /**
   * Storer of a diagnosis.
   * @param diagnosis
   * @param folderPath
   * @throws Exception
   */
  public static void store(Diagnosis diagnosis, String folderPath) throws Exception {
    HttpURLConnection connection = Requests.openPUTConnection(folderPath + "/" + diagnosis.getType().fileName, true);
    PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
    diagnosis.store(fileWriter);
    fileWriter.close();
    Requests.closeConnection(connection);
  }
}
