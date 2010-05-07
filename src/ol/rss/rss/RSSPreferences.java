package ol.rss.rss;

import ol.rss.R;
import ol.rss.ViewCentral;
import ol.rss.generic.Popups;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;


// prefs : sources
// 

public class RSSPreferences extends PreferenceActivity {
	
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.rssprefs);
		
		Preference customPref = (Preference) findPreference("customPref");
		
		customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() { 
			public boolean onPreferenceClick(Preference preference) {
			
				Toast.makeText(getBaseContext(), "preferences clicked", Toast.LENGTH_LONG).show();
			
				SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = customSharedPreference.edit();
				editor.putString("myCustomPref","prferences clicked");
				editor.commit();
			
				return true;
			}
		 });
	}

	static public void show(ViewCentral hub) {
	 	Intent settingsActivity = new Intent(hub, RSSPreferences.class);
		hub.startActivity(settingsActivity);
		
		Popups.instance.showAlert("class", settingsActivity.toString());
	}
	
	static public Object getSomething(ViewCentral hub) {
		
		//SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		 //new StringSource(prefs.getString("rss.gallery", ""));
		
		
		
		//Editor e = prefs.edit();
		//e.putString("password", "woof");
		//e.commit();
		return null;
	}
	
	static public String debugg() {
		return "<sources>" +
		
		/* "<source><url>http://www.orestad-linux.se/news/feed</url>" +
		"<name>Örestad</name>" +
		"<image>" + String.valueOf(R.drawable.orestad)+ "</image>" +
		"<filter></filter></source>" + */
		
		"<source><url>http://www.aftonbladet.se/rss.xml</url>" +
		"<name>Aftonbladet</name>" +
		"<filter></filter></source>" +
		
		/* "<source><url>http://www.dn.se/m/rss/toppnyheter</url>" +
		"<name>DN</name>" +
		"<image>" + String.valueOf(R.drawable.dn)+ "</image>" +
		"<filter></filter></source>" + */
		
		"</sources>";
	}

}
