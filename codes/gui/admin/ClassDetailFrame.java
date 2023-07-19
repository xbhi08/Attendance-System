package gui.admin;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import functions.DataHandler;
import functions.ENV;
import functions.Helper;
import gui.components.DisplayTable;
import gui.components.HeaderLabel;
import gui.components.SearchBar;

import java.awt.Dimension;
import java.awt.GridLayout;

public class ClassDetailFrame extends JFrame implements ActionListener {
    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    DefaultTableModel tableModel = new DefaultTableModel();
    DisplayTable classTable = new DisplayTable(tableModel);
    JButton generateReportBtn = new JButton("Generate report");
    JButton defaulterListBtn = new JButton("Defaulters list");
    String [] colNames = {"Student Id", "Student Name", "Presence"};

    String className = "";
    String classId = "";
    String dateInput = "";
    SearchBar dateSearch = new SearchBar(this);

    public ClassDetailFrame(String classId, String className, String currentDate) {
        super();
        this.className = className.toUpperCase();
        this.classId = classId;
        this.dateInput = currentDate;
        generateReportBtn.addActionListener(this);
        defaulterListBtn.addActionListener(this); 
        
        setSize(1000, 800);
        addComponents();  
        addClassTable(classId, currentDate, "");
        add(body);
        setVisible(true);   
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void addComponents() {
        JPanel panel = new JPanel(new GridLayout(2,1));

        JPanel optionsPanel = new JPanel();
        JLabel dateLabel = new JLabel("Enter date(yyyy-mm-dd) ");
        dateLabel.setFont(ENV.Font_18);
        optionsPanel.setBackground(ENV.BG_ColorPale);
        dateSearch.setBackground(ENV.BG_ColorPale);
        optionsPanel.add(dateLabel);
        optionsPanel.add(dateSearch);

        defaulterListBtn.setPreferredSize(new Dimension(200, 30));
        defaulterListBtn.setFont(ENV.Font_18);
        generateReportBtn.setPreferredSize(new Dimension(150, 30));
        generateReportBtn.setFont(ENV.Font_18);
        optionsPanel.add(defaulterListBtn);
        optionsPanel.add(generateReportBtn);

        panel.add(new HeaderLabel(className.toUpperCase() + " class", 20));
        panel.add(optionsPanel);
        body.setTopComponent(panel);
                
        for(int i=0; i<colNames.length; i++) {
            tableModel.addColumn(colNames[i]);
        }
        body.setBottomComponent(classTable.scrollableTable());
    }

    boolean dataFound;

    public void addClassTable(String classId, String currentDate, String additionalQuery) {
        dataFound = false;
        tableModel = (DefaultTableModel) classTable.getModel();
        tableModel.setRowCount(0);
        String sqlQuery = "SELECT A.class_id, S.name, A.attendance FROM attendance_taken A, student S WHERE A.stud_id=S.id AND A.class_id="+classId+" AND A.dateTaken='"+currentDate+"'";
        if(additionalQuery.length()>0) sqlQuery+=additionalQuery;
        Helper.simpleQuery(sqlQuery, new DataHandler() {
            @Override
            public void display(ResultSet res) {
                try {
                    while(res.next()) {
                        dataFound = true;
                        tableModel.insertRow(0, new Object[] { res.getString(1), res.getString(2), res.getString(3)});
                    }
                    
                } catch (Exception exception){
                    System.out.println(exception);
                } 
            } 
        });

        if (!dataFound) {
            String msg = "Class: " + className + "\nDate: " + currentDate + "\nNo attendance found.";
            JOptionPane.showMessageDialog(this, msg,"No attendance found",JOptionPane.WARNING_MESSAGE);  ;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("search")) {
            dateInput = dateSearch.getSearchText();
            addClassTable(classId, dateInput, "");
            return;
        }
        if(e.getActionCommand().equals("Generate report")) {
            new GenerateReportFrame(classId);
            return;
        }
        if(e.getActionCommand().equals("Defaulters list")) {
            if(dateSearch.getSearchText().length()>0) dateInput = dateSearch.getSearchText();
            addClassTable(classId, dateInput, "AND A.attendance='absent'");
            return;
        }
    }
}
