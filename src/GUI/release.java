package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import Run.User;

public class release {
public release(User U) {
	JFrame release=new JFrame("博客发表");
	release.setSize(500, 300);
	release.setLayout(null);
	release.setLocationRelativeTo(null);
	release.setResizable(false);
	JTextArea content=new JTextArea();
	
	JScrollPane release_panel=new JScrollPane(content);
	release_panel.setBounds(30, 50, 400, 200);
	
	
	release.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	release.addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e) {
    	 release.dispose();
     	new blog_view(U);
     }
     });
     
	JButton release_but=new JButton("发表");
	release_but.setBounds(350, 2, 100, 30);
	release_but.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 String Info=null;
        	 String blog_content=content.getText();
        	 Info=U.PostBlog(blog_content);
        	 if(Info.equals("发表成功！"))
        	 {
        		 new JOptionPane().showMessageDialog(null, Info, "提示", JOptionPane.INFORMATION_MESSAGE);
	        	 new blog_view(U);
	             release.dispose();
        	 }
        	 else
        		 new JOptionPane().showMessageDialog(null, Info, "警告", JOptionPane.WARNING_MESSAGE);
        	 
         }
     });
	JButton return_mian=new JButton("←");
	return_mian.setBounds(10, 2, 100, 30);
	return_mian.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            new blog_view(U);
            release.dispose();
        }
    });
	
	release.add(release_panel);
	release.add(return_mian);
	release.add(release_but);
	release.setVisible(true);
	
}

}
