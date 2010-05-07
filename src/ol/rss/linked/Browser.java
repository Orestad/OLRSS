package ol.rss.linked;


import ol.rss.generic.Popups;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Browser {

	private WebView m_webView;
	
	public Browser() {}
	
	public void show(WebView vw, Link<?> target) {
		
		Log.v("show link",  String.valueOf(vw));
		
		if (m_webView != vw) {
		
			m_webView = vw;
			
			WebSettings webSettings = m_webView.getSettings();
			webSettings.setSavePassword(false);
			webSettings.setSaveFormData(false);
			webSettings.setJavaScriptEnabled(false);
			webSettings.setSupportZoom(true);

			m_webView.setWebChromeClient(new WebChromeClient());
			m_webView.setWebViewClient(new Client());
		}
		
		m_webView.loadUrl(target.getLink());
	}
	
	public void showAgain(Link<?> target) {
		m_webView.loadUrl(target.getLink());
	}
	
	public class Client extends WebViewClient {
		
		private Client () {
			super();
		}
		
		@Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageFinished(view, url);
			Popups.instance.showLoadWait();
		}
		
		@Override public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Popups.instance.hideLoadWait();
		}
	}
}
