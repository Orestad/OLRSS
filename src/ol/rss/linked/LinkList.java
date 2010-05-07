package ol.rss.linked;

import ol.rss.events.EventBridge;
import ol.rss.events.GlobalEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import static ol.rss.events.GlobalEvent.Qualifiers.*;

public class LinkList<Datatype extends Link<Datatype>> implements OnClickListener {
	
	private Datatype m_current;
	private Browser m_browser;
	private EventBridge m_eventExchange;
	
	private ImageButton m_backButton, m_forwardButton, m_upwardButton;
	
	public LinkList(EventBridge eventExchange) {
		m_eventExchange = eventExchange;
		m_browser = new Browser();
	}
	
	public void show(ImageButton back, ImageButton forward,  ImageButton upward, WebView wv, Datatype current) {
		
		m_backButton = back;
		m_backButton.setOnClickListener(this);
		
		m_forwardButton = forward;
		m_forwardButton.setOnClickListener(this);
		
		m_upwardButton = upward;
		m_upwardButton.setOnClickListener(this);
		
		m_current = current;
		m_browser.show(wv, m_current);
		setButtonStates();
	}

	public void showAgain() {
		m_browser.showAgain(m_current);
	}
	
	private void setButtonStates() {
		m_backButton.setClickable(m_current.hasPrevious());
		m_forwardButton.setClickable(m_current.hasNext());
	}
	
	public void onClick(View clicked) {
		
		if (clicked == m_backButton) {
			
			if (m_current.hasPrevious()) {
				setButtonStates();
				m_current = m_current.getPrevious();
				showAgain();
			}
			
		} else if (clicked == m_forwardButton) {
			
			if (m_current.hasNext()) {
				setButtonStates();
				m_current = m_current.getNext();
				showAgain();
			}
			
		} else if (clicked == m_upwardButton) {
			m_eventExchange.putEvent(new GlobalEvent(this.getClass(), CLOSED, null));
		}
	}
	
}
