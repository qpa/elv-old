/*
 * ExecutablePropertiesPanel.java
 */
package elv.gui;

/**
 * Class for editing properties of task executables.
 * @author Elv
 */
public class ExecutablePropertiesPanel extends PropertiesPanel
{
  
  // Variables.
  /** The owner executable. */
  private elv.task.executables.Executable executable;
  /** The owner task. */
  private elv.task.Task task;
  /** The state of the owner task. */
  private elv.util.State state;
  
  /**
   * Constructor.
   * @param executable the executable which has these properties.
   * @param task the parent task of this executable.
   * @param cloneableTask the task with the cloneable properties.
   */
  public ExecutablePropertiesPanel(elv.task.executables.Executable executable, elv.task.Task task, elv.task.Task cloneableTask)
  {
    super(executable);
    this.executable = executable;
    this.task = task;
    try
    {
      state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).getValue();
      isEnabled = (!(task.getContainer() instanceof elv.task.Archive) && 
        (state.equals(new elv.util.State(elv.util.State.UNDEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.DEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.SCHEDULED))));
      // Set state change support.
      task.getChangeSupport().addPropertyChangeListener(elv.util.State.TITLE, new java.beans.PropertyChangeListener()
      {
        public void propertyChange(java.beans.PropertyChangeEvent evt)
        {
          stateChange(evt);
        }
      });
      
      // Get propeties.
      if(state.equals(new elv.util.State(elv.util.State.UNDEFINED)))
      {
        if(cloneableTask != null)
        {
          elv.util.State cloneState = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, cloneableTask.getProperties()).getValue();
          if(!cloneState.equals(new elv.util.State(elv.util.State.UNDEFINED)))
          {
            if(cloneableTask.getContainer() instanceof elv.task.Archive)
            {
              java.lang.String name = cloneableTask.getName() + "/" + cloneableTask.PROPERTY_FOLDER + "/" + executable.getPropertyFile();
              executable.setProperties(elv.util.client.ClientStub.loadProperties(cloneableTask.getContainer().getFolderPath(), name));
            }
            else
            {
              executable.setProperties(elv.util.client.ClientStub.loadProperties(cloneableTask.getPropertyFolderPath(), executable.getPropertyFile()));
            }
          }
        }
      }
      else
      {
        if(task.getContainer() instanceof elv.task.Archive)
        {
          java.lang.String name = task.getName() + "/" + task.PROPERTY_FOLDER + "/" + executable.getPropertyFile();
          executable.setProperties(elv.util.client.ClientStub.loadProperties(task.getContainer().getFolderPath(), name));
        }
        else
        {
          executable.setProperties(elv.util.client.ClientStub.loadProperties(task.getPropertyFolderPath(), executable.getPropertyFile()));
        }
      }
      executable.setPropertiesDefaultValues();
      properties = elv.util.Property.clone(executable.getProperties());
      initPropertiesTable();
      
