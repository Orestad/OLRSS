package ol.rss.datasources;

import org.xml.sax.ContentHandler;
import ol.rss.generic.LoggedException;
import android.util.Xml;
import static ol.rss.generic.Utilities.trim;
import static ol.rss.generic.IllegalEmpty.checkNull;


/*!*/
public class StringSource extends Sibling<StringSource> implements XMLSource {
	
	/*!*/
	private String m_data;
	
	/*!*/
	public StringSource(String data) {
		super();
		m_data = trim(data);
	}
	
	/*!*/
	public StringSource(StringSource original) {
		super(original);
		m_data = original.m_data;
	}
	
	/*!*/
	@Override public StringSource clone() {
		synchronized(this) { return new StringSource(this); }
	}
	
	/*!*/
	public void parse(ContentHandler handler) {
		
		checkNull(handler);
		
		try {
			if (m_data.length() > 0) {
				synchronized(this) { Xml.parse(m_data, handler); }
			}
		} catch (Exception e) {
			throw new LoggedException(e);
		}
	}
}
