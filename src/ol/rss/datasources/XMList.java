package ol.rss.datasources;

import org.xml.sax.ContentHandler;
import android.sax.EndElementListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import static ol.rss.generic.OutOfRange.isInRange;
import static ol.rss.generic.IllegalEmpty.checkNull;


public abstract class XMList<Datatype extends Sibling<Datatype>> 
extends BaseAdapter implements ListAdapter, EndElementListener
{	
	/*!*/
	private int m_size;
	private final Object m_synchronizer = new Object();
	
	private Datatype m_template;	
	private Datatype m_previous;
	
	private Datatype m_current;
	private int m_currentIndex;
	
	/*!*/
	public XMList() {
		super();
		clear(null);
	}
	
	/*!*/
	public int getCount() {
		synchronized(m_synchronizer) { return m_size; }
	}
	
	/*!*/
	public long getItemId(int position) {
        return position;
    }
	
	/*!*/
	public Datatype getCurrent() {
		synchronized(m_synchronizer) { return m_current; }
	}
	
	
	public Datatype getItem(int position) {
		
		synchronized(m_synchronizer) {
			
			if (isInRange(0, m_size - 1, position)) {
			
				int direction = position - m_currentIndex;
		
				if (direction != 0) {
					direction = direction / Math.abs(direction);
				
					do {
						
						m_currentIndex += direction;
						
						if (direction > 0) {
							m_current = m_current.getNext();
						} else {
							m_current = m_current.getPrevious();
						}
						
					} while (m_currentIndex != position);
				}
			
				return m_current;
			}
		}
		
		return null;
	}
	
	/*!*/
	public void end() {
		
		synchronized(m_synchronizer) {
		
			checkNull(m_template);
    	
			Datatype tmpl = m_template.clone();
			checkNull(tmpl);
			
			m_size++;
			m_template.setPrevious(tmpl);
    	
			if (m_previous == null) {
				m_current = tmpl;
				m_currentIndex = 0;
			} else { 
				m_previous.setNext(tmpl); 
			}
    	
			m_previous = tmpl;
		}
    }
	
	/*!*/
	private void clear(Datatype template) {
		
		m_template = template;
		m_previous = null;
		m_current = null;
		
		m_size = 0;
		m_currentIndex = -1;
	}
	
	/*!*/
	@SuppressWarnings("unchecked")
	protected void showSource(XMLSource target, ContentHandler handler, Datatype template, AdapterView visual) {
		
		checkNull(target, handler, template, visual);
		
		synchronized(m_synchronizer) {
			clear(template);
			target.parse(handler);
			m_template = null;
		}
		
		visual.setAdapter(this);
	}
	
	@SuppressWarnings("unchecked")
	protected void showAgain(AdapterView visual) {
		checkNull(visual);
		visual.setAdapter(this);
	}
	
}
