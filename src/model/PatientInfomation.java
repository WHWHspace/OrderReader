package model;

public class PatientInfomation {
	private String patient_id; // 病人编号
	private String patient_ic; // 病人身份证号
	private String patient_name; // 病人姓名

	public PatientInfomation() {
	}

	public PatientInfomation(String patient_id, String patient_ic, String patient_name) {
		super();
		this.patient_id = patient_id;
		this.patient_ic = patient_ic;
		this.patient_name = patient_name;
	}

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getPatient_ic() {
		return patient_ic;
	}

	public void setPatient_ic(String patient_ic) {
		this.patient_ic = patient_ic;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	@Override
	public String toString() {
		return "PatientInfomation [patient_id=" + patient_id + ", patient_ic="
				+ patient_ic + ", patient_name=" + patient_name + "]";
	}

}
