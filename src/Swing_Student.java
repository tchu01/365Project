import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.lang.*;

public class Swing_Student {
    Connection con;
    Student stud;
    Professor prof;
    Vendor vend;

    public static JFrame frame = new JFrame("EL DATABASE CORRAL BOOKSTORE");//creating instance of JFrame
    Container panelCont = new Container();
    CardLayout cl = new CardLayout();
    JPanel frontPage = new JPanel();
    JPanel studentSignIn  = new JPanel();
    JPanel existingStudent  = new JPanel();
    JPanel createNewStudent  = new JPanel(new GridLayout(0,1));


    public Swing_Student(){

        frame.getContentPane().setBackground(Color.yellow);
        this.panelCont.setLayout(null);
       // this.connect = con;
        panelCont.setLayout(cl);

        frontPage.setLayout(null);
        studentSignIn.setLayout(null);
        existingStudent.setLayout(null);
        createNewStudent.setLayout(null);

        //FRONT PAGE
        JButton button1 = new JButton("Professor");
        JButton button2 = new JButton("Student");
        JButton button3 = new JButton("Vendor");
        button1.setBounds(280,270,220, 70);//x axis, y axis, width, height
        button2.setBounds(280,360,220, 70);//x axis, y axis, width, height
        button3.setBounds(280,450,220, 70);//x axis, y axis, width, height

        frontPage.add(button1);
        frontPage.add(button2);
        frontPage.add(button3);
        frontPage.setBackground(Color.YELLOW);

       /* JPanel frontPanel = new JPanel(new BorderLayout());
        frontPanel.add(frontPage, BorderLayout.NORTH);
        frontPanel.add(new JLabel("WELCOME!",  JLabel.CENTER)); */

        //Student's next page
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                cl.show(panelCont, "2");
            }
        });


        //STUDENT STUFF
        JButton newStudent = new JButton("New Student");
        JButton oldStudent = new JButton("Login with Student ID");
        newStudent.setBounds(280,270,220, 70);//x axis, y axis, width, height
        oldStudent.setBounds(280,400,220, 70);//x axis, y axis, width, height
        studentSignIn.add(newStudent);
        studentSignIn.add(oldStudent);
        studentSignIn.setBackground(Color.YELLOW);



        //IF EXISTING STUDENT (oldStudent)
        existingStudent.setLayout(new FlowLayout());
        JLabel lab1 = new JLabel("Enter Student ID: ");
        existingStudent.add(lab1);
        JButton enter = new JButton("enter");
        JTextField custID = new JTextField(5);
        custID.setText("");
        custID.setColumns(20);

        existingStudent.add(custID);
        existingStudent.add(enter);
        existingStudent.setBackground(Color.YELLOW);
        panelCont.add(existingStudent);


        //IF NEW STUDENT (new Student)
        GridLayout experimentLayout = new GridLayout(0,2);
        createNewStudent.setLayout(new FlowLayout());
        createNewStudent.setLayout(experimentLayout);

        JLabel cust_name = new JLabel("Enter Student Name: ");
        cust_name.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel cust_phone = new JLabel("Enter Student Phone Number: ");
        cust_phone.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel street = new JLabel("Enter Street Address: ");
        street.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel city = new JLabel("Enter City: ");
        city.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel state = new JLabel("Enter State: ");
        state.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel zip_code = new JLabel("Enter Zip Code: ");
        zip_code.setHorizontalAlignment(SwingConstants.CENTER);

        createNewStudent.add(cust_name);
        JTextField c_name = new JTextField();
        c_name.setText("");
        c_name.setColumns(10);
        createNewStudent.add(c_name);

        createNewStudent.add(cust_phone);
        JTextField c_phone = new JTextField();
        c_phone.setText("");
        c_phone.setColumns(10);
        createNewStudent.add(c_phone);

        createNewStudent.add(street);
        JTextField c_street = new JTextField();
        c_street.setText("");
        c_street.setColumns(10);
        createNewStudent.add(c_street);

        createNewStudent.add(city);
        JTextField c_city = new JTextField();
        c_city.setText("");
        c_city.setColumns(10);
        createNewStudent.add(c_city);

        createNewStudent.add(state);
        JTextField c_state = new JTextField();
        c_state.setText("");
        c_state.setColumns(10);
        createNewStudent.add(c_state);

        createNewStudent.add(zip_code);
        JTextField c_zip = new JTextField();
        c_zip.setText("");
        c_zip.setColumns(10);
        createNewStudent.add(c_zip);

        createNewStudent.add(enter);
        createNewStudent.setBackground(Color.YELLOW);
        panelCont.add(createNewStudent);

        //panelCont.add(frontPanel,"0");
        panelCont.add(frontPage, "1");
        panelCont.add(studentSignIn, "2");
        panelCont.add(existingStudent, "3");
        panelCont.add(createNewStudent, "4");

        cl.show(panelCont, "0");
        frame.add(panelCont);


        // IF EXISTING STUDENT BUTTON
        oldStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                cl.show(panelCont, "3");
            }
        });

        // IF NEW STUDENT BUTTON
        newStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                cl.show(panelCont, "4");
            }
        });

        //CONFIRMING EXISTING STUDENT ID
        enter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){

                    int customerID = Integer.parseInt(custID.getText());
                    //MAKE NEW STUDENT
                    //stud = new Student(connection, customerID);
            }
        });





        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800,1000);//400 width and 500 height
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        // Connection connect;
        /* try {
            Class.forName("com.mysql.jdbc.Driver");

            String jdbc = "jdbc:mysql://cslvm74.csc.calpoly.edu:3306/aquach04?user=aquach04&password=12345678";

            // String jdbc = "jdbc:mysql://localhost:3306/project?user=root&password=123";
            System.out.println("fuck me");

            Connection connect = DriverManager.getConnection(jdbc);
            System.out.println("fuck me2");
            connect.setAutoCommit(false);
            connect.close(); */

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Swing();
                }
            });


            //connect.close();
        /* } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
            System.err.println("Exception: " + e);
            e.printStackTrace();
        } catch (SQLException e) {
            printSQLException(e);
        } catch (Exception e) {
            e.printStackTrace();
        } */

    }

}
