package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.ArrayList;

public class Frame11 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table= new JTable();
    private ArrayList<JButton> btnArr=new ArrayList<>();
    private JButton btnTeam = new JButton("Team");
    private JButton unassignedButton = new JButton("Unassigned");
    private JButton leaderButton;
    private DefaultTableModel model = new DefaultTableModel();
    //private ArrayList<Task<Team>> allTasks;
    private ArrayList<Task<?>> allTasks = new ArrayList<>();
    private ArrayList<Task<?>> curTasks = new ArrayList<>();
    private int comp,pend,all,aging;
    private JButton btnComp, btnPend, btnAge, btnAll;
    @SuppressWarnings("unused")
	private JRadioButton rdbtn0, rdbtn1;
    //private Team curTeam;  
    private JButton btnTP;

    public Frame11(Team team) {
      allTasks.addAll(team.unassigned);
      allTasks.addAll(team.leader.tasks);
      for (Member m : team.members) {
        allTasks.addAll(Task.filter_(m.tasks,team));
      }
      curTasks.addAll(allTasks);
      comp=Task.filter_(allTasks,1).size();
      pend=Task.filter_(allTasks,0).size();
      all=allTasks.size();
      aging=Task.filter_(allTasks).size();

      //curUser=null;

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 667, 431);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(39, 154, 216));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setTitle("Track Progress");
        contentPane.setLayout(null);

        ArrayList<Member> members = new ArrayList<>();
        members.addAll(team.members);

        JPanel panelTeams = new JPanel();
        panelTeams.setBackground(new Color(197, 217, 243));
        System.out.println(members.size()+1);
        panelTeams.setBounds(0, 26, 133, 21+(40*(members.size()+3)));
        contentPane.add(panelTeams);

        btnTeam.setEnabled(false);
        btnTeam.setBackground(new Color(255, 255, 255));
        btnTeam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              for(JButton btn : btnArr) {
                btn.setEnabled(true);
              }
              //curTeam=null;
              btnTeam.setEnabled(false);

              curTasks.clear();
              curTasks.addAll(team.unassigned);
              curTasks.addAll(team.leader.tasks);
              for (Member m : team.members) {
                curTasks.addAll(Task.filter_(m.tasks,team));
              }
              comp=Task.filter_(curTasks,1).size();
              pend=Task.filter_(curTasks,0).size();
              all=curTasks.size();
              aging=Task.filter_(curTasks).size();

              btnAll.setText("All Tasks("+all+")");
              btnComp.setText("Completed("+comp+")");
              btnPend.setText("Pending("+pend+")");
              btnAge.setText("Overdue("+aging+")");

              populateTable(curTasks,model);

            }
        });

        panelTeams.setLayout(new GridLayout(0, 1, 0, 0));
        panelTeams.add(btnTeam);

        unassignedButton.setBackground(new Color(255, 255, 255));
        unassignedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              for(JButton btn : btnArr) {
                btn.setEnabled(true);
              }
              //curTeam=Misc.searchTeam(teams,memberButton.getText());
              btnTeam.setEnabled(true);
              leaderButton.setEnabled(true);
              unassignedButton.setEnabled(false);

              curTasks.clear();
              curTasks.addAll(team.unassigned); 
              comp=Task.filter_(curTasks,1).size();
              pend=Task.filter_(curTasks,0).size();
              all=curTasks.size();
              aging=Task.filter_(curTasks).size();

              btnAll.setText("All Tasks("+all+")");
              btnComp.setText("Completed("+comp+")");
              btnPend.setText("Pending("+pend+")");
              btnAge.setText("Overdue("+aging+")");

              populateTable(curTasks,model);
            }
        });
        btnArr.add(unassignedButton);
        panelTeams.add(unassignedButton);
        
                leaderButton = new JButton(team.leader.getName());
                leaderButton.setBackground(new Color(255, 255, 255));
                leaderButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                      for(JButton btn : btnArr) {
                        btn.setEnabled(true);
                      }
                      //curTeam=Misc.searchTeam(teams,memberButton.getText());
                      btnTeam.setEnabled(true);
                      unassignedButton.setEnabled(true);
                      leaderButton.setEnabled(false);

                      curTasks=Task.filter_(allTasks,leaderButton.getText()); 
                      comp=Task.filter_(curTasks,1).size();
                      pend=Task.filter_(curTasks,0).size();
                      all=curTasks.size();
                      aging=Task.filter_(curTasks).size();

                      btnAll.setText("All Tasks("+all+")");
                      btnComp.setText("Completed("+comp+")");
                      btnPend.setText("Pending("+pend+")");
                      btnAge.setText("Overdue("+aging+")");

                      populateTable(curTasks,model);
                    }
                });
                btnArr.add(leaderButton);
                panelTeams.add(leaderButton);



        for (Member m : members) {
            JButton memberButton = new JButton(m.getName());
            memberButton.setBackground(new Color(183, 224, 249));
            memberButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  for(JButton btn : btnArr) {
                    btn.setEnabled(true);
                  }
                  //curTeam=Misc.searchTeam(teams,memberButton.getText());
                  btnTeam.setEnabled(true);
                  memberButton.setEnabled(false);

                  curTasks=Task.filter_(allTasks,memberButton.getText()); 
                  comp=Task.filter_(curTasks,1).size();
                  pend=Task.filter_(curTasks,0).size();
                  all=curTasks.size();
                  aging=Task.filter_(curTasks).size();

                  btnAll.setText("All Tasks("+all+")");
                  btnComp.setText("Completed("+comp+")");
                  btnPend.setText("Pending("+pend+")");
                  btnAge.setText("Overdue("+aging+")");

                  populateTable(curTasks,model);
                }
            });
            btnArr.add(memberButton);
            panelTeams.add(memberButton);
        }

        JPanel panelTasks = new JPanel();
        panelTasks.setBackground(new Color(255, 255, 255));
        panelTasks.setBounds(133, 26, 520, 373);
        contentPane.add(panelTasks);
        panelTasks.setLayout(null);

        //if (teams.size()>0)
        //{
        btnComp = new JButton("Completed("+comp+")");
        btnComp.setBackground(new Color(255, 255, 255));
        btnComp.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            populateTable(Task.filter_(curTasks,1),model);
          }
        });
        btnComp.setBounds(130, 11, 131, 23);
        panelTasks.add(btnComp);

        btnAll = new JButton("All Tasks("+all+")");
        btnAll.setBackground(new Color(255, 255, 255));
        btnAll.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            populateTable(curTasks,model);
          }
        });
        btnAll.setBounds(9, 11, 111, 23);
        panelTasks.add(btnAll);

        btnPend = new JButton("Pending("+pend+")");
        btnPend.setBackground(new Color(255, 255, 255));
        btnPend.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            populateTable(Task.filter_(curTasks,0),model);
          }
        });
        btnPend.setBounds(274, 11, 117, 23);
        panelTasks.add(btnPend);

        btnAge = new JButton("Overdue("+aging+")");
        btnAge.setBackground(new Color(255, 255, 255));
        btnAge.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            populateTable(Task.filter_(curTasks),model);
          }
        });
        btnAge.setBounds(401, 11, 109, 23);
        panelTasks.add(btnAge);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setEnabled(false);
        scrollPane.setBounds(15, 45, 490, 235);
        panelTasks.add(scrollPane);
        String[] columns = {"Name", "Task", "Status", "Deadline"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        setColumnWidth(table, 0, 30);
        setColumnWidth(table, 1, 140);
        setColumnWidth(table, 2, 25);
        setColumnWidth(table,3,30);

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

        populateTable(allTasks,model);


        //}

        /*else {
          JLabel lblMsg = new JLabel("No Teams found");
          lblMsg.setBounds(150,120,400,100);
          lblMsg.setFont(new Font("Tahoma", Font.PLAIN, 25));
            panelTasks.add(lblMsg);
        }*/

        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(39, 154, 216));
        panelTop.setBounds(0, 0, 653, 26);
        contentPane.add(panelTop);
        panelTop.setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Frame12 frame12 = new Frame12(team.leader);
				frame12.setVisible(true);

				// Close the current frame
				dispose();
        	}
        });
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.setBounds(0, 0, 73, 26);
        panelTop.add(btnBack);
    }

    public static void populateTable(ArrayList<Task<?>> tasks, DefaultTableModel model) {
        model.setRowCount(0); // Clear existing rows

        for (Task<?> task : tasks) {
            Object[] rowData = {
                    task.user != null ? task.user.getName() : "Unassigned",
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

    @SuppressWarnings("unused")
	private JButton findButtonByText(ArrayList<JButton> buttonList, String searchText) {
        for (JButton button : buttonList) {
            if (button.getText().equals(searchText)) {
                return button; // Found the button with the specified text
            }
        }
        return null; // Button with the specified text not found
    }
}