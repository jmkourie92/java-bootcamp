package bigbox.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import bigbox.business.Store;
import bigbox.db.StoreDB;
import bigbox.db.DBException;

@SuppressWarnings("serial")
public class StoreTableModel extends AbstractTableModel {
    private List<Store> stores;
    private final String[] COLUMN_NAMES = { "ID", "Div Nbr", "Str Nbr", "Name", "Sales" };
 
    public StoreTableModel() {
    	StoreDB sDB = new StoreDB();
        stores = sDB.getAllStores();
    }
    
    @Override
    public int getRowCount() {
        return stores.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return stores.get(rowIndex).getId();
            case 1:
                return stores.get(rowIndex).getDivNbr();
            case 2:
                return stores.get(rowIndex).getStoreNbr();
            case 3:
                return stores.get(rowIndex).getName();
            case 4:
                return stores.get(rowIndex).getSales();
            default:
                return null;
        }
    }   
    
    Store getStore(int rowIndex) {
        return stores.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
        	StoreDB storeDB = new StoreDB();
            stores = storeDB.getAllStores();
            fireTableDataChanged();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }    
}