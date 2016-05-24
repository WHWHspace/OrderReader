package hisInterface;

import java.util.ArrayList;
import java.util.Date;

import model.DrugList;
import model.LongTermOrder;
import model.PatientInfomation;
import model.ShortTermOrder;

/**
 * Created by 31344 on 2016/2/24. his数据库接口
 */
public interface OrderInterface {

	// 获取所有病人某一段时间内新增的长期医嘱
	public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate,
			Date toDate);

	// 根据病人id获取某一段时间内新增的长期医嘱
	public ArrayList<LongTermOrder> getUpdatedLongTermOrder(Date fromDate,
			Date toDate, ArrayList<String> ids);

	// 获取所有病人某一段时间内新增的短期医嘱
	public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate,
			Date toDate);

	// 根据病人id获取某一段时间内新增的短期医嘱
	public ArrayList<ShortTermOrder> getUpdatedShortTermOrder(Date fromDate,
			Date toDate, ArrayList<String> ids);

	// 获取药品名称与药品代码
	public ArrayList<DrugList> getUpdatedDrugList(Date fromDate, Date toDate);

	// 获取病人信息
	public ArrayList<PatientInfomation> getUpdatedPatientInfomation(
			Date fromDate, Date toDate);
}
