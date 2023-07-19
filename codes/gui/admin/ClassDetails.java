package gui.admin;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import functions.Admin;
import functions.DataHandler;
import functions.ENV;
import gui.components.DisplayTable;
import gui.components.SearchBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.BorderLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ClassDetails extends JPanel implements ActionListener, ListSelectionListener{
    Admin admin = new Admin();
    String [] colNames = {"", "Class Id", "Class Name", "Assigned Faculty","Faculty Email"};
    
    DefaultTableModel tableModel = new DefaultTableModel();
    SearchBar searchbar =  new SearchBar(this);
    DisplayTable displayTable = new DisplayTable(tableModel, this);
    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
  
    public ClassDetails() { 
        searchbar.setBackground(ENV.BG_ColorPale);
        setLayout(new BorderLayout(0, 20));
        setTableContent();
        addComponents();
    }   
    
    public void setTableContent() {
        for(int i=0; i<colNames.length; i++) {
            tableModel.addColumn(colNames[i]);
        }

        admin.getClassInfo(new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        tableModel.insertRow(res.getRow()-1, new Object[] {res.getRow(), res.getString(1), res.getString(2), res.getString(3), res.getString(4) });
                    }
                } catch (Exception exception){
                    System.out.println(exception);
                } 
            } 
        });  
    }
    
    public void addComponents() {
        add(searchbar, BorderLayout.NORTH);
        add(displayTable.scrollableTable(), BorderLayout.CENTER);
    }

    public void updateTable(String searchData) {
        tableModel = (DefaultTableModel) displayTable.getModel();
        tableModel.setRowCount(0);
        admin.getClassInfo(new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        tableModel.insertRow(res.getRow()-1, new Object[] { res.getRow(), res.getString(1), res.getString(2), res.getString(3), res.getString(4) });
                    }
                } catch (Exception exception){
                    System.out.println(exception);
                } 
            } 
        }, searchData);  
    }

    @Override
    public void actionPerformed(ActionEvent e){  
        updateTable(searchbar.getSearchText());  
        searchbar.clearText();
    }

    // Added a count because for some reason this event is firing twice
    // and causing 2 ClassDetailFrame to be rendered.
    int count=0;
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (displayTable.getSelectedRow() > -1) {
            if (count%2==0) {
                String id = displayTable.getValueAt(displayTable.getSelectedRow(), 1).toString();
                String name = displayTable.getValueAt(displayTable.getSelectedRow(), 2).toString();
                new ClassDetailFrame(id, name, dateFormat.format(new Date()));
            }
            count++;
        }
    }
}
