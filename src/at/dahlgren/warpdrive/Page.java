package at.dahlgren.warpdrive;

import org.me.sfBackendClient.sfClient;
import org.me.sfBackendClient.sfRequest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Page extends Activity {
	// For threading
	protected RunCommand mTask;

	// Default onCreate
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.loading);

		Object retained = getLastNonConfigurationInstance();
		if ( retained instanceof RunCommand ) {
			mTask = (RunCommand) retained;
			mTask.setActivity(this);
		} else {
			refresh();
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		if (mTask != null) {
			mTask.setActivity(null);
			return mTask;
		}
		return null;
	}

	// To be able to use the CMD
	protected final sfRequest request = sfClient.getInstance().createRequest();
	protected String commandData;

	/* Code for running stuff in separate thread */
	private class RunCommand extends Task<sfRequest, Void, String> {

		String data;

		protected RunCommand(Activity activity) {
			super(activity);
		}

		protected void onPreExecute() {}

		@Override
		protected String doInBackground(sfRequest... request) {
			request[0].execute();
			return request[0].getResult();
		}

		protected void onPostExecute(String data) {
			Log.v("RecievedData", data);
			this.data = data;
			this.completed = true;
			notifyActivityTaskCompleted();
		}

		@Override
		protected void notifyActivityTaskCompleted() {
			if (data.contains("The operation timed out") || data.contains("Host is unresolved"))
				((Page) activity).showToast("Kunde inte nå servern, problem med uppkopplingen?");
			else
				((Page) activity).commandReady(data);
		}
	}

	// Initiate a request to the server
	protected void runCommand() {
		mTask = new RunCommand(this);
		mTask.execute(request);
	}

	// Method to be run when data is received from server
	protected void commandReady(String data) {}

	// Default menu-action, used to refresh UI-content
	protected void refresh() {
		// TODO Auto-generated method stub
	}

	/* Easier way to show toasts */
	public void showToast(String text, int length) {
		Context context = getApplicationContext();
		int duration = length;

		Toast toast = Toast.makeText(context, text, duration);
		//toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}

	public void showToast(String text) {
		showToast(text, Toast.LENGTH_LONG);
	}
}
