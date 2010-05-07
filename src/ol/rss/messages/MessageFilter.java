package ol.rss.messages;

public interface MessageFilter {
	public String parse(String attributeName, String rawValue);
}
