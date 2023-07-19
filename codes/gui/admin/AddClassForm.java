package gui.admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import functions.Admin;
import functions.DataHandler;
import functions.ENV;
import functions.Helper;
import gui.components.HeaderLabel;

import java.awt.*;

public class AddClassForm extends JPanel implements ActionListener {
	Admin admin = new Admin();
	ArrayList<String> facultyArr = new ArrayList<>();

	HeaderLabel header = new HeaderLabel("Add new class", 20);
	JLabel classnameLabel = new JLabel("CLASSNAME");
	JLabel facultyLabel = new JLabel("FACULTY");

	JComboBox<String> facultyList;

    JTextField classnameField = new JTextField();

    JButton submitButton = new JButton("SUBMIT");
    JButton resetButton = new JButton("RESET");
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	JPanel bg = new JPanel();
	
    public AddClassForm() {
		admin.getAllFaculty(new DataHandler() {
			@Override
			public void display(ResultSet result) {
				try {
					while(result.next()) {
						facultyArr.add(result.getString(1) + "-" + result.getString(2));
					}
				} catch (Exception exception) {
					System.out.println(exception);
				}

			}
		});
		String[] faculties = facultyArr.toArray(new String[0]);
		facultyList = new JComboBox<>(faculties);
		setLayout(null);
        addComponentsToContainer();
		setLocationAndSize();
		addActionEvent();
    }
	
	public void setLocationAndSize() {
		int startX = (int) (screenSize.getWidth() - 390) / 2;
		int startY = 100, containerHeight = 40;

		bg.setBackground(ENV.BG_ColorPale);
		bg.setBounds(startX - 50, containerHeight, 500, 200);

		header.setBounds(startX, containerHeight, 390, 50);
		header.setFont(new Font("Lato", Font.BOLD, 20));

		classnameLabel.setBounds(startX, startY + (containerHeight * 0), 140, 30);
		classnameField.setBounds(startX + 140, startY + (containerHeight * 0), 250, 30);
		
		facultyLabel.setBounds(startX,  startY + (containerHeight * 1), 140, 30);
		facultyList.setBounds(startX + 140,  startY + (containerHeight * 1), 250, 30);
		
		submitButton.setBounds(startX + 85, startY + (containerHeight * 2),100,30);
		resetButton.setBounds(startX + 205, startY + (containerHeight * 2),100,30);
	}

	public void addComponentsToContainer() {
		add(header);
		add(classnameLabel);
		add(classnameField);
		add(facultyLabel);
		add(facultyList);
		add(submitButton);
		add(resetButton);
		add(bg);
    }

	public void addActionEvent() {
       submitButton.addActionListener(this);
       resetButton.addActionListener(this);
   	} 
    
    @Override
    public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submitButton) {
			String classname, faculty;
			faculty = facultyList.getSelectedItem().toString();
			classname = classnameField.getText();

			if(faculty.equals("") || classname.equals("")) {
				JOptionPane.showMessageDialog(this, "Please fill out all fields", "Invalid input", JOptionPane.ERROR_MESSAGE);
			} else {
				int id = Helper.generateNewId("class_details");

				if(admin.addNewClass(id, classname, faculty.split("-")[0])) {
					JOptionPane.showMessageDialog(this, "<html><p>Class was successfully registered</p><p>Class ID: " + id + "</p></html>", 
												"Class Credentials", 
												JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if(e.getSource()==resetButton) clearTextFields();
    }

	public void clearTextFields() {
		classnameField.setText("");
	}
}
