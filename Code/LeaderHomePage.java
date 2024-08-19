package test;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class LeaderHomePage <T extends Leader> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Launch the application.
	 */
	
	private JFrame frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Misc.init();
					
					Leader leader = Misc.leaders.get(0);
					leader.displayLeaderDetails();
					System.out.println(leader.teams);
					new LeaderHomePage<Leader>(leader);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public LeaderHomePage(T leader) {
		initialize(leader);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize(T leader) {
		
		frame = new JFrame();
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		Frame2<Leader> window = new Frame2<Leader>(leader);
		window.setVisible(true);
		
	}

}
