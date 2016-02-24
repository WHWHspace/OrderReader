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
            //��̬����mysql����
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //��������
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("����oracle����ʧ�ܣ�");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("�������ݿ�����ʧ�ܣ�");
            e.printStackTrace();
        }
    }

    /**
     * �ر�����
     */
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("�ر�oracle���ݿ�����ʧ�ܣ�");
            e.printStackTrace();
        }
    }
}
