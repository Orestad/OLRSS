package ol.rss.generic;

public class LoggedException extends RuntimeException {

	private static final long serialVersionUID = -4140493042932401174L;

	public LoggedException(Exception cause) {
		super(cause);
	}
	
}
