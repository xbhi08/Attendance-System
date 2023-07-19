package gui.admin;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import functions.Admin;
import functions.ENV;
import functions.Helper;

import java.awt.*;

public class RegisterUserForm extends JPanel implements ActionListener {
	Admin admin = new Admin();
	String users[] = {"admin", "student", "faculty"};
	String genders[] = {"male", "female"};
	JLabel header = new JLabel("Register new user", SwingConstants.CENTER);
    JLabel typeLabel = new JLabel("USER TYPE");
	JLabel surnameLabel = new JLabel("SURNAME");
	JLabel othernameLabel = new JLabel("OTHER NAMES");
	JLabel emailLabel = new JLabel("EMAIL");
	JLabel contactLabel = new JLabel("CONTACT NUMBER");
	JLabel addressLabel = new JLabel("ADDRESS");
	JLabel genderLabel = new JLabel("GENDER");

	JComboBox<String> userTypes = new JComboBox<>(users);
	JComboBox<String> userGender = new JComboBox<>(genders);

    JTextField surnameField = new JTextField();
    JTextField othernameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField contactField = new JTextField();
    JTextField addressField = new JTextField();

    JButton submitButton = new JButton("SUBMIT");
    JButton resetButton = new JButton("RESET");
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	JPanel bg = new JPanel();
 
    public RegisterUserForm() {
        setLayout(null);
        addComponentsToContainer();
		setLocationAndSize();
		addActionEvent();
    }
	
	public void setLocationAndSize() {
		int startX = (int) (screenSize.getWidth() - 390) / 2;
		int startY = 100, containerHeight = 40;

		bg.setBackground(ENV.BG_ColorPale);
		bg.setBounds(startX - 50, containerHeight, 500, 400);

		header.setFont(new Font("Lato", Font.BOLD, 20));
		header.setBounds(startX, containerHeight, 390, 50);

		typeLabel.setBounds(startX, startY + (containerHeight * 0), 140, 30);
		userTypes.setBounds(startX + 140, startY + (containerHeight * 0), 250, 30);

		surnameLabel.setBounds(startX,  startY + (containerHeight * 1), 140, 30);
		surnameField.setBounds(startX + 140,  startY + (containerHeight * 1), 250, 30);

		othernameLabel.setBounds(startX,  startY + (containerHeight * 2), 140, 30);
		othernameField.setBounds(startX + 140,  startY + (containerHeight * 2), 250, 30);

		emailLabel.setBounds(startX,  startY + (containerHeight * 3), 140, 30);
		emailField.setBounds(startX + 140,  startY + (containerHeight * 3), 250, 30);

		addressLabel.setBounds(startX,  startY + (containerHeight * 4), 140, 30);
		addressField.setBounds(startX + 140,  startY + (containerHeight * 4), 250, 30);

		contactLabel.setBounds(startX,  startY + (containerHeight * 5), 140, 30);
		contactField.setBounds(startX + 140,  startY + (containerHeight * 5), 250, 30);

		genderLabel.setBounds(startX, startY + (containerHeight * 6), 140, 30);
		userGender.setBounds(startX + 140, startY + (containerHeight * 6), 250, 30);
		
		submitButton . setBounds(startX + 85, startY + (containerHeight * 7),100,30);
		resetButton.setBounds(startX + 205, startY + (containerHeight * 7),100,30);
	}

	public void addComponentsToContainer() {
		add(header);
		add(typeLabel);
		add(userTypes);
		add(surnameLabel);
		add(surnameField);
		add(othernameLabel);
		add(othernameField);
		add(emailLabel);
		add(emailField);
		add(addressLabel);
		add(addressField);
		add(contactLabel);
		add(contactField);
		add(genderLabel);
		add(userGender);
		add(submitButton) ;
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
			String userType, surname, othername, email, address, contactNum, gender;
			userType = userTypes.getSelectedItem().toString();
			gender = userGender.getSelectedItem().toString();
			surname = surnameField.getText();
			othername = othernameField.getText();
			email = emailField.getText();
			address = addressField.getText();
			contactNum = contactField.getText();

			if(surname.equals("") || othername.equals("") || email.equals("") ||
				address.equals("") || contactNum.equals("") ) {
				JOptionPane.showMessageDialog(this, "Please fill out all fields", "Invalid input", JOptionPane.ERROR_MESSAGE);
			} else {
				int id = Helper.generateNewId(userType);
				String password = Helper.generatePassword(8);
				if(admin.registerUser(userType, id, password, surname.toUpperCase() + " " + othername, email, address, contactNum, gender)) {

					JOptionPane.showMessageDialog(this, "<html><p>User was successfully registered</p><p>User ID: " + id + "</p><p>Generated Password: " + password + "</p></html>", 
												"User Login Credentials", 
												JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if(e.getSource()==resetButton) clearTextFields();
    }

	public void clearTextFields() {
		surnameField.setText("");
		othernameField.setText("");
		emailField.setText("");
		contactField.setText("");
		addressField.setText("");
	}
}
