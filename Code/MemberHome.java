package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.ArrayList;

public class MemberHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table= new JTable();
    private ArrayList<JButton> btnArr=new ArrayList<>();
    private JButton btnallteam = new JButton("All Teams");
    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<Task<Member>> allTasks = new ArrayList<>();
    private int comp,pend,all,aging;
    private JButton btnComp, btnPend, btnAge, btnAll, btnLeave;
    private JRadioButton rdbtn0, rdbtn1;
    private Team curTeam;  
    private JButton btnTP;
    private JButton btnTD;

    public MemberHome(Member member) {
    	
    	System.out.println(member.tasks);
    	comp=Task.filter(member.tasks,1).size();
    	pend=Task.filter(member.tasks,0).size();
    	all=member.tasks.size();
    	aging=Task.filter(member.tasks).size();
    	allTasks.addAll(member.tasks);
    	curTeam=null;
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Member Home Page");
        setBounds(100, 100, 640, 431);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(39, 154, 216));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        ArrayList<Team> teams = new ArrayList<>();
        for (Task<Member> task : member.tasks) {
            Team team = task.team;
            if (!teams.contains(team)) {
                teams.add(team);
            }
        }

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
            	btnLeave.setEnabled(false);
            	btnallteam.setEnabled(false);
            	comp=Task.filter(member.tasks,1).size();
            	pend=Task.filter(member.tasks,0).size();
            	all=member.tasks.size();
            	aging=Task.filter(member.tasks).size();
            	allTasks.clear();
            	allTasks.addAll(member.tasks);
            	
            	comp=Task.filter(allTasks,1).size();
            	pend=Task.filter(allTasks,0).size();
            	all=allTasks.size();
            	aging=Task.filter(allTasks).size();
            	
            	btnAll.setText("All Tasks("+all+")");
            	btnComp.setText("Completed("+comp+")");
            	btnPend.setText("Pending("+pend+")");
            	btnAge.setText("Overdue("+aging+")");
            	
            	populateTable(member.tasks,model);
            	
            }
        });
        panelTeams.setLayout(new GridLayout(0, 1, 0, 0));
        panelTeams.add(btnallteam);

        for (Team team : teams) {
            JButton teamButton = new JButton(team.tname);
            teamButton.setBackground(new Color(255, 255, 255));
            teamButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	for(JButton btn : btnArr) {
                		btn.setEnabled(true);
                	}
                	curTeam=Misc.searchTeam(teams,teamButton.getText());
                	btnLeave.setEnabled(true);
                	btnallteam.setEnabled(true);
                	teamButton.setEnabled(false);
                	
                	allTasks=Task.filter(member.tasks,teamButton.getText());
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
        
        //if (teams.size()>0)
        //{
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
        table.setFillsViewportHeight(true);
        
        //table = new JTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setEnabled(false);
        scrollPane.setBounds(15, 45, 500, 199);
        panelTasks.add(scrollPane);
        String[] columns = {"Team", "Task", "Status", "Deadline"};
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
        		ArrayList<Task<Member>> tasks = new ArrayList<>();
        		int[] selectedRows = table.getSelectedRows();
                for (int row : selectedRows) {
                	int status=(rdbtn1.isSelected())? 1:0;
                    Task<Member> t = Misc.searchTask(member.tasks, (String)table.getValueAt(row, 0), (String)table.getValueAt(row, 1));
                    tasks.add(t);
                    member.updateProgress(tasks,status);
                    
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
        btnStatus.setBounds(399, 321, 116, 23);
        panelTasks.add(btnStatus);
        
        btnLeave = new JButton("Leave Team");
        btnLeave.setBackground(new Color(255, 255, 255));
        btnLeave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        			member.leaveTeam(curTeam);
        			JButton del = findButtonByText(btnArr, curTeam.tname);
        			panelTeams.remove(del);
        			panelTeams.revalidate();
        			panelTeams.repaint();
        			
        			
        			for(JButton btn : btnArr) {
                		btn.setEnabled(true);
                	}
        			
        			teams.remove(curTeam); 

        			panelTeams.setBounds(0, 26, 96, 21+(40*(teams.size()+1)));
        			
                	curTeam=null;
                	btnLeave.setEnabled(false);
                	btnallteam.setEnabled(false);
                	comp=Task.filter(member.tasks,1).size();
                	pend=Task.filter(member.tasks,0).size();
                	all=member.tasks.size();
                	aging=Task.filter(member.tasks).size();
                	allTasks=member.tasks;
                	
                	comp=Task.filter(allTasks,1).size();
                	pend=Task.filter(allTasks,0).size();
                	all=allTasks.size();
                	aging=Task.filter(allTasks).size();
                	
                	btnAll.setText("All Tasks("+all+")");
                	btnComp.setText("Completed("+comp+")");
                	btnPend.setText("Pending("+pend+")");
                	btnAge.setText("Overdue("+aging+")");
                	
                	populateTable(member.tasks,model);
                	
        	}
        });
        btnLeave.setBounds(141, 321, 116, 23);
        panelTasks.add(btnLeave);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        
        rdbtn0 = new JRadioButton("Pending");
        rdbtn0.setBackground(new Color(255, 255, 255));
        rdbtn0.setBounds(404, 291, 75, 23);
        panelTasks.add(rdbtn0);
        
        rdbtn1 = new JRadioButton("Completed");
        rdbtn1.setBackground(new Color(255, 255, 255));
        rdbtn1.setBounds(404, 265, 92, 23);
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
        btnTP.setBounds(267, 321, 122, 23);
        panelTasks.add(btnTP);
        
        btnTD = new JButton("Team Details");
        btnTD.setBackground(new Color(255, 255, 255));
        btnTD.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Frame16(curTeam);
        	}
        });
        btnTD.setBounds(15, 321, 116, 23);
        panelTasks.add(btnTD);
        
        populateTable(member.tasks,model);
        
        //}
        
        /*else {
        	JLabel lblMsg = new JLabel("No Teams found");
        	lblMsg.setBounds(150,120,400,100);
        	lblMsg.setFont(new Font("Tahoma", Font.PLAIN, 25));
            panelTasks.add(lblMsg);
        }*/

        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(39, 154, 216));
        panelTop.setBounds(0, 0, 626, 26);
        contentPane.add(panelTop);
        panelTop.setLayout(null);
        
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setBackground(new Color(255, 255, 255));
        btnSignOut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		//Misc.term();
        		Login login = new Login();
                login.setVisible(true);
        	}
        });
        btnSignOut.setBounds(529, 0, 97, 26);
        panelTop.add(btnSignOut);
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
    
    private JButton findButtonByText(ArrayList<JButton> buttonList, String searchText) {
        for (JButton button : buttonList) {
            if (button.getText().equals(searchText)) {
                return button; // Found the button with the specified text
            }
        }
        return null; // Button with the specified text not found
    }
}