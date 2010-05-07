package ol.rss.generic;

import static ol.rss.generic.Utilities.*;

public class IllegalEmpty extends IllegalArgumentException{

	private static final long serialVersionUID = -1636762952851811223L;

	public IllegalEmpty(String txt) { super (txt); }

	public static void checkNull(Object ... sources) {
		
		int index = 0; 
		
		for (Object source : sources) {
			
			if (source == null) {
				throw new IllegalEmpty("Null, variable #" + String.valueOf(index));
			}
			
			index++;
		}
	}
	
	// garantera trimmat icke-empty värde, annars exception
	public static String trimCheck(String target) {
		target = trim(target);
		if (target.length() < 1) throw new IllegalEmpty("empty");
		
		return target;
	}
	
}
