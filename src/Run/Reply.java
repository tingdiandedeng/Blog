package Run;

public class Reply{
	private String RID;
	private String BID;
	private String fromUID;
	private String toUID;
	private String fromUname;
	private String toUname;
	private String Content;
	
	

	public Reply(String rID, String bID, String fromUID, String toUID, String fromUname, String toUname,
			String content) {
		RID = rID;
		BID = bID;
		this.fromUID = fromUID;
		this.toUID = toUID;
		this.fromUname = fromUname;
		this.toUname = toUname;
		Content = content;
	}
	
	public String getFromUID() {
		return fromUID;
	}

	public String getFromUname() {
		return fromUname;
	}

	public String getToUname() {
		return toUname;
	}

	public String getContent() {
		return Content;
	}

	@Override
	public String toString() {
		return "Reply [fromUname=" + fromUname + ", toUname=" + toUname + ", Content=" + Content + "]";
	}
	
}
