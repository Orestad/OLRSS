package ol.rss.generic;

import java.text.MessageFormat;
import static java.lang.String.valueOf;


public class OutOfRange extends RuntimeException {
	
	private static final long serialVersionUID = 329599542875311573L;
	public static final String MESSAGE = "Value {0} out of range expected range [{1}-{2}]";
	
	public OutOfRange(int min, int max, int actual) {
		super(MessageFormat.format(MESSAGE, valueOf(actual), valueOf(min), valueOf(max)));
	}
	
	public static boolean isInRange(int min, int max, int actual) {
		return (min <= max) && (actual >= min) && (actual <= max);
	}
	
	public static void checkRange(int min, int max, int actual) {
		if ((min > max) || (actual > max) || (actual < min)) throw new OutOfRange(min, max, actual);
	}
}
