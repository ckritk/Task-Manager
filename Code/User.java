package test;
import java.sql.*;

class User {

    private int id;
    private String email;
    private String name;
    private String pswd;

  // Constructor
    public User() {}

  public User(int id, String email, String name, String pswd) {
    this.id=id;
    this.email = email;
    this.name = name;
    this.pswd = pswd;
  }

  // Getter methods
  public int getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPswd() {
    return pswd;
  }

  // Setter methods
  public void setEmail(String email) {
    this.email = email;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }

  public static class InvalidCredentialsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException(String message) {
        super(message);
    }
  }

  public int login(String providedEmail, String providedPswd) throws InvalidCredentialsException {
      // JDBC connection parameters
      String url = "jdbc:mysql://localhost:3306/taskmanager";
      String username = "root";
      String password = "Root09";

      Connection connection = null;
      PreparedStatement statement = null;
      ResultSet resultSet = null;

      try {
          DriverManager.registerDriver(new com.mysql.jdbc.Driver());
          connection = DriverManager.getConnection(url, username, password);

          String sql = "SELECT UserID FROM Users WHERE EMail = ? AND Password = ?";
          statement = connection.prepareStatement(sql);
          statement.setString(1, providedEmail);
          statement.setString(2, providedPswd);

          resultSet = statement.executeQuery();

          if (resultSet.next()) {
              // Successful login, return the UserID
              return resultSet.getInt("UserID");
          } else {
              // Invalid email or password, throw custom exception
              throw new InvalidCredentialsException("The credentials are wrong");
          }
      } catch (SQLException e) {
          e.printStackTrace();
          throw new InvalidCredentialsException("Error during login");
      } finally {
          try {
              if (resultSet != null) {
                  resultSet.close();
              }
              if (statement != null) {
                  statement.close();
              }
              if (connection != null) {
                  connection.close();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
  }
}


/*public class User {

    public static void main(String[] args) {
        try {

            Users user = new Users("john.doe@example.com", "John Doe", "password123");
            int userID = user.login("john.doe@example.com", "password123");

            if (userID != -1) {
                System.out.println("Login successful. UserID: " + userID);
            } else {
                System.out.println("Invalid credentials");
            }
        } catch (Users.InvalidCredentialsException e) {
            // Handle the exception (print message, log, etc.)
            System.out.println("Error: " + e.getMessage());
        }
    }
}*/

