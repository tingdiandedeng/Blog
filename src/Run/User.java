package Run;

import java.sql.Connection;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import ConnectDB.DB;

//User�࣬�洢�û�������Ϣ���������������
public class User{
	private String UID;
	private String Uname;
	private String Psw;
	
	//ע��ʱ��ȡ��Ϣ
	public void setUser(String id,String name,String psw) {
		this.UID=id;this.Psw=psw;this.Uname=name;
	}
	
	//��¼ʱ��ȡ��Ϣ
	public void setUser(String id,String psw) {
		this.UID=id;this.Psw=psw;
	}
	
	//��¼��֤�������ɹ�����true�����򷵻ش�����Ϣ
	public String Login() {
		String Info=null;
		try {
			if(UID.length()==0||Psw.length()==0)
				Info = "�˺Ż����벻��Ϊ��!";
			else {
				Connection DBConnection = DB.getConnect();
				String Hpsw=DB.getHpsw(UID, DBConnection);
				if(Hpsw==null)
					Info = "�˺Ż��������";
				else {
					String hpsw=DigestUtils.md5Hex(Psw);
					if(hpsw.equals(Hpsw))
						Info = "true";
					else
						Info ="�˺Ż��������";
				}
				DBConnection.close();
			}
		} catch (Exception e) {
			Info = "���ݿ�����ʧ��";
		}
		return Info;
	}
	
	//ע�᷽�����ɹ�����true�����򷵻ش�����Ϣ
	public String SignUp() {
		String Info=null;
		try {
			if(UID.length()==0||Psw.length()==0||Uname.length()==0)
				Info="ע����Ϣ����Ϊ��";
			else if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,10}$", UID)==false)
				Info= "�˺Ű�����������ĸ���Ҳ�����10λ";
			else if(Uname.length()>8)
				Info= "�û������ܴ���8λ";
			else if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,10}$", Psw)==false)
				Info= "���������������ĸ���Ҳ�����10λ";
			else{
			Connection DBConnection = DB.getConnect();
			int i=DB.SignUp(UID, Uname, Psw, DBConnection);
			if(i==0)
				Info="ע��ʧ��";
			else
				Info="true";
			DBConnection.close();
			}
		} catch (Exception e) {
			Info= "���ݿ�����ʧ��";
		}
		return Info;
	}
}
