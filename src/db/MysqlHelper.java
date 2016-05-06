package db;

import launcher.Main;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;

/**
 * Created by 31344 on 2016/2/24.
 * 连接mysql数据库
 */
public class MysqlHelper extends DBHelper{

    public MysqlHelper(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * 建立数据库连接
     */
    public void getConnection(){
        while(connection == null){
            try {
                //动态加载mysql驱动
                Class.forName("com.mysql.jdbc.Driver");

                //建立连接
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                logger.error(new Date() + " 加载mysql驱动失败\n" + e);
            } catch (SQLException e) {
                logger.error(new Date() + " 建立mysql连接失败\n" + e);
            }
            //判断是否已经建立连接，如果没有，等待一秒后继续尝试
            if(connection == null){
                logger.error(new Date() + " 建立mysql连接失败,继续连接中...\n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
