import hemodialysis.DBHelper;

import java.sql.Connection;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

    public static void main(String args[]){

        Connection c = DBHelper.GetDBConnection();
        System.out.println(c);


//        //�趨���ݿ����������ݿ����ӵ�ַ���˿ڡ����ƣ��û���������
//        String driverName="oracle.jdbc.driver.OracleDriver";
//        String url="jdbc:oracle:thin:@localhost:1521:orcl";  //testΪ���ݿ����ƣ�1521Ϊ�������ݿ��Ĭ�϶˿�
//        String user="TEST";   //aaΪ�û���
//        String password="123456";  //123Ϊ����
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        //���ݿ����Ӷ���
//        Connection conn = null;
//
//        try {
//            //����Oracle���ݿ�����������
//            Class.forName(driverName);
//
//            //��ȡ���ݿ�����
//            conn = DriverManager.getConnection(url, user, password);
//
//            //������ݿ�����
//            System.out.println(conn);
//
//            //����sql����
//            String sql = "select * from \"aa\"";
//
//            //�����������µ�PreparedStatement����
//            pstmt = conn.prepareStatement(sql);
//
//            //ִ�в�ѯ��䣬�����ݱ��浽ResultSet������
//            rs = pstmt.executeQuery();
//
//            //��ָ���Ƶ���һ�У��ж�rs���Ƿ�������
//            while(rs.next()){
//                //�����ѯ���
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
