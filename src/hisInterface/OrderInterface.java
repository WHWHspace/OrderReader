package hisInterface;

import model.LongTermOrder;
import model.ShortTermOrder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 * his数据库接口
 */
public interface OrderInterface {

//    //获取所有病人的长期医嘱
//    public ArrayList<LongTermOrder> getAllLongTermOrder();
//
//    //获取指定病人的长期医嘱
//    public ArrayList<LongTermOrder> getAllLongTermOrder(String id);
//
//    //获取所有病人的短期医嘱
//    public ArrayList<ShortTermOrder> getAllShortTermOrder();
//
//    //获取指定病人的短期医嘱
//    public ArrayList<ShortTermOrder> getAllShortTermOrder(String id);

    //获取所有病人某一时间之后新增的长期医嘱
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date date);

    //获取所有病人某一时间之后新增的短期医嘱
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date date);
}
