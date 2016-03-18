package comparc;

public class Register {
	private String reg;
	private String regValue;
	private boolean status;
	
	public Register(String reg){
		this.reg = reg;
		this.regValue = "0000000000000000";
		this.status = true;
	}

	public String getReg() {
		return reg;
	}
	
	public String getRegValue() {
		return regValue;
	}
	
	public boolean getStatus(){
		return status;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public void setRegValue(String regValue) {
		this.regValue = regValue;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
}
