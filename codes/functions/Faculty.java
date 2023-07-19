package functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Faculty extends Auth {

    public Faculty() {
        super("Faculty");
    }

    public ArrayList<String[]> getClassesAssigned() {
        String sqlQuery = "SELECT * FROM class_details WHERE faculty_id=?";
        ArrayList<String[]> classes = new ArrayList<String[]>();

        try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);
            PreparedStatement query = conn.prepareStatement(sqlQuery);
            query.setInt(1, Integer.parseInt(super.userId));
            ResultSet result = query.executeQuery();

            while(result.next()) {
                String[] res = {result.getString(1), result.getString(2)};
                classes.add(res);
            }
            conn.close();
            query.close();
            return classes;
        } catch(Exception e) {
            System.out.println(e);
            return classes;
        }
    }

    public ArrayList<String[]> getStudEnrolled(String classId) {
        String sqlQuery = "SELECT S.id, S.name FROM class C, student S WHERE C.stud_id = S.id AND C.class_id=?";
        ArrayList<String[]> students = new ArrayList<String[]>();

        try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);
            PreparedStatement query = conn.prepareStatement(sqlQuery);
            query.setString(1, classId);
            ResultSet result = query.executeQuery();

            while(result.next()) {
                String[] res = {result.getString(1), result.getString(2)};
                students.add(res);
            }
            conn.close();
            query.close();
            return students;
        } catch(Exception e) {
            System.out.println(e);
            return students;
        }
    }
    
    public int simpleUpdate(String sqlQuery) {
    	try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);

            Statement query = conn.createStatement();
            int row = 0;
            row = query.executeUpdate(sqlQuery);
            //data.display(result);

            conn.close();
            query.close();
            //result.close();
            return row;
        } 
        catch (Exception exception) {
            System.out.println(exception);
            return 0;
        } 
    }

    public boolean addAttendanceRecord(String classId, ArrayList<String[]> studRecords) {
        String sqlQuery = "INSERT INTO attendance_details(class_id) VALUES(?)";
        String updateQuery = "INSERT INTO attendance(attendance_id, stud_id, attendance) VALUES(?,?,?)";

        try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);
            PreparedStatement query = conn.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            query.setString(1, classId);

            query.executeUpdate();
            ResultSet generatedKeys = query.getGeneratedKeys();
            if (generatedKeys.next()) {
                int attendanceId = generatedKeys.getInt(1);
                query.close();
                generatedKeys.close();
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                conn.setAutoCommit(false);
                for(int i=0; i<studRecords.size(); i++) {
                    updateStmt.setInt(1, attendanceId);
                    updateStmt.setString(2, studRecords.get(i)[0]);
                    updateStmt.setString(3, studRecords.get(i)[1]);
                    updateStmt.addBatch();
                }
                updateStmt.executeBatch();
                conn.commit();
                conn.setAutoCommit(true);
                conn.close();
                updateStmt.close();
                return true;
            }
            return false;
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
