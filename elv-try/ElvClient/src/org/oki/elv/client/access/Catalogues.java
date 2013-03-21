package org.oki.elv.client.access;

import org.oki.elv.common.face.Catalogue;
import org.oki.elv.common.net.Request;

/**
 * Catalogue access methods.
 * @author Elv
 */
public final class Catalogues {
  /**
   * Creator of catalogue.
   * @param catalogue an catalogue.
   * @throws Exception
   */
  public static void create(Catalogue catalogue) throws Exception {
    Request create = new Request(Request.TYPE.CREATE_FOLDER, catalogue.getPath());
    String[] responses = Requests.post(create);
    String cataloguePath = responses[0]; // The created path: numbered if necessary!
    catalogue.setPath(cataloguePath);
  }
  
  /**
   * Mover of the catalogue to the target path.
   * @param catalogue
   * @param targetPath 
   * @throws Exception
   */
  public static void move(Catalogue catalogue, String targetPath) throws Exception {
    Request move = new Request(Request.TYPE.MOVE_FOLDER, catalogue.getPath(), targetPath);
    String[] responses = Requests.post(move);
    String cataloguePath = responses[0]; // The moved path: numbered if necessary!
    catalogue.setPath(cataloguePath);
  }
  
  /**
   * Copier of the catalogue to the target path.
   * @param catalogue
   * @param targetPath
   * @throws Exception
   */
  public static void copy(Catalogue catalogue, String targetPath) throws Exception {
    Request copy = new Request(Request.TYPE.COPY_FOLDER, catalogue.getPath(), targetPath);
    String[] responses = Requests.post(copy);
    String cataloguePath = responses[0]; // The copied path: numbered if necessary!
    catalogue.setPath(cataloguePath);
  }

  /**
   * Deleter of the catalogue.
   * @param catalogue
   * @throws Exception
   */
  public static void delete(Catalogue catalogue) throws Exception {
    Request delete = new Request(Request.TYPE.DELETE_FOLDER, catalogue.getPath());
    Requests.post(delete);
  }
}
