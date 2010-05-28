package at.dahlgren.warpdrive.pages;


public class Latest extends ShowQuotes {

	protected void refresh() {
		request.setMethod("GET");
		request.setUrl("http://warpdrive.se/senaste");
		runCommand();
	}
}
