package Run;

public class Like {
	private String BID;
	private String UID;
	private String Uname;
	
	public Like(String bID, String uID, String uname) {
		BID = bID;
		UID = uID;
		Uname = uname;
	}

	public String getUID() {
		return UID;
	}

	public String getUname() {
		return Uname;
	}

	@Override
	public String toString() {
		return "[UID=" + UID + ",Uname=" + Uname + "]  ";
	}
	
	
}
