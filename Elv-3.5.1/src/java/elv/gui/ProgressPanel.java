/*
 * ProgressPanel.java
 */

package elv.gui;

/**
 * Class for progress reprezentation.
 * @author Qpa
 */
public class ProgressPanel extends javax.swing.JPanel
{
  /**
   * Variable.
   */
  private elv.util.Progress progress;
  
  /**
   * Constructor
   */
  public ProgressPanel(elv.util.Progress progress)
  {
    this.progress = progress;
    initComponents();
  }
  
  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  private void initComponents()//GEN-BEGIN:initComponents
  {
    progressLabel = new javax.swing.JLabel();
    progressBar = new javax.swing.JProgressBar();

    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

    progressLabel.setLabelFor(progressBar);
    progressLabel.setText(progress.toString() + ": " + progress.getValue() + "/" + progress.getMaximum()
    );
    add(progressLabel);

    progressBar.setMaximum(progress.getMaximum());
    progressBar.setMinimum(progress.getMinimum());
    progressBar.setValue(progress.getValue());
    add(progressBar);

  }//GEN-END:initComponents

  /**
   * Method to get the progress.
   * @return the progress of this progress panel.
   */
  public elv.util.Progress getProgress()
  {
    return progress;
  }
  
  /**
   * Method to set the value.
   * @param the new value.
   */
  public void set(int value)
  {
    progressLabel.setText(progress.toString() + ": " + value + "/" + progress.getMaximum());
    progressBar.setValue(value);
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JProgressBar progressBar;
  private javax.swing.JLabel progressLabel;
  // End of variables declaration//GEN-END:variables
  
}
