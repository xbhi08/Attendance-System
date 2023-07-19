package gui.admin;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import functions.Admin;
import functions.DataHandler;
import gui.components.DisplayTable;
import java.sql.ResultSet;

public class ViewStudAttendanceDetails extends JFrame {
    Admin admin = new Admin();
    String [] colNames = {"Date", "Attendance"};
    DefaultTableModel tableModel = new DefaultTableModel();
    DisplayTable displayTable = new DisplayTable(tableModel);
    String sId;
    String cId;

    public ViewStudAttendanceDetails(String sId, String cId) { 
        super("Attendance Details");
        this.sId = sId;
        this.cId = cId;
        setTableContent();
        addComponents();
        setSize(400, 500);
        setVisible(true);
    }   
    
    public void setTableContent() {
        for(int i=0; i<colNames.length; i++) {
            tableModel.addColumn(colNames[i]);
        }

        admin.viewAttendanceInfo(new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        tableModel.insertRow(res.getRow()-1, new Object[] { res.getString(1), res.getString(2)  });
                    }
                } catch (Exception exception){
                    System.out.println(exception);
                } 
            } 
        }, cId, sId);    
    }
    
    public void addComponents() {
        add(displayTable.scrollableTable());
    }

}
