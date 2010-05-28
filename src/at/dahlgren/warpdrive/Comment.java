package at.dahlgren.warpdrive;

public class Comment {
	
	final int id;
	final String date;
	final String user;
	final String text;
	
	public Comment(String user, String date, String text) {
		this.id = 0;
		this.user = user;
		this.date = date;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

}
