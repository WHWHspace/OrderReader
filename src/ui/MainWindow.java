package ui;

import hemodialysis.OrderReader;
import launcher.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by 31344 on 2016/2/29.
 */
public class MainWindow extends JFrame{

    JButton readAllOrders;
    JTabbedPane panel;
    JPanel functionPanel;
    JPanel logPanel;
    static TextArea logArea;
    static ArrayList<String> messageList = new ArrayList<String>();

    public MainWindow(){
        initComponent();

        this.setTitle("his医嘱读取系统");
        this.setSize(500, 300);
        this.setLocation(100, 100);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initComponent() {
        for (int i = 0; i < 15; i++){
            messageList.add("\n");
        }

        panel = new JTabbedPane();
        panel.setSize(500, 300);
        panel.setLocation(0, 0);
        this.add(panel);

        functionPanel = new JPanel();
        functionPanel.setSize(500, 300);
        panel.addTab("功能", functionPanel);

        logPanel = new JPanel();
        logPanel.setLayout(null);
        logPanel.setSize(500, 300);
        panel.addTab("日志", logPanel);

        logArea = new TextArea();
        logArea.setSize(500, 300);
        logArea.setLocation(0, 0);
        logPanel.add(logArea);

        readAllOrders = new JButton("读取所有医嘱");
        readAllOrders.setSize(100, 50);
        readAllOrders.setLocation(50, 50);
        functionPanel.add(readAllOrders);
        readAllOrders.addActionListener(new ReadOrderActionListener());
    }


    private class ReadOrderActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null,"该操作将删除已经读取的医嘱记录，重新读取所有医嘱数据。是否继续？",null,JOptionPane.YES_NO_OPTION);
            //0,确认。1,取消
            if (result == 0){
                Main.readAllOrders();
            }
        }
    }

    public static void showMessage(String message){
        messageList.remove(0);
        messageList.add(message);

        String text = "";
        for (int i = 0; i < messageList.size(); i++ ){
            text += messageList.get(i);
            text += "\n";
        }
        logArea.setText(text);
    }
}
