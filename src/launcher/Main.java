package launcher;

import db.MysqlHelper;
import hemodialysis.OrderReader;
import hemodialysis.ReadOrderThread;
import hisImpl.OrderImplOf117Hospital;
import hisInterface.OrderInterface;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

    public static Logger logger = Logger.getLogger(Main.class);
    public static int INTERVAL = 10000;

    static Date lastReadTime;
    static OrderInterface inter = new OrderImplOf117Hospital();
    static OrderReader reader = new OrderReader(inter);
    static ReadOrderThread thread;

    public static void main(String args[]){

        readLastReadTime();
        readInterval();

        logger.info(new Date() + " 开启线程任务");
        thread = new ReadOrderThread(lastReadTime,reader,INTERVAL);
        thread.start();
    }

    /**
     * 读取时间间隔
     */
    private static void readInterval() {
        try {
            String path = System.getProperty("user.dir");
            BufferedReader r = new BufferedReader(new FileReader(new File(path + "/config/interval.txt")));
            String s = r.readLine();
            if (s != null){
                INTERVAL = Integer.parseInt(s);
            }
            logger.info(new Date() + " 读取时间间隔完成");
            r.close();
        } catch (FileNotFoundException e) {
            logger.error(new Date() + " 未找到配置文件，请在config目录下添加interval.txt,设置读取时间间隔（以毫秒为单位）\n" + e);
            showErrorMessage(" 未找到配置文件，请在config目录下添加interval.txt,设置读取时间间隔（以毫秒为单位）");
        } catch (IOException e) {
            logger.error(new Date() + " 读取时间间隔配置文件失败！\n" + e);
            showErrorMessage("");
        }
    }

    /**
     * 读取上一次读取数据的时间
     * 如果设置上一次时间为很早以前，就是读取所有的数据，建议第一次运行的时候设置
     */
    private static void readLastReadTime() {
        try {
            String path = System.getProperty("user.dir");
            BufferedReader r = new BufferedReader(new FileReader(new File(path + "/config/lastReadTime.txt")));
            String s = r.readLine();
            if (s != null){
                lastReadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
            }
            else{
                lastReadTime = new Date();
            }
            logger.info(new Date() + " 读取上一次时间完成");
            r.close();
        } catch (FileNotFoundException e) {
            logger.error(new Date() + " 未找到配置文件，请在config目录下添加lastReadTime.txt,设置上一次读取的时间。时间格式 2000-02-23 12:12:12\n"  + e);
            showErrorMessage(" 未找到配置文件，请在config目录下添加lastReadTime.txt,设置上一次读取的时间。时间格式 2000-02-23 12:12:12\n");
        } catch (IOException e) {
            logger.error(new Date() + " 读取上一次时间配置文件失败！\n" + e);
            showErrorMessage("读取时间配置文件错误");
        } catch (ParseException e) {
            logger.error(new Date() + " 日期格式错误，解析错误\n" + e);
            showErrorMessage("配置文件日期格式错误");
        }

    }

    private static void showErrorMessage(String s){
        if((s == null)||("".equals(s))){
            s = "发生错误，请重启该应用";
        }
        JDialog errorDialog = new JDialog();
        errorDialog.setTitle("发生错误");
        errorDialog.setSize(300,100);
        JLabel label = new JLabel(s);
        errorDialog.add(label);
        errorDialog.setVisible(true);
    }

}
