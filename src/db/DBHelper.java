package db;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;

/**
 * Created by 31344 on 2016/5/6.
 */
public class DBHelper {

    Logger logger = Logger.getLogger(this.getClass());
    Connection connection;
    String url;
    String user;
    String password;

    /**
     * �������ݿ�����
     */
    public void getConnection(){

    }


    /**
     * ִ�в�ѯ���
     * @param sql
     */
    public ResultSet executeQuery(String sql){
        Statement s = null;
        ResultSet rs = null;
        try {
            s = connection.createStatement();
            rs = s.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            logger.error(new Date() + " ִ�����ݿ��ѯ����\n" + e);
        }
        return null;
    }


    /**
     * ִ�и��£��������
     * @param sql
     */
    public void executeUpdate(String sql){
        Statement s = null;
        try {
            s = connection.createStatement();
            int result = s.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(new Date() + " ִ�����ݿ���´���\n" + e);
        }
    }


    /**
     * �ر�����
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(new Date() + " �ر����ݿ�����ʧ��\n" + e);
        }
    }
}

