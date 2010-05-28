package at.dahlgren.warpdrive;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DefaultListAdapter<T> extends BaseAdapter {

	protected Context context;
	private List<T> arr;

	public DefaultListAdapter(Context c, List<T> arr) {
		this.context = c;
		this.arr = arr;
	}

	@Override
	public int getCount() {
		return arr.size();
	}

	@Override
	public T getItem(int position) {
		return arr.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
