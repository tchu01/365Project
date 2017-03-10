import java.sql.*;

class VendorTester {
    static Connection connect;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //String jdbc = "jdbc:mysql://cslvm74.csc.calpoly.edu:3306/tchu01?user=tchu01&password=abc123";
            String jdbc = "jdbc:mysql://localhost:3306/365Project?autoReconnect=true&useSSL=false";
            connect = DriverManager.getConnection(jdbc, "root", "");
            connect.setAutoCommit(false);

            if(Vendor.IDExists(connect, 2)) {
                Vendor v1 = new Vendor(connect, 2);
                System.out.println(v1.getID() + ", " + v1.getName());
                // v1.addNewTextbook("20", "testBookLOL", "meme", "kingpepe", 10, 100);
                // v1.addNewTextbook("19", "testBookLOL", "meme", "kingpepe", 10, 100);
                // v1.addExistingTextbook("18", 100);
                System.out.println(Vendor.IDExists(connect, 2));
                System.out.println(Vendor.nextID(connect));
            }
            Vendor v2 = new Vendor(connect, "Charlie", "2222222222", "Louis", "CA", "94340");




            connect.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
            System.err.println("Exception: " + e);
            e.printStackTrace();
        } catch (SQLException e) {
            printSQLException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printSQLException(SQLException e) {
        System.err.println("SQLException information");
        while (e != null) {
            System.err.println("Error msg: " + e.getMessage());
            System.err.println("SQLSTATE: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
            e.printStackTrace();
            e = e.getNextException();
        }
        System.exit(-1);
    }
}