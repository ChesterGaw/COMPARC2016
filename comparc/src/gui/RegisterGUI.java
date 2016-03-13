package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import comparc.Instruction;
import comparc.Register;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RegisterGUI {
	private JFrame jFrame = new JFrame("Registers");
	private JPanel mainPanel = new JPanel();
	private JPanel editPanel = new JPanel();
	private JTable jTable;
	private JLabel lblNewRegisterValue = new JLabel("New register value for register R");
    private JTextField newRValue1 = new JTextField();
    private JTextField newRValue2 = new JTextField();
    private JTextField newRValue3 = new JTextField();
    private JTextField newRValue4 = new JTextField();
    private JButton btnEdit = new JButton("Edit");
    private JButton btnBack = new JButton("Back");
	private int row = 0;
    
	public RegisterGUI(ArrayList<Instruction> ins, ArrayList<Register> reg){
        mainPanel.setPreferredSize (new Dimension(400, 610));
        mainPanel.setLayout(null);

	    jFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    jFrame.getContentPane().add(mainPanel, BorderLayout.EAST);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 11, 380, 475);
	    mainPanel.add(scrollPane);
	    
	    jTable = new JTable();
	    scrollPane.setViewportView(jTable);
	    jFrame.pack();
	    jFrame.setVisible (true);
	    jFrame.setLocationRelativeTo(null);
	    
    	DefaultTableModel model = new DefaultTableModel(new Object[]{"Register", "Value"}, 0) {
    		   @Override
    		   public boolean isCellEditable(int row, int column) {
    		       return false;
    		   }
    	};
    	
    	model.addRow(new Object[]{"Register", "Value"});
        for(int i = 0; i < reg.size(); i++){
        	model.addRow(new Object[]{reg.get(i).getReg(), reg.get(i).getRegValue()});
        }
        jTable.setModel(model); 
        
        editPanel.setBounds(10, 497, 380, 66);
        mainPanel.add(editPanel);
        editPanel.setLayout(null);
        
        lblNewRegisterValue.setBounds(10, 11, 157, 14);
        editPanel.add(lblNewRegisterValue);
        
        newRValue1.setBounds(10, 35, 40, 20);
        editPanel.add(newRValue1);
        newRValue1.setColumns(10);
        
        newRValue2.setColumns(10);
        newRValue2.setBounds(60, 35, 40, 20);
        editPanel.add(newRValue2);
        
        newRValue3.setColumns(10);
        newRValue3.setBounds(110, 35, 40, 20);
        editPanel.add(newRValue3);
       
        newRValue4.setColumns(10);
        newRValue4.setBounds(160, 35, 40, 20);
        editPanel.add(newRValue4);
        
        btnEdit.setBounds(281, 34, 89, 23);
        editPanel.add(btnEdit);
        
        btnBack.setBounds(153, 574, 89, 23);
        mainPanel.add(btnBack);
        
        editPanel.setVisible(false);
        
        
        jTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent m) {
            	if (m.getClickCount() == 2) {
            		row = jTable.getSelectedRow();
            		
            		String[] splited = jTable.getValueAt(row, 1).toString().split("\\s+");
            		
            		newRValue1.setText(splited[0]);
            		newRValue2.setText(splited[1]);
            		newRValue3.setText(splited[2]);
            		newRValue4.setText(splited[3]);
            		
            		editPanel.setVisible(true);
            	}
            }
        });
        
        btnEdit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	String regValue = newRValue1.getText() + " " + newRValue2.getText() + " " + newRValue3.getText() + " " + newRValue4.getText();
            	reg.get(row-1).setRegValue(regValue);
            	jFrame.dispose();
            	new RegisterGUI(ins, reg);
            }
        });
        
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	jFrame.dispose();
            	new MainGUI(ins, reg);
            }
        });

	}
}