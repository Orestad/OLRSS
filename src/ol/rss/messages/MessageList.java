package ol.rss.messages;


import ol.rss.ViewCentral;
import ol.rss.datasources.XMList;
import android.view.View;
import android.view.ViewGroup;


// todo; trådsäkra, kontrollera at det finns en hård koppling inom cursorn

abstract public class MessageList<Datatype extends Message<Datatype>> extends XMList<Datatype> {

	public MessageList(ViewCentral hub) {
		super();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return getItem(position).getView(position, convertView, parent);
	}
	
}
