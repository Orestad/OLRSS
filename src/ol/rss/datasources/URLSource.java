package ol.rss.datasources;

import java.io.InputStream;
import java.net.URL;
import org.xml.sax.ContentHandler;
import ol.rss.generic.LoggedException;
import android.util.Xml;
import static ol.rss.generic.IllegalEmpty.*;


abstract public class URLSource<Datatype extends URLSource<Datatype>> 
extends Sibling<Datatype> implements XMLSource 
{	
	/*!*/
	public static final String ATR_URL = "url";
	
	/*!*/
	protected URLSource() {
		super();
	}
	
	/*!*/
	public URLSource(Datatype original) {
		super(original);
	}
	
	/*!*/
	@Override abstract public Datatype clone();
	
	/*!*/
	public void setURL(URL newURL) {
		synchronized(newURL) {
			setAttribute(ATR_URL, (newURL == null)? "" : newURL.toString());
		}
	}
	
	/*!*/
	public URL getURL() {	
		try {
			return new URL(getAttribute(ATR_URL));
		} catch (Exception e) {
			throw new LoggedException(e);
		}
	}
	
	/*!*/
	public void parse(ContentHandler handler) {
		
		checkNull(handler);
		
		try {
			
			synchronized(this) {
				
				InputStream m_in = getURL().openStream();
				checkNull(m_in);
			
				Xml.parse(m_in, Xml.Encoding.UTF_8, handler);
			}
			
		} catch (Exception e) {
			throw new LoggedException(e);
		}
	}
	
}
