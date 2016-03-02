package hemodialysis;

import launcher.Main;
import org.apache.log4j.Logger;

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
    private static Logger logger = Main.logger;

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
            logger.info(date);
            Date currentDate = new Date();          //���ڵ�ʱ��
            writeLastReadTime(currentDate);
            reader.ReadNewAddedLongTermOrder(date); //��ȡ��һ�ε�����֮�������
            reader.ReadNewAddedShortTermOrder(date);
            date = currentDate;                     //������һ�ζ�ȡ��ʱ��
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                logger.error(new Date() + " ��ͣ�̴߳���\n" + e.getStackTrace());
            }
        }
    }

    private void writeLastReadTime(Date currentDate) {
        try {
            String path = System.getProperty("user.dir");
            BufferedWriter w = new BufferedWriter(new FileWriter(new File(path + "/config/lastReadTime.txt"),false));
            w.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate));
            w.close();
        } catch (IOException e) {
            logger.error(new Date() + " д����һ�ζ�ȡʱ�����\n" + e.getStackTrace());
        }
    }
}
