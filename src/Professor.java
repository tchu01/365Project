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
            rs = statement.executeQuery("SELECT * FROM Professor P WHERE P.Professor_ID = " + professor_ID + ";");
            rs.next();

            this.connect = connect;
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
        try {
            this.connect = connect;
            this.Professor_ID = nextID(connect);
            this.Professor_Name = Professor_Name;
            this.Email = Email;
            this.Department = Department;

            int ret;
            Statement statement = connect.createStatement();
            String q1 = "INSERT INTO Professor (Professor_Name, Email, Department) ";
            q1 += "VALUES (\"" + Professor_Name + "\", \"" + Email + "\", \"" + Department + "\");";
            ret = statement.executeUpdate(q1);
            if (ret != 1) {
                System.err.println("Professor constructor did not insert into database; returned: " + ret);
                System.exit(-1);
            }

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public void addCourse(String Department, int Course_Number) {
        try {
            int ret;
            Statement statement = connect.createStatement();
            String q1 = "INSERT INTO Course (Department, Course_Number, Professor_ID) ";
            q1 += "VALUES (\"" + Department + "\", " + Course_Number + ", " + Professor_ID + ");";
            ret = statement.executeUpdate(q1);

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public boolean courseExists(String Department, int Course_Number) {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            String q1 = "SELECT * FROM Course C WHERE C.Department = \"" + Department + "\" AND C.Course_Number = " + Course_Number + " AND C.Professor_ID = " + this.Professor_ID + ";";
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

    public SQLTableModel getProfessorCoursesTableModel() {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            String q1 = "SELECT * FROM Course C WHERE C.Professor_ID = " + Professor_ID + ";";
            rs = statement.executeQuery(q1);

            SQLTableModel table = new SQLTableModel(rs);
            // statement.close();
            // rs.close();
            return table;
        } catch (SQLException e) {
            Database.printSQLException(e);
        }

        return null;
    }

    public void addTextbook(String Department, int Course_Number, String ISBN, String Title, String Subject, String Author, int Edition) {
        try {
            int ret;
            Statement statement = connect.createStatement();
            String q1 = "INSERT INTO Textbook (ISBN, Title, Subject, Author, Edition) ";
            q1 += "VALUES (\"" + ISBN + "\", \"" + Title + "\", \"" + Subject + "\", \"" + Author + "\", " + Edition + ");";
            ret = statement.executeUpdate(q1);

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

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

    public void addRequiredBook(String Department, int Course_Number, String ISBN) {
        try {
            int ret;
            Statement statement = connect.createStatement();
            String q1 = "INSERT INTO RequiredBook (Department, Course_Number, Professor_ID, Textbook_Required) ";
            q1 += "VALUES (\"" + Department + "\", " + Course_Number + ", " + Professor_ID + ", \"" + ISBN + "\");";
            ret = statement.executeUpdate(q1);

            statement.close();
            this.connect.commit();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
    }

    public boolean requiredBookExists(String Department, int Course_Number, String ISBN) {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            String q1 = "SELECT * FROM RequiredBook R WHERE R.Department = \"" + Department + "\" AND R.Course_Number = " + Course_Number + " AND R.Professor_ID = " + Professor_ID + " AND R.Textbook_Required = \"" + ISBN + "\";";
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

    public void sqltemplate() {
        /*
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("");
            rs.next();

            statement.close();
            rs.close();
        } catch (SQLException e) {
            Database.printSQLException(e);
        }
        */
    }

    public int get_ID() {
        return Professor_ID;
    }

    public String getProfessor_Name() {
        return Professor_Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getDepartment() {
        return Department;
    }

    public static boolean IDExists(Connection connect, int id) {
        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT * FROM Professor P WHERE P.Professor_ID = " + id + ";");
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

    public static int nextID(Connection connect) {
        int count = 0;

        try {
            ResultSet rs;
            Statement statement = connect.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM Professor;");
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
