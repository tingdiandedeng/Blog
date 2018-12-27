package ConnectDB;

import java.sql.*;
import org.apache.commons.codec.digest.DigestUtils;

//DB�����ڶ����ݿ���н�����������ɾ�Ĳ����
public class DB {
	
	
//��ȡ���ݿ�����
public static Connection getConnect() throws Exception {
     String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL���ݿ�����
     String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=BLOG";//����Դ  ��������ע�������ּ��ػ����������ݿ�ʧ��һ���������������
     String Name="sa";
     String Pwd="123456";
	Class.forName(driverName);
	return DriverManager.getConnection(dbURL,Name,Pwd);
	}

//�û�ע����Ϣ�������ݿ�,����ֵΪ0�����ʧ��
public static int SignUp(String ID,String Name,String psw,Connection connection){
	int i=0;
	String Hpsw=DigestUtils.md5Hex(psw);//���û�������м��ܴ洢
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

//����UID��ȡ���ݿ��еĶ�ӦHpsw�����޸�UID�򷵻�NULL
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

//���벩�����ݣ�ʧ�ܷ���0
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

