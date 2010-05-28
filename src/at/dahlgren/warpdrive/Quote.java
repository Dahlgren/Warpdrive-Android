package at.dahlgren.warpdrive;

public class Quote {
	
	final int id;
	final int rank;
	final String text;
	
	public Quote(int id, int rank, String text) {
		this.id = id;
		this.rank = rank;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public int getRank() {
		return rank;
	}

	public String getText() {
		return text;
	}
}
