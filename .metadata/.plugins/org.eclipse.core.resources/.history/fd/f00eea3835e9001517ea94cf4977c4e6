package comparc;

import java.util.ArrayList;

import gui.MainGUI;

public class Driver {
	public static void main(String[] args){
		ArrayList<Instruction> ins = new ArrayList<Instruction>();
		ArrayList<Register> reg = new ArrayList<Register>();
		
		for(int i = 1; i < 32; i++){
			String iString = "R" +  String.valueOf(i);
			reg.add(new Register(iString));
		}
		new MainGUI(ins, reg);
	}
}
