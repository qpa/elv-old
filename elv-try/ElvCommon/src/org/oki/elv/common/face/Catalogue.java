package org.oki.elv.common.face;

import org.oki.elv.common.util.Persistence;
import org.oki.elv.common.util.Path;
import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.io.Serializable;
import java.util.Date;

/**
 * Catalogue.
 * @author Elv
 */
public final class Catalogue  extends Path implements Persistence, Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = -1451540694361115282L;
  
  /** Catalogue type. */
  public static enum TYPE {
    /** Simple catalogue type. */
    SIMPLE(""),
    /** Archived catalogue type. */
    ARCHIVE(".zip");
    
    /** Extension. */
    public final String extension;
    
    /**
     * Contructor.
     * @param extension catalog name extension.
     */
    private TYPE(String extension) {
      this.extension = extension;
    }
  };
  
  /** The type of the catalogue. */
  private TYPE type;
  /** The creation date. */
  private Date creationDate;
  /** The modification date. */
  private Date modificationDate;
  
  /**
   * Constructor.
   * @param path the path of the catalogue.
   * @param type the type of the catalogue.
   */
  public Catalogue(String path, TYPE type) {
    if(path == null) {
      throw new NullPointerException("Null path!");
    }
    else if(path.equals("")) {
      throw new IllegalArgumentException("Empty path!");
    }
    this.path = path;
    this.type = type;
  }

  /**
   * Getter of the type.
   * @return the type.
   */
  public TYPE getType(){
    return type;
  }
  
  /**
   * Getter of the creationDate.
   * @return the creationDate.
   */
  public Date getCreationDate() {
    return creationDate;
  }

  /**
   * Setter of the creationDate.
   * @param creationDate the creationDate to set.
   */
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  /**
   * Getter of the modificationDate.
   * @return the modificationDate.
   */
  public Date getModificationDate() {
    return modificationDate;
  }

  /**
   * Setter of the modificationDate.
   * @param modificationDate the modificationDate to set.
   */
  public void setModificationDate(Date modificationDate) {
    this.modificationDate = modificationDate;
  }
  
  @Override
  public PersistenceDelegate getPersistenceDelegate() {
    return new DefaultPersistenceDelegate(new String[]{"path", "type"});
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Catalogue other = (Catalogue)obj;
    if (path == null) {
      if (other.path != null) {
        return false;
      }
    } else if (!path.equals(other.path)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return getName();
  }
}
