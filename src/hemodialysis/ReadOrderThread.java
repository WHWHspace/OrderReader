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
//            System.out.println(date);               //上一次读取数据的时间
            MainWindow.showMessage(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            Date currentDate = new Date();          //现在的时间
            writeLastReadTime(currentDate);
            reader.ReadNewAddedLongTermOrder(date); //读取上一次到现在之间的数据
            reader.ReadNewAddedShortTermOrder(date);
            date = currentDate;                     //跟新上一次读取的时间
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
