package at.dahlgren.warpdrive.pages;


public class Random extends ShowQuotes {

	protected void refresh() {
		request.setMethod("GET");
		request.setUrl("http://warpdrive.se/slumpa");
		runCommand();
	}
}
