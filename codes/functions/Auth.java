package functions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * It's a class that handles the authentication of a user
 */

public class Auth {

    private String userType; // Holds user type: admin, student, faculty
    public String errorMessage; // Holds any error generated during authentication
    public boolean authenticated; 
    public String userId;
    public String userName;
    public String userEmail;
    public String userContactNum;
    public String userAddress;
    public String userGender;

    Auth(String type) {
        this.userType = type;
        this.authenticated = false;
    }

    /**
     * It takes in a userId and password, and checks if the user is in the database
     * 
     * @param id userId
     * @param pwd the password entered by the user
     * @return The return type is boolean.
     */
    public boolean login(String id, String pwd) {
        if (id.equals("") || pwd.equals("")) {
            errorMessage = "Please fill out all informations";
            return false;
        }
        String sqlQuery = "SELECT * FROM " + userType + " WHERE id=?";
        
		try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);

            PreparedStatement query = conn.prepareStatement(sqlQuery);
            query.setString(1, id);
            ResultSet resultSet = query.executeQuery();

            if(resultSet.next()) {
                if(resultSet.getString("password").equals(pwd)) {
                    authenticated = true;
                    userId = resultSet.getString(1);
                    userName = resultSet.getString(2);
                    userEmail = resultSet.getString(3);
                    userContactNum = resultSet.getString(5);
                    userAddress = resultSet.getString(6);
                    userGender = resultSet.getString(7);
                } else {
                    errorMessage = "Wrong password";
                }
            } else {
                errorMessage = "No " + userType + " with id " + id + " found.";
            }
            
            conn.close();
            resultSet.close();
            query.close();
        } 
        catch (Exception exception) {
            System.out.println(exception);
            errorMessage = "Error logging in. Please try again.";
            return false;
        } 

        return authenticated;
    }

   /**
    * Function that logs user out.
    * 
    * @return authentication status of user.
    */
    public boolean logout() {
        authenticated = false;
        return authenticated;
    }

   /**
    * This function returns the error message that is set when the user is not authenticated
    * 
    * @return The error message.
    */
    public String getAuthError() {
        return this.errorMessage;
    }
}