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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import at.dahlgren.warpdrive.Page;
import at.dahlgren.warpdrive.R;

public class MainMenu extends Page {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.mainmenu);
		
		ArrayList<MainMenuItem> arr = new ArrayList<MainMenuItem>();
		arr.add(new MainMenuItem(getResources().getString(R.string.latest), new Intent(this, Latest.class)));
		arr.add(new MainMenuItem(getResources().getString(R.string.random), new Intent(this, Random.class)));
		arr.add(new MainMenuItem(getResources().getString(R.string.top), new Intent(this, Top.class)));

		ListView list = new ListView(this);
		list.setAdapter(new ListAdapter(this, arr));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					MainMenu.this.startActivity(((MainMenuItem) parent.getAdapter().getItem(position)).intent);
			}
		});

		this.setContentView(list);
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

	private class ListAdapter extends BaseAdapter {

		protected Context context;
		private ArrayList<MainMenuItem> arr;

		public ListAdapter(Context c, ArrayList<MainMenuItem> arr) {
			this.context = c;
			this.arr = arr;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout layout = new LinearLayout(context);
			TextView text = new TextView(context);
			text.setText(arr.get(position).text);
			text.setTextSize(getResources().getDimension(R.dimen.menuMain));
			layout.addView(text);
			return layout;
		}

		@Override
		public int getCount() {
			return arr.size();
		}

		@Override
		public Object getItem(int position) {
			return arr.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}	
}
