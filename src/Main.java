import hemodialysis.DBHelper;

import java.sql.Connection;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

    public static void main(String args[]){

        Connection c = DBHelper.GetDBConnection();
        System.out.println(c);


//        //设定数据库驱动，数据库连接地址、端口、名称，用户名，密码
//        String driverName="oracle.jdbc.driver.OracleDriver";
//        String url="jdbc:oracle:thin:@localhost:1521:orcl";  //test为数据库名称，1521为连接数据库的默认端口
//        String user="TEST";   //aa为用户名
//        String password="123456";  //123为密码
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        //数据库连接对象
//        Connection conn = null;
//
//        try {
//            //反射Oracle数据库驱动程序类
//            Class.forName(driverName);
//
//            //获取数据库连接
//            conn = DriverManager.getConnection(url, user, password);
//
//            //输出数据库连接
//            System.out.println(conn);
//
//            //定制sql命令
//            String sql = "select * from \"aa\"";
//
//            //创建该连接下的PreparedStatement对象
//            pstmt = conn.prepareStatement(sql);
//
//            //执行查询语句，将数据保存到ResultSet对象中
//            rs = pstmt.executeQuery();
//
//            //将指针移到下一行，判断rs中是否有数据
//            while(rs.next()){
//                //输出查询结果
//                System.out.println(rs.getString("stu_name") + " " + rs.getString("class_name"));
//            }
//
//
//            conn.close();
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }
}
