package at.dahlgren.warpdrive.pages;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import at.dahlgren.warpdrive.DefaultListAdapter;
import at.dahlgren.warpdrive.Page;
import at.dahlgren.warpdrive.Parser;
import at.dahlgren.warpdrive.Quote;
import at.dahlgren.warpdrive.R;

public class ShowQuotes extends Page {

	private List<Quote> quotes;

	protected void commandReady(String data) {
		quotes = Parser.parseQuotes(data);
		showQuotes();	
	}

	private void showQuotes() {
		ListView list = new ListView(ShowQuotes.this);
		list.setId(R.id.layout_root);
		list.setAdapter(new ListAdapter(ShowQuotes.this, quotes));
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ShowQuotes.this, ShowComments.class);
				intent.putExtra("id", ((Quote) parent.getAdapter().getItem(position)).getId());
				startActivity(intent);
				return true;
			}
			
		});
		ShowQuotes.this.setContentView(list);
	}

	private class ListAdapter extends DefaultListAdapter<Quote> {
		public ListAdapter(Context c, List<Quote> arr) {
			super(c, arr);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView != null)
				view = convertView;
			else
				view= View.inflate(context, R.layout.quote, null);
			
			TextView id = (TextView) view.findViewById(R.id.quote_id);
			TextView rank = (TextView) view.findViewById(R.id.quote_rank);
			TextView text = (TextView) view.findViewById(R.id.quote_text);

			Quote quote = (Quote) this.getItem(position);

			id.setText("#" + quote.getId());
			rank.setText("Rank: " + quote.getRank());
			
			String str = quote.getText();
			while (str.endsWith("<br />"))
				str = str.substring(0, str.length() - "<br />".length());
			text.setText(Html.fromHtml(str));
			
			return view;
		}
	}
}
