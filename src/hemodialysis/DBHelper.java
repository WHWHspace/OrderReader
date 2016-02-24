package hemodialysis;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.*;

/**
 * Created by 31344 on 2016/2/24.
 * 连接血透数据库，mysql
 */
public class DBHelper {

    private Connection connection;

    public DBHelper(){

    }

    /**
     * 建立数据库连接
     */
    public void getConnection(){
        try {
            //动态加载mysql驱动
            Class.forName("com.mysql.jdbc.Driver");

            String url="jdbc:mysql://127.0.0.1:3306/hemodialysis";
            String user = "root";
            String password = "123456";

            //建立连接
            connection = DriverManager.getConnection(url,user,password);

//            Statement s = connection.createStatement();
//            String sql = "select * from patient";
//            ResultSet rs = s.executeQuery(sql);
//
//            while(rs.next()){
//                System.out.println(rs.getString("patient_id") +" "+ rs.getString("patient_name") +" "+ rs.getString("patient_age"));
//            }
//            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("加载mysql驱动失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("建立数据库连接失败！");
            e.printStackTrace();
        }
    }

    /**
     * 执行更新，插入语句
     * @param sql
     */
    public void executeUpdate(String sql){
        Statement s = null;
        try {
            s = connection.createStatement();
            int result = s.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("更新失败");
            e.printStackTrace();
        }
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
            System.out.println("更新失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败！");
            e.printStackTrace();
        }
    }

}
