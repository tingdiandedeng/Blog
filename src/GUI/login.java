package GUI;
import javax.swing.*;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.*;
import Run.*;

public class login {
    public login(User U){
    	JFrame f = new JFrame("登录");
        JPasswordField value = new JPasswordField();
        value.setBounds(100, 75, 100, 30);
        
        JLabel l1 = new JLabel("账号:");
        l1.setBounds(20, 20, 80, 30);
        JTextField text = new JTextField();
        text.setBounds(100, 20, 100, 30);
        
        JLabel l2 = new JLabel("密码:");
        l2.setBounds(20, 75, 80, 30);
        JButton b = new JButton("登录");
        b.setBounds(60, 120, 80, 30);
        JButton c = new JButton("注册");
        c.setBounds(160, 120, 80, 30);
        
        Dialog  d = new Dialog(f, "错误", true);//弹出的对话框
        d.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        d.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        JLabel l3 = new JLabel();//创建lab标签填写提示内容
        Button ok = new Button("确定");
        d.setLocationRelativeTo(null);//显示屏幕中央
        d.setResizable(true);
        d.add(l3);
        d.add(ok);
       
        f.add(value);
        f.add(l1);
        f.add(l2);
        f.add(b);
        f.add(c);
        f.add(text);
        f.setSize(300, 300);
        f.setLocationRelativeTo(null);//显示屏幕中央
        f.setLayout(null);
        f.setVisible(true);
        //登录按钮
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID =text.getText();
                String psw=new String(value.getPassword());
                U.setUser(ID, psw);
                String Info=U.Login();
//                System.out.println(Info);
                if(Info.equals("true")) {
                	System.out.println("成功");
                }
                else {
                	l3.setText(Info);
                	d.setVisible(true);
                	return;
                }
            }
        });
        c.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new register(new User());
            }
        });
        
        // 确定按钮监听器
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
            }
        });
        //退出
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.setVisible(false);//设置对话框不可见
       
            }});

    }
}