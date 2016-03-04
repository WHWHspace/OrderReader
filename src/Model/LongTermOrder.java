package model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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

    public int getLgord_id() {
        return lgord_id;
    }

    public void setLgord_id(int lgord_id) {
        this.lgord_id = lgord_id;
    }

    public String getLgord_patic() {
        return lgord_patic;
    }

    public void setLgord_patic(String lgord_patic) {
        this.lgord_patic = changeGBKEncodingToUtf8(lgord_patic);
    }

    public String getLgord_dateord() {
        return lgord_dateord;
    }

    public void setLgord_dateord(String lgord_dateord) {
        this.lgord_dateord = changeGBKEncodingToUtf8(lgord_dateord);
    }

    public String getLgord_timeord() {
        return lgord_timeord;
    }

    public void setLgord_timeord(String lgord_timeord) {
        this.lgord_timeord = changeGBKEncodingToUtf8(lgord_timeord);
    }

    public String getLgord_usr1() {
        return lgord_usr1;
    }

    public void setLgord_usr1(String lgord_usr1) {
        this.lgord_usr1 = changeGBKEncodingToUtf8(lgord_usr1);
    }

    private String changeGBKEncodingToUtf8(String s) {
        return s;
    }

    public String getLgord_drug() {
        return lgord_drug;
    }

    public void setLgord_drug(String lgord_drug) {
        this.lgord_drug = changeGBKEncodingToUtf8(lgord_drug);
    }

    public String getLgord_actst() {
        return lgord_actst;
    }

    public void setLgord_actst(String lgord_actst) {
        this.lgord_actst = changeGBKEncodingToUtf8(lgord_actst);
    }

    public String getLgord_dtactst() {
        return lgord_dtactst;
    }

    public void setLgord_dtactst(String lgord_dtactst) {
        this.lgord_dtactst = changeGBKEncodingToUtf8(lgord_dtactst);
    }

    public String getLgord_usr2() {
        return lgord_usr2;
    }

    public void setLgord_usr2(String lgord_usr2) {
        this.lgord_usr2 = changeGBKEncodingToUtf8(lgord_usr2);
    }

    public String getLgord_comment() {
        return lgord_comment;
    }

    public void setLgord_comment(String lgord_comment) {
        this.lgord_comment = changeGBKEncodingToUtf8(lgord_comment);
    }

    public String getLgord_intake() {
        return lgord_intake;
    }

    public void setLgord_intake(String lgord_intake) {
        this.lgord_intake = changeGBKEncodingToUtf8(lgord_intake);
    }

    public String getLgord_freq() {
        return lgord_freq;
    }

    public void setLgord_freq(String lgord_freq) {
        this.lgord_freq = changeGBKEncodingToUtf8(lgord_freq);
    }

    public String getLgord_medway() {
        return lgord_medway;
    }

    public void setLgord_medway(String lgord_medway) {
        this.lgord_medway = changeGBKEncodingToUtf8(lgord_medway);
    }
}
