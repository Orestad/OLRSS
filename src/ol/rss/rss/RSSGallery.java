package ol.rss.rss;

import org.xml.sax.ContentHandler;
import ol.rss.ViewCentral;
import ol.rss.datasources.StringSource;
import ol.rss.datasources.XMList;
import ol.rss.events.EventBridge;
import ol.rss.events.GlobalEvent;
import android.sax.Element;
import android.sax.RootElement;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import static ol.rss.events.GlobalEvent.Qualifiers.*;

public class RSSGallery extends XMList<RSSSource> implements OnItemClickListener {
	
	private ContentHandler m_handler;
	private RSSSource m_template;
	private ViewCentral m_hub;
	private EventBridge m_eventExchange;
	
	public RSSGallery(ViewCentral hub, EventBridge eventExchange) {
		
		super();
		m_hub = hub;
		
		RootElement root = new RootElement("sources");
		Element elem = root.getChild("source");
		elem.setEndElementListener(this);
	
		m_handler = root.getContentHandler();
		m_template = new RSSSource(elem);
		m_eventExchange = eventExchange;
	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	// todo; ändra cast 
    	
    	RSSSource source = (RSSSource)getItem(position);
    	ImageView i = new ImageView(m_hub);
    		
    	if (source != null) {
    		i.setImageBitmap(source.getImage());
    		i.setLayoutParams(new Gallery.LayoutParams(90, 50));
    		i.setScaleType(ImageView.ScaleType.FIT_XY);
    		//i.setBackgroundResource(mGalleryItemBackground);
    	}
    	
        return i;
    }
    
	public RSSSource getSelected() {
		return (RSSSource)getCurrent();
	}
	
	public void show(StringSource definitionXML, Gallery gallry) {
		showSource(definitionXML, m_handler, m_template, gallry);
		//Toast.makeText(hub, source.getName(), Toast.LENGTH_SHORT).show();
		gallry.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		m_eventExchange.putEvent(new GlobalEvent(getClass(), SELECTED ,getItem(position)));
	}
	
}
