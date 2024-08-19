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
public class Frame5<T extends Leader> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTable table;
	DefaultTableModel model;
	private JTable table_2;

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

	public Frame5(T leader, Team team) {
		initialize(leader, team);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(T leader, Team team) {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.setBounds(100, 100, 620, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Edit Team");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(309, 144, 265, 199);
		frame.getContentPane().add(scrollPane);

		HashSet<Object> selectedMembers = new HashSet<>();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(34, 144, 265, 199);
		frame.getContentPane().add(scrollPane_1);

		table_2 = new JTable();
		scrollPane_1.setViewportView(table_2);

		// Create the model for table_2
		model = new DefaultTableModel();
		Object[] column1 = { "Member ID", "Member" };
		model.setColumnIdentifiers(column1);
		table_2.setModel(model);

		// Populate table_2 with all member names
		for (Member member : Misc.members) {
			if (member.getID() != leader.getID() && !team.trackers.contains(Misc.search(Misc.trackers, member.getID())) ) {
				Object[] row1 = { member.getID(), member.getName() };
				model.addRow(row1);
			}
		}

		table = new JTable();
		scrollPane.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = { "Member ID", "Member" };
		Object[] row = new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);

		for (Member member : team.members) {
			Object[] row1 = { member.getID(), member.getName() };
			model.addRow(row1);
			selectedMembers.add(member.getID());
		}

		Label label = new Label("Edit Members");
		label.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(42, 77, 230, 55);
		frame.getContentPane().add(label);

		Label label_1 = new Label(team.tname);
		label_1.setAlignment(Label.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 26));
		label_1.setBounds(292, 77, 230, 55);
		frame.getContentPane().add(label_1);

		Button button = new Button("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame2<Leader> frame2 = new Frame2<Leader>(leader);
				frame2.setVisible(true);

				// Close the current frame
				frame.dispose();
			}
		});

		JButton addButton = new JButton("Add");
		addButton.setBounds(34, 354, 104, 41);
		frame.getContentPane().add(addButton);
		addButton.setBackground(new Color(255, 255, 255));
		addButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {

				Team currentteam = null;
				int[] selectedRows = table_2.getSelectedRows();

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

							leader.addMember(team, memberID);

						} catch (Leader.UserNotFoundException e1) {
							// Handle the exception, e.g., show an error message
							System.out.println(e1.msg); // You may want to log the exception
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

				}

				System.out.println("Selcted Members");
				System.out.println(selectedMembers);

			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int rowCount = model.getRowCount();

				// Collect the selected row indices in a set
				int[] selectedRows = table_2.getSelectedRows();
				Set<Integer> selectedIDs = new HashSet<>();

				// Collect the selected row indices in a set

				for (int i : selectedRows) {
					selectedIDs.add((int) table_2.getValueAt(i, 0));
				}

				// Iterate through the rows of the model
				int i = rowCount - 1;
				while (i >= 0) {

					int id = (int) model.getValueAt(i, 0);
					// Check if the row index is in the selected rows set
					if (selectedIDs.contains(id)) {

						// Remove the member from the selectedMembers set
						selectedMembers.remove(id);
						selectedIDs.remove(id);

						// Remove the row from the JTable model
						model.removeRow(i);
						try {
							leader.removeMember(team, Misc.search(Misc.members, id));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					System.out.println(i - selectedIDs.size());
					i--;

				}

				System.out.println("Selcted Members");
				System.out.println(selectedMembers);
			}
		});
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(148, 354, 104, 41);
		frame.getContentPane().add(btnDelete);

		JButton addButton_1_1 = new JButton("Next");
		addButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Frame10<T> frame10 = new Frame10<T>(leader, team);

				// Close the current frame
				frame.setVisible(false);
				frame.dispose();

			}
		});
		addButton_1_1.setBackground(Color.WHITE);
		addButton_1_1.setBounds(470, 354, 104, 41);
		frame.getContentPane().add(addButton_1_1);
		
		Button button_1 = new Button("Back");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  Frame1 frame1 = new Frame1(leader);
	                frame1.frame.setVisible(true);

	                // Close the current frame
	                frame.dispose();
			}
		});
		button_1.setBounds(38, 30, 100, 41);
		frame.getContentPane().add(button_1);
		frame.setVisible(true);
	}
}
