import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HTMLWriter {
	static Connection connection;
	PrintWriter output;
	String fileString = "webpage.html";
	Path filePath;
	File htmlFile;

	public ArrayList<String> myArrayList = new ArrayList<String>();

	public HTMLWriter(String fileString) {

		filePath = Paths.get(fileString);
		if (Files.notExists(filePath)) {

			try {
				Files.createFile(filePath);
				htmlFile = filePath.toFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		htmlFile = filePath.toFile();
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter(htmlFile)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void StartTag(String tag) {
		myArrayList.add(tag);
		output.println("<" + tag + ">");
	}

	public void EndTag() {
		String endString = myArrayList.get(myArrayList.size() - 1);
		myArrayList.remove(myArrayList.size()-1);
		output.println("</" + endString + ">");

	}

	public void WriteData(String content) {

		output.print(content);
	}

	public void CloseFile() {
		output.flush();
		output.close();
	}


public static Connection getConnection()
{
    try
    {
        // if necessary, set the home directory for Derby
        String dbDirectory = "c:/Users/MAX-Student/desktop/code camp/HTML Writer";
        System.setProperty("derby.system.home", dbDirectory);

        // set the db url, username, and password
        String dbUrl = "jdbc:derby:InPeopleDB";
        String username = "";
        String password = "";

        connection = DriverManager.getConnection(dbUrl, username, password);
        return connection;
    }
    catch (SQLException e)
    {
        for (Throwable t : e)
            t.printStackTrace();   // for debugging
        return null;
    	}
	}

public void fillBodyTag (){
	String Query = "SELECT Text " +
				   "FROM Paragraphs";	
	try (Statement statement = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement(Query);
			ResultSet rs = ps.executeQuery())
	{
		
		while (rs.next()){
			String text = rs.getString("Text");
			WriteData(text);
		}
	}
	catch (SQLException e){
		System.out.println(e);
	}
}

}