package comparc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
	public static String intToBin(int reg, int bits){
		String bin = Integer.toBinaryString(reg);
		int i = 0;
		int l = bin.length();
		
		for(i = 0; i < bits - l; i++)
			bin = '0' + bin;
		
		return bin;
	}
	
	public static void main(String[] args){
		
		String Op = "";
		String A = "";
		String B = "";
		String Imm = "";
		String temp = "";
		int PC = 0;
		int i;
		ArrayList<Instruction> instructions= new ArrayList<Instruction>();
		
		Scanner sc = new Scanner(System.in);
		try {
			Scanner sa = new Scanner(new File("C:\\Users\\Walchester\\Dropbox\\Acads\\Java\\comparc\\bin\\ins.txt"));
			while(sa.hasNext()){
				switch(sa.next()){
					case "DADDU":	Op = "000000";
									Imm = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5) + "00000101101";
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									break;
									
					case "DMULU":	Op = "000000";
									Imm = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5) + "00010011101";
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									break;
									
					case "DMUHU":	Op = "000000";
									Imm = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5) + "00011011101";
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									break;
									
					case "SLT":		Op = "000000";
									Imm = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5) + "00000101010";
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									break;
									
					case "SELEQZ":	Op = "000000";
									Imm = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5) + "00000110101";
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									break;
									
					case "BEQC":	Op = "001000";
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									break;
									
					case "LD":		Op = "110111";
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									temp = sa.next();
									Imm = intToBin(Character.getNumericValue(temp.charAt(0)), 4) + intToBin(Character.getNumericValue(temp.charAt(1)), 4) + intToBin(Character.getNumericValue(temp.charAt(2)), 4) + intToBin(Character.getNumericValue(temp.charAt(3)), 4);
									A = intToBin(Character.getNumericValue(temp.charAt(6)), 5);
									break;
									
					case "SD":		Op = "111111";
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									temp = sa.next();
									Imm = intToBin(Character.getNumericValue(temp.charAt(0)), 4) + intToBin(Character.getNumericValue(temp.charAt(1)), 4) + intToBin(Character.getNumericValue(temp.charAt(2)), 4) + intToBin(Character.getNumericValue(temp.charAt(3)), 4);
									A = intToBin(Character.getNumericValue(temp.charAt(6)), 5);
									break;
									
					case "DADDUI":	Op = "011001";
									B = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									A = intToBin(Character.getNumericValue(sa.next().charAt(1)), 5);
									temp = sa.next();
									Imm = intToBin(Character.getNumericValue(temp.charAt(1)), 4) + intToBin(Character.getNumericValue(temp.charAt(2)), 4) + intToBin(Character.getNumericValue(temp.charAt(3)), 4) + intToBin(Character.getNumericValue(temp.charAt(4)), 4);
									break;
									
					case "BC": 		Op = "110010";
									break;
								
					default:		System.out.println("Sorry, instruction entered is not valid!");
				}
				instructions.add(new Instruction(PC, Op, A, B, Imm));
				PC += 4;
			}
			for(i = 0; i < instructions.size(); i++){
				System.out.println(instructions.get(i).toString());
				System.out.println(instructions.get(i).toHex());
			}
			
			sa.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
