package com.frostwire.gui.download.bittorrent;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.limegroup.gnutella.gui.GUIMediator;
import com.limegroup.gnutella.gui.I18n;
import com.limegroup.gnutella.gui.actions.LimeAction;
import com.limegroup.gnutella.gui.util.GUILauncher;
import com.limegroup.gnutella.gui.util.GUILauncher.LaunchableProvider;

final class BTDownloadActions {

    static final ShowDetailsAction SHOW_DETAILS_ACTION = new ShowDetailsAction();
    static final ExploreAction EXPLORE_ACTION = new ExploreAction();
    static final LaunchAction LAUNCH_ACTION = new LaunchAction();
    static final ResumeAction RESUME_ACTION = new ResumeAction();
    static final PauseAction PAUSE_ACTION = new PauseAction();
    static final RemoveAction REMOVE_ACTION = new RemoveAction();

    private static abstract class RefreshingAction extends AbstractAction {

        /**
         * 
         */
        private static final long serialVersionUID = -937688457597255711L;

        public final void actionPerformed(ActionEvent e) {
            performAction(e);
            BTDownloadMediator.instance().doRefresh();
        }

        protected abstract void performAction(ActionEvent e);
    }

    final static class ShowDetailsAction extends RefreshingAction {

        /**
         * 
         */
        private static final long serialVersionUID = 6100070262538050091L;

        public ShowDetailsAction() {
            putValue(Action.NAME, I18n.tr("Details"));
            putValue(LimeAction.SHORT_NAME, I18n.tr("Show Details"));
            putValue(Action.SHORT_DESCRIPTION, I18n.tr("Show Torrent Details"));
            putValue(LimeAction.ICON_NAME, "LIBRARY_EXPLORE");
        }

        protected void performAction(ActionEvent e) {
            System.out.println("Pending implementation");
        }
    }

    private static class ExploreAction extends RefreshingAction {
        /**
         * 
         */
        private static final long serialVersionUID = -4648558721588938475L;

        public ExploreAction() {
            putValue(Action.NAME, I18n.tr("Explore"));
            putValue(LimeAction.SHORT_NAME, I18n.tr("Explore"));
            putValue(Action.SHORT_DESCRIPTION, I18n.tr("Open Folder Containing the File"));
            putValue(LimeAction.ICON_NAME, "LIBRARY_EXPLORE");
        }

        public void performAction(ActionEvent e) {
            BTDownloader[] downloaders = BTDownloadMediator.instance().getSelectedDownloaders();
            if (downloaders.length > 0) {
                File toExplore = downloaders[0].getSaveLocation();

                if (toExplore == null) {
                    return;
                }

                GUIMediator.launchExplorer(toExplore);
            }
        }
    }

    private static class LaunchAction extends RefreshingAction {

        /**
         * 
         */
        private static final long serialVersionUID = -567893064454697074L;

        public LaunchAction() {
            putValue(Action.NAME, I18n.tr("Preview Download"));
            putValue(LimeAction.SHORT_NAME, I18n.tr("Preview"));
            putValue(Action.SHORT_DESCRIPTION, I18n.tr("Preview Selected Downloads"));
            putValue(LimeAction.ICON_NAME, "DOWNLOAD_LAUNCH");
        }

        public void performAction(ActionEvent e) {
            BTDownloader[] downloaders = BTDownloadMediator.instance().getSelectedDownloaders();
            if (downloaders.length > 0) {

                LaunchableProvider[] providers = new LaunchableProvider[downloaders.length];

                for (int i = 0; i < downloaders.length; i++) {
                    providers[i] = new DownloaderProvider(downloaders[i]);
                }
                GUILauncher.launch(providers);
            }
        }
    }

    private static class ResumeAction extends RefreshingAction {

        /**
         * 
         */
        private static final long serialVersionUID = -4449981369424872994L;

        public ResumeAction() {
            putValue(Action.NAME, I18n.tr("Resume Download"));
            putValue(LimeAction.SHORT_NAME, I18n.tr("Resume"));
            putValue(Action.SHORT_DESCRIPTION, I18n.tr("Reattempt Selected Downloads"));
            putValue(LimeAction.ICON_NAME, "DOWNLOAD_FILE_MORE_SOURCES");
        }

        public void performAction(ActionEvent e) {
            BTDownloader[] downloaders = BTDownloadMediator.instance().getSelectedDownloaders();
            for (int i = 0; i < downloaders.length; i++) {
                downloaders[i].resume();
            }
        }
    }

    private static class PauseAction extends RefreshingAction {

        /**
         * 
         */
        private static final long serialVersionUID = 4682149704934484393L;

        public PauseAction() {
            putValue(Action.NAME, I18n.tr("Pause Download"));
            putValue(LimeAction.SHORT_NAME, I18n.tr("Pause"));
            putValue(Action.SHORT_DESCRIPTION, I18n.tr("Pause Selected Downloads"));
            putValue(LimeAction.ICON_NAME, "DOWNLOAD_PAUSE");
        }

        public void performAction(ActionEvent e) {
            BTDownloader[] downloaders = BTDownloadMediator.instance().getSelectedDownloaders();
            for (int i = 0; i < downloaders.length; i++) {
                downloaders[i].pause();
            }
        }
    }

    private static class RemoveAction extends RefreshingAction {

        /**
         * 
         */
        private static final long serialVersionUID = -1742554445891016991L;

        public RemoveAction() {
            putValue(Action.NAME, I18n.tr("Cancel Download"));
            putValue(LimeAction.SHORT_NAME, I18n.tr("Cancel"));
            putValue(Action.SHORT_DESCRIPTION, I18n.tr("Cancel Selected Downloads"));
            putValue(LimeAction.ICON_NAME, "DOWNLOAD_KILL");
        }

        public void performAction(ActionEvent e) {
            BTDownloadMediator.instance().removeSelection();
        }
    }

    private static class DownloaderProvider implements LaunchableProvider {

        private final BTDownloader downloader;

        public DownloaderProvider(BTDownloader downloader) {
            this.downloader = downloader;
        }

        public BTDownloader getDownloader() {
            return downloader;
        }

        public File getFile() {
            return null;
        }

    }
}