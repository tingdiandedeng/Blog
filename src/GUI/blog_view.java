package GUI;

import javax.swing.*;

import Run.Blog;
import Run.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class blog_view extends JFrame {
	public  blog_view(User U) {
			JFrame blog_view=new JFrame("主界面");
		    blog_view.setSize(980, 830);
		    blog_view.setLocationRelativeTo(null);
		    blog_view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    blog_view.setResizable(false);
		    JPanel left_panel = new JPanel();//面板1
		    left_panel.setLayout(null);
		    left_panel.setSize(120,400);
		    
		    Container right_panel =getContentPane();//容器2
//		    right_panel.setSize(880,400);
		    
		    
		    //获取所有博客内容
		    ArrayList<Blog> Blogs =new ArrayList<Blog>();
		    U.getAllBlogs(Blogs);
		    
			//主面板
		    JPanel blog=new JPanel();
		    
		    int SumOfReply=0;
		    for(int f=0;f<Blogs.size();f++)
		    {
		    	SumOfReply+=Blogs.get(f).getReplySize();
		    }
		    blog.setPreferredSize(new Dimension(800,150*Blogs.size()+35*SumOfReply));
		    blog.setLayout(null);
		    blog_view.setVisible(true);
		    
		    //滚动窗格
		    JScrollPane blog_jScrollPane=new JScrollPane(blog);
	        blog_jScrollPane.setBounds(100, 250, 400, 200);
	        getContentPane().add(blog_jScrollPane);
	        blog_jScrollPane.getVerticalScrollBar().setUnitIncrement(10);
	        
	        //内容
	        int NumberOfReply=0;
	        for (int i = 0; i < Blogs.size(); i++) {
	            String name=Blogs.get(i).getUname();
	            
	            //获取时间
	            Date time=Blogs.get(i).getTimestamp();
	            long a=(new Date().getTime()-time.getTime())/1000;
	            String timestamp=null;
	            if(a<60)
	            	timestamp="刚刚";
	            else if(a<60*60)
	            	timestamp=(int)Math.floor(a/60)+"分钟前";
	            else if(a<60*60*24)
	            	timestamp=(int)Math.floor(a/3600)+"小时前";
	            else
	            	timestamp=(int)Math.floor(a/3600/24)+"天前";
	            
	            //获取内容
	            String content=Blogs.get(i).getContent();
	            JLabel user_name=new JLabel(name);
	            user_name.setFont(new java.awt.Font("黑体", 1, 17));
	            blog.add(user_name);
	            user_name.setBounds(20, 20+150*i+NumberOfReply*35, 60, 20);
	            
	            JLabel Release_time=new JLabel(timestamp);
	            blog.add(Release_time);
	            Release_time.setBounds(220, 20+150*i+NumberOfReply*35, 160, 20);
	            
	            //点赞
	            JButton like=new JButton("❤");
	            blog.add(like);
	            like.setBounds(620, 100+150*i+NumberOfReply*35, 50, 30);
	            like.addActionListener(new ActionListener() {
	            	String BID;
	                public void actionPerformed(ActionEvent e) {
	                   String Info=U.Like(BID);
	                   new JOptionPane().showMessageDialog(null, Info, "警告", JOptionPane.WARNING_MESSAGE);
	                   blog_view.dispose();
	                   new blog_view(U);
	                   return;
	                }
	                public ActionListener accept(String bID) {
	                    this.BID = bID;
	                    return this;
	                }
	            }.accept(Blogs.get(i).getBID()));
	            
	          //读取点赞人信息
	            Blog B=Blogs.get(i);
	            String LikePeople=B.getLike();
	            JLabel LikeInfo=new JLabel(LikePeople);
	            blog.add(LikeInfo);
	            LikeInfo.setBounds(40, 100+150*i+NumberOfReply*35, 450, 20);

	            //评论按钮
	            JButton reply=new JButton("评论");
	            blog.add(reply);
	            reply.setBounds(690, 100+150*i+NumberOfReply*35, 80, 30);
	            reply.addActionListener(new ActionListener() {
	            	String BID,fromUID;
		            public void actionPerformed(ActionEvent e) {
		            	Reply_GUI R=new Reply_GUI(U,BID,fromUID,null);
		            	blog_view.dispose();
		            }
		            public ActionListener accept(String BID,String fromUID) {
	                    this.BID=BID;
	                    this.fromUID=fromUID;
	                    return this;
	                }
	            }.accept(Blogs.get(i).getBID(),U.getUID()));  
	            
	            
	            //博客内容框
	            JTextArea blog_content=new JTextArea(content);
	            blog.add(blog_content);
	            blog_content.setBounds(20, 50+150*i+NumberOfReply*35, 740, 50);
	            blog_content.setEditable(false);
	            blog_content.setLineWrap(true);
	            
	            //评论内容显示
	            for(int k=0;k<Blogs.get(i).getReplySize();k++) {
		            String fromName=Blogs.get(i).getReplyFromName(k);
		            String toName=Blogs.get(i).getReplyToName(k);
		            String Content=Blogs.get(i).getReplyContent(k);
		            String Reply=null;
		            if(toName==null)
		            	Reply=fromName+" :"+Content;
		            else
		            	Reply=fromName+" 回复 "+toName+" :"+Content;
		            JTextArea JTAReply=new JTextArea(Reply);
		            JTAReply.setBounds(40, 130+150*i+NumberOfReply*35+k*35, 550, 30);
		            JTAReply.setEditable(false);
		            blog.add(JTAReply);
		            
		            //回复按钮
		            JButton reply_but=new JButton("回复");
		            reply_but.setFont(new java.awt.Font("黑体", 1, 17));
		            reply_but.setBounds(600, 135+150*i+NumberOfReply*35+k*35, 80, 30);
		            reply_but.setOpaque(true);
		            reply_but.setContentAreaFilled(false);
		            reply_but.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		            blog.add(reply_but);
		            reply_but.addActionListener(new ActionListener() {
		            	String BID,fromUID,toUID;
			            public void actionPerformed(ActionEvent e) {
			            	new Reply_GUI(U,BID,fromUID,toUID);
			            	blog_view.dispose();
			            }
			            public ActionListener accept(String BID,String fromUID,String toUID) {
		                    this.BID=BID;
		                    this.fromUID=fromUID;
		                    this.toUID=toUID;
		                    return this;
		                }
		            }.accept(Blogs.get(i).getBID(),U.getUID(),Blogs.get(i).getReplyUID(k)));
		            }
		            NumberOfReply+=Blogs.get(i).getReplySize();
	        }
	        
	        
		    //左面板组件
	        //用户名
	        JLabel user_name=new JLabel("用户名:");
	        user_name.setBounds(5, 20, 130, 50);
	        left_panel.add(user_name);
	        JLabel user_name1=new JLabel(U.getUname());
	        user_name1.setBounds(5, 40, 100, 50);
	        left_panel.add(user_name1);
	        user_name1.setFont(new java.awt.Font("黑体", 1, 17));
	        //功能区标签
		    JLabel function = new JLabel("功能区:");
		    left_panel.add(function);
		    //发表按钮
		    function.setBounds(5, 100, 100, 50);
	        JButton Release=new JButton("发表博客");
	        left_panel.add(Release);
	        Release.setBounds(5, 200, 100, 50);
	        Release.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               new release(U);
	               blog_view.dispose();
	            }
	        });
	        
	        JButton fresh=new JButton("⟲");
	        fresh.setBounds(5,150, 50, 40);
	        left_panel.add(fresh);
	        fresh.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
		               blog_view.dispose();
		               new blog_view(U);
		            }
		        });
	        //我的博客按钮
	        JButton my_blog=new JButton("我的博客");
	        my_blog.setBounds(5, 300, 100, 50);
	        my_blog.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	ArrayList<Blog> blogs=new ArrayList<Blog>();
	            	String Info=U.getOwnBlogs(blogs);
	            	if(Info!=null) {
	            		new JOptionPane().showMessageDialog(null, Info, "提示", JOptionPane.INFORMATION_MESSAGE);
	            	}
	            	else {
	               new my_blog(U);
	               blog_view.dispose();
	            	}
	            }
	        });
	        left_panel.add(my_blog);
	        //查询框
	        JTextField user_id=new JTextField("输入用户ID");
	        user_id.addFocusListener(new JTextFieldHintListener("输入用户ID", user_id));
	        user_id.setBounds(5, 400, 100, 30);
	        left_panel.add(user_id);
	        //查询按钮
	        JButton others_blog=new JButton("查询博客");
	        others_blog.setBounds(5, 460, 100, 50);
	        others_blog.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               String user_id_value=user_id.getText();
	               ArrayList<Blog> blogs =new ArrayList<Blog>();
	   		       String Info=U.getOtherBlogs(user_id_value, blogs);
	   		       if(Info==null)
	   		       {   	new others_blog(U,user_id_value);
	   		       		blog_view.dispose();
	   		       }
	   		       else
	   		    	new JOptionPane().showMessageDialog(null, Info, "警告", JOptionPane.WARNING_MESSAGE);
	            }
	        });
	        left_panel.add(others_blog);
	        
	        
	        JSplitPane splitPane = new JSplitPane();
	        splitPane.setDividerSize(1);//设置分割线的宽度
	        splitPane.setDividerLocation(120);
	        splitPane.setContinuousLayout(true);
	        blog_view.setContentPane(splitPane);
	        

	        // 设置左右两边显示的组件
	       
	        right_panel.add(blog_jScrollPane);
	        splitPane.setLeftComponent(left_panel);
	        splitPane.setRightComponent(right_panel);
	        
	}
//	public static void main(String[] args) throws Exception {
//		User U=new User();
//		U.Login("001", "aa123456");
//		new blog_view(U);
//	}
}
