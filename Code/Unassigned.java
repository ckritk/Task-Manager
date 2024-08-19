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
public class Unassigned<T extends Team> extends JFrame {

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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Misc.init();
					Leader leader = Misc.leaders.get(0);
					Unassigned<Team> window = new Unassigned<Team>(leader.teams.get(0));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 */
	private static boolean hasDuplicates(ArrayList<Member> list) {
		Set<Member> set = new HashSet<Member>(list);
		return set.size() < list.size();
	}

	public Unassigned(T team) {
		initialize(team);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(T team) {
		HashSet<Object> selectedMembers = new HashSet<>();

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.setBounds(100, 100, 620, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

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
		Object[] column = { "Task ID", "Task Description" };
		model.setColumnIdentifiers(column);
		table_2.setModel(model);
		model.setRowCount(0);

		// Populate table_2 with all member names
		for (Task<?> unassigned : team.unassigned) {
            Object[] row = {unassigned.ID, unassigned.desc};
            model.addRow(row);
        }

			

		
		frame.setVisible(true);
	}
}
