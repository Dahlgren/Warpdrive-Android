package at.dahlgren.warpdrive;

import android.app.Activity;
import android.os.AsyncTask;

public abstract class Task<T1, T2, T3> extends AsyncTask<T1, T2, T3> {

	protected Activity activity;
	protected boolean completed;

	protected abstract void notifyActivityTaskCompleted();

	protected Task(Activity activity) {
		this.activity = activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
		if ( completed && activity != null ) {
			notifyActivityTaskCompleted();
		}
	}
}
