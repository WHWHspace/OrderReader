package util;

import db.MysqlHelper;
import hemodialysis.OrderReader;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 31344 on 2016/5/7.
 */
public class DialysisDataTool {

    private static Logger logger = Logger.getLogger(DialysisDataTool.class);

    /**
     * 把所有的id拼接进sql语句,可以接在in语句后面
     * 例：
     * ids = [1,2,3,4]
     * return ('1','2','3','4')
     * sql语句： “...... where patient_id in ” + 返回值
     * @param ids
     * @return
     */
    public static String buildSqlString(ArrayList<String> ids) {
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

    /**
     * 根据pat_info表的pif_insid字段获取pif_ic（身份证号）
     * @param patientID
     * @return
     */
    public static String getPatientIC(String patientID) {
        MysqlHelper helper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        helper.getConnection();

        String patientIC = null;
        String sql = "SELECT pif_ic FROM pat_info where pif_insid = '" + patientID +"';";
        ResultSet rs = helper.executeQuery(sql);
        try {
            if((rs != null)&&rs.next()){
                patientIC = rs.getString("pif_ic");
            }
        } catch (SQLException e) {
            logger.error(new Date() + "读取病人身份证号错误" + e);
            e.printStackTrace();
        }
        finally {
            helper.closeConnection();
        }
        return patientIC;
    }


    /**
     * 根据药品名称获取药品id
     * @param drugName
     * @return
     */
    public static String getDrugId(String drugName) {
        String id = null;
        MysqlHelper helper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        helper.getConnection();

        if(drugName != null){
            String sql = "select drg_code from drug_list where drg_name = '" + drugName +"'";
            ResultSet rs = helper.executeQuery(sql);
            try {
                if((rs != null)&&rs.next()){
                    id = rs.getString("drg_code");
                }
            } catch (SQLException e) {
                logger.error(new Date() + "获取药品id错误\n" + e);
                e.printStackTrace();
            }
            finally {
                helper.closeConnection();
            }
        }
        return id;
    }
}
