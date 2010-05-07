package ol.rss.rss;

import java.io.InputStream;
import java.net.URL;

import ol.rss.datasources.URLSource;
import ol.rss.generic.LoggedException;
import ol.rss.messages.MessageFilter;
import ol.rss.messages.MessageSource;
import android.graphics.Bitmap;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;
import android.widget.ImageView;
import static ol.rss.generic.Utilities.loadBitmap;

public class RSSSource extends URLSource<RSSSource> implements MessageSource {

	public static final String ATR_NAME="name";
	public static final String ATR_IMAGE="image";
	public static final String ATR_FILTER="filter";
	
	public static final String FEED_URL = "url";
	public static final String NAME = "name";
	public static final String IMAGE = "image";
	public static final String FILTER = "filter";
	
	private boolean m_noMeta;
	
	// synka på this
	
	
	// to create a template
	public RSSSource(Element elem) {
		
		super();
		
		registerAttributeListener(elem, FEED_URL, FEED_URL);
		registerAttributeListener(elem, NAME, NAME);
		registerAttributeListener(elem, IMAGE, IMAGE);
		registerAttributeListener(elem, FILTER, FILTER);
		
		m_noMeta = true;
	}
	
	public RSSSource(RSSSource original) {
		super(original);
		
		m_noMeta = original.m_noMeta;
	}
	
	// GARANTI
	@Override public RSSSource clone() {
		return new RSSSource(this);
	}
	
	protected void fetchMeta() {
	
		try {
		
			URL metaURL = getURL();
			InputStream in = metaURL.openStream();
		
			RootElement root = new RootElement("rss");
			Element elem = root.getChild("channel");
			elem = elem.getChild("image");
		
			registerAttributeListener(elem, "url", ATR_IMAGE);
			registerAttributeListener(elem, "width", "image_width");
			registerAttributeListener(elem, "height", "image_height");
			
			Xml.parse(in, Xml.Encoding.UTF_8, root.getContentHandler());
			
			m_noMeta = false;
		
		} catch(Exception e) {
			throw new LoggedException(e);
		}

	}
	
	
	
	public String getName() {
		
		if (m_noMeta) {
			fetchMeta();
		}
		
		return getAttribute(ATR_NAME);
	}
	
	public Bitmap getImage() {
		
		try {
			if (m_noMeta) {
				fetchMeta();
			}
		
			return loadBitmap(new URL(getAttribute(ATR_IMAGE)));
		} catch (Exception e) {
			Log.v("got", getAttribute(ATR_IMAGE));
			return null;
		}
		
	}
	
	public MessageFilter getFilter() {
		
		String className = getAttribute(ATR_FILTER);
		
		if (className.length() > 0) {
			
			try {
				
				Class<?> targetClass = Class.forName(className);
				return (MessageFilter)targetClass.newInstance();
			
			} catch (Exception e) { /* todo */ }
		}
		
    	return null;
    }
}
