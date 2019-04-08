package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Run.User;

public class Reply_GUI {
	public Reply_GUI(User U,String BID,String fromUID,String toUID) {
		JFrame f=new JFrame("内容");
		f.setResizable(false);
        f.setSize(400, 300);
    	Container reply=f.getContentPane();
        reply.setSize(400, 300);
        f.setLocationRelativeTo(null);
        reply.setLayout(null);
        reply.setBackground(Color.WHITE);
        
        JTextArea reply_content=new JTextArea();
        reply_content.setBounds(0, 0, 400, 200);
        reply.add(reply_content);
        
        JButton reply_sure=new JButton("确定");
        reply_sure.setBounds(150, 200, 100, 50);
        reply.add(reply_sure);
        f.setVisible(true);
        
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        	f.dispose();
        	new blog_view(U);
        }
        });
        
        reply_sure.addActionListener(new ActionListener() {
        	String In;
        	JFrame A;
        	public void actionPerformed(ActionEvent e) {
        		String Content =reply_content.getText();
        		String Info=U.Reply(BID,fromUID,toUID,Content);
        		if(Info.equals("评论成功！") || Info.equals("回复成功！"))
        		{
        			new JOptionPane().showMessageDialog(null, Info, "提示", JOptionPane.INFORMATION_MESSAGE);
	        		f.dispose();
	        		new blog_view(U);
        		}
        		else
        			new JOptionPane().showMessageDialog(null, Info, "警告", JOptionPane.WARNING_MESSAGE);
            }
        	
        });
          
	}
}
