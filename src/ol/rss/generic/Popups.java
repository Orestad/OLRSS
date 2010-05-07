package ol.rss.generic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;


public class Popups {

	static public final Popups instance = new Popups();
	
	private Activity m_activity;
	private ProgressDialog m_progress;
	
	private Popups() { /* Privatizer */ } 
	
	public void setContext(Activity act) {
		m_activity = act;
	}
	
	public void showAlert(String title, String mymessage) {

		new AlertDialog.Builder(m_activity)
		.setMessage(mymessage)  
		.setTitle(title)  
		.setCancelable(true)  
		.setNeutralButton(android.R.string.cancel,  
				new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int whichButton){}  
		})  
		.show();  
	}
	
	
	// todo ; räknare(upp) samt threadsafe
	public void showLoadWait() {
		m_progress = ProgressDialog.show(m_activity, "", "Loading, please wait...", true);
	}
	
	// todo ; räknare(ner) samt threadsafe
	public void hideLoadWait() {
		if (m_progress != null) m_progress.dismiss();
	}
}
