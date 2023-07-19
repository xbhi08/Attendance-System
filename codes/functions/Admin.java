package functions;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class Admin extends Auth {
    private boolean successful;

    public Admin() {
        super("Admin");
        successful = false;
    }

    /**
     * It takes in a userType, id, password, name, email, address, contactNum, and gender, and inserts
     * them into the database
     * 
     * @param userType the type of user (customer, admin, etc)
     * @param id int
     * @param password String
     * @param name String
     * @param email "test@test.com"
     * @param address "123"
     * @param contactNum +639171234567
     * @param gender String
     * @return The method is returning a boolean value.
     */
    public boolean registerUser(String userType, int id, String password, String name, String email, String address, String contactNum, String gender) {
        String sqlQuery = "INSERT INTO " + userType + " VALUES (?,?,?,?,?,?,?)";
        
		try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);

            PreparedStatement query = conn.prepareStatement(sqlQuery);
            query.setInt(1, id);
            query.setString(2, name);
            query.setString(3, email);
            query.setString(4, password);
            query.setString(5, contactNum);
            query.setString(6, address);
            query.setString(7, gender);

            int result = query.executeUpdate();

            conn.close();
            query.close();
            if(result>0) {
                successful = true;
            } else {
                successful = false;
                errorMessage = "Error registering new user. Please try again later";
            }
        } 
        catch (Exception exception) {
            System.out.println(exception);
            successful = false;
        } 

        return successful;
    }

    /**
     * It adds a new class to the database
     * 
     * @param id int
     * @param name String
     * @param facultyId int
     * @return A boolean value.
     */
    public boolean addNewClass(int id, String name, String facultyId) {
        String sqlQuery = "INSERT INTO class_details VALUES (?,?,?)";
        
		try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);

            PreparedStatement query = conn.prepareStatement(sqlQuery);
            query.setInt(1, id);
            query.setString(2, name);
            query.setString(3, facultyId);

            int result = query.executeUpdate();
            conn.close();
            query.close();
            if(result>0) {
                successful = true;
            } else {
                successful = false;
                errorMessage = "Error registering new user. Please try again later";
            }
        } 
        catch (Exception exception) {
            System.out.println(exception);
            successful = false;
        } 

        return successful;
    }

   /**
    * Get all faculty from the database and pass them to the data handler.
    * 
    * @param dataHandler A class that implements the DataHandler interface.
    */
    public void getAllFaculty(DataHandler dataHandler) {
        String sqlQuery = "SELECT id, name FROM faculty";
        Helper.simpleQuery(sqlQuery, dataHandler);
    }

    /**
     * It takes a DataHandler object as a parameter and calls the simpleQuery function of the Helper
     * class with the sqlQuery and the dataHandler as parameters
     * 
     * @param dataHandler This is a callback function that will be called when the query is executed.
     */
    public void getClassInfo(DataHandler dataHandler) {
        String sqlQuery = "SELECT c.id, c.name, f.name, f.email FROM class_details c, faculty f WHERE c.faculty_id=f.id ";
        Helper.simpleQuery(sqlQuery, dataHandler);
    }

    /**
     * It takes a DataHandler object and a String as parameters, and then it executes a query on the
     * database and passes the result to the DataHandler object
     * 
     * @param dataHandler This is the object of the class that implements the DataHandler interface.
     * @param additionalQuery This is the search query that the user enters in the search box.
     */
    public void getClassInfo(DataHandler dataHandler, String additionalQuery) {
        String sqlQuery = "SELECT c.id, c.name, f.name, f.email FROM class_details c, faculty f WHERE c.faculty_id=f.id ";
        if (additionalQuery.length()>0) {
            sqlQuery += "AND (c.id LIKE '" + additionalQuery + "%' OR c.name LIKE '%" + additionalQuery + "%')";
        }
        Helper.simpleQuery(sqlQuery, dataHandler);
    }

    /**
     * It takes a DataHandler object as a parameter and calls the simpleQuery function of the Helper
     * class with the sqlQuery and the dataHandler as parameters
     * 
     * @param dataHandler This is a callback function that will be called when the query is executed.
     */
    public void getStudInfo(DataHandler dataHandler) {
        String sqlQuery = "SELECT id, name, email, contact, address, gender FROM student";
        Helper.simpleQuery(sqlQuery, dataHandler);
    } 

    /**
     * It takes a DataHandler object and a String as parameters, and then it executes a query and
     * passes the result to the DataHandler object
     * 
     * @param dataHandler This is a class that implements the DataHandler interface. This is the class
     * that will handle the data that is returned from the database.
     * @param additionalQuery This is the search query that the user enters in the search box.
     */
    public void getStudInfo(DataHandler dataHandler, String additionalQuery) {
        String sqlQuery = "SELECT id, name, email, contact, address, gender FROM student ";
        if (additionalQuery.length()>0) {
            sqlQuery += "WHERE (id LIKE '" + additionalQuery + "%' OR name LIKE '" + additionalQuery + "%')";
        }
        Helper.simpleQuery(sqlQuery, dataHandler);
    }

    public void viewAttendanceInfo(DataHandler dataHandler, String cId, String sId) {
        String sqlQuery = "SELECT D.dateTaken, A.attendance FROM attendance A, attendance_details D WHERE A.attendance_id=D.id and D.class_id=" + cId + " AND A.stud_id=" + sId + " ORDER BY D.dateTaken DESC";
        Helper.simpleQuery(sqlQuery, dataHandler);
    }
}