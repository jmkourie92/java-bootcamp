package bigbox.db;

import bigbox.business.Store;

public interface StoreListWriter {
	public boolean addStore(Store inStore);
	public boolean deleteStore(String inDivNbr, String inStoreNbr);
}
