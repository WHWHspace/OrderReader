package Model;

/**
 * Created by 31344 on 2016/2/24.
 * 长期医嘱
 */
public class LongTermOrder {
    private int lgord_id;           //id
    private String lgord_patic;     //病人编号
    private String lgord_dateord;   //日期
    private String lgord_timeord;   //时间
    private String lgord_usr1;      //经治医生
    private String lgord_drug;      //药品名称
    private String lgord_actst;     //是否启用
    private String lgord_dtactst;   //停用时间
    private String lgord_usr2;      //记录人
    private String lgord_comment;   //备注
    private String lgord_intake;    //剂量
    private String lgord_freq;      //频率
    private String lgord_medway;    //给药方式
}
