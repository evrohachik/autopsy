/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2018 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *s
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.contentviewers;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.freedesktop.gstreamer.GstException;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.MessageNotifyUtil;
import org.sleuthkit.datamodel.AbstractFile;

/**
 * Media content viewer for videos, sounds and images.
 */
@SuppressWarnings("PMD.SingularField") // UI widgets cause lots of false positives
class MediaFileViewer extends javax.swing.JPanel implements FileTypeViewer {

    private static final Logger LOGGER = Logger.getLogger(MediaFileViewer.class.getName());
    private AbstractFile lastFile;
    //UI
    private MediaPlayerPanel mediaPlayerPanel;
    private final MediaViewImagePanel imagePanel;
    private final boolean imagePanelInited;

    private static final String IMAGE_VIEWER_LAYER = "IMAGE"; //NON-NLS
    private static final String MEDIA_PLAYER_LAYER = "AUDIO_VIDEO"; //NON-NLS

    /**
     * Creates a new MediaFileViewer.
     */
    public MediaFileViewer() {

        initComponents();

        try {
            mediaPlayerPanel = new MediaPlayerPanel();
        } catch (GstException | UnsatisfiedLinkError ex) {
            LOGGER.log(Level.SEVERE, "Error initializing gstreamer for audio/video viewing and frame extraction capabilities", ex); //NON-NLS
            MessageNotifyUtil.Notify.error(
                    NbBundle.getMessage(this.getClass(), "MediaFileViewer.initGst.gstException.msg"),
                    ex.getMessage());
        }
        imagePanel = new MediaViewImagePanel();
        imagePanelInited = imagePanel.isInited();

        customizeComponents();
        LOGGER.log(Level.INFO, "Created MediaView instance: {0}", this); //NON-NLS
    }

    private void customizeComponents() {
        add(imagePanel, IMAGE_VIEWER_LAYER);
        
        if(mediaPlayerPanel != null) {
            add(mediaPlayerPanel, MEDIA_PLAYER_LAYER);
        }

        showImagePanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.CardLayout());
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(MediaFileViewer.class, "MediaFileViewer.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * Returns a list of mimetypes supported by this viewer
     *
     * @return list of supported mimetypes
     */
    @Override
    public List<String> getSupportedMIMETypes() {

        List<String> mimeTypes = new ArrayList<>();

        mimeTypes.addAll(this.imagePanel.getSupportedMimeTypes());
        if(mediaPlayerPanel != null) {
            mimeTypes.addAll(this.mediaPlayerPanel.getSupportedMimeTypes());
        }
        
        return mimeTypes;
    }

    /**
     * Set up the view to display the given file.
     *
     * @param file file to display
     */
    @Override
    public void setFile(AbstractFile file) {
        try {

            if (file == null) {
                resetComponent();
                return;
            }

            if (file.equals(lastFile)) {
                return; //prevent from loading twice if setNode() called mult. times
            }

            lastFile = file;
            if (mediaPlayerPanel != null && mediaPlayerPanel.isSupported(file)) {
                mediaPlayerPanel.loadFile(file);
                this.showVideoPanel();
            } else if (imagePanelInited && imagePanel.isSupported(file)) {
                imagePanel.showImageFx(file);
                this.showImagePanel();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while setting node", e); //NON-NLS
        }
    }

    /**
     * Show the media player panel.
     */
    private void showVideoPanel() {
        CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, MEDIA_PLAYER_LAYER);
    }

    /**
     * Show the image panel.
     */
    private void showImagePanel() {
        CardLayout layout = (CardLayout) this.getLayout();
        layout.show(this, IMAGE_VIEWER_LAYER);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void resetComponent() {
        if (mediaPlayerPanel != null) {
            mediaPlayerPanel.reset();
        }
        imagePanel.reset();
        lastFile = null;
    }

    /**
     * Panel used to display media content.
     */
    protected interface MediaViewPanel {

        /**
         * @return supported mime types
         */
        List<String> getSupportedMimeTypes();

        /**
         * returns supported extensions (each starting with .)
         *
         * @return
         */
        List<String> getSupportedExtensions();

        boolean isSupported(AbstractFile file);
    }
}
