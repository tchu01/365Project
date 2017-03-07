import java.sql.*;

class database {
    static Connection connect;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://cslvm74.csc.calpoly.edu:3306/tchu01?user=tchu01&password=abc123");


            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Customer");
            while(rs.next()) {
                int customerID = rs.getInt(1);
                System.out.println("Customer id: " + customerID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}