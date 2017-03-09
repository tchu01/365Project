/**
 * Created by Daniel on 3/8/17.
 */

import java.sql.*;

public class Vendor {
    private Connection connect;
    private int Vendor_ID;
    private String Vendor_Name;
    private String Phone_Number;
    private String Street;
    private String State;
    private String Zip_Code;

    /**
     * Constructor for an existing vendor in the Vendor table
     * @param connect {@code Connection}
     * @param Vendor_ID {@code int}
     */
    public Vendor(Connection connect, int Vendor_ID) {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Vendor V WHERE V.Vendor_ID = " + Vendor_ID);
            rs.next();
            this.Vendor_ID = rs.getInt("Vendor_ID");
            this.Vendor_Name = rs.getString("Vendor_Name");
            this.Phone_Number = rs.getString("Phone_Number");
            this.Street = rs.getString("Street");
            this.State = rs.getString("State");
            this.Zip_Code = rs.getString("Zip_Code");

            statement.close();
            rs.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    /**
     * Constructor that creates a new Vendor to use and adds it to the Vendor table
     * @param connect {@code Connection}
     * @param Vendor_Name {@code String}
     * @param Phone_Number {@code String} 
     * @param Street {@code String}
     * @param State {@code String}
     * @param Zip_Code {@code String}
    */
    public Vendor(Connection connect, String Vendor_Name, String Phone_Number, String Street, String State, String Zip_Code) {
        this.connect = connect;
        this.Vendor_ID = nextID(connect);
        this.Vendor_Name = Vendor_Name;
        this.Phone_Number = Phone_Number;
        this.Street = Street;
        this.State = State;
        this.Zip_Code = Zip_Code;

        try {
            int ret;
            Statement statement = connect.createStatement();
            String q1 = "INSERT INTO Vendor (Vendor_ID, Vendor_Name, Phone_Number, Street, City, State, Zip_Code) ";
            q1 += "VALUES (\"" + this.Vendor_ID + "\", \"" + this.Vendor_Name + "\", \"" + this.Phone_Number + "\",
                \"" + this.Phone_Number + "\", \"" + this.Street + "\", \"" + this.State + "\", "+ this.Zip_Code + ");";
            ret = statement.executeUpdate(q1);

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    /**
     * Adds a new Textbook tuple and a new VendorArchive tuple that shows that this vendor owns the new textbook
     * @param ISBN {@code String} of textbook 
     * @param Title {@code String} of textbook 
     * @param Subject {@code String} of textbook 
     * @param Author {@code String} of textbook 
     * @param Edition {@code int} of textbook 
     * @param Price {@code int} of textbook for this vendor
    */
    public void addNewTextbook(String ISBN, String Title, String Subject, String Author, int Edition, int Price) {
        try {
            int ret;
            Statement statement = connect.createStatement();
            String q1 = "INSERT INTO Textbook (ISBN, Title, Subject, Author, Edition) ";
            q1 += "VALUES (\"" + ISBN + "\", \"" + Title + "\", \"" + Subject + "\", \"" + Author + "\", " + Edition + ");";
            ret = statement.executeUpdate(q1);

            String q2 = "INSERT INTO VendorArchive (Vendor_ID, ISBN, Price) ";
            q2 += "VALUES (\"" + this.Vendor_ID + "\", \"" + ISBN + "\", " + Price + ");";
            ret = statement.executeUpdate(q2);

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

    }

    /**
     * Addsa new VendorArchive tuple that shows that this vendor owns an existing textbook
     * @param ISBN {@code String} of textbook 
     * @param Price {@code int} of textbook for this vendor
    */
    public void addExistingTextbook(String ISBN, int Price) {
        try {
            int ret;
            String q1 = "INSERT INTO VendorArchive (Vendor_ID, ISBN, Price) ";
            q1 += "VALUES (\"" + this.Vendor_ID + "\", \"" + ISBN + "\", " + Price + ");";
            ret = statement.executeUpdate(q1);

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

    }

    /**
     * Checks to see if a particular textbook exists in the database
     * @param ISBN {@code String} of textbook 
     * @return true if textbook exists, false if textbook doesn't exist
     *
    */
    public boolean textbookExists(String ISBN) {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            String q1 = "SELECT * FROM Textbook T WHERE T.ISBN = \"" + ISBN + "\";";
            rs = statement.executeQuery(q1);
            if (rs.next()) {
                statement.close();
                rs.close();
                return true;
            }

            statement.close();
            rs.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

        return false;
    }

    /**
     * Checks to see if a particular ID exists in the vendor table
     * @param connect {@code Connection}
     * @param id {@code int} that we are checking
     * @return true if ID exists, false if ID doesn't exist
     *
    */
    public static boolean IDExists(Connection connect, int id) {
        boolean exists = false;
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Vendor V WHERE V.Vendor_ID = " + id);
            if (rs.next()) {
                exists = true;
            }

            statement.close();
            rs.close();

            return exists;
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

        return exists;
    }

    /**
     * Gives us the next ID to use for our vendors
     * @param connect {@code Connection}
     * @return the next ID available to use
    */
    public static int nextID(Connection connect) {
        int count = 0;

        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM Vendor");
            if (rs.next()) {
                count = rs.getInt(1);
            }

            statement.close();
            rs.close();

            return count + 1;
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

        return count + 1;
    }

}
