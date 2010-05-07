package ol.rss.events;


public class GlobalEvent {

	private Class<?> m_category;
	private Qualifiers m_qualifier;
	private Object m_data;
	
	public GlobalEvent(Class<?> category, Qualifiers qualifier, Object data) {
		m_category = category;
		m_qualifier = qualifier;
		m_data = data;
	}
	
	public Class<?> getCategory() {
		return m_category;
	}
	
	public Qualifiers getQualifier() {
		return m_qualifier;
	}
	
	public Object getData() {
		return m_data;
	}
	
	public enum Qualifiers { EXPANDED, SELECTED, CLOSED }
}
