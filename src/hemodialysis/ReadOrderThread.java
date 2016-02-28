package hemodialysis;

import sun.applet.Main;

import java.util.Date;

/**
 * Created by 31344 on 2016/2/26.
 */
public class ReadOrderThread implements Runnable{

    Date date;
    OrderReader reader;
    int interval;

    public ReadOrderThread(Date date,OrderReader reader,int interval){
        this.date = date;
        this.reader = reader;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true){
            System.out.println(date);               //��һ�ζ�ȡ���ݵ�ʱ��
            Date currentDate = new Date();          //���ڵ�ʱ��
            reader.ReadNewAddedLongTermOrder(date); //��ȡ��һ�ε�����֮�������
            reader.ReadNewAddedShortTermOrder(date);
            date = currentDate;                     //������һ�ζ�ȡ��ʱ��
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
