/*
 * TreeTableModel.java
 */
package elv.gui.treetable;

/**
 * TreeTableModel is the model used by a JTreeTable. It extends TreeModel
 * to add methods for getting information about the set of columns each 
 * node in the TreeTableModel may have. Each column, like a column in 
 * a TableModel, has a name and a type associated with it. Each node in 
 * the TreeTableModel can return a value for each of the columns and 
 * set that value if isCellEditable() returns true. 
 * @author Qpa
 */
public interface TreeTableModel extends javax.swing.tree.TreeModel
{
    /**
     * Returns the number of available columns.
     */
    public int getColumnCount();

    /**
     * Returns the name for column number <code>column</code>.
     */
    public java.lang.String getColumnName(int column);

    /**
     * Returns the type for column number <code>column</code>.
     */
    public java.lang.Class getColumnClass(int column);

    /**
     * Returns the value to be displayed for node <code>node</code>, 
     * at column number <code>column</code>.
     */
    public java.lang.Object getValueAt(java.lang.Object node, int column);

    /**
     * Indicates whether the the value for node <code>node</code>, 
     * at column number <code>column</code> is editable.
     */
    public boolean isCellEditable(java.lang.Object node, int column);

    /**
     * Sets the value for node <code>node</code>, 
     * at column number <code>column</code>.
     */
    public void setValueAt(java.lang.Object aValue, java.lang.Object node, int column);
}

