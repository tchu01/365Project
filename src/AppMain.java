import java.sql.*;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AppMain {
    static Connection connect;
    static Professor prof;

    JFrame frame = new JFrame("CardLayout");
    JPanel panelCont = new JPanel();
    CardLayout cl = new CardLayout();

    JPanel ProfessorCourses = new Swing_ProfessorCourses(prof, frame, panelCont, cl);
    JPanel ProfessorNewCourse = new Swing_ProfessorNewCourse(prof, frame, panelCont, cl);
  JPanel Home = new Swing_Home(frame, panelCont, cl);

    public AppMain() {
        panelCont.setLayout(cl);

        panelCont.add(ProfessorCourses, "ProfessorCourses");
        panelCont.add(ProfessorNewCourse, "ProfessorNewCourse");
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
            String jdbc = "jdbc:mysql://localhost:3306/project?user=nyee&useSSL=true";
            connect = DriverManager.getConnection(jdbc);
            connect.setAutoCommit(false);

            if(Professor.IDExists(connect, 1)) {
                prof = new Professor(connect, 1);
            } else {
                System.err.println("No professor");
                System.exit(-1);
            }

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
