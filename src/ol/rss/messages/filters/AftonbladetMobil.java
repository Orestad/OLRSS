package aexp.elistcbox.messages.filters;

import aexp.elistcbox.messages.MessageFilter;
import android.net.Uri;

public class AftonbladetMobil implements MessageFilter {

	public static final String LINK_TAG = "link";
	public static final String MOBIL_URL = "http://mobil.aftonbladet.se";
	
	
	public String parse(String attributeName, String rawValue) {
		
		if (LINK_TAG.equalsIgnoreCase(attributeName)) {
			Uri rawUri = Uri.parse(rawValue);
			return MOBIL_URL + rawUri.getPath();
		}
		
		return rawValue;
	}
	
}
