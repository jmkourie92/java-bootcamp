import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class HTMLWriterForm extends JFrame{

	static InPeopleDB inpdb = new InPeopleDB();
	
	JPanel frame;
	JComboBox heroCmbbx; 
	String person;
	JScrollPane scroll;
	JTextArea heroTxt;
	
	JLabel personLbl;
	JButton addBtn;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HTMLWriterForm frame = new HTMLWriterForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HTMLWriterForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frame);
		

		heroCmbbx = new JComboBox();
		
		fillComboBox(getConnection());
			
			ListModel listModel = new DefaultListModel();
			
			
		personLbl = new JLabel("Person:");
		
	
		
		addBtn = new JButton("Add");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				person = heroCmbbx.getSelectedItem().toString();
				ArrayList<String> myArrayList = new ArrayList<String>();
				myArrayList.add(person);
				System.out.println(person);
				
				
				fillTextBox(getConnection());
			}
		});
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String updated = "Database has been updated!";
				updateParagraphs(getConnection());
				heroTxt.setText(updated);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		

		
		GroupLayout gl_frame = new GroupLayout(frame);
		gl_frame.setHorizontalGroup(
			gl_frame.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_frame.createSequentialGroup()
					.addGroup(gl_frame.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_frame.createSequentialGroup()
							.addContainerGap()
							.addComponent(addBtn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(submitBtn))
						.addGroup(Alignment.LEADING, gl_frame.createSequentialGroup()
							.addGap(26)
							.addComponent(personLbl)
							.addGap(18)
							.addGroup(gl_frame.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
								.addComponent(heroCmbbx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE)))
					.addGap(30))
		);
		gl_frame.setVerticalGroup(
			gl_frame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_frame.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_frame.createParallelGroup(Alignment.BASELINE)
						.addComponent(personLbl)
						.addComponent(heroCmbbx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addGroup(gl_frame.createParallelGroup(Alignment.BASELINE)
						.addComponent(submitBtn)
						.addComponent(addBtn))
					.addGap(21))
		);
		
		heroTxt = new JTextArea();
		heroTxt.setLineWrap(true);
		scrollPane.setViewportView(heroTxt);
		frame.setLayout(gl_frame);     
	}
	

	public static Connection getConnection()
    {
        Connection connection = null;
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
	
	private void fillTextBox(Connection connection){
		String Query = "SELECT Name, Text " +
					   "FROM People p " +
					   "INNER JOIN Paragraphs para " +
					   "ON p.People_key = para.People_key " +
					   "WHERE Name = \'" + person + "\'";
					   
		
		try (Statement statement = connection.createStatement();
				PreparedStatement ps = connection.prepareStatement(Query);
				ResultSet rs = ps.executeQuery())
		{
			String output = "";
			while (rs.next()){
				String text = rs.getString("Text");
				output += text + "\n" + "\n";
			}
			heroTxt.setText(output);
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	private void fillComboBox (Connection connection){
		String Query = "SELECT Name " +
					   "FROM People";	
		try (Statement statement = connection.createStatement();
				PreparedStatement ps = connection.prepareStatement(Query);
				ResultSet rs = ps.executeQuery())
		{
			
			while (rs.next()){
				String name = rs.getString("Name");
				heroCmbbx.addItem(name);
			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
	}
	private void updateParagraphs(Connection connection){
		String Query = "UPDATE Paragraphs " +
					   "SET Text = ?, People_key = ? " +
					   "WHERE Sequence = ?";
		
		try(PreparedStatement ps = connection.prepareStatement(Query)){
			String textString = heroTxt.toString();
			ps.setString(1, textString);
			int count = ps.executeUpdate();
			
			String[] myArray = textString.split("\n");
		}
		catch (SQLException e){
			System.out.println(e);
		}
		
		
		
	}
}
