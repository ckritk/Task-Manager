package test;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Test <T extends Leader> extends JFrame {

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
					Leader leader = Misc.leaders.get(2);
					System.out.println(leader.getID());
					new Test<Leader>(leader);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public Test(T leader) {
		initialize(leader);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize(T leader) {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Frame2 window = new Frame2();
		//window.setVisible(true);
		
	}

}
