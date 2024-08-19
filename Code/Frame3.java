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
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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

@SuppressWarnings("unused")
public class Frame3<T extends Leader> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTable table;
	DefaultTableModel model;
	private JTable table_2;
	private boolean teamCreated = false;
	Team newteam = null;

	// Helper method to check if a row index is present in the selected rows array
	private static boolean isRowSelected(int[] selectedRows, int rowIndex) {

		for (int selectedRow : selectedRows) {
			if (selectedRow == rowIndex) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Frame3 window = new Frame3();
	 * window.frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); }
	 * } }); }
	 */
	/**
	 * Create the application.
	 * 
	 */
	private static boolean hasDuplicates(ArrayList<Member> list) {
		Set<Member> set = new HashSet<Member>(list);
		return set.size() < list.size();
	}

	public Frame3(T leader) {
		initialize(leader);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(T leader) {
		HashSet<Object> selectedMembers = new HashSet<>();

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.setBounds(100, 100, 620, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Create Team");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(309, 144, 265, 199);
		frame.getContentPane().add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(34, 144, 265, 199);
		frame.getContentPane().add(scrollPane_1);

		table_2 = new JTable();
		scrollPane_1.setViewportView(table_2);

		// TABLE TO DISPLAY USERS WHO ARE MEMBERS
		model = new DefaultTableModel();
		Object[] column1 = { "Member ID", "Member" };
		model.setColumnIdentifiers(column1);
		table_2.setModel(model);
		model.setRowCount(0);

		// Populate table_2 with all member names
		for (Member member : Misc.members) {
			if (member.getID() != leader.getID() && !selectedMembers.contains(member.getID())) {
				Object[] row1 = { member.getID(), member.getName() };
				model.addRow(row1);
			}
		}

		table = new JTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = { "Member ID", "Member" };
		Object[] row = new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);

		Label label = new Label("Select Members");
		label.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(42, 77, 230, 55);
		frame.getContentPane().add(label);

		Label label_1 = new Label("TEAM");
		label_1.setAlignment(Label.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		label_1.setBounds(294, 44, 230, 55);
		frame.getContentPane().add(label_1);

		
		JButton addButton_1_1 = new JButton("Next");
		addButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if (newteam==null)
                {
                	JOptionPane.showMessageDialog(frame, "No Members have been added", "Error", JOptionPane.ERROR_MESSAGE);
					return; 
                }
                System.out.println("Created team");
                System.out.println(newteam.getTeamMembersString());
				Frame4<T> frame4 = new Frame4<T>(leader, newteam);

				// Close the current frame
				frame.setVisible(false);
				frame.dispose();
                
			}
		});
		addButton_1_1.setBackground(Color.WHITE);
		addButton_1_1.setBounds(470, 354, 104, 41);
		frame.getContentPane().add(addButton_1_1);

		Button button = new Button("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame2<Leader> frame2 = new Frame2<Leader>(leader);
				frame2.setVisible(true);

				// Close the current frame
				frame.dispose();
			}
		});

		button.setBounds(34, 30, 88, 41);
		frame.getContentPane().add(button);

		TextField textField = new TextField();
		textField.setText("Enter team name");

		textField.setBounds(309, 99, 265, 30);
		frame.getContentPane().add(textField);

		JButton addButton = new JButton("Add");
		addButton.setBounds(34, 354, 104, 41);
		frame.getContentPane().add(addButton);
		addButton.setBackground(new Color(255, 255, 255));
		addButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {

				if (!textField.getText().isEmpty() && !textField.getText().equals("Enter team name")) {

					String enteredname = textField.getText();

					Team currentteam = null;
					int[] selectedRows = table_2.getSelectedRows();

					if (!teamCreated) {
						// Call createTeam function with the entered team name
						newteam = leader.createTeam(enteredname);
						currentteam = newteam;
						teamCreated = true;

						if (newteam != null) {

							for (int row : selectedRows) {

								System.out.println("Selected row = " + row);

								String memberName = table_2.getValueAt(row, 1).toString();
								int memberID = (int) table_2.getValueAt(row, 0);

								// Check if the member has already been selected
								if (!selectedMembers.contains(memberID)) {

									Object[] rowData = { memberID, memberName };
									model.addRow(rowData);

									selectedMembers.add(memberID);

									try {

										leader.addMember(newteam, memberID);

									} catch (Leader.UserNotFoundException e1) {
										// Handle the exception, e.g., show an error message
										System.out.println(e1.msg); // You may want to log the exception
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}

							}
							System.out.println(newteam.getTeamMembersString());
						}
					}

					else {
						System.out.println(newteam.tname);
						for (int row : selectedRows) {

							String memberName = table_2.getValueAt(row, 1).toString();
							int memberID = (int) table_2.getValueAt(row, 0);
							if (!selectedMembers.contains(memberID)) {

								Object[] rowData = { memberID, memberName };
								model.addRow(rowData);

								selectedMembers.add(memberID);

								try {

									leader.addMember(newteam, memberID);

								} catch (Leader.UserNotFoundException e1) {
									// Handle the exception, e.g., show an error message
									System.out.println(e1.msg); // You may want to log the exception
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
					}

				} else if (textField.getText().isEmpty() || textField.getText().equals("Enter team name")) {
					// Display an error message using JOptionPane
					JOptionPane.showMessageDialog(frame, "Enter team name first", "Error", JOptionPane.ERROR_MESSAGE);
					return; // Exit the method if an error occurs
				}
				System.out.println("Selcted Members");
				 System.out.println(selectedMembers);

			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (selectedMembers.size() == 0) {
		            JOptionPane.showMessageDialog(frame, "No Members in team", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        } 
		        else {
		        	
		            int rowCount = model.getRowCount();
		           
		            // Collect the selected row indices in a set
		            int[] selectedRows = table_2.getSelectedRows();
		            Set<Integer> selectedIDs = new HashSet<>();

		            // Collect the selected row indices in a set
		           
		            for (int i : selectedRows) {
		                selectedIDs.add((int) table_2.getValueAt(i, 0));
		            }

		            
		            // Iterate through the rows of the model
		            int i=rowCount-1;
		            while (i>=0)
		            {
		            	
		            	int id = (int) model.getValueAt(i, 0);
		                // Check if the row index is in the selected rows set
		                if (selectedIDs.contains(id)) {
		                    

		                    // Remove the member from the selectedMembers set
		                    selectedMembers.remove(id);
		                    selectedIDs.remove(id);

		                    // Remove the row from the JTable model
		                    model.removeRow(i);
		                    try {
								leader.removeMember(newteam, Misc.search(Misc.members, id));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		                    		                    
		                }
		               System.out.println(i-selectedIDs.size());
		               i--;
		                
		            }
		        }
		        System.out.println("Selcted Members");
		        System.out.println(selectedMembers);
		    }
		});


		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(148, 354, 104, 41);
		frame.getContentPane().add(btnDelete);

		frame.setVisible(true);
	}
}
