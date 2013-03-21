/**
 * Executor.java
 */
package org.oki.elv.server.engine;

import java.sql.SQLException;
import java.util.*;

import javax.naming.NamingException;

import org.oki.elv.server.io.Logs;

/**
 * Executes the scheduled tasks.
 * @author Elv
 */
public final class Executor extends TimerTask {
  /** The delay between two checkings for scheduling. */
  private int delay = 5000;
  /** The count of concurrently executed tasks. */
  private int concurrentCount = 1;
  /** The array of executions. */
  private Stack<elv.task.Execution> executions;
  /** The array of scheduled tasks. */
  private Stack<elv.task.Task> scheduledTasks;
  /** The scheduling timer. */
  private Timer timer;
  /** Singleton instance of this class. */
  private static Executor instance = null;
  
  /**
   * Getter of the instance.
   * @return the singleton instance of this class.
   * @throws SQLException 
   * @throws NamingException 
   */
  public static Executor getInstance() {
    if(instance == null) {
      instance = new Executor();
    }
    return instance;
  }
  
  /** Private constructor. */
  private Executor() {
    executions = new java.util.Stack<elv.task.Execution>();
    scheduledTasks = new java.util.Stack<elv.task.Task>();
    timer = new java.util.Timer(true);
    timer.schedule(this, new java.util.Date());
  }
  
  /**
   * Getter of the concurrent count.
   * @return the concurrentCount
   */
  int getConcurrentCount() {
    return concurrentCount;
  }

  /**
   * Setter of the concurrent count.
   * @param concurrentCount the concurrentCount to set
   */
  void setConcurrentCount(int concurrentCount) {
    this.concurrentCount = concurrentCount;
  }
  
  /**
   * Getter of the delay.
   * @return the delay
   */
  int getDelay() {
    return delay;
  }

  /**
   * Setter of the delay.
   * @param delay the delay to set
   */
  void setDelay(int delay) {
    this.delay = delay;
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
        
        if(concurrentCount - executions.size() > 0)
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
          ServerStub.getRoot().loadChildren();
          java.util.Vector<elv.util.ServerUser> users = ServerStub.getRoot().getChildren();
          for(elv.util.ServerUser iteratorUser : users)
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
                      java.lang.String pathName = iteratorTask.getPropertyFolderPath() + "/" + iteratorTask.getPropertyFile();
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
        elv.util.Logs.logServerError(exc);
      }
    }
  }
}
