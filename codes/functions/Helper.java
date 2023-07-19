package functions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;   
import java.text.SimpleDateFormat;
import java.util.Date;  
    
public class Helper {
    /**
     * It generates a random string of length passwordLength, using the characters in the string chars
     * 
     * @param passwordLength The length of the password you want to generate.
     * @return A random string of characters.
     */
    public static String generatePassword(int passwordLength) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
        String password = "";
		for (int i = 0; i < passwordLength; i++) {
            password += chars.charAt(rnd.nextInt(chars.length()));
        }
        return password;
	}

    /**
     * It connects to the database, executes a query, and returns the result
     * 
     * @return The next available ID number.
     */
    public static int generateNewId(String tableName) {
        int nextId = (int)Math.floor(Math.random()*(300000-100000+1)+300000);

        try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);

            Statement query = conn.createStatement();
            ResultSet result = query.executeQuery("SELECT MAX(id) + 1 FROM " + tableName);
            
            while(result.next()) {
                nextId = result.getInt(1)>0 ? result.getInt(1) : nextId;
            } 

            result.close();
            query.close();
            conn.close();
        } 
        catch (Exception exception) {
            System.out.println(exception);  
        } 

        return nextId;
    }

    /**
     * It takes a SQL query and a DataHandler object as parameters, connects to the database, executes
     * the query, and passes the result to the DataHandler object
     * 
     * @param sqlQuery The SQL query to be executed.
     * @param data a class that implements the DataHandler interface.
     */
    public static void simpleQuery(String sqlQuery, DataHandler data) {
        try {
			Class.forName(ENV.JBDC_Classname);
			Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);

            Statement query = conn.createStatement();
            ResultSet result = query.executeQuery(sqlQuery);
            data.display(result);

            conn.close();
            query.close();
            result.close();
        } 
        catch (Exception exception) {
            System.out.println(exception);
        } 
    }

    /**
     * It takes in a directory path, a class id, and two dates, and generates an excel file with the
     * attendance of the students in the class between the two dates
     * 
     * @param directoryPath The directory where the file will be saved
     * @param classId The class id of the class you want to generate the report for.
     * @param dateFrom the date from which the report will be generated
     * @param dateTo the date of the last attendance taken
     */
    public static boolean generateReport(String directoryPath, String classId, String dateFrom, String dateTo) {
        try  { 
            SimpleDateFormat dFormat = new SimpleDateFormat ("yyyy-MM-dd__HH.mm.ss");
            String filename = String.format("%s/attendance%s_%s.xlsx", directoryPath, classId, dFormat.format(new Date()));  
            XSSFWorkbook workbook = new XSSFWorkbook();   
            XSSFFont redFont = workbook.createFont(); 
            redFont.setBold(true);
            redFont.setColor(IndexedColors.RED.index); 
            XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(redFont);
            
            String sqlQuery = "SELECT A.stud_id, S.name, A.attendance, A.dateTaken FROM attendance_taken A, student S WHERE A.stud_id=S.id AND A.class_id=? AND dateTaken BETWEEN ? AND ? ORDER BY dateTaken DESC;";
            Connection conn = DriverManager.getConnection(ENV.DB_ConnectionURL, ENV.DB_User, ENV.DB_Password);
            PreparedStatement query = conn.prepareStatement(sqlQuery);
            query.setInt(1, Integer.parseInt(classId));
            query.setString(2, dateFrom);
            query.setString(3, dateTo);
            ResultSet res = query.executeQuery();
            
            String currentTime = dateTo;
            XSSFSheet sheet = workbook.createSheet(currentTime);   
            XSSFRow rowhead = sheet.createRow((short)0);  
            rowhead.createCell(0).setCellValue("Student Id");
            rowhead.createCell(1).setCellValue("Student Name");  
            rowhead.createCell(2).setCellValue("Attendance");  
            while(res.next()) {
                if(!res.getString(4).equals(currentTime)) {   
                    currentTime = res.getString(4);
                    sheet = workbook.createSheet(currentTime);   
                    rowhead = sheet.createRow((short)0);  
                    rowhead.createCell(0).setCellValue("Student Id");
                    rowhead.createCell(1).setCellValue("Student Name");  
                    rowhead.createCell(2).setCellValue("Attendance");
                }
                rowhead = sheet.createRow((short) res.getRow());  
                rowhead.createCell(0).setCellValue(res.getInt(1));
                rowhead.createCell(1).setCellValue(res.getString(2));  
                XSSFCell cell = rowhead.createCell(2);
                cell.setCellValue(res.getString(3));
                if(res.getString(3).equals("absent")) cell.setCellStyle(style);
            }

            
            FileOutputStream fileOut = new FileOutputStream(filename);  
            workbook.write(fileOut); 
            query.close();
            conn.close();
            res.close();
            fileOut.close();   
            workbook.close();   
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
