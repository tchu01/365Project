import java.sql.*;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AppMain {
    public static Connection connect;

  //FIX LATER
  Student stud;
  
  JFrame frame = new JFrame("CardLayout");
  JPanel panelCont = new JPanel();
  CardLayout cl = new CardLayout();

  JPanel Home = new Swing_Home(frame, panelCont, cl);
  JPanel profLogin = new Swing_ProfessorLogin(frame, panelCont, cl);
  JPanel studentLogin = new Swing_StudentLogin(frame, panelCont, cl);
  JPanel newProf = new Swing_NewProfessor(frame, panelCont, cl);
  JPanel newStudent = new Swing_NewStudent(stud, frame, panelCont, cl);
  JPanel existingStudent = new Swing_ExistingStudent(stud, frame, panelCont, cl);
  JPanel enterCourses = new Swing_EnterCourse(stud, frame, panelCont, cl);
  JPanel displayCourses = new Swing_DisplayCourses(stud, frame, panelCont, cl);

    public AppMain() {
        panelCont.setLayout(cl);


        panelCont.add(profLogin, "ProfessorLogin");
        panelCont.add(studentLogin, "StudentLogin");
        panelCont.add(newProf, "NewProfessor");
        panelCont.add(newStudent, "newStudent");
        panelCont.add(existingStudent, "existingStudent");
        panelCont.add(enterCourses, "EnterCourses");
        panelCont.add(displayCourses, "DisplayCourses");

        panelCont.add(Home, "Home");

        cl.show(panelCont, "Home");


        frame.add(panelCont);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    connect.close();
                } catch (SQLException ex) {
                    Database.printSQLException(ex);
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://localhost:3306/project?user=root&password=123";
            connect = DriverManager.getConnection(jdbc);
            connect.setAutoCommit(false);

            // if(Professor.IDExists(connect, 1)) {
            //     prof = new Professor(connect, 1);
            // } else {
            //     System.err.println("No professor");
            //     System.exit(-1);
            // }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new AppMain();
                }
            });

        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
            System.err.println("Exception: " + e);
            e.printStackTrace();
        } catch (SQLException e) {
            Database.printSQLException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
