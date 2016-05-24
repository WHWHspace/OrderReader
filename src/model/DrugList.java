package model;

public class DrugList {
	private String drug_id; // 药品代码
	private String drug_name; // 药品名称

	public DrugList() {
	}

	public DrugList(String drug_id, String drug_name) {
		super();
		this.drug_id = drug_id;
		this.drug_name = drug_name;
	}

	public String getDrug_id() {
		return drug_id;
	}

	public void setDrug_id(String drug_id) {
		this.drug_id = drug_id;
	}

	public String getDrug_name() {
		return drug_name;
	}

	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}

	@Override
	public String toString() {
		return "DrugList [drug_id=" + drug_id + ", drug_name=" + drug_name
				+ "]";
	}

}
