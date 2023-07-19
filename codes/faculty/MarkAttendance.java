package faculty;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

import functions.ENV;
import functions.Faculty;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class MarkAttendance extends JFrame implements ActionListener{

    ArrayList<ButtonGroup> attendanceArr = new ArrayList<ButtonGroup>();
    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    ArrayList<String[]> students;
    Faculty fac;
    String classId;

    public MarkAttendance(ArrayList<String[]> studs, Faculty fac, String classId) {
        super("Attendance");
        students = studs;
        this.fac = fac; 
        this.classId = classId;
        body.setTopComponent(fieldColumnName());
        body.setBottomComponent(new JScrollPane(tableComponents()));
        add(body);
        
        setSize(630, 600);
        setVisible(true);
    }

    public JPanel tableComponents() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); 

        for (int i = 0; i < students.size(); i++) {
            container.add(studentField(students.get(i)[0], students.get(i)[1]));
        }

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(ENV.Font_18);
        submitBtn.addActionListener(this);
        container.add(submitBtn);

        return container;
    }

    public JPanel fieldColumnName() {
        JPanel field = new JPanel();
        field.setBackground(ENV.BG_Color);
        JLabel id = new JLabel("Student Id");
        id.setFont(ENV.Font_18_Bold);
        id.setPreferredSize(new Dimension(105, 30));

        JLabel name = new JLabel("Student Name");
        name.setPreferredSize(new Dimension(305, 30));
        name.setFont(ENV.Font_18_Bold);

        JLabel present = new JLabel("Present");
        present.setPreferredSize(new Dimension(80, 30));
        present.setHorizontalAlignment(SwingConstants.CENTER);
        present.setFont(ENV.Font_18_Bold);

        JLabel absent = new JLabel("Absent");
        absent.setPreferredSize(new Dimension(80, 30));
        absent.setHorizontalAlignment(SwingConstants.CENTER);
        absent.setFont(ENV.Font_18_Bold);

        field.add(id);
        field.add(name);
        field.add(present);
        field.add(absent);  

        return field;
    }
    
    public JPanel studentField(String studId, String studName) {
        JPanel field = new JPanel();
        field.setBackground(ENV.BG_ColorPale);
        JLabel id = new JLabel(studId);
        id.setPreferredSize(new Dimension(100, 30));
        id.setFont(ENV.Font_18);
        
        JLabel name = new JLabel(studName);
        name.setPreferredSize(new Dimension(300, 30));
        name.setFont(ENV.Font_18);
        
        JCheckBox present = new JCheckBox();
        present.setPreferredSize(new Dimension(80, 30));
        present.setBackground(ENV.BG_ColorPale);
        present.setHorizontalAlignment(SwingConstants.CENTER);
        present.setActionCommand(studId + " present");

        JCheckBox absent = new JCheckBox();
        absent.doClick();
        absent.setPreferredSize(new Dimension(80, 30));
        absent.setBackground(ENV.BG_ColorPale);
        absent.setHorizontalAlignment(SwingConstants.CENTER);
        absent.setActionCommand(studId + " absent");

        ButtonGroup presence = new ButtonGroup();
        presence.add(present); 
        presence.add(absent); 
        attendanceArr.add(presence);
        
        field.add(id);
        field.add(name);
        field.add(present);
        field.add(absent);
        
        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Submit")) {
            ArrayList<String[]> studRecords = new ArrayList<String[]>();
            for(ButtonGroup btn : attendanceArr) { 
                studRecords.add(btn.getSelection().getActionCommand().split(" "));
            }
            boolean insertSuccessful = fac.addAttendanceRecord(classId, studRecords);
            if(insertSuccessful) {
				JOptionPane.showMessageDialog(this, "Attendance was successfully recorded", "Attendance", JOptionPane.INFORMATION_MESSAGE);
            } else {
				JOptionPane.showMessageDialog(this, "An error occured. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
