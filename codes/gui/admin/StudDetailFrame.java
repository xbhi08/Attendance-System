package gui.admin;

import javax.swing.table.DefaultTableModel;

import functions.DataHandler;
import functions.ENV;
import functions.Helper;

import java.awt.*;
import java.sql.ResultSet;

import javax.swing.*;
import gui.components.DisplayTable;
import gui.components.HeaderLabel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudDetailFrame extends JFrame implements ListSelectionListener {
    JSplitPane header = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    DefaultTableModel tableModel = new DefaultTableModel();
    DisplayTable classTable = new DisplayTable(tableModel, this);
    String [] colNames = {"Class Id", "Class Name", "Assigned Faculty"};
    String sId;
    
    public StudDetailFrame(String id, String name, String email, String gender, String address, String contact) {
        super("Student Details");
        this.sId = id;
        setTableContent(id);
        addComponents(id, name, email, gender, address, contact);
        setSize(1000, 600);
        setVisible(true);
    }

    public void addComponents(String id, String name, String email, String gender, String address, String contact) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(ENV.BG_ColorPale);
        detailsPanel.setLayout(new GridLayout(3,2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        detailsPanel.add(label("Id: " + id));
        detailsPanel.add(label("Address: " + address));
        detailsPanel.add(label("Name: " + name));
        detailsPanel.add(label("Contact number: " + contact));
        detailsPanel.add(label("Email: " + email));
        detailsPanel.add(label("Gender: " + gender));
        header.setTopComponent(new HeaderLabel("Student Details", 25));
        header.setBottomComponent(detailsPanel);
        body.setTopComponent(header);
        body.setBottomComponent(classTable.scrollableTable());
        add(body);
    }

    public void setTableContent(String id) {
        String sqlQuery = "SELECT D.id, D.name, F.name FROM class_details D, class C, faculty F WHERE D.id=C.class_id AND F.id=D.faculty_id AND C.stud_id="+id;
        
        for(int i=0; i<colNames.length; i++) {
            tableModel.addColumn(colNames[i]);
        }

        Helper.simpleQuery(sqlQuery, new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        tableModel.insertRow(0, new Object[] { res.getString(1), res.getString(2), res.getString(3)});
                    }
                } catch (Exception exception){
                    System.out.println(exception);
                } 
            } 
        });
    }

    public JLabel label(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(ENV.Font_18_Bold);
        return label;
    }

    // Added a count because for some reason this event is firing twice
    // and causing 2 ClassDetailFrame to be rendered.
    int count=0;
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (classTable.getSelectedRow() > -1) {
            if (count%2==0) {
                String cId = classTable.getValueAt(classTable.getSelectedRow(), 0).toString();
                new ViewStudAttendanceDetails(sId, cId);
            }
            count++;
        }
    }
}
