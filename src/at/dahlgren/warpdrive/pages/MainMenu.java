package at.dahlgren.warpdrive.pages;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import at.dahlgren.warpdrive.DefaultListAdapter;
import at.dahlgren.warpdrive.Page;
import at.dahlgren.warpdrive.R;

public class MainMenu extends Page {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pages);
		
		ArrayList<MainMenuItem> arr = new ArrayList<MainMenuItem>();
		arr.add(new MainMenuItem(getResources().getString(R.string.latest), new Intent(this, Latest.class)));
		arr.add(new MainMenuItem(getResources().getString(R.string.top), new Intent(this, Top.class)));
		arr.add(new MainMenuItem(getResources().getString(R.string.random), new Intent(this, Random.class)));
		arr.add(new MainMenuItem(getResources().getString(R.string.search), new Intent(this, Search.class)));

		ListView list = (ListView) this.findViewById(R.id.list);
		list.setAdapter(new ListAdapter(this, arr));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {	
				MainMenuItem item = ((MainMenuItem) parent.getAdapter().getItem(position));
				if (item.intent.getComponent().getClassName().contentEquals(Search.class.getName()))
					startSearch(null, false, null, false);
				else
					MainMenu.this.startActivity(item.intent);
			}
		});
	}

	protected class MainMenuItem {
		final String text;
		final Intent intent;

		MainMenuItem(String text, Intent intent) {
			this.text = text;
			this.intent = intent;
		}
	}
	
	// Creates menu
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	
	// Actions to be performed on menu
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aboutButton:
			startActivity(new Intent(this, About.class));
			return true;
		}
		return false;
	}

	private class ListAdapter extends DefaultListAdapter<MainMenuItem> {

		public ListAdapter(Context c, ArrayList<MainMenuItem> arr) {
			super(c, arr);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView != null)
				view = convertView;
			else
				view= View.inflate(context, R.layout.menuitem, null);
			
			TextView text = (TextView) view.findViewById(R.id.name);
			MainMenuItem item = this.getItem(position);
			text.setText(item.text);
			return view;
		}
	}	
}
