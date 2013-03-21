package org.oki.elv.common.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * File query request.
 * @author Elv
 */
public final class FileQuery {
  /** File types. */
  public enum FileType {
    /** Binary file type. */
    BIN("bin"),
    /** Text file type. */
    TEXT("text"),
    /** HTML file type. */
    HTML("html");
    /** File type name. */
    public final String name;
    
    /**
     * Contructor.
     * @param name type name.
     */
    private FileType(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }

    /**
     * Creates a <code>FileType</code> from the given string.
     * @param fileTypeName the name of a file type.
     * @return the converted file type.
     * @see toString().
     */
    public static FileType fromString(String fileTypeName) {
      for(FileType iteratorFT : FileType.values()) {
        if(iteratorFT.name.equals(fileTypeName)) {
          return iteratorFT;
        }
      }
      throw new IllegalArgumentException("Not a valid file type name: \"" + fileTypeName + "\"");
    }
  }
  
  /** The "file" key in the query part of the HTML request. */
  private final static String FILE = "file";
  /** The "type" key in the query part of the HTML request. */
  private final static String TYPE = "type";
  /** The "EQUALS" key for merging the parameter (key=value) sections of the query part of the HTML request. */
  private final static String EQUALS = "=";
  /** The "AND" key for merging the sections of the query part of the HTML request. */
  private final static String AND = "&";
  /** The "QUERY" key determining the query part of the HTML request. */
  public final static String QUERY = "?";
  
  /** The character set for URL encoding and decoding. */
  public static final String URL_ENCODING = "UTF-8";
  
  /** File type. */
  private FileType fileType;
  /** File path. */
  private String filePath;
  
  /**
   * Constructor.
   * @param fileType
   * @param filePath
   */
  public FileQuery(FileType fileType, String filePath) {
    this.fileType = fileType;
    this.filePath = filePath;
  }

  /**
   * Getter of the file type.
   * @return the type of the file.
   */
  public FileType getFileType() {
    return fileType;
  }

  /**
   * Getter of the file path.
   * @return the file path.
   */
  public String getFilePath() {
    return filePath;
  }

  @Override
  public String toString() {
    String typeMap = TYPE + EQUALS + fileType;
    String fileMap = null;
    try {
      fileMap = FILE + EQUALS + URLEncoder.encode(filePath, URL_ENCODING);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return QUERY + typeMap + AND + fileMap;
  }

  /**
   * Creates a FileQuery from the given string.
   * @param queryString
   * @return the FileQuery constructed from the argument.
   * @see toString().
   */
  public static FileQuery fromString(String queryString) {
    if(queryString.startsWith(QUERY)) {
      queryString.substring(QUERY.length()); // Strip QUERY character, if exists.
    }
    String[] parameters = queryString.split(Pattern.quote(AND));
    String filePath = null;
    FileType fileType = FileType.BIN;
    for(String iteratorParameter : parameters) {
      String[] keyValues = iteratorParameter.split(Pattern.quote(EQUALS));
      if(keyValues[0].equals(FILE)) {
        try {
          filePath = URLDecoder.decode(keyValues[1], URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
      else if(keyValues[0].equals(TYPE)) {
        fileType = FileType.fromString(keyValues[1]);
      }
    }
    return new FileQuery(fileType, filePath);
  }
}
