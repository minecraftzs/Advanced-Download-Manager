package me.zs.AdvancedDownloadManager.managers;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

public class DownloadManager {
    public static void mode1(String urlPath, String filePath) throws Exception {
        System.out.println("开始下载......");
        URL url = new URL(urlPath);
        URLConnection con = url.openConnection();
        con.connect();
        InputStream is = con.getInputStream();
        FileOutputStream os = new FileOutputStream(filePath);
        byte[] buf = new byte[10000000];
        int len;
        while((len=is.read(buf)) != -1) {
            os.write(buf, 0, len);
        } os.close();
    }//低于10MB

    public static void mode2(String urlPath, String filePath) throws Exception {
        System.out.println("开始下载......");
        URL url = new URL(urlPath);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }//高于10MB低于1GB

    public static void mode3(String urlPath, String filePath) throws Exception {
        System.out.println("开始下载......");
    }//高于1GB: 施工

    public static void mode4(String urlPath, String filePath) throws Exception {
        System.out.println("开始下载......");
    }//磁力下载: 施工

    public static void mode5(String urlPath, String filePath) throws Exception {
        System.out.println("开始下载......");
    }//ED2K下载: 施工

}
