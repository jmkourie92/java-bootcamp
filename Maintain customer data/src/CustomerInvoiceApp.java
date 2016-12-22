import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class CustomerInvoiceApp {

	static MurachDB mdb = new MurachDB();
	private static Connection connection = null;
	 
	public static void main(String args[])
	    {
	        // get the connection and start the Derby engine
	        connection = MurachDB.getConnection();
	        if (connection != null)
	            System.out.println("Derby has been started.\n");
	        
	        // select data from database
	        printCustomer();
	     
	        // disconnect from the database
	        if (MurachDB.disconnect())
	            System.out.println("Derby has been shut down.\n");
	    }

	    public static void printCustomer()
	    {
	    	String joinQuery = 
	    			"SELECT EmailAddress, InvoiceNumber, InvoiceDate, InvoiceTotal "+
	    					"FROM Customers c " +
	    					"INNER JOIN Invoices i " +
	    					"ON c.CustomerID = i.CustomerID "+
	    					"ORDER BY EmailAddress ASC";
	       
	    	try (Statement statement = connection.createStatement();
	    			 PreparedStatement ps = connection.prepareStatement(joinQuery);
		             ResultSet rs = ps.executeQuery())
	        {            
	            Customer c = null;

	            while(rs.next())
	            {
	            	
	            	Date invoiceDate = rs.getDate("InvoiceDate");
	            	String invoiceNumber = rs.getString("InvoiceNumber");
	            	String emailAddress = rs.getString("EmailAddress");
	                double invoiceTotal = rs.getDouble("InvoiceTotal");

	                c = new Customer(emailAddress, invoiceNumber, invoiceDate, invoiceTotal);

	                printCustomerFormatt(c);
	            }
	            System.out.println();
	        }
	        catch(SQLException e)
	        {
	            e.printStackTrace();  // for debugging
	        }
	    }

	    // use this method to print a Product object on a single line
	    private static void printCustomerFormatt(Customer c)
	    {
	        String customerString =
	            StringUtils.padWithSpaces(c.getEmailAddress(), 25) +
	            StringUtils.padWithSpaces(c.getInvoiceNumber(), 25) +
	            StringUtils.padWithSpaces(c.getInvoiceDate().toString(), 20) +
	            c.getFormattedInvoiceTotal();
	        System.out.println(customerString);
	    }
}