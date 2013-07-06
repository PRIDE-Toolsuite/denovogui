/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.denovogui.gui;

import com.compomics.denovogui.DeNovoSequencingHandler;
import com.compomics.denovogui.io.FileProcessor;
import com.compomics.denovogui.util.ExtensionFileFilter;
import com.compomics.util.experiment.identification.SearchParameters;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author mva037
 */
public class SelectResultsDialog extends javax.swing.JDialog {

    /**
     * Indicates whether the user pressed the cancel button
     */
    private boolean canceled = false;
    /**
     * The output file selected
     */
    private File outFile = null;
    /**
     * The mgf file selected
     */
    private File mgfFile = null;
    /**
     * The search parameters
     */
    private SearchParameters searchParameters = null;
    /**
     * The last selected folder
     */
    private String lastSelectedFolder = null;

    /**
     * Creates new form SelectResult
     */
    public SelectResultsDialog(java.awt.Frame parent, String lastSelectedFolder) {
        super(parent, true);
        this.lastSelectedFolder = lastSelectedFolder;
        initComponents();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Indicates whether the user pressed the cancel button.
     *
     * @return whether the user pressed the cancel button
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Returns the output file selected by the user.
     *
     * @return the output file selected by the user
     */
    public File getOutFile() {
        return outFile;
    }

    /**
     * Returns the mgf file selected by the user.
     *
     * @return the mgf file selected by the user
     */
    public File getMgfFile() {
        return mgfFile;
    }

    /**
     * Returns the search parameters selected by the user.
     *
     * @return the search parameters selected by the user
     */
    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    /**
     * Returns the last selected folder
     *
     * @return the last selected folder
     */
    public String getLastSelectedFolder() {
        return lastSelectedFolder;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        browseOutButton = new javax.swing.JButton();
        outTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        browseMgfButton = new javax.swing.JButton();
        browseParametersButton = new javax.swing.JButton();
        mgfTxt = new javax.swing.JTextField();
        paramtersTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Select DenovoGUI Output"));

        browseOutButton.setText("Browse");
        browseOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseOutButtonActionPerformed(evt);
            }
        });

        outTxt.setEditable(false);

        jLabel1.setText("Pepnovo Result:");

        jLabel2.setText("Spectrua:");

        jLabel3.setText("Parameters:");

        browseMgfButton.setText("Browse");
        browseMgfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseMgfButtonActionPerformed(evt);
            }
        });

        browseParametersButton.setText("Browse");
        browseParametersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseParametersButtonActionPerformed(evt);
            }
        });

        mgfTxt.setEditable(false);

        paramtersTxt.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paramtersTxt)
                            .addComponent(mgfTxt)))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(browseOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(browseParametersButton, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(browseMgfButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseOutButton)
                    .addComponent(outTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(browseMgfButton)
                    .addComponent(mgfTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(browseParametersButton)
                    .addComponent(paramtersTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("OK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browseOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseOutButtonActionPerformed

        File startLocation = new File(lastSelectedFolder);
        JFileChooser fc = new JFileChooser(startLocation);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new ExtensionFileFilter("out", false));
        fc.setDialogTitle("Select output file");
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            outFile = fc.getSelectedFile();
            lastSelectedFolder = outFile.getParent();
            outTxt.setText(outFile.getName());
            // try to find the mgf file
            File tempMgfFile = FileProcessor.getMgfFile(outFile);
            if (tempMgfFile.exists()) {
                mgfFile = tempMgfFile;
                mgfTxt.setText(mgfFile.getName());
            }
            // try to find the parameters file
            File tempParamtersFile = new File(outFile.getParent(), DeNovoSequencingHandler.paramtersFileName);
            if (tempParamtersFile.exists()) {
                try {
                    searchParameters = SearchParameters.getIdentificationParameters(tempParamtersFile);
                    paramtersTxt.setText(tempParamtersFile.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_browseOutButtonActionPerformed

    private void browseMgfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseMgfButtonActionPerformed

        File startLocation = new File(lastSelectedFolder);
        JFileChooser fc = new JFileChooser(startLocation);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new ExtensionFileFilter("mgf", false));
        fc.setDialogTitle("Select spectrum file");
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            mgfFile = fc.getSelectedFile();
            lastSelectedFolder = mgfFile.getParent();
            mgfTxt.setText(mgfFile.getName());
        }
    }//GEN-LAST:event_browseMgfButtonActionPerformed

    private void browseParametersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseParametersButtonActionPerformed

        File startLocation = new File(lastSelectedFolder);
        JFileChooser fc = new JFileChooser(startLocation);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new ExtensionFileFilter("mgf", false));
        fc.setDialogTitle("Select spectrum file");
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File tempParamtersFile = fc.getSelectedFile();
            try {
                searchParameters = SearchParameters.getIdentificationParameters(tempParamtersFile);
                lastSelectedFolder = tempParamtersFile.getParent();
                paramtersTxt.setText(tempParamtersFile.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_browseParametersButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        canceled = true;
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseMgfButton;
    private javax.swing.JButton browseOutButton;
    private javax.swing.JButton browseParametersButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField mgfTxt;
    private javax.swing.JTextField outTxt;
    private javax.swing.JTextField paramtersTxt;
    // End of variables declaration//GEN-END:variables
}