/*
 * Node.java
 */
package elv.util;

/**
 * Class for a main tree node.
 * @author Qpa
 */
public class Node extends javax.swing.tree.DefaultMutableTreeNode implements java.io.Serializable
{
 
  // Constant.
  /** The dummy node object. */
  public static java.lang.String DUMMY = "DUMMY";
  
  // Variable.
  /** The state of the node. */
  private State state;
  
  /**
   * Constructor.
   * @param nodeObject the object, which represents the node.
   * @param isExpanded boolean for signaling.
   * @param state the state of node.
   */
  public Node(java.lang.Object nodeObject, State state)
  {
    super(nodeObject);
    this.state = state;
  }
  
  /**
   * Constructor.
   * @param nodeObject the object, which represents the node.
   */
  public Node(java.lang.Object nodeObject)
  {
    this(nodeObject, new State(State.UNDEFINED));
    if(nodeObject instanceof elv.task.Task)
    {
      elv.task.Task task = (elv.task.Task)nodeObject;
      try
      {
        state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).getValue();
      }
      catch (Exception ex)
      {
      }
    }
  }
  
  /**
   * Method for getting the node state.
   * @return the state of the node.
   */
  public State getState()
  {
    return state;
  }
  
  /**
   * Method for setting the node state.
   * @param the new state of the node.
   */
  public void setState(State state)
  {
    this.state = state;
  }
  
  /**
   * Method for getting the node icon.
   * @return the icon of the node.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    javax.swing.ImageIcon icon = null;
    if(getUserObject() instanceof Visualizable)
    {
      icon = ((Visualizable)getUserObject()).getIcon();
    }
    return icon;
  }
  
  /**
   * Method for getting the state icon.
   * @return the state icon of the node.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getStateIcon() throws java.lang.Exception
  {
    return state.getIcon();
  }
  
  /**
   * Method for getting the tool-tip text.
   * @return the tool-tip of the node.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public java.lang.String getToolTipText() throws java.lang.Exception
  {
    java.lang.String text = null;
    java.util.Vector<java.lang.String> propertyNames = new java.util.Vector<java.lang.String>();
    if(getUserObject() instanceof Root)
    {
      text = elv.util.Util.translate(elv.util.Util.TITLE);
    }
    else if(getUserObject() instanceof User)
    {
      propertyNames.add(User.TYPE_NAME);
      propertyNames.add(User.DESCRIPTION_NAME);
      text = "<html><body>" + getPropertiesTable(propertyNames) + "</body></html>";
    }
    else if(getUserObject() instanceof elv.task.Container)
    {
      text = "<html><body>" + getPropertiesTable(propertyNames) + "</body></html>";
    }
    else if(getUserObject() instanceof elv.task.Task)
    {
      text = "<html><body>" + getPropertiesTable(propertyNames) + "</body></html>";
    }
    return text;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param node a node.
   * @return true, if this object equals with the given object.
   */
  public boolean equals(java.lang.Object node)
  {
    boolean isEqual = false;
    if(node != null && node instanceof Node)
    {
      isEqual = (getUserObject().equals(((Node)node).getUserObject()));
    }
    return isEqual;
  }
  
  /**
   * Overriddem method from <CODE>java.lang.Object</CODE>.
   */
  public java.lang.String toString()
  {
    return getUserObject().toString();
  }
  
  /**
   * Method for build a html table of properties with names matching the given property names
   * or all properties if there are no names specified.
   * @param propertyNames the vector of property names.
   */
  private java.lang.String getPropertiesTable(java.util.Vector<java.lang.String> propertyNames)
  {
    java.lang.String tableText = "";
    if(getUserObject() instanceof elv.util.Propertable)
    {
      elv.util.Propertable propertable = (elv.util.Propertable)getUserObject();
      tableText = "<table cellpadding=1 cellspacing=0>";
      for(elv.util.Property iteratorProperty : propertable.getProperties())
      {
        if(propertyNames == null || propertyNames.size() == 0)
        {
          tableText += "<tr><td><b>" + elv.util.Util.translate(iteratorProperty.getName()) + ":</b></td><td>" + iteratorProperty + "</td></tr>";
        }
        else
        {
          for(java.lang.String iteratorPropertyName : propertyNames)
          {
            if(iteratorProperty.getName().equals(iteratorPropertyName))
            {
              tableText += "<tr><td><b>" + elv.util.Util.translate(iteratorProperty.getName()) + ":</b></td><td>" + iteratorProperty + "</td></tr>";
            }
          }
        }
      }
      tableText += "</table>";
    }
    return tableText;
  }
  
}
