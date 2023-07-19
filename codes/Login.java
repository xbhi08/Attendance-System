
import javax.swing.*;

import functions.Admin;
import gui.admin.*;
import faculty.FacultyPanel;
import functions.ENV;
import functions.Faculty;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
	String users[] = {"admin","faculty"};
    Container container=getContentPane();
	JLabel header = new JLabel("Attendance Management System", SwingConstants.CENTER);
    JLabel typeLabel=new JLabel("USER TYPE");
	JComboBox<String> userTypes =new JComboBox<>(users);
    JLabel userLabel=new JLabel("USERID");
    JLabel passwordLabel=new JLabel("PASSWORD");
    JTextField userTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JButton loginButton=new JButton("LOGIN");
    JButton resetButton=new JButton("RESET");
    JCheckBox showPassword=new JCheckBox("Show Password");
 
    Login() {
		super("Login Form");
        setVisible(true);
        setResizable(false);
		setSize(600,500);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
		addActionEvent();
    }

    public void setLayoutManager() {
    	container.setLayout(null);
		container.setBackground(ENV.BG_Color);
    }
   
   public void setLocationAndSize() {
		header.setBounds(0, 10, 600, 50);
		header.setFont(new Font("Lato", Font.BOLD, 30));

		typeLabel.setBounds(175, 90, 250, 30);
		userTypes.setBounds(175, 120, 250, 30);

		userLabel.setBounds(175,170,100,30);
		userTextField.setBounds(175,200,250,30);
		
		passwordLabel.setBounds(175,240,100,30);
		passwordField.setBounds(175,270,250,30);

		showPassword.setBounds(175,300,150,30);
		showPassword.setBackground(ENV.BG_Color);

		loginButton.setBounds(175,340,100,30);
		resetButton.setBounds(325,340,100,30);
	}

	public void addComponentsToContainer() {
		container.add(header);
		container.add(typeLabel);
		container.add(userTypes);
    	container.add(userLabel);
    	container.add(passwordLabel);
    	container.add(userTextField);
    	container.add(passwordField);
    	container.add(showPassword);
    	container.add(loginButton);
    	container.add(resetButton);
    }

	public void addActionEvent() {
       loginButton.addActionListener(this);
       resetButton.addActionListener(this);
       showPassword.addActionListener(this);
   	} 
  
    @Override
    public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
            String userId = userTextField.getText();
            String userPass = String.valueOf(passwordField.getPassword());
			String userType = userTypes.getSelectedItem().toString();

			if(userType.equals("admin")) {
				Admin auth = new Admin();
				if(auth.login(userId, userPass)) {
					new AdminPanel();
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, auth.getAuthError(), "Login Error", JOptionPane.WARNING_MESSAGE);
				}	
			} 
			if(userType.equals("faculty")) {
				Faculty auth = new Faculty();
				if(auth.login(userId, userPass)) {
					new FacultyPanel(auth);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, auth.getAuthError(), "Login Error", JOptionPane.WARNING_MESSAGE);
				}	
			} 
			// TODO: ADD NEW USER
        }
        
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

	public static void main(String arg[]) {
        new Login();
	}       
}