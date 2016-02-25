import hisImpl.OrderImplOf117Hospital;
import hisInterface.OrderInterface;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

    public static void main(String args[]){

        OrderInterface inter = new OrderImplOf117Hospital();
        inter.getAllLongTermOrder();
    }
}
