package hemodialysis;

import launcher.Main;
import model.LongTermOrder;
import model.ShortTermOrder;
import db.MysqlHelper;
import hisInterface.OrderInterface;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 */
public class OrderReader {

    private Logger logger = Main.logger;
    MysqlHelper mysqlHelper;
    OrderInterface hisImpl;

    public OrderReader(OrderInterface inter){
        String url="jdbc:mysql://127.0.0.1:3306/hemodialysis";
        String user = "root";
        String password = "123456";
        mysqlHelper = new MysqlHelper(url,user,password);

        hisImpl = inter;
    }

//    /**
//     * 读取所有长期医嘱。会删除已有的医嘱数据
//     */
//    public void ReadAllLongTermOrder(){
//        ArrayList<LongTermOrder> orders = hisImpl.getAllLongTermOrder();
//        if (orders.size() > 0){
//            logger.info(new Date() + " 添加" + orders.size() +"条长期医嘱数据");
//            mysqlHelper.getConnection();
//
//            clearLongTermOrder();
//            insertLongTermOrder(orders);
//
//            mysqlHelper.closeConnection();
//        }
//    }

//    /**
//     * 读取所有短期医嘱。会删除已有的医嘱数据
//     */
//    public void ReadAllShortTermOrder(){
//
//        ArrayList<ShortTermOrder> orders = hisImpl.getAllShortTermOrder();
//
//        if (orders.size() > 0){
//            logger.info(new Date() + " 添加" + orders.size() +"条短期医嘱数据");
//            mysqlHelper.getConnection();
//
//            clearShortTermOrder();
//            insertShortTermOrder(orders);
//
//            mysqlHelper.closeConnection();
//        }
//    }

    /**
     *读取新增的长期医嘱
     */
    public void ReadNewAddedLongTermOrder(Date date){
        ArrayList<LongTermOrder> orders = hisImpl.getUpdatedLongTermOrder(date);
        if(orders.size() > 0){
            logger.info(new Date() + " 添加" + orders.size() +"条长期医嘱数据");
            mysqlHelper.getConnection();

            insertLongTermOrder(orders);

            mysqlHelper.closeConnection();
        }
    }

    /**
     *读取新增的短期医嘱
     */
    public void ReadNewAddedShortTermOrder(Date date){
        ArrayList<ShortTermOrder> orders = hisImpl.getUpdatedShortTermOrder(date);
        if(orders.size() > 0){
            logger.info(new Date() + " 添加" + orders.size() +"条短期医嘱数据");
            mysqlHelper.getConnection();

            insertShortTermOrder(orders);

            mysqlHelper.closeConnection();
        }
    }


    /**
     * 插入短期医嘱
     * @param orders
     */
    private void insertShortTermOrder(ArrayList<ShortTermOrder> orders) {
        for (int i = 0; i < orders.size(); i++){
            ShortTermOrder order = orders.get(i);
            String sql = "INSERT INTO `shortterm_ordermgt` (`shord_patic`, `shord_dateord`, `shord_timeord`, `shord_usr1`, `shord_drug`," +
                    " `shord_actst`, `shord_dtactst`, `shord_usr2`, `shord_comment`," +
                    " `shord_intake`, `shord_freq`, `shord_medway`) VALUES " +
                    "('" + order.getShord_patic() + "', '" + order.getShord_dateord() + "', '" + order.getShord_timeord() + "', '" + order.getShord_usr1() + "', '" + order.getShord_drug() + "'," +
                    " '" + order.getShord_actst() + "', '" + order.getShord_dtactst() + "', '" + order.getShord_usr2() + "', '" + order.getShord_comment() + "'," +
                    " '" + order.getShord_intake() + "', '" + order.getShord_freq() + "', '" + order.getShord_medway() + "');";
            mysqlHelper.executeUpdate(sql);
        }
    }

    /**
     * 插入长期医嘱
     * @param orders
     */
    private void insertLongTermOrder(ArrayList<LongTermOrder> orders) {
        for (int i = 0; i < orders.size(); i++){
            LongTermOrder order = orders.get(i);
            String sql = "INSERT INTO `longterm_ordermgt` (`lgord_patic`, `lgord_dateord`, `lgord_timeord`, `lgord_usr1`, `lgord_drug`," +
                    " `lgord_actst`, `lgord_dtactst`, `lgord_usr2`, `lgord_comment`," +
                    " `lgord_intake`, `lgord_freq`, `lgord_medway`) VALUES " +
                    "('" + order.getLgord_patic() + "', '" + order.getLgord_dateord() + "', '" + order.getLgord_timeord() + "', '" + order.getLgord_usr1() + "', '" + order.getLgord_drug() + "'," +
                    " '" + order.getLgord_actst() + "', '" + order.getLgord_dtactst() + "', '" + order.getLgord_usr2() + "', '" + order.getLgord_comment() + "'," +
                    " '" + order.getLgord_intake() + "', '" + order.getLgord_freq() + "', '" + order.getLgord_medway() + "');";
            mysqlHelper.executeUpdate(sql);
        }
    }

//    /**
//     * 清空长期医嘱数据
//     */
//    private void clearLongTermOrder() {
//        String sql = "delete from longterm_ordermgt where lgord_id > 0;";
//        mysqlHelper.executeUpdate(sql);
//    }
//
//    /**
//     * 清空短期医嘱数据
//     */
//    private void clearShortTermOrder() {
//        String sql = "delete from shortterm_ordermgt where shord_id > 0;";
//        mysqlHelper.executeUpdate(sql);
//    }
}
