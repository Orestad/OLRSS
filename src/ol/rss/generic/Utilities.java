package ol.rss.generic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;

public class Utilities {

	// garantera icke-null
	static public String trim(String original) {
		return (original == null)? "" : original.trim();
	}
	
	static public String htmlToString(String source) {
		return trim(String.valueOf(Html.fromHtml(source)));
	}
	
	public static Bitmap loadBitmap(URL aURL) {
		
		Bitmap bm = null;
		
		try {
		
			InputStream is = aURL.openStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			bm = BitmapFactory.decodeStream(bis);

			bis.close();  
			is.close();  
		
		} catch(Exception e) {}
		
		return bm;
	}

	
}
