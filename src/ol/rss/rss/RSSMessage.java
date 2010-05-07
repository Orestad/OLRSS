package ol.rss.rss;

import ol.rss.ViewCentral;
import ol.rss.events.EventBridge;
import ol.rss.linked.Link;
import ol.rss.messages.Message;
import android.graphics.Color;
import android.sax.Element;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import static ol.rss.generic.Utilities.*;


public class RSSMessage extends Message<RSSMessage> implements OnClickListener, Link<RSSMessage>  {

	static public final String PUB_DATE = "pubDate";
	static public final  String DESCRIPTION = "description";
	static public final  String LINK = "link";
	static public final  String TITLE = "title";

	
	// Used for Template creation ONLY
	public RSSMessage(ViewCentral hub, Element elem, EventBridge eventExchange) {
		
		super(hub, eventExchange);
		resetCursor(this);
		
		// Only templates are "created" by the parser, the attribute 
		registerAttributeListener(elem, TITLE, TITLE);
		registerAttributeListener(elem, LINK, LINK);
		registerAttributeListener(elem, DESCRIPTION, DESCRIPTION);
		registerAttributeListener(elem, PUB_DATE, PUB_DATE);
	}

	public RSSMessage(RSSMessage original) {
		super(original);
	}

	@Override public RSSMessage clone() {
		return new RSSMessage(this);
	}

	public String getTitle() {
		return getAttribute("title");
	}

	public String getDescription() {
		return getAttribute("description");
	}

	public String getLink() {
		return getAttribute("link");
	}
	
	public SpannableString getText() {

		String title = htmlToString(getTitle());

		if (isOpen()) {

			String cated = title + "\n" + htmlToString(getDescription());
			SpannableString txt = new SpannableString(cated);
			txt.setSpan(new ForegroundColorSpan(Color.RED), title.length(), cated.length(), 0); 

			return txt;

		} else {
			return new SpannableString(title);
		}
	}
	
	public void showLink(WebView browser) {
		browser.loadUrl(getLink());
	}
	
}
