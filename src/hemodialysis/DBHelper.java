package hemodialysis;

import java.sql.*;

/**
 * Created by 31344 on 2016/2/24.
 * ����Ѫ͸���ݿ⣬mysql
 */
public class DBHelper {

    public static Connection GetDBConnection(){
        try {
            //��̬����mysql����
            Class.forName("com.mysql.jdbc.Driver");

            String url="jdbc:mysql://127.0.0.1:3306/hemodialysis";
            String user = "root";
            String password = "123456";

            //��������
            Connection connection = DriverManager.getConnection(url,user,password);
            return connection;

//            Statement s = connection.createStatement();
//            String sql = "select * from patient";
//            ResultSet rs = s.executeQuery(sql);
//
//            while(rs.next()){
//                System.out.println(rs.getString("patient_id") +" "+ rs.getString("patient_name") +" "+ rs.getString("patient_age"));
//            }
//            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("����mysql����ʧ�ܣ�");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("�������ݿ�����ʧ�ܣ�");
            e.printStackTrace();
        }
        return null;
    }

}
