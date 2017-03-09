import java.util.*;
import java.sql.*;

public class Student {
	static Connection con; 			//Connection that persists through entire transaction.	
	public int studentID;
	private ResultSet rs;
	private Statement statement;
	
	// define query
	
	public Student(int studentID, Connection con){
		this.studentID = studentID;
		Student.con = con;
	
		try {
			statement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//SEES if Customer/Student exists in Record, based on their Customer ID.
	public boolean findStudent(int studentID){
		String search = "SELECT * FROM Customer WHERE Customer_ID = " + Integer.toString(studentID);
		search = search.concat(";");
		boolean studentExists = true;
	
		try{
			rs = statement.executeQuery(search);
			if (!rs.next())					//if no record is returned, is false.
				studentExists = false;
			
            statement.close();
            rs.close();
		}
		
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return studentExists;
	}

	
	//If student doesn't exist, makes a new student and gets their customer_ID. If Customer_ID = -1, means customer wasn't successfully made.
	//Needs to be called by MAIN.
	public int makeNewStudent(String name, String phone, String street, String city, String state, String zip){
		int newCustID = -1;
		String search = "INSERT INTO Customer(Customer_Name, Phone_Number, Street, City, State, Zip_Code) VALUES(";
		String query ="'" + name +  "', '" + phone + "', '" + street + "', '" + city + "', '" + state + "', '" + zip + "');";
		search = search.concat(query);
		
		try {
			statement.executeUpdate(search);
			String getID = "SELECT Customer_ID FROM Customer ORDER BY Customer_ID DESC LIMIT 1;";
			rs = statement.executeQuery(getID);
			newCustID = rs.getInt("Customer_ID");   
			this.studentID = newCustID;
			
            statement.close();
            rs.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		return newCustID;
	}
	
	//prints out all the courses 
	public void getCourses(String department, int Course_Number){
		//Should we display Professor_ID since that should be a personal/private identifier?
		String query = "SELECT c.Department, c.Course_Number, c.Professor_ID, p.Professor_Name FROM Course c, Professor p WHERE c.Professor_ID = p.Professor_ID AND c.Course_Number = " + Integer.toString(Course_Number) + " AND WHERE c.Department = '" + department + "';" ;

		try {	
			rs = statement.executeQuery(query);
		
			while (rs.next())
				System.out.println(rs.getString("Department") + " " +  rs.getInt("Course_Number")+ " " + rs.getInt("Professor_ID") + " " + rs.getString("Professor_Name"));
		
            statement.close();
            rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//student select class and professor id to query books
	public void getRequiredBooks(String dept, int course, int profID){
		String query = "SELECT r.Department, r.Course_Number, r.Professor_ID, t.Title, t.ISBN, t.Price FROM RequiredBook r, Textbook t WHERE r.Textbook_Required = t.ISBN AND r.Department = '" + dept + "' AND r.Course_Number = " + Integer.toString(course) + " AND r.Professor_ID = " + Integer.toString(profID) + ";";
		
		try {
			rs = statement.executeQuery(query);
			while (rs.next())
				System.out.println(rs.getString("Department") + " " + rs.getInt("Course_Number") + " || Professor ID: " + rs.getInt("Professor_ID") + " || Text Required: "  + rs.getString("Title") + " ISBN: " + rs.getString("ISBN") + " Price: " + rs.getDouble("Price"));
		
            statement.close();
            rs.close();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void getVendor(int ISBN){
		String query = "SELECT v.Vendor_ID, v.Vendor_Name, t.ISBN, t.Title, t.Price FROM VendorArchive v, Vendor ven, Textbook t WHERE v.Vendor_ID = ven.Vendor_ID AND v.ISBN = T.ISBN AND v.ISBN =  " + Integer.toString(ISBN) + ";";
	
		try {
			rs = statement.executeQuery(query);
			while(rs.next())
				System.out.println("Vendor: " + rs.getInt("Vendor_ID") + "-" + rs.getString("Vendor_Name") + " || Text: " + rs.getString("ISBN") + " " +  rs.getString("Title") + " PRICE: " + rs.getDouble("Price"));
			
			statement.close();
            rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//HOW TO TEST FOR VALID INSERT? Should this return 1 for successful?
	public int makeTransaction(int vendorID, int isbn, int custID){
		String insertQuery = "INSERT INTO Transaction(Vendor_ID, Customer_ID, ISBN) VALUES(" + Integer.toString(vendorID) + ", '" + Integer.toString(isbn) + "', " + Integer.toBinaryString(custID) + ");"; 
		int result = -1; 		//default failed operation
		
		try {
			result = statement.executeUpdate(insertQuery);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;

		
	}
}
