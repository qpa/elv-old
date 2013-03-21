/*
 * PropertiesPanel.java
 */
package elv.gui;

/**
 * Class for editing properties.
 * @author Qpa
 */
public class PropertiesPanel extends javax.swing.JPanel implements elv.util.Saveable
{
  
  // Variables.
  /** The owner propertable. */
  private elv.util.Propertable propertable;
  /** The properties. */
  protected java.util.Vector<elv.util.Property> properties;
  /** The changed state of properties. */
  protected boolean isChanged = false;
  /** The state of changeability. */
  protected boolean isEnabled = true;
  /** The table of properties. */
  protected PropertiesTable propertiesTable;
  
  /**
   * Constructor.
   * @param aPropertable the object which has these properties.
   */
  public PropertiesPanel(elv.util.Propertable aPropertable)
  {
    this.propertable = aPropertable;
    try
    {
      // Set default values.
      aPropertable.setPropertiesDefaultValues();
      // Get propeties.
      properties = elv.util.Property.clone(propertable.getProperties());
      
      if(propertable instanceof elv.task.Task)
      {
        elv.util.State state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, ((elv.task.Task)propertable).getProperties()).getValue();
        isEnabled = (!(((elv.task.Task)propertable).getContainer() instanceof elv.task.Archive) && 
          (state.equals(new elv.util.State(elv.util.State.UNDEFINED)) ||
          state.equals(new elv.util.State(elv.util.State.DEFINED)) ||
          state.equals(new elv.util.State(elv.util.State.SCHEDULED))));
        // Set state change support.
        propertable.getChangeSupport().addPropertyChangeListener(elv.util.State.TITLE, new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            stateChange(evt);
          }
        });
        // Set modification support.
        propertable.getChangeSupport().addPropertyChangeListener(elv.task.Task.MODIFIED_NAME, new java.beans.PropertyChangeListener()
        {
          public void propertyChange(java.beans.PropertyChangeEvent evt)
          {
            stateChange(evt);
          }
        });
      }
      
      // Initialize panel.
      initComponents();
      initPropertiesTable();
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
    propertiesScrollPane = new javax.swing.JScrollPane();

    setLayout(new java.awt.BorderLayout());

    add(propertiesScrollPane, java.awt.BorderLayout.CENTER);

  }//GEN-END:initComponents
  
  protected void initPropertiesTable()
  {
    propertiesTable = new PropertiesTable();
    propertiesScrollPane.setViewportView(propertiesTable);
  }
  
  /**
   * Method for state change.
   * @param evt a <CODE>java.beans.PropertyChangeEvent</>code object.
   */
  protected void stateChange(java.beans.PropertyChangeEvent evt)
  {
    try
    {
      elv.util.State state = (elv.util.State)evt.getNewValue();
      elv.task.Task task = (elv.task.Task)propertable;
      propertable.setProperties(elv.util.client.ClientStub.loadProperties(task.getPropertyFolderPath(), task.getPropertyFile()));
      propertable.setPropertiesDefaultValues();
      properties = elv.util.Property.clone(propertable.getProperties());
      initPropertiesTable();
      isEnabled = (!(((elv.task.Task)propertable).getContainer() instanceof elv.task.Archive) && 
        (state.equals(new elv.util.State(elv.util.State.UNDEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.DEFINED)) ||
        state.equals(new elv.util.State(elv.util.State.SCHEDULED))));
      propertiesTable.setEnabled(isEnabled);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Method for getting the properties.
   * @return a vector of properties.
   */
  public java.util.Vector<elv.util.Property> getProperties()
  {
    return properties;
  }
  
  /**
   * Inner class for properties table.
   */
  protected class PropertiesTable extends javax.swing.JTable
  {
    
    /**
     * Constructor.
     */
    public PropertiesTable()
    {
      super();
      setRowHeight(elv.util.Util.ROW_HEIGHT);
      setFillsViewportHeight(true);
      setModel(new PropertiesTableModel());
      setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * Overriddenen method from <CODE>javax.swing.JTable</CODE>.
     */
    public javax.swing.table.TableCellRenderer getCellRenderer(int row, int column)
    {
      return new PropertiesTableCellRenderer();
    }

    /**
     * Overriddenen method from <CODE>javax.swing.JTable</CODE>.
     */
    public javax.swing.table.TableCellEditor getCellEditor(int row, int column)
    {
      return new PropertiesTableCellEditor();
    }
  }
  
  /**
   * Inner class for properties table model.
   */
  private class PropertiesTableModel extends javax.swing.table.DefaultTableModel
  {
    
    /**
     * Constructor.
     */
    public PropertiesTableModel()
    {
      try
      {
        addColumn(elv.util.Util.translate(elv.util.Property.NAME));
        addColumn(elv.util.Util.translate(elv.util.Util.VALUE));
        setProperties(propertable.getRootName());

        addTableModelListener(new javax.swing.event.TableModelListener()
        {
          public void tableChanged(javax.swing.event.TableModelEvent evt)
          {
            if(evt.getType() == javax.swing.event.TableModelEvent.UPDATE)
            {
              setRowCount(0);
              setProperties(propertable.getRootName());
            }
          }
        });
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }
    
    /**
     * Inner method for populating the table.
     */
    public void setProperties(java.lang.String rootValue)
    {
      try
      {
        java.util.Vector<elv.util.Property> props = elv.util.Property.getRelatives(rootValue, properties);
        if(props.isEmpty() && rootValue.equals(propertable.getRootName())) // There are no properties.
        {
          elv.util.Property property = new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, false, elv.util.Util.NO_VALUE, "");
          addRow(new java.lang.Object[]{property.getName(), property});
        }
        else
        {
          // Set root properties.
          for(elv.util.Property iteratorProperty : props)
          {
            if(!iteratorProperty.getEditorType().equals(elv.util.Property.NO_FIELD))
            {
              addRow(new java.lang.Object[]{iteratorProperty.getName(), iteratorProperty});
            }
          }
          // Set sub-properties.
          for(elv.util.Property iteratorProperty : props)
          {
            if(!iteratorProperty.getEditorType().equals(elv.util.Property.NO_FIELD) && iteratorProperty.haveToTranslate() &&
              iteratorProperty.getValue() instanceof java.lang.String && !((java.lang.String)iteratorProperty.getValue()).equals(rootValue))
            {
              setProperties((java.lang.String)iteratorProperty.getValue());
            }
          }
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }

    /**
     * Overriddenen method from <CODE>javax.swing.table.DefaultTableModel</CODE>.
     */
    public boolean isCellEditable(int row, int column)
    {
      boolean isCellEditable = false;
      try
      {
        if(getColumnName(column).equals(elv.util.Util.translate(elv.util.Util.VALUE)))
        {
          elv.util.Property property = (elv.util.Property)getValueAt(row, column);
          boolean schedulingIsEnabled = false;
          if(propertable instanceof elv.task.Task)
          {
            elv.util.State state = (elv.util.State)elv.util.Property.get(elv.task.Task.STATE_NAME, ((elv.task.Task)propertable).getProperties()).getValue();
            schedulingIsEnabled = (property.getName().equals(elv.task.Task.SCHEDULED_NAME) &&
              !state.equals(new elv.util.State(elv.util.State.EXECUTED)) &&
              !state.equals(new elv.util.State(elv.util.State.DONE)));
          }
          isCellEditable = property.getEditorType().equals(elv.util.Property.LABEL_FIELD) ? false : (schedulingIsEnabled ? true : isEnabled);
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
      return isCellEditable;
    }
    
  }
  
  /**
   * Inner class for cell rendering.
   */
  private class PropertiesTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer
  {
    
    /**
     * Overriddenen method from <CODE>javax.swing.table.DefaultTableCellRenderer</CODE>.
     */
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object cellObject, boolean isSelected, boolean hasFocus, int row, int column)
    {
      super.getTableCellRendererComponent(table, cellObject, isSelected, hasFocus, row, column);
      try
      {
        if(table.getColumnName(column).equals(elv.util.Util.translate(elv.util.Property.NAME)))
        {
          java.lang.String name = (java.lang.String)cellObject;
          setText(elv.util.Util.translate(name));
        }
        else
        {
          setFont(new java.awt.Font("DialogInput", 0, 12));
          elv.util.Property property = (elv.util.Property)cellObject;
          setText(property.toString());
          setToolTipText(property.toString());
          if(property.getValue() instanceof elv.util.State)
          {
            setIcon(((elv.util.State)property.getValue()).getIcon());
          }
          else if(property.getValue() instanceof elv.task.Task)
          {
            setIcon(((elv.task.Task)property.getValue()).getIcon());
          }
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(this, exc);
      }
      return this;
    }
    
  }
  
  /**
   * Inner class for cell editing.
   */
  private class PropertiesTableCellEditor extends javax.swing.DefaultCellEditor
  {
    
    /**
     * Variable.
     */
    elv.util.Property property;
    
    /**
     * Constructor.
     */
    public PropertiesTableCellEditor()
    {
      super(new javax.swing.JTextField());
      setClickCountToStart(1);
    }
    
    /**
     * Overriddenen method from <CODE>javax.swing.DefaultCellEditor</CODE>.
     */
    public java.lang.Object getCellEditorValue()
    {
      return property;
    }
      
    /**
     * Overriddenen method from <CODE>javax.swing.DefaultCellEditor</CODE>.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, java.lang.Object cellObject, boolean isSelected, int row, int column)
    {
      super.getTableCellEditorComponent(table, cellObject, isSelected, row, column);
      final int thisRow = row;
      final int thisColumn = column;
      try
      {
        property = (elv.util.Property)cellObject;
        if(property.getEditorType().equals(elv.util.Property.LABEL_FIELD))
        {
          final javax.swing.JLabel editorField = new javax.swing.JLabel(property.toString());
          editorField.setFont(new java.awt.Font("DialogInput", 0, 12));
          editorComponent = editorField;
        }
        else if(property.getEditorType().equals(elv.util.Property.TEXT_FIELD))
        {
          final javax.swing.JTextField editorField = new javax.swing.JTextField(property.toString());
          editorField.setFont(new java.awt.Font("DialogInput", 0, 12));
          editorField.selectAll();
          editorField.addCaretListener(new javax.swing.event.CaretListener()
          {
            public void caretUpdate(javax.swing.event.CaretEvent evt)
            {
              if(!property.toString().equals(editorField.getText()))
              {
                property.setValue(editorField.getText());
                setChanged();
              }
            }
          });
          editorField.addActionListener(new java.awt.event.ActionListener()
          {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
              if(!property.toString().equals(editorField.getText()))
              {
                property.setValue(editorField.getText());
                setChanged();
              }
              fireEditingStopped();
            }
          });
          editorComponent = editorField;
        }
        else if(property.getEditorType().equals(elv.util.Property.SPINNER))
        {
          final javax.swing.JSpinner editorSpinner = new javax.swing.JSpinner();
          editorSpinner.setFont(new java.awt.Font("DialogInput", 0, 12));
          if(property.getType().equals(elv.util.Property.INTEGER))
          {
            editorSpinner.setModel(new javax.swing.SpinnerNumberModel((java.lang.Integer)property.getValue(), new java.lang.Integer(0), null, new java.lang.Integer(1)));
          }
          else if(property.getType().equals(elv.util.Property.DOUBLE))
          {
            editorSpinner.setModel(new javax.swing.SpinnerNumberModel((java.lang.Double)property.getValue(), new java.lang.Double(0), null, new java.lang.Double(0.001)));
          }
          editorSpinner.addChangeListener(new javax.swing.event.ChangeListener()
          {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
              java.lang.Object editorValue = editorSpinner.getModel().getValue();
              if(!property.getValue().equals(editorValue))
              {
                property.setValue(editorValue);
                setChanged();
              }
            }
          });
          javax.swing.JSpinner.NumberEditor numberEditor = (javax.swing.JSpinner.NumberEditor)editorSpinner.getEditor();
          numberEditor.getFormat().setDecimalFormatSymbols(new java.text.DecimalFormatSymbols(elv.util.Util.getActualLocale()));
          numberEditor.getFormat().setGroupingUsed(false);
          final javax.swing.JFormattedTextField editorField = numberEditor.getTextField();
          editorField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
          editorField.selectAll();
          editorField.addCaretListener(new javax.swing.event.CaretListener()
          {
            public void caretUpdate(javax.swing.event.CaretEvent evt)
            {
              java.lang.Object editorValue = editorSpinner.getModel().getValue();
              if(!property.getValue().equals(editorValue))
              {
                property.setValue(editorValue);
                setChanged();
              }
              ((javax.swing.table.DefaultTableModel)propertiesTable.getModel()).fireTableCellUpdated(thisRow, thisColumn);
            }
          });
          editorField.addActionListener(new java.awt.event.ActionListener()
          {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
              java.lang.Object editorValue = editorSpinner.getModel().getValue();
              if(!property.getValue().equals(editorValue))
              {
                property.setValue(editorValue);
                setChanged();
              }
            }
          });
          editorComponent = editorSpinner;
        }
        else if(property.getEditorType().equals(elv.util.Property.COMBO_BOX))
        {
          final javax.swing.JComboBox editorComboBox = new javax.swing.JComboBox();
          editorComboBox.setFont(new java.awt.Font("DialogInput", 0, 12));
          for(java.lang.Object iteratorValue : property.getDefaultValues())
          {
            elv.util.Property defaultProperty = new elv.util.Property(property.getType(), property.getEditorType(), property.haveToTranslate(), property.getName(), iteratorValue);
            editorComboBox.addItem(defaultProperty.toString());
            if(property.getValue().equals(iteratorValue))
            {
              editorComboBox.setSelectedItem(defaultProperty.toString());
            }
          }
          editorComboBox.addActionListener(new java.awt.event.ActionListener()
          {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
              java.lang.Object editorValue = property.getDefaultValues().get(editorComboBox.getSelectedIndex());
              if(!property.getValue().equals(editorValue))
              {
                property.setValue(editorValue);
                setChanged();
              }
              fireEditingStopped();
            }
          });
          editorComponent = editorComboBox;
        }
        else
        {
          javax.swing.JPanel editorPanel = new javax.swing.JPanel();
          editorPanel.setLayout(new java.awt.BorderLayout());

          javax.swing.JTextField editorField = new javax.swing.JTextField(property.toString());
          editorField.setFont(new java.awt.Font("DialogInput", 0, 12));
          editorField.setEditable(false);
          editorPanel.add(editorField,  java.awt.BorderLayout.CENTER);

          javax.swing.JButton editorButton = new javax.swing.JButton("...");
          editorButton.setPreferredSize(new java.awt.Dimension(16, 16));
          editorButton.setToolTipText(new elv.util.Action(elv.util.Action.MODIFY).toString());
          editorPanel.add(editorButton,  java.awt.BorderLayout.EAST);
          
          if(property.getEditorType().equals(elv.util.Property.PASSWORD_FIELD))
          {
            editorButton.addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(java.awt.event.ActionEvent evt)
              {
                getPasswordValue();
                fireEditingStopped();
              }
            });
          }
          else if(property.getEditorType().equals(elv.util.Property.DATE_BUTTON))
          {
            editorButton.addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(java.awt.event.ActionEvent evt)
              {
                getDateValue();
                fireEditingStopped();
              }
            });
          }
          else if(property.getEditorType().equals(elv.util.Property.LIST_BUTTON))
          {
            editorButton.addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(java.awt.event.ActionEvent evt)
              {
                getListValues();
                fireEditingStopped();
              }
            });
          }
          else if(property.getEditorType().equals(elv.util.Property.TABLE_BUTTON))
          {
            editorButton.addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(java.awt.event.ActionEvent evt)
              {
                getDoubleTableValues();
                fireEditingStopped();
              }
            });
          }
          else if(property.getEditorType().equals(elv.util.Property.TREE_BUTTON))
          {
            editorButton.addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(java.awt.event.ActionEvent evt)
              {
                getTaskTreeValue();
                fireEditingStopped();
              }
            });
          }

          editorComponent = editorPanel;
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
      return editorComponent;
    }
    
    /**
     * Inner method for editing password value.
     */
    private void getPasswordValue()
    {
      try
      {
        PasswordsDialog passwordsDialog = new PasswordsDialog(elv.util.Util.translate(property.getName()), (java.lang.String)property.getValue());
        passwordsDialog.setVisible(true);
        java.lang.String newPassword = passwordsDialog.getPassword();
        if(newPassword != null)
        {
          property.setValue(newPassword);
          setChanged();
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }
    
    /**
     * Inner method for editing date value.
     */
    private void getDateValue()
    {
      try
      {
        java.util.Date scheduledTime = (java.util.Date)elv.util.Property.get(elv.task.Task.SCHEDULED_NAME, properties).getValue();
        java.util.Date serverTime = elv.util.client.ClientStub.getDate();
        java.util.Date startTime = (scheduledTime == null || scheduledTime.before(serverTime) ? new java.util.Date(serverTime.getTime() + 1000) : scheduledTime);
        javax.swing.JSpinner editorSpinner = new javax.swing.JSpinner();
        editorSpinner.setFont(new java.awt.Font("DialogInput", 0, 12));
        javax.swing.SpinnerDateModel spinnerDateModel = new javax.swing.SpinnerDateModel(startTime, serverTime, null, java.util.Calendar.SECOND);
        editorSpinner.setModel(spinnerDateModel);

        java.lang.String pattern = ((java.text.SimpleDateFormat)java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.MEDIUM, java.text.DateFormat.MEDIUM, elv.util.Util.getActualLocale())).toPattern();
        javax.swing.JSpinner.DateEditor dateEditor = new javax.swing.JSpinner.DateEditor(editorSpinner, pattern);
        dateEditor.getFormat().setDateFormatSymbols(new java.text.DateFormatSymbols(elv.util.Util.getActualLocale()));
        editorSpinner.setEditor(dateEditor);
        dateEditor.getTextField().revalidate();
        dateEditor.getTextField().repaint();

        int returnOption = elv.util.Option.yesNoCancel(editorSpinner, elv.util.Util.translate(property.getName()));
        if(returnOption == javax.swing.JOptionPane.YES_OPTION)
        {
          property.setValue(spinnerDateModel.getDate());
          setChanged();
          if(property.getName().equals(elv.task.Task.SCHEDULED_NAME))
          {
            elv.util.Property.get(elv.task.Task.STATE_NAME, properties).setValue(new elv.util.State(elv.util.State.SCHEDULED));
          }
        }
        else if(returnOption == javax.swing.JOptionPane.NO_OPTION)
        {
          property.setValue(null);
          setChanged();
          if(property.getName().equals(elv.task.Task.SCHEDULED_NAME))
          {
            elv.util.Property.get(elv.task.Task.STATE_NAME, properties).setValue(new elv.util.State(elv.util.State.DEFINED));
          }
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }
    
    /**
     * Inner method for editing string list values.
     */
    private void getListValues()
    {
      try
      {
        java.util.Vector<java.lang.Object> defaultProperties = new java.util.Vector<java.lang.Object>();
        for(java.lang.Object iteratorValue : property.getDefaultValues())
        {
          defaultProperties.add(new elv.util.Property(property.getElementType(), property.getEditorType(), property.haveToTranslate(), property.getName(), iteratorValue));
        }
        javax.swing.JList editorList = new javax.swing.JList(defaultProperties);
        editorList.setFont(new java.awt.Font("DialogInput", 0, 12));
        java.util.Vector<java.lang.Object> values = (java.util.Vector<java.lang.Object>)property.getValue();
        if(values == null)
        {
          values = new java.util.Vector<java.lang.Object>();
        }
        int[] selectedIndices = new int[values.size()];
        int valueCount = 0;
        for(java.lang.Object iteratorValue : values)
        {
          int defaultValueCount = 0;
          for(java.lang.Object iteratorDefaultValue : property.getDefaultValues())
          {
            if(iteratorDefaultValue.equals(iteratorValue))
            {
              selectedIndices[valueCount] = defaultValueCount;
              break;
            }
            defaultValueCount++;
          }
          valueCount++;
        }
        editorList.setSelectedIndices(selectedIndices);
        javax.swing.JScrollPane editorScrollPane = new javax.swing.JScrollPane();
        editorScrollPane.setViewportView(editorList);
        editorScrollPane.setPreferredSize(new java.awt.Dimension(50, 200));
        
        int returnOption = elv.util.Option.okCancel(editorScrollPane, elv.util.Util.translate(property.getName()));
        if(returnOption == javax.swing.JOptionPane.OK_OPTION)
        {
          values = new java.util.Vector<java.lang.Object>();
          for(int i : editorList.getSelectedIndices())
          {
            values.add(property.getDefaultValues().get(i));
          }
          property.setValue(values);
          setChanged();
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }
    
    /**
     * Inner method for editing double table values.
     */
    private void getDoubleTableValues()
    {
      try
      {
        final java.util.Vector<java.util.Vector> tableData = new java.util.Vector<java.util.Vector>();
        java.util.Vector<java.lang.Object> values = (java.util.Vector<java.lang.Object>)property.getValue();
        for(java.lang.Object iteratorValue : values)
        {
          java.util.Vector<elv.util.Property> rowData = new java.util.Vector<elv.util.Property>();
          rowData.add(new elv.util.Property(property.getElementType(), property.getEditorType(), property.haveToTranslate(), property.getName(), iteratorValue));
          tableData.add(rowData);
        }
        final java.util.Vector<java.lang.String> tableColumnNames = new java.util.Vector<java.lang.String>();
        tableColumnNames.add(elv.util.Util.VALUE);
        final javax.swing.JTable editorTable = new javax.swing.JTable(new javax.swing.table.DefaultTableModel(tableData, tableColumnNames));
        editorTable.setFillsViewportHeight(true);
        editorTable.setFont(new java.awt.Font("DialogInput", 0, 12));
        editorTable.setRowHeight(elv.util.Util.ROW_HEIGHT);
        editorTable.setTableHeader(null);
        editorTable.setDefaultEditor(editorTable.getColumnClass(0), new javax.swing.DefaultCellEditor(new javax.swing.JTextField())
        {

          private elv.util.Property prop;

          {
            setClickCountToStart(1);
          }

          public java.lang.Object getCellEditorValue()
          {
            return prop;
          }

          public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object cellObject, boolean isSelected, int row, int column)
          {
            final int thisRow = row;
            final int thisColumn = column;
            prop = (elv.util.Property)cellObject;
            final javax.swing.JSpinner editorSpinner = new javax.swing.JSpinner();
            editorSpinner.setFont(new java.awt.Font("DialogInput", 0, 12));
            java.lang.Double value = (java.lang.Double)prop.getValue();
            java.lang.Double previousValue = (row == 0 ? new java.lang.Double(0) : (java.lang.Double)((elv.util.Property)table.getModel().getValueAt(row - 1, column)).getValue());
            java.lang.Double nextValue = (row == table.getRowCount() - 1 ? null : (java.lang.Double)((elv.util.Property)table.getModel().getValueAt(row + 1, column)).getValue());
            java.lang.Double min = (row == 0 ? new java.lang.Double(0) : new java.lang.Double(previousValue.doubleValue() + 0.001));
            java.lang.Double max = (row == table.getRowCount() - 1 ? null : new java.lang.Double(nextValue.doubleValue() - 0.001));
            editorSpinner.setModel(new javax.swing.SpinnerNumberModel(value, min, max, new java.lang.Double(0.001)));
            editorSpinner.addChangeListener(new javax.swing.event.ChangeListener()
            {
              public void stateChanged(javax.swing.event.ChangeEvent evt)
              {
                java.lang.Object editorValue = editorSpinner.getModel().getValue();
                if(!prop.getValue().equals(editorValue))
                {
                  prop.setValue(editorValue);
                }
              }
            });
            javax.swing.JSpinner.NumberEditor numberEditor = (javax.swing.JSpinner.NumberEditor)editorSpinner.getEditor();
            numberEditor.getFormat().setDecimalFormatSymbols(new java.text.DecimalFormatSymbols(elv.util.Util.getActualLocale()));
            numberEditor.getFormat().setGroupingUsed(false);
            final javax.swing.JFormattedTextField editorField = numberEditor.getTextField();
            editorField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            editorField.selectAll();
            editorField.addCaretListener(new javax.swing.event.CaretListener()
            {
              public void caretUpdate(javax.swing.event.CaretEvent evt)
              {
                java.lang.Object editorValue = editorSpinner.getModel().getValue();
                if(!property.getValue().equals(editorValue))
                {
                  property.setValue(editorValue);
                  setChanged();
                }
                ((javax.swing.table.DefaultTableModel)propertiesTable.getModel()).fireTableCellUpdated(thisRow, thisColumn);
              }
            });
            editorField.addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(java.awt.event.ActionEvent evt)
              {
                java.lang.Object editorValue = editorSpinner.getModel().getValue();
                if(!prop.getValue().equals(editorValue))
                {
                  prop.setValue(editorValue);
                }
              }
            });
            return editorSpinner;
          }
        });
        javax.swing.JScrollPane editorScrollPane = new javax.swing.JScrollPane();
        editorScrollPane.setViewportView(editorTable);

        javax.swing.JButton addButton = new javax.swing.JButton();
        addButton.setText("+");
        addButton.setToolTipText(new elv.util.Action(elv.util.Action.ADD).toString());
        addButton.addActionListener(new java.awt.event.ActionListener()
        {
          public void actionPerformed(java.awt.event.ActionEvent evt)
          {
            java.lang.Double lastValue = (java.lang.Double)((elv.util.Property)editorTable.getModel().getValueAt(editorTable.getRowCount() - 1, 0)).getValue();
            java.lang.Double min = (editorTable.getRowCount() == 0 ? new java.lang.Double(0) : new java.lang.Double(lastValue.doubleValue() + 0.5));
            java.util.Vector<elv.util.Property> dataRow = new java.util.Vector<elv.util.Property>();
            dataRow.add(new elv.util.Property(property.getElementType(), property.getEditorType(), property.haveToTranslate(), property.getName(), min));
            ((javax.swing.table.DefaultTableModel)editorTable.getModel()).addRow(dataRow);
          }
        });

        javax.swing.JButton removeButton = new javax.swing.JButton();
        removeButton.setText("-");
        removeButton.setToolTipText(new elv.util.Action(elv.util.Action.REMOVE).toString());
        removeButton.addActionListener(new java.awt.event.ActionListener()
        {
          public void actionPerformed(java.awt.event.ActionEvent evt)
          {
            int[] selectedRows = editorTable.getSelectedRows();
            for(int i = selectedRows.length - 1; i >= 0; i--)
            {
              tableData.remove(selectedRows[i]);
              editorTable.setModel(new javax.swing.table.DefaultTableModel(tableData, tableColumnNames));
            }
          }
        });

        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new java.awt.GridLayout(0, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        javax.swing.JPanel buttonOrientationPanel = new javax.swing.JPanel();
        buttonOrientationPanel.setLayout(new java.awt.BorderLayout());
        buttonOrientationPanel.add(buttonPanel, java.awt.BorderLayout.NORTH);

        javax.swing.JPanel editorPanel = new javax.swing.JPanel();
        editorPanel.setLayout(new java.awt.BorderLayout());
        editorPanel.add(editorScrollPane, java.awt.BorderLayout.CENTER);
        editorPanel.add(buttonOrientationPanel, java.awt.BorderLayout.EAST);
        editorPanel.setPreferredSize(new java.awt.Dimension(50, 200));

        int returnOption = elv.util.Option.okCancel(editorPanel, elv.util.Util.translate(property.getName()));
        if(returnOption == javax.swing.JOptionPane.OK_OPTION)
        {
          values = new java.util.Vector<java.lang.Object>();
          for(int i = 0; i < editorTable.getRowCount(); i++)
          {
            values.add(((elv.util.Property)editorTable.getModel().getValueAt(i, 0)).getValue());
          }
          property.setValue(values);
          setChanged();
        }
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }

    /**
     * Inner method for editing task tree values.
     */
    private void getTaskTreeValue()
    {
      try
      {
        final javax.swing.JTree editorTree = new javax.swing.JTree();
        editorTree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        editorTree.setModel(new javax.swing.tree.DefaultTreeModel(new elv.util.Node(new elv.util.Root())));
        editorTree.setCellRenderer(new javax.swing.tree.DefaultTreeCellRenderer()
        {
          public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, java.lang.Object treeNode, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
          {
            super.getTreeCellRendererComponent(tree, treeNode, sel, expanded, leaf, row, hasFocus);
            setToolTipText(null);
            try
            {
              setEnabled(((elv.util.Node)treeNode).getChildCount() == 0);
              setIcon(((elv.util.Node)treeNode).getIcon());
            }
            catch(java.lang.Exception exc)
            {
              elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
            }
            return this;
          }
        });
        javax.swing.tree.DefaultTreeModel editorTreeModel = (javax.swing.tree.DefaultTreeModel)editorTree.getModel();
        // Load tree.
        elv.util.Node rootNode = (elv.util.Node)editorTreeModel.getRoot();
        elv.util.Root root = elv.util.client.ClientStub.loadAllDescendants();
        java.util.Vector<elv.util.User> users = elv.util.Util.getActualUser().getRelatives(root.getChildren());
        for(elv.util.User iteratorUser : users)
        {
          elv.util.Node iteratorUserNode = new elv.util.Node(iteratorUser);
          rootNode.add(iteratorUserNode);
          java.util.Vector<elv.task.Container> containers = iteratorUser.getChildren();
          for(elv.task.Container iteratorContainer : containers)
          {
            elv.util.Node iteratorContainerNode = new elv.util.Node(iteratorContainer);
            iteratorUserNode.add(iteratorContainerNode);
            java.util.Vector<elv.task.Task> tasks = iteratorContainer.getChildren();
            for(elv.task.Task iteratorTask : tasks)
            {
              elv.util.Node iteratorTaskNode = new elv.util.Node(iteratorTask);
              iteratorContainerNode.add(iteratorTaskNode);
            }
          }
        }
        javax.swing.JScrollPane editorScrollPane = new javax.swing.JScrollPane();
        editorScrollPane.setViewportView(editorTree);

        javax.swing.JPanel editorPanel = new javax.swing.JPanel();
        editorPanel.setLayout(new java.awt.BorderLayout());
        editorPanel.add(editorScrollPane, java.awt.BorderLayout.CENTER);
        editorPanel.setPreferredSize(new java.awt.Dimension(300, 400));

        int returnOption = elv.util.Option.yesNoCancel(editorPanel, elv.util.Util.translate(property.getName()));
        if(returnOption == javax.swing.JOptionPane.YES_OPTION)
        {
          elv.util.Node node = (elv.util.Node)editorTree.getLastSelectedPathComponent();
          if(node.getUserObject() instanceof elv.task.Task && !property.getValue().equals(node.getUserObject()))
          {
            property.setValue(node.getUserObject());
            setChanged();
          }
        }
        else if(returnOption == javax.swing.JOptionPane.NO_OPTION && property.getValue()!= null)
        {
          property.setValue(null);
          setChanged();
        }
        
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(PropertiesPanel.this, exc);
      }
    }
    
    /**
     * Inner method for change setting.
     */
    private void setChanged()
    {
      isChanged = true;
      elv.util.Util.getMainFrame().setChanged(true);
    }

  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   * @return the changeable owner object.
   */
  public elv.util.Changeable getOwner()
  {
    return propertable;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Saveable</CODE>.
   * @return the type object.
   */
  public java.lang.Object getType()
  {
    return null;
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
        java.lang.String folderPath = "";
        if(propertable instanceof elv.util.User)
        {
          folderPath = ((elv.util.User)propertable).getFolderPath();
        }
        else if(propertable instanceof elv.task.Task)
        {
          folderPath = ((elv.task.Task)propertable).getPropertyFolderPath();
        }
        java.lang.String pathName = folderPath + elv.util.Util.getFS() + propertable.getPropertyFile();
        elv.util.client.ClientStub.storeProperties(pathName, properties);
        propertable.setProperties(properties);
        if(propertable.equals(elv.util.Util.getActualUser()))
        {
          elv.util.Util.getActualUser().setProperties(properties);
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
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane propertiesScrollPane;
  // End of variables declaration//GEN-END:variables
  
}
