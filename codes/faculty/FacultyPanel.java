package faculty;


import functions.DataHandler;
import functions.ENV;
import functions.Faculty;
import functions.Helper;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import gui.components.HeaderLabel;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class FacultyPanel extends JFrame{
    JSplitPane header = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    Faculty facDetails;
    
    JButton attendanceBtn, editDetailsBtn, viewAttendanceBtn; 
    
	String classname;
    
    public FacultyPanel(Faculty fac) {
        super("Faculty Panel");
        facDetails = fac;
        addComponents(facDetails.userId, facDetails.userName, facDetails.userEmail, facDetails.userGender, facDetails.userAddress, facDetails.userContactNum);
        setSize(1000, 600);
        setVisible(true);
    }

    public void addComponents(String id, String name, String email, String gender, String address, String contact) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(ENV.BG_ColorPale);
        detailsPanel.setLayout(new GridLayout(3,2, 10, 10) );
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        detailsPanel.add(label("Id: " + id));
        detailsPanel.add(label("Address: " + address));
        detailsPanel.add(label("Name: " + name));
        detailsPanel.add(label("Contact number: " + contact));
        detailsPanel.add(label("Email: " + email));
        detailsPanel.add(label("Gender: " + gender));
        
        
        //JPanel editDetailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //editDetailsPanel.setBackground(ENV.BG_ColorPale);
        //detailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        
        JButton invisibleBtn = new JButton("Invisible button"); 
        invisibleBtn.setVisible(false);
        detailsPanel.add(invisibleBtn);
        
        editDetailsBtn = new JButton("Edit personal details"); 
        editDetailsBtn.setFocusPainted(false); //removes border around jbutton's text
        editDetailsBtn.setFont(ENV.Font_18);
        editDetailsBtn.addActionListener(new EditDetailsBtnHandler());
        editDetailsBtn.setPreferredSize(new Dimension(150, 40));      
        //editDetailsPanel.add(editDetailsBtn);
        //detailsPanel.add(editDetailsPanel);
        detailsPanel.add(editDetailsBtn);
        
        header.setTopComponent(new HeaderLabel("Faculty Panel", 25));
        header.setBottomComponent(detailsPanel);
        body.setTopComponent(header);       
        body.setBottomComponent(classesAssignedComponent());
        add(body);
    }

    public JPanel classesAssignedComponent() {
        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBackground(ENV.BG_ColorPale);
        
        JLabel classTitle = label("Classes");
        classTitle.setBounds(20, 5, 200, 30);

        ArrayList<String[]> classes = facDetails.getClassesAssigned();

        for (int i = 0; i < classes.size(); i++) {
            JPanel classPanel = new JPanel();
            classPanel.setBorder(BorderFactory.createLineBorder(ENV.BG_Color));
            classPanel.setBackground(ENV.BG_ColorPale);
            JLabel name = label(classes.get(i)[1]);
            name.setPreferredSize(new Dimension(300, 40));
            attendanceBtn = new JButton("Take attendance");
            attendanceBtn.setActionCommand(classes.get(i)[0]);
            attendanceBtn.addActionListener(new AttendanceBtnHandler());
            attendanceBtn.setPreferredSize(new Dimension(150, 40));
            
            viewAttendanceBtn = new JButton("View attendance");
            viewAttendanceBtn.setActionCommand(classes.get(i)[0]);
            viewAttendanceBtn.addActionListener(new ViewAttendanceBtnHandler());
            viewAttendanceBtn.setPreferredSize(new Dimension(150, 40));
            
            
            classPanel.add(name);
            classPanel.add(attendanceBtn);
            classPanel.add(viewAttendanceBtn);
            classPanel.setBounds(20, (i+1) * 50, 620, 50);
            container.add(classPanel);
        }

        container.add(classTitle);
        return container;   
    }

    public JLabel label(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(ENV.Font_18_Bold);
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        return label;
    }

    public class AttendanceBtnHandler implements ActionListener{
	    public void actionPerformed(ActionEvent e) {	    	
	    		String classId = e.getActionCommand();
	            new MarkAttendance(facDetails.getStudEnrolled(classId), facDetails, classId);
	            
	    }
		        
	    
	}
    
    
    public class ViewAttendanceBtnHandler implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		String classId = e.getActionCommand();
    		String sqlQuery = "SELECT name FROM class_details WHERE id = " + classId;
    		Helper.simpleQuery(sqlQuery, new DataHandler() {
	            @Override
	            public void display(ResultSet res) {
	                try {
	                    while(res.next()) {
	                    	classname = res.getString(1);
	                        
	                    }
	                    
	                } catch (Exception exception){
	                    System.out.println(exception);
	                } 
	            } 
    		});
            new ViewAttendance(classId, classname); 
            
    	}
    }
    
    
    public class EditDetailsBtnHandler implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		new EditPersonalDetails(facDetails);
    	}
    }
}