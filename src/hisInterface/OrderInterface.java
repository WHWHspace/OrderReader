package hisInterface;

import model.LongTermOrder;
import model.ShortTermOrder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 * his���ݿ�ӿ�
 */
public interface OrderInterface {

//    //��ȡ���в��˵ĳ���ҽ��
//    public ArrayList<LongTermOrder> getAllLongTermOrder();
//
//    //��ȡָ�����˵ĳ���ҽ��
//    public ArrayList<LongTermOrder> getAllLongTermOrder(String id);
//
//    //��ȡ���в��˵Ķ���ҽ��
//    public ArrayList<ShortTermOrder> getAllShortTermOrder();
//
//    //��ȡָ�����˵Ķ���ҽ��
//    public ArrayList<ShortTermOrder> getAllShortTermOrder(String id);

    //��ȡ���в���ĳһʱ��֮�������ĳ���ҽ��
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date date);

    //��ȡ���в���ĳһʱ��֮�������Ķ���ҽ��
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date date);
}
