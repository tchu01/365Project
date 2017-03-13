import java.sql.*;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Swing_VendorExample {
    static Connection connect;
    static Vendor v;

    JFrame frame = new JFrame("CardLayout");
    JPanel panelCont = new JPanel();
    CardLayout cl = new CardLayout();

    JPanel VendorTextbooks = new Swing_VendorTextbooks(v, frame, panelCont, cl);
    JPanel VendorNewTextbook = new Swing_VendorNewTextbook(v, frame, panelCont, cl);
    JPanel VendorArchiveTextbooks = new Swing_VendorArchiveTextbooks(v, frame, panelCont, cl);


    public Swing_VendorExample() {
        panelCont.setLayout(cl);

        panelCont.add(VendorTextbooks, "VendorTextbooks");
        panelCont.add(VendorNewTextbook, "VendorNewTextbook");
        panelCont.add(VendorArchiveTextbooks, "VendorArchiveTextbooks");
        cl.show(panelCont, "VendorTextbooks");

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
            String jdbc = "jdbc:mysql://localhost:3306/365Project?autoReconnect=true&useSSL=false";
            connect = DriverManager.getConnection(jdbc, "root", "");
            connect.setAutoCommit(false);

            if(Vendor.IDExists(connect, 1)) {
                v = new Vendor(connect, 1);
            } else {
                System.err.println("No vendor");
                System.exit(-1);
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Swing_VendorExample();
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