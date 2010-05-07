package ol.rss.datasources;

import org.xml.sax.ContentHandler;

/*!*/
public interface XMLSource {
	
	/*!*/
	public void parse(ContentHandler handler);
}
