/*
 * Copyright (C) 2016 Thomas Kercheval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package CafeDansaDB;

import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * EditDancer.java
 * A class which defines the form by which users may edit a dancer
 * extant in our application.
 * <pre>
    Project: CafeDansa Database
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on Apr 14, 2016, 12:04:19 PM
    Revised on Arp 14, 2016, 2:27:35 PM
 </pre>
 * @author Thomas Kercheval
 */
public class EditDancer extends javax.swing.JDialog 
{

    Validation valid = new Validation();
    private Dancer infoDancer = null;
    private int id;
    private String dancerName;
    private int style;
    private String prof;
    private String years;
    private String phone;
    private String email;
    
    public EditDancer() 
    {
        this.setModal(true);
        initComponents();
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(saveJButton);
    }

    /**
     * Constructor which spawns EditDancer GUI and sets modality.
     * @param aThis GUI which spawns EditDancer
     * @param b boolean value which indicates modality
     */
    public EditDancer(Dancer dansa) 
    {
        this();
        Integer[] yearOptions = new Integer[100];
        for (int i = 0; i < yearOptions.length; i++) {
            yearOptions[i] = i;
        }
        styleJComboBox.setModel(new DefaultComboBoxModel(
                CafeDanseDataBaseGUI.STYLES));
        profJComboBox.setModel(new DefaultComboBoxModel(
                CafeDanseDataBaseGUI.PROFICIENCIES));
        yearsJComboBox.setModel(new DefaultComboBoxModel(yearOptions));
        nameJTextField.setText(dansa.getName());
        styleJComboBox.setSelectedItem(dansa.getStyle());
        profJComboBox.setSelectedItem(dansa.getProf());
        yearsJComboBox.setSelectedItem(dansa.getYears());
        phoneJTextField.setText(dansa.getPhone());
        emailJTextField.setText(dansa.getEmail());
    }


    /**
     * Method: getDancer
     * Returns the new Dancer object.
     * @return Dancer: the dancer being edited.
     */
    public Dancer getDancer() 
    {
        return infoDancer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        saveJButton = new javax.swing.JButton();
        exitJButton = new javax.swing.JButton();
        displayJPanel = new javax.swing.JPanel();
        nameJLabel = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        styleJLabel = new javax.swing.JLabel();
        styleJComboBox = new javax.swing.JComboBox<>();
        profJLabel = new javax.swing.JLabel();
        profJComboBox = new javax.swing.JComboBox<>();
        yearsJLabel = new javax.swing.JLabel();
        yearsJComboBox = new javax.swing.JComboBox<>();
        phoneJLabel = new javax.swing.JLabel();
        phoneJTextField = new javax.swing.JTextField();
        emailJLabel = new javax.swing.JLabel();
        emailJTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Dancer Form");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 2, 36)); // NOI18N
        jLabel2.setText("Edit Existing Dancer");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        controlPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setMinimumSize(new java.awt.Dimension(299, 45));
        controlPanel.setLayout(new java.awt.GridLayout(1, 5, 5, 5));

        saveJButton.setBackground(new java.awt.Color(204, 255, 204));
        saveJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        saveJButton.setMnemonic('S');
        saveJButton.setText("Save");
        saveJButton.setToolTipText("Save edited Dancer");
        saveJButton.setMinimumSize(new java.awt.Dimension(57, 45));
        saveJButton.setPreferredSize(new java.awt.Dimension(57, 35));
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(saveJButton);

        exitJButton.setBackground(new java.awt.Color(204, 255, 204));
        exitJButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        exitJButton.setMnemonic('C');
        exitJButton.setText("Cancel");
        exitJButton.setToolTipText("Cancel editing Dancer");
        exitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });
        controlPanel.add(exitJButton);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        displayJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        displayJPanel.setLayout(new java.awt.GridLayout(6, 2, 5, 5));

        nameJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameJLabel.setText("Name of Dancer: ");
        displayJPanel.add(nameJLabel);

        nameJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameJTextField.setToolTipText("Enter First&Last name, middle optional, must be capitalized...");
        displayJPanel.add(nameJTextField);

        styleJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        styleJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        styleJLabel.setText("Style:");
        displayJPanel.add(styleJLabel);

        styleJComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        styleJComboBox.setToolTipText("Select the style of dance");
        displayJPanel.add(styleJComboBox);

        profJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        profJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        profJLabel.setText("Level of Proficiency:");
        displayJPanel.add(profJLabel);

        profJComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        profJComboBox.setToolTipText("Select the level of proficiency");
        displayJPanel.add(profJComboBox);

        yearsJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        yearsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        yearsJLabel.setText("Years dancing:");
        displayJPanel.add(yearsJLabel);

        yearsJComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        yearsJComboBox.setToolTipText("Select the number of years of experience");
        displayJPanel.add(yearsJComboBox);

        phoneJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        phoneJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        phoneJLabel.setText("phone:");
        displayJPanel.add(phoneJLabel);

        phoneJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        phoneJTextField.setToolTipText("Enter phone number, with area code");
        displayJPanel.add(phoneJTextField);

        emailJLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailJLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emailJLabel.setText("email:");
        displayJPanel.add(emailJLabel);

        emailJTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        emailJTextField.setToolTipText("Enter a valid email address");
        displayJPanel.add(emailJTextField);

        getContentPane().add(displayJPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event handler for saving the edited dancer from text field input.
     * Validates input before saving and closing the pop-up window.
     * @param evt
     * @return void
     */
    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
        int id = 0;
        String name = nameJTextField.getText();
        String prof = (String) profJComboBox.getSelectedItem();
        int years = (int) yearsJComboBox.getSelectedItem();
        String phone = phoneJTextField.getText();
        String style = (String) styleJComboBox.getSelectedItem();
        String email = emailJTextField.getText();
        if (validateFields(name, phone, email)) {
            try {
                infoDancer = new Dancer(id, name, style, prof, years, phone, email);
                this.setVisible(false);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                                          "Some Fields must be numeric.",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_saveJButtonActionPerformed

    /**
     * Method to validate the input for a dancer.
     * @param name Name of the Dancer to be edited.
     * @param phone Phone of the Dancer to be edited.
     * @param email Email of the Dancer to be edited.
     * @return true if fields are valid.
     */
    public  boolean validateFields(String name, String phone, String email) {
        if (!Validation.isName(name)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid name.\n"
                                        + "First and Last, middle optional.\n"
                                        + "\nDid you remember to Capitalize?",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!Validation.isPhone(phone)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid phone number.",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            
            return false;
        } else if (!Validation.isEmail(email)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid email.",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Event handler for the cancellation of new Dancer creation.
     * @param evt
     * @return void
     */
    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        // End  program
        this.infoDancer = null;
        this.setVisible(false);
    }//GEN-LAST:event_cancelJButtonActionPerformed

//    /**
//     * Spawns AddDancer form.
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(EditDancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(EditDancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(EditDancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(EditDancer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new EditDancer().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayJPanel;
    private javax.swing.JLabel emailJLabel;
    private javax.swing.JTextField emailJTextField;
    private javax.swing.JButton exitJButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JLabel phoneJLabel;
    private javax.swing.JTextField phoneJTextField;
    private javax.swing.JComboBox<String> profJComboBox;
    private javax.swing.JLabel profJLabel;
    private javax.swing.JButton saveJButton;
    private javax.swing.JComboBox<String> styleJComboBox;
    private javax.swing.JLabel styleJLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JComboBox<String> yearsJComboBox;
    private javax.swing.JLabel yearsJLabel;
    // End of variables declaration//GEN-END:variables
}
