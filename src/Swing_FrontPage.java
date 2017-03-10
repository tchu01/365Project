import javax.swing.*;
public class FrontPage {
    public static void main(String[] args) {
        JFrame frame=new JFrame();//creating instance of JFrame

        JButton b=new JButton("Professor");//creating instance of JButton
        b.setBounds(130,20,100, 40);//x axis, y axis, width, height

        frame.add(b);//adding button in JFrame

        JButton s=new JButton("Student");//creating instance of JButton
        s.setBounds(130,80,100, 40);//x axis, y axis, width, height

        frame.add(s);//adding button in JFrame

        JButton v=new JButton("Vendor");//creating instance of JButton
        v.setBounds(130,140,100, 40);//x axis, y axis, width, height

        frame.add(v);//adding button in JFrame

        frame.setSize(400,500);//400 width and 500 height
        frame.setLayout(null);//using no layout managers
        frame.setVisible(true);//making the frame visible
    }
}  
