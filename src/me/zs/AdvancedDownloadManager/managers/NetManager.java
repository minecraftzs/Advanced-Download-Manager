package me.zs.AdvancedDownloadManager.managers;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class NetManager {
    public static void checkNet() {
        Runtime r = Runtime.getRuntime();
        Process p;
        try {
            System.out.println("正在检查网络连接......");
            p = r.exec("ping download.grmine.cn");
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            isr.close();
            br.close();
            if (!sb.toString().equals("")) {
                if (sb.toString().indexOf("TTL") > 0) {
                    System.out.println("网络通畅!");
                } else {
                    System.out.println("未连接网络!");
                    JOptionPane.showMessageDialog(null, "网络出现问题, 请检查网络连接.", "警告", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);//bug4: 接下去不显示MainUI但不退出程序
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNetFileSize(String urlPath) {
        URLConnection connection = null;
        try {
            System.out.println("正在连接网络资源中......");
            URL url = new URL(urlPath);
            connection = url.openConnection();
            connection.connect();
        } catch (Exception e) {
            System.out.println("连接资源失败!");
        } return connection.getContentLength() / 1048576;
    }

    public static int getMagnetFileSize(String urlPath) {
        try {
            System.out.println("正在连接网络资源中......");
        } catch (Exception e) {
            System.out.println("连接资源失败!");
        }
        return 0;
    }
    public static String getUrlText(InputStream is) throws Exception {
        byte[] b = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int length;
        while ((length = is.read(b)) != -1) {
            sb.append(new String(b, 0, length));
        } return sb.toString();
    }

    public static InputStream Get(String urlPath) throws Exception {
        URL url = new URL(urlPath);//初始化url路径
        HttpURLConnection huc = (HttpURLConnection)url.openConnection();//连接url
        return huc.getInputStream();
    }

    public static void Download(String urlPath) {
        try {
            if (urlPath.startsWith("https://download.grmine.cn")) {
                System.out.println("欢迎使用Grmine下载源!");
                String trueUrlPath = NetManager.getUrlText(NetManager.Get(urlPath));
                int fileSize = NetManager.getNetFileSize(trueUrlPath);
                System.out.println("资源大小: " + fileSize + "MB");
                String filePath = System.getProperty("user.dir") + "\\" + urlPath.substring(urlPath.substring(0, urlPath.indexOf("?/")).length() + 2);
                System.out.println("文件保存位置: " + filePath);
                DownloadManager.mode1(trueUrlPath, filePath);
                System.out.println("下载成功!");
                JOptionPane.showMessageDialog(null, "下载成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);//bug3: 这里要取消下面的所有行为但不关闭程序
            }
            int fileSize = NetManager.getNetFileSize(urlPath);
            System.out.println("资源大小: " + fileSize + "MB");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showDialog(new JLabel(), "保存");
            String filePath = fileChooser.getSelectedFile().getPath();
            if (fileSize <= 10) {
                System.out.println("已开启稳定模式");
                DownloadManager.mode1(urlPath, filePath);
            } if (fileSize > 1000) {
                System.out.println("太大了");//分片下载施工中
            } if (10 < fileSize && fileSize <= 1000) {
                System.out.println("已开启秒传模式");
                DownloadManager.mode2(urlPath, filePath);
            }
            System.out.println("下载成功!");
            JOptionPane.showMessageDialog(null, "下载成功!", "提示", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e1) {
            System.out.println("未知错误, 请重试.");
        }
    }
}
