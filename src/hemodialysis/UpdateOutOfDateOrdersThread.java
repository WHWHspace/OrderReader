package hemodialysis;

import launcher.Main;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by 31344 on 2016/3/31.
 * 更新医嘱状态线程
 */
public class UpdateOutOfDateOrdersThread extends Thread{

    public volatile boolean exit = false;
    private static Logger logger = Main.logger;
    private OrderStatusManager manager;
    private int interval = 360000;   //时间间隔

    public UpdateOutOfDateOrdersThread(){
        manager = new OrderStatusManager();
    }

    @Override
    public void run() {
        while (!exit){
            manager.updateOrderStatus();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                logger.error(new Date() + " 暂停线程错误\n" + e);
            }
        }
    }
}
