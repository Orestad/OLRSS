package ol.rss.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import android.os.Handler;
import android.util.Log;


public class EventBridge extends Handler implements Runnable {

	private LinkedBlockingQueue<GlobalEvent> m_incoming;
	private HashMap<Class<?>, ArrayList<EventListener>> m_listeners;
	
	public EventBridge() {
		m_listeners = new HashMap<Class<?>, ArrayList<EventListener>>();
		m_incoming = new LinkedBlockingQueue<GlobalEvent>();
		
		(new Thread(this)).start();
	}
	
	public void addListener(Class<?> category, EventListener listener) {
		
		ArrayList<EventListener> list = m_listeners.get(category);
		
		if (list == null) {
			list = new ArrayList<EventListener>();
			m_listeners.put(category, list);
		}
		
		list.add(listener);
	}
	
	public void putEvent(GlobalEvent newEvent) {
		m_incoming.add(newEvent);
		Log.v("added event", newEvent.toString());
	}
	
	public void run() {
		
		while (true) {
			
			GlobalEvent ev = null;
			try { ev = m_incoming.poll(3600, TimeUnit.SECONDS); } catch (Exception e) { }
			
			if (ev != null) {
				ArrayList<EventListener> category = m_listeners.get(ev.getCategory());
				
				if (category != null) {
					for (EventListener listener : category) {
						super.post(new Dispatcher(listener, ev));
					}
				}
			}	
		}
	}
	
	public class Dispatcher implements Runnable {

		private EventListener m_listener;
		private GlobalEvent m_event;
		
		private Dispatcher(EventListener listener, GlobalEvent ev) {
			m_listener = listener;
			m_event = ev;
		}
		
		public void run() {
			m_listener.incomingEvent(m_event);	
		}
		
	}
	
}
