package launcher;

import hemodialysis.DrugReader;
import hemodialysis.OrderReader;
import hemodialysis.PatientInfoReader;
import hemodialysis.ReadOrderThread;
import hemodialysis.UpdateOutOfDateOrdersThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Created by 31344 on 2016/2/23.
 */
public class Main {

	public static Logger logger = Logger.getLogger(Main.class);
	public static int INTERVAL = 10000; // 默认间隔10秒，实际需要从配置文件读取

	private Date lastReadTime;
	private OrderReader reader;
	private ReadOrderThread thread;
	private UpdateOutOfDateOrdersThread updateThread;
	private DrugReader drugReader;
	private PatientInfoReader patientInfoReader;

	public static void main(String args[]) {
		new Main().launch();
	}

	private void launch() {
		readLastReadTime();
//		String date = "2016-05-01 00:00:00";
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//		try {
//			lastReadTime = dateFormat.parse(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		readInterval();

		patientInfoReader = new PatientInfoReader();
		drugReader = new DrugReader();
		reader = new OrderReader();

		logger.info(new Date() + " 开启读取医嘱线程任务");
		thread = new ReadOrderThread(lastReadTime, reader, drugReader,
				patientInfoReader, INTERVAL);
		thread.start();

		logger.info(new Date() + " 开启更新医嘱状态线程任务");
		updateThread = new UpdateOutOfDateOrdersThread();
		updateThread.start();

	}

	/**
	 * 读取时间间隔
	 */
	private void readInterval() {
		try {
			String path = System.getProperty("user.dir");
			BufferedReader r = new BufferedReader(new FileReader(new File(path
					+ "/config/interval.txt")));
			String s = r.readLine();
			if (s != null) {
				INTERVAL = Integer.parseInt(s);
			}
			logger.info(new Date() + " 读取时间间隔完成");
			r.close();
		} catch (FileNotFoundException e) {
			logger.error(new Date()
					+ " 未找到配置文件，请在config目录下添加interval.txt,设置读取时间间隔（以毫秒为单位）\n"
					+ e);
		} catch (IOException e) {
			logger.error(new Date() + " 读取时间间隔配置文件失败！\n" + e);
		}
	}

	/**
	 * 读取上一次读取数据的时间 如果设置上一次时间为很早以前，就是读取所有的数据，建议第一次运行的时候设置
	 */
	private void readLastReadTime() {
		try {
			String path = System.getProperty("user.dir");
			BufferedReader r = new BufferedReader(new FileReader(new File(path
					+ "/config/lastReadTime.txt")));
			String s = r.readLine();
			if (s != null) {
				lastReadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(s);
			} else {
				lastReadTime = new Date();
			}
			logger.info(new Date() + " 读取上一次时间完成");
			r.close();
		} catch (FileNotFoundException e) {
			logger.error(new Date()
					+ " 未找到配置文件，请在config目录下添加lastReadTime.txt,设置上一次读取的时间。时间格式 2000-02-23 12:12:12\n"
					+ e);
		} catch (IOException e) {
			logger.error(new Date() + " 读取上一次时间配置文件失败！\n" + e);
		} catch (ParseException e) {
			logger.error(new Date() + " 日期格式错误，解析错误\n" + e);
		}

	}
}
