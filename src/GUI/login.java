package GUI;
import javax.swing.*;

import java.awt.event.*;
import Run.*;

public class login {

    public login(User U){
 
    	JFrame login = new JFrame("登录");
    	login.setSize(300, 300);
    	login.setLocationRelativeTo(null);
        login.setLayout(null);
        login.setResizable(false);
        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel ID = new JLabel("账号:");
        ID.setBounds(20, 20, 80, 30);
        
        JTextField ID_value = new JTextField();
        ID_value.setBounds(100, 20, 100, 30);
        JLabel psw = new JLabel("密码:");
        
        psw.setBounds(20, 75, 80, 30);
    	JPasswordField psw_value = new JPasswordField();
        psw_value.setBounds(100, 75, 100, 30);
        
        JButton login_but = new JButton("登录");
        login_but.setBounds(100, 120, 100, 50);
        JButton register_but = new JButton("注册");
        register_but.setBounds(160, 180, 70, 30);
       
        login.add(psw_value);
        login.add(ID);
        login.add(psw);
        login.add(login_but);
        login.add(register_but);
        login.add(ID_value);
        
        
        login.setVisible(true);
        
        //登录按钮
        login_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID =ID_value.getText();
                String psw=new String(psw_value.getPassword());
                String Info=U.Login(ID,psw);
                if(Info.equals("true")) {
                	new blog_view(U);
                	login.dispose();
                }
                else {
                	new JOptionPane().showMessageDialog(null, Info, "警告", JOptionPane.WARNING_MESSAGE);
                	return;
                }
            }
        });
        //注册按钮监听器
        register_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new register(U);
               login.dispose();
            }
        });
    }
}