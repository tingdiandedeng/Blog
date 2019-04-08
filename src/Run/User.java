package Run;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import ConnectDB.DB;

//User类，存储用户基本信息，并定义基本方法
public class User{
	private String UID;
	private String Uname;
	
	@Override
	public String toString() {
		return "User [UID=" + UID + ", Uname=" + Uname + "]";
	}

	public String getUID() {
		return UID;
	}

	public String getUname() {
		return Uname;
	}

	//存储用户ID,获取昵称并存储
	public void setUser(String id) {
		Connection con;
		try {
			con = DB.getConnect();
			this.UID=id;this.Uname=DB.getUname(id, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//登录验证方法，成功返回true，否则返回错误信息
	public String Login(String UID,String Psw) {
		String Info=null;
		try {
			if(UID.length()==0||Psw.length()==0)
				Info = "账号或密码不能为空!";
			else {
				Connection DBConnection = DB.getConnect();
				String Hpsw=DB.getHpsw(UID, DBConnection);
				if(Hpsw==null)
					Info = "账号或密码错误！";
				else {
					String hpsw=DigestUtils.md5Hex(Psw);
					if(hpsw.equals(Hpsw)){
						Info = "true";
						setUser(UID);
						}
					else
						Info ="账号或密码错误！";
				}
				DBConnection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Info;
	}
	
	//注册方法，成功返回true，否则返回错误信息
	public String SignUp(String UID,String Psw,String Uname) {
		String Info=null;
		try {
			if(UID.length()==0||Psw.length()==0||Uname.length()==0)
				Info="注册信息不能为空";
			else if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,10}$", UID)==false)
				Info= "账号有且仅有数字与字母，且不超过10位";
			else if(Uname.length()>8)
				Info= "用户昵称不能大于8位";
			else if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,10}$", Psw)==false)
				Info= "密码有且仅有数字与字母，且不超过10位";
			else{
			Connection DBConnection = DB.getConnect();
			int i=DB.SignUp(UID, Uname, Psw, DBConnection);
			if(i==0)
				Info="已存在该用户，请更换账号";
			else
				Info="true";
			DBConnection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Info;
	}

	//博客发表方法，返回错误信息
	public String PostBlog(String Blog) {
		String Info=null;
		if(Blog.length()>500)
			Info="当前字数为:"+Blog.length()+",博客字数不能大于500，请编辑后重试！";
		else if(Blog.length()==0)
			Info="请输入内容！";
		else
		{
			Connection connection;
			try {
				connection = DB.getConnect();
				if(DB.PostBlog(this.UID, Blog, new Date(), connection)==1)
					Info="发表成功！";
				else
					Info="发表失败！";
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return Info;
	}
	
	//获取所有博客内容，返回博客条数
	public int getAllBlogs(ArrayList<Blog> Blogs) {
		int i=0;
		try {
			Connection connection = DB.getConnect();
			i=DB.getBlog(null, Blogs, connection);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public String getOtherBlogs(String UID,ArrayList<Blog> Blogs) {
		String Info=null;
		if(UID.equals("输入用户ID"))
			Info="请输入你要查询的用户ID！";
		else {
			try {
				Connection connection = DB.getConnect();
				if(DB.isExistUser(UID, connection)==0)
					Info="未找到该用户";
				else if(DB.getBlog(UID, Blogs, connection)==0)
					Info="该用户尚未发表博客";
				else
				{}
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return Info;
		
	}
	
	
	public String getOwnBlogs(ArrayList<Blog> Blogs) {
		try {
			Connection connection = DB.getConnect();
			if(DB.getBlog(this.UID, Blogs, connection)==0) {
				return "你还没有博客记录，发表一条试试吧！";
			}
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String Like(String BID) {
		String Info=null;
		try {
			Connection connection=DB.getConnect();
			int i=DB.Like(BID, this.UID,connection);
			if(i==1)
				Info="取消点赞成功";
			else if(i==2)
				Info="点赞成功";
			else
				Info="不可预知错误";
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return Info;
	}
	//删除博客
		public String deleteBlog(String BID) {
			String Info=null;
			try {
				Connection connection = DB.getConnect();
				if(DB.DeleteBlog(BID, connection)!=0) {
					return "删除成功！";
				}
				else
					Info="无法预知的错误！";
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Info;
		}
		//回复功能
		public String Reply(String BID,String fromUID,String toUID,String Content) {
			String Info=null;
			if(Content.length()>100)
				Info="内容不能大于100字，请修改后重试";
			else if(Content.length()==0)
				Info="请输入内容！";
			else {
			try {
				Connection connection = DB.getConnect();
				if(DB.setReply(BID, fromUID, toUID, Content, new Date(), connection)==1) {
					if(toUID==null)
						Info= "评论成功！";
					else
						Info="回复成功！";
				}
				else
					Info="无法预知的错误！";
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			return Info;
		}


}
