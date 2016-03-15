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
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

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
	
	public RunGUI(ArrayList<Instruction> ins, ArrayList<Register> reg,  ArrayList<Memory> mem ){
		PC = 0;
		
		jFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.setPreferredSize (new Dimension(750, 500));
        mainPanel.setLayout (null);
        
        cycleScroll.setBounds(378, 39, 358, 450);
        mainPanel.add(cycleScroll);
        cycleScroll.add(cycleTable);
		
        mapScroll.setBounds(10, 39, 358, 350);
        mainPanel.add(mapScroll);
        mapScroll.add(mapTable);
        
        lblPipelineMap.setBounds(160, 11, 79, 17);
        mainPanel.add(lblPipelineMap);
        
        lblCycleVariables.setBounds(519, 14, 90, 14);
        mainPanel.add(lblCycleVariables);
        lblCycleNumber.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblCycleNumber.setBounds(154, 400, 72, 39);
        mainPanel.add(lblCycleNumber);
        
        btnNextCycle.setBounds(265, 400, 103, 39);
        mainPanel.add(btnNextCycle);
        
        btnPrevCycle.setBounds(10, 400, 103, 39);
        mainPanel.add(btnPrevCycle);
        
        btnFullExe.setBounds(119, 450, 249, 39);
        mainPanel.add(btnFullExe);
        
        btnBack.setBounds(10, 450, 103, 39);
        mainPanel.add(btnBack);
        
        jFrame.pack();
        jFrame.setVisible (true);
        jFrame.setLocationRelativeTo(null);
        
        boolean x = true;
        clockCycle = 1;
        int stall = -1;
        while(x){
        	cycles.add(new Cycle());
        	cycles.get(cycles.size() - 1).setCycleNum(clockCycle);
        	int i = 0;
        	int j = 0;
        	boolean readyA = true;
        	boolean readyB = true;

        
        	//PAT
        	//WB
        	if(cycles.size() - 1 > 3 && cycles.get(cycles.size() - 2).getMEMWB_index() != -1){
        		ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).setWB(clockCycle);
        		String r = "";
        		switch(ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getIns()){
	        		case "DADDU":
	        		case "DMULU":
	        		case "DMUHU":
	        		case "SLT":
	        		case "SELEQZ":break;
	        		case "LD":	r = ins.get(cycles.get(cycles.size() - 2).getMEMWB_index()).getRt();
	        					for(i = 0; i < reg.size(); i++)
	        						if(r.equals(reg.get(i).getReg()))
	        							break;
	        					reg.get(i).setStatus(true);
	        					reg.get(i).setRegValue(cycles.get(cycles.size() - 2).getMEMWB_LMD());
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
        		}
        		
        	}
        	if((PC - 4) == ins.get(cycles.get(cycles.size() - 1).getMEMWB_index()).getPC())
        		x = false;
        	
        	//MEM
        	
        	//EX
        	
        	//ID
        	if(cycles.size() - 1 > 0 && cycles.get(cycles.size() - 2).getIFID_index() != -1){
            	String r = Integer.toString(Integer.parseInt(ins.get(cycles.get(cycles.size() - 2).getIFID_index()).getBinA(), 2));
            	r = "R" + r;
            	for(i = 0; i < reg.size(); i++)
            		if(r.equals("R0") || (r.equals(reg.get(i).getReg()) && reg.get(i).getStatus() == true))
            			break;
            	if(i == reg.size())
            		readyA = false;
            	
            	r = Integer.toString(Integer.parseInt(ins.get(cycles.get(cycles.size() - 2).getIFID_index()).getBinB(), 2));
            	r = "R" + r;
            	for(j = 0; j < reg.size(); j++)
            		if(r.equals("R0") || (r.equals(reg.get(j).getReg()) && reg.get(j).getStatus() == true))
            			break;
            	if(j == reg.size())
            		readyB = false;
            	
            	switch(ins.get(cycles.get(cycles.size() - 2).getIFID_index()).getIns()){
					case "DADDU":
					case "DMULU":
					case "DMUHU":
					case "SLT":
					case "SELEQZ":
					case "BEQC":if(readyA && readyB){
									ins.get(cycles.get(cycles.size() - 2).getIFID_index()).setID(clockCycle);
				            		cycles.get(cycles.size() - 1).setIDEX_index(cycles.get(cycles.size() - 2).getIFID_index());
				                	cycles.get(cycles.size() - 1).setIDEX_IR(cycles.get(cycles.size() - 2).getIFID_IR());
				            		cycles.get(cycles.size() - 1).setIDEX_A(reg.get(i).getRegValue());
				                	cycles.get(cycles.size() - 1).setIDEX_B(reg.get(j).getRegValue());
				                	r = ins.get(cycles.get(cycles.size() - 2).getIFID_index()).toHex();
				                	r = r.substring(4);
				                	if(r.charAt(0) >= '0' && r.charAt(0) <= '7')
				                		for(i = 0; i < 12; i++)
				                			r = "0" + r;
				                	else
				                		r = "F" + r;
				                	cycles.get(cycles.size() - 1).setIDEX_Imm(r);
				                	stall = -1;
								}else{
									stall = clockCycle;
								}break;
								
					case "LD":
					case "SD":
					case "DADDUI":	if(readyA){
										ins.get(cycles.get(cycles.size() - 2).getIFID_index()).setID(clockCycle);
					            		cycles.get(cycles.size() - 1).setIDEX_index(cycles.get(cycles.size() - 2).getIFID_index());
					                	cycles.get(cycles.size() - 1).setIDEX_IR(cycles.get(cycles.size() - 2).getIFID_IR());
					            		cycles.get(cycles.size() - 1).setIDEX_A(reg.get(i).getRegValue());
					                	cycles.get(cycles.size() - 1).setIDEX_B(reg.get(j).getRegValue());
					                	r = ins.get(cycles.get(cycles.size() - 2).getIFID_index()).toHex();
					                	r = r.substring(4);
					                	if(r.charAt(0) >= '0' && r.charAt(0) <= '7')
					                		for(i = 0; i < 12; i++)
					                			r = "0" + r;
					                	else
					                		r = "F" + r;
					                	cycles.get(cycles.size() - 1).setIDEX_Imm(r);
					                	stall = -1;
									}else{
										stall = clockCycle;
									}break;
									
					case "BC":		ins.get(cycles.get(cycles.size() - 2).getIFID_index()).setID(clockCycle);
				            		cycles.get(cycles.size() - 1).setIDEX_index(cycles.get(cycles.size() - 2).getIFID_index());
				                	cycles.get(cycles.size() - 1).setIDEX_IR(cycles.get(cycles.size() - 2).getIFID_IR());
				            		cycles.get(cycles.size() - 1).setIDEX_A(reg.get(i).getRegValue());
				                	cycles.get(cycles.size() - 1).setIDEX_B(reg.get(j).getRegValue());
				                	r = ins.get(cycles.get(cycles.size() - 2).getIFID_index()).toHex();
				                	r = r.substring(4);
				                	if(r.charAt(0) >= '0' && r.charAt(0) <= '7')
				                		for(i = 0; i < 12; i++)
				                			r = "0" + r;
				                	else
				                		r = "F" + r;
				                	cycles.get(cycles.size() - 1).setIDEX_Imm(r);
				                	stall = -1;
				                	break;
				}
        	}
        	
        	
        	//IF
        	if(stall == -1){
        		for(i = 0; i < ins.size();i++)
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
        	
        	
//        	switch(ins.get(i).getIns()){
//        		case "DADDU":break;
//        		case "DMULU":break;
//        		case "DMUHU":break;
//        		case "SLT":break;
//        		case "SELEQZ":break;
//        		case "BEQC":break;
//        		case "LD":break;
//        		case "SD":break;
//        		case "DADDUI":break;
//        		case "BC":break;
//        	}
        	clockCycle++;
        }
        
        btnNextCycle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	
            }
        });
        
        btnPrevCycle.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	
            }
        });
        
        btnFullExe.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	
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
