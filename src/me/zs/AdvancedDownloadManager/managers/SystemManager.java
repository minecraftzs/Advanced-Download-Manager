package me.zs.AdvancedDownloadManager.managers;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class SystemManager {
    public static String getClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf == null) {
            return null;
        }
        if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                return (String) clipTf.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static void detectionClipboardText(boolean true_or_false) {
        if (true_or_false) {
            String urlPath = SystemManager.getClipboardText();
            if (urlPath.startsWith("http")) {
                int option = JOptionPane.showConfirmDialog(null, "检测到链接, 是否开始下载?\n" + urlPath, "确认", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    NetManager.Download(urlPath);
                    System.exit(0);//bug1: 这里要排除已经下载过的url再循环检测剪贴板
                } //bug2: 这里要排除刚刚的url再循环检测剪贴板
            }
        }
    }

    public static void autoDetectionClipboardText() {
        while (true) {
            try {
                Thread.sleep(4000);
                detectionClipboardText(Boolean.parseBoolean(SettingsManager.isDetectionClipboardText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
