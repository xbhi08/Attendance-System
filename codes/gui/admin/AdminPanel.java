package gui.admin;

import javax.swing.JPanel;
import gui.components.Dashboard;
import gui.components.DisplayArea;

public class AdminPanel extends Dashboard {
    JPanel displayArea = new JPanel();
    static String [] tabNames = { "View attendance details", "View student details", "Register new user", "Add new class" };
    static JPanel [] tabPages = { new ClassDetails(), new StudDetails(), new RegisterUserForm(), new AddClassForm() };
   
    public AdminPanel() {
        super("Admin Panel", new DisplayArea(tabNames, tabPages));
    }
}
