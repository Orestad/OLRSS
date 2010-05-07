package ol.rss.datasources;

import java.util.HashMap;
import android.sax.Element;
import android.sax.EndTextElementListener;
import static ol.rss.generic.IllegalEmpty.*;
import static ol.rss.generic.Utilities.*;

// Please note : the m_previous & m_next attributes are not guaranteed to contain non-null values
// this also means that the getters may return null (and that the setters accepts null)


abstract public class Sibling<Datatype extends Sibling<Datatype>> implements Cloneable {
	
	/*!*/
	private HashMap<String, String> m_attributes;
	private Datatype m_previous, m_next;
	
	/*!*/
	protected Sibling() {
		m_attributes = new HashMap<String, String>();
	}
	
	/*!*/
	protected Sibling(Datatype original) {
		
		checkNull(original);
		m_attributes = new HashMap<String, String>();
		
		synchronized(original) {
			m_attributes.putAll(original.m_attributes);
			m_previous = original.m_previous;
			m_next = original.m_next;
		}
	}
	
	/*!*/
	@Override abstract public Datatype clone();	
	
	/*!*/
	protected void setAttribute(String key, String value) {
		
		key = trimCheck(key);
		
		synchronized(this) { 
			m_attributes.put(key, value); 
		}
	}
	
	/*!*/
	protected String getAttribute(String key) {
		
		key = trim(key);
		if (key.length() < 1) return "";

		synchronized(this) {
			return m_attributes.get(key);
		}
		
	}
	
	/*!*/
	public void resetCursor(Datatype endPointer) {
		
		checkNull(endPointer);
		
		synchronized(this) {
			m_previous = endPointer;
			m_next = endPointer;
		}
	}
	
	public boolean hasPrevious() {
		return m_previous != null;
	}
	
	/*!*/
	public void setPrevious(Datatype previous) {
		synchronized(this) { m_previous = previous; }
	}
	
	/*!*/
	public Datatype getPrevious() {
		synchronized(this) { return m_previous; }
	}
	
	public boolean hasNext() {
		return m_next != null;
	}
	
	/*!*/
	public void setNext(Datatype next) {
		synchronized(this) { m_next = next; }
	}

	/*!*/
	public Datatype getNext() {
		synchronized(this) { return m_next; }
	}
	
	/*!*/
	public void registerAttributeListener(Element item, String tagName, final String name) {
		item.getChild(tagName).setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				setAttribute(name, body);
			}
		});
	}
}
