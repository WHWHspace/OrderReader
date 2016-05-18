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
            Date currentDate = new Date();          //现在的时间

            logger.info(currentDate);
//            reader.readNewAddedLongTermOrder(date,currentDate); //读取上一次到现在之间的数据
//            reader.readNewAddedShortTermOrder(date,currentDate);
            reader.readNewAddedLongTermOrderByIDs(date,currentDate);
            reader.readNewAddedShortTermOrderByIDs(date, currentDate);
            writeLastReadTime(currentDate);
            date = currentDate;                     //跟新上一次读取的时间
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                logger.error(new Date() + " 暂停线程错误\n" + e);
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
            logger.error(new Date() + " 写入上一次读取时间错误\n" + e);
        }
    }
}
