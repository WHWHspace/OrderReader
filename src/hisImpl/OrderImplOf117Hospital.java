package hisImpl;

import Model.LongTermOrder;
import Model.ShortTermOrder;
import db.OracleHelper;
import hisInterface.OrderInterface;

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

    private static final String LongTermOrderViewName = "longterm_order_117";
    private static final String ShortTermOrderViewName = "shortterm_order_117";
    private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String user = "TEST";
    private static final String password = "123456";
    private OracleHelper helper;

    public OrderImplOf117Hospital(){
        helper = new OracleHelper(url,user,password);
    }




    /**
     * 获取所有病人的长期医嘱
     */
    @Override
    public ArrayList<LongTermOrder> getAllLongTermOrder() {
        helper.getConnection();
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();

        String sql = "SELECT * from \""+ LongTermOrderViewName +"\"";
        ResultSet rs = helper.executeQuery(sql);

        orders = readLongTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

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
                o.setLgord_patic(rs.getString("lgord_patic"));
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date = readFormat.parse(rs.getString("start_date_time"));
                    o.setLgord_dateord(dateFormat.format(date));
                    o.setLgord_timeord(timeFormat.format(date));
                } catch (ParseException e) {
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
                    o.setLgord_dtactst(dateFormat.format(readFormat.parse(rs.getString("stop_date_time"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                o.setLgord_usr2(rs.getString("nurse"));
                o.setLgord_comment(rs.getString("lgord_comment"));
                o.setLgord_intake(rs.getString("dosage") + rs.getString("dosage_unit"));
                o.setLgord_freq(rs.getString("lgord_freq"));
                o.setLgord_medway(rs.getString("lgord_medway"));

                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                o.setShord_patic(rs.getString("shord_patic"));
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date = readFormat.parse(rs.getString("start_date_time"));
                    o.setShord_dateord(dateFormat.format(date));
                    o.setShord_timeord(timeFormat.format(date));
                } catch (ParseException e) {
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
                    o.setShord_dtactst(dateFormat.format(readFormat.parse(rs.getString("stop_date_time"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                o.setShord_usr2(rs.getString("nurse"));
                o.setShord_comment(rs.getString("shord_comment"));
                o.setShord_intake(rs.getString("dosage") + rs.getString("dosage_unit"));
                o.setShord_freq(rs.getString("shord_freq"));
                o.setShord_medway(rs.getString("shord_medway"));

                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<LongTermOrder> getAllLongTermOrder(String id) {
        return null;
    }

    @Override
    public ArrayList<ShortTermOrder> getAllShortTermOrder() {
        helper.getConnection();
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();

        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\"";
        ResultSet rs = helper.executeQuery(sql);

        orders = readShortTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

    @Override
    public ArrayList<ShortTermOrder> getAllShortTermOrder(String id) {
        return null;
    }

    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date date) {
        helper.getConnection();
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ LongTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(date) +"', 'yyyy-mm-dd hh24:mi:ss')";

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
        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(date) +"', 'yyyy-mm-dd hh24:mi:ss')";

        ResultSet rs = helper.executeQuery(sql);
        orders = readShortTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }
}
