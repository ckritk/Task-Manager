package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.ArrayList;

public class Frame13 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table= new JTable();
    private ArrayList<JButton> btnArr=new ArrayList<>();
    private JButton btnallteam = new JButton("All Teams");
    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<Task<Leader>> allTasks = new ArrayList<>();
    private int comp,pend,all,aging;
    private JButton btnComp, btnPend, btnAge, btnAll;
    private JRadioButton rdbtn0, rdbtn1;
    @SuppressWarnings("unused")
	private Team curTeam;  
    private JButton btnTP;

    public Frame13(Leader leader) {
    	comp=Task.filter(leader.tasks,1).size();
    	pend=Task.filter(leader.tasks,0).size();
    	all=leader.tasks.size();
    	aging=Task.filter(leader.tasks).size();
    	allTasks.addAll(leader.tasks);
    	curTeam=null;
    	
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 431);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(39, 154, 216));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setTitle("Track Progress");

        ArrayList<Team> teams = leader.teams;

        JPanel panelTeams = new JPanel();
        panelTeams.setBackground(new Color(197, 217, 243));
        System.out.println(teams.size()+1);
        panelTeams.setBounds(0, 26, 96, 21+(40*(teams.size()+1)));
        contentPane.add(panelTeams);

        btnallteam.setEnabled(false);
        btnallteam.setBackground(new Color(255, 255, 255));
        btnallteam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for(JButton btn : btnArr) {
            		btn.setEnabled(true);
            	}
            	curTeam=null;
            	btnallteam.setEnabled(false);
            	comp=Task.filter(leader.tasks,1).size();
            	pend=Task.filter(leader.tasks,0).size();
            	all=leader.tasks.size();
            	aging=Task.filter(leader.tasks).size();
            	allTasks.clear();
            	allTasks.addAll(leader.tasks);
            	
            	comp=Task.filter(allTasks,1).size();
            	pend=Task.filter(allTasks,0).size();
            	all=allTasks.size();
            	aging=Task.filter(allTasks).size();
            	
            	btnAll.setText("All Tasks("+all+")");
            	btnComp.setText("Completed("+comp+")");
            	btnPend.setText("Pending("+pend+")");
            	btnAge.setText("Overdue("+aging+")");
            	
            	populateTable(leader.tasks,model);
            	
            }
        });
        panelTeams.setLayout(new GridLayout(0, 1, 0, 0));
        panelTeams.add(btnallteam);

        for (Team team : teams) {
            JButton teamButton = new JButton(team.tname);
            teamButton.setBackground(new Color(183,224,249));
            teamButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	for(JButton btn : btnArr) {
                		btn.setEnabled(true);
                	}
                	
                	System.out.println("Crrteam");
                	System.out.println(Misc.searchTeam(teams,teamButton.getText()));
                	//curTeam=Misc.searchTeam(teams,teamButton.getText());
                	btnallteam.setEnabled(true);
                	teamButton.setEnabled(false);

                	allTasks=Task.filter(leader.tasks,teamButton.getText());
                	comp=Task.filter(allTasks,1).size();
                	pend=Task.filter(allTasks,0).size();
                	all=allTasks.size();
                	aging=Task.filter(allTasks).size();
                	
                	btnAll.setText("All Tasks("+all+")");
                	btnComp.setText("Completed("+comp+")");
                	btnPend.setText("Pending("+pend+")");
                	btnAge.setText("Overdue("+aging+")");
                	
                	populateTable(allTasks,model);
                }
            });
            btnArr.add(teamButton);
            panelTeams.add(teamButton);
        }

        JPanel panelTasks = new JPanel();
        panelTasks.setBackground(new Color(255, 255, 255));
        panelTasks.setBounds(97, 26, 529, 373);
        contentPane.add(panelTasks);
        panelTasks.setLayout(null);
        
        if (teams.size()>0)
        {
        btnComp = new JButton("Completed("+comp+")");
        btnComp.setBackground(new Color(255, 255, 255));
        btnComp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		populateTable(Task.filter(allTasks,1),model);
        	}
        });
        btnComp.setBounds(134, 11, 141, 23);
        panelTasks.add(btnComp);
        
        btnAll = new JButton("All Tasks("+all+")");
        btnAll.setBackground(new Color(255, 255, 255));
        btnAll.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		populateTable(allTasks,model);
        	}
        });
        btnAll.setBounds(9, 11, 116, 23);
        panelTasks.add(btnAll);
        
        btnPend = new JButton("Pending("+pend+")");
        btnPend.setBackground(new Color(255, 255, 255));
        btnPend.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		populateTable(Task.filter(allTasks,0),model);
        	}
        });
        btnPend.setBounds(284, 11, 117, 23);
        panelTasks.add(btnPend);
        
        btnAge = new JButton("Overdue("+aging+")");
        btnAge.setBackground(new Color(255, 255, 255));
        btnAge.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		populateTable(Task.filter(allTasks),model);
        	}
        });
        btnAge.setBounds(410, 11, 109, 23);
        panelTasks.add(btnAge);
        
        //table = new JTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setEnabled(false);
        scrollPane.setBounds(15, 45, 500, 235);
        panelTasks.add(scrollPane);
        String[] columns = {"Team", "Task", "Status",  "Deadline"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        setColumnWidth(table, 0, 30);
        setColumnWidth(table, 1, 170);
        setColumnWidth(table, 2, 40);
        setColumnWidth(table,3,40);
        
        
        JButton btnStatus = new JButton("Update Status");
        btnStatus.setBackground(new Color(255, 255, 255));
        btnStatus.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ArrayList<Task<Leader>> tasks = new ArrayList<>();
        		int[] selectedRows = table.getSelectedRows();
                for (int row : selectedRows) {
                	int status=(rdbtn1.isSelected())? 1:0;
                    Task<Leader> t = Misc.searchTaskL(leader.tasks, (String)table.getValueAt(row, 0), (String)table.getValueAt(row, 1));
                    tasks.add(t);
                    leader.updateProgress(tasks,status);
                    
                    comp=Task.filter(allTasks,1).size();
                	pend=Task.filter(allTasks,0).size();
                	all=allTasks.size();
                	aging=Task.filter(allTasks).size();
                	
                	btnAll.setText("All Tasks("+all+")");
                	btnComp.setText("Completed("+comp+")");
                	btnPend.setText("Pending("+pend+")");
                	btnAge.setText("Overdue("+aging+")");
                	
                	populateTable(allTasks,model);
        		}
        	}
        });
        btnStatus.setBounds(344, 321, 141, 23);
        panelTasks.add(btnStatus);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        
        rdbtn0 = new JRadioButton("Pending");
        rdbtn0.setBackground(new Color(255, 255, 255));
        rdbtn0.setBounds(334, 291, 75, 23);
        panelTasks.add(rdbtn0);
        
        rdbtn1 = new JRadioButton("Completed");
        rdbtn1.setBackground(new Color(255, 255, 255));
        rdbtn1.setBounds(423, 291, 92, 23);
        panelTasks.add(rdbtn1);
        
        buttonGroup.add(rdbtn0);
        buttonGroup.add(rdbtn1);

        rdbtn1.setSelected(true);
        
        btnTP = new JButton("Summary");
        btnTP.setBackground(new Color(255, 255, 255));
        btnTP.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EventQueue.invokeLater(new Runnable() {
        			public void run() {
        				try {
        					Frame7 frame = new Frame7(all,comp,pend-aging,aging);
        					frame.setVisible(true);
        				} catch (Exception e) {
        					e.printStackTrace();
        				}
        			}
        		});
        	}
        });
        btnTP.setBounds(164, 321, 141, 23);
        panelTasks.add(btnTP);
        
        populateTable(leader.tasks,model);
        
        }
        
        else {
        	JLabel lblMsg = new JLabel("No Teams found");
        	lblMsg.setBounds(150,120,400,100);
        	lblMsg.setFont(new Font("Tahoma", Font.PLAIN, 25));
            panelTasks.add(lblMsg);
        }

        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(39, 154, 216));
        panelTop.setBounds(0, 0, 626, 26);
        contentPane.add(panelTop);
        panelTop.setLayout(null);
        
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Frame12 frame12 = new Frame12(leader);
                frame12.setVisible(true);

                // Close the current frame
                dispose();
        	}
        });
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.setBounds(0, 0, 73, 26);
        panelTop.add(btnBack);
        
    }
    
    public static <U extends User & Executor> void populateTable(ArrayList<Task<U>> tasks, DefaultTableModel model) {
        model.setRowCount(0); // Clear existing rows

        for (Task<U> task : tasks) {
            Object[] rowData = {
                    task.team != null ? task.team.tname : "No Team",
                    task.desc,
                    task.status == 1? "completed" : "pending",
                    task.deadline
            };
            model.addRow(rowData);
        }
    }
       
    private static void setColumnWidth(JTable table, int columnIndex, int width) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setPreferredWidth(width);
    }
}