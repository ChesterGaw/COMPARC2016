package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import comparc.Instruction;
import comparc.Memory;
import comparc.Register;

import java.awt.BorderLayout;

public class MemoryGUI {
	private JFrame jFrame = new JFrame("Memory");
	private JPanel mainPanel = new JPanel();
	private JPanel editPanel = new JPanel();
	private JPanel gotoPanel = new JPanel();
	private JTable jTable;
	private JLabel lblNewMemoryValue = new JLabel();
	private JLabel lblGotoMemory = new JLabel("Go to memory: ");
	private JTextField txtNewMemoryValue = new JTextField();
	private JTextField txtMemoryLoc = new JTextField();
	private JButton btnGo = new JButton("GO");
    private JButton btnEdit = new JButton("Edit");
    private JButton btnBack = new JButton("Back");
    private int row = 0;
	
	public MemoryGUI(ArrayList<Instruction> ins, ArrayList<Register> reg, ArrayList<Memory> mem){
        mainPanel.setPreferredSize (new Dimension(300, 600));
        mainPanel.setLayout (null);

	    jFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    jFrame.getContentPane().add(mainPanel, BorderLayout.EAST);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 11, 280, 475);
	    mainPanel.add(scrollPane);
	    
	    jTable = new JTable();
	    scrollPane.setViewportView(jTable);
	    jFrame.pack();
	    jFrame.setVisible (true);
	    jFrame.setLocationRelativeTo(null);
	    
    	DefaultTableModel model = new DefaultTableModel(new Object[]{"Memory Location", "Data"}, 0) {
    		   @Override
    		   public boolean isCellEditable(int row, int column) {
    		       return false;
    		   }
    	};
    	
        for(int i = 0; i < mem.size(); i++){
        	model.addRow(new Object[]{mem.get(i).getAddress(), mem.get(i).getData()});
        }
        jTable.setModel(model); 
	    
	    
	    
	    //gotoPanel
	    gotoPanel.setBounds(10, 489, 280, 70);
	    mainPanel.add(gotoPanel);
	    gotoPanel.setLayout(null);
	    
	    lblGotoMemory.setBounds(10, 11, 130, 23);
	    gotoPanel.add(lblGotoMemory);
	    
	    txtMemoryLoc.setBounds(187, 11, 83, 23);
	    gotoPanel.add(txtMemoryLoc);
	    
	    btnGo.setBounds(94, 36, 89, 23);
	    gotoPanel.add(btnGo);
	    
	    gotoPanel.setVisible(true);
	    //end of gotoPanel
	    
	    
	    //editPanel
	    editPanel.setBounds(10, 489, 280, 70);
	    mainPanel.add(editPanel);
	    editPanel.setLayout(null);
	    
	    lblNewMemoryValue.setBounds(10, 11, 260, 23);
	    editPanel.add(lblNewMemoryValue);
	    
	    txtNewMemoryValue.setBounds(10, 36, 55, 23);
	    editPanel.add(txtNewMemoryValue);
	    txtNewMemoryValue.setColumns(10);
	    
	    btnEdit.setBounds(181, 36, 89, 23);
	    editPanel.add(btnEdit);
	    
	    editPanel.setVisible(false);
	    //end of editPanel
	    
	    btnBack.setBounds(104, 570, 89, 23);
	    mainPanel.add(btnBack);
	    
	    jFrame.pack();
	    jFrame.setVisible (true);
	    jFrame.setLocationRelativeTo(null);
	    
	    //ActionListeners
	    jTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent m) {
            	if (m.getClickCount() == 2) {
            		row = jTable.getSelectedRow();
            		lblNewMemoryValue.setText("New memory value for memory for " + mem.get(row).getAddress());
            		gotoPanel.setVisible(false);
            		editPanel.setVisible(true);
            	}
            }
        });
	    
	    btnEdit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	String pattern = "[0-9a-fA-F][0-9a-fA-F]";

            	Pattern p = Pattern.compile(pattern);

            	Matcher mNewMemVal = p.matcher(txtNewMemoryValue.getText());
            	boolean bNewMemVal = false;
            	
            	if(mNewMemVal.find())
            		bNewMemVal = true;
            	
            	if(txtNewMemoryValue.getText().length() != 2)
            		new JOptionPane().showMessageDialog(null, "Please input 2 characters!");
            	else if(bNewMemVal == false){
            		new JOptionPane().showMessageDialog(null, "Please enter Hex Values only!");
            	}
            	else{
            		mem.get(row).setData(txtNewMemoryValue.getText());
            		jFrame.dispose();
            		new MemoryGUI(ins, reg, mem);
            	}
            }
        });
	    
	    btnGo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	String newLoc = txtMemoryLoc.getText();
            	int check = 0;
            	//carvin check hex newLoc
            	if(newLoc.length() != 4)
            		new JOptionPane().showMessageDialog(null, "Please input 4 characters!");
            	else{
            		
            		for(int i = 0; i < jTable.getRowCount(); i++){
            			if(newLoc.toUpperCase().equals(jTable.getValueAt(i, 0).toString()) == true){
            				check = 1;
            				jTable.scrollRectToVisible(jTable.getCellRect(i, 0, true));
            			}
            		}
            		if(check == 0)
            			new JOptionPane().showMessageDialog(null, "Address entered does not exist!");
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
}
