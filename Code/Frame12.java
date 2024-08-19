package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame12 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel buttonPanel;
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Misc.init(); Frame12<Leader> window =
	 * new Frame12<Leader>(Misc.leaders.get(0)); window.frame.setVisible(true); }
	 * catch (Exception e) { e.printStackTrace(); } } }); }
	 */

	public Frame12(Leader leader) {
		initialize(leader);
	}

	private void initialize(Leader leader) {
		frame = new JFrame();
		frame.setSize(500, 500);
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Track Progress");

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(39, 154, 216));

		JScrollPane scrollPane = new JScrollPane(buttonPanel);
		scrollPane.setBounds(10, 10, 464, 441); // Adjust the bounds as needed
		frame.getContentPane().add(scrollPane);

		System.out.println(leader.teams);
		addButton(leader, "Own progress");

		for (int i = 0; i < leader.teams.size(); i++) {
			addButton(leader, leader.teams.get(i).tname);// Add an initial button for demonstration purposes
		}
		

		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame2<Leader> frame2 = new Frame2<Leader>(leader);
                frame2.setVisible(true);

                // Close the current frame
                frame.dispose();
			}
		});
	
		button.setBackground(Color.WHITE); // Set button background color
		button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14)); // Set button font
		buttonPanel.add(button);

		frame.setVisible(true);
	}

	private void addButton(Leader leader, String name) {
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		JButton button = new JButton(name);
	
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (name.equals("Own progress")) {
					Frame13 frame13 = new Frame13(leader);
					frame13.setVisible(true);

					// Close the current frame
					frame.dispose();
				} else {
					Team team = Misc.searchTeam(leader.teams, name);
					System.out.println(team);
					Frame11 frame11 = new Frame11(team);
					frame11.setVisible(true);

					// Close the current frame
					frame.dispose();
				}
			}
		});
		button.setBackground(Color.WHITE); // Set button background color
		button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14)); // Set button font
		buttonPanel.add(button);
		frame.revalidate(); // Refresh the frame to reflect the changes
	}
}
