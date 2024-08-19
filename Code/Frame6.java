package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.List;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.event.ItemEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Label;
import java.awt.Font;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Panel;
import java.awt.TextField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("unused")
public class Frame6<T extends Leader> extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTable table;
	DefaultTableModel model;
	private JTextField textField;
	private JTextField textField_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Misc.init();
					Leader leader = Misc.leaders.get(0);
					Frame6<Leader> window = new Frame6<Leader>(leader.teams.get(0));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private static void setColumnWidth(JTable table, int columnIndex, int width) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
    }
	public Frame6(Team team) {
		try {
			initialize(team);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initialize(Team team) throws Exception {
        frame = new JFrame();
        frame.setSize(700, 400);
        frame.getContentPane().setBackground(new Color(39, 154, 216));
        frame.setBounds(100, 100, 650, 470);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Assign Tasks");
        
        Button button_1 = new Button("Back");
        button_1.setBackground(new Color(217, 217, 217));
        button_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        button_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 Frame1 frame1 = new Frame1(team.leader);
                 frame1.frame.setVisible(true);

                 // Close the current frame
                 frame.dispose();
        	}
        });
        button_1.setBounds(30, 21, 84, 35);
        frame.getContentPane().add(button_1);
        
        

        // Initialize the table model with column headers
        // Create the JTable and set its model
		
        JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 118, 376, 194);
		frame.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);

		// TABLE TO DISPLAY USERS WHO ARE MEMBERS
		model = new DefaultTableModel();
		Object[] column = { "ID", "Task Description", "Deadline" };
		model.setColumnIdentifiers(column);
		table.setModel(model);
		model.setRowCount(0);

		// Populate table_2 with all member names
		for (Task<?> unassigned : team.unassigned) {
            Object[] row = {unassigned.ID, unassigned.desc, unassigned.deadline};
            model.addRow(row);
        }

		
		 setColumnWidth(table,0,4);
		 setColumnWidth(table,1,50);
		 setColumnWidth(table,2,20);
	        
	        
        Label label = new Label("UNASSIGNED TASKS");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        label.setBounds(30, 77, 211, 35);
        frame.getContentPane().add(label);
        
  
        JRadioButton rdbtnNewRadioButton = new JRadioButton("Minimum number of tasks");
        rdbtnNewRadioButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        rdbtnNewRadioButton.setForeground(new Color(255, 255, 255));
        rdbtnNewRadioButton.setBackground(new Color(39, 154, 216));
        rdbtnNewRadioButton.setBounds(416, 195, 216, 23);
        frame.getContentPane().add(rdbtnNewRadioButton);
        
        JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Maximum task completion ");
        rdbtnNewRadioButton_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        rdbtnNewRadioButton_1.setForeground(new Color(255, 255, 255));
        rdbtnNewRadioButton_1.setBackground(new Color(39, 154, 216));
        rdbtnNewRadioButton_1.setBounds(416, 221, 216, 23);
        frame.getContentPane().add(rdbtnNewRadioButton_1);
        
        
        Button button = new Button("Assign task");
        button.setBackground(new Color(255, 255, 255));
        button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        button.setBounds(423, 277, 140, 35);
        frame.getContentPane().add(button);
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = table.getSelectedRow();

        	    if (selectedRow != -1) {
        	        // Check if a row is selected
        	    	 int tid = (int) table.getValueAt(selectedRow, 0) ;
        	       
        		if (rdbtnNewRadioButton.isSelected())
        		{
        			Member member = team.findMemberWithLeastTasks();
        			try {
						team.leader.assignTask(Misc.searchTask(team.unassigned,tid), team,member);
						team.unassigned.remove(Misc.searchTask(team.unassigned,tid));
						 model.removeRow(selectedRow);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			System.out.println("Assigned to"+member.getName());
					System.out.println(member.tasks);
        		}
        		else if (rdbtnNewRadioButton_1.isSelected())
        		{
        			Member member = team.findMemberWithMaxCompletionPercentage();
        			try {
						team.leader.assignTask(Misc.searchTask(team.unassigned,tid), team,member);
						team.unassigned.remove(Misc.searchTask(team.unassigned,tid));
						 model.removeRow(selectedRow);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			System.out.println("Assigned to"+member.getName());
					System.out.println(member.tasks);
        		}
        	    }
        	    else
        	    {
        	    	 System.out.println("No row selected");
        	    }
        	}
        });
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    String selectedItem = (String) comboBox.getSelectedItem();
        	    int selectedRow = table.getSelectedRow();

        	    if (selectedRow != -1) {
        	        // Check if a row is selected
        	        int tid = (int) table.getValueAt(selectedRow, 0) ;
        	        System.out.println(selectedItem);
        	        if (selectedItem.equals("Add to my list")) {
        	            try {
        	                team.leader.assignTasktoSelf(Misc.searchTask(team.unassigned, tid),team);
        	                team.unassigned.remove(Misc.searchTask(team.unassigned,tid));
        	                model.removeRow(selectedRow);
        	                System.out.println(team.leader.tasks);
        	            } catch (Exception e1) {
        	                e1.printStackTrace();
        	            }
        	        } else {
        	            try {
							team.leader.assignTask(Misc.searchTask(team.unassigned,tid), team, Misc.searchName(team.members, selectedItem));
							team.unassigned.remove(Misc.searchTask(team.unassigned,tid));
							 model.removeRow(selectedRow);
							System.out.println(Misc.searchName(team.members, selectedItem).tasks);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        	        }
        	    } else {
        	        System.out.println("No row selected");
        	    }
        	}

        });
        comboBox.setBounds(423, 118, 179, 22);
        frame.getContentPane().add(comboBox);
        
        Label label_1 = new Label("Assign to member with");
        label_1.setForeground(new Color(255, 255, 255));
        label_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
        label_1.setBounds(423, 168, 190, 21);
        frame.getContentPane().add(label_1);
        
        JLabel lblTaskDesc = new JLabel("Task Description");
        lblTaskDesc.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblTaskDesc.setForeground(new Color(255, 255, 255));
        lblTaskDesc.setBackground(new Color(255, 255, 255));
        lblTaskDesc.setBounds(30, 347, 121, 23);
        frame.getContentPane().add(lblTaskDesc);
        
        JLabel lblDead = new JLabel("Deadline ");
        lblDead.setForeground(new Color(255, 255, 255));
        lblDead.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblDead.setBounds(30, 369, 84, 35);
        frame.getContentPane().add(lblDead);
        
        textField = new JTextField();
        textField.setBounds(159, 348, 170, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(159, 376, 170, 23);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        btnAdd.setBackground(new Color(255, 255, 255));
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e){
        		//createTask(Team team, String desc, float weightage, String deadline)
        		try {
        			Task<?> task = team.leader.createTask(team,textField.getText(),1,textField_1.getText());
        		    Object[] row = {task.ID, task.desc, task.deadline};
                    model.addRow(row);
        		
        		} catch(Exception ex){ex.printStackTrace();}
        	}
        });
        btnAdd.setBounds(339, 375, 89, 23);
        frame.getContentPane().add(btnAdd);
              
        Label label_2 = new Label("CREATE TASK");
        label_2.setForeground(Color.WHITE);
        label_2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        label_2.setBounds(30, 318, 211, 35);
        frame.getContentPane().add(label_2);
     
       
        comboBox.addItem("Add to my list");
        
        for (Member member : team.members)
        {
        	comboBox.addItem(member.getName());
        }
        

        frame.setVisible(true);
    }
}