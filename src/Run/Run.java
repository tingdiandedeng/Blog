package Run;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import ConnectDB.*;
import GUI.login;

public class Run {

	public static void main(String[] args) throws Exception {
		User user=new User();
		new login(user);
//		int f=DB.PostBlog("0001", "001", "hahhahhaha", DB.getConnect());
//		System.out.println(f);
	}

}
