import java.sql.*;

class Database {
    static Connection connect;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // String jdbc = "jdbc:mysql://cslvm74.csc.calpoly.edu:3306/tchu01?user=tchu01&password=abc123";
            String jdbc = "jdbc:mysql://localhost:3306/365Project?user=root&password=123";
            connect = DriverManager.getConnection(jdbc);

            int newID = Professor.nextID(connect);
            System.out.println(newID);

            // Example
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Customer");
            while (rs.next()) {
                int customerID = rs.getInt(1);
                System.out.println("Customer id: " + customerID);
            }
            statement.close();
            rs.close();

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
        while(e != null) {
            System.err.println("Error msg: " + e.getMessage());
            System.err.println("SQLSTATE: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
            e.printStackTrace();
            e = e.getNextException();
        }
    }
}