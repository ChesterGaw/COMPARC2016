package comparc;

import java.util.ArrayList;

import gui.MainGUI;

public class Driver {
	public static void main(String[] args){
		ArrayList<Instruction> ins = new ArrayList<Instruction>();
		new MainGUI(ins);
	}
}
