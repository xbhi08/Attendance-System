package gui.components;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import functions.ENV;

public class HeaderLabel extends JLabel {
    public HeaderLabel(String title, int fontSize) {
        super(title, SwingConstants.CENTER);
		setFont(new Font("Lato", Font.BOLD, fontSize));
		setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        setBackground(ENV.BG_ColorPale);
        setOpaque(true);
    }
}
