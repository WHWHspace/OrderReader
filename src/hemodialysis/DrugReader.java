package hemodialysis;

import hisImpl.OrderImpl;
import hisInterface.OrderInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import launcher.Main;
import model.DrugList;

import org.apache.log4j.Logger;

import db.MysqlHelper;

public class DrugReader {

	private Logger logger = Main.logger;
	private MysqlHelper mysqlHelper;
	private OrderInterface hisImpl;

	public DrugReader() {
		hisImpl = new OrderImpl();
	}

	public void readDrugList(Date fromDate, Date toDate) {
		ArrayList<DrugList> drugList = new ArrayList<DrugList>();
		drugList = hisImpl.getUpdatedDrugList(fromDate, toDate);
		
		insertDrugList(drugList);
	}

	public void insertDrugList(ArrayList<DrugList> drugList) {
		if (drugList != null && drugList.size() > 0) {
			mysqlHelper = new MysqlHelper(OrderReader.url, OrderReader.user,
					OrderReader.password);
			mysqlHelper.getConnection();
			int i = 0;
			for (DrugList d : drugList) {
				// String sql = "select * from drug_list";
				String sql = "select * from drug_list where drg_code='"
						+ d.getDrug_id() + "'";
				ResultSet rs = mysqlHelper.executeQuery(sql);
				try {
					if (!rs.next()) {
						i++;
						String sql1 = "insert into drug_list(drg_name,drg_code) values('"
								+ d.getDrug_name()
								+ "','"
								+ d.getDrug_id()
								+ "')";
						// System.out.println("no");
						mysqlHelper.executeUpdate(sql1);

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			logger.info(new Date() + " 添加" + i + "条药品信息");
			mysqlHelper.closeConnection();
		}
	}

}
