import java.util.*;
import java.sql.*;

public class Student {
	private Connection con; 			//Connection that persists through entire transaction.	
	static ResultSet rs;
	private static Statement statement;
	private String student_name;
        private int student_ID;
        private static String dept;
        private static String courseID;

	public Student(Connection connect, int student_ID){
        try {
        	this.con = connect;
                this.student_ID = student_ID;
                this.statement = connect.createStatement();

           findStudent(connect,student_ID);
        } 

        catch (SQLException e) {
            System.out.println("OH NO");
            Database.printSQLException(e);
        }
    }

    public Student(Connection connect, String customer_name, String phone, String street, String city, String state, String zip) {
        try {
            this.con = connect;
            this.statement = connect.createStatement();

            int success = this.makeNewStudent(customer_name, phone, street, city, state, zip);
            if (success == -1) {
                System.err.println("Student constructor did not insert into database; returned: " + success);
                System.exit(-1);
            }

			student_name = customer_name;
            statement.close();
            this.con.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public String getStudentName(){
		return this.student_name;
	}
	//SEES if Customer/Student exists in Record, based on their Customer ID.
	public static boolean findStudent(Connection con, int studentID){
		String search = "SELECT * FROM Customer WHERE Customer_ID = ";
		search = search.concat(Integer.toString(studentID));
		search = search.concat(";");
		boolean studentExists = true;
	
		try{
			statement = con.createStatement();
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

	public void setCourseInfo(String dept, String courseID){
            this.dept = dept;
            this.courseID = courseID;
            System.out.println("successful set");
        }
        
        public String getDept(){
            return this.dept;
        }
        public String getCourseID(){
            return this.courseID;
        }
	//If student doesn't exist, makes a new student and gets their customer_ID. If Customer_ID = -1, means customer wasn't successfully made.
	//Needs to be called by MAIN.
	public int makeNewStudent(String name, String phone, String street, String city, String state, String zip){
		int newCustID = -1;
		String search = "INSERT INTO Customer(Customer_Name, Phone_Number, Street, City, State, Zip_Code) VALUES(";
		String query ="'" + name +  "', '" + phone + "', '" + street + "', '" + city + "', '" + state + "', '" + zip + "');";
		search = search.concat(query);
		//System.out.println("INSERT CUST QUERY: " + search);

		try {
			statement.executeUpdate(search);
			System.out.println("INSERT CUST QUERY: " + search);

			String getID = "SELECT Customer_ID FROM Customer ORDER BY Customer_ID DESC LIMIT 1;";
			rs = statement.executeQuery(getID);
			rs.next();
			newCustID = rs.getInt("Customer_ID");
			System.out.println("NEW CUST ID: " + newCustID);
			this.con.commit();
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
		String query = "SELECT v.Vendor_ID, v.Vendor_Name, t.ISBN, t.Title, v.Price FROM VendorArchive v, Vendor ven, Textbook t WHERE v.Vendor_ID = ven.Vendor_ID AND v.ISBN = T.ISBN AND v.ISBN =  " + Integer.toString(ISBN) + ";";
	
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
		String insertQuery = "INSERT INTO Transaction(Vendor_ID, Customer_ID, ISBN) VALUES(" + Integer.toString(vendorID) + ", '" + Integer.toString(isbn) + "', " + Integer.toString(custID) + ");"; 
		int result = -1; 		//default failed operation
		
		try {
			result = statement.executeUpdate(insertQuery);


		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;	
	}
        
        
        public SQLTableModel getCoursesTableModel(String dept, String course_ID) {
        try {
            ResultSet rs;
            Statement statement = con.createStatement();
            String query = "SELECT c.Department, c.Course_Number, c.Professor_ID, p.Professor_Name FROM Course c, Professor p WHERE c.Department = '" + dept + "' AND c.Course_Number = " + course_ID + ";" ;
            System.out.println(query);
            rs = statement.executeQuery(query);

            SQLTableModel table = new SQLTableModel(rs);
            // statement.close();
            // rs.close();
            return table;
            
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

        return null;
    }

}
