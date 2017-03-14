import java.sql.*;
import java.util.Scanner;

class Database {
    static Connection connect;
    static Scanner scan = new Scanner(System.in);
    static Professor prof;
    static Student stud;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://cslvm74.csc.calpoly.edu:3306/aquach04?user=aquach04&password=12345678";
            // String jdbc = "jdbc:mysql://localhost:3306/project?user=root&password=123";
            connect = DriverManager.getConnection(jdbc);
            connect.setAutoCommit(false);

            run();

            /*
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

                System.out.println("list courses:");
                p1.listCourses();
                System.out.println();

                boolean exists = p1.courseExists(testCourseDepartment, testCourseNumber);
                if (exists) {
                    System.out.println("Course exists");

                    exists = p1.textbookExists(testISBN);
                    if (exists) {
                        System.out.println("Textbook exists");
                    } else {
                        p1.addTextbook(testCourseDepartment, testCourseNumber, testISBN, testTitle, testSubject, testAuthor, testEdition);
                        System.out.println("Creating " + testTitle + " (ISBN + " + testISBN + ") Textbook");
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
            */

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

    public static void run() {
        System.out.println("Welcome to the Bookstore.");

        String choice = "";
        do {
            System.out.println("Press P to continue as a Professor, S to continue as a Student, and V to continue as a Vendor.");
            System.out.print(">>>");
            choice = scan.next();
        } while(!choice.equals("P") && !choice.equals("S") && !choice.equals("V"));

        switch(choice) {
            case "P":
                professor();
                break;
            case "S":
                student();
                break;
            case "V":
                break;
            default:


        }

        boolean cont = true;


        while (cont) {
            String input = scan.next();
            if (input.equals("QUIT")) {
                cont = false;
            }
        }
    }

    public static void professor() {
        professorLogin();
    }

    public static void professorLogin() {
        boolean cont = true;
        do {
            System.out.println("Enter your professor ID to continue, or type NONE to create a new account:");
            System.out.print(">>>");
            String id = scan.next();
            if (id.equals("NONE")) {
                cont = false;
                System.out.println("Enter your name:");
                System.out.println(">>>");
                String name = scan.next();
                System.out.println("Enter your email:");
                System.out.println(">>>");
                String email = scan.next();
                System.out.println("Enter your department:");
                System.out.println(">>>");
                String department = scan.next();
                prof = new Professor(connect, name, email, department);
            } else {
                try {
                    int id_ = Integer.parseInt(id);
                    if (Professor.IDExists(connect, id_)) {
                        cont = false;
                        prof = new Professor(connect, id_);
                        String yn = "";
                        do {
                            System.out.println("Continue as " + prof.getProfessor_Name() + " with email " + prof.getEmail() + "? (Y/N)");
                            System.out.println(">>>");
                            yn = scan.next();
                            if(yn.equals("N")) {
                                cont = true;
                            }
                        } while (!yn.equals("Y") && !yn.equals("N"));

                    } else {
                        System.out.println("No match for your ID in the database.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter an integer for your ID");
                }
            }
        } while (cont);
    }


    public static void student(){
        boolean cont = true;
        do {
            System.out.println("Enter your Student ID to continue, or type NONE to create a new account:");
            System.out.print(">>>");
            String id = scan.next();
            if (id.equals("NONE")) {
                cont = false;
                System.out.println("Enter your name: ");
                String name = scan.next();
                System.out.println("Enter your phone number: ");
                String phone = scan.next();
                System.out.println("Enter your street address: ");
                String street = scan.next();
                System.out.println("Enter your city: ");
                String city = scan.next();
                System.out.println("Enter your state: ");
                String state = scan.next();
                System.out.println("Enter your zip code: ");
                String zip = scan.next();
                stud = new Student(connect, name, phone, street, city, state, zip);
            } else {
                try {
                    int Sid= Integer.parseInt(id);
                    if (Student.findStudent(connect, Sid)) {
                        cont = false;
                        stud = new Student(connect, Sid);
                        String yn = "";
                        do {
                            System.out.println("Continue as " + stud.getStudentName() + "? (Y/N)");
                            System.out.println(">>>");
                            yn = scan.next();
                            if(yn.equals("N")) {
                                cont = true;
                            }
                        } while (!yn.equals("Y") && !yn.equals("N"));

                    } else {
                        System.out.println("No match for your ID in the database.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter an integer for your ID");
                }
            }
        } while (cont);
    }


}
