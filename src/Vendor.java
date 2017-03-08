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

    public Vendor(Connection connect, String Vendor_Name, String Phone_Number, String Street, String State, String Zip_Code) {
        this.connect = connect;
        this.Vendor_ID = nextID(connect);
        this.Vendor_Name = Vendor_Name;
        this.Phone_Number = Phone_Number;
        this.Street = Street;
        this.State = State;
        this.Zip_Code = Zip_Code;
    }

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

    public static int nextID(Connection connect) {
        int count = 0;

        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM Vedor");
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
