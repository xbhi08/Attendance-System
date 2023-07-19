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

public class StudDetails extends JPanel implements ActionListener, ListSelectionListener{
    Admin admin = new Admin();
    String [] colNames = {"", "Student Id", "Student Name", "Student Email", "Contact Number", "Address", "Gender"};
    
    DefaultTableModel tableModel = new DefaultTableModel();
    SearchBar searchbar =  new SearchBar(this);
    DisplayTable displayTable = new DisplayTable(tableModel, this);
  
    public StudDetails() { 
        searchbar.setBackground(ENV.BG_ColorPale);
        setLayout(new BorderLayout(0, 20));
        setTableContent();
        addComponents();
    }   
    
    public void setTableContent() {
        for(int i=0; i<colNames.length; i++) {
            tableModel.addColumn(colNames[i]);
        }

        admin.getStudInfo(new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        tableModel.insertRow(res.getRow()-1, new Object[] { res.getRow() ,res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6) });
                    }
                } catch (Exception exception){
                    System.out.println(exception);
                } 
            } 
        });  
    }
    
    public void addComponents() {
        displayTable.getColumn(colNames[0]).setPreferredWidth(20);
        add(searchbar, BorderLayout.NORTH);
        add(displayTable.scrollableTable(), BorderLayout.CENTER);
    }

    public void updateTable(String searchData) {
        tableModel = (DefaultTableModel) displayTable.getModel();
        tableModel.setRowCount(0);
        admin.getStudInfo(new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        tableModel.insertRow(res.getRow()-1, new Object[] {res.getRow(), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6) });
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
                String email = displayTable.getValueAt(displayTable.getSelectedRow(), 3).toString();
                String contact = displayTable.getValueAt(displayTable.getSelectedRow(), 4).toString();
                String address = displayTable.getValueAt(displayTable.getSelectedRow(), 5).toString();
                String gender = displayTable.getValueAt(displayTable.getSelectedRow(), 6).toString();
                new StudDetailFrame(id, name, email, gender, address, contact);
            }
            count++;
        }
    }
}
