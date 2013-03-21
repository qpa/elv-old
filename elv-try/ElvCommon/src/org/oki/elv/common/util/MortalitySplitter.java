package org.oki.elv.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Class for splitting.
 * @author Elv
 */
public class MortalitySplitter {
  /**
   * Constructor.
   */
  public MortalitySplitter() {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      String separator = ";";
      int currentYear = 2010;
      String inputFolderName = "F:/ELV/data/mortality_2010/in";
      String outputFileName = "F:/ELV/data/mortality_2010/out/" + currentYear + ".csv";
      PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName, true)));
      File[] files = new File(inputFolderName).listFiles();
      for(File file : files) {
        if(file.isDirectory()) {
          continue;
        }
        System.out.println(file);
        int lineCount = 0;
        String line = "";
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while((line = fileReader.readLine()) != null) {
          lineCount++;
          String newLine = "";
          try {
            String[] lineSplit = line.split(Pattern.quote(separator));
            String deathMonth = lineSplit[0];
            String permanentResidence = lineSplit[1];
//            String permanentResidence = lineSplit[2];
            String effectiveResidence = (lineSplit[2].equals("0000") ? lineSplit[1] : lineSplit[2]);
//            String effectiveResidence = (lineSplit[3].equals("0000") ? lineSplit[2] : lineSplit[3]);
            String gender = lineSplit[3];
//            String gender = lineSplit[4];
            
            String birthDate = lineSplit[4];
//            String birthDate = lineSplit[1];
            String birthYear = birthDate.substring(0, 4);
            String birthMonth = birthDate.substring(4, 6);
            String birthDay = birthDate.substring(6, 8);
			
            String diagnoser = lineSplit[5];
//            String diagnoser = lineSplit[6];
            String medicalTreatment = lineSplit[6];
//            String medicalTreatment = lineSplit[7];

            int bnoCodeLength = 5;
            String diagnosis_1 = lineSplit[7];
//            String diagnosis_1 = lineSplit[5];
            for(int i = diagnosis_1.length(); i < bnoCodeLength; i++) {
              diagnosis_1 += "_";
            }
            
            newLine = currentYear + "," + deathMonth + ",0,0,0," + // Death day, hour, minute.
              birthYear + "," + birthMonth + "," + birthDay + "," +
              permanentResidence + "," + effectiveResidence + "," + gender + "," +
              (currentYear - Integer.parseInt(birthYear)) + "," + diagnosis_1 + ",00000,00000,00000,00000," +
              diagnoser + "," + medicalTreatment;
            fileWriter.println(newLine);
          }
          catch(Exception exc) {
            System.out.println("ERROR: Line " + lineCount + ": " + line);
            System.out.println("ERROR: Message: " + exc.getMessage());
          }
        }
        fileReader.close();
      }
      fileWriter.close();
    }
    catch(Exception exc) {
      exc.printStackTrace();
    }
  }
}
