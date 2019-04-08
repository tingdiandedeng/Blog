package Run;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Blog {
	private String BID;
	private String Uname;
	private String UID;
	private String Content;
	private Date timestamp;
	private ArrayList<Reply> Replys=null;
	private ArrayList<Like> Likes=null;
	
	public Blog(String bID, String uname, String uID, String content, Date timestamp2,ArrayList<Reply>replys,ArrayList<Like> likes) {
		BID = bID;
		Uname = uname;
		UID = uID;
		Content = content;
		this.timestamp = timestamp2;
		this.Replys=replys;
		this.Likes=likes;
	}
	
	public String getUID() {
		return UID;
	}
	
	public String getBID() {
		return BID;
	}

	public String getUname() {
		return Uname;
	}

	public String getContent() {
		return Content;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public void printReply() {
		if(Replys.size()==0)
			return;
		Iterator<Reply> I=Replys.iterator();
		while(I.hasNext()) {
			Reply B=(Reply)I.next();
			System.out.println(B);
		}
	}
	
	public void printLike() {
		if(Likes.size()==0)
			return;
		Iterator<Like> I=Likes.iterator();
		while(I.hasNext()) {
			Like B=(Like)I.next();
			System.out.print(B);
		}
		System.out.println();
	}
	
	public int getReplySize() {
		return Replys.size();
	}
	
	public String getReplyFromName(int i) {
		return Replys.get(i).getFromUname();
	}
	
	public String getReplyToName(int i) {
		return Replys.get(i).getToUname();
	}
	
	public String getReplyContent(int i) {
		return Replys.get(i).getContent();
	}
	
	public String getReplyUID(int i) {
		return Replys.get(i).getFromUID();
	}
	
	@Override
	public String toString() {
		DateFormat df2 = DateFormat.getDateTimeInstance();
		return "Blog [BID=" + BID + ", Uname=" + Uname + ", UID=" + UID + ", Content=" + Content + ", Btimestamp="
				+ df2.format(timestamp) + "]";
	}
	//返回点赞人信息
		public String getLike() {
			StringBuilder Info=new StringBuilder();
			if(Likes.size()==0)
				return null;
			else if(Likes.size()<=7)
			{
				Iterator<Like> I=Likes.iterator();
				while(I.hasNext()) {
					Like B=(Like)I.next();
					if(B==Likes.get(Likes.size()-1))
						Info.append(B.getUname());
					else
						Info.append(B.getUname()).append(",");	
				}
				Info.append(" 觉得很赞");
			}
			else {
				for(int i=0;i<7;i++) {
					Like B=Likes.get(i);
					if(i<6)
						Info.append(B.getUname()).append(",");
					else
						Info.append(B.getUname()).append(" 等").append(Likes.size()).append("人觉得很赞");
				}
			}
			return Info.toString();
		}
	
}


