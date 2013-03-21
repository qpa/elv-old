/*
 * SettlementsPanel.java
 */
package elv.gui;

/**
 * Class for editing one type of settlements.
 * @author Qpa
 */
public class SettlementsPanel extends javax.swing.JPanel implements elv.util.Saveable
{
  
  // Variables.
  /** The owner task. */
  private elv.task.Task task;
  /** The type of settlements. */
  private elv.util.parameters.Settlement parameterType;
  /** The settlements. */
  private java.util.Vector<elv.util.parameters.Settlement> settlements;
  /** The districts (aggregated settlements). */
  private java.util.Vector<elv.util.parameters.District> districts;
  /** The changed state of settlements and districts. */
  private boolean isChanged;
  /** The state of the owner task. */
  private elv.util.State state;
  /** The state of changeability. */
  private boolean isEnabled = true;
  /** The button for aggregation. */
  private javax.swing.JButton aggregateButton;
  /** The button for list the districts.*/
  private javax.swing.JButton districtButton;
  /** The tree of districts. */
  private javax.swing.JTree districtsTree;
  
  /**
   * Constructor.
   * @param task the owner task.
   * @param parameterType the type of settlements.
   * @param cloneableTask the task with the cloneable settlements.
   */
  public SettlementsPanel(elv.task.Task task, elv.util.parameters.Settlement parameterType, elv.task.Task cloneableTask)
  {
    this.task = task;
    this.parameterType = parameterType;
    try
    {
      state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).getValue();
      isEnabled = (!(task.getContainer() instanceof elv.task.Archive) && 
        (state.equals(new elv.util.State(elv.util.State.UNDEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.DEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.SCHEDULED))));
      if(state.equals(new elv.util.State(elv.util.State.UNDEFINED)))
      {
        settlements = new java.util.Vector<elv.util.parameters.Settlement>();
        districts = new java.util.Vector<elv.util.parameters.District>();
        if(cloneableTask != null)
        {
          elv.util.State cloneState = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, cloneableTask.getProperties()).getValue();
          if(!cloneState.equals(new elv.util.State(elv.util.State.UNDEFINED)))
          {
            if(cloneableTask.getContainer() instanceof elv.task.Archive)
            {
              java.lang.String name = cloneableTask.getName() + elv.util.Util.ZIP_SEPARATOR + cloneableTask.PROPERTY_FOLDER + elv.util.Util.ZIP_SEPARATOR + parameterType.getFile();
              settlements = elv.util.client.ClientStub.loadParameters(cloneableTask.getContainer().getFolderPath(), name, parameterType);
              elv.util.parameters.District districtType = new elv.util.parameters.District();
              name = cloneableTask.getName() + elv.util.Util.ZIP_SEPARATOR + cloneableTask.PROPERTY_FOLDER + elv.util.Util.ZIP_SEPARATOR + districtType.getFile();
              districts = elv.util.client.ClientStub.loadParameters(cloneableTask.getContainer().getFolderPath(), name, districtType);
            }
            else
            {
              settlements = elv.util.client.ClientStub.loadParameters(cloneableTask.getPropertyFolderPath(), parameterType.getFile(), parameterType);
              districts = elv.util.client.ClientStub.loadParameters(cloneableTask.getPropertyFolderPath(), new elv.util.parameters.District().getFile(), new elv.util.parameters.District());
            }
          }
        }
      }
      else
      {
        if(task.getContainer() instanceof elv.task.Archive)
        {
          java.lang.String name = task.getName() + elv.util.Util.ZIP_SEPARATOR + task.PROPERTY_FOLDER + elv.util.Util.ZIP_SEPARATOR + parameterType.getFile();
          settlements = elv.util.client.ClientStub.loadParameters(task.getContainer().getFolderPath(), name, parameterType);
          elv.util.parameters.District districtType = new elv.util.parameters.District();
          name = task.getName() + elv.util.Util.ZIP_SEPARATOR + task.PROPERTY_FOLDER + elv.util.Util.ZIP_SEPARATOR + districtType.getFile();
          districts = elv.util.client.ClientStub.loadParameters(task.getContainer().getFolderPath(), name, districtType);
        }
        else
        {
          settlements = elv.util.client.ClientStub.loadParameters(task.getPropertyFolderPath(), parameterType.getFile(), parameterType);
          districts = elv.util.client.ClientStub.loadParameters(task.getPropertyFolderPath(), new elv.util.parameters.District().getFile(), new elv.util.parameters.District());
        }
      }
      // Set state change support.
      task.getChangeSupport().addPropertyChangeListener(elv.util.State.TITLE, new java.beans.PropertyChangeListener()
      {
        public void propertyChange(java.beans.PropertyChangeEvent evt)
        {
          stateChange(evt);
        }
      });
      if(parameterType instanceof elv.util.parameters.BenchmarkSettlement &&
        (task.getType() == elv.task.Task.MORTALITY_POINT_SOURCE_ANALYSIS ||
         task.getType() == elv.task.Task.MORBIDITY_POINT_SOURCE_ANALYSIS))
      {
        // Set "pointSettlement" change support.
        task.getChangeSupport().addPropertyChangeListener(task.getName(), new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            pointChange(evt);
          }
        });
      }
      
      initComponents();
      if(parameterType instanceof elv.util.parameters.BaseSettlement &&
         (task.getType() != elv.task.Task.MORTALITY_SELECTION &&
          task.getType() != elv.task.Task.MORBIDITY_SELECTION))
      {
        initAggregateButtons();
      }
      setEnabled(isEnabled);
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
  private void initComponents()//GEN-BEGIN:initComponents
  {
    aggregatePopupMenu = new javax.swing.JPopupMenu();
    noAggregationMenuItem = new javax.swing.JMenuItem();
    customAggregationMenuItem = new javax.swing.JMenuItem();
    mmAggregationMenuItem = new javax.swing.JMenuItem();
    settlementsScrollPane = new javax.swing.JScrollPane();
    settlementsTree = new javax.swing.JTree();
    settlementsButtonOrientationPanel = new javax.swing.JPanel();
    settlementsButtonPanel = new javax.swing.JPanel();
    addButton = new javax.swing.JButton();
    removeButton = new javax.swing.JButton();

    noAggregationMenuItem.setText(new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO).toString());
    noAggregationMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        noAggregationMenuItemActionPerformed(evt);
      }
    });

    aggregatePopupMenu.add(noAggregationMenuItem);

    customAggregationMenuItem.setText(new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.CUSTOM).toString());
    customAggregationMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        customAggregationMenuItemActionPerformed(evt);
      }
    });

    aggregatePopupMenu.add(customAggregationMenuItem);

    mmAggregationMenuItem.setText(new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.MM).toString());
    mmAggregationMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        mmAggregationMenuItemActionPerformed(evt);
      }
    });

    aggregatePopupMenu.add(mmAggregationMenuItem);

    setLayout(new java.awt.BorderLayout());

    settlementsTree.setCellRenderer(new elv.util.Util.DefaultTreeCellRenderer());
    settlementsTree.setModel(new SettlementsTreeModel());
    settlementsTree.setRootVisible(false);
    settlementsTree.setShowsRootHandles(true);
    settlementsTree.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        settlementsTreeMouseClicked(evt);
      }
    });

    settlementsScrollPane.setViewportView(settlementsTree);

    add(settlementsScrollPane, java.awt.BorderLayout.CENTER);

    settlementsButtonOrientationPanel.setLayout(new java.awt.BorderLayout());

    settlementsButtonPanel.setLayout(new java.awt.GridLayout(0, 1));

    addButton.setText("+");
    addButton.setToolTipText(new elv.util.Action(elv.util.Action.ADD).toString());
    addButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        addButtonActionPerformed(evt);
      }
    });

    settlementsButtonPanel.add(addButton);

    removeButton.setText("-");
    removeButton.setToolTipText(new elv.util.Action(elv.util.Action.REMOVE).toString());
    removeButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        removeButtonActionPerformed(evt);
      }
    });

    settlementsButtonPanel.add(removeButton);

    settlementsButtonOrientationPanel.add(settlementsButtonPanel, java.awt.BorderLayout.NORTH);

    add(settlementsButtonOrientationPanel, java.awt.BorderLayout.EAST);

  }//GEN-END:initComponents

  private void settlementsTreeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_settlementsTreeMouseClicked
  {//GEN-HEADEREND:event_settlementsTreeMouseClicked
    setEnabled(isEnabled);
  }//GEN-LAST:event_settlementsTreeMouseClicked

  /**
   * Method for no aggregation menu item.
   */
  private void noAggregationMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_noAggregationMenuItemActionPerformed
  {//GEN-HEADEREND:event_noAggregationMenuItemActionPerformed
    settlementsAggregateAction(elv.util.parameters.Aggregation.NO);
  }//GEN-LAST:event_noAggregationMenuItemActionPerformed

  /**
   * Method for 2000-type aggregation menu item.
   */
  private void mmAggregationMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mmAggregationMenuItemActionPerformed
  {//GEN-HEADEREND:event_mmAggregationMenuItemActionPerformed
    settlementsAggregateAction(elv.util.parameters.Aggregation.MM);
  }//GEN-LAST:event_mmAggregationMenuItemActionPerformed

  /**
   * Method for custom aggregation menu item.
   */
  private void customAggregationMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_customAggregationMenuItemActionPerformed
  {//GEN-HEADEREND:event_customAggregationMenuItemActionPerformed
    settlementsAggregateAction(elv.util.parameters.Aggregation.CUSTOM);
  }//GEN-LAST:event_customAggregationMenuItemActionPerformed

  /**
   * Method for inintializing the aggregation buttons.
   */
  private void initAggregateButtons()
  {
    try
    {
      aggregateButton = new javax.swing.JButton();
      districtButton = new javax.swing.JButton();
      
      aggregateButton.setText("\u2192\u2190");
      aggregateButton.setToolTipText(new elv.util.Action(elv.util.Action.AGGREGATE).toString());
      aggregateButton.addActionListener(new java.awt.event.ActionListener()
      {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
          aggregateButtonActionPerformed(evt);
        }
      });
      settlementsButtonPanel.add(aggregateButton);
      
      districtButton.setText("...");
      districtButton.setToolTipText(new elv.util.Action(elv.util.Action.DISTRICTS).toString());
      districtButton.addActionListener(new java.awt.event.ActionListener()
      {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
          districtButtonActionPerformed(evt);
        }
      });
      settlementsButtonPanel.add(districtButton);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Method for Add button action.
   */
  private void addButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addButtonActionPerformed
  {//GEN-HEADEREND:event_addButtonActionPerformed
    try
    {
      javax.swing.JTree allSettlementsTree = new javax.swing.JTree();
      if(parameterType instanceof elv.util.parameters.BaseSettlement &&
        (task.getType() == elv.task.Task.MORTALITY_POINT_SOURCE_ANALYSIS ||
         task.getType() == elv.task.Task.MORBIDITY_POINT_SOURCE_ANALYSIS))
      {
        allSettlementsTree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
      }
      allSettlementsTree.setRootVisible(false);
      allSettlementsTree.setShowsRootHandles(true);
      allSettlementsTree.setCellRenderer(new elv.util.Util.DefaultTreeCellRenderer());
      allSettlementsTree.setModel(new javax.swing.tree.DefaultTreeModel(new javax.swing.tree.DefaultMutableTreeNode())
      {
        /**
         * Overridden constructor.
         */
        {
          javax.swing.tree.DefaultMutableTreeNode nodeLevels[] = new javax.swing.tree.DefaultMutableTreeNode[10];
          javax.swing.tree.DefaultMutableTreeNode node = null;
          nodeLevels[0] = new javax.swing.tree.DefaultMutableTreeNode(parameterType.parseInit(elv.util.parameters.Settlement.DEFAULT_LINE));
          java.util.Vector<elv.util.parameters.Settlement> settlements = parameterType.getAllSettlements();
          for(elv.util.parameters.Settlement iteratorSettlement : settlements)
          {
            java.lang.String[] pars = iteratorSettlement.getParagraph().split(java.util.regex.Pattern.quote("."));
            java.lang.String country = pars[0];
            if(country.equals("1")) // Hungary.
            {
              node = new javax.swing.tree.DefaultMutableTreeNode(iteratorSettlement);
              nodeLevels[iteratorSettlement.getParagraphLevel()] = node;
              nodeLevels[iteratorSettlement.getParagraphLevel() - 1].add(node);
            }
          }
          setRoot(nodeLevels[0]);
        }
      });
      javax.swing.JScrollPane allSettlementsScrollPane = new javax.swing.JScrollPane();
      allSettlementsScrollPane.setViewportView(allSettlementsTree);
      int returnOption = elv.util.Option.okCancel(allSettlementsScrollPane, parameterType.getTitle());
      if(returnOption == javax.swing.JOptionPane.OK_OPTION && allSettlementsTree.getSelectionPaths() != null)
      {
        javax.swing.tree.DefaultTreeModel treeModel = (javax.swing.tree.DefaultTreeModel)settlementsTree.getModel();
        javax.swing.tree.DefaultMutableTreeNode rootNode = (javax.swing.tree.DefaultMutableTreeNode)settlementsTree.getModel().getRoot();
        javax.swing.tree.DefaultMutableTreeNode treeNode;
        elv.util.parameters.Settlement settlement;
        for(int i = 0; i < allSettlementsTree.getSelectionPaths().length; i++)
        {
          treeNode = (javax.swing.tree.DefaultMutableTreeNode)allSettlementsTree.getSelectionPaths()[i].getLastPathComponent();
          if(!isNodeChild(treeNode, rootNode))
          {
            setChanged(true);
            // Enable the "Save" buttons and menues.
            elv.util.Util.getMainFrame().setChanged(true);
            treeModel.insertNodeInto(treeNode, rootNode, rootNode.getChildCount());
            settlementsTree.scrollPathToVisible(new javax.swing.tree.TreePath(treeNode.getPath()));
            for(java.util.Enumeration e = treeNode.preorderEnumeration(); e.hasMoreElements(); )
            {
              treeNode = (javax.swing.tree.DefaultMutableTreeNode)e.nextElement();
              settlement = (elv.util.parameters.Settlement)treeNode.getUserObject();
              // Set the paragraph variable.
              javax.swing.tree.TreeNode[] parentNodes = treeNode.getPath();
              for(int j = 1; j < parentNodes.length; j++)
              {
                treeNode = (javax.swing.tree.DefaultMutableTreeNode)parentNodes[j];
                java.lang.String level = java.lang.Integer.toString(treeNode.getParent().getIndex(treeNode) + 1);
                if(j == 1)
                {
                  settlement.setParagraph(level);
                }
                else
                {
                  settlement.setParagraph(settlement.getParagraph() + "." + level);
                }
              }
              settlements.add(settlement);
            }
          }
        }
        createDistricts();
        if(parameterType instanceof elv.util.parameters.BaseSettlement &&
          (task.getType() == elv.task.Task.MORTALITY_POINT_SOURCE_ANALYSIS ||
           task.getType() == elv.task.Task.MORBIDITY_POINT_SOURCE_ANALYSIS))
        {
          task.getChangeSupport().firePropertyChange(task.getName(), null, settlements.get(0));
        }
      }
      setEnabled(isEnabled);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }//GEN-LAST:event_addButtonActionPerformed

  /**
   * Method for Remove button action.
   */
  private void removeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeButtonActionPerformed
  {//GEN-HEADEREND:event_removeButtonActionPerformed
    try
    {
      javax.swing.tree.TreePath[] removedPaths = settlementsTree.getSelectionPaths();
      if(removedPaths != null)
      {
        javax.swing.tree.DefaultTreeModel treeModel = (javax.swing.tree.DefaultTreeModel)settlementsTree.getModel();
        javax.swing.tree.DefaultMutableTreeNode rootNode = (javax.swing.tree.DefaultMutableTreeNode)settlementsTree.getModel().getRoot();
        javax.swing.tree.DefaultMutableTreeNode treeNode;
        elv.util.parameters.Settlement settlement;
        for(int i = 0; i < removedPaths.length; i++)
        {
          treeNode = (javax.swing.tree.DefaultMutableTreeNode)removedPaths[i].getLastPathComponent();
          if(!treeNode.isRoot())
          {
            if(!isNodeAncestor(treeNode, removedPaths, i))
            {
              setChanged(true);
              // Enable the "Save" buttons and menues.
              elv.util.Util.getMainFrame().setChanged(true);
              treeModel.removeNodeFromParent(treeNode);
              for(java.util.Enumeration e = treeNode.preorderEnumeration(); e.hasMoreElements(); )
              {
                treeNode = (javax.swing.tree.DefaultMutableTreeNode)e.nextElement();
                settlement = (elv.util.parameters.Settlement)treeNode.getUserObject();

                // Remove the district which remains without settlement.
                int districtCount = 0;
                elv.util.parameters.District settlementDistrict = null;
                for(elv.util.parameters.District iteratorDistrict : districts)
                {
                  if(iteratorDistrict.getCode() == settlement.getDistrictCode() &&
                     iteratorDistrict.getAggregation().equals(settlement.getAggregation()))
                  {
                    settlementDistrict = iteratorDistrict;
                    districtCount++;
                  }
                }
                // There were no other settlements belonging to this settlement's district.
                if(districtCount == 1)
                {
                  districts.remove(settlementDistrict);
                }
                settlements.remove(settlement);
              }
            }
          }
        }
        treeNode = rootNode;
        java.util.Enumeration e = treeNode.preorderEnumeration();
        treeNode = (javax.swing.tree.DefaultMutableTreeNode)e.nextElement();
        while(e.hasMoreElements())
        {
          treeNode = (javax.swing.tree.DefaultMutableTreeNode)e.nextElement();
          settlement = (elv.util.parameters.Settlement)treeNode.getUserObject();
          // Set the paragraph variable.
          javax.swing.tree.TreeNode[] parentNodes = treeNode.getPath();
          for(int j = 1; j < parentNodes.length; j++)
          {
            treeNode = (javax.swing.tree.DefaultMutableTreeNode)parentNodes[j];
            java.lang.String level = java.lang.Integer.toString(treeNode.getParent().getIndex(treeNode) + 1);
            if(j == 1)
            {
              settlement.setParagraph(level);
            }
            else
            {
              settlement.setParagraph(settlement.getParagraph() + "." + level);
            }
          }
        }
        createDistricts();
        if(parameterType instanceof elv.util.parameters.BaseSettlement &&
          (task.getType() == elv.task.Task.MORTALITY_POINT_SOURCE_ANALYSIS ||
           task.getType() == elv.task.Task.MORBIDITY_POINT_SOURCE_ANALYSIS))
        {
          task.getChangeSupport().firePropertyChange(task.getName(), "", null);
        }
      }
      setEnabled(isEnabled);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }//GEN-LAST:event_removeButtonActionPerformed

  /**
   * Method for Aggregate button action.
   */
  private void aggregateButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    try
    {
      if(aggregatePopupMenu.isVisible())
      {
        aggregatePopupMenu.setVisible(false);
      }
      else
      {
        aggregatePopupMenu.show(settlementsButtonPanel, aggregateButton.getX(), aggregateButton.getY() + aggregateButton.getHeight()); 
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }

  /**
   * Method for aggregation.
   * @param type the type of aggregation.
   */
  private void settlementsAggregateAction(int type)
  {
    try
    {
      javax.swing.tree.TreePath[] selectedPaths = settlementsTree.getSelectionPaths();
      if(selectedPaths != null)
      {
        javax.swing.tree.DefaultTreeModel treeModel = (javax.swing.tree.DefaultTreeModel)settlementsTree.getModel();
        javax.swing.tree.DefaultMutableTreeNode pathNode;
        javax.swing.tree.DefaultMutableTreeNode treeNode;

        java.util.Vector<javax.swing.tree.DefaultMutableTreeNode> selectedTreeNodes = new java.util.Vector<javax.swing.tree.DefaultMutableTreeNode>();
        java.util.Vector<elv.util.parameters.Settlement> selectedSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
        for(int i = 0; i < selectedPaths.length; i++)
        {
          pathNode = (javax.swing.tree.DefaultMutableTreeNode)selectedPaths[i].getLastPathComponent();
          if(!pathNode.isRoot())
          {
            setChanged(true);
            // Enable the "Save" buttons and menues.
            elv.util.Util.getMainFrame().setChanged(true);
            
            for(java.util.Enumeration e = pathNode.preorderEnumeration(); e.hasMoreElements(); )
            {
              treeNode = (javax.swing.tree.DefaultMutableTreeNode)e.nextElement();
              selectedTreeNodes.add(treeNode);
              elv.util.parameters.Settlement settlement = (elv.util.parameters.Settlement)treeNode.getUserObject();
              if (settlement.isPlace() || settlement.isRealSettlement()) // Is settlement.
              {
                selectedSettlements.add(settlement);
                // Remove the district which remains without settlement.
                int countDistricts = 0;
                elv.util.parameters.District settlementDistrict = null;
                for(elv.util.parameters.District iteratorDistrict : districts)
                {
                  if(iteratorDistrict.getCode() == settlement.getDistrictCode() &&
                     iteratorDistrict.getAggregation().equals(settlement.getAggregation()))
                  {
                    settlementDistrict = iteratorDistrict;
                    countDistricts++;
                  }
                }
                int countSettlements = 0;
                for(elv.util.parameters.Settlement iteratorSettlement : settlements)
                {
                  if(iteratorSettlement.getDistrictCode() == settlement.getDistrictCode() &&
                     iteratorSettlement.getAggregation().equals(settlement.getAggregation()))
                  {
                    countSettlements++;
                  }
                }
                if(countDistricts == 1 && countSettlements == 1)
                {
                  districts.remove(settlementDistrict);
                }
                // Set the aggregation type.
                settlement.setAggregation(new elv.util.parameters.Aggregation(type));
                // Reset the custom district code.
                settlement.setCustomDistrictCode(0);
              }
            }
          }
        }
        if(type == elv.util.parameters.Aggregation.CUSTOM)
        {
          // Determine a unique district code.
          int customDistrictCode = 0;
          int previousDistrictCode = 0;
          int insertIndex = 0;
          while(customDistrictCode == previousDistrictCode)
          {
            customDistrictCode++;
            insertIndex = 0;
            for(elv.util.parameters.District iteratorDistrict : districts)
            {
              if(customDistrictCode == iteratorDistrict.getCode())
              {
                previousDistrictCode = customDistrictCode;
                break;
              }
              else if(customDistrictCode < iteratorDistrict.getCode())
              {
                break;
              }
              insertIndex++;
            }
          }
          // Set the custom district code.
          for(elv.util.parameters.Settlement iteratorSettlement : selectedSettlements)
          {
            iteratorSettlement.setCustomDistrictCode(customDistrictCode);
          }
        }
        createDistricts();
        for(javax.swing.tree.DefaultMutableTreeNode iteratorTreeNode : selectedTreeNodes)
        {
          treeModel.nodeChanged(iteratorTreeNode);
        }
      }
      setEnabled(isEnabled);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Method for District button action.
   */
  private void districtButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
    try
    {
      java.util.Vector<elv.util.parameters.District> cloneDistricts = new java.util.Vector<elv.util.parameters.District>();
      for(elv.util.parameters.District iteratorDistrict : districts)
      {
        cloneDistricts.add((elv.util.parameters.District)iteratorDistrict.clone());
      }
      districtsTree = new javax.swing.JTree(cloneDistricts);
      districtsTree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
      districtsTree.setEditable(isEnabled);
      districtsTree.setCellRenderer(new elv.util.Util.DefaultTreeCellRenderer());
      districtsTree.setCellEditor(new javax.swing.tree.DefaultTreeCellEditor(districtsTree, new javax.swing.tree.DefaultTreeCellRenderer())
      {

        /**
         * Variable.
         */
        elv.util.parameters.District district;

        /**
         * Overridden method from javax.swing.tree.DefaultTreeCellEditor.
         */
        public java.lang.Object getCellEditorValue()
        {
          javax.swing.JTextField editorField = (javax.swing.JTextField)editingComponent;
          if(!district.getName().equals(editorField.getText()))
          {
            district.setName(editorField.getText());
            isChanged = true;
          }
          return district;
        }

        /**
         * Overridden method from javax.swing.tree.DefaultTreeCellEditor.
         */
        public java.awt.Component getTreeCellEditorComponent(javax.swing.JTree tree, java.lang.Object nodeObject, boolean isSelected, boolean expanded, boolean leaf, int row)
        {
          super.getTreeCellEditorComponent(tree, nodeObject, isSelected, expanded, leaf, row);
          javax.swing.tree.DefaultMutableTreeNode treeNode = (javax.swing.tree.DefaultMutableTreeNode)nodeObject;
          district = (elv.util.parameters.District)treeNode.getUserObject();
          javax.swing.JTextField editorField = (javax.swing.JTextField)editingComponent;
          editorField.setText(district.getName());
          editorField.selectAll();
          return editingContainer;
        }

      });
      javax.swing.JScrollPane districtsScrollPane = new javax.swing.JScrollPane();
      districtsScrollPane.setViewportView(districtsTree);
      int returnOption = elv.util.Option.okCancel(districtsScrollPane, new elv.util.parameters.District().getTitle());
      if(returnOption == javax.swing.JOptionPane.OK_OPTION)
      {
        districts = cloneDistricts;
        setChanged(isChanged);
        elv.util.Util.getMainFrame().setChanged(isChanged);
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }

  /**
   * Method for child determination.
   */
  private boolean isNodeChild(javax.swing.tree.DefaultMutableTreeNode treeNode, javax.swing.tree.DefaultMutableTreeNode anotherNode)
  {
    boolean isNodeChild = false;
    javax.swing.tree.DefaultMutableTreeNode childNode;
    for(java.util.Enumeration e = anotherNode.children(); e.hasMoreElements(); )
    {
      childNode = (javax.swing.tree.DefaultMutableTreeNode)e.nextElement();
      if(childNode.equals(treeNode))
      {
        isNodeChild = true;
        break;
      }
    }
    return isNodeChild;
  }
  
  /**
   * Method for ancestor determination.
   */
  private boolean isNodeAncestor(javax.swing.tree.DefaultMutableTreeNode treeNode, javax.swing.tree.TreePath[] paths, int excludedIndex)
  {
    boolean isNodeAncestor = false;
    javax.swing.tree.DefaultMutableTreeNode pathNode;
    if(paths != null)
    {
      for(int i = 0; i < paths.length; i++)
      {
        if(i != excludedIndex)
        {
          pathNode = (javax.swing.tree.DefaultMutableTreeNode)paths[i].getLastPathComponent();
          if(treeNode.isNodeAncestor(pathNode))
          {
            isNodeAncestor = true;
            break;
          }
        }
      }
    }
    return isNodeAncestor;
  }
  
  /**
   * Method for creating districts.
   */
  private void createDistricts()
  {
    try
    {
      elv.util.parameters.District district = null;
      elv.util.parameters.District previousDistrict = null;
      for(elv.util.parameters.Settlement districtIteratorSettlement : settlements)
      {
        if(task.getType() == elv.task.Task.MORBIDITY_STANDARDIZATION ||
           task.getType() == elv.task.Task.MORBIDITY_SMOOTHING ||
           task.getType() == elv.task.Task.MORBIDITY_CLUSTER_ANALYSIS ||
           task.getType() == elv.task.Task.MORBIDITY_REGION_ANALYSIS ||
           task.getType() == elv.task.Task.MORBIDITY_POINT_SOURCE_ANALYSIS)
        {
          if(districtIteratorSettlement.isPlace())
          {
            // Determine the district properties.
            java.lang.String name = "";
            java.lang.String previousName = "";
            double area = 0;
            double xCoordinate = 0;
            double yCoordinate = 0;
            int settlementCount = 0;
            for(elv.util.parameters.Settlement iteratorSettlement : settlements)
            {
              if(iteratorSettlement.isPlace() &&
                 iteratorSettlement.getDistrictCode() == districtIteratorSettlement.getDistrictCode() &&
                 iteratorSettlement.getAggregation().equals(districtIteratorSettlement.getAggregation()))
              {
                name += (settlementCount == 0 ? iteratorSettlement.getName() : (settlementCount < 3 && !iteratorSettlement.getName().equals(previousName) ? "-" + iteratorSettlement.getName() : (settlementCount == 3 ? "-..." : "")));
                area += iteratorSettlement.getArea();
                xCoordinate += iteratorSettlement.getXCoordinate();
                yCoordinate += iteratorSettlement.getYCoordinate();
                previousName = iteratorSettlement.getName();
                settlementCount++;
              }
            }
            district = new elv.util.parameters.District(districtIteratorSettlement.getDistrictCode(), name, area, xCoordinate / settlementCount, yCoordinate / settlementCount, districtIteratorSettlement.getAggregation());
          }
        }
        else
        {
          if(districtIteratorSettlement.isRealSettlement())
          {
            if(districtIteratorSettlement.getAggregation().equals(new elv.util.parameters.Aggregation(elv.util.parameters.Aggregation.NO)))
            {
              int code = 0;
              if(task.getType() == elv.task.Task.MORBIDITY_PREPARATION)
              {
                code = java.lang.Integer.parseInt(districtIteratorSettlement.getCodes()[elv.util.parameters.Settlement.POSTAL]);
              }
              else
              {
                code = java.lang.Integer.parseInt(districtIteratorSettlement.getCodes()[elv.util.parameters.Settlement.STATISTICAL]);
              }
              districtIteratorSettlement.setNoDistrictCode(code);
              district = new elv.util.parameters.District(code, districtIteratorSettlement.getName(), districtIteratorSettlement.getArea(), districtIteratorSettlement.getXCoordinate(), districtIteratorSettlement.getYCoordinate(), districtIteratorSettlement.getAggregation());
            }
            else if(previousDistrict == null || (previousDistrict.getCode() != districtIteratorSettlement.getDistrictCode() || !previousDistrict.getAggregation().equals(districtIteratorSettlement.getAggregation())))
            {
              java.util.Vector<elv.util.parameters.Settlement> districtSettlements = new java.util.Vector<elv.util.parameters.Settlement>();
              // Determine the district properties.
              java.lang.String name = "";
              java.lang.String previousName = "";
              double area = 0;
              double xCoordinate = 0;
              double yCoordinate = 0;
              int settlementCount = 0;
              for(elv.util.parameters.Settlement iteratorSettlement : settlements)
              {
                if(iteratorSettlement.isRealSettlement() &&
                   iteratorSettlement.getDistrictCode() == districtIteratorSettlement.getDistrictCode() &&
                   iteratorSettlement.getAggregation().equals(districtIteratorSettlement.getAggregation()))
                {
                  // Determine if this settlement was parsed.
                  boolean parsed = false;
                  for(elv.util.parameters.Settlement iteratorDistrictSettlement : districtSettlements)
                  {
                    if(iteratorDistrictSettlement.equals(iteratorSettlement))
                    {
                      parsed = true;
                      break;
                    }
                  }
                  if(!parsed)
                  {
                    name += (settlementCount == 0 ? iteratorSettlement.getName() : (settlementCount < 3 && !iteratorSettlement.getName().equals(previousName) ? "-" + iteratorSettlement.getName() : (settlementCount == 3 ? "-..." : "")));
                    area += iteratorSettlement.getArea();
                    xCoordinate += iteratorSettlement.getXCoordinate();
                    yCoordinate += iteratorSettlement.getYCoordinate();
                    previousName = iteratorSettlement.getName();
                    settlementCount++;
                    districtSettlements.add(iteratorSettlement);
                  }
                }
              }
              district = new elv.util.parameters.District(districtIteratorSettlement.getDistrictCode(), name, area, xCoordinate / settlementCount, yCoordinate / settlementCount, districtIteratorSettlement.getAggregation());
              previousDistrict = district;
            }
          }
        }
        // Add district to the districts list, if there is not, in ascending mode.
        if(district != null)
        {
          elv.util.parameters.District existingDistrict = null;
          int index = 0;
          for(elv.util.parameters.District iteratorDistrict : districts)
          {
            if(iteratorDistrict.equals(district))
            {
              existingDistrict = iteratorDistrict;
              break;
            }
            else if(district.getCode() < iteratorDistrict.getCode())
            {
              break;
            }
            index++;
          }
          if(existingDistrict == null)
          {
            districts.add(index, district);
          }
          else
          {
            districts.set(index, district);
          }
        }
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Overridden method from <CODE>javax.swing.JComponent</CODE>.
   * @param isEnabled the enabling direction.
   */
  public void setEnabled(boolean isEnabled)
  {
    this.isEnabled = isEnabled;
    
    if(parameterType instanceof elv.util.parameters.BaseSettlement &&
      (task.getType() == elv.task.Task.MORTALITY_POINT_SOURCE_ANALYSIS ||
       task.getType() == elv.task.Task.MORBIDITY_POINT_SOURCE_ANALYSIS))
    {
      addButton.setEnabled(isEnabled && settlementsTree.getRowCount() == 0);
      removeButton.setEnabled(isEnabled && settlementsTree.getSelectionCount() > 0);
    }
    else
    {
      addButton.setEnabled(isEnabled);
      removeButton.setEnabled(isEnabled && settlementsTree.getSelectionCount() > 0);
    }
    if(parameterType instanceof elv.util.parameters.BaseSettlement &&
       (task.getType() != elv.task.Task.MORTALITY_SELECTION &&
        task.getType() != elv.task.Task.MORBIDITY_SELECTION))
    {
      aggregateButton.setEnabled(isEnabled && settlementsTree.getSelectionCount() > 0);
      if(districtsTree != null)
      {
        districtsTree.setEditable(isEnabled);
      }
    }
  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   * @return the changeable owner object.
   */
  public elv.util.Changeable getOwner()
  {
    return task;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   * @return the type object.
   */
  public java.lang.Object getType()
  {
    return parameterType;
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
        elv.util.client.ClientStub.storeParameters(task.getPropertyFolderPath() + elv.util.Util.getFS() + parameterType.getFile(), settlements);
        elv.util.client.ClientStub.storeParameters(task.getPropertyFolderPath() + elv.util.Util.getFS() + new elv.util.parameters.District().getFile(), districts);
        if(task != null)
        {
          elv.util.Property.get(elv.task.Task.MODIFIED_NAME, task.getProperties()).setValue(new java.util.Date());
          java.lang.String pathName = task.getPropertyFolderPath() + elv.util.Util.getFS() + task.getPropertyFile();
          elv.util.client.ClientStub.storeProperties(pathName, task.getProperties());
          // Fire property change for reload the task properties.
          task.getChangeSupport().firePropertyChange(elv.task.Task.MODIFIED_NAME, null, state);
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
  private void stateChange(java.beans.PropertyChangeEvent evt)
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
        setEnabled(isEnabled);
      }
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Method for "pointSettlement" change.
   * @param evt a <CODE>java.beans.PropertyChangeEvent</>code object.
   */
  private void pointChange(java.beans.PropertyChangeEvent evt)
  {
    try
    {
      elv.util.parameters.Settlement pointSettlement = (elv.util.parameters.Settlement)evt.getNewValue();
      settlements.removeAllElements();
      if(pointSettlement != null)
      {
        double pX = pointSettlement.getXCoordinate();
        double pY = pointSettlement.getYCoordinate();
        java.util.Vector<elv.util.parameters.Settlement> allSettlements = parameterType.getAllSettlements();
        java.lang.String placeParagraph = null;
        int settlementCount = 0;
        int subSettlementCount = 0;
        for(elv.util.parameters.Settlement iteratorSettlement : allSettlements)
        {
          if(iteratorSettlement.isPlace())
          {
            placeParagraph = null;
            subSettlementCount = 0;
            double iX = iteratorSettlement.getXCoordinate();
            double iY = iteratorSettlement.getYCoordinate();
            double distance = java.lang.Math.sqrt((iX - pX) * (iX - pX) + (iY - pY) * (iY - pY));
            if(distance < elv.task.executables.PointSourceSpotting.BENCHMARK_RADIUS)
            {
              placeParagraph = iteratorSettlement.getParagraph();
              subSettlementCount = 0;
              iteratorSettlement.setParagraph(java.lang.Integer.toString(++settlementCount));
              settlements.add(iteratorSettlement);
            }
          }
          else if(iteratorSettlement.isRealSettlement() && placeParagraph != null &&
            iteratorSettlement.getParagraph().contains(placeParagraph))
          {
            iteratorSettlement.setParagraph(settlementCount + "." + (++subSettlementCount));
            settlements.add(iteratorSettlement);
          }
        }
      }
      settlementsTree.setModel(new SettlementsTreeModel());
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Inner class for tree model.
   */
  private class SettlementsTreeModel extends javax.swing.tree.DefaultTreeModel
  {
    
    /**
     * Constructor.
     */
    public SettlementsTreeModel()
    {
      super(new javax.swing.tree.DefaultMutableTreeNode());
      
      javax.swing.tree.DefaultMutableTreeNode nodeLevels[] = new javax.swing.tree.DefaultMutableTreeNode[10];
      nodeLevels[0] = new javax.swing.tree.DefaultMutableTreeNode(parameterType.TITLE);
      javax.swing.tree.DefaultMutableTreeNode treeNode = null;
      
      for(elv.util.parameters.Settlement iteratorSettlement : settlements)
      {
        treeNode = new javax.swing.tree.DefaultMutableTreeNode(iteratorSettlement);
        nodeLevels[iteratorSettlement.getParagraphLevel()] = treeNode;
        nodeLevels[iteratorSettlement.getParagraphLevel() - 1].add(treeNode);
      }
      setRoot(nodeLevels[0]);
    }
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addButton;
  private javax.swing.JPopupMenu aggregatePopupMenu;
  private javax.swing.JMenuItem customAggregationMenuItem;
  private javax.swing.JMenuItem mmAggregationMenuItem;
  private javax.swing.JMenuItem noAggregationMenuItem;
  private javax.swing.JButton removeButton;
  private javax.swing.JPanel settlementsButtonOrientationPanel;
  private javax.swing.JPanel settlementsButtonPanel;
  private javax.swing.JScrollPane settlementsScrollPane;
  private javax.swing.JTree settlementsTree;
  // End of variables declaration//GEN-END:variables

}