      if(executable instanceof elv.task.executables.Standardization && !(executable instanceof elv.task.executables.SpotStandardization))
      {
        // Set the intervals for YEAR_WINDOWS property.
        java.util.Vector<elv.util.parameters.YearInterval> intervals = null;
        try
        {
          elv.util.parameters.YearInterval yeatIntervalType = new elv.util.parameters.YearInterval();
          if(task.getContainer() instanceof elv.task.Archive)
          {
            java.lang.String name = task.getName() + "/" + task.PROPERTY_FOLDER + "/" + yeatIntervalType.getFile();
            intervals = elv.util.client.ClientStub.loadParameters(task.getContainer().getFolderPath(), name, yeatIntervalType);
          }
          else
          {
            intervals = elv.util.client.ClientStub.loadParameters(task.getPropertyFolderPath(), yeatIntervalType.getFile(), yeatIntervalType);
          }
        }
        catch(java.lang.Exception exc)
        {
          intervals = new java.util.Vector<elv.util.parameters.YearInterval>();
        }
        setIntervals(intervals);
        // Set intervals change support.
        task.getChangeSupport().addPropertyChangeListener(elv.util.parameters.YearInterval.TITLE, new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            intervalsChange(evt);
          }
        });
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {

    setLayout(new java.awt.BorderLayout());

  }// </editor-fold>//GEN-END:initComponents
  
  /**
   * Method for intervals change.
   * @param evt A <CODE>java.beans.PropertyChangeEvent</>code object.
   */
  private void intervalsChange(java.beans.PropertyChangeEvent evt)
  {
    try
    {
      setIntervals((java.util.Vector<elv.util.parameters.YearInterval>)evt.getNewValue());
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Method for setting the intervals for YEAR_WINDOWS property.
   * @param intervals a vector of intervals.
   */
  private void setIntervals(java.util.Vector<elv.util.parameters.YearInterval> intervals)
  {
    try
    {
      java.util.Vector<java.lang.Integer> defaultYearWindows = new java.util.Vector<java.lang.Integer>();
      int count = 0;
      for(elv.util.parameters.YearInterval iteratorInterval : intervals)
      {
        count++;
        defaultYearWindows.add(count);
      }
      if(count == 0)
      {
        defaultYearWindows.add(elv.task.executables.Standardization.DEFAULT_YEAR_WINDOW);
      }
      elv.util.Property.get(elv.task.executables.Standardization.YEAR_WINDOWS_NAME, properties).setDefaultValues(defaultYearWindows);
      // Test whether the actual values are proper values and remove if not, and setChanged.
      java.util.Vector<java.lang.Integer> yearWindows = (java.util.Vector<java.lang.Integer>)elv.util.Property.get(elv.task.executables.Standardization.YEAR_WINDOWS_NAME, properties).getValue();
      boolean isProper = true;
      for(java.lang.Integer iteratorYearWindow : yearWindows)
      {
        boolean found = false;
        for(java.lang.Integer iteratorDefaultYearWindow : defaultYearWindows)
        {
          if(iteratorDefaultYearWindow.equals(iteratorYearWindow))
          {
            found = true;
            break;
          }
        }
        if(!found)
        {
          yearWindows.remove(iteratorYearWindow);
          isProper = false;
        }
      }
      if(!isProper)
      {
//        ((javax.swing.table.DefaultTableModel)propertiesTable.getModel()).
        isChanged = true;
        elv.util.Util.getMainFrame().setChanged(true);
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  
  /**
   * Overriden method from <CODE>elv.gui.PropertiesPanel</CODE>.
   * @return the changeable owner object.
   */
  public elv.util.Changeable getOwner()
  {
    return task;
  }
  
  /**
   * Overriden method from <CODE>elv.gui.PropertiesPanel</CODE>.
   * @return the type object.
   */
  public java.lang.Object getType()
  {
    return executable;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   */
  public void save()
  {
    if(isChanged())
    {
      try
      {
        if(task != null)
        {
          java.lang.String pathName = task.getPropertyFolderPath() + "/" + executable.getPropertyFile();
          elv.util.client.ClientStub.storeProperties(pathName, properties);
          elv.util.Property.get(elv.task.Task.MODIFIED_NAME, task.getProperties()).setValue(new java.util.Date());
          pathName = task.getPropertyFolderPath() + "/" + task.getPropertyFile();
          elv.util.client.ClientStub.storeProperties(pathName, task.getProperties());
          // Fire property change for reload the task properties.
          task.getChangeSupport().firePropertyChange(elv.util.State.TITLE, null, state);
        }
        setChanged(false);
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(this, exc);
      }
    }
  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   * @return true if there were changes.
   */
  public boolean isChanged()
  {
    return isChanged;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   * @param isChanged boolean to set the change state.
   */
  public void setChanged(boolean isChanged)
  {
    this.isChanged = isChanged;
  }
  
  /**
   * Method for state change.
   * @param evt a <CODE>java.beans.PropertyChangeEvent</>code object.
   */
  protected void stateChange(java.beans.PropertyChangeEvent evt)
  {
    try
    {
      boolean previousIsEnabled = isEnabled;
      elv.util.State state = (elv.util.State)evt.getNewValue();
      isEnabled = (!(task.getContainer() instanceof elv.task.Archive) && 
        (state.equals(new elv.util.State(elv.util.State.UNDEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.DEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.SCHEDULED))));
      if(isEnabled != previousIsEnabled)
      {
        propertiesTable.setEnabled(isEnabled);
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  
}
