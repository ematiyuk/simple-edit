package simpleedit;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * FindDialog.java
 *
 * @author Eugene Matiyuk (ematiyuk@gmail.com)
 * @year 2012
 */
public class FindDialog extends javax.swing.JDialog {

    private JTextArea currTextArea;
    private String prgrmName;

    /**
     * Creates new FindDialog
     *
     * @param parent
     * @param modal
     */
    public FindDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextLabel = new javax.swing.JLabel();
        jInputTextField = new javax.swing.JTextField();
        jFindButton = new javax.swing.JButton();
        jCancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Найти");
        setResizable(false);

        jTextLabel.setText("Что:");

        jFindButton.setText("Найти");
        jFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFindButtonActionPerformed(evt);
            }
        });

        jCancelButton.setText("Отмена");
        jCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jInputTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFindButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextLabel)
                    .addComponent(jInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFindButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCancelButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCancelButtonActionPerformed
        hideDialog();
    }//GEN-LAST:event_jCancelButtonActionPerformed

    private void jFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFindButtonActionPerformed
        findText();
    }//GEN-LAST:event_jFindButtonActionPerformed

    public void setTextArea(JTextArea textarea) {
        currTextArea = textarea;
    }

    public void setProgramName(String programName) {
        prgrmName = programName;
    }

    public String getProgramName() {
        return prgrmName;
    }

    public void showDialog() {
        FindDialog.this.setVisible(true);
    }

    public void hideDialog() {
        FindDialog.this.setVisible(false);
    }

    public void findText() {
        String currSearchText = jInputTextField.getText();
        if (currSearchText.isEmpty()) {
        } else {
            currTextArea.getHighlighter().removeAllHighlights();
            ArrayList<Integer> indices;
            indices = Utils.kmpSearch(currTextArea.getText().toCharArray(), currSearchText.toCharArray());
            if (!indices.isEmpty()) {
                DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
                Highlighter highlighter = currTextArea.getHighlighter();
                int _fromRange;
                int _toRange;
                for (Integer elem : indices) {
                    _fromRange = elem;
                    _toRange = _fromRange + currSearchText.length();
                    try {
                        highlighter.addHighlight(_fromRange, _toRange, painter);
                    } catch (BadLocationException ex) {
                        System.out.println("BadLocationException was caught." + ex);
                    }
                }
            } else {
                String message = "Не удается найти \"" + currSearchText + "\"";
                JOptionPane.showMessageDialog(this, message, this.getProgramName(), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCancelButton;
    private javax.swing.JButton jFindButton;
    private javax.swing.JTextField jInputTextField;
    private javax.swing.JLabel jTextLabel;
    // End of variables declaration//GEN-END:variables
}
