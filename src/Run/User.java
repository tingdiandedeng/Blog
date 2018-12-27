package Run;

import java.sql.Connection;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import ConnectDB.DB;

//User类，存储用户基本信息，并定义基本方法
public class User{
	private String UID;
	private String Uname;
	private String Psw;
	
	//注册时获取信息
	public void setUser(String id,String name,String psw) {
		this.UID=id;this.Psw=psw;this.Uname=name;
	}
	
	//登录时获取信息
	public void setUser(String id,String psw) {
		this.UID=id;this.Psw=psw;
	}
	
	//登录验证方法，成功返回true，否则返回错误信息
	public String Login() {
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
					if(hpsw.equals(Hpsw))
						Info = "true";
					else
						Info ="账号或密码错误！";
				}
				DBConnection.close();
			}
		} catch (Exception e) {
			Info = "数据库连接失败";
		}
		return Info;
	}
	
	//注册方法，成功返回true，否则返回错误信息
	public String SignUp() {
		String Info=null;
		try {
			if(UID.length()==0||Psw.length()==0||Uname.length()==0)
				Info="注册信息不能为空";
			else if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,10}$", UID)==false)
				Info= "账号包含数字与字母，且不超过10位";
			else if(Uname.length()>8)
				Info= "用户名不能大于8位";
			else if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,10}$", Psw)==false)
				Info= "密码包含数字与字母，且不超过10位";
			else{
			Connection DBConnection = DB.getConnect();
			int i=DB.SignUp(UID, Uname, Psw, DBConnection);
			if(i==0)
				Info="注册失败";
			else
				Info="true";
			DBConnection.close();
			}
		} catch (Exception e) {
			Info= "数据库连接失败";
		}
		return Info;
	}
}
