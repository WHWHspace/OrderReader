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
 * 读取医嘱线程
 */
public class ReadOrderThread extends Thread{

    public volatile boolean exit = false;
    private static Logger logger = Main.logger;

    private Date lastDate;              //记录上一次读取医嘱的时间
    private OrderReader reader;
    private int interval;

    public ReadOrderThread(Date date,OrderReader reader,int interval){
        this.lastDate = date;
        this.reader = reader;
        this.interval = interval;
    }


    /**
     * 每隔一段时间读取一次医嘱数据
     */
    @Override
    public void run() {
        while (!exit){
            Date currentDate = new Date();          //现在的时间
            writeLastReadTime(currentDate);
            logger.info(currentDate);
            //读取上一次时间到现在这段时间内新增的医嘱
            reader.readNewAddedLongTermOrderByIDs(lastDate,currentDate);
            reader.readNewAddedShortTermOrderByIDs(lastDate,currentDate);

            lastDate = currentDate;                     //更新上一次读取的时间
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                logger.error(new Date() + " 暂停线程错误\n" + e);
            }
        }
    }

    /**
     * 把上一次读取医嘱的时间保存到文件中
     * @param currentDate
     */
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
