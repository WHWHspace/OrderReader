package hemodialysis;

import sun.applet.Main;

import java.util.Date;

/**
 * Created by 31344 on 2016/2/26.
 */
public class ReadOrderThread implements Runnable{

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
        while (true){
            System.out.println(date);               //上一次读取数据的时间
            Date currentDate = new Date();          //现在的时间
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
}
