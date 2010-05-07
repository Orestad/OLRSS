package ol.rss.messages;

import android.app.Activity;
import android.graphics.Bitmap;
import android.content.res.Resources;

import static android.graphics.BitmapFactory.decodeResource;
import static ol.rss.R.drawable.*;

interface PackageResources {

	public static final Initializer resource_creator = new Initializer();
	
	public static final BitmapBucket ARROW_COLLAPSE = new BitmapBucket(); 
	public static final BitmapBucket ARROW_EXPAND = new BitmapBucket();

	
	public class Initializer {
		
		private boolean m_runMe = true;
		
		public void initiate(Activity act) {
			
			if (m_runMe) {
				
				Resources activity_resources = act.getResources(); 
			
				ARROW_COLLAPSE.bitmap = decodeResource(activity_resources, arrowcollapsed);
				ARROW_EXPAND.bitmap = decodeResource(activity_resources, arrowexpanded);
				
				m_runMe = false;
			}
		}
	}
	
	public class BitmapBucket { public Bitmap bitmap; }
	
}
