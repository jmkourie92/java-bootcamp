package bigbox.ui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import bigbox.business.Store;
import bigbox.db.DAOFactory;
import bigbox.db.StoreDAO;
import bigbox.db.StoreTextFile;
import bigbox.util.Validator;

public class BigBoxApp {
    // declare class variables
    private static StoreDAO storeDAO = null;
    private static Scanner sc = null;

	public static void main(String[] args) {
        // display a welcome message
        System.out.println("Welcome to the Big Box application\n");
        
        // set the class variables
        storeDAO = DAOFactory.getProductDAO(); // populate storeDB instance w/ stores data
        sc = new Scanner(System.in);

        // display the command menu
        displayMenu();

        // perform 1 or more actions
        String action = "";
        while (!action.equalsIgnoreCase("exit"))
        {
            // get the input from the user
            action = Validator.getString(sc,
                    "Enter a command: ");
            System.out.println();

            if (action.equalsIgnoreCase("list"))
                displayAllStores();
            else if (action.equalsIgnoreCase("div"))
                displayAllStoresForDivision();
            else if (action.equalsIgnoreCase("add"))
                addAStore();
            else if (action.equalsIgnoreCase("sales"))
                displaySalesSummary();
            else if (action.equalsIgnoreCase("help") || action.equalsIgnoreCase("menu"))
                displayMenu();
            else if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("quit"))
                System.out.println("Bye.\n");
            else
                System.out.println("Error! Not a valid command.\n");
        }
        		
	}
	
    public static void displayMenu()
    {
        System.out.println("COMMAND MENU");
        System.out.println("list    - List all stores");
        System.out.println("div     - List all stores for a division");
        System.out.println("add     - Add a store");
        System.out.println("sales   - Sales summary");
        System.out.println("help    - Show this menu");
        System.out.println("exit    - Exit this application\n");
    }

    public static void displayAllStores()
    {
        System.out.println("STORES LIST");
        StringBuilder sb = new StringBuilder();
        
        ArrayList<Store> storeList = storeDAO.getAllStores();
        
        for (Store s:storeList) {
        	sb.append(s.toString()+"\n");
        }

        System.out.println(sb.toString());
    }

    public static void displayAllStoresForDivision()
    {
        System.out.println("STORES LIST BY DIVISION");
        StringBuilder sb = new StringBuilder();
        boolean validDiv = false;
        while (!validDiv) {
	        String div = Validator.getString(sc,
	                "Enter a Division #: ");
	        System.out.println();
	        
	        ArrayList<Store> storeList = storeDAO.getAllStoresByDivision(div);
	        //If storeList is empty, display a msg
	        if (storeList==null || storeList.size()==0) {
	        	System.out.println("Enter a valid division.  "+div+" is not valid.");
	        }
	        else {
	        	validDiv=true;
		        for (Store s:storeList) {
		        	sb.append(s.toString()+"\n");
		        }
		        System.out.println(sb.toString());
	        }
        }
    }

    private static void addAStore() {
        System.out.println("ADD A STORE");
        
        int facId;
        double sales;
        // defining all string method variables on one line
        String divNbr, storeNbr, facName, facAddress, facCity, facState, facZip; 
       	facId = Validator.getInt(sc, "Enter a Facility ID (key, 3 bytes, numeric): "); 
        divNbr = Validator.getStringNumeric(sc, "Enter a Division #: ",3);  // division # must be 3 bytes
        storeNbr = Validator.getStringNumeric(sc, "Enter a store # (5 bytes, numeric): ", 5); //store must by 3 bytes
        sales = Validator.getDouble(sc, "Enter the sales for this store: ");
        facName = Validator.getLine(sc, "Enter store name: ");
        facAddress = Validator.getLine(sc, "Enter street address: ");
        facCity = Validator.getLine(sc, "Enter the city: ");
        facState = Validator.getString(sc, "Enter state abbreviation: ",2).toUpperCase();
        facZip = Validator.getStringNumeric(sc, "Enter zip code: ", 5);
	    System.out.println();

	    Store s = new Store(facId, divNbr, storeNbr, sales, facName, facAddress, facCity, facState, facZip);
	    if (storeDAO.addStore(s))
	    	System.out.println("Store # "+storeNbr+" successfully added!");
	    else
	    	System.out.println("Error adding store # "+storeNbr);
    }

    public static void displaySalesSummary()
    {
        System.out.println("SALES SUMMARY");
        
        ArrayList<Store> storeList = storeDAO.getAllStores();
        double salesSum = 0;
        for (Store s:storeList) {
        	salesSum+=s.getSales();
        }
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        System.out.println("Total Sales for all stores = "+currency.format(salesSum));
    }
}
