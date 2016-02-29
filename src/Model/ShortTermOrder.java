package model;

/**
 * Created by 31344 on 2016/2/24.
 * 短期医嘱
 */
public class ShortTermOrder {
    private int shord_id;           //id
    private String shord_patic;     //病人编号
    private String shord_dateord;   //日期
    private String shord_timeord;   //时间
    private String shord_usr1;      //经治医生
    private String shord_drug;      //药品名称
    private String shord_actst;     //是否启用
    private String shord_dtactst;   //停用时间
    private String shord_usr2;      //记录人
    private String shord_comment;   //备注
    private String shord_intake;    //剂量
    private String shord_freq;      //频率
    private String shord_medway;    //给药方式


    public int getShord_id() {
        return shord_id;
    }

    public void setShord_id(int shord_id) {
        this.shord_id = shord_id;
    }

    public String getShord_patic() {
        return shord_patic;
    }

    public void setShord_patic(String shord_patic) {
        this.shord_patic = shord_patic;
    }

    public String getShord_dateord() {
        return shord_dateord;
    }

    public void setShord_dateord(String shord_dateord) {
        this.shord_dateord = shord_dateord;
    }

    public String getShord_timeord() {
        return shord_timeord;
    }

    public void setShord_timeord(String shord_timeord) {
        this.shord_timeord = shord_timeord;
    }

    public String getShord_usr1() {
        return shord_usr1;
    }

    public void setShord_usr1(String shord_usr1) {
        this.shord_usr1 = shord_usr1;
    }

    public String getShord_drug() {
        return shord_drug;
    }

    public void setShord_drug(String shord_drug) {
        this.shord_drug = shord_drug;
    }

    public String getShord_actst() {
        return shord_actst;
    }

    public void setShord_actst(String shord_actst) {
        this.shord_actst = shord_actst;
    }

    public String getShord_dtactst() {
        return shord_dtactst;
    }

    public void setShord_dtactst(String shord_dtactst) {
        this.shord_dtactst = shord_dtactst;
    }

    public String getShord_usr2() {
        return shord_usr2;
    }

    public void setShord_usr2(String shord_usr2) {
        this.shord_usr2 = shord_usr2;
    }

    public String getShord_comment() {
        return shord_comment;
    }

    public void setShord_comment(String shord_comment) {
        this.shord_comment = shord_comment;
    }

    public String getShord_intake() {
        return shord_intake;
    }

    public void setShord_intake(String shord_intake) {
        this.shord_intake = shord_intake;
    }

    public String getShord_freq() {
        return shord_freq;
    }

    public void setShord_freq(String shord_freq) {
        this.shord_freq = shord_freq;
    }

    public String getShord_medway() {
        return shord_medway;
    }

    public void setShord_medway(String shord_medway) {
        this.shord_medway = shord_medway;
    }
}
