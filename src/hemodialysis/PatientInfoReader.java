package hemodialysis;

import hisImpl.OrderImpl;
import hisInterface.OrderInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import launcher.Main;
import model.PatientInfomation;

import org.apache.log4j.Logger;

import db.MysqlHelper;

public class PatientInfoReader {

	private Logger logger = Main.logger;
	private MysqlHelper mysqlHelper;
	private OrderInterface hisImpl;

	public PatientInfoReader() {
		hisImpl = new OrderImpl();
	}

	public void readPatientInfoList(Date fromDate, Date toDate) {
		ArrayList<PatientInfomation> patientinfoList = new ArrayList<PatientInfomation>();
		patientinfoList = hisImpl.getUpdatedPatientInfomation(fromDate, toDate);
		insertPatientinfoList(patientinfoList);
	}

	public void insertPatientinfoList(
			ArrayList<PatientInfomation> patientinfoList) {
		if (patientinfoList != null && patientinfoList.size() > 0) {
			mysqlHelper = new MysqlHelper(OrderReader.url, OrderReader.user,
					OrderReader.password);
			mysqlHelper.getConnection();
			int i = 0;
			for (PatientInfomation p : patientinfoList) {
				String sql = "select * from pat_info where pif_ic='"
						+ p.getPatient_ic() + "'";
				ResultSet rs = mysqlHelper.executeQuery(sql);

				try {
					if (!rs.next()) {
						i++;
						String sql1 = "insert into pat_info(pif_name,pif_ic,pif_mrn,pif_insid) values('"
								+ p.getPatient_name()
								+ "','"
								+ p.getPatient_ic()
								+ "','"
								+ p.getPatient_id()
								+ "','" + p.getPatient_id() + "')";
						mysqlHelper.executeUpdate(sql1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info(new Date() + " 添加" + i + "条病人信息");
			mysqlHelper.closeConnection();
		}
	}
}
