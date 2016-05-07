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
import util.DialysisDataTool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 * 医嘱接口实现(本地数据库)
 */
public class OrderImpl implements OrderInterface{
    private Logger logger = Main.logger;

    //建立的医嘱视图名称
    private static final String LongTermOrderViewName = "longterm_order";
    private static final String ShortTermOrderViewName = "shortterm_order";
    //his数据库连接参数，oracle,test,123456
    private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    private static final String user = "test";
    private static final String password = "123456";

    private OracleHelper helper;

    public OrderImpl(){
        helper = new OracleHelper(url,user,password);
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
        try {
            while(rs.next()){
                LongTermOrder o = new LongTermOrder();
                //读取his的病人id
                String patientID = rs.getString("lgord_patic");
                //his的病人id记录在血透数据库中pat_info的pif_insid(原来用作医保号，现在用来记录病人his的id号)，建立病人资料的时候输入
                //因为血透中使用身份证号来对应病人的医嘱，所以需要先读取病人的身份证号，保存到医嘱表的lgord_patic
                String ic = DialysisDataTool.getPatientIC(patientID);
                //找不到身份证，废弃
                if(ic == null){
                    continue;
                }
                o.setLgord_patic(ic);

                //一般his数据库记录的时间是yyyy-MM-dd HH:mm:ss的格式
                //但是血透数据库用lgord_dateord记录年月日:yyyy/MM/dd,用lgord_timeord记录时分秒:HH:mm:ss
                //这里对读取的时间进行格式转换
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date;
                    //有开始日期
                    if(rs.getString("start_date_time") != null){
                        date = readFormat.parse(rs.getString("start_date_time"));
                        o.setLgord_dateord(dateFormat.format(date));
                        o.setLgord_timeord(timeFormat.format(date));
                    }
                    //没有开始日期，可以认为这条医嘱是没用的
                    else{
                        continue;
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析开始日期错误\n" + e);
                    e.printStackTrace();
                }

                o.setLgord_usr1(rs.getString("lgord_usr1"));                //医生
                String drugName = rs.getString("lgord_drug");
                o.setLgord_drug(DialysisDataTool.getDrugId(drugName));      //药品id
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
                        //这里只需要把停止时间记录下来就可以，至于判断医嘱是否过期，交给更新医嘱状态的线程去处理
                    }
                    else {
                        o.setLgord_dtactst("");
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析停止日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setLgord_usr2(rs.getString("nurse"));             //护士
                o.setLgord_comment(rs.getString("lgord_comment"));  //备注
                String dosage = ((rs.getString("dosage")==null)?"":rs.getString("dosage"));
                String dosage_units = ((rs.getString("dosage_units")==null)?"":rs.getString("dosage_units"));
                o.setLgord_intake(dosage + dosage_units);           //剂量
                o.setLgord_freq(rs.getString("lgord_freq"));        //频率
                o.setLgord_medway(rs.getString("lgord_medway"));    //用药方式

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
        if(rs == null){
            return orders;
        }
        try {
            while(rs.next()){
                ShortTermOrder o = new ShortTermOrder();
                //读取his的病人id
                String patientID = rs.getString("shord_patic");
                //his的病人id记录在血透数据库中pat_info的pif_insid(原来用作医保号，现在用来记录病人his的id号)，建立病人资料的时候输入
                //因为血透中使用身份证号来对应病人的医嘱，所以需要先读取病人的身份证号，保存到医嘱表的shord_patic
                String ic = DialysisDataTool.getPatientIC(patientID);
                //找不到身份证，废弃
                if(ic == null){
                    continue;
                }
                o.setShord_patic(ic);

                //一般his数据库记录的时间是yyyy-MM-dd HH:mm:ss的格式
                //但是血透数据库用shord_dateord记录年月日:yyyy/MM/dd,用shord_timeord记录时分秒:HH:mm:ss
                //这里对读取的时间进行格式转换
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date;
                    //有开始日期
                    if(rs.getString("start_date_time") != null){
                        date = readFormat.parse(rs.getString("start_date_time"));
                        o.setShord_dateord(dateFormat.format(date));
                        o.setShord_timeord(timeFormat.format(date));
                    }
                    //没有开始日期，可以认为这条医嘱是没用的
                    else{
                        continue;
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析开始日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setShord_usr1(rs.getString("shord_usr1"));        //医生
                String drugName = rs.getString("shord_drug");
                o.setShord_drug(DialysisDataTool.getDrugId(drugName));  //药品代号
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
                        //这里只需要把停止时间记录下来就可以，至于判断医嘱是否过期，交给更新医嘱状态的线程去处理
                    }
                    else {
                        o.setShord_dtactst("");
                    }
                } catch (ParseException e) {
                    logger.error(new Date() + " 解析停用日期错误\n" + e);
                    e.printStackTrace();
                }
                o.setShord_usr2(rs.getString("nurse"));             //护士
                o.setShord_comment(rs.getString("shord_comment"));  //备注
                String dosage = ((rs.getString("dosage")==null)?"":rs.getString("dosage"));
                String dosage_units = ((rs.getString("dosage_units")==null)?"":rs.getString("dosage_units"));
                o.setShord_intake(dosage + dosage_units);           //剂量
                o.setShord_freq(rs.getString("shord_freq"));        //频率
                o.setShord_medway(rs.getString("shord_medway"));    //用药方式

                orders.add(o);
            }

            return orders;
        } catch (SQLException e) {
            logger.error(new Date() + " 读取查询数据错误\n" + e);
            e.printStackTrace();
        }
        return orders;
    }


    /**
     * 获取所有病人某一段时间内新增的长期医嘱
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate,Date toDate) {
        return null;
    }

    /**
     * 获取所有病人某一段时间内新增的短期医嘱
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate,Date toDate) {
        return null;
    }

    /**
     * 根据病人id获取某一段时间内新增的长期医嘱
     * @param fromDate
     * @param toDate
     * @param ids
     * @return
     */
    @Override
    public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate, Date toDate, ArrayList<String> ids) {
        //建立数据库连接
        helper = new OracleHelper(url,user,password);
        helper.getConnection();
        //获取id的sql语句
        ArrayList<LongTermOrder> orders = new ArrayList<LongTermOrder>();
        if((ids == null)||(ids.size() == 0)){
            return orders;
        }
        String idsSql = "";
        idsSql = DialysisDataTool.buildSqlString(ids);
        //拼接sql语句
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ LongTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"enter_date_time\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss') " +
                "and \"lgord_patic\" in " + idsSql;

        ResultSet rs = helper.executeQuery(sql);
        orders = readLongTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }

    /**
     * 根据病人id获取某一段时间内新增的短期医嘱
     * @param fromDate
     * @param toDate
     * @param ids
     * @return
     */
    @Override
    public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate, Date toDate, ArrayList<String> ids) {
        //建立数据库连接
        helper = new OracleHelper(url,user,password);
        helper.getConnection();
        //获取id的sql语句
        ArrayList<ShortTermOrder> orders = new ArrayList<ShortTermOrder>();
        if((ids == null)||(ids.size() == 0)){
            return orders;
        }
        String idsSql = "";
        idsSql = DialysisDataTool.buildSqlString(ids);
        //拼接sql语句
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "SELECT * from \""+ ShortTermOrderViewName +"\" WHERE \"enter_date_time\" > to_date('"+ dateFormat.format(fromDate) +"', 'yyyy-mm-dd hh24:mi:ss') and \"enter_date_time\" <= to_date('"+ dateFormat.format(toDate) +"', 'yyyy-mm-dd hh24:mi:ss')" +
                "and \"shord_patic\" in " + idsSql;
        ResultSet rs = helper.executeQuery(sql);
        orders = readShortTermOrderData(rs);

        helper.closeConnection();
        return orders;
    }
}
