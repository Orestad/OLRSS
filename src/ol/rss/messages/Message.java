package ol.rss.messages;

import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import ol.rss.R;
import ol.rss.ViewCentral;
import ol.rss.datasources.Sibling;
import ol.rss.events.EventBridge;
import ol.rss.events.GlobalEvent;
import static ol.rss.events.GlobalEvent.Qualifiers.*;

abstract public class Message<Datatype extends Message<Datatype>> 
extends Sibling<Datatype> implements OnClickListener, PackageResources {
	
	public static final String ATR_FILTER = "filter";
	
	private boolean m_open;
	private LayoutInflater m_inflater;
	private EventBridge m_eventExchange;
	
	protected Message(ViewCentral hub, EventBridge eventExchange) {
		
		super();
		
		resource_creator.initiate(hub);
		
		m_eventExchange = eventExchange;
		m_inflater = LayoutInflater.from(hub);
		
		m_open = false;
	}
	
	protected Message(Datatype original) {
		
		super(original);
		
		m_eventExchange = original.m_eventExchange; 
		m_open = original.m_open;
		m_inflater = original.m_inflater;
	}
	
	@Override abstract public Datatype clone();
	
	public void setFilter(MessageFilter newFilter) {
		setAttribute(ATR_FILTER, newFilter.getClass().getName());
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
	
	public boolean isOpen() {
		return m_open;
	}
	
	public void toggleOpen() {
		m_open = !m_open;
	}
	
	public void onClick(View v) {
		
		if (v instanceof ImageView) {
			toggleOpen();
			m_eventExchange.putEvent(new GlobalEvent(Message.class, EXPANDED, this));
		} else {		
			m_eventExchange.putEvent(new GlobalEvent(Message.class, SELECTED, this));
		}		

	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = m_inflater.inflate(R.layout.group_row, null);
		rowView.setTag(new ViewHolder(rowView, this));
	
		return rowView;
	}
	
	abstract public SpannableString getText();
	
	static class ViewHolder {
		
		TextView text;
		ImageView icon;
		
		private ViewHolder(View rowView, Message msg) {
			icon = (ImageView) rowView.findViewById(R.id.pengu);
			icon.setImageBitmap(msg.isOpen() ? ARROW_COLLAPSE.bitmap : ARROW_EXPAND.bitmap);
			icon.setOnClickListener(msg);
			
			text = (TextView) rowView.findViewById(R.id.groupname);
			text.setText(msg.getText());
			text.setOnClickListener(msg);
		}
	}
	
}
