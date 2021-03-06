/*
 * DataLoadDialog.java
 */

package elv.gui;

/**
 * Class for loading new data.
 * @author Elv
 */
public class DataLoadDialog extends javax.swing.JDialog
{
  // Constants.
  /** Title constant. */
  private static final java.lang.String COLUMN_SEPARATORS = "Separators.Column";
  /** Title constant. */
  private static final java.lang.String TEXT_SEPARATORS = "Separators.Text";
  /** Title constant. */
  private static final java.lang.String ENCODING = "Encoding";
  /** Title constant. */
  private static final java.lang.String VISIBLE_COUNT = "Lines.VisibleCount";
  /** Title constant. */
  private static final java.lang.String IS_HEADER = "Lines.IsHeader";
  
  /**
   * Constructor.
   */
  public DataLoadDialog()
  {
    try
    {
      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
    }
    initComponents();
    dataEditorPane.putClientProperty(javax.swing.JEditorPane.HONOR_DISPLAY_PROPERTIES, java.lang.Boolean.TRUE);
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

    buttonGroup = new javax.swing.ButtonGroup();
    dataScrollPane = new javax.swing.JScrollPane();
    dataEditorPane = new javax.swing.JEditorPane();
    encodingLabel = new javax.swing.JLabel();
    encodingComboBox = new javax.swing.JComboBox();
    linesLabel = new javax.swing.JLabel();
    linesSpinner = new javax.swing.JSpinner();
    headerCheckBox = new javax.swing.JCheckBox();
    columnSeparatorsLabel = new javax.swing.JLabel();
    columnSeparatorsTextField = new javax.swing.JTextField();
    textSeparatorsLabel = new javax.swing.JLabel();
    textSeparatorsTextField = new javax.swing.JTextField();
    okButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();
    tableScrollPane = new javax.swing.JScrollPane();
    tableTable = new javax.swing.JTable();
    tableLabel = new javax.swing.JLabel();
    refreshButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    dataEditorPane.setContentType("text/html");
    dataEditorPane.setEditable(false);
    dataEditorPane.setText("<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \rL\u00f3fasz\n    </p>\r\n  </body>\r\n</html>\r\n");
    dataScrollPane.setViewportView(dataEditorPane);

    encodingLabel.setText(elv.util.Util.translate(ENCODING) + ":");

    encodingComboBox.setModel(new EncodingComboBoxModel());
    encodingComboBox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        encodingComboBoxActionPerformed(evt);
      }
    });

    linesLabel.setText(elv.util.Util.translate(VISIBLE_COUNT) + ":");

    linesSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, 100, 1));

    headerCheckBox.setText(elv.util.Util.translate(IS_HEADER));
    headerCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    headerCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

    columnSeparatorsLabel.setText(elv.util.Util.translate(COLUMN_SEPARATORS) + ":");

    textSeparatorsLabel.setText(elv.util.Util.translate(TEXT_SEPARATORS) + ":");

    textSeparatorsTextField.setText("\"");

    okButton.setText(new elv.util.Action(elv.util.Action.OK).toString());
    okButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        okButtonActionPerformed(evt);
      }
    });

    cancelButton.setText(new elv.util.Action(elv.util.Action.CANCEL).toString());
    cancelButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        cancelButtonActionPerformed(evt);
      }
    });

    tableTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null},
        {null, null},
        {null, null},
        {null, null}
      },
      new String []
      {
        "No", "Col"
      }
    )
    {
      Class[] types = new Class []
      {
        java.lang.Integer.class, java.lang.String.class
      };

      public Class getColumnClass(int columnIndex)
      {
        return types [columnIndex];
      }
    });
    tableScrollPane.setViewportView(tableTable);

    tableLabel.setText(elv.util.Util.translate(elv.util.Table.TITLE)+ ":");

    refreshButton.setText(new elv.util.Action(elv.util.Action.REFRESH).toString());
    refreshButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        refreshButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addComponent(encodingLabel)
                  .addComponent(columnSeparatorsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                  .addComponent(encodingComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(columnSeparatorsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addComponent(textSeparatorsLabel)
                  .addComponent(linesLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(textSeparatorsTextField)
                  .addComponent(linesSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
              .addComponent(dataScrollPane))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(refreshButton)
            .addGap(6, 6, 6)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(headerCheckBox)
              .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
              .addComponent(tableLabel)))
          .addGroup(layout.createSequentialGroup()
            .addGap(172, 172, 172)
            .addComponent(okButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(cancelButton)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(headerCheckBox)
            .addGap(21, 21, 21)
            .addComponent(tableLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(encodingLabel)
              .addComponent(encodingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(linesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(linesLabel))
            .addGap(21, 21, 21)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(columnSeparatorsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(columnSeparatorsLabel)
              .addComponent(textSeparatorsLabel)
              .addComponent(textSeparatorsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(dataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
              .addComponent(refreshButton))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(cancelButton)
          .addComponent(okButton))
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_refreshButtonActionPerformed
  {//GEN-HEADEREND:event_refreshButtonActionPerformed
    loadAction();
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void encodingComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_encodingComboBoxActionPerformed
  {//GEN-HEADEREND:event_encodingComboBoxActionPerformed
    loadAction();
  }//GEN-LAST:event_encodingComboBoxActionPerformed

  private void okButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_okButtonActionPerformed
  {//GEN-HEADEREND:event_okButtonActionPerformed
    okAction();
  }//GEN-LAST:event_okButtonActionPerformed

  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
  {//GEN-HEADEREND:event_cancelButtonActionPerformed
    cancelAction();
  }//GEN-LAST:event_cancelButtonActionPerformed
  
  /**
   * Method for load action.
   */
  private void loadAction()
  {
    try
    {
//      java.lang.String fileName = "D:/data/morb_2002.txt";
      java.lang.String fileName = "c:/Documents and Settings/qpa/Dokumentumok/megbetgedés-preparation.csv";
      java.lang.String encoding = encodingComboBox.getSelectedItem().toString();
      int lineCount = java.lang.Integer.parseInt(linesSpinner.getValue().toString());
      java.lang.String columnSeparators = columnSeparatorsTextField.getText();
      java.lang.String textSeparator = textSeparatorsTextField.getText();
      if(columnSeparators.equals(""))
      {
        dataEditorPane.setContentType("text/plain");
      }
      else
      {
        dataEditorPane.setContentType("text/html");
      }
     
      java.lang.String dataString = "";
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(fileName), encoding));
      java.lang.String iteratorLine;
      for(int iteratorLineCount = 0; iteratorLineCount < lineCount && (iteratorLine = fileReader.readLine()) != null; iteratorLineCount++)
      {
        if(columnSeparators.equals(""))
        {
          dataString += iteratorLine + "\n";
        }
        else
        {
          java.lang.String row = "";
          java.lang.String header = "";
          java.lang.String[] splits = iteratorLine.split("[" + java.util.regex.Pattern.quote(columnSeparators) + "]", -1);
          for(int j = 0; j < splits.length; j++)
          {
            java.lang.String cell = splits[j];
            if(cell.startsWith(textSeparator) && cell.endsWith(textSeparator) && !cell.equals(textSeparator))
            {
              cell = cell.substring(cell.indexOf(textSeparator) + textSeparator.length(), cell.lastIndexOf(textSeparator));
            }
            row += "<td>" + cell + "</td>";
            header += iteratorLineCount != 0 ? "" : "<th>-" + (j + 1) + (headerCheckBox.isSelected() ? "-" + cell : "") + "-</th>";
          }
          if(iteratorLineCount == 0) // First.
          {
            dataString = "<html><head><style type=\"text/css\"> p {font-size: 11pt; font-family: Dialog; margin-top: 1} th {background: #E0E0E0} td {background: #FFFFFF}</style></head><body>";
            dataString += "<table bgcolor=#000000 border=0 cellpadding=1 cellspacing=1>";
            dataString += "<tr>" + header + "</tr>";
            if(!headerCheckBox.isSelected())
            {
              dataString += "<tr>" + row + "</tr>";
            }
          }
          else if(iteratorLineCount == lineCount - 1) // Last.
          {
            dataString += "<tr>" + row + "</tr>";
            dataString += "</body></html>";
          }
          else
          {
            dataString += "<tr>" + row + "</tr>";
          }
        }
      }
      fileReader.close();
//System.out.println(dataString);
      java.awt.Rectangle rectangle = dataEditorPane.getVisibleRect();
      dataEditorPane.setText(dataString);
      dataScrollPane.revalidate();
      dataEditorPane.scrollRectToVisible(rectangle);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.showErrorMessage(this, exc);
    }
  }
  
  /**
   * Method for ok action.
   */
  private void okAction()
  {
    setVisible(false);
    dispose();
    // TODO add your handling code here:
  }
  
  /**
   * Method for cancel action.
   */
  private void cancelAction()
  {
    setVisible(false);
    dispose();
  }
 
  /**
   * Inner class for encoding combo box model.
   */
  private class EncodingComboBoxModel extends javax.swing.DefaultComboBoxModel
  {
    public EncodingComboBoxModel()
    {
      for(java.nio.charset.Charset iteratorCharset : java.nio.charset.Charset.availableCharsets().values())
      {
        addElement(iteratorCharset);
        if(java.nio.charset.Charset.defaultCharset().equals(iteratorCharset))
        {
          setSelectedItem(iteratorCharset);
        }
      }
    }
  }
  public static void main(String[] args)
  {
    DataLoadDialog dld = new DataLoadDialog();
    dld.pack();
    dld.setVisible(true);
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.ButtonGroup buttonGroup;
  private javax.swing.JButton cancelButton;
  private javax.swing.JLabel columnSeparatorsLabel;
  private javax.swing.JTextField columnSeparatorsTextField;
  private javax.swing.JEditorPane dataEditorPane;
  private javax.swing.JScrollPane dataScrollPane;
  private javax.swing.JComboBox encodingComboBox;
  private javax.swing.JLabel encodingLabel;
  private javax.swing.JCheckBox headerCheckBox;
  private javax.swing.JLabel linesLabel;
  private javax.swing.JSpinner linesSpinner;
  private javax.swing.JButton okButton;
  private javax.swing.JButton refreshButton;
  private javax.swing.JLabel tableLabel;
  private javax.swing.JScrollPane tableScrollPane;
  private javax.swing.JTable tableTable;
  private javax.swing.JLabel textSeparatorsLabel;
  private javax.swing.JTextField textSeparatorsTextField;
  // End of variables declaration//GEN-END:variables
  
}
