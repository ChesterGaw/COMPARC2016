package comparc;

public class Register {
	private String reg;
	private String regValue;
	
	public Register(String reg){
		this.reg = reg;
		this.regValue = "0000 0000 0000 0000";
	}

	public String getReg() {
		return reg;
	}
	
	public String getRegValue() {
		return regValue;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public void setRegValue(String regValue) {
		this.regValue = regValue;
	}
}
