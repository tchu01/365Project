import java.sql.*;

class Database {
    static Connection connect;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // String jdbc = "jdbc:mysql://cslvm74.csc.calpoly.edu:3306/tchu01?user=tchu01&password=abc123";
            String jdbc = "jdbc:mysql://localhost:3306/project?user=root&password=123";
            connect = DriverManager.getConnection(jdbc);
            connect.setAutoCommit(false);

            // Professor
            int testID = 21;
            String testProfessorName = "Timothy Chu";
            String testEmail = "tchu01@calpoly.edu";
            String testProfessorDepartment = "CSC";
            // Course
            String testCourseDepartment = "CPE";
            int testCourseNumber = 101;

            // Textbook
            String testISBN = "20";
            String testTitle = "Intro to Java";
            String testSubject = "CPE";
            String testAuthor = "Nathan Yee";
            int testEdition = 1;

            if (Professor.IDExists(connect, testID)) {
                Professor p1 = new Professor(connect, testID);
                System.out.println("Professor exists");

                boolean exists = p1.courseExists(testCourseDepartment, testCourseNumber);
                if (exists) {
                    System.out.println("Course exists");

                    exists = p1.textbookExists(testISBN);
                    if (exists) {
                        System.out.println("Textbook exists");
                    } else {
                        p1.addTextbook(testCourseDepartment, testCourseNumber, testISBN, testTitle, testSubject, testAuthor, testEdition);
                        System.out.println("Creating " + testTitle + " (ISBN + " + testISBN+ ") Textbook");
                    }

                    exists = p1.requiredBookExists(testCourseDepartment, testCourseNumber, testISBN);
                    if (exists) {
                        System.out.println("RequiredBook exists");
                    } else {
                        p1.addRequiredBook(testCourseDepartment, testCourseNumber, testISBN);
                        System.out.println("Creating RequiredBook");
                    }
                } else {
                    p1.addCourse(testCourseDepartment, testCourseNumber);
                    System.out.println("Creating " + testCourseDepartment + " " + testCourseNumber + " Course");
                }

            } else {
                Professor p2 = new Professor(connect, testProfessorName, testEmail, testProfessorDepartment);
                System.out.println("Creating Professor: " + p2.get_ID() + ", " + p2.getProfessor_Name() + ", " + p2.getEmail() + ", " + p2.getDepartment());
            }

            // ResultSet rs;
            // Statement statement = connect.createStatement();
            // rs = statement.executeQuery("SELECT * FROM Customer");
            // while (rs.next()) {
            //     int customerID = rs.getInt(1);
            //     System.out.println("Customer id: " + customerID);
            // }
            // statement.close();
            // rs.close();

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