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

    //��ȡ���в���ĳһ��ʱ���������ĳ���ҽ��
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate,Date toDate);

    //���ݲ���id��ȡĳһ��ʱ���������ĳ���ҽ��
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate,Date toDate, ArrayList<String> ids);

    //��ȡ���в���ĳһ��ʱ���������Ķ���ҽ��
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate,Date toDate);

    //���ݲ���id��ȡĳһ��ʱ���������Ķ���ҽ��
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate,Date toDate, ArrayList<String> ids);
}
