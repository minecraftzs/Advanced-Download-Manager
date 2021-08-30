package me.zs.AdvancedDownloadManager;

import me.zs.AdvancedDownloadManager.managers.NetManager;
import me.zs.AdvancedDownloadManager.managers.SettingsManager;
import me.zs.AdvancedDownloadManager.managers.SystemManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Advanced Download Manager (ADM) | by zs\n");
        UI.TrayUI();
        SettingsManager.checkSettingsFile();
        NetManager.checkNet();
        UI.MainUI();
        SystemManager.autoDetectionClipboardText();
    }
}
