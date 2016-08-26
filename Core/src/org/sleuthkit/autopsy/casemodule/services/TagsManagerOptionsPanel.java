/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.sleuthkit.autopsy.casemodule.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.corecomponents.OptionsPanel;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.ModuleSettings;
import org.sleuthkit.datamodel.TskCoreException;

/**
 * A panel to allow the user to create new tag names or to delete tag names that
 * user has created in the past. List of user tag names is maintained in a
 * properties file, able to be used across cases.
 * Potentially room to add other tag name options in the future.
 */
public class TagsManagerOptionsPanel extends javax.swing.JPanel implements OptionsPanel {
    
    private static final String TAGS_SETTINGS_NAME = "Tags"; //NON-NLS
    private static final String TAG_NAMES_SETTING_KEY = "TagNames"; //NON-NLS
    
    private static final String DEFAULT_DESCRIPTION = "";
    private static final String DEFAULT_COLOR_STRING = "NONE";
    
    private final DefaultListModel<CustomTagName> tagNamesListModel;
    private Set<CustomTagName> tagNames;
    private List<String> newDisplayNames;
    
    
    /**
     * Creates new form TagsManagerOptionsPanel
     */
    public TagsManagerOptionsPanel() {
        initComponents();
        
        tagNamesListModel = new DefaultListModel<>();
        tagNamesList.setModel(tagNamesListModel);
        tagNames = getTagNamesFromTagsSettings();
        newDisplayNames = new ArrayList<>();
        
        userTagNameTextField.setText("");
        tagNameErrLabel.setText("");
    }
    
    /**
     * Gets tag names from properties file.
     * 
     * @return A set, possibly empty, of CustomTagName objects
     */
    private Set<CustomTagName> getTagNamesFromTagsSettings() {
        Set<CustomTagName> tagNamesFromSettings = new TreeSet<>();
        String setting = ModuleSettings.getConfigSetting(TAGS_SETTINGS_NAME, TAG_NAMES_SETTING_KEY);
        if ((setting != null) && !setting.isEmpty()) {
            List<String> tagNameTuples = Arrays.asList(setting.split(";"));
            for (String tagNameTuple : tagNameTuples) {
                String[] tagNameAttributes = tagNameTuple.split(",");
                CustomTagName tagName = new CustomTagName(tagNameAttributes[0], tagNameAttributes[1], tagNameAttributes[2]);
                tagNamesFromSettings.add(tagName);
            }
        }
        return tagNamesFromSettings;
    }
    
    /**
     * Updates the tag names model for the tag names list component.
     */
    private void updateTagNamesListModel() {
        tagNamesListModel.clear();
        tagNames.stream().forEach((tagName) -> {
            tagNamesListModel.addElement(tagName);
        });
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
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        tagNamesListLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tagNamesList = new javax.swing.JList<>();
        userTagNameTextField = new javax.swing.JTextField();
        addTagNameButton = new javax.swing.JButton();
        deleteTagNameButton = new javax.swing.JButton();
        tagNameErrLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        jPanel1.setPreferredSize(new java.awt.Dimension(750, 500));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.jLabel1.text")); // NOI18N

        jSplitPane1.setDividerLocation(-100);
        jSplitPane1.setDividerSize(1);

        org.openide.awt.Mnemonics.setLocalizedText(tagNamesListLabel, org.openide.util.NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.tagNamesListLabel.text")); // NOI18N

        jScrollPane1.setViewportView(tagNamesList);

        userTagNameTextField.setText(org.openide.util.NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.userTagNameTextField.text")); // NOI18N
        userTagNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTagNameTextFieldActionPerformed(evt);
            }
        });

        addTagNameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/add16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(addTagNameButton, org.openide.util.NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.addTagNameButton.text")); // NOI18N
        addTagNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTagNameButtonActionPerformed(evt);
            }
        });

        deleteTagNameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/delete16.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteTagNameButton, org.openide.util.NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.deleteTagNameButton.text")); // NOI18N
        deleteTagNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTagNameButtonActionPerformed(evt);
            }
        });

        tagNameErrLabel.setForeground(new java.awt.Color(255, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(tagNameErrLabel, org.openide.util.NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.tagNameErrLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tagNameErrLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(tagNamesListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(userTagNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addTagNameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteTagNameButton)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tagNamesListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userTagNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTagNameButton)
                    .addComponent(deleteTagNameButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagNameErrLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSplitPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Adds a new tag name to the tag names list component without necessarily
     * saving the changes.
     * 
     * @param evt ActionEvent generated by clicking the Add Tag Name button
     */
    private void addTagNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTagNameButtonActionPerformed
        String newDisplayName = userTagNameTextField.getText();
        
        if (newDisplayName.isEmpty()) {
            tagNameErrLabel.setText(NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.addTagNameButton.empty"));
            return;
        }
        if (TagsManager.containsIllegalCharacters(newDisplayName)) {
            tagNameErrLabel.setText(NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.addTagNameButton.containInvalidCharacter"));
            return;
        }
        
        CustomTagName tagName = new CustomTagName(newDisplayName, DEFAULT_DESCRIPTION, DEFAULT_COLOR_STRING);
                
        boolean addedToTagNames = tagNames.add(tagName);
        
        if (!addedToTagNames) {
            tagNameErrLabel.setText(NbBundle.getMessage(TagsManagerOptionsPanel.class, "TagsManagerOptionsPanel.addTagNameButton.alreadyExists"));
            return;
        }
        
        updateTagNamesListModel();
        
        newDisplayNames.add(newDisplayName);
        userTagNameTextField.setText("");
        tagNameErrLabel.setText("");
        
        firePropertyChange(OptionsPanelController.PROP_CHANGED, null, null);
    }//GEN-LAST:event_addTagNameButtonActionPerformed
    
    /**
     * Deletes a tag name from the tag names list component without saving the
     * changes.
     * 
     * @param evt ActionEvent generated by clicking the Delete Tag Name button
     */
    private void deleteTagNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTagNameButtonActionPerformed
        CustomTagName tagName = tagNamesList.getSelectedValue();
        if (tagName == null) {
            tagNameErrLabel.setText("No tag name selected.");
        } else {
            tagNames.remove(tagName);
            updateTagNamesListModel();
            
            if (!tagNamesListModel.isEmpty()) {
                tagNamesList.setSelectedIndex(0);
            }
            
            newDisplayNames.remove(tagName.getDisplayName());
            
            firePropertyChange(OptionsPanelController.PROP_CHANGED, null, null);
        }
    }//GEN-LAST:event_deleteTagNameButtonActionPerformed

    /**
     * Activates addTagNameButtonActionPerformed instead of the OptionsPanel OK
     * button when the userTagNameTextField experiences an action.
     * @param evt ActionEvent
     */
    private void userTagNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userTagNameTextFieldActionPerformed
        addTagNameButtonActionPerformed(evt);
    }//GEN-LAST:event_userTagNameTextFieldActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTagNameButton;
    private javax.swing.JButton deleteTagNameButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel tagNameErrLabel;
    private javax.swing.JList<CustomTagName> tagNamesList;
    private javax.swing.JLabel tagNamesListLabel;
    private javax.swing.JTextField userTagNameTextField;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Stores tag name changes in the properties file, called when OK or Apply
     * is selected in the OptionsPanel.
     * 
     * Adds all new tag names to the case database for use.
     */
    @Override
    public void store() {
        for (String displayName : newDisplayNames) {
            try {
                Case.getCurrentCase().getServices().getTagsManager().addTagName(displayName);
            } catch (TagsManager.TagNameAlreadyExistsException ex) {
                //Do nothing, this is just to update the database
            } catch (TskCoreException ex) {
                Logger.getLogger(TagsManagerOptionsPanel.class.getName()).log(Level.SEVERE, "Error adding " + displayName + " tag name", ex);
            }
        }
        newDisplayNames = new ArrayList<>();
        
        StringBuilder builder = new StringBuilder();
        for (CustomTagName tagName : tagNames) {
            if (builder.length() != 0) {
                builder.append(";");
            }
            builder.append(tagName.toSettingsFormat());
        }
        ModuleSettings.setConfigSetting(TAGS_SETTINGS_NAME, TAG_NAMES_SETTING_KEY, builder.toString());
    }
    
    /**
     * Updates the tag names list component with tag names from the properties
     * file.
     */
    @Override
    public void load() {
        tagNames = getTagNamesFromTagsSettings();
        updateTagNamesListModel();
        if (!tagNamesListModel.isEmpty()) {
            tagNamesList.setSelectedIndex(0);
        }
        tagNameErrLabel.setText("");
    }
    
    /**
     * Discards changes made to the tag names list component if Cancel button is
     * selected in the OptionsPanel.
     */
    public void cancelChanges() {
        tagNames = getTagNamesFromTagsSettings();
        newDisplayNames = new ArrayList<>();
    }
    
    /**
     * Because the DTO TagName constructor should not be called outside of its
     * class package, CustomTagName is used in this tags managers panel for the
     * purpose of tracking the description and color of each tag name.
     */
    private class CustomTagName implements Comparable<CustomTagName> {
        private final String displayName;
        private final String description;
        private final String colorName;
        
        private CustomTagName(String displayName, String description, String colorName) {
            this.displayName = displayName;
            this.description = description;
            this.colorName = colorName;
        }
        
        private String getDisplayName() {
            return displayName;
        }
        
        @Override
        public int compareTo(CustomTagName other) {
            return this.getDisplayName().toLowerCase().compareTo(other.getDisplayName().toLowerCase());
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + Objects.hashCode(this.displayName);
            hash = 83 * hash + Objects.hashCode(this.description);
            hash = 83 * hash + Objects.hashCode(this.colorName);
            return hash;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof CustomTagName)) return false;
            CustomTagName thatTagName = (CustomTagName) obj;
            return this.getDisplayName().equals(thatTagName.getDisplayName());
        }
        
        @Override
        public String toString() {
            return displayName;
        }
        
        /**
         * @return A string representation of the tag name in the format that is
         *         used by the properties file.
         */
        public String toSettingsFormat() {
            return displayName + "," + description + "," + colorName;
        }
        
    }
}
