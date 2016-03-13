package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import comparc.Instruction;
import java.awt.BorderLayout;

public class MemoryGUI {
	private JFrame jFrame = new JFrame();
	private JPanel mainPanel = new JPanel();
	private JTable table;
	
	public MemoryGUI(ArrayList<Instruction> ins){
        mainPanel.setPreferredSize (new Dimension(264, 600));
        mainPanel.setLayout (null);

	    jFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	    jFrame.getContentPane().add(mainPanel, BorderLayout.EAST);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 11, 244, 467);
	    mainPanel.add(scrollPane);
	    
	    table = new JTable();
	    scrollPane.setViewportView(table);
	    jFrame.pack();
	    jFrame.setVisible (true);
	    jFrame.setLocationRelativeTo(null);
	}
}
