package hisImpl;

import db.MysqlHelper;
import hemodialysis.OrderReader;
import launcher.Main;
import model.LongTermOrder;
import model.ShortTermOrder;
import db.OracleHelper;
import hisInterface.OrderInterface;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 * 中国人民解放军第117医院医嘱接口实现
 */
public class OrderImplOf117Hospital implements OrderInterface{
    private Logger logger = Main.logger;

    private static final String LongTermOrderViewName = "LONGTERM_ORDER";
    private static final String ShortTermOrderViewName = "SHORTTERM_ORDER";
    private static final String url = "jdbc:oracle:thin:@132.147.160.7:1521:orcl";
    private static final String user = "lab";
    private static final String password = "lab117";
    private OracleHelper helper;

    MysqlHelper mysqlHelper;

    public OrderImplOf117Hospital(){
        helper = new OracleHelper(url,user,password);
        mysqlHelper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
    }




//    /**
//     * 获取所有病人的长期医嘱
//     */
//    public ArrayList<LongTermOrder> getAllLongTermOrder() {
//        helper.getConnection();
//        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();
//
//        String sql = "SELECT * from \""+ LongTermOrderViewName +"\"";
//        ResultSet rs = helper.executeQuery(sql);
//
//        orders = readLongTermOrderData(rs);
//
//        helper.closeConnection();
//        return orders;
//    }

    /**
     * 解析longtermorder的resultSet
     * @param rs
     * @return
     */
    private ArrayList<LongTermOrder> readLongTermOrderData(ResultSet rs) {
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();
        try {
            while(rs.next()){
                LongTermOrder o = new LongTermOrder();
                if(rs.getString("id_no") == null){
                    continue;
                }
                o.setLgord_patic(rs.getString("id_no"));
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date;
                    if(rs.getString("start_date_time") != null){
                        date = readFormat.parse(rs.getString("start_date_time"));
                    }
                    else {
                        date = new Date();
                    }
                    o.setLgord_dateord(dateFormat.format(date));
                    o.setLgord_timeord(timeFormat.format(date));
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析开始日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setLgord_usr1(rs.getString("lgord_usr1"));
                o.setLgord_drug(rs.getString("lgord_drug"));
                //1，新开。2，执行。3，停止。4，作废。
                if ("1".equals(rs.getString("order_status")) || "2".equals(rs.getString("order_status"))){
                    o.setLgord_actst("启用");
                }
                else{
                    o.setLgord_actst("停用");
                }
                try {
                    if (rs.getString("stop_date_time") != null){
                        o.setLgord_dtactst(dateFormat.format(readFormat.parse(rs.getString("stop_date_time"))));
                    }
                    else {
                        o.setLgord_dtactst(dateFormat.format(new Date()));
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析停止日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setLgord_usr2(rs.getString("nurse"));
                o.setLgord_comment(rs.getString("lgord_comment"));
                o.setLgord_intake(rs.getString("dosage") + rs.getString("dosage_units"));
                o.setLgord_freq(rs.getString("lgord_freq"));
                o.setLgord_medway(rs.getString("lgord_medway"));

                logger.info(o.getLgord_drug());

                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            logger.error(new Date() + " 读取查询到的医嘱结果错误\n" + e);
            e.printStackTrace();
        }
        return orders;
    }


    /**
     * 解析shorttermorder的resultSet
     * @param rs
     * @return
     */
    private ArrayList<ShortTermOrder> readShortTermOrderData(ResultSet rs) {
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();
        try {
            while(rs.next()){
                ShortTermOrder o = new ShortTermOrder();
                if(rs.getString("id_no") == null){
                    continue;
                }
                o.setShord_patic(rs.getString("id_no"));
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date;
                    if(rs.getString("start_date_time") != null){
                        date = readFormat.parse(rs.getString("start_date_time"));
                    }
                    else {
                        date = new Date();
                    }
                    o.setShord_dateord(dateFormat.format(date));
                    o.setShord_timeord(timeFormat.format(date));
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析开始日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setShord_usr1(rs.getString("shord_usr1"));
                o.setShord_drug(rs.getString("shord_drug"));
                //1，新开。2，执行。3，停止。4，作废。
                if ("1".equals(rs.getString("order_status")) || "2".equals(rs.getString("order_status"))){
                    o.setShord_actst("启用");
                }
                else{
                    o.setShord_actst("停用");
                }
                try {
                    if (rs.getString("stop_date_time") != null){
                        o.setShord_dtactst(dateFormat.format(readFormat.parse(rs.getString("stop_date_time"))));
                    }
                    else {
                        o.setShord_dtactst(dateFormat.format(new Date()));
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析停用日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setShord_usr2(rs.getString("nurse"));
                o.setShord_comment(rs.getString("shord_comment"));
                o.setShord_intake(rs.getString("dosage") + rs.getString("dosage_units"));
                o.setShord_freq(rs.getString("shord_freq"));
                o.setShord_medway(rs.getString("shord_medway"));

                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            logger.error(new Date() + " 读取查询数据错误\n" + e);
            e.printStackTrace();
        }
        return orders;
    }


//    public ArrayList<ShortTermOrder> getAllShortTermOrder() {
//        helper.getConnection();
//        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();
//
//        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\"";
//        ResultSet rs = helper.executeQuery(sql);
//
//        orders = readShortTermOrderData(rs);
//
//        helper.closeConnection();
//        return orders;
//    }

//    public ArrayList<ShortTermOrder> getAllShortTermOrder(String id) {
//        return null;
//    }

    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date date) {
        helper.getConnection();
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT LGORD_PATIC,TO_NCHAR(LGORD_DRUG) as LGORD_DRUG,ENTER_DATE_TIME,TO_NCHAR(LGORD_USR1) as LGORD_USR1,DOSAGE,DOSAGE_UNITS,ORDER_STATUS,TO_NCHAR(LGORD_MEDWAY) as LGORD_MEDWAY,TO_NCHAR(LGORD_FREQ) as LGORD_FREQ,TO_NCHAR(LGORD_COMMENT) as LGORD_COMMENT,START_DATE_TIME,STOP_DATE_TIME,TO_NCHAR(NURSE) as NURSE,ID_NO from \""+ LongTermOrderViewName +"\" WHERE \"ENTER_DATE_TIME\" > to_date('"+ dateFormat.format(date) +"', 'yyyy-mm-dd hh24:mi:ss')";

        ResultSet rs = helper.executeQuery(sql);
        orders = readLongTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

    @Override
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date date) {
        helper.getConnection();
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"ENTER_DATE_TIME\" > to_date('"+ dateFormat.format(date) +"', 'yyyy-mm-dd hh24:mi:ss')";

        ResultSet rs = helper.executeQuery(sql);
        orders = readShortTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }
}
