package me.zs.AdvancedDownloadManager;

import me.zs.AdvancedDownloadManager.managers.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UI {
    public static void MainUI() {
        JFrame inputUI = new JFrame("输入文件地址");
        try {
            Image icon = ImageIO.read(inputUI.getClass().getResource("/me/zs/AdvancedDownloadManager/resources/icon_32×32.png"));
            inputUI.setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputUI.setLayout(new FlowLayout());
        inputUI.setBounds(620, 340, 300, 90);
        JTextField textField = new JTextField();
        textField.setColumns(20);
        textField.requestFocus();
        textField.setFont(new Font("楷体", Font.PLAIN, 20));
        inputUI.add(textField);
        JButton button = new JButton();
        button.setText("确定");
        button.addActionListener(e -> {
            String urlPath = textField.getText();
            if (urlPath.startsWith("http")) {
                inputUI.setVisible(false);
                NetManager.Download(urlPath);
            }
            if (urlPath.startsWith("magnet:?")) {
                inputUI.setVisible(false);
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.showDialog(new JLabel(), "保存");
                    String filePath = fileChooser.getSelectedFile().getPath();
                    JOptionPane.showMessageDialog(null, "检测到您下载的是磁力资源\n已自动为您转为磁力下载通道", "提示", JOptionPane.PLAIN_MESSAGE);
                    DownloadManager.mode4(urlPath, filePath);
                } catch (Exception e1) {
                    System.out.println("未知错误, 请重试.");
                }
            }
            if (urlPath.startsWith("ed2k:")) {
                inputUI.setVisible(false);
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.showDialog(new JLabel(), "保存");
                    String filePath = fileChooser.getSelectedFile().getPath();
                    JOptionPane.showMessageDialog(null, "检测到您下载的是ED2K资源\n已自动为您转为ED2K下载通道", "提示", JOptionPane.PLAIN_MESSAGE);
                    DownloadManager.mode5(urlPath, filePath);
                } catch (Exception e1) {
                    System.out.println("未知错误, 请重试.");
                }
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    inputUI.setVisible(false);
                }
            }
        });
        inputUI.add(button);
        inputUI.setVisible(true);
    }

    public static void TrayUI() {
        if (SystemTray.isSupported()) {
            try {
                PopupMenu frame = new PopupMenu();
                MenuItem about = new MenuItem("About ADM");
                about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Advanced Download Manager(ADM) | by zs\n\n" +
                        "v1.1-----2021.8.24\n更新了秒传模式和稳定模式: \n秒传模式资源必须大于10MB并小于1G, 速度最快可达10MB/s;\n稳定模式针对小于10MB的资源, 速度较慢.\n\n" +
                        "v1.2-----2021.8.27\n更新了检测剪贴板中url并询问是否下载, 修改下载方法, 提升性能.\n" +
                        "本程序开源免费, 请尊重版权.", "关于本程序", JOptionPane.PLAIN_MESSAGE));
                frame.add(about);

                MenuItem settings = new MenuItem("Settings");
                settings.addActionListener(e -> UI.SettingUI());
                frame.add(settings);

                MenuItem exit = new MenuItem("Exit");
                exit.addActionListener(e -> System.exit(0));
                frame.add(exit);

                Image icon = ImageIO.read(frame.getClass().getResource("/me/zs/AdvancedDownloadManager/resources/icon_16×16.png"));
                TrayIcon trayIcon = new TrayIcon(icon, "Advanced Download Manager", frame);
                trayIcon.addActionListener(e -> {
                    NetManager.checkNet();
                    UI.MainUI();
                });
                SystemTray.getSystemTray().add(trayIcon);
            } catch (Exception e) {
                System.out.println("创建托盘图标错误!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "系统不兼容托盘图标", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void SettingUI() {
        JFrame frame = new JFrame("设置");
        try {
            Image icon = ImageIO.read(frame.getClass().getResource("/me/zs/AdvancedDownloadManager/resources/icon_32×32.png"));
            frame.setIconImage(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setLayout(new FlowLayout());
        frame.setBounds(580, 280, 500, 400);
        JPanel detectionClipboardTextPanel = new JPanel();
        JLabel detectionClipboardTextLabel = new JLabel("剪贴板链接监视");
        detectionClipboardTextPanel.add(detectionClipboardTextLabel);
        JCheckBox detectionClipboardTextCheckBox = new JCheckBox();
        try {
            detectionClipboardTextCheckBox.setSelected(Boolean.parseBoolean(SettingsManager.isDetectionClipboardText()));
            detectionClipboardTextCheckBox.addItemListener(e -> SettingsManager.writeSettingsToFile(detectionClipboardTextCheckBox.isSelected()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        detectionClipboardTextPanel.add(detectionClipboardTextCheckBox);
        frame.add(detectionClipboardTextPanel);
        frame.setVisible(true);
    }
}
