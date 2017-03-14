import java.util.*;
import java.sql.*;

public class Student {
	private Connection con; 			//Connection that persists through entire transaction.	
	private int student_ID;
        private String student_name;
        private String phone;
        private String address;
        private String city;
        private String state;
        private String zip;
    
        private String dept;
        private String courseID;
        private String profID;
        private String profName;
        
        private String vendorID;
        private String isbn;
        private String price;
       

	public Student(Connection connect, int student_ID){
        try {
        	this.con = connect;
                this.student_ID = student_ID;
                ResultSet rs;
                Statement statement = connect.createStatement();
                String cust_ID = Integer.toString(student_ID);
                rs = statement.executeQuery("SELECT * FROM Customer c WHERE c.Customer_ID = " + cust_ID + ";");
                rs.next();
           
                this.student_ID = rs.getInt("Customer_ID");
                this.student_name = rs.getString("Customer_Name");
                this.phone = rs.getString("Phone_Number");
                this.address = rs.getString("Street");
                this.city = rs.getString("City");
                this.state = rs.getString("State");
                this.zip = rs.getString("Zip_Code");


          // findStudent(connect,student_ID);
        } 
        catch (SQLException e) {
            System.out.println("OH NO");
            Database.printSQLException(e);
        }
    }

    public Student(Connection connect, String customer_name, String phone, String street, String city, String state, String zip) {
        try {
            this.con = connect;
            Statement statement = connect.createStatement();

            this.makeNewStudent(customer_name, phone, street, city, state, zip);

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
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(search);
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

        public void setStudentInfo(String name, String phone, String address, String city, String state, String zip){
            this.student_name = name;
            this.phone = phone;
            this.address = address;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }
        
	public void setCourseInfo(String dept, String courseID){
            this.dept = dept;
            this.courseID = courseID;
        }
        
        public String getDept(){
            return this.dept;
        }
        public String getCourseID(){
            return this.courseID;
        }
	//If student doesn't exist, makes a new student and gets their customer_ID. If Customer_ID = -1, means customer wasn't successfully made.
	//Needs to be called by MAIN.
	public void makeNewStudent(String name, String phone, String street, String city, String state, String zip){
		String search = "INSERT INTO Customer(Customer_Name, Phone_Number, Street, City, State, Zip_Code) VALUES(";
		String query ="'" + name +  "', '" + phone + "', '" + street + "', '" + city + "', '" + state + "', '" + zip + "');";
		search = search.concat(query);
		//System.out.println("INSERT CUST QUERY: " + search);

		try {
                        Statement statement = con.createStatement();
                        ResultSet rs;
			statement.executeUpdate(search);
			System.out.println("INSERT CUST QUERY: " + search);

			String getID = "SELECT Customer_ID FROM Customer ORDER BY Customer_ID DESC LIMIT 1;";
			rs = statement.executeQuery(getID);
			rs.next();
			this.student_ID = rs.getInt("Customer_ID");
			this.con.commit();
                        statement.close();
                        rs.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//prints out all the courses 
	public void getCourses(){
		//Should we display Professor_ID since that should be a personal/private identifier?
		String query = "SELECT c.Department, c.Course_Number, c.Professor_ID, p.Professor_Name FROM Course c, Professor p WHERE c.Professor_ID = p.Professor_ID";

		try {	
                        ResultSet rs;
                        Statement statement = con.createStatement();
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
	
        public void setCourseSelection(String profID, String profName){
            this.profID = profID;
            this.profName = profName;
        }
        
        public void setBookSelection(String vendorID, String isbn, String price){
            this.vendorID = vendorID;
            this.isbn = isbn;
            this.price = price;
            
        }
	//student select class and professor id to query books
	public void getRequiredBooks(String dept, int course, int profID){
		String query = "SELECT r.Department, r.Course_Number, r.Professor_ID, t.Title, t.Price FROM RequiredBook r, Textbook t WHERE r.Textbook_Required = t.ISBN AND r.Department = '" + dept + "' AND r.Course_Number = " + Integer.toString(course) + " AND r.Professor_ID = " + Integer.toString(profID) + ";";
		
		try {   
                        ResultSet rs;
                        Statement statement = con.createStatement();

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
                        Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
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
	public void makeTransaction(){
            String insertQuery = "INSERT INTO Transaction(Vendor_ID, Customer_ID, ISBN, Price) VALUES ('" 
                    + this.vendorID + "' , " + this.student_ID + ", '" + this.isbn + "', " + this.price +");"; 
           
		try {
                        Statement statement = con.createStatement();
			statement.executeUpdate(insertQuery);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
        
        
        public SQLTableModel getCoursesTableModel(String dept, String course_ID) {
        try {
            ResultSet rs;
            Statement statement = con.createStatement();
            String query = "SELECT c.Department, c.Course_Number, c.Professor_ID, p.Professor_Name FROM Course c, Professor p WHERE c.Department = '" + dept + "' AND c.Course_Number = " + course_ID + ";" ;
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

        public SQLTableModel getRequiredBooksTableModel() {
        try {
            ResultSet rs;
            Statement statement = con.createStatement();
            String query = "SELECT V.Vendor_ID, V.Vendor_Name, T.Title, VA.ISBN, VA.Price" 
            + " FROM RequiredBook RB, Course C, Textbook T, VendorArchive VA, Vendor V"
            + " WHERE RB.Department = C.Department"
            + " AND RB.Course_Number = C.Course_Number"
            + " AND RB.Professor_ID = C.Professor_ID"
            + " AND RB.Textbook_Required = T.ISBN"
            + " AND VA.ISBN = T.ISBN"
            + " AND VA.Vendor_ID = V.Vendor_ID"
            + " AND RB.Department = '" + this.dept
            + "' AND RB.Course_Number = " + this.courseID 
            + " AND RB.Professor_ID = " + this.profID + ";";

            System.out.println("Required books query: " + query);
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
        
        public SQLTableModel getTransactionTableModel() {
        try {
            ResultSet rs;
            Statement statement = con.createStatement();
            System.out.println("VENDOR ID " + this.vendorID);
            System.out.println("STUDENT ID: " + this.student_ID);
            System.out.println("ISBN " + this.isbn);
            String query =  "SELECT V.Vendor_Name, C.Customer_Name, T.Title, VA.Price, TS.Purchase_Date"
                            + " FROM Vendor V, Customer C, Textbook T, VendorArchive VA, Transaction TS"
                            + " WHERE V.Vendor_ID = " + this.vendorID
                            + " AND C.Customer_ID = " + this.student_ID
                            + " AND T.ISBN = " + this.isbn
                            + " AND VA.ISBN = " + this.isbn
                            + " AND VA.Vendor_ID = " + this.vendorID
                            + " AND VA.Vendor_ID = V.Vendor_ID"
                            + " AND VA.ISBN = T.ISBN"
                            + " AND TS.Vendor_ID = V.Vendor_ID"
                            + " AND TS.Customer_ID = C.Customer_ID"
                            + " AND TS.ISBN = T.ISBN"
                            + " AND TS.ISBN = " + this.isbn + ";";
            
            System.out.println("transaction query: " + query);
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
