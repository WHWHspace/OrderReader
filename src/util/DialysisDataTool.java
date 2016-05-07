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
     * �����е�idƴ�ӽ�sql���,���Խ���in������
     * ����
     * ids = [1,2,3,4]
     * return ('1','2','3','4')
     * sql��䣺 ��...... where patient_id in �� + ����ֵ
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
     * ����pat_info���pif_insid�ֶλ�ȡpif_ic�����֤�ţ�
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
            logger.error(new Date() + "��ȡ�������֤�Ŵ���" + e);
            e.printStackTrace();
        }
        finally {
            helper.closeConnection();
        }
        return patientIC;
    }


    /**
     * ����ҩƷ���ƻ�ȡҩƷid
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
                logger.error(new Date() + "��ȡҩƷid����\n" + e);
                e.printStackTrace();
            }
            finally {
                helper.closeConnection();
            }
        }
        return id;
    }
}
