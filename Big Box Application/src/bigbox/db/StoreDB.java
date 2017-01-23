package bigbox.db;
import java.util.*;

import bigbox.business.Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class StoreDB implements StoreDAO
{
    public StoreDB() {
    	// Empty constructor
    }

	public ArrayList<Store> getAllStores() {
		String sql = "SELECT * from STORES";
		ArrayList<Store> stores = new ArrayList<>();

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("FacilityID");
				String divNbr = rs.getString("DivisionNumber");
				String storeNbr = rs.getString("StoreNumber");
				double sales = rs.getDouble("Sales");
				String name = rs.getString("FacilityName");
				String address = rs.getString("Address");
				String city = rs.getString("City");
				String state = rs.getString("State");
				String zip = rs.getString("ZipCode");
				Store s = new Store(id, divNbr, storeNbr, sales, name, address, city, state, zip);
				stores.add(s);
			}
			return stores;
		} catch (SQLException e) {
			System.err.println(e);
			return null;
		}

	}

    public ArrayList<Store> getStoresByDivision(String inDiv)
    {
        ArrayList<Store> storesForDiv = new ArrayList<>();  
		String sql = "SELECT * from STORES where DivisionNumber = ?";

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, inDiv);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("FacilityID");
				String divNbr = rs.getString("DivisionNumber");
				String storeNbr = rs.getString("StoreNumber");
				double sales = rs.getDouble("Sales");
				String name = rs.getString("FacilityName");
				String address = rs.getString("Address");
				String city = rs.getString("City");
				String state = rs.getString("State");
				String zip = rs.getString("ZipCode");
				Store s = new Store(id, divNbr, storeNbr, sales, name, address, city, state, zip);
				storesForDiv.add(s);
			}
			return storesForDiv;
		} catch (SQLException e) {
			System.err.println(e);
			return null;
		}            
    }

	@Override
	public boolean addStore(Store store) {
		// add this store to the list
		boolean addSuccess = false;
		if (store!=null) {
			String sql = "INSERT INTO Stores VALUES (?,?,?,?,?,?,?,?,?)";

			try (Connection connection = getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setInt(1, store.getId());
				ps.setString(2,store.getDivNbr());
				ps.setString(3,store.getStoreNbr());
				ps.setDouble(4, store.getSales());
				ps.setString(5, store.getName());
				ps.setString(6, store.getAddress());
				ps.setString(7, store.getCity());
				ps.setString(8, store.getState());
				ps.setString(9, store.getZipCode());
				ps.executeUpdate();
				addSuccess = true;
			} catch (SQLException e) {
				System.err.println(e);
				return false;
			}            
		}
		return addSuccess;
	}

    public Store getStore(String divNbr, String strNbr) {
    	Store s = null;
		String sql = "SELECT * from STORES where DivisionNumber = ? and StoreNumber = ?";

		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.setString(1, divNbr);
			ps.setString(2, strNbr);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("FacilityID");
				String storeNbr = rs.getString("StoreNumber");
				double sales = rs.getDouble("Sales");
				String name = rs.getString("FacilityName");
				String address = rs.getString("Address");
				String city = rs.getString("City");
				String state = rs.getString("State");
				String zip = rs.getString("ZipCode");
				s = new Store(id, divNbr, storeNbr, sales, name, address, city, state, zip);
			}
		} catch (SQLException e) {
			System.err.println(e);
			return null;
		}            
    	return s;
    }

    @Override
	public boolean deleteStore(String divNbr, String strNbr) {
		// delete a store from the list
    	Store store = getStore(divNbr, strNbr);
		boolean delSuccess = false;
		if (store!=null) {
			String sql = "DELETE from STORES where DivisionNumber = ? and StoreNumber = ?";

			try (Connection connection = getConnection();
					PreparedStatement ps = connection.prepareStatement(sql);) {
				ps.setString(1, divNbr);
				ps.setString(2, strNbr);
				ps.executeUpdate();
				delSuccess = true;
			} catch (SQLException e) {
				System.err.println(e);
				return false;
			}            
		}
		return delSuccess;
	}

    public static Connection getConnection()
    {
        Connection connection = null;
        try
        {
        	String dbDirectory = "c:/murach/java/db";
            System.setProperty("derby.system.home", dbDirectory);

            // set the db url, username, and password
            String url = "jdbc:derby:BigBoxTestDB";
            String username = "";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (SQLException e)
        {
            for (Throwable t : e)
                t.printStackTrace();   // for debugging
            return null;
        }
    }
    
    public boolean disconnect()
    {
        try
        {
            // On a successful shutdown, this throws an exception
            String shutdownURL = "jdbc:derby:;shutdown=true";
            DriverManager.getConnection(shutdownURL);
        }
        catch (SQLException e)
        {
            if (e.getMessage().equals("Derby system shutdown."))
                return true;
        }
        return false;
    }

	@Override
	public ArrayList<Store> getAllStoresByDivision(String inDiv) {
		
		return null;
	}
}