package gui.admin;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.awt.Dimension;
import java.awt.Toolkit;
import functions.ENV;
import functions.Helper;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent; 

public class GenerateReportFrame extends JFrame implements ActionListener {
    SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
    JButton pathButton = new JButton("Select path");
    JTextField pathField = new JTextField();
    ButtonGroup dateOptions = new ButtonGroup();
    JButton generateReportBtn = new JButton("Generate Report");
    JLabel customDateLabel = new JLabel("Select custom date range: ");
    JLabel dateFromLabel = new JLabel("Date from:");
    JTextField dateFromField = new JTextField();
    JLabel dateToLabel = new JLabel("Date to:");
    JTextField dateToField = new JTextField();
    JRadioButton dateOption1 = new JRadioButton("Today only");  
    JRadioButton dateOption2 = new JRadioButton("Custom range");    
    String directoryPath = "";
    String classId = "";

    public GenerateReportFrame(String classId) {
        super("Excel sheet generator");
        this.classId = classId;
        pathButton.addActionListener(this);
        generateReportBtn.addActionListener(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() / 2) - 185, 100);
        getContentPane().setBackground(ENV.BG_ColorPale);
        setLayout(null);
        setSize(370, 280);
        addComponents();
        setVisible(true);
    }

    public void addComponents() {
        JLabel filepathLabel = new JLabel("Select path to save report:");
        filepathLabel.setBounds(20, 20, 350, 30);
        filepathLabel.setFont(ENV.Font_18_Bold);
        JToolBar fileTool = new JToolBar();
        fileTool.setBackground(ENV.BG_ColorPale);
        fileTool.setBounds(0, 50, 350, 30);
        fileTool.add(pathField);
        fileTool.add(pathButton);
        
        JLabel dateoptionLabel = new JLabel("Select date range:");
        dateoptionLabel.setBounds(20, 100, 350, 30);
        dateoptionLabel.setFont(ENV.Font_18_Bold);

        dateOption1.addActionListener(this);  
        dateOption1.setSelected(true);
        dateOption1.setBounds(20,130,150,30);
        dateOption1.setBackground(ENV . BG_ColorPale);

        dateOption2.addActionListener(this);
        dateOption2.setBounds(180,130,150,30);   
        dateOption2.setBackground(ENV.BG_ColorPale);
        dateOptions.add(dateOption1);
        dateOptions.add(dateOption2);

        
        customDateLabel.setBounds(20, 180, 350, 30);
        customDateLabel.setFont(ENV.Font_18_Bold);
        customDateLabel.setVisible(false);
        dateFromLabel.setVisible(false);
        dateFromLabel.setBounds(20, 210, 100, 30);
        dateFromField.setBounds(20, 240, 100, 30);
        dateFromField.setVisible(false);
        dateToLabel.setVisible(false);
        dateToLabel.setBounds(140, 210, 100, 30);
        dateToField.setBounds(140, 240, 100, 30);  
        dateToField.setVisible(false);

        generateReportBtn.setBounds(20, 180, 320, 30);

        add(filepathLabel);
        add(fileTool);
        add(dateoptionLabel);
        add(dateOption1) ;
        add(dateOption2);
        add(customDateLabel);
        add(dateFromLabel);
        add(dateToLabel);
        add(dateFromField);
        add(dateToField);
        add(generateReportBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Select path")) {
            JFileChooser file = new JFileChooser();
            file.setMultiSelectionEnabled(false);
            file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            file.setFileHidingEnabled(false);
            if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                directoryPath = file.getSelectedFile().getPath();
                pathField.setText(directoryPath);
            }
            return;
        }
        if(e.getActionCommand().equals("Custom range")){
            setSize(370, 370);
            customDateLabel.setVisible(true);
            dateFromLabel.setVisible(true);
            dateFromField.setVisible(true);
            dateToLabel.setVisible(true);
            dateToField.setVisible(true);
            generateReportBtn.setBounds(20, 290, 320, 30);
            return;
        }
        if(e.getActionCommand().equals("Today only")){
            setSize(370, 280);
            customDateLabel.setVisible(false);
            dateFromLabel.setVisible(false);
            dateFromField.setVisible(false);
            dateToLabel.setVisible(false);
            dateToField.setVisible(false);
            generateReportBtn.setBounds(20, 180, 320, 30);
            return;
        }
        if(e.getActionCommand().equals("Generate Report")){
            boolean successful;
            if(dateOption2.isSelected()) {
                successful = Helper.generateReport(directoryPath, classId, dateFromField.getText(), dateToField.getText());
            } else {
                String dateToday = dateFormat.format(new Date());
                successful = Helper.generateReport(directoryPath, classId, dateToday, dateToday);
            }

            if(successful) {
				JOptionPane.showMessageDialog(this, "Excel sheet generated", "Excel Generator", JOptionPane.INFORMATION_MESSAGE);
            } else {
				JOptionPane.showMessageDialog(this, "Attendance was unsuccessful", "Attendance", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
