package me.zs.AdvancedDownloadManager.managers;

import java.io.*;

public class SettingsManager {
    public static File filePath = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\ADM");
    public static File file = new File(filePath + "\\Settings.txt");
    public static String settingsFilePath = String.valueOf(file);

    public static void checkSettingsFile() {
        if (!filePath.exists()) {
            filePath.mkdir();
        } if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(settingsFilePath, false);
                fw.write("Open Detection ClipboardText:true");
                fw.flush();
                fw.close();
            } catch (Exception e) {
                System.out.println("设置文件创建失败!");
            }
        }
    }//检查设置文件是否存在

    public static void writeSettingsToFile(boolean true_or_false) {
        try {
            FileWriter fw = new FileWriter(settingsFilePath, false);
            fw.write("Open Detection ClipboardText:" + true_or_false);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//写入设置到文件

    public static String isDetectionClipboardText() throws Exception {
        FileReader fr = new FileReader(settingsFilePath);
        BufferedReader br = new BufferedReader(fr);
        String str1 = br.readLine();
        String detectionClipboardText = str1.substring(str1.substring(0, str1.indexOf(":")).length() + 1);
        br.close();
        return detectionClipboardText;
    }//在文件中获取剪贴板设置

}
