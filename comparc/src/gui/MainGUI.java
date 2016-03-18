package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.*;

import comparc.Instruction;
import comparc.Memory;
import comparc.Register;

import java.util.ArrayList;

public class MainGUI{
	private JFrame frame = new JFrame("COMPARC");
    private static JPanel jPanel = new JPanel();
	private JButton btnCode = new JButton ("Code");;
    private JButton btnReg = new JButton ("Registers");
    private JButton btnMem = new JButton ("Memory");
    private JButton btnRun = new JButton ("Run");
    private JButton btnExit = new JButton ("Exit");

    public MainGUI(ArrayList<Instruction> ins, ArrayList<Register> reg, ArrayList<Memory> mem) {
    	
        jPanel.setPreferredSize (new Dimension(272, 285));
        jPanel.setLayout (null);

        //add components
        jPanel.add(btnCode);
        jPanel.add(btnReg);
        jPanel.add(btnMem);
        jPanel.add(btnRun);
        jPanel.add(btnExit);

        //set component bounds (only needed by Absolute Positioning)
        btnCode.setBounds (85, 30, 100, 25);
        btnReg.setBounds (85, 70, 100, 25);
        btnMem.setBounds (85, 110, 100, 25);
        btnRun.setBounds (85, 150, 100, 25);
        btnExit.setBounds (85, 190, 100, 25);
        
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(jPanel);
        frame.pack();
        frame.setVisible (true);
        frame.setLocationRelativeTo(null);
        
        btnCode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	frame.dispose();
            	new CodeGUI(ins, reg, mem);
            }
        });
        
        btnReg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	frame.dispose();
            	new RegisterGUI(ins, reg, mem);
            }
        });
        
        btnMem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	frame.dispose();
            	new MemoryGUI(ins, reg, mem);
            }
        });
        
        btnRun.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	if(ins.size()==0){
            		new JOptionPane().showMessageDialog(null, "Please enter instructions first!");
            	
            	}
            	else{
	            	frame.dispose();
	            	new RunGUI(ins, reg, mem);
            	}
            }
        });
        
        btnExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	System.exit(0);
            }
        });
    }


}
