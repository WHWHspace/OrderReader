import hemodialysis.OrderReader;
import hemodialysis.ReadOrderThread;
import hisImpl.OrderImplOf117Hospital;
import hisInterface.OrderInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

    public static void main(String args[]){

        OrderInterface inter = new OrderImplOf117Hospital();
        OrderReader reader = new OrderReader(inter);
//        reader.ReadAllLongTermOrder();
//        try {
//            reader.ReadNewAddedLongTermOrder((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse("2011-1-2 12:12:12"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Thread thread = new Thread(new ReadOrderThread(new Date(),reader,10000));
        thread.start();
    }

}
