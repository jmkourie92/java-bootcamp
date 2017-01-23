package bigbox.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bigbox.business.Store;
import bigbox.db.StoreDB;
import bigbox.db.DBException;

@SuppressWarnings("serial")
public class StoreManagerFrame extends JFrame {
    private JTable storeTable;
    private StoreTableModel storeTableModel;
    
    public StoreManagerFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }        
        setTitle("Store Manager");
        setSize(768, 384);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        storeTable = buildStoreTable();
        add(new JScrollPane(storeTable), BorderLayout.CENTER);
        setVisible(true);        
    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add store");
        addButton.addActionListener((ActionEvent) -> {
            doAddButton();
        });
        panel.add(addButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit selected store");
        editButton.addActionListener((ActionEvent) -> {
            doEditButton();
        });
        panel.add(editButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected store");
        deleteButton.addActionListener((ActionEvent) -> {
            doDeleteButton();
        });
        panel.add(deleteButton);
        
        JButton printButton = new JButton("Print");
        printButton.setToolTipText("Print selected store");
        printButton.addActionListener((ActionEvent) -> {
            doPrintButton();
        });
        panel.add(printButton);

        return panel;
    }
    
    private void doAddButton() {
        JOptionPane.showMessageDialog(this,
            "This feature hasn't been implemented yet.",
            "Not yet implemented", JOptionPane.ERROR_MESSAGE);
    }
    
    private void doEditButton() {
        doAddButton();
    }
    
    private void doPrintButton() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No store is currently selected.", 
                    "No store selected", JOptionPane.ERROR_MESSAGE);
        } else {
	        JOptionPane.showMessageDialog(this,
	                "The document has been sent to the printer.",
	                "Printed, Man!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void doDeleteButton() {
        int selectedRow = storeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No store is currently selected.", 
                    "No store selected", JOptionPane.ERROR_MESSAGE);
        } else {
            Store store = storeTableModel.getStore(selectedRow);
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + 
                            store.getName() + " from the database?",
                    "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
            	StoreDB storeDB = new StoreDB();
                storeDB.deleteStore(store.getDivNbr(), store.getStoreNbr());
                storeTableModel.databaseUpdated();
            }
        }
    }
    
    private JTable buildStoreTable() {
        storeTableModel = new StoreTableModel();
        JTable table = new JTable(storeTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(null);
        return table;
    }
}