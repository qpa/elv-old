/*
 * RegionSpotting.java
 */
package elv.task.executables;

/**
 * Class for implementing the region spotting.
 * @author Elv
 */
public class RegionSpotting extends Spotting
{
  
  /**
   * Constants.
   */
  public final static java.lang.String SIZE_NAME = NAME + ".Size";
  public final static java.lang.String LOW_THRESHOLD_NAME = NAME + ".Threshold.Low";
  public final static java.lang.String HIGH_THRESHOLD_NAME = NAME + ".Threshold.High";
  public final static java.lang.String PERCENTAGE_NAME = NAME + ".SelectionPercentage";
  
  // Property values.
  public final static double DEFAULT_SIZE = 2.5;
  public final static double DEFAULT_LOW_THRESHOLD = 2.5;
  public final static double DEFAULT_HIGH_THRESHOLD = 5.5;
  public final static double DEFAULT_PERCENTAGE = 2.5;
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public RegionSpotting() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.SPINNER, false, SIZE_NAME, DEFAULT_SIZE));
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.SPINNER, false, LOW_THRESHOLD_NAME, DEFAULT_LOW_THRESHOLD));
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.SPINNER, false, HIGH_THRESHOLD_NAME, DEFAULT_HIGH_THRESHOLD));
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.SPINNER, false, PERCENTAGE_NAME, DEFAULT_PERCENTAGE));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    // Parse.
//    java.lang.Runtime.getRuntime().exec("arc_regions " + task.getExecutionFolder());
    // Wait for the external execution.
//    new jgrass.module.console.Console().
    while(!isDone(execution.getTask()))
    {
      java.lang.Thread.yield();
      java.lang.Thread.sleep(5000);
      // Return, if execution was stopped.
      if(!execution.isExecuted())
      {
        return false;
      }
// Fake code!
setDone(execution.getTask(), true);
    }
//BEGIN: Fake code!
    java.util.Vector<elv.util.parameters.District> districts = new java.util.Vector<elv.util.parameters.District>();
    districts.add(execution.getDistricts().get(0));
    districts.add(execution.getDistricts().get(1));
    districts.add(execution.getDistricts().get(2));
    districts.add(execution.getDistricts().get(3));
    districts.add(execution.getDistricts().get(4));
    districts.add(execution.getDistricts().get(5));
    SpotResult spotResult = new SpotResult(true, true, districts);
    execution.getSpots().add((new elv.util.parameters.Spot(10, "Lofi", spotResult)));
    districts = new java.util.Vector<elv.util.parameters.District>();
    districts.add(execution.getDistricts().get(6));
    districts.add(execution.getDistricts().get(7));
    districts.add(execution.getDistricts().get(8));
    districts.add(execution.getDistricts().get(9));
    districts.add(execution.getDistricts().get(10));
    districts.add(execution.getDistricts().get(11));
    districts.add(execution.getDistricts().get(12));
    districts.add(execution.getDistricts().get(13));
    districts.add(execution.getDistricts().get(14));
    spotResult = new SpotResult(true, true, districts);
    execution.getSpots().add((new elv.util.parameters.Spot(11, "Bubu", spotResult)));
    districts = new java.util.Vector<elv.util.parameters.District>();
    districts.add(execution.getDistricts().get(15));
    districts.add(execution.getDistricts().get(16));
    districts.add(execution.getDistricts().get(17));
    spotResult = new SpotResult(true, false, districts);
    execution.getSpots().add((new elv.util.parameters.Spot(12, "Tutu", spotResult)));
    districts = new java.util.Vector<elv.util.parameters.District>();
    for(int i = 18; i < execution.getDistricts().size(); i++)
    {
      districts.add(execution.getDistricts().get(i));
    }
    spotResult = new SpotResult(true, false, districts);
    execution.getSpots().add((new elv.util.parameters.Spot(13, "Lulu", spotResult)));
// END: Fake code!
    
    // Create favorable and unfavorable aggregate spots.
    java.util.Vector<elv.util.parameters.District> favorableDistricts = new java.util.Vector<elv.util.parameters.District>();
    int favorableCount = 0;
    java.util.Vector<elv.util.parameters.District> unfavorableDistricts = new java.util.Vector<elv.util.parameters.District>();
    int unfavorableCount = 0;
    for(elv.util.parameters.Spot iteratorSpot : execution.getSpots())
    {
      if(iteratorSpot.getResult().isSelected)
      {
        for(elv.util.parameters.District iteratorDistrict : iteratorSpot.getResult().districts)
        {
          if(iteratorSpot.getResult().isFavorable)
          {
            favorableDistricts.add(iteratorDistrict);
            favorableCount++;
          }
          else
          {
            unfavorableDistricts.add(iteratorDistrict);
            unfavorableCount++;
          }
        }
      }
    }
    SpotResult unfavorableSpotResult = new SpotResult(true, false, unfavorableDistricts);
    execution.getSpots().add(new elv.util.parameters.Spot(elv.util.parameters.Spot.UNFAVORABLE_CODE, "", unfavorableSpotResult));
    SpotResult favorableSpotResult = new SpotResult(true, true, favorableDistricts);
    execution.getSpots().add(new elv.util.parameters.Spot(elv.util.parameters.Spot.FAVORABLE_CODE, "", favorableSpotResult));
    
   return true;
  }
  
}
