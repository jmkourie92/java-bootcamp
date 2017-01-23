package bigbox.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import bigbox.business.Store;

public class StoreTextFile implements StoreDAO {

	private ArrayList<Store> stores = null;
	private Path storesPath = null;
	private File storesFile = null;
	private final String FIELD_SEP = "\t";
	
	public StoreTextFile(){
		//initialize class variable
		storesPath = Paths.get("stores.txt");
		storesFile = storesPath.toFile();
		stores =  getAllStores();
	}

	@Override
	public boolean addStore(Store inStore) {
		boolean addSuccess = false;
		if(inStore != null){
			stores.add(inStore);
			addSuccess = true;
		}
		return addSuccess;
	}

	@Override
	public ArrayList<Store> getAllStores() {
		ArrayList<Store> allStores = new ArrayList<Store>();
		//Load the array list with store objects created from the data in the file
		if(Files.exists(storesPath)){
			try (BufferedReader br = new BufferedReader(new FileReader(storesFile))){
				//read the text file, line by line, creating Store objects for each
				String storeLine = br.readLine();
				while (storeLine != null){
					//split up the file, delimited by tabs, into fields
					String[] columns = storeLine.split(FIELD_SEP);
					int id = Integer.parseInt(columns[0]);
					String divNbr = columns[1];
					String storeNbr = columns[2];
					double sales = Double.parseDouble(columns[3]);
					String name = columns[4];
					String address = columns[5];
					String city = columns[6];
					String state = columns[7];
					String zipCode = columns[8];
					//create a store object
					Store s = new Store(id, divNbr, storeNbr, sales, name, address, city, state, zipCode);
					allStores.add(s);
					//read the next line (cause you're in a while loop silly!)
					storeLine = br.readLine();
				}
				//parse out the individual elements from from the line
			} catch (IOException e) {
				System.out.println("Error encountered while reading store file!");
				e.printStackTrace();
			}
		}
		return allStores;
	}

	@Override
	public ArrayList<Store> getAllStoresByDivision(String inDiv) {
		ArrayList <Store> storesForDiv = new ArrayList<Store>();
		ArrayList<Store> allStores = getAllStores();
		for(Store s: allStores){
			if(s.getDivNbr().equals(inDiv)){
				storesForDiv.add(s);
			}
		}
		return storesForDiv;
	}

	@Override
	public Store getStore(String inDiv, String inStore) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean deleteStore(String inDivNbr, String inStoreNbr) {
		// TODO Auto-generated method stub
		return false;
	}

}
