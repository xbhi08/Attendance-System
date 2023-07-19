package functions;
import java.awt.Color;
import java.awt.Font;

/**
 * It's a class that contains static variables that are used to store the database connection
 * information
 */
public class ENV {
    public static String DB_ConnectionURL="jdbc:mysql://localhost:3306/attendence";
    public static String DB_User="root";
    public static String DB_Password="";
    public static String JBDC_Classname="com.mysql.jdbc.Driver";
    public static Color BG_Color = new Color(96, 137, 204);
    public static Color BG_ColorPale = new Color(191, 211, 245);
    public static Font Font_18 = new Font("Lato", Font.PLAIN, 16);
    public static Font Font_18_Bold = new Font("Lato", Font.BOLD, 16);
    public static Font Font_20_Bold = new Font("Lato", Font.BOLD, 20);
}
