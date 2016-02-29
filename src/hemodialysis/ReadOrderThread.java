package hemodialysis;

import sun.applet.Main;
import ui.MainWindow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/26.
 */
public class ReadOrderThread extends Thread{

    public volatile boolean exit = false;

    Date date;
    OrderReader reader;
    int interval;

    public ReadOrderThread(Date date,OrderReader reader,int interval){
        this.date = date;
        this.reader = reader;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!exit){
//            System.out.println(date);               //��һ�ζ�ȡ���ݵ�ʱ��
            MainWindow.showMessage(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            Date currentDate = new Date();          //���ڵ�ʱ��
            writeLastReadTime(currentDate);
            reader.ReadNewAddedLongTermOrder(date); //��ȡ��һ�ε�����֮�������
            reader.ReadNewAddedShortTermOrder(date);
            date = currentDate;                     //������һ�ζ�ȡ��ʱ��
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeLastReadTime(Date currentDate) {
        try {
            String path = System.getProperty("user.dir");
            BufferedWriter w = new BufferedWriter(new FileWriter(new File(path + "/timerecord/lastReadTime.txt"),false));
            w.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
