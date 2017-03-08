/**
 * Created by timothy on 3/7/17.
 */

import java.sql.*;

public class Professor {
    private Connection connect;
    private int Professor_ID;
    private String Professor_Name;
    private String Email;
    private String Department;

    public Professor(Connection connect, int professor_ID) {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Professor P WHERE P.Professor_ID = " + professor_ID);
            rs.next();
            this.Professor_ID = rs.getInt("Professor_ID");
            this.Professor_Name = rs.getString("Professor_Name");
            this.Email = rs.getString("Email");
            this.Department = rs.getString("Department");

            statement.close();
            rs.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public Professor(Connection connect, String Professor_Name, String Email, String Department) {
        this.connect = connect;
        this.Professor_ID = nextID(connect);
        this.Professor_Name = Professor_Name;
        this.Email = Email;
        this.Department = Department;
    }

    public static boolean IDExists(Connection connect, int id) {
        boolean exists = false;
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Professor P WHERE P.Professor_ID = " + id);
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
            rs = statement.executeQuery("SELECT COUNT(*) FROM Professor");
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
