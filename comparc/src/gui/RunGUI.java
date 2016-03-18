package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import comparc.Cycle;
import comparc.Instruction;
import comparc.Memory;
import comparc.Register;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class RunGUI {
	private JFrame jFrame = new JFrame("Run");
	private JPanel mainPanel = new JPanel();
	private JTable mapTable = new JTable();
	private JTable cycleTable = new JTable();
	private JScrollPane mapScroll = new JScrollPane();
	private JScrollPane cycleScroll = new JScrollPane();
	private JLabel lblPipelineMap = new JLabel("Pipeline Map");
	private JLabel lblCycleVariables = new JLabel("Cycle Variables");
	private JLabel lblCycleNumber = new JLabel("Cycle Number");
	private JButton btnNextCycle = new JButton("Next Cycle");
	private JButton btnPrevCycle = new JButton("Previous Cycle");
	private JButton btnFullExe = new JButton("Full Execution");
	private JButton btnBack = new JButton("Back");
    private ArrayList<Cycle> cycles = new ArrayList<Cycle>();
    private int PC;
    private int clockCycle;
    private int cycle;
	
	public RunGUI(ArrayList<Instruction> ins, ArrayList<Register> reg,  ArrayList<Memory> mem ){
		PC = 0;
		
		jFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setPreferredSize (new Dimension(750, 500));
        mainPanel.setLayout (null);
        
        cycleScroll.setBounds(378, 39, 358, 450);
        mainPanel.add(cycleScroll);
        cycleScroll.setViewportView(cycleTable);
		
        mapScroll.setBounds(10, 39, 358, 350);
        mainPanel.add(mapScroll);
        mapScroll.setViewportView(mapTable);
        
        lblPipelineMap.setBounds(160, 11, 79, 17);
        mainPanel.add(lblPipelineMap);
        
        lblCycleVariables.setBounds(519, 14, 90, 14);
        mainPanel.add(lblCycleVariables);
        lblCycleNumber.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblCycleNumber.setBounds(151, 400, 72, 39);
        mainPanel.add(lblCycleNumber);
        
        btnNextCycle.setBounds(248, 400, 120, 39);
        mainPanel.add(btnNextCycle);
        
        btnPrevCycle.setBounds(10, 400, 120, 39);
        mainPanel.add(btnPrevCycle);
        
        btnFullExe.setBounds(140, 450, 228, 39);
        mainPanel.add(btnFullExe);
        
        btnBack.setBounds(10, 450, 120, 39);
        mainPanel.add(btnBack);
        
        jFrame.pack();
        jFrame.setVisible (true);
        jFrame.setLocationRelativeTo(null);
        
        boolean x = true;
        clockCycle = 1;
        int nstall = 0;
        while(x){
        	cycles.add(new Cycle());
        	cycles.get(cycles.size() - 1).setCycleNum(clockCycle);
        	int i = 0;
        	int j = 0;
        	boolean readyA = true;
        	boolean readyB = true;

        
        	//PAT
        	
        	//MEM
        	if(cycles.size() - 1 > 2 && cycles.get(cycles.size() - 2).getEXMEM_index() != -1){
        		ins.get(cycles.get(cycles.size() - 2).getEXMEM_index()).setMEM(clockCycle);
        		cycles.get(cycles.size() - 1).setMEMWB_index(cycles.get(cycles.size() - 2).getEXMEM_index());
            	cycles.get(cycles.size() - 1).setMEMWB_IR(cycles.get(cycles.size() - 2).getEXMEM_IR());
            	cycles.get(cycles.size() - 1).setMEMWB_ALU(cycles.get(cycles.size() - 2).getEXMEM_ALU());
        		String r = "";
        		int add = 0;
            	switch(ins.get(cycles.get(cycles.size() - 2).getEXMEM_index()).getIns()){
	        		case "LD":	r = cycles.get(cycles.size() - 2).getEXMEM_ALU();
	        					r = r.substring(12);
	        					add = hexToDecimal(r);
	        					if(add >= 16384){
	        						add %= 8192;
	        						add += 8192;
	        					}
	        					r = Integer.toHexString(add).toUpperCase();
	        					for(i = 0; i < mem.size(); i++)
	        						if(r.equals(mem.get(i).getAddress()))
	        							break;
	        					r = "";
	        					for(j = 0; j < 8; j++){
	        						if(i == mem.size())
		        						i = 0;
		        					r = mem.get(i).getData() + r;
		        					i++;
	        					}
	        					cycles.get(cycles.size() - 1).setMEMWB_LMD(r);
	        					cycles.get(cycles.size() - 1).setMEMWB_range("-");
	        					break;
	        					
	        		case "SD":	r = cycles.get(cycles.size() - 2).getEXMEM_ALU();
								r = r.substring(12);
								add = hexToDecimal(r);
								if(add >= 16384){
									add %= 8192;
									add += 8192;
								}
								r = Integer.toHexString(add).toUpperCase();
								String data = cycles.get(cycles.size() - 2).getEXMEM_B();
								for(i = 0; i < mem.size(); i++)
									if(r.equals(mem.get(i).getAddress()))
										break;
								for(j = 15; j > 0; j -= 2){
	        						if(i == mem.size())
		        						i = 0;
		        					mem.get(i).setData(data.substring(j - 1, j));
		        					i++;
	        					}
								r += " - " + mem.get(i - 1).getAddress();
								cycles.get(cycles.size() - 1).setMEMWB_range(r);
	        					cycles.get(cycles.size() - 1).setMEMWB_LMD("-");
	        					break;
        			case "DADDU":
	        		case "DMULU":
	        		case "DMUHU":
	        		case "SLT":
	        		case "SELEQZ":
	        		case "DADDUI":
	        		case "BEQC":
	        		case "BC":	cycles.get(cycles.size() - 1).setMEMWB_LMD("-");
				        		cycles.get(cycles.size() - 1).setMEMWB_range("-");
				        		break;
	        	}
        	}
        	
        	//EX
        	if(cycles.size() - 1 > 1 && cycles.get(cycles.size() - 2).getIDEX_index() != -1){
        		int A = 0;
        		int B = 0;
        		String Ans = "";
        		ins.get(cycles.get(cycles.size() - 2).getIDEX_index()).setEX(clockCycle);
        		cycles.get(cycles.size() - 1).setEXMEM_index(cycles.get(cycles.size() - 2).getIDEX_index());
            	cycles.get(cycles.size() - 1).setEXMEM_IR(cycles.get(cycles.size() - 2).getIDEX_IR());
            	cycles.get(cycles.size() - 1).setEXMEM_B(cycles.get(cycles.size() - 2).getIDEX_B());
            	switch(ins.get(cycles.get(cycles.size() - 2).getIDEX_index()).getIns()){
	        		case "DADDU":	A = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_A());
	        						B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_B());
	        						Ans = Integer.toHexString(A + B);
	        						if(Ans.length() > 16){
	        							Ans = Ans.substring(Ans.length() - 16);
	        						}else{
	        							for(i = Ans.length(); i < 16; i++)
	        								Ans = "0" + Ans;
	        						}
	        						cycles.get(cycles.size() - 1).setEXMEM_ALU(Ans);
	        						cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
	        						break;
	        						
	        		case "DMULU":	A = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_A());
									B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_B());
									Ans = Integer.toHexString(A * B);
									if(Ans.length() > 16){
										Ans = Ans.substring(Ans.length() - 16);
									}else{
										for(i = Ans.length(); i < 16; i++)
											Ans = "0" + Ans;
									}
									cycles.get(cycles.size() - 1).setEXMEM_ALU(Ans);
									cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
									break;
									
	        		case "DMUHU":	A = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_A());
									B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_B());
									Ans = Integer.toHexString(A * B);
									if(Ans.length() > 16){
										Ans = Ans.substring(0, Ans.length() - 17);
									}
									for(i = Ans.length(); i < 16; i++)
										Ans = "0" + Ans;									
									cycles.get(cycles.size() - 1).setEXMEM_ALU(Ans);
									cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
									break;
									
	        		case "SLT":	A = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_A());
								B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_B());
								if(A < B)
									cycles.get(cycles.size() - 1).setEXMEM_ALU("0000000000000001");
								else
									cycles.get(cycles.size() - 1).setEXMEM_ALU("0000000000000000");
								cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
								break;
								
	        		case "SELEQZ":	B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_B());
				        			if(B == 0)
				        				cycles.get(cycles.size() - 1).setEXMEM_ALU(cycles.get(cycles.size() - 2).getIDEX_A());
				        			else
				        				cycles.get(cycles.size() - 1).setEXMEM_ALU("0000000000000000");
	        						cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
				        			break;
				        			
	        		case "BEQC":	A = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_A());
									B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_B());
									Ans = ins.get(cycles.get(cycles.size() - 2).getIDEX_index()).getOffset() + ":";
					            	for(i = 0; i < ins.size(); i++)
					            		if(Ans.equalsIgnoreCase(ins.get(i).getLabel()))
					            			break;
					            	Ans = Integer.toHexString(ins.get(i).getPC());
					            	for(i = Ans.length(); i < 16; i++)
					            		Ans = "0" + Ans;
					            	cycles.get(cycles.size() - 1).setEXMEM_ALU(Ans);
									if(A == B){
										cycles.get(cycles.size() - 1).setEXMEM_Cond(true);
									}else
										cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
	        						break;
	        						
	        		case "LD":	
	        		case "SD":	
	        		case "DADDUI":	A = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_A());
									B = hexToDecimal(cycles.get(cycles.size() - 2).getIDEX_Imm());
									Ans = Integer.toHexString(A + B);
									for(i = Ans.length(); i < 16; i++)
					            		Ans = "0" + Ans;
									cycles.get(cycles.size() - 1).setEXMEM_ALU(Ans);
									cycles.get(cycles.size() - 1).setEXMEM_Cond(false);
									break;
								
	        		case "BC":	Ans = ins.get(cycles.get(cycles.size() - 2).getIDEX_index()).getOffset() + ":";
				            	for(i = 0; i < ins.size(); i++)
				            		if(Ans.equalsIgnoreCase(ins.get(i).getLabel()))
				            			break;
				            	Ans = Integer.toHexString(ins.get(i).getPC());
				            	for(i = Ans.length(); i < 16; i++)
				            		Ans = "0" + Ans;
				            	cycles.get(cycles.size() - 1).setEXMEM_ALU(Ans);
				            	cycles.get(cycles.size() - 1).setEXMEM_Cond(true);
	        					break;
	        	}
        	}
        	
        	//ID
        	if(cycles.size() - 1 > 0 && cycles.get(cycles.size() - 2 - nstall).getIFID_index() != -1){
            	String r = Integer.toString(Integer.parseInt(ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).getBinA(), 2));
            	r = "R" + r;
            	for(i = 0; i < reg.size(); i++)
            		if(r.equals("R0") || (r.equals(reg.get(i).getReg()) && reg.get(i).getStatus() == true))
            			break;
            	if(i == reg.size())
            		readyA = false;
            	
            	r = Integer.toString(Integer.parseInt(ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).getBinB(), 2));
            	r = "R" + r;
            	for(j = 0; j < reg.size(); j++)
            		if(r.equals("R0") || (r.equals(reg.get(j).getReg()) && reg.get(j).getStatus() == true))
            			break;
            	if(j == reg.size())
            		readyB = false;
            	
            	switch(ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).getIns()){
					case "DADDU":
					case "DMULU":
					case "DMUHU":
					case "SLT":
					case "SELEQZ":
					case "BEQC":
					case "SD":	if(ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).getIns() != "BEQC" && ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).getIns() != "SD"){
									r = ins.get(cycles.get(cycles.size() - 2).getIFID_index()).getRt();
									for(i = 0; i < reg.size(); i++)
										if(r.equals(reg.get(i).getReg()))
											break;
									reg.get(i).setStatus(false);
									if(r.equals(reg.get(0).getReg()))
										reg.get(i).setStatus(true);
								}
								if(readyA && readyB){
									ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).setID(clockCycle);
				            		cycles.get(cycles.size() - 1).setIDEX_index(cycles.get(cycles.size() - 2 - nstall).getIFID_index());
				                	cycles.get(cycles.size() - 1).setIDEX_IR(cycles.get(cycles.size() - 2 - nstall).getIFID_IR());
				                	cycles.get(cycles.size() - 1).setIDEX_NPC(cycles.get(cycles.size() - 2 - nstall).getIFID_NPC());
				            		cycles.get(cycles.size() - 1).setIDEX_A(reg.get(i).getRegValue());
				                	cycles.get(cycles.size() - 1).setIDEX_B(reg.get(j).getRegValue());
				                	r = ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).toHex();
				                	r = r.substring(4);
				                	if(r.charAt(0) >= '0' && r.charAt(0) <= '7')
				                		for(i = 0; i < 12; i++)
				                			r = "0" + r;
				                	else
				                		r = "F" + r;
				                	cycles.get(cycles.size() - 1).setIDEX_Imm(r);
				                	nstall = 0;
								}else{
									nstall++;
								}break;
								
					case "LD":
					case "DADDUI":	r = ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getRt();
									for(i = 0; i < reg.size(); i++)
										if(r.equals(reg.get(i).getReg()))
											break;
									reg.get(i).setStatus(false);
									if(r.equals(reg.get(0).getReg()))
										reg.get(i).setStatus(true);
									if(readyA){
										ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).setID(clockCycle);
					            		cycles.get(cycles.size() - 1).setIDEX_index(cycles.get(cycles.size() - 2 - nstall).getIFID_index());
					                	cycles.get(cycles.size() - 1).setIDEX_IR(cycles.get(cycles.size() - 2 - nstall).getIFID_IR());
					                	cycles.get(cycles.size() - 1).setIDEX_NPC(cycles.get(cycles.size() - 2 - nstall).getIFID_NPC());
					                	cycles.get(cycles.size() - 1).setIDEX_A(reg.get(i).getRegValue());
					                	cycles.get(cycles.size() - 1).setIDEX_B(reg.get(j).getRegValue());
					                	r = ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).toHex();
					                	r = r.substring(4);
					                	if(r.charAt(0) >= '0' && r.charAt(0) <= '7'){
					                		for(i = 0; i < 12; i++)
					                			r = "0" + r;
					                	}else{
					                		for(i = 0; i < 12; i++)
					                			r = "F" + r;
					                	}	
					                	cycles.get(cycles.size() - 1).setIDEX_Imm(r);
					                	nstall = 0;
									}else{
										nstall++;
									}break;
									
					case "BC":		ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).setID(clockCycle);
				            		cycles.get(cycles.size() - 1).setIDEX_index(cycles.get(cycles.size() - 2 - nstall).getIFID_index());
				                	cycles.get(cycles.size() - 1).setIDEX_IR(cycles.get(cycles.size() - 2 - nstall).getIFID_IR());
				                	cycles.get(cycles.size() - 1).setIDEX_NPC(cycles.get(cycles.size() - 2 - nstall).getIFID_NPC());
				                	cycles.get(cycles.size() - 1).setIDEX_A(reg.get(i).getRegValue());
				                	cycles.get(cycles.size() - 1).setIDEX_B(reg.get(j).getRegValue());
				                	r = ins.get(cycles.get(cycles.size() - 2 - nstall).getIFID_index()).toHex();
				                	r = r.substring(4);
				                	if(r.charAt(0) >= '0' && r.charAt(0) <= '7')
				                		for(i = 0; i < 12; i++)
				                			r = "0" + r;
				                	else
				                		r = "F" + r;
				                	cycles.get(cycles.size() - 1).setIDEX_Imm(r);
				                	nstall = 0;
				                	break;
				}
        	}
        	
        	
        	//IF
        	if(nstall == 0){
        		for(i = 0; i < ins.size(); i++)
            		if(PC == ins.get(i).getPC())
            			break;
        		if(i != ins.size()){
        			ins.get(i).setIF(clockCycle);
                	cycles.get(cycles.size() - 1).setIFID_index(i);
                	cycles.get(cycles.size() - 1).setIFID_IR(ins.get(i).toHex());
                	if(cycles.size() - 1 > 2 && cycles.get(cycles.size() - 2).getEXMEM_Cond() == true && (ins.get(cycles.get(cycles.size() - 2).getEXMEM_index()).getIns() == "BEQC" || ins.get(cycles.get(cycles.size() - 2).getEXMEM_index()).getIns() == "BC")){
                		cycles.get(cycles.size() - 1).setIFID_NPC(cycles.get(cycles.size() - 2).getEXMEM_ALU());
                    	PC = hexToDecimal(cycles.get(cycles.size() - 2).getEXMEM_ALU());
                	}else{
                		PC += 4;
                		String npc = Integer.toHexString(PC);
                		for(i = npc.length(); i < 16; i++)
                			npc = "0" + npc;
                		cycles.get(cycles.size() - 1).setIFID_NPC(npc);
                	}
        		}
            	
        	}
        	
        	//WB
        	if(cycles.size() - 1 > 3 && cycles.get(cycles.size() - 2).getMEMWB_index() != -1){
        		ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).setWB(clockCycle);
        		String r = "";
        		switch(ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getIns()){
	        		case "LD":	r = ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getRt();
	        					for(i = 0; i < reg.size(); i++)
	        						if(r.equals(reg.get(i).getReg()))
	        							break;
	        					reg.get(i).setStatus(true);
	        					reg.get(i).setRegValue(cycles.get(cycles.size() - 2).getMEMWB_LMD());
	        					cycles.get(cycles.size() - 2).setRn(r + " = " + reg.get(i).getRegValue());
	        					break;
	        					
	        		case "DADDU":
	        		case "DMULU":
	        		case "DMUHU":
	        		case "SLT":
	        		case "SELEQZ":	r = ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getRd();
									for(i = 0; i < reg.size(); i++)
										if(r.equals(reg.get(i).getReg()))
											break;
									reg.get(i).setStatus(true);
									reg.get(i).setRegValue(cycles.get(cycles.size() - 2).getMEMWB_ALU());
									cycles.get(cycles.size() - 2).setRn(r + " = " + reg.get(i).getRegValue());
									break;
									
	        		case "DADDUI":	r = ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getRt();
									for(i = 0; i < reg.size(); i++)
										if(r.equals(reg.get(i).getReg()))
											break;
									reg.get(i).setStatus(true);
									reg.get(i).setRegValue(cycles.get(cycles.size() - 2).getMEMWB_ALU());
									cycles.get(cycles.size() - 2).setRn(r + " = " + reg.get(i).getRegValue());
									break;
	        		case "SD":
	        		case "BC":
	        		case "BEQC":	cycles.get(cycles.size() - 2).setRn("-");
	        						break;
        		}
        		if((PC - 4) == ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getPC())
            		x = false;
        	}
        	
        	clockCycle++;
        }
        
        cycle = 1;
        lblCycleNumber.setText(Integer.toString(cycle));
        DefaultTableModel model = new DefaultTableModel(new Object[]{" ", " "}, 0) {
	  		   @Override
	  		   public boolean isCellEditable(int row, int column) {
	  		       return false;
	  		   }
	    };
	    model.addRow(new Object[]{"Cycle Number", cycles.get(cycle - 1).getCycleNum()});
	    model.addRow(new Object[]{"IF", " "});
	    model.addRow(new Object[]{"IF/ID.IR", cycles.get(cycle - 1).getIFID_IR()});
	    model.addRow(new Object[]{"IF/ID.NPC", cycles.get(cycle - 1).getIFID_NPC()});
	    model.addRow(new Object[]{"PC", cycles.get(cycle - 1).getIFID_NPC()});
	    model.addRow(new Object[]{"ID", " "});
	    model.addRow(new Object[]{"ID/EX.A", cycles.get(cycle - 1).getIDEX_A()});
	    model.addRow(new Object[]{"ID/EX.B", cycles.get(cycle - 1).getIDEX_B()});
	    model.addRow(new Object[]{"ID/EX.Imm", cycles.get(cycle - 1).getIDEX_Imm()});
	    model.addRow(new Object[]{"ID/EX.IR", cycles.get(cycle - 1).getIDEX_IR()});
	    model.addRow(new Object[]{"ID/EX.NPC", cycles.get(cycle - 1).getIDEX_NPC()});
	    model.addRow(new Object[]{"EX", " "});
	    model.addRow(new Object[]{"EX/MEM.ALUOutput", cycles.get(cycle - 1).getEXMEM_ALU()});
	    model.addRow(new Object[]{"EX/MEM.COND", cycles.get(cycle - 1).getEXMEM_Cond()});
	    model.addRow(new Object[]{"EX/MEM.IR", cycles.get(cycle - 1).getEXMEM_IR()});
	    model.addRow(new Object[]{"EX/MEM.B", cycles.get(cycle - 1).getEXMEM_B()});
	    model.addRow(new Object[]{"MEM", " "});
	    model.addRow(new Object[]{"MEM/WB.LMD", cycles.get(cycle - 1).getMEMWB_LMD()});
	    model.addRow(new Object[]{"Range of memory locations affected", cycles.get(cycle - 1).getMEMWB_range()});
	    model.addRow(new Object[]{"MEM/WB.IR", cycles.get(cycle - 1).getMEMWB_IR()});
	    model.addRow(new Object[]{"MEM/WB.ALUOutput", cycles.get(cycle - 1).getMEMWB_ALU()});
	    model.addRow(new Object[]{"WB", " "});
	    model.addRow(new Object[]{"Rn", cycles.get(cycle - 1).getRn()});
	    cycleTable.setModel(model);
	    
	    System.out.println(clockCycle);
        
        btnNextCycle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	cycle += 1;
            	if(cycle == cycles.size()+1){
            		cycle -= 1;
            		new JOptionPane().showMessageDialog(null, "Cycle at max!");
            	}
            	else{
            		lblCycleNumber.setText(Integer.toString(cycle));
            		model.setRowCount(0);
	        	    model.addRow(new Object[]{"Cycle Number", cycles.get(cycle - 1).getCycleNum()});
	        	    model.addRow(new Object[]{"IF", " "});
	        	    model.addRow(new Object[]{"IF/ID.IR", cycles.get(cycle - 1).getIFID_IR()});
	        	    model.addRow(new Object[]{"IF/ID.NPC", cycles.get(cycle - 1).getIFID_NPC()});
	        	    model.addRow(new Object[]{"PC", cycles.get(cycle - 1).getIFID_NPC()});
	        	    model.addRow(new Object[]{"ID", " "});
	        	    model.addRow(new Object[]{"ID/EX.A", cycles.get(cycle - 1).getIDEX_A()});
	        	    model.addRow(new Object[]{"ID/EX.B", cycles.get(cycle - 1).getIDEX_B()});
	        	    model.addRow(new Object[]{"ID/EX.Imm", cycles.get(cycle - 1).getIDEX_Imm()});
	        	    model.addRow(new Object[]{"ID/EX.IR", cycles.get(cycle - 1).getIDEX_IR()});
	        	    model.addRow(new Object[]{"ID/EX.NPC", cycles.get(cycle - 1).getIDEX_NPC()});
	        	    model.addRow(new Object[]{"EX", " "});
	        	    model.addRow(new Object[]{"EX/MEM.ALUOutput", cycles.get(cycle - 1).getEXMEM_ALU()});
	        	    model.addRow(new Object[]{"EX/MEM.COND", cycles.get(cycle - 1).getEXMEM_Cond()});
	        	    model.addRow(new Object[]{"EX/MEM.IR", cycles.get(cycle - 1).getEXMEM_IR()});
	        	    model.addRow(new Object[]{"EX/MEM.B", cycles.get(cycle - 1).getEXMEM_B()});
	        	    model.addRow(new Object[]{"MEM", " "});
	        	    model.addRow(new Object[]{"MEM/WB.LMD", cycles.get(cycle - 1).getMEMWB_LMD()});
	        	    model.addRow(new Object[]{"Range of memory locations affected", cycles.get(cycle - 1).getMEMWB_range()});
	        	    model.addRow(new Object[]{"MEM/WB.IR", cycles.get(cycle - 1).getMEMWB_IR()});
	        	    model.addRow(new Object[]{"MEM/WB.ALUOutput", cycles.get(cycle - 1).getMEMWB_ALU()});
	        	    model.addRow(new Object[]{"WB", " "});
	        	    model.addRow(new Object[]{"Rn", cycles.get(cycle - 1).getRn()});
	        	    model.fireTableDataChanged();
	        	    cycleTable.setModel(model);
            	}
            }
        });
        
        btnPrevCycle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	cycle -= 1;
            	if(cycle == 0){
            		cycle += 1;
            		new JOptionPane().showMessageDialog(null, "Cycle at min!");
            	}
            	else{
            		lblCycleNumber.setText(Integer.toString(cycle));
            		model.setRowCount(0);
	        	    model.addRow(new Object[]{"Cycle Number", cycles.get(cycle - 1).getCycleNum()});
	        	    model.addRow(new Object[]{"IF", " "});
	        	    model.addRow(new Object[]{"IF/ID.IR", cycles.get(cycle - 1).getIFID_IR()});
	        	    model.addRow(new Object[]{"IF/ID.NPC", cycles.get(cycle - 1).getIFID_NPC()});
	        	    model.addRow(new Object[]{"PC", cycles.get(cycle - 1).getIFID_NPC()});
	        	    model.addRow(new Object[]{"ID", " "});
	        	    model.addRow(new Object[]{"ID/EX.A", cycles.get(cycle - 1).getIDEX_A()});
	        	    model.addRow(new Object[]{"ID/EX.B", cycles.get(cycle - 1).getIDEX_B()});
	        	    model.addRow(new Object[]{"ID/EX.Imm", cycles.get(cycle - 1).getIDEX_Imm()});
	        	    model.addRow(new Object[]{"ID/EX.IR", cycles.get(cycle - 1).getIDEX_IR()});
	        	    model.addRow(new Object[]{"ID/EX.NPC", cycles.get(cycle - 1).getIDEX_NPC()});
	        	    model.addRow(new Object[]{"EX", " "});
	        	    model.addRow(new Object[]{"EX/MEM.ALUOutput", cycles.get(cycle - 1).getEXMEM_ALU()});
	        	    model.addRow(new Object[]{"EX/MEM.COND", cycles.get(cycle - 1).getEXMEM_Cond()});
	        	    model.addRow(new Object[]{"EX/MEM.IR", cycles.get(cycle - 1).getEXMEM_IR()});
	        	    model.addRow(new Object[]{"EX/MEM.B", cycles.get(cycle - 1).getEXMEM_B()});
	        	    model.addRow(new Object[]{"MEM", " "});
	        	    model.addRow(new Object[]{"MEM/WB.LMD", cycles.get(cycle - 1).getMEMWB_LMD()});
	        	    model.addRow(new Object[]{"Range of memory locations affected", cycles.get(cycle - 1).getMEMWB_range()});
	        	    model.addRow(new Object[]{"MEM/WB.IR", cycles.get(cycle - 1).getMEMWB_IR()});
	        	    model.addRow(new Object[]{"MEM/WB.ALUOutput", cycles.get(cycle - 1).getMEMWB_ALU()});
	        	    model.addRow(new Object[]{"WB", " "});
	        	    model.addRow(new Object[]{"Rn", cycles.get(cycle - 1).getRn()});
	        	    model.fireTableDataChanged();
	        	    cycleTable.setModel(model);
            	}
            }
        });
        
        btnFullExe.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	model.setRowCount(0);
            	for(int cycle = 1; cycle < cycles.size() + 1;cycle++){
            		lblCycleNumber.setText(Integer.toString(cycle));
            		
            	    model.setRowCount(0);
            	    model.addRow(new Object[]{"Cycle Number", cycles.get(cycle - 1).getCycleNum()});
            	    model.addRow(new Object[]{"IF", " "});
            	    model.addRow(new Object[]{"IF/ID.IR", cycles.get(cycle - 1).getIFID_IR()});
            	    model.addRow(new Object[]{"IF/ID.NPC", cycles.get(cycle - 1).getIFID_NPC()});
            	    model.addRow(new Object[]{"PC", cycles.get(cycle - 1).getIFID_NPC()});
            	    model.addRow(new Object[]{"ID", " "});
            	    model.addRow(new Object[]{"ID/EX.A", cycles.get(cycle - 1).getIDEX_A()});
            	    model.addRow(new Object[]{"ID/EX.B", cycles.get(cycle - 1).getIDEX_B()});
            	    model.addRow(new Object[]{"ID/EX.Imm", cycles.get(cycle - 1).getIDEX_Imm()});
            	    model.addRow(new Object[]{"ID/EX.IR", cycles.get(cycle - 1).getIDEX_IR()});
            	    model.addRow(new Object[]{"ID/EX.NPC", cycles.get(cycle - 1).getIDEX_NPC()});
            	    model.addRow(new Object[]{"EX", " "});
            	    model.addRow(new Object[]{"EX/MEM.ALUOutput", cycles.get(cycle - 1).getEXMEM_ALU()});
            	    model.addRow(new Object[]{"EX/MEM.COND", cycles.get(cycle - 1).getEXMEM_Cond()});
            	    model.addRow(new Object[]{"EX/MEM.IR", cycles.get(cycle - 1).getEXMEM_IR()});
            	    model.addRow(new Object[]{"EX/MEM.B", cycles.get(cycle - 1).getEXMEM_B()});
            	    model.addRow(new Object[]{"MEM", " "});
            	    model.addRow(new Object[]{"MEM/WB.LMD", cycles.get(cycle - 1).getMEMWB_LMD()});
            	    model.addRow(new Object[]{"Range of memory locations affected", cycles.get(cycle - 1).getMEMWB_range()});
            	    model.addRow(new Object[]{"MEM/WB.IR", cycles.get(cycle - 1).getMEMWB_IR()});
            	    model.addRow(new Object[]{"MEM/WB.ALUOutput", cycles.get(cycle - 1).getMEMWB_ALU()});
            	    model.addRow(new Object[]{"WB", " "});
            	    model.addRow(new Object[]{"Rn", cycles.get(cycle - 1).getRn()});
            	    model.fireTableDataChanged();
            	    cycleTable.setModel(model);
            	    try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						System.out.println("hi");
						System.out.println(cycles.size());
            	}
            }
        });
        
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	jFrame.dispose();
            	new MainGUI(ins, reg, mem);
            }
        });
        
	}
	
	private static int hexToDecimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
}
