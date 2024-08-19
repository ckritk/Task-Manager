package test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame1 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JTable table;
	DefaultTableModel model;

	public static boolean isTabPresent(JTabbedPane tabbedPane, String tabTitle) {
		int tabCount = tabbedPane.getTabCount();
		for (int i = 0; i < tabCount; i++) {
			String existingTitle = tabbedPane.getTitleAt(i);
			if (existingTitle.equals(tabTitle)) {
				// Tab with the same title is already present
				return true;
			}
		}
		// Tab with the given title is not present
		return false;
	}

	public Frame1(Leader leader) {

		frame = new JFrame();
		frame.setSize(681, 400);
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("View Team");

		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Teams ");
		lblNewLabel.setBounds(232, 50, 371, 64);
		lblNewLabel.setBackground(new Color(39, 154, 216));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel);

		Panel panel = new Panel();
		panel.setBounds(27, 143, 127, 282);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(182, 143, 466, 282);
		frame.getContentPane().add(tabbedPane);

		for (Team team : leader.teams) {
			model = new DefaultTableModel();
			table = new JTable();
			System.out.println(team.members);
			if (isTabPresent(tabbedPane, team.tname) == false) {
				System.out.println(team.tname + " is not in tabbed pane");
				tabbedPane.addTab(team.tname, null, table, null);

				// Create the model for table_2

				int maxLength = Math.max(Math.max(team.members.size(), team.trackers.size()), team.unassigned.size());

				// Create a 2D array to store data
				Object[][] rowData = new Object[maxLength][3];
				Object[] column = {"Members", "Trackers", "Unassigned Tasks"};
				
				model.setColumnIdentifiers(column);

				// Populate the columns separately
				for (int i = 0; i < maxLength; i++) {

					rowData[i][0] = i < team.members.size() ? team.members.get(i).getName() : null;
					rowData[i][1] = i < team.trackers.size() ? team.trackers.get(i).getName() : null;
					rowData[i][2] = i < team.unassigned.size() ? team.unassigned.get(i).desc : null;
				}

				// Create a DefaultTableModel with the data
				DefaultTableModel model = new DefaultTableModel(rowData,
						new String[] { "Members", "Trackers", "Unassigned Tasks" });

				table.setModel(model);
			}
		}

		Button button_4 = new Button("View unassigned");
		button_4.setBackground(new Color(255, 255, 255));
		button_4.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
				System.out.println("Selected Tab Title: " + selectedTitle);
				for (Team team : leader.teams) {
					if (team.tname.equals(selectedTitle)) {
						Frame6<Leader> frame6 = new Frame6<Leader>(team);
						frame6.setVisible(true);

						// Close the current frame
						frame.dispose();
					}
				}
			}
		});
		
		Button button_3 = new Button("Delete team");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
				for (Team team:leader.teams)
				{
					if (team.tname == selectedTitle)
					{
						try {
							leader.deleteTeam(team);
							for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					            if (selectedTitle.equals(tabbedPane.getTitleAt(i))) {
					                tabbedPane.remove(i);
					                return; // Assuming each tab has a unique title, we can exit early when a match is found.
					            }
					        }
							break;
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		button_3.setBackground(new Color(255, 255, 255));
		button_3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		panel.add(button_3);
		panel.add(button_4);

		Button button_1 = new Button("Edit Team");
		button_1.setBackground(new Color(255, 255, 255));
		button_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
				System.out.println("Selected Tab Title: " + selectedTitle);
				for (Team team : leader.teams) {
					if (team.tname.equals(selectedTitle)) {
						Frame5<Leader> frame5 = new Frame5<Leader>(leader, team);
						frame5.setVisible(true);

						// Close the current frame
						frame.dispose();
					}
				}

			}
		});
		panel.add(button_1);
		
				Button button = new Button("Back");
				button.setBackground(new Color(255, 255, 255));
				panel.add(button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Frame2<Leader> frame2 = new Frame2<Leader>(leader);
						frame2.setVisible(true);

						// Close the current frame
						frame.dispose();
					}
				});
				button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));

	}
}
