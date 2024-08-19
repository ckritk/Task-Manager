package test;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame16{

	public Frame16(Team team) {
	        JFrame tasksFrame = new JFrame();
	        tasksFrame.getContentPane().setBackground(new Color(39, 154, 216));
	        tasksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        tasksFrame.setSize(500, 400);
	        tasksFrame.setLocationRelativeTo(null);
	        tasksFrame.setTitle("Team Details");
	
	        JPanel tasksPanel = new JPanel(null);
	        tasksPanel.setBackground(new Color(39, 154, 216));
	
	            String leaderName = team.leader.getName();
	
	            // Fetch other details (trackers, coworkers)...
	            ArrayList<String> trackerNames = new ArrayList<>();
	            ArrayList<String> coWorkers = new ArrayList<>();
	            
	            for (Member m: team.members) {
	            	coWorkers.add(m.getName());
	            }
	            for (Tracker t: team.trackers) {
	            	trackerNames.add(t.getName());
	            }
	
	            // Trackers Table...
	            String[] trackerColumnNames = { "Team Trackers " };
	            Object[][] trackerData = new Object[trackerNames.size()][1];
	            for (int i = 0; i < trackerNames.size(); i++) {
	                trackerData[i][0] = trackerNames.get(i);
	            }
	            JTable trackerTable = new JTable(trackerData, trackerColumnNames);
	            JScrollPane trackerScrollPane = new JScrollPane(trackerTable);
	            trackerScrollPane.setBounds(30, 140, 200, 185);
	
	            // Co-workers Table...
	            String[] coworkerColumnNames = { "Team member" };
	            Object[][] coworkerData = new Object[coWorkers.size()][1];
	            for (int i = 0; i < coWorkers.size(); i++) {
	                coworkerData[i][0] = coWorkers.get(i);
	            }
	            JTable coworkerTable = new JTable(coworkerData, coworkerColumnNames);
	            JScrollPane coworkerScrollPane = new JScrollPane(coworkerTable);
	            coworkerScrollPane.setBounds(253, 140, 200, 185);
	
	            tasksPanel.add(trackerScrollPane);
	            tasksPanel.add(coworkerScrollPane);
	
	            // Display Leader's name at the top
	            JLabel leaderLabel = new JLabel("Leader: " + leaderName);
	            leaderLabel.setForeground(new Color(255, 255, 255));
	            leaderLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
	            leaderLabel.setBounds(30, 109, 200, 20);
	            tasksPanel.add(leaderLabel);
	
	        
	
	        tasksFrame.getContentPane().add(tasksPanel);
	        
	        JLabel lblNewLabel = new JLabel("Team : "+ team.tname);
	        lblNewLabel.setForeground(new Color(255, 255, 255));
	        lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
	        lblNewLabel.setBounds(30, 84, 188, 14);
	        tasksPanel.add(lblNewLabel);
	        
	        JButton btnBack = new JButton("Back");
	        btnBack.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
	        btnBack.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        	}
	        });
	        
	        btnBack.setBackground(new Color(217, 217, 217));
	        btnBack.setBounds(10, 11, 89, 35);
	        tasksPanel.add(btnBack);
	        
	        JLabel lblNewLabel_1 = new JLabel("Team Details");
	        lblNewLabel_1.setForeground(new Color(255, 255, 255));
	        lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
	        lblNewLabel_1.setBackground(new Color(255, 255, 255));
	        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel_1.setBounds(137, 32, 200, 41);
	        tasksPanel.add(lblNewLabel_1);
	        tasksFrame.setVisible(true);
	    }
}