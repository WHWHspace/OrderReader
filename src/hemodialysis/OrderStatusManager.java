package hemodialysis;

import db.MysqlHelper;
import launcher.Main;

/**
 * Created by 31344 on 2016/3/31.
 */
public class OrderStatusManager {

    /**
     * 检查医嘱的停止时间，如果医嘱过期，修改医嘱的状态
     */
    public void updateOrderStatus(){
        updateLongTermOrderStatus();
        updateShortTermOrderStatus();
    }

    private void updateShortTermOrderStatus() {
        MysqlHelper helper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        helper.getConnection();

        String sql = "update shortterm_ordermgt \n" +
                "set shord_actst = '00002' \n" +
                "where shord_id > 1 and shord_actst = '00001' and shord_dtactst <> '' and shord_dtactst is not null and shord_dtactst < date_sub(now(),interval 1 week)";
        helper.executeUpdate(sql);

        helper.closeConnection();
    }

    private void updateLongTermOrderStatus() {
        MysqlHelper helper = new MysqlHelper(OrderReader.url,OrderReader.user,OrderReader.password);
        helper.getConnection();

        String sql = "update longterm_ordermgt \n" +
                "set lgord_actst = '00002' \n" +
                "where lgord_id > 1 and lgord_actst = '00001' and lgord_dtactst <> '' and lgord_dtactst is not null and lgord_dtactst < curdate()";
        helper.executeUpdate(sql);

        helper.closeConnection();
    }
}
