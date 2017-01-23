package bigbox.db;
import java.sql.*;

public class BigBoxDB
{
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

    public static boolean disconnect()
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
}