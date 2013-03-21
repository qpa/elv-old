/**
 * App.java
 */
package org.oki.elv.client.gui;

import java.awt.Window;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.SingleFrameApplication;
import org.oki.elv.common.face.Person;

/**
 * The main class of the application.
 */
public class App extends SingleFrameApplication {
  /** Actual person. */
  private Person person;
  /** Actual locale. */
  private Locale locale = Locale.getDefault();
  /** Actual collator. */
  private Collator collator = Collator.getInstance(locale);
  /** The view. */
  View view;

  @Override
  protected void initialize(String[] arg0) {
    try {
      super.initialize(arg0);
//      List<Person> persons = Persons.loadAll();
      List<Person> persons = new ArrayList<Person>();
      Person aPerson = new Person("qpa", Person.TYPE.ADMINISTRATOR);
      aPerson.setPassword("qpa");
      persons.add(aPerson);
      
      LoginDialog loginDialog = new LoginDialog(persons);
      loginDialog.setLocationRelativeTo(null);
      show(loginDialog);
      person = loginDialog.getLoggedPerson();
      locale = aPerson.getLocale();
      collator = Collator.getInstance(locale);
    } catch (Exception ex) {
      Dialogs.showException(null, ex);
      exit();
    }
  }
  
  /**
   * At startup create and show the main frame of the application.
   */
  @Override
  protected void startup() {
    view = new View(this);
    show(view);
  }

  /**
   * This method is to initialize the specified window by injecting resources.
   * Windows shown in our application come fully initialized from the GUI
   * builder, so this additional configuration is not needed.
   */
  @Override
  protected void configureWindow(Window root) {
  }

  @Override
  protected void shutdown() {
    super.shutdown();
    try {
      view.storeDockModel();
    } catch (IOException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * A convenient static getter for the application instance.
   * @return the instance of Application
   */
  public static App getApplication() {
    return App.getInstance(App.class);
  }
  
  /**
   * Getter of the person.
   * @return the person.
   */
  public final Person getPerson() {
    return person;
  }
  
  /**
   * Getter of the locale.
   * @return the locale.
   */
  public final Locale getLocale() {
    return locale;
  }
  
  /**
   * Getter of the collator.
   * @return the collator.
   */
  public final Collator getCollator() {
    return collator;
  }

  /**
   * Main method launching the application.
   */
  public static void main(String[] args) {
    launch(App.class, args);
  }
}
