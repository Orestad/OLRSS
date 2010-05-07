package ol.rss;

// todo; "vänta"-messagebox
// todo; stängs alla connections?
// todo; next - LinkView, se till att listknappen fungerar
// todo; flytta allt ifrån MessageView till messagelist utom galleriet
// todo; isolera paketen
// todo; refresh på alla klasser
// todo; se till att externa källor inte tillfrågas i onödan
// todo; sök ut Xml.parse och flytta dessa till trådar, skapa Popups.addBusy(txt) och removeBusy() som
// incrementerar busypekare. Detta kan kräva eventhantering 
// todo; tryck in "Busy, wait ..." - meddelandet i en handler
// todo; kapsla in alla visuella komponenter så att de skickar sina events till exchange

import ol.rss.datasources.StringSource;
import ol.rss.events.EventBridge;
import ol.rss.events.EventListener;
import ol.rss.events.GlobalEvent;
import ol.rss.generic.Popups;
import ol.rss.linked.LinkList;
import ol.rss.messages.Message;
import ol.rss.rss.RSSGallery;
import ol.rss.rss.RSSMessage;
import ol.rss.rss.RSSMessageList;
import ol.rss.rss.RSSPreferences;
import ol.rss.rss.RSSSource;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Gallery;
import android.widget.ImageButton;
import static ol.rss.events.GlobalEvent.Qualifiers.*;


public class ViewCentral extends Activity implements OnClickListener, EventListener 
{
	private static Popups s_boxes;
	private RSSMessageList m_messageList;
	
	private int m_currentView;
	private RSSGallery m_rssGallery;
	private EventBridge m_eventExchange;
	private LinkList<RSSMessage> m_linkList;
	
	public ViewCentral() {
		super();
		m_currentView = -1;
		
		m_eventExchange = new EventBridge();
		
		m_eventExchange.addListener(RSSGallery.class, this);
		m_eventExchange.addListener(Message.class, this);
		m_eventExchange.addListener(LinkList.class, this);
	}
	
	@Override public void setContentView(int layoutResID) {
		if (layoutResID != m_currentView) {
			super.setContentView(layoutResID);
			m_currentView = layoutResID; 
		}
	}
	
	@Override
    public void onCreate(Bundle icicle) {
        
		super.onCreate(icicle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		s_boxes = Popups.instance;
		s_boxes.setContext(this);
		
		try { 
			
			m_rssGallery = new RSSGallery(this, m_eventExchange);
			m_messageList = new RSSMessageList((RSSSource)m_rssGallery.getItem(0), this, m_eventExchange);
			
			m_linkList = new LinkList<RSSMessage>(m_eventExchange);
			
			switchToMessageList(true, true);
			
		} catch (Exception e) {
			Log.d("huga", "buga", e);
			Popups.instance.showAlert(e.toString(), e.getMessage());
		} 
	}
	
	public void switchToLinkList(RSSMessage selectedMessage) {
		setContentView(R.layout.webling);
		
		//
		
		m_linkList.show(
				(ImageButton)findViewById(R.id.back), 
				(ImageButton)findViewById(R.id.forward),
				(ImageButton)findViewById(R.id.toList),
				(WebView)findViewById(R.id.webview), selectedMessage);
		
		
	}
	
	public void switchToMessageList(boolean drawGallery, boolean rereadList) {
	
		setContentView(R.layout.main);
		ImageButton prefsButton = (ImageButton)findViewById(R.id.titleBut);
		prefsButton.setOnClickListener(this);
		
		if (drawGallery) {
			m_rssGallery.show(new StringSource(RSSPreferences.debugg()), (Gallery) findViewById(R.id.gallery));
		}
		
		if (rereadList) {
			m_messageList.show(m_rssGallery.getSelected(), this);
		} else {
			m_messageList.show(this);
		}
	}
	
	public void onClick(View v) {
		switchToPreferences();
	}
	
	public void switchToPreferences() {
		RSSPreferences.show(this);
	}

	public void incomingEvent(GlobalEvent ev) {
		
		Class<?> category = ev.getCategory(); 
		
		
		if (RSSGallery.class.equals(category)) {
			switchToMessageList(false, true);
		} else if (Message.class.equals(category)) {
			
			switch(ev.getQualifier()) {
			
				case EXPANDED :
					switchToMessageList(false, false);
					break;
					
				case SELECTED :
					switchToLinkList((RSSMessage)ev.getData());
				
			}
			
		} else if (LinkList.class.equals(category)) {
			Log.v("Got", "LinkList");
			if (ev.getQualifier() == CLOSED) {
				Log.v("Got", "closed");
				switchToMessageList(true, true);
			}
		}
		
	}	
}