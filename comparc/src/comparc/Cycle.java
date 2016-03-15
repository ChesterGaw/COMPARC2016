package comparc;

public class Cycle {
	private int cycleNum;
	private int IFID_index;
	private String IFID_IR;
	private int IFID_NPC;
	private int IDEX_index;
	private String IDEX_A;
	private String IDEX_B;
	private String IDEX_Imm;
	private String IDEX_IR;
	private int EXMEM_index;
	private String EXMEM_ALU;
	private String EXMEM_Cond;
	private String EXMEM_Ir;
	private String EXMEM_B;
	private int MEMWB_index;
	private String MEMWB_LMD;
	private String MEMWB_IR;
	private String MEMWB_ALU;
	private String Rn;
	
	public Cycle(){
		cycleNum = 0;
		IFID_IR = "";
		IFID_NPC = 0;
		IDEX_A = "";
		IDEX_B = "";
		IDEX_Imm = "";
		IDEX_IR = "";
		EXMEM_ALU = "";
		EXMEM_Cond = "";
		EXMEM_Ir = "";
		EXMEM_B = "";
		MEMWB_LMD = "";
		MEMWB_IR = "";
		MEMWB_ALU = "";
		Rn = "";
	}
	
	public int getCycleNum() {
		return cycleNum;
	}
	
	public String getIFID_IR() {
		return IFID_IR;
	}
	
	public int getIFID_NPC() {
		return IFID_NPC;
	}
	
	public String getIDEX_A() {
		return IDEX_A;
	}
	
	public String getIDEX_B() {
		return IDEX_B;
	}
	
	public String getIDEX_Imm() {
		return IDEX_Imm;
	}
	
	public String getIDEX_IR() {
		return IDEX_IR;
	}
	
	public String getEXMEM_ALU() {
		return EXMEM_ALU;
	}
	
	public String getEXMEM_Cond() {
		return EXMEM_Cond;
	}
	
	public String getEXMEM_Ir() {
		return EXMEM_Ir;
	}
	
	public String getEXMEM_B() {
		return EXMEM_B;
	}
	
	public String getMEMWB_LMD() {
		return MEMWB_LMD;
	}
	
	public String getMEMWB_IR() {
		return MEMWB_IR;
	}
	
	public String getMEMWB_ALU() {
		return MEMWB_ALU;
	}
	
	public String getRn() {
		return Rn;
	}
	
	public void setCycleNum(int cycleNum) {
		this.cycleNum = cycleNum;
	}
	
	public void setIFID_IR(String iFID_IR) {
		IFID_IR = iFID_IR;
	}
	
	public void setIFID_NPC(int iFID_NPC) {
		IFID_NPC = iFID_NPC;
	}
	
	public void setIDEX_A(String iDEX_A) {
		IDEX_A = iDEX_A;
	}
	
	public void setIDEX_B(String iDEX_B) {
		IDEX_B = iDEX_B;
	}
	
	public void setIDEX_Imm(String iDEX_Imm) {
		IDEX_Imm = iDEX_Imm;
	}
	
	public void setIDEX_IR(String iDEX_IR) {
		IDEX_IR = iDEX_IR;
	}
	
	public void setEXMEM_ALU(String eXMEM_ALU) {
		EXMEM_ALU = eXMEM_ALU;
	}
	
	public void setEXMEM_Cond(String eXMEM_Cond) {
		EXMEM_Cond = eXMEM_Cond;
	}
	
	public void setEXMEM_Ir(String eXMEM_Ir) {
		EXMEM_Ir = eXMEM_Ir;
	}
	
	public void setEXMEM_B(String eXMEM_B) {
		EXMEM_B = eXMEM_B;
	}
	
	public void setMEMWB_LMD(String mEMWB_LMD) {
		MEMWB_LMD = mEMWB_LMD;
	}
	
	public void setMEMWB_IR(String mEMWB_IR) {
		MEMWB_IR = mEMWB_IR;
	}
	
	public void setMEMWB_ALU(String mEMWB_ALU) {
		MEMWB_ALU = mEMWB_ALU;
	}
	
	public void setRn(String rn) {
		Rn = rn;
	}

	public int getIFID_index() {
		return IFID_index;
	}

	public void setIFID_index(int iFID_index) {
		IFID_index = iFID_index;
	}

	public int getIDEX_index() {
		return IDEX_index;
	}

	public void setIDEX_index(int iDEX_index) {
		IDEX_index = iDEX_index;
	}

	public int getEXMEM_index() {
		return EXMEM_index;
	}

	public void setEXMEM_index(int eXMEM_index) {
		EXMEM_index = eXMEM_index;
	}

	public int getMEMWB_index() {
		return MEMWB_index;
	}

	public void setMEMWB_index(int mEMWB_index) {
		MEMWB_index = mEMWB_index;
	}
}