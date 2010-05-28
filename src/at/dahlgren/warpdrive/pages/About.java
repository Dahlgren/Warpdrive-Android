package at.dahlgren.warpdrive.pages;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import at.dahlgren.warpdrive.Page;
import at.dahlgren.warpdrive.R;

public class About extends Page {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}

	/* Empty menus */
	public boolean onCreateOptionsMenu(Menu menu) {return false;}
	public boolean onOptionsItemSelected(MenuItem item) {return false;}
}