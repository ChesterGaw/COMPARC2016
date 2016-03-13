package comparc;

import java.util.ArrayList;

import gui.MainGUI;

public class Driver {
	public static void main(String[] args){
		ArrayList<Instruction> ins = new ArrayList<Instruction>();
		ArrayList<Register> reg = new ArrayList<Register>();
		ArrayList<Memory> mem = new ArrayList<Memory>();
		
		for(int i = 1; i < 32; i++){
			String iString = "R" +  String.valueOf(i);
			reg.add(new Register(iString));
		}
		
		for(int i = 8192; i < 16384; i++){
			mem.add(new Memory(Integer.toHexString(i).toUpperCase()));
		}
		
		new MainGUI(ins, reg, mem);
	}
}
