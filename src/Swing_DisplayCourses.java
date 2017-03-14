
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author AngelinaQuach
 */
public class Swing_DisplayCourses extends javax.swing.JPanel {
    Student stud;
    SQLTableModel model;

    /**
     * Creates new form Swing_DisplayCourses
     */
    public Swing_DisplayCourses(Student stud, JFrame frame, JPanel panelCont, CardLayout cl) {
        initComponents();
        String dept = stud.getDept();
        String courseID = stud.getCourseID();
        model = stud.getCoursesTableModel(dept, courseID);
        jTable1.setModel(model);

        
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(jTable1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(frame, "Please select a row");
                } else {
                    String profID = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 2);
                    String profName = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 3);
                    stud.setCourseSelection(profID, profName);
                    JPanel requiredBooks = new Swing_RequiredBooks(stud, frame, panelCont, cl);
                    panelCont.add(requiredBooks, "RequiredBooks");
                }
            }
        });
        
        
       /* jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String errorMsg = "";
                String dept = jTextField1.getText();
                String course = jTextField2.getText();
                String prof_id = jTextField3.getText();
                
                if (dept.length() != 3 || !dept.matches("[a-zA-Z]+"))
                    errorMsg+="Wrong Department format e.g. CSC";
                if (course.length()!= 3 || !course.matches("[0-9]+"))
                    errorMsg+= "\nWrong course number format e.g. 100";
           
                if (errorMsg.length() > 0)
                    JOptionPane.showMessageDialog(frame.getComponent(0), errorMsg);
                else{
                    stud.setCourseInfo(dept, course);
                    JPanel displayCourses = new Swing_DisplayCourses(stud, frame, panelCont, cl);
                    panelCont.add(displayCourses, "DisplayCourses");
                    cl.show(panelCont, "DisplayCourses");
                }
            }
        });   */   
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 102, 0));

        jScrollPane1.setBackground(new java.awt.Color(0, 102, 0));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setFont(new java.awt.Font("AppleGothic", 0, 12)); // NOI18N

        jTable1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), null));
        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Department", "Course Number", "Professor ID", "Professor Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setRowHeight(20);
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("AppleGothic", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Select From Below");

        jButton2.setText("Enter");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel4)
                .addGap(0, 114, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
