package hemodialysis;

import db.MysqlHelper;
import hisImpl.OrderImplOf117Hospital;
import hisInterface.OrderInterface;

/**
 * Created by 31344 on 2016/2/24.
 */
public class OrderReader {

    MysqlHelper mysqlHelper;
    OrderInterface hisImpl;

    public OrderReader(){
        String url="jdbc:mysql://127.0.0.1:3306/hemodialysis";
        String user = "root";
        String password = "123456";
        mysqlHelper = new MysqlHelper(url,user,password);

        hisImpl = new OrderImplOf117Hospital();
    }

    public void ReadAllLongTermOrder(){

    }

    public void ReadAllShortTermOrder(){

    }



}
