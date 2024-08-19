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

@SuppressWarnings("unused")
public class Frame10<T extends Leader> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTable table_4;
	DefaultTableModel model;
	private JTable table_3;

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

	public Frame10(T leader, Team team) {
		initialize(leader, team);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(T leader, Team team) {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.setBounds(100, 100, 612, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Edit Team");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(309, 136, 259, 207);
		frame.getContentPane().add(scrollPane);

		HashSet<Object> selectedTrackers = new HashSet<>();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 136, 267, 207);
		frame.getContentPane().add(scrollPane_1);

		table_3 = new JTable();
		scrollPane_1.setViewportView(table_3);

		// Create the model for table_2
		model = new DefaultTableModel();
		Object[] column1 = { "Tracker ID", "Tracker" };
		model.setColumnIdentifiers(column1);
		table_3.setModel(model);

		Button button = new Button("Back");
		scrollPane_1.setColumnHeaderView(button);

		// Populate table_2 with all member names
		for (Tracker tracker : Misc.trackers) {

			if (tracker.getID() != leader.getID()
					&& !team.members.contains(Misc.search(Misc.members, tracker.getID()))  
					&& !team.trackers.contains(tracker)) {
				Object[] row1 = { tracker.getID(), tracker.getName() };
				model.addRow(row1);
			}
		}

		table_4 = new JTable();
		scrollPane.setViewportView(table_4);
		model = new DefaultTableModel();
		Object[] column = { "Tracker ID", "Tracker" };
		Object[] row = new Object[0];
		model.setColumnIdentifiers(column);
		table_4.setModel(model);
		
		
		for (Tracker tracker : team.trackers) {
	            Object[] row1 = {tracker.getID(), tracker.getName()};
	            model.addRow(row1);
	            selectedTrackers.add(tracker.getID());
	     }

		Label label = new Label("Select Trackers");
		label.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(59, 75, 230, 55);
		frame.getContentPane().add(label);

		Label label_1 = new Label("TEAM");
		label_1.setAlignment(Label.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		label_1.setBounds(309, 75, 230, 55);
		frame.getContentPane().add(label_1);



		JButton addButton_1_1 = new JButton("Done");
		addButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.out.println("Created team");
	             System.out.println(team.getTeamTrackersString());
				Frame2<T> frame2 = new Frame2<T>(leader);
				frame2.setVisible(true);

				// Close the current frame
				frame.dispose();
			}
		});
		addButton_1_1.setBackground(Color.WHITE);
		addButton_1_1.setBounds(464, 354, 104, 41);
		frame.getContentPane().add(addButton_1_1);

		Button button_1 = new Button("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		button_1.setBounds(32, 28, 88, 41);
		frame.getContentPane().add(button_1);

		JButton addButton = new JButton("Add");
		addButton.setBounds(34, 354, 104, 41);
		frame.getContentPane().add(addButton);
		addButton.setBackground(new Color(255, 255, 255));
		addButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {

				int[] selectedRows = table_3.getSelectedRows();

				for (int row : selectedRows) {

					System.out.println("Selected row = " + row);

					String trackerName = table_3.getValueAt(row, 1).toString();
					int trackerID = (int) table_3.getValueAt(row, 0);
                    
					System.out.println("Selcted Trackers");
			        System.out.println(selectedTrackers);
					// Check if the member has already been selected
					if (!selectedTrackers.contains(trackerID)) {
						System.out.println("Doesn't have "+trackerID);
						Object[] rowData = { trackerID, trackerName };
						model.addRow(rowData);

						selectedTrackers.add(trackerID);

						try {

							leader.addTracker(team, trackerID);

						} catch (Leader.UserNotFoundException e1) {
							// Handle the exception, e.g., show an error message
							System.out.println(e1.msg); // You may want to log the exception
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					

				}
				
		        System.out.println(team.getTeamTrackersString());
			
			}

		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(146, 354, 104, 41);
		frame.getContentPane().add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (selectedTrackers.size() == 0) {
		            JOptionPane.showMessageDialog(frame, "No Trackers in team", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        } 
		        else {
		        	
		            int rowCount = model.getRowCount();
		           
		            // Collect the selected row indices in a set
		            int[] selectedRows = table_3.getSelectedRows();
		            Set<Integer> selectedIDs = new HashSet<>();

		            // Collect the selected row indices in a set
		           
		            for (int i : selectedRows) {
		                selectedIDs.add((int) table_3.getValueAt(i, 0));
		            }

		            
		            // Iterate through the rows of the model
		            int i=rowCount-1;
		            while (i>=0)
		            {
		            	
		            	int id = (int) model.getValueAt(i, 0);
		                // Check if the row index is in the selected rows set
		                if (selectedIDs.contains(id)) {
		                    
                            System.out.println("Before removing");
                            System.out.println(team.getTeamTrackersString());
		                    // Remove the member from the selectedMembers set
		                    selectedTrackers.remove(id);
		                    selectedIDs.remove(id);

		                    // Remove the row from the JTable model
		                    model.removeRow(i);
		                    try {
								leader.removeTracker(team, Misc.search(Misc.trackers, id));
								System.out.println("After removing");
	                            System.out.println(team.getTeamTrackersString());
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		                    		                    
		                }
		               System.out.println(i-selectedIDs.size());
		               i--;
		                
		            }
		        }
		        System.out.println("Selcted Trackers");
		        System.out.println(selectedTrackers);
		        System.out.println(team.getTeamTrackersString());
		    }
		});

		frame.setVisible(true);
	}
}
