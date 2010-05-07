package ol.rss.rss;

import org.xml.sax.ContentHandler;

import ol.rss.R;
import ol.rss.ViewCentral;
import ol.rss.events.EventBridge;
import ol.rss.generic.Popups;
import ol.rss.messages.MessageList;
import android.sax.Element;
import android.sax.RootElement;
import android.widget.ListView;

// todo; flytta ut galleriet till ViewCentral

public class RSSMessageList extends MessageList<RSSMessage>  {
	
	private ContentHandler m_handler;
	private RSSMessage m_template;
	
	public RSSMessageList(RSSSource target, ViewCentral hub, EventBridge eventExchange) {
		
		super(hub);
		
		RootElement root = new RootElement("rss");
		Element channel = root.getChild("channel");
		Element item = channel.getChild("item");
		
		item.setEndElementListener(this);
		m_template = new RSSMessage(hub, item, eventExchange);
		
		m_handler = root.getContentHandler();
		
	}
	
	public void show(RSSSource source, ViewCentral hub) {
		
    	try {
    		
    		//Popups.instance.showLoadWait();
			showSource(source, m_handler, m_template, (ListView)hub.findViewById(R.id.theList));
			Popups.instance.hideLoadWait();
    		
    		
    	} catch (Exception e) {
    		throw new RuntimeException(e); 
    	}
    }
	
	public void show(ViewCentral hub) {

		try {
    		
    		//Popups.instance.showLoadWait();
			
			showAgain((ListView)hub.findViewById(R.id.theList));
			
			Popups.instance.hideLoadWait();
    		//Toast.makeText(hub, source.getName(), Toast.LENGTH_SHORT).show();
    		
    	} catch (Exception e) {
    		throw new RuntimeException(e); 
    	}
	}
	
}
