package bigbox.db;

public class DAOFactory {
	//this method maps the StoreDAO interface to the appropriate data storage mechanism
	public static StoreDAO getProductDAO()
	{
		StoreDAO sDAO = new StoreTextFile();
		return sDAO;
	}
}
