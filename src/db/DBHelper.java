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
     * 建立数据库连接
     */
    public void getConnection(){

    }


    /**
     * 执行查询语句
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
            logger.error(new Date() + " 执行数据库查询错误\n" + e);
        }
        return null;
    }


    /**
     * 执行更新，插入语句
     * @param sql
     */
    public int executeUpdate(String sql){
        Statement s = null;
        int result = 0;
        try {
            s = connection.createStatement();
            result = s.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(new Date() + " 执行数据库更新错误\n" + e);
        }
        return result;
    }


    /**
     * 关闭连接
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(new Date() + " 关闭数据库连接失败\n" + e);
        }
    }
}

