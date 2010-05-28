package at.dahlgren.warpdrive.pages;


public class Top extends ShowQuotes {

	protected void refresh() {
		request.setMethod("GET");
		request.setUrl("http://warpdrive.se/topplistan");
		runCommand();
	}
}
