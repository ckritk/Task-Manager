package test;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;

@SuppressWarnings("unused")
public class Frame2 <T extends Leader> extends JFrame{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Misc.init();
					Frame2<Leader> window = new Frame2<Leader>(Misc.leaders.get(0));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public Frame2(T leader) {
		initialize(leader);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Leader leader) {
		frame = new JFrame();
		frame.setSize(367,354);
		frame.setBackground(new Color(240, 240, 240));
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.getContentPane().setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		frame.getContentPane().setBackground(new Color(39, 154, 216));
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Leader Home Page");
		
		Button button_1 = new Button("Sign Out");
		button_1.setBackground(new Color(255, 255, 255));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 dispose();
		            Login login = new Login();
		                login.setVisible(true);
			}
		});
		button_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		button_1.setForeground(new Color(0, 0, 0));
		button_1.setBounds(290, 332, 133, 57);
		frame.getContentPane().add(button_1);
		
		Button button_1_1 = new Button("View Team");
		button_1_1.setBackground(new Color(255, 255, 255));
		button_1_1.setForeground(Color.BLACK);
		button_1_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		button_1_1.setBounds(290, 228, 133, 57);
		button_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Frame1 object
                Frame1 frame1 = new Frame1(leader);
                frame1.frame.setVisible(true);

                // Close the current frame
                frame.dispose();
            }
        });
        frame.getContentPane().add(button_1_1);

		
		Button button_1_2 = new Button("Create Team");
		button_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open the Frame1 object
                Frame3<Leader> frame3 = new Frame3<Leader>(leader);
                frame3.setVisible(true);

                // Close the current frame
                frame.dispose();
				
			}
		});
		
		button_1_2.setBackground(new Color(255, 255, 255));
		button_1_2.setForeground(Color.BLACK);
		button_1_2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		button_1_2.setBounds(109, 228, 133, 57);
		frame.getContentPane().add(button_1_2);
		
		Button button_1_3 = new Button("Track Progress");
		button_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame12 frame12 = new Frame12(leader);
                frame12.setVisible(true);

                // Close the current frame
                frame.dispose();
			}
		});
		button_1_3.setBackground(new Color(255, 255, 255));
		button_1_3.setForeground(Color.BLACK);
		button_1_3.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		button_1_3.setBounds(109, 332, 133, 57);
		frame.getContentPane().add(button_1_3);
		
		Label label = new Label("Welcome!");
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 64));
		label.setBounds(120, 56, 282, 70);
		frame.getContentPane().add(label);
		
		Label label_1 = new Label("What would you like to do today?");
		label_1.setFont(new Font("Dialog", Font.PLAIN, 19));
		label_1.setBounds(121, 148, 302, 21);
		frame.getContentPane().add(label_1);
		frame.setBounds(100, 100, 545, 529);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		frame.setVisible(true);
		
	}
}
