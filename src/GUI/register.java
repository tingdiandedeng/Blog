package GUI;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

import Run.User;

class register{


	public register(User U) {
		JFrame f = new JFrame("注册");
		 JPasswordField value = new JPasswordField();
       value.setBounds(100, 100, 100, 30);
       
       JLabel l1 = new JLabel("账号:");
       l1.setBounds(20, 20, 80, 30);
       JTextField text1 = new JTextField();
       text1.setBounds(100, 20, 100, 30);
       
       JLabel l2 = new JLabel("用户名:");
       l2.setBounds(20, 60, 80, 30);
       JTextField text2 = new JTextField();
       text2.setBounds(100,60, 100, 30);
       
       JLabel l3 = new JLabel("密码:");
       l3.setBounds(20, 100, 80, 30);
       
       JButton c = new JButton("注册");
       c.setBounds(100, 150, 80, 30);
       JLabel s1 = new JLabel("账号要求:");
       s1.setBounds(200, 20, 80, 30);
       JLabel s2 = new JLabel("用户名要求:");
       s2.setBounds(200, 60, 80, 30);
       JLabel s3 = new JLabel("密码要求:");
       s3.setBounds(200, 100, 80, 30);
       
       Dialog  d = new Dialog(f, "提示", true);
       d.setBounds(400, 200, 350, 150);
       d.setLayout(new FlowLayout());
       Button ok = new Button("确定");
       d.setLocationRelativeTo(null);
       JLabel l4 = new JLabel();
       d.add(l4);
       
       f.add(s1);
       f.add(s2);
       f.add(s3);
       
     f.add(value);
     f.add(l1);
     f.add(l2);
     f.add(l3);
     f.add(c);
     f.add(text1);
     f.add(text2);
     f.setSize(300, 300);
     f.setLocationRelativeTo(null);//显示屏幕中央
     f.setLayout(null);
     f.setVisible(true);
 
     
     c.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
           String ID =text1.getText();
           String username =text2.getText();
           String psw=new String(value.getPassword());
           U.setUser(ID, username, psw);
           String Info=U.SignUp();
           if(Info.equals("true"))
           {
        	   System.out.println("注册成功");
           }
           else
           {
        	l4.setText(Info);
           	d.setVisible(true);
           	return;
           }
        	   
       }
   });
     // 确定按钮监听器
     ok.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             d.setVisible(false);
         }
     });
     //X按钮监听器
     d.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
             d.setVisible(false);//设置对话框不可见
    
         }});
	}
}
