package hisImpl;

import Model.LongTermOrder;
import Model.ShortTermOrder;
import db.OracleHelper;
import hisInterface.OrderInterface;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 * 中国人民解放军第117医院医嘱接口实现
 */
public class OrderImplOf117Hospital implements OrderInterface{

    OracleHelper helper;

    public OrderImplOf117Hospital(){
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String user="TEST";
        String password="123456";

        helper = new OracleHelper(url,user,password);
    }



    @Override
    public ArrayList<LongTermOrder> getAllLongTermOrder() {
        return null;
    }

    @Override
    public ArrayList<LongTermOrder> getAllLongTermOrder(String id) {
        return null;
    }

    @Override
    public ArrayList<ShortTermOrder> getAllShortTermOrder() {
        return null;
    }

    @Override
    public ArrayList<ShortTermOrder> getAllShortTermOrder(String id) {
        return null;
    }

    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date date) {
        return null;
    }

    @Override
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date date) {
        return null;
    }
}
