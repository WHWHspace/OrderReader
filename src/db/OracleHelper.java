package db;

import launcher.Main;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 */
public class OracleHelper {
    private Logger logger = Main.logger;
    Connection connection;
    String url;
    String user;
    String password;

    public OracleHelper(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void getConnection(){
        try {
            //��̬����mysql����
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //��������
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            logger.error(new Date() + " ����oracle����ʧ��\n" + e.getStackTrace());
        } catch (SQLException e) {
            logger.error(new Date() + " ����oracle����ʧ��\n" + e.getStackTrace());
        }
    }

    /**
     * �ر�����
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(new Date() + " �ر�oracle���ݿ�����ʧ��\n" + e.getStackTrace());
        }
    }

    public ResultSet executeQuery(String sql) {
        Statement s = null;
        ResultSet rs = null;
        try {
            s = connection.createStatement();
            rs = s.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            logger.error(new Date() + " ��ѯoracle���ݿ�ʧ��\n" + e.getStackTrace());
        }
        return  null;
    }
}
