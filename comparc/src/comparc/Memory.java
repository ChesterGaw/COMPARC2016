package comparc;

public class Memory {
	private String address;
	private String data;
	
	public Memory(String address){
		this.address = address;
		data = "00";
	}

	public String getAddress() {
		return address;
	}
	
	public String getData() {
		return data;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setData(String data) {
		this.data = data;
	}
}
