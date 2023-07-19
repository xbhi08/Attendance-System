package gui.components;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import functions.ENV;

public class DisplayTable extends JTable {
    public DisplayTable(DefaultTableModel tableModel) {
        super(tableModel);
        addTableStyles();
    }

    public DisplayTable(DefaultTableModel tableModel, ListSelectionListener e) {
        super(tableModel);
        addTableStyles();
        getSelectionModel().addListSelectionListener(e);
    }

    public void addTableStyles() { 
        setBackground(ENV.BG_ColorPale);
        getTableHeader().setBackground(ENV.BG_Color);
        getTableHeader().setFont(ENV.Font_18_Bold);
        setFont(ENV.Font_18);
        setRowHeight(30);  
    }

    public JScrollPane scrollableTable() {
        JScrollPane pane = new JScrollPane(this);
        pane.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        return pane;
    }
}
