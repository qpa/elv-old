/*
 * StandardPreparation.java
 */
package elv.task.executables;

/**
 * Generic class for implementing preparation method for standardization.
 * @author Elv
 */
public abstract class StandardPreparation extends Preparation
{
  
  /**
   * Constant.
   */
  public final static java.lang.String HEADER = "Year" + VS + "Age interval" + VS +
    "Place" + VS + "Population" + VS + "Total cases" + VS + "Analyzed cases";
    
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public StandardPreparation() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.GENDER_ARRAY, elv.util.Property.LIST_BUTTON, true, GENDERS_NAME, elv.util.parameters.Gender.getAllGenders()));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.COMBO_BOX, false, BENCHMARK_YEAR_NAME, 0));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(GENDERS_NAME, properties).setDefaultValues(elv.util.parameters.Gender.getAllGenders());
    elv.util.Property.get(BENCHMARK_YEAR_NAME, properties).setDefaultValues(elv.util.parameters.YearInterval.getAllYears());
  }
  
}
