package hemodialysis;

import launcher.Main;
import model.LongTermOrder;
import model.ShortTermOrder;
import db.MysqlHelper;
import hisInterface.OrderInterface;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 */
public class OrderReader {

    private Logger logger = Main.logger;
    MysqlHelper mysqlHelper;
    OrderInterface hisImpl;
    public static String url="jdbc:mysql://127.0.0.1:3306/hemodialysis?useUnicode=true&characterEncoding=UTF-8";
//    public static String url="jdbc:mysql://127.0.0.1:3306/myhaisv4?useUnicode=true&characterEncoding=UTF-8";
    public static String user = "root";
//    public static String password = "";
    public static String password = "123456";

    public OrderReader(OrderInterface inter){
        mysqlHelper = new MysqlHelper(url,user,password);

        hisImpl = inter;
    }



    /**
     * 读取某一段时间内新增的长期医嘱
     * @param fromDate
     * @param toDate
     */
    public void readNewAddedLongTermOrder(Date fromDate,Date toDate){
        ArrayList<LongTermOrder> orders = hisImpl.getUpdatedLongTermOrder(fromDate, toDate);
        insertLongTermOrder(orders);
    }

    /**
     *读取某一段时间内新增的短期医嘱
     * @param fromDate
     * @param toDate
     */
    public void readNewAddedShortTermOrder(Date fromDate,Date toDate){
        ArrayList<ShortTermOrder> orders = hisImpl.getUpdatedShortTermOrder(fromDate, toDate);
        insertShortTermOrder(orders);
    }

    /**
     * 根据病人id读取某一段时间内新增的长期医嘱
     * @param fromDate
     * @param toDate
     */
    public void readNewAddedLongTermOrderByIDs(Date fromDate,Date toDate){
        ArrayList<String> ids = getPatientIds();

        ArrayList<LongTermOrder> orders = hisImpl.getUpdatedLongTermOrder(fromDate, toDate, ids);
        insertLongTermOrder(orders);
    }

    /**
     * 根据病人id读取某一段时间内新增的短期医嘱
     * @param fromDate
     * @param toDate
     */
    public void readNewAddedShortTermOrderByIDs(Date fromDate,Date toDate){
        ArrayList<String> ids = getPatientIds();

        ArrayList<ShortTermOrder> orders = hisImpl.getUpdatedShortTermOrder(fromDate, toDate, ids);
        insertShortTermOrder(orders);
    }

    /**
     * 读取所有血透病人在his系统中的id
     * @return
     */
    private ArrayList<String> getPatientIds() {
        mysqlHelper.getConnection();

        ArrayList<String> ids = new ArrayList<String>();
        String sql = "SELECT pif_insid FROM pat_info";
        ResultSet rs = mysqlHelper.executeQuery(sql);
        try {
            while(rs.next()){
                ids.add(rs.getString("pif_insid"));
            }
        } catch (SQLException e) {
            logger.error(new Date() + "读取所有病人id失败" + e);
            e.printStackTrace();
        }
        mysqlHelper.closeConnection();
        return  ids;
    }


    /**
     * 插入短期医嘱
     * @param orders
     */
    private void insertShortTermOrder(ArrayList<ShortTermOrder> orders) {
        if(orders == null){
            return;
        }
        if(orders.size() > 0){
            logger.info(new Date() + " 添加" + orders.size() + "条短期医嘱数据");
            mysqlHelper.getConnection();

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
            mysqlHelper.closeConnection();
        }
    }

    /**
     * 插入长期医嘱
     * @param orders
     */
    private void insertLongTermOrder(ArrayList<LongTermOrder> orders) {
        if(orders == null){
            return;
        }
        if(orders.size() > 0){
            logger.info(new Date() + " 添加" + orders.size() + "条长期医嘱数据");
            mysqlHelper.getConnection();

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
            mysqlHelper.closeConnection();
        }


    }

}
