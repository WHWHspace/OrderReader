package hemodialysis;

import Model.LongTermOrder;
import db.MysqlHelper;
import hisImpl.OrderImplOf117Hospital;
import hisInterface.OrderInterface;

import java.util.ArrayList;

/**
 * Created by 31344 on 2016/2/24.
 */
public class OrderReader {

    MysqlHelper mysqlHelper;
    OrderInterface hisImpl;

    public OrderReader(OrderInterface inter){
        String url="jdbc:mysql://127.0.0.1:3306/hemodialysis";
        String user = "root";
        String password = "123456";
        mysqlHelper = new MysqlHelper(url,user,password);

        hisImpl = inter;
    }

    /**
     * ��ȡ���г���ҽ������ɾ�����е�ҽ������
     */
    public void ReadAllLongTermOrder(){
        ArrayList<LongTermOrder> orders = hisImpl.getAllLongTermOrder();

        mysqlHelper.getConnection();

        clearLongTermOrder();
        insertLongTermOrder(orders);

        mysqlHelper.closeConnection();
    }

    public void ReadAllShortTermOrder(){

    }

    /**
     * ���볤��ҽ��
     * @param orders
     */
    private void insertLongTermOrder(ArrayList<LongTermOrder> orders) {
        for (int i = 0; i < orders.size(); i++){
            LongTermOrder order = orders.get(i);
            String sql = "INSERT INTO `hemodialysis`.`longterm_ordermgt` (`lgord_patic`, `lgord_dateord`, `lgord_timeord`, `lgord_usr1`, `lgord_drug`," +
                    " `lgord_actst`, `lgord_dtactst`, `lgord_usr2`, `lgord_comment`," +
                    " `lgord_intake`, `lgord_freq`, `lgord_medway`) VALUES " +
                    "('" + order.getLgord_patic() + "', '" + order.getLgord_dateord() + "', '" + order.getLgord_timeord() + "', '" + order.getLgord_usr1() + "', '" + order.getLgord_drug() + "'," +
                    " '" + order.getLgord_actst() + "', '" + order.getLgord_dtactst() + "', '" + order.getLgord_usr2() + "', '" + order.getLgord_comment() + "'," +
                    " '" + order.getLgord_intake() + "', '" + order.getLgord_freq() + "', '" + order.getLgord_medway() + "');";
            mysqlHelper.executeUpdate(sql);
        }
    }

    /**
     * ��ճ���ҽ������
     */
    private void clearLongTermOrder() {
        String sql = "delete from longterm_ordermgt where lgord_id > 0;";
        mysqlHelper.executeUpdate(sql);
    }

}
