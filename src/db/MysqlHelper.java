package db;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.*;

/**
 * Created by 31344 on 2016/2/24.
 * ����Ѫ͸���ݿ⣬mysql
 */
public class MysqlHelper {

    private Connection connection;
    String url;
    String user;
    String password;

    public MysqlHelper(String url,String user,String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * �������ݿ�����
     */
    public void getConnection(){
        try {
            //��̬����mysql����
            Class.forName("com.mysql.jdbc.Driver");

            //��������
            connection = DriverManager.getConnection(url, user, password);
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
            System.out.println("��ѯʧ��");
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
            System.out.println("�ر�mysql���ݿ�����ʧ�ܣ�");
            e.printStackTrace();
        }
    }

}
