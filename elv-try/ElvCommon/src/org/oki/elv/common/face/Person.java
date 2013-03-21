package org.oki.elv.common.face;

import org.oki.elv.common.util.Persistence;
import org.oki.elv.common.util.Path;
import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Person.
 * @author Elv
 */
public final class Person extends Path implements Persistence, Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = 6811926738188326747L;

  /** Person types. */
  public static enum TYPE {
    /** Administrator type. */
    ADMINISTRATOR,
    /** Single type. */
    SINGLE,
    /** Comunity type. */
    COMMUNITY
  };
  /** The property file name. */
  public static final String PROPERTY_FILE_NAME = "person.xml";
  /** The type of the person. */
  private TYPE type;
  /** The description of the person. */
  private String description = "";
  /** The password of the person. */
  private String password;
  /** The locale of the person. */
  private Locale locale;
  /** The "look and feel" class name of the person. */
  private String lookAndFeelClass;
  /** The refreshing delay of the client interface. */
  private int refreshingDelay;

  /**
   * Constructor.
   * @param path
   * @param type
   */
  public Person(String path, TYPE type) {
    if(path == null) {
      throw new NullPointerException("Null path!");
    }
    else if(path.equals("")) {
      throw new IllegalArgumentException("Empty path!");
    }
    this.path = path;
    this.type = type;
    // Default values.
    password = getName();
    locale = Locale.getDefault();
    lookAndFeelClass = "";
    refreshingDelay = 5000;
  }

  /**
   * Contructor.
   */
  public Person() {
  }

  /**
   * Getter of the type.
   * @return the type.
   */
  public TYPE getType() {
    return type;
  }

  /**
   * Setter of the type.
   * @param type the person type to set.
   */
  public void setType(TYPE type) {
    this.type = type;
  }

  /**
   * Getter of the description.
   * @return the person description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter of the description.
   * @param description the person description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Getter of the password.
   * @return the person password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Setter of the password.
   * @param password the person password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Getter of the locale.
   * @return the person locale.
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Setter of the locale.
   * @param locale the person locale to set.
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  /**
   * Getter of the "look and feel" class name.
   * @return the person "look and feel".
   */
  public String getLookAndFeelClass() {
    return lookAndFeelClass;
  }

  /**
   * Setter of the "look and feel" class name.
   * @param lookAndFeelClass the person "look and feel" to set.
   */
  public void setLookAndFeelClass(String lookAndFeelClass) {
    this.lookAndFeelClass = lookAndFeelClass;
  }

  /**
   * Getter of the refreshing delay.
   * @return the time in miliseconds between two refreshes of the client interface.
   */
  public int getRefreshingDelay() {
    return refreshingDelay;
  }

  /**
   * Setter of the refreshing delay.
   * @param refreshingDelay the value of refreshing delay to set.
   * @see #getRefreshingDelay()
   */
  public void setRefreshingDelay(int refreshingDelay) {
    this.refreshingDelay = refreshingDelay;
  }

  /**
   * Getter of the relatives.
   * @param persons the list of existing persons.
   * @return a list of relatives.
   */
  public List<Person> getRelatives(List<Person> persons) {
    List<Person> relatives = new ArrayList<Person>();
    if(type == TYPE.ADMINISTRATOR) {
      relatives = persons;
    }
    else if(type == TYPE.SINGLE) {
      relatives.add(this);
    }
    else {
      for(Person iteratorPerson : persons) {
        if(iteratorPerson.getType() == type) {
          relatives.add(iteratorPerson);
        }
      }
    }
    return relatives;
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
    if(this == obj) {
      return true;
    }
    if(obj == null) {
      return false;
    }
    if(getClass() != obj.getClass()) {
      return false;
    }
    final Person other = (Person)obj;
    if(path == null) {
      if(other.path != null) {
        return false;
      }
    }
    else if(!path.equals(other.path)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return getName();
  }

  /**
   * Finder of the named person.
   */
  public static final Person findNamedPerson(List<Person> persons, String name) {
    for(Person iteratorPerson : persons) {
      if(iteratorPerson.getName().equals(name)) {
        return iteratorPerson;
      }
    }
    return null;
  }
}
