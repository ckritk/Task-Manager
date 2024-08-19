package test;

import javax.swing.*;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Label;

class Login extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton leaderRadio;
    private JRadioButton memberRadio;
    private JRadioButton trackerRadio;
    private ButtonGroup roleButtonGroup;

    private JButton loginButton;
    private JButton signUpButton;

    private int loggedInUserID = -1;
    private String loggedInRole = "";

    public Login() {

        setTitle("Login Form");
        setSize(500, 450);
        getContentPane().setBackground(new Color(39, 154, 216));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null); // Set null layout

        JLabel usernameLabel = new JLabel("User Name");
        usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        usernameLabel.setForeground(new Color(255, 255, 255));
        usernameLabel.setBounds(95, 133, 123, 25);
        getContentPane().add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(228, 135, 151, 25);
        getContentPane().add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(new Color(255, 255, 255));
        passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        passwordLabel.setBounds(95, 169, 80, 25);
        getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(228, 171, 151, 25);
        getContentPane().add(passwordField);

        JLabel loginAsLabel = new JLabel("Login as");
        loginAsLabel.setForeground(new Color(255, 255, 255));
        loginAsLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        loginAsLabel.setBounds(95, 224, 80, 25);
        getContentPane().add(loginAsLabel);

        leaderRadio = new JRadioButton("Leader");
        leaderRadio.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        leaderRadio.setForeground(new Color(255, 255, 255));
        leaderRadio.setBackground(new Color(39, 154, 216));
        leaderRadio.setBounds(228, 224, 123, 25);
        getContentPane().add(leaderRadio);

        memberRadio = new JRadioButton("Member");
        memberRadio.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        memberRadio.setForeground(new Color(255, 255, 255));
        memberRadio.setBackground(new Color(39, 154, 216));
        memberRadio.setBounds(228, 252, 123, 25);
        getContentPane().add(memberRadio);

        trackerRadio = new JRadioButton("Tracker");
        trackerRadio.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        trackerRadio.setForeground(new Color(255, 255, 255));
        trackerRadio.setBackground(new Color(39, 154, 216));
        trackerRadio.setBounds(228, 280, 123, 25);
        getContentPane().add(trackerRadio);

        roleButtonGroup = new ButtonGroup();
        roleButtonGroup.add(leaderRadio);
        roleButtonGroup.add(memberRadio);
        roleButtonGroup.add(trackerRadio);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        loginButton.setBounds(228, 320, 90, 41);
        loginButton.addActionListener(this);
        getContentPane().add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(255, 255, 255));
        signUpButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        signUpButton.setBounds(328, 320, 98, 41);
        signUpButton.addActionListener(this);
        getContentPane().add(signUpButton);
        
        Label label = new Label("Task Manager");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Dialog", Font.PLAIN, 50));
        label.setBounds(86, 34, 453, 82);
        getContentPane().add(label);
    }


    private boolean validateLogin(String username, String password, String role) {
        boolean isValid = false;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());}
            catch (Exception e) { e.printStackTrace(); }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager", "root", "Root09");
            String query = "SELECT * FROM Users WHERE Name = ? AND Password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = true;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isValid;
    }

    private int getUserID(String username) {
        int userID = -1;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());}
            catch (Exception e) { e.printStackTrace(); }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager", "root", "Root09");
            String query = "SELECT UserID FROM Users WHERE Name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userID = resultSet.getInt("UserID");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userID;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String selectedRole = "";

        if (leaderRadio.isSelected()) {
            selectedRole = "Leader";
        } else if (memberRadio.isSelected()) {
            selectedRole = "Member";
        } else if (trackerRadio.isSelected()) {
            selectedRole = "Tracker";
        }

        if (e.getSource() == loginButton) {
            if (validateLogin(username, password, selectedRole)) {
                JOptionPane.showMessageDialog(this, "Successfully logged in!");
                loggedInUserID = getUserID(username);
                loggedInRole = selectedRole;

                dispose();

                int userID = loggedInUserID;
                String userRole = loggedInRole;

              if (userRole.equals("Member")) {
                  EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                Misc.init();
                                Member member = Misc.search(Misc.members,userID); // Fetch the specific member based on the logged-in user's ID
                                MemberHome frame = new MemberHome(member);
                                frame.setVisible(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else if (userRole.equals("Tracker")) 
                {
                	EventQueue.invokeLater(new Runnable() {
            			public void run() {
            				try {
            					
            					Misc.init();
            					System.out.println("Logged in as tracker!");
            					Tracker tracker = Misc.search(Misc.trackers, getUserID(username));
               				
            					TrackerHome frame = new TrackerHome(tracker);
            					frame.setVisible(true);
            					
            					
            				} catch (Exception e) {
            					e.printStackTrace();
            				}
            			}
            		});
                }
                else
                {
                	EventQueue.invokeLater(new Runnable() {
            			public void run() {
            				try {
            					
            					Misc.init();
            					
            					Leader leader = Misc.search(Misc.leaders, getUserID(username));
            					System.out.println(leader);
            					leader.displayLeaderDetails();
            					System.out.println(leader.teams);
            					LeaderHomePage<Leader> frame = new LeaderHomePage<Leader>(leader);
            					frame.setVisible(true);
            					
            					
            				} catch (Exception e) {
            					e.printStackTrace();
            				}
            			}
            		});
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials or role. Login failed.");
            }
        } else if (e.getSource() == signUpButton) {
            openSignUpPage();
        }
    }

    private void openSignUpPage() {
        SwingUtilities.invokeLater(() -> new Signup());
    }
    
    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
    }
}

