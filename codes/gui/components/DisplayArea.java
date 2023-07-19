package gui.components;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class DisplayArea extends JTabbedPane{

    public DisplayArea(String[] tabNames, JPanel[] tabPages) {
        setFont(new Font("Lato", Font.BOLD, 18));
        for(int i=0; i<tabNames.length; i++) {   
            add(tabNames[i], tabPages[i]);  
        }
    }
}
