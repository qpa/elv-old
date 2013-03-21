/*
 * ClosableTabbedPane.java
 */
package elv.gui;

/**
 * Class for representing a tabbed frame with closable tabs.
 * @author Elv
 */
public class ClosableTabbedPane extends javax.swing.JTabbedPane
{
  
  // Variable.
  /** The main frame. */
  private Manager mainFrame;
  /** The owner object. */
  private java.lang.Object owner;
  
  /**
   * Constructor.
   * @param mainFrame the main frame.
   * @param owner the owner object.
   */
  public ClosableTabbedPane(Manager mainFrame, java.lang.Object owner)
  {
    this.mainFrame = mainFrame;
    this.owner = owner;
    initComponents();
  }
  
  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents()
  {
  }// </editor-fold>//GEN-END:initComponents
  
  /**
   * Method for getting the owner object.
   * @return the owner object.
   */
  public java.lang.Object getOwner()
  {
    return owner;
  }
  /**
   * Method for adding a new tab, with the same parameters like at the <CODE>javax.swing.JTabbedPane</CODE>.
   * @param title the title text of the tab.
   * @param icon the icon text of the tab.
   * @param component the component which the tab contains.
   * @param toolTipText the tooltip text of the tab.
   */
  public void addTab(java.lang.String title, javax.swing.Icon icon, java.awt.Component component, java.lang.String toolTipText)
  {
    try
    {
      int index = -1;
      elv.util.Saveable newSaveable = (elv.util.Saveable)component;
      for(int i = 0; i < getTabCount(); i++)
      {
        elv.util.Saveable iteratorSaveable = (elv.util.Saveable)getComponentAt(i);
        if(iteratorSaveable.getOwner().equals(newSaveable.getOwner()))
        {
          if(newSaveable.getType() != null)
          {
            if(newSaveable.getType().getClass().isInstance(iteratorSaveable.getType()) &&
               newSaveable.getType().equals(iteratorSaveable.getType()))
            {
              index = i;
              break;
            }
          }
          else
          {
            index = i;
            break;
          }
        }
      }
      if(index == -1) // Not found.
      {
        super.addTab(null, component);
        index = getTabCount() - 1;
        setTabComponentAt(index, new TabTitle(title, icon, toolTipText));
        
        javax.swing.event.SwingPropertyChangeSupport changeSupport = newSaveable.getOwner().getChangeSupport();
        if(component instanceof DiagnosisesPanel)
        {
          changeSupport.addPropertyChangeListener(elv.task.executables.Preparation.NAME, new java.beans.PropertyChangeListener()
          {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
              deleteChange(evt);
            }
          });
        }
        changeSupport.addPropertyChangeListener(new elv.util.Action(elv.util.Action.RENAME).getName(), new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            renameChange(evt);
          }
        });
        changeSupport.addPropertyChangeListener(new elv.util.Action(elv.util.Action.DELETE).getName(), new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            deleteChange(evt);
          }
        });
        changeSupport.addPropertyChangeListener(new elv.util.Action(elv.util.Action.CLEAN).getName(), new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            deleteChange(evt);
          }
        });
        changeSupport.addPropertyChangeListener(new elv.util.Action(elv.util.Action.REWIND).getName(), new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            deleteChange(evt);
          }
        });
      }
      setSelectedIndex((index >= 0 ? index : getTabCount() - 1));
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(mainFrame, exc);
    }
  }
  
  /**
   * Method for delete change.
   * @param evt a <CODE>java.beans.PropertyChangeEvent</>code object.
   */
  private void deleteChange(java.beans.PropertyChangeEvent evt)
  {
    try
    {
      for(int i = 0; i < getTabCount(); i++)
      {
        if(((elv.util.Saveable)getComponentAt(i)).getOwner().equals(evt.getOldValue()))
        {
          removeTabAt(i);
        }
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(mainFrame, exc);
    }
  }
  
  /**
   * Method for rename change.
   * @param evt a <CODE>java.beans.PropertyChangeEvent</>code object.
   */
  private void renameChange(java.beans.PropertyChangeEvent evt)
  {
    try
    {
      for(int i = 0; i < getTabCount(); i++)
      {
        if(((elv.util.Saveable)getComponentAt(i)).getOwner().equals(evt.getOldValue()))
        {
          java.lang.String newTabTitle = ((java.lang.String[])evt.getNewValue())[0];
          java.lang.String newTabToolTipText = ((java.lang.String[])evt.getNewValue())[1];
          java.lang.Object type = ((elv.util.Saveable)getComponentAt(i)).getType();
          if(type != null)
          {
            if(type instanceof elv.util.parameters.Parameter)
            {
              newTabTitle += elv.util.Util.TITLE_SEPARATOR + ((elv.util.parameters.Parameter)type).getTitle();
            }
            else
            {
              newTabTitle += elv.util.Util.TITLE_SEPARATOR + type;
            }
          }
          ((TabTitle)getTabComponentAt(i)).setTitle(newTabTitle);
          ((TabTitle)getTabComponentAt(i)).setToolTipText(newTabToolTipText);
        }
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(mainFrame, exc);
    }
  }
  
  /** Inner class for tab title reprezentation. */
  public class TabTitle extends javax.swing.JPanel
  {
    /** The title text. */
    private java.lang.String  titleText;
    /** The title label. */
    private javax.swing.JLabel  titleLabel;
    /** The changed state. */
    private boolean isChanged;
    
    private TabTitle(java.lang.String titleText, javax.swing.Icon icon, java.lang.String toolTipText)
    {
      this.titleText = titleText;
      titleLabel = new javax.swing.JLabel(titleText);
      titleLabel.setIcon(icon);
      titleLabel.setIconTextGap(2);
// TODO: Need workaround!!!! Set the MouseMotionListeners of the tabbed pane.
//        titleLabel.setToolTipText(toolTipText);
      elv.util.Action closeAction = new elv.util.Action(elv.util.Action.CLOSE);
      final javax.swing.JButton closeButton = new javax.swing.JButton(closeAction.getIcon());
      closeButton.setToolTipText(closeAction.toString());
      closeButton.setFocusable(false);
      closeButton.setBorderPainted(false);
      closeButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      closeButton.setContentAreaFilled(false); // Use setContentAreaFilled instead of setOpaque()!!!
      closeButton.addActionListener(new java.awt.event.ActionListener()
      {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
          int i = ClosableTabbedPane.this.indexOfTabComponent(TabTitle.this);
          if(i != -1)
          {
            if(ClosableTabbedPane.this.getComponentAt(i) instanceof elv.util.Saveable &&
               ((elv.util.Saveable)ClosableTabbedPane.this.getComponentAt(i)).isChanged())
            {
              int returnOption = elv.util.Option.yesNoCancel(new elv.util.Action(elv.util.Action.SAVE).getQuestion(), elv.util.Util.translate(new elv.util.Action(elv.util.Action.SAVE).getName()));
              if(returnOption == javax.swing.JOptionPane.YES_OPTION)
              {
                ((elv.util.Saveable)ClosableTabbedPane.this.getComponentAt(i)).save();
                TabTitle.this.setChanged(false);
                mainFrame.setActions ();
              }
              else if(returnOption == javax.swing.JOptionPane.CANCEL_OPTION)
              {
                return;
              }
            }
            ClosableTabbedPane.this.removeTabAt(i);
            if(ClosableTabbedPane.this.getTabCount() == 0)
            {
              java.awt.Container parent = ClosableTabbedPane.this.getParent();
              if(parent instanceof javax.swing.JSplitPane)
              {
                java.awt.Component sibling = ((javax.swing.JSplitPane)parent).getRightComponent() == ClosableTabbedPane.this ?
                  ((javax.swing.JSplitPane)parent).getLeftComponent() :((javax.swing.JSplitPane)parent).getRightComponent();
                java.awt.Container parentOfParent = parent.getParent();
                int dividerLocation = 0;
                if(parentOfParent instanceof javax.swing.JSplitPane)
                {
                  dividerLocation = ((javax.swing.JSplitPane)parentOfParent).getDividerLocation();
                }
                parent.remove(ClosableTabbedPane.this);
                parent.remove(sibling);
                parentOfParent.remove(parent);
                parentOfParent.add(sibling);
                if(parentOfParent instanceof javax.swing.JSplitPane)
                {
                  ((javax.swing.JSplitPane)parentOfParent).setDividerLocation(dividerLocation);
                }
                ((javax.swing.JComponent)parentOfParent).revalidate();
              }
              else
              {
                parent.remove(ClosableTabbedPane.this);
                ((javax.swing.JComponent)parent).revalidate();
              }
              repaint();
            }
          }
        }
      });
      closeButton.addMouseMotionListener(new java.awt.event.MouseMotionListener()
      {
        public void mouseDragged(java.awt.event.MouseEvent evt)
        {
          closeButton.setContentAreaFilled(false);
        }

        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
          closeButton.setContentAreaFilled(true);
        }
      });
      javax.swing.JToolBar tabTitleToolBar = new javax.swing.JToolBar();
      tabTitleToolBar.setOpaque(false);
      tabTitleToolBar.setFloatable(false);
      tabTitleToolBar.add(closeButton);
      setOpaque(false);
      setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 0));
      add(titleLabel);
      add(tabTitleToolBar);

      ClosableTabbedPane.this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
      {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
          closeButton.setContentAreaFilled(false);
        }
      });
      isChanged = false;
    }
    
    /**
     * Method for getting the tab title.
     * @return the tab title.
     */
    public java.lang.String getTitle()
    {
      return titleText;
    }
    
    /**
     * Method for setting the tab title.
     * @param title the new tab title.
     */
    public void setTitle(java.lang.String title)
    {
      titleLabel.setText(titleText + (isChanged ? elv.util.Util.CHANGED_MARK : ""));
    }
    
    /**
     * Method for setting the tab tooltip text.
     * @param toolTipText the new tooltip text.
     */
    public void setToolTipText(java.lang.String toolTipText)
    {
// TODO: Need workaround!!!! Set the MouseMotionListeners of the tabbed pane.
//      titleLabel.setToolTipText(toolTipText);
    }
    
    /**
     * Method for getting the changed state.
     * @return true, if the tab title is followed by the CHANGED_MARK.
     */
    public boolean isChanged()
    {
      return isChanged;
    }
    
    /**
     * Method for setting changed state (also on the tab title).
     * @param isChanged if true, the tab title followed by the CHANGED_MARK.
     */
    public void setChanged(boolean isChanged)
    {
      this.isChanged = isChanged;
      titleLabel.setText(titleText + (isChanged ? elv.util.Util.CHANGED_MARK : ""));
    }
    
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  
}