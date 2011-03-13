package at.dahlgren.warpdrive.pages;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import at.dahlgren.warpdrive.Comment;
import at.dahlgren.warpdrive.DefaultListAdapter;
import at.dahlgren.warpdrive.Page;
import at.dahlgren.warpdrive.Parser;
import at.dahlgren.warpdrive.R;

public class ShowComments extends Page {

	private List<Comment> comments;
	private int offset;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			offset = savedInstanceState.getInt("offset", 1);
	}
	
	protected void refresh() {
		int id = this.getIntent().getIntExtra("id", 0);
		request.setMethod("GET");
		request.setUrl("http://warpdrive.se/" + id + "/kommentar/" + offset);
		offset++;
		runCommand();
	}

	protected void commandReady(String data) {
		comments = Parser.parseComments(data);
		showComments();	
	}

	private void showComments() {
		ListView list = new ListView(ShowComments.this);
		list.setId(R.id.layout_root);
		list.setAdapter(new ListAdapter(ShowComments.this, comments));
		ShowComments.this.setContentView(list);
	}

	private class ListAdapter extends DefaultListAdapter<Comment> {
		public ListAdapter(Context c, List<Comment> arr) {
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

			Comment comment = (Comment) this.getItem(position);

			id.setText(comment.getUser());
			rank.setText(comment.getDate());
			text.setText(Html.fromHtml(comment.getText()));
			
			return view;
		}
	}
}
