package gui.components;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


public class Dashboard extends JFrame {
    JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    HeaderLabel header;
    public Dashboard(String title, JTabbedPane display) {
        super(title);
        header = new HeaderLabel(title, 30);
        body.setTopComponent(header);
        body.setBottomComponent(display);

        add(body);
        setVisible(true);   
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}   
