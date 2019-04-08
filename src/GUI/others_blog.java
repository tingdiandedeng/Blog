package GUI;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import Run.Blog;
import Run.User;

public class others_blog extends JFrame{
	public  others_blog(User U,String UID){
		JFrame others_blog=new JFrame("别人的博客");
	    others_blog.setSize(980, 830);
	    others_blog.setLocationRelativeTo(null);
	    others_blog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    others_blog.setResizable(false);
	    JPanel left_panel = new JPanel();//面板1
	    left_panel.setLayout(null);
	    left_panel.setSize(120,400);
	    
	    Container right_panel =getContentPane();//容器2
//	    right_panel.setSize(880,400);
	    
	    others_blog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    others_blog.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        	others_blog.dispose();
        	new blog_view(U);
        }
        });
	    
	    
	    //获取所有博客内容
	    ArrayList<Blog> Blogs =new ArrayList<Blog>();
	    U.getOtherBlogs(UID, Blogs);
	    
		//主面板
	    JPanel blog=new JPanel();
	    
	    int SumOfReply=0;
	    for(int f=0;f<Blogs.size();f++)
	    {
	    	SumOfReply+=Blogs.get(f).getReplySize();
	    }
	    blog.setPreferredSize(new Dimension(800,150*Blogs.size()+35*SumOfReply));
	    blog.setLayout(null);
	    others_blog.setVisible(true);
	    
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
                   others_blog.dispose();
                   new others_blog(U,UID);
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
	            	Reply_GUI2 R=new Reply_GUI2(U,BID,fromUID,null);
	            	others_blog.dispose();
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
		            	new Reply_GUI2(U,BID,fromUID,toUID);
		            	others_blog.dispose();
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
	    
	    
	    //返回按钮
	    function.setBounds(5, 100, 100, 50);
        JButton return1=new JButton("←");
        left_panel.add(return1);
        return1.setBounds(5, 200, 100, 50);
        return1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               others_blog.dispose();
               new blog_view(U);
            }
        });
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(1);//设置分割线的宽度
        splitPane.setDividerLocation(120);
        splitPane.setContinuousLayout(true);
        others_blog.setContentPane(splitPane);
        

        // 设置左右两边显示的组件
       
        right_panel.add(blog_jScrollPane);
        splitPane.setLeftComponent(left_panel);
        splitPane.setRightComponent(right_panel);
        
        
}
}
