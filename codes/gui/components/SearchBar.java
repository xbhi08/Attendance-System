package gui.components;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import functions.ENV;

import java.awt.event.ActionListener;

public class SearchBar extends JPanel {
    JTextField searchField = new JTextField();
    JButton searchBtn = new JButton("search");

    public SearchBar(ActionListener handler) {
        addComponents();
        setComponentSize();
        searchBtn.addActionListener(handler);
    }

    public void addComponents() {
        add(searchField);
        add(searchBtn);
    }

    public void setComponentSize() {
        searchField.setPreferredSize(new Dimension(300, 30));
        searchBtn.setPreferredSize(new Dimension(100, 30));
        searchBtn.setFont(ENV.Font_18);
        searchField.setFont(ENV.Font_18);
    }

    public void clearText() {
        searchField.setText("");
    }
    
    public String getSearchText() {
        return searchField.getText();
    }
    
    public void setSearchText(String s) {
        searchField.setText(s);
    }
}
