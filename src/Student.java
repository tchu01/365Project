import java.util.*;
import java.sql.*;

public class Student {
	private Connection con; 			//Connection that persists through entire transaction.	
	ResultSet rs;
	private Statement statement;
	
	private int customer_ID;


	public Student(Connection connect, int student_ID){
        try {
        	this.con = connect;
            this.statement = connect.createStatement();

           this.findStudent(student_ID);
        } 

        catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public Student(Connection connect, String customer_name, String phone, String street, String city, String state, String zip) {
        try {
            this.connect = connect;
            this.statement = connect.createStatement();

            int success = this.makeNewStudent(name, phone, street, city, state, zip);
            if (ret == -1) {
                System.err.println("Student constructor did not insert into database; returned: " + ret);
                System.exit(-1);
            }

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }
	
	//SEES if Customer/Student exists in Record, based on their Customer ID.
	public boolean findStudent(int studentID){
		String search = "SELECT * FROM Customer WHERE Customer_ID = ";
		search = search.concat(Integer.toString(studentID));
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
			
			this.connect.commit();
            statement.close();
            rs.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		return newCustID;
	}
	
	//prints out all the courses 
	public void getCourses(){
		//Should we display Professor_ID since that should be a personal/private identifier?
		String query = "SELECT c.Department, c.Course_Number, c.Professor_ID, p.Professor_Name FROM Course c, Professor p WHERE c.Professor_ID = p.Professor_ID";

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
		String query = "SELECT r.Department, r.Course_Number, r.Professor_ID, t.Title, t.Price FROM RequiredBook r, Textbook t WHERE r.Textbook_Required = t.ISBN AND r.Department = '" + dept + "' AND r.Course_Number = " + Integer.toString(course) + " AND r.Professor_ID = " + Integer.toString(profID) + ";";
		
		try {
			rs = statement.executeQuery(query);
			while (rs.next())
				System.out.println(rs.getString("Department") + " " + rs.getInt("Course_Number") + " || Professor ID: " + rs.getInt("Professor_ID") + " || Text Required: "  + rs.getString("Title") + " Price: " + rs.getDouble("Price"));
		
            statement.close();
            rs.close();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void getVendor(int ISBN){
		String query = "SELECT v.Vendor_ID, v.Vendor_Name, t.ISBN, t.Title FROM VendorArchive v, Vendor ven, Textbook t WHERE v.Vendor_ID = ven.Vendor_ID AND v.ISBN = T.ISBN AND v.Sells = 1 AND v.ISBN =  " + Integer.toString(ISBN) + ";";
	
		try {
			rs = statement.executeQuery(query);
			while(rs.next())
				System.out.println("Vendor: " + rs.getInt("Vendor_ID") + "-" + rs.getString("Vendor_Name") + " || Text: " + rs.getString("ISBN") + " " +  rs.getString("Title"));
			
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
