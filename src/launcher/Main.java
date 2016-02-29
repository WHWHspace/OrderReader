package launcher;

import hemodialysis.OrderReader;
import hemodialysis.ReadOrderThread;
import hisImpl.OrderImplOf117Hospital;
import hisInterface.OrderInterface;
import ui.MainWindow;

import java.io.*;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

    public static final int INTERVAL = 10000;

    static Date lastReadTime;
    static OrderInterface inter = new OrderImplOf117Hospital();
    static OrderReader reader = new OrderReader(inter);
    static ReadOrderThread thread;

    public static void main(String args[]){
        readLastReadTime();

        MainWindow w = new MainWindow();
        thread = new ReadOrderThread(lastReadTime,reader,INTERVAL);
        thread.start();
    }

    private static void readLastReadTime() {
        try {
            String path = System.getProperty("user.dir");
            BufferedReader r = new BufferedReader(new FileReader(new File(path + "/timerecord/lastReadTime.txt")));
            String s = r.readLine();
            if (s != null){
                lastReadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
            }
            else{
                lastReadTime = new Date();
            }
            r.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    public static void readAllOrders(){
        thread.exit = true;
//        System.out.println("每隔5分钟读取医嘱线程退出");
        MainWindow.showMessage("暂停监听...");
        //读取医嘱数据
        reader.ReadAllLongTermOrder();
        reader.ReadAllShortTermOrder();

        thread = new ReadOrderThread(new Date(),reader,INTERVAL);
        thread.start();
//        System.out.println("每隔5分钟读取医嘱线程重新开始");
        MainWindow.showMessage("继续监听...");
    }

}
