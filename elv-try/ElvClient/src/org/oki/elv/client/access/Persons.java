package org.oki.elv.client.access;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.List;
import org.oki.elv.common.face.Person;
import org.oki.elv.common.net.Request;

/**
 * Person access methods.
 * @author Elv
 */
public final class Persons {
  /**
   * Creator of person.
   * @param person an person.
   * @throws Exception
   */
  public static void create(Person person) throws Exception {
    Request create = new Request(Request.TYPE.CREATE_FOLDER_AND_ENCODE_BEAN, person.getPath(), person, Person.PROPERTY_FILE_NAME, person.getPersistenceDelegate());
    String[] responses = Requests.post(create);
    String personPath = responses[0]; // The created path: numbered if necessary!
    person.setPath(personPath);
  }
  
  /**
   * Mover of the person to the target path.
   * @param person
   * @param targetPath 
   * @throws Exception
   */
  public static void move(Person person, String targetPath) throws Exception {
    Request move = new Request(Request.TYPE.MOVE_FOLDER, person.getPath(), targetPath);
    String[] responses = Requests.post(move);
    String personPath = responses[0]; // The moved path: numbered if necessary!
    person.setPath(personPath);
  }
  
  /**
   * Copier of the person to the target path.
   * @param person
   * @param targetPath
   * @throws Exception
   */
  public static void copy(Person person, String targetPath) throws Exception {
    Request copy = new Request(Request.TYPE.COPY_FOLDER, person.getPath(), targetPath);
    String[] responses = Requests.post(copy);
    String personPath = responses[0]; // The copied path: numbered if necessary!
    person.setPath(personPath);
  }

  /**
   * Deleter of the person.
   * @param person
   * @throws Exception
   */
  public static void delete(Person person) throws Exception {
    Request delete = new Request(Request.TYPE.DELETE_FOLDER, person.getPath());
    Requests.post(delete);
  }
  
  /**
   * Storer of the person.
   * @param person
   * @throws Exception
   */
  public static void store(Person person) throws Exception {
    HttpURLConnection connection = Requests.openPUTConnection(person.getPath() + "/" + Person.PROPERTY_FILE_NAME, true);
    XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(connection.getOutputStream()));
    xmlEncoder.setPersistenceDelegate(Person.class, person.getPersistenceDelegate());
    try {
      xmlEncoder.writeObject(person);
    }
    finally {
      xmlEncoder.close();
    }
    connection.getOutputStream().close();
    Requests.closeConnection(connection);
  }
  
  /**
   * Loader of all persons.
   * @return a list with all persons.
   * @throws Exception
   */
  public static List<Person> loadAll() throws Exception {
    Request load = new Request(Request.TYPE.LOAD_CHILDREN);
    Person[] responses = Requests.post(load);
    return Arrays.asList(responses);
  }

}
