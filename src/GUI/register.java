package GUI;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

import Run.User;

class register{
	public register(User U) {
	   JFrame register = new JFrame("注册");
	   register.setSize(450, 300);
	   register.setLocationRelativeTo(null);
	   register.setLayout(null); 
	   register.setResizable(false);
	   register.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   
	   register.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	   register.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
    	   register.dispose();
       	new login(U);
       }
       });
	   //文字
       JLabel ID = new JLabel("账号:");
       ID.setBounds(20, 20, 80, 30);
       ID.setFont(new Font("楷体",Font.BOLD,20));
       JTextField ID_value = new JTextField();
       ID_value.setBounds(100, 20, 100, 30);
       
       JLabel username = new JLabel("用户名:");
       username.setBounds(20, 60, 80, 30);
       username.setFont(new Font("楷体",Font.BOLD,20));
       JTextField username_value = new JTextField();
       username_value.setBounds(100,60, 100, 30);
       
       JLabel psw = new JLabel("密码:");
       psw.setBounds(20, 100, 80, 30);
       psw.setFont(new Font("楷体",Font.BOLD,20));
	   JPasswordField psw_value = new JPasswordField();
       psw_value.setBounds(100, 100, 100, 30);
       
       //按钮
       JButton register_but = new JButton("注册");
       register_but.setBounds(150, 150, 100, 50);
       register_but.setFont(new Font("楷体",Font.BOLD,15));
       JLabel ID_limit = new JLabel("账号包含数字与字母，且不超过10位");
       ID_limit.setFont(new Font("楷体",Font.BOLD,13));
       ID_limit.setBounds(200, 20, 300, 30);
       JLabel username_limit = new JLabel("用户名不能大于8位");
       username_limit.setFont(new Font("楷体",Font.BOLD,13));
       username_limit.setBounds(200, 60, 300, 30);
       JLabel psw_limit = new JLabel("密码包含数字与字母，且不超过10位");
       psw_limit.setFont(new Font("楷体",Font.BOLD,13));
       psw_limit.setBounds(200, 100, 300, 30);
       
       register.add(ID_limit);
       register.add(username_limit);
       register.add(psw_limit);
       register.add(psw_value);
       register.add(ID);
       register.add(username);
       register.add(psw);
       register.add(register_but);
       register.add(ID_value);
       register.add(username_value);

       register.setVisible(true);
 
     //注册按钮
       register_but.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
           String ID =ID_value.getText();
           String username =username_value.getText();
           String psw=new String(psw_value.getPassword());
           String Info=U.SignUp(ID,psw,username);
           if(Info.equals("true"))
           {
        	   new JOptionPane().showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
               register.dispose();
               new login(U);
           }
           else
           {
           new JOptionPane().showMessageDialog(null, Info, "警告", JOptionPane.WARNING_MESSAGE);
           	return;
           }   
       }
   });
    
	}
}
