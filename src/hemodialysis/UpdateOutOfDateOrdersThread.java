package hemodialysis;

import launcher.Main;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by 31344 on 2016/3/31.
 */
public class UpdateOutOfDateOrdersThread extends Thread{

    public volatile boolean exit = false;
    private static Logger logger = Main.logger;
    OrderStatusManager manager;
    int interval = 10000;

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
                logger.error(new Date() + " ÔÝÍ£Ïß³Ì´íÎó\n" + e);
            }
        }
    }
}
