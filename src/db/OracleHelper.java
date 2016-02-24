package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by 31344 on 2016/2/24.
 */
public class OracleHelper {

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
            //动态加载mysql驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //建立连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("加载oracle驱动失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("建立数据库连接失败！");
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("关闭oracle数据库连接失败！");
            e.printStackTrace();
        }
    }
}
