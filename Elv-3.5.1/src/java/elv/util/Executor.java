/*
 * Executor.java
 */

package elv.util;

import elv.task.Container;

/**
 * Class for executing the scheduled tasks.
 * @author Qpa
 */
public class Executor extends java.util.TimerTask
{
  
  // Constant.
  /** The delay between two checkings for scheduling. */
  private final static int DELAY = 5000;
  
  // Variables.
  /** The array of executions. */
  private java.util.Stack<elv.task.Execution> executions;
  /** The array of scheduled tasks. */
  private java.util.Stack<elv.task.Task> scheduledTasks;
  /** The scheduling timer. */
  private java.util.Timer timer;
  
  /**
   * Constructor.
   */
  public Executor() throws java.lang.Exception
  {
    executions = new java.util.Stack<elv.task.Execution>();
    scheduledTasks = new java.util.Stack<elv.task.Task>();
    timer = new java.util.Timer(true);
    timer.schedule(this, new java.util.Date());
  }
  
  /**
   * Method for getting an execution from list by task.
   * @param task the user of the execution.
   */
  public elv.task.Execution get(elv.task.Task task)
  {
    elv.task.Execution execution = null;
    for(elv.task.Execution iteratorExecution : executions)
    {
      if(iteratorExecution.getTask().equals(task))
      {
        execution = iteratorExecution;
        break;
      }
    }
    return execution;
  }
  
  /**
   * Method for getting the execution tasks.
   * @return a stack of task.
   */
  public java.util.Stack<elv.task.Task> getExecutedTasks()
  {
    java.util.Stack<elv.task.Task> tasks = new java.util.Stack<elv.task.Task>();
    for(elv.task.Execution iteratorExecution : executions)
    {
      tasks.add(iteratorExecution.getTask());
    }
    return tasks;
  }
  
  /**
   * Implemented method from <CODE>java.util.TimerTask</CODE>.
   */
  public synchronized void run()
  {
    while(true)
    {
      try
      {
        // Purge runing list.
        for(int i = executions.size() - 1; i >= 0 ; i--)
        {
          elv.task.Execution iteratorExecution = executions.get(i);
          if(!iteratorExecution.isExecuted())
          {
            executions.remove(iteratorExecution);
          }
        }
        
        if(elv.util.Util.getConcurrentCount() - executions.size() > 0)
        {
          // Array for testing if an execution has no task.
          boolean[] goodExecutions = new boolean[executions.size()];
          for(int i = 0; i < executions.size(); i++)
          {
            goodExecutions[i] = false;
          }
          elv.task.Task waitingTask = null;
          java.util.Date now = new java.util.Date();
          java.util.Date previousScheduledTime = new java.util.Date();
          elv.util.Root root = new elv.util.Root();
          root.loadChildren();
          java.util.Vector<elv.util.User> users = root.getChildren();
          for(elv.util.User iteratorUser : users)
          {
            iteratorUser.loadChildren();
            java.util.Vector<elv.task.Container> containers = iteratorUser.getChildren();
            for(elv.task.Container iteratorContainer : containers)
            {
              if(!(iteratorContainer instanceof elv.task.Archive))
              {
                iteratorContainer.loadChildren();
                java.util.Vector<elv.task.Task> tasks = iteratorContainer.getChildren();
                for(elv.task.Task iteratorTask : tasks)
                {
                  elv.task.Execution execution = null;
                  for(int k = 0; k < executions.size(); k++)
                  {
                    if(executions.get(k).getTask().equals(iteratorTask))
                    {
                      execution  = executions.get(k);
                      goodExecutions[k] = true;
                    }
                  }
                  if(execution == null)
                  {
                    elv.util.Property<elv.util.State> stateProperty = elv.util.Property.get(elv.task.Task.STATE_NAME, iteratorTask.getProperties());
                    elv.util.State state = stateProperty.getValue();
                    if(state.equals(new elv.util.State(elv.util.State.SCHEDULED)))
                    {
                      java.util.Date scheduledTime = (java.util.Date)elv.util.Property.get(elv.task.Task.SCHEDULED_NAME, iteratorTask.getProperties()).getValue();
                      if(scheduledTime != null && scheduledTime.before(now))
                      {
                        if(scheduledTime.before(previousScheduledTime))
                        {
                          waitingTask = iteratorTask;
                          previousScheduledTime = scheduledTime;
                        }
                      }
                    }
                    else if(state.equals(new elv.util.State(elv.util.State.EXECUTED)))
                    {  // Tests the killed executions.
                      stateProperty.setValue(new elv.util.State(elv.util.State.STOPPED));
                      java.lang.String pathName = iteratorTask.getPropertyFolderPath() + elv.util.Util.getFS() + iteratorTask.getPropertyFile();
                      elv.util.Property.store(pathName, iteratorTask.getProperties());
                    }
                  }
                  java.lang.Thread.yield();
                }
              }
            }
          }
        
          for(int i = 0; i < executions.size(); i++)
          {
            if(!goodExecutions[i])
            {
              executions.remove(i); // Removes the pending execution.
            }
          }

          if(waitingTask != null)
          {
            elv.task.Execution newExecution = new elv.task.Execution(waitingTask);
            executions.push(newExecution);
            newExecution.start();
          }
        }
        java.lang.Thread.yield();
        java.lang.Thread.sleep(DELAY);
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.logServerError(exc);
      }
    }
  }
  
}
