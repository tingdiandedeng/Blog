package ConnectDB;

import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

//DB类用于对数据库进行建立连接与增删改查操作
public class DB {
	
	
//获取数据库连接
public static Connection getConnect() throws Exception {
     String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
     String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=BLOG";//数据源  ！！！！注意若出现加载或者连接数据库失败一般是这里出现问题
     String Name="sa";
     String Pwd="123456";
	Class.forName(driverName);
	return DriverManager.getConnection(dbURL,Name,Pwd);
	}

//用户注册信息存入数据库,返回值为0则插入失败
public static int SignUp(String ID,String Name,String psw,Connection connection){
	int i=0;
	String Hpsw=DigestUtils.md5Hex(psw);//将用户密码进行加密存储
	String sql="INSERT INTO Users (Uname,UID,Hpsw) values(?,?,?)";
	PreparedStatement pstmt;
	try {
		pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, Name);
		pstmt.setString(2, ID);
		pstmt.setString(3, Hpsw);
		i=pstmt.executeUpdate();
		pstmt.close();
	} catch (SQLException e) {
//		e.printStackTrace();
	}
	return i;
	}

//根据UID获取数据库中的对应Hpsw，若无该UID则返回NULL
public static String getHpsw(String UID,Connection connection) {
	String sql="SELECT Hpsw FROM Users"+" WHERE UID=?";
	try {
		PreparedStatement pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, UID);
		ResultSet rs=pstmt.executeQuery();
		if (rs.next())
				return rs.getString("Hpsw");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}

//存入博客内容，失败返回0
public static int PostBlog(String BID,String UID,String Content,Connection connection) {
	int f=0;
	String sql="INSERT INTO Blog (BID,UID,Content) values(?,?,?)";
	PreparedStatement pstmt;
	try {
		pstmt=(PreparedStatement) connection.prepareStatement(sql);
		pstmt.setString(1, BID);
		pstmt.setString(2, UID);
		pstmt.setString(3, Content);
		f=pstmt.executeUpdate();
		pstmt.close();
	} catch (SQLException e) {
//		e.printStackTrace();
	}
	return f;
}



}

