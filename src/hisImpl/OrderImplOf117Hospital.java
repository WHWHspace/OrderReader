package hisImpl;

import db.MysqlHelper;
import hemodialysis.OrderReader;
import launcher.Main;
import model.LongTermOrder;
import model.OrderStatus;
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

//    private static final String LongTermOrderViewName = "longterm_order";
//    private static final String ShortTermOrderViewName = "shortterm_order";
//    private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//    private static final String user = "test";
//    private static final String password = "123456";

    private OracleHelper helper;

    public MysqlHelper mysqlHelper;

    public OrderImplOf117Hospital(){
        helper = new OracleHelper(url,user,password);
        mysqlHelper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
    }

    /**
     * 解析longtermorder的resultSet
     * @param rs
     * @return
     */
    private ArrayList<LongTermOrder> readLongTermOrderData(ResultSet rs) {
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();
        if(rs == null){
            return orders;
        }
        mysqlHelper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        mysqlHelper.getConnection();
        try {
            while(rs.next()){
                LongTermOrder o = new LongTermOrder();
                String patientID = rs.getString("lgord_patic");
                String ic = getPatientIC(patientID);
                if(ic == null){
                    continue;
                }
                o.setLgord_patic(ic);
                System.out.println(ic);
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
                String drugName = rs.getString("lgord_drug");
                o.setLgord_drug(getDrugId(drugName));
                //1，新开。2，执行。3，停止。4，作废。
                if ("1".equals(rs.getString("order_status")) || "2".equals(rs.getString("order_status"))){
                    o.setLgord_actst(OrderStatus.USE);
                }
                else{
                    o.setLgord_actst(OrderStatus.NOTUSE);
                }
                try {
                    if (rs.getString("stop_date_time") != null){
                        Date stopDate = readFormat.parse(rs.getString("stop_date_time"));
                        o.setLgord_dtactst(dateFormat.format(stopDate));
                        if((stopDate.after(new Date()))||dateFormat.format(stopDate).equals(dateFormat.format(new Date()))){
                            o.setLgord_actst(OrderStatus.USE);
                        }
                        else{
                            o.setLgord_actst(OrderStatus.NOTUSE);
                        }
                    }
                    else {
                        o.setLgord_dtactst("");
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析停止日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setLgord_usr2(rs.getString("nurse"));
                o.setLgord_comment(rs.getString("lgord_comment"));
                String dosage = ((rs.getString("dosage")==null)?"":rs.getString("dosage"));
                String dosage_units = ((rs.getString("dosage_units")==null)?"":rs.getString("dosage_units"));
                o.setLgord_intake(dosage + dosage_units);
                o.setLgord_freq(rs.getString("lgord_freq"));
                o.setLgord_medway(rs.getString("lgord_medway"));

                System.out.println(o.getLgord_id());
                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            logger.error(new Date() + " 读取查询到的医嘱结果错误\n" + e);
            e.printStackTrace();
        }
        mysqlHelper.closeConnection();
        return orders;
    }

    private String getDrugId(String drugName) {
        String id = "";
        if(drugName == null){
            return id;
        }
        else{
            String sql = "select drg_code from drug_list where drg_name = '" + drugName +"'";
            ResultSet rs = mysqlHelper.executeQuery(sql);
            if(rs == null){
                return id;
            }
            try {
                if(rs.next()){
                    id = rs.getString("drg_code");
                }
            } catch (SQLException e) {
                logger.error(new Date() + "获取药品id错误\n" + e);
                e.printStackTrace();
                return "";
            }
        }
        return id;
    }

    private String getPatientIC(String patientID) {

        String patientIC = null;
        String sql = "SELECT pif_ic FROM pat_info where pif_insid = '" + patientID +"';";
        ResultSet rs = mysqlHelper.executeQuery(sql);
        if(rs == null){
            return patientIC;
        }
        try {
            if(rs.next()){
                patientIC = rs.getString("pif_ic");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patientIC;
    }


    /**
     * 解析shorttermorder的resultSet
     * @param rs
     * @return
     */
    private ArrayList<ShortTermOrder> readShortTermOrderData(ResultSet rs) {
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();
        if(rs == null){
            return orders;
        }
        mysqlHelper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        mysqlHelper.getConnection();
        try {
            while(rs.next()){
                ShortTermOrder o = new ShortTermOrder();
                String patientID = rs.getString("shord_patic");
                String ic = getPatientIC(patientID);
                if(ic == null){
                    continue;
                }
                o.setShord_patic(ic);
                System.out.println(ic);
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
                String drugName = rs.getString("shord_drug");
                o.setShord_drug(getDrugId(drugName));
                //1，新开。2，执行。3，停止。4，作废。
                if ("1".equals(rs.getString("order_status")) || "2".equals(rs.getString("order_status"))){
                    o.setShord_actst(OrderStatus.USE);
                }
                else{
                    o.setShord_actst(OrderStatus.NOTUSE);
                }
                try {
                    if (rs.getString("stop_date_time") != null){
                        Date stopDate = readFormat.parse(rs.getString("stop_date_time"));
                        o.setShord_dtactst(dateFormat.format(stopDate));
                        if((stopDate.after(new Date()))||dateFormat.format(stopDate).equals(dateFormat.format(new Date()))){
                            o.setShord_actst(OrderStatus.USE);
                        }
                        else{
                            o.setShord_actst(OrderStatus.NOTUSE);
                        }
                    }
                    else {
                        o.setShord_dtactst("");
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析停用日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setShord_usr2(rs.getString("nurse"));
                o.setShord_comment(rs.getString("shord_comment"));
                String dosage = ((rs.getString("dosage")==null)?"":rs.getString("dosage"));
                String dosage_units = ((rs.getString("dosage_units")==null)?"":rs.getString("dosage_units"));
                o.setShord_intake(dosage + dosage_units);
                o.setShord_freq(rs.getString("shord_freq"));
                o.setShord_medway(rs.getString("shord_medway"));

                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            logger.error(new Date() + " 读取查询数据错误\n" + e);
            e.printStackTrace();
        }
        mysqlHelper.closeConnection();
        return orders;
    }


    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate,Date toDate) {
        helper = new OracleHelper(url,user,password);
        helper.getConnection();
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ LongTermOrderViewName +"\" WHERE \"ENTER_DATE_TIME\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"ENTER_DATE_TIME\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss')";
//        String sql = "SELECT * from \""+ LongTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"enter_date_time\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss')";
        ResultSet rs = helper.executeQuery(sql);
        orders = readLongTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

    @Override
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate,Date toDate) {
        helper = new OracleHelper(url,user,password);
        helper.getConnection();
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"ENTER_DATE_TIME\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"ENTER_DATE_TIME\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss')";
//        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"enter_date_time\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss')";
        ResultSet rs = helper.executeQuery(sql);
        orders = readShortTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate, Date toDate, ArrayList<String> ids) {
        helper = new OracleHelper(url,user,password);
        helper.getConnection();
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();
        if((ids == null)||(ids.size() == 0)){
            return orders;
        }
        String idsSql = "";
        idsSql = buildSqlString(ids);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ LongTermOrderViewName +"\" WHERE \"ENTER_DATE_TIME\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"ENTER_DATE_TIME\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss') " +
                "and \"LGORD_PATIC\" in " + idsSql;
//        String sql = "SELECT * from \""+ LongTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"enter_date_time\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss') " +
//                "and \"lgord_patic\" in " + idsSql;
        ResultSet rs = helper.executeQuery(sql);
        orders = readLongTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }



    @Override
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate, Date toDate, ArrayList<String> ids) {
        helper = new OracleHelper(url,user,password);
        helper.getConnection();
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();
        if((ids == null)||(ids.size() == 0)){
            return orders;
        }
        String idsSql = "";
        idsSql = buildSqlString(ids);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"ENTER_DATE_TIME\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"ENTER_DATE_TIME\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss') " +
                "and \"SHORD_PATIC\" in " + idsSql;
//        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"enter_date_time\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss')" +
//                "and \"shord_patic\" in " + idsSql;
        ResultSet rs = helper.executeQuery(sql);
        orders = readShortTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

    private String buildSqlString(ArrayList<String> ids) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < ids.size(); i++){
            sb.append("'");
            sb.append(ids.get(i));
            sb.append("'");
            if(i != ids.size() - 1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }


}
