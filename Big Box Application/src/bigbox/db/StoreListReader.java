package bigbox.db;

import java.util.ArrayList;

import bigbox.business.Store;

public interface StoreListReader {
	public ArrayList<Store> getAllStores();
	public ArrayList<Store> getAllStoresByDivision(String inDiv);
	public Store getStore(String inDiv, String inStore);
}
