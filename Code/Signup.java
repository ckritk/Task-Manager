package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

public class Signup extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
  
    public Signup() {
    	getContentPane().setBackground(new Color(39, 154, 216));
   
        setTitle("Sign Up Form");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(255, 255, 255));
        emailLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        emailLabel.setBounds(50, 201, 80, 25);
        getContentPane().add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(241, 203, 200, 25);
        getContentPane().add(emailField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(255, 255, 255));
        nameLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        nameLabel.setBounds(50, 237, 80, 25);
        getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(241, 239, 200, 25);
        getContentPane().add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(255, 255, 255));
        passwordLabel.setBounds(50, 273, 80, 25);
        getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(241, 275, 200, 25);
        getContentPane().add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(new Color(255, 255, 255));
        confirmPasswordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        confirmPasswordLabel.setBounds(50, 309, 138, 25);
        getContentPane().add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(241, 311, 200, 25);
        getContentPane().add(confirmPasswordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(255, 255, 255));
        signUpButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        signUpButton.setBounds(332, 396, 108, 37);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
        getContentPane().add(signUpButton);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.setBounds(50, 396, 108, 37);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btnBack);
        
        Label label = new Label("Sign-up");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Arial Narrow", Font.PLAIN, 64));
        label.setBounds(140, 29, 319, 88);
        getContentPane().add(label);
        
        Label label_1 = new Label("Make task management easier");
        label_1.setForeground(new Color(255, 255, 255));
        label_1.setFont(new Font("Dialog", Font.PLAIN, 19));
        label_1.setBounds(122, 123, 366, 21);
        getContentPane().add(label_1);

        setVisible(true);
    }

    private void signUp() {
        String email = emailField.getText();
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
            return;
        }

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());}
            catch (Exception e) { e.printStackTrace(); }

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager", "root", "root");

            // Check if email already exists
            String checkQuery = "SELECT * FROM Users WHERE EMail = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet checkResultSet = checkStatement.executeQuery();

            if (checkResultSet.next()) {
                JOptionPane.showMessageDialog(this, "Email already exists. Please use a different email.");
                checkStatement.close();
                connection.close();
                return;
            }

            // Close the ResultSet and PreparedStatement for email check
            checkResultSet.close();
            checkStatement.close();

            // Query to get the maximum user ID
            String maxUserIdQuery = "SELECT MAX(UserID) FROM Users";
            PreparedStatement maxUserIdStatement = connection.prepareStatement(maxUserIdQuery);

            // Execute the query
            ResultSet maxUserIdResultSet = maxUserIdStatement.executeQuery();

            int maxUserId = 0;

            // Retrieve the maximum user ID
            if (maxUserIdResultSet.next()) {
                maxUserId = maxUserIdResultSet.getInt(1);
            }

            // Close the ResultSet and PreparedStatement for maxUserID
            maxUserIdResultSet.close();
            maxUserIdStatement.close();

            // Generate the next user ID
            int nextUserId = maxUserId + 1;

            // Insert user
            String insertQuery = "INSERT INTO Users (UserID, EMail, Name, Password) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, nextUserId);
            insertStatement.setString(2, email);
            insertStatement.setString(3, name);
            insertStatement.setString(4, password);

            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
            }

            // Close the PreparedStatement for user insertion
            insertStatement.close();

            // Close the database connection
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user. Please try again.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup());
    }
}