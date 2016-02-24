package hemodialysis;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.*;

/**
 * Created by 31344 on 2016/2/24.
 * ����Ѫ͸���ݿ⣬mysql
 */
public class DBHelper {

    private Connection connection;

    public DBHelper(){

    }

    /**
     * �������ݿ�����
     */
    public void getConnection(){
        try {
            //��̬����mysql����
            Class.forName("com.mysql.jdbc.Driver");

            String url="jdbc:mysql://127.0.0.1:3306/hemodialysis";
            String user = "root";
            String password = "123456";

            //��������
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
            System.out.println("����mysql����ʧ�ܣ�");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("�������ݿ�����ʧ�ܣ�");
            e.printStackTrace();
        }
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
            System.out.println("����ʧ��");
            e.printStackTrace();
        }
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
            System.out.println("����ʧ��");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * �ر�����
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("�ر����ݿ�����ʧ�ܣ�");
            e.printStackTrace();
        }
    }

}
