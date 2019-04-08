package ConnectDB;

import java.util.Date;
import java.util.UUID;
import java.sql.*;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

import Run.Blog;
import Run.Reply;
import Run.Like;

//DB类用于对数据库进行建立连接与增删改查操作
public class DB {
	
	
//获取数据库连接
public static Connection getConnect() throws Exception {
     String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
     String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=BLOG";//数据源  ！
     String Name="sa";
     String Pwd="123456";
	Class.forName(driverName);
	return DriverManager.getConnection(dbURL,Name,Pwd);
	}

//用户注册信息存入数据库,返回值为0则插入失败
public static int SignUp(String ID,String Name,String psw,Connection connection){
	int i=0;
	String Hpsw=DigestUtils.md5Hex(psw);//将用户密码进行加密存储
	String sql="INSERT"
			+ " INTO Users (Uname,UID,Hpsw) values(?,?,?)";
	PreparedStatement pstmt;
	try {
		pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, Name);
		pstmt.setString(2, ID);
		pstmt.setString(3, Hpsw);
		i=pstmt.executeUpdate();
		pstmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return i;
	}

//查找是否有对应UID注册用户
public static int isExistUser(String UID,Connection connection) {
	int i=0;
	String sql="SELECT*"
			+ " FROM Users"
			+ " WHERE UID=?";
	PreparedStatement pstmt;
	try {
		pstmt = (PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, UID);
		ResultSet rs=pstmt.executeQuery();
		if (rs.next())
			i++;
		rs.close();
		pstmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return i;
}

//根据UID获取数据库中的对应Hpsw，若无该UID则返回NULL
public static String getHpsw(String UID,Connection connection) {
	String sql="SELECT Hpsw"
			+ " FROM Users"
			+ " WHERE UID=?";
	
	try {
		PreparedStatement pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, UID);
		ResultSet rs=pstmt.executeQuery();
		if (rs.next())
				return rs.getString("Hpsw");
		rs.close();
		pstmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}

//登录验证成功后，获取用户昵称
public static String getUname(String UID,Connection connection) {
	String sql="SELECT Uname"
			+ " FROM Users"
			+ " WHERE UID=?";
	try {
		PreparedStatement pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, UID);
		ResultSet rs=pstmt.executeQuery();
		if (rs.next())
				return rs.getString("Uname").trim();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}

//存入博客内容，失败返回0
public static int PostBlog(String UID,String Content,Date timestamp,Connection connection) {
	int f=0;
	UUID BId=UUID.randomUUID();
	String BID=BId.toString();
	String sql="INSERT"
			+ " INTO Blog (UID,Content,BTimestamp,BID) values(?,?,?,?)";
	PreparedStatement pstmt;
	try {
		pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, UID);
		pstmt.setString(2,Content);
		pstmt.setTimestamp(3, new Timestamp(timestamp.getTime()));
		pstmt.setString(4, BID);
		f=pstmt.executeUpdate();
		pstmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return f;
}


//根据指定BID删除博客,返回1删除成功
public static int DeleteBlog(String BID,Connection connection) {
	int i=0;
	String sqlBlog="DELETE"
			+ " FROM Blog"
			+ " WHERE BID=?";
	String sqlLike="DELETE"
			+ " FROM [Like]"
			+ " WHERE BID=?";
	String sqlReply="DELETE"
			+ " FROM Reply"
			+ " WHERE BID=?";
	try {
		PreparedStatement pstmt1=(PreparedStatement) connection.prepareStatement(sqlLike);
		PreparedStatement pstmt2=(PreparedStatement) connection.prepareStatement(sqlReply);
		PreparedStatement pstmt3=(PreparedStatement) connection.prepareStatement(sqlBlog);
		pstmt1.setString(1, BID);
		pstmt2.setString(1, BID);
		pstmt3.setString(1, BID);
		pstmt1.executeUpdate();
		pstmt2.executeUpdate();
		i=pstmt3.executeUpdate();
		pstmt1.close();
		pstmt2.close();
		pstmt3.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return i;
	
}

//根据指定UID，查找该用户所发的所有帖子,若UID为null，则查询所有帖子。返回帖子条数
public static int getBlog(String UId,ArrayList<Blog>Blogs,Connection connection) {
	int i=0;
	try {
	ResultSet Re = null;
	PreparedStatement pstmt=null;
	if(UId!=null) //UID不为null，执行查询指定UID语句
	{
	String sql="SELECT BID,Blog.UID,Content,BTimestamp,Users.Uname"
			+ " FROM Blog,Users"
			+ " WHERE Blog.UID=? AND Blog.UID=Users.UID"
			+ " ORDER BY BTimestamp ASC";
	pstmt=connection.prepareStatement(sql);
	pstmt.setString(1, UId);
	Re=pstmt.executeQuery();
	}
	else //UID为null，查询所有博客
	{
	String sql="SELECT BID,Blog.UID,Content,BTimestamp,Users.Uname"
			+ " FROM Blog,Users WHERE Users.UID=Blog.UID"
			+ " ORDER BY BTimestamp ASC";
	pstmt=(PreparedStatement) connection.prepareStatement(sql);
	Re=pstmt.executeQuery();
	}
	
	//将查询结果存入博客列表
	while(Re.next()) {
		i++;
		String BID=Re.getString("BID");
		String Uname=Re.getString("Uname");
		String UID=Re.getString("UID");
		String content=Re.getString("Content");
		Date timestamp=Re.getTimestamp("BTimestamp");
		
		ArrayList<Reply> replys=new ArrayList<Reply>();//创建回复对象列表，并获取回复内容
		getReply(BID,replys,connection);
		
		ArrayList<Like> Likes=new ArrayList<Like>();//创建点赞对象列表，获取点赞内容
		getLike(BID,Likes,connection);
		
		Blogs.add(new Blog(BID.trim(),Uname.trim(),UID.trim(),content,timestamp,replys,Likes));//由博客内容与回复内容构成完整博客，添加到博客列表
	}
	Re.close();
	pstmt.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return i;
}

//存入回复内容
public static int setReply(String BID,String fromUID,String toUID,String Content,Date RTimestamp,Connection connection) {
	int i=0;
	UUID rID=UUID.randomUUID();
	String RID=rID.toString();
	String sql="INSERT "
			+ "INTO Reply (RID,BID,fromUID,toUID,Content,RTimestamp) values(?,?,?,?,?,?)";
	try {
		PreparedStatement pstmt=connection.prepareStatement(sql);
		pstmt.setString(1, RID);
		pstmt.setString(2, BID);
		pstmt.setString(3, fromUID);
		pstmt.setString(4, toUID);
		pstmt.setString(5, Content);
		pstmt.setTimestamp(6, new Timestamp(RTimestamp.getTime()));
		i=pstmt.executeUpdate();
		pstmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}

//根据博客ID获取评论列表，并存储于评论集合中，返回值为该博客评论条数
public static int getReply(String BID,ArrayList<Reply> replys,Connection connection)
{
	int i=0;
	String sql="SELECT RID,BID,fromUID,U2.Uname fromUname,toUID,U.Uname toUname,Content,Rtimestamp"
			+ " FROM Reply R LEFT JOIN USERS U ON R.toUID=U.UID,Users U2"
			+ " WHERE BID=? AND fromUID=U2.UID"
			+ " ORDER BY Rtimestamp ASC";
	try {
		PreparedStatement pstmt=connection.prepareStatement(sql);
		pstmt.setString(1, BID);
		ResultSet R=pstmt.executeQuery();
		while(R.next()) {
			i++;
			String RID=R.getString("RID");
			String fromUID=R.getString("fromUID");
			String fromUname=R.getString("fromUname");
			String toUID=null;
			String toUname=null;
			if(R.getString("toUID")!=null) {
				toUID=R.getString("toUID").trim();
				toUname=R.getString("toUname").trim();
			}
			String Content=R.getString("Content");
			replys.add(new Reply(RID,BID,fromUID.trim(),toUID,fromUname.trim(),toUname,Content));
		}
		R.close();
		pstmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}

//删除或存入帖子所对应的点赞信息
public static int Like(String BID,String UID,Connection connection) {
	String sql="DELETE"
			+ " FROM [Like]"
			+ " WHERE BID=? AND UID=?";
	try {
		PreparedStatement pstmt=connection.prepareStatement(sql);
		pstmt.setString(1, BID);
		pstmt.setString(2, UID);
		if(pstmt.executeUpdate()==1) {//取消点赞成功
			pstmt.close();
			return 1;
		}
	
		else {
			String sql2="INSERT"
					+ " INTO [Like](BID,UID)"
					+ " VALUES(?,?)";
			PreparedStatement pstmt2=connection.prepareStatement(sql2);
			pstmt2.setString(1, BID);
			pstmt2.setString(2, UID);
			if(pstmt2.executeUpdate()==1) {//点赞成功
				pstmt2.close();
				return 2;
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return 0;
}

//获取帖子所包含的点赞信息
public static int getLike(String BID,ArrayList<Like> Likes,Connection connection) {
	int i=0;
	String sql="SELECT [Like].UID UID,Uname"
			+ " FROM [Like],Users"
			+ " WHERE [Like].BID=? AND [Like].UID=Users.UID";
	try {
		PreparedStatement pstmt=connection.prepareStatement(sql);
		pstmt.setString(1, BID);
		ResultSet Re=pstmt.executeQuery();
		while(Re.next()) {
			i++;
			String UID=Re.getString("UID");
			String Uname=Re.getString("Uname");
			Likes.add(new Like(BID,UID.trim(),Uname.trim()));
		}
		Re.close();
		pstmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return i;
}
}

