package GUI;
import javax.swing.*;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.*;
import Run.*;

public class login {
    public login(User U){
    	JFrame f = new JFrame("��¼");
        JPasswordField value = new JPasswordField();
        value.setBounds(100, 75, 100, 30);
        
        JLabel l1 = new JLabel("�˺�:");
        l1.setBounds(20, 20, 80, 30);
        JTextField text = new JTextField();
        text.setBounds(100, 20, 100, 30);
        
        JLabel l2 = new JLabel("����:");
        l2.setBounds(20, 75, 80, 30);
        JButton b = new JButton("��¼");
        b.setBounds(60, 120, 80, 30);
        JButton c = new JButton("ע��");
        c.setBounds(160, 120, 80, 30);
        
        Dialog  d = new Dialog(f, "����", true);//�����ĶԻ���
        d.setBounds(400, 200, 350, 150);//���õ����Ի����λ�úʹ�С
        d.setLayout(new FlowLayout());//���õ����Ի���Ĳ���Ϊ��ʽ����
        JLabel l3 = new JLabel();//����lab��ǩ��д��ʾ����
        Button ok = new Button("ȷ��");
        d.setLocationRelativeTo(null);//��ʾ��Ļ����
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
        f.setLocationRelativeTo(null);//��ʾ��Ļ����
        f.setLayout(null);
        f.setVisible(true);
        //��¼��ť
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID =text.getText();
                String psw=new String(value.getPassword());
                U.setUser(ID, psw);
                String Info=U.Login();
//                System.out.println(Info);
                if(Info.equals("true")) {
                	System.out.println("�ɹ�");
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
        
        // ȷ����ť������
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
            }
        });
        //�˳�
        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.setVisible(false);//���öԻ��򲻿ɼ�
       
            }});

    }
}