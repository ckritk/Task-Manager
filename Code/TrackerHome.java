package test;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class TrackerHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Misc.init();
					TrackerHome frame = new TrackerHome(Misc.trackers.get(0));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TrackerHome(Tracker tracker) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 342);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(39, 154, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Tracker Home Page");

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		btnSignOut.setBackground(new Color(255, 255, 255));
		btnSignOut.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		btnSignOut.setBounds(342, 11, 99, 33);
		contentPane.add(btnSignOut);

		JPanel panel = new JPanel();
		panel.setBounds(135, 55, 176, 55 * (tracker.teams.size()));
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblNewLabel = new JLabel("My Teams");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblNewLabel.setBounds(135, 11, 176, 33);
		contentPane.add(lblNewLabel);

		// Dynamically create buttons for each team name
		for (Team t : tracker.teams) {
			JButton teamButton = new JButton(t.tname);
			teamButton.setBackground(new Color(255, 255, 255));
			teamButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Frame14 frame = new Frame14(Misc.searchTeam(tracker.teams, teamButton.getText()), tracker);
					frame.setVisible(true);
				}
			});
			panel.add(teamButton);
		}
	}
}