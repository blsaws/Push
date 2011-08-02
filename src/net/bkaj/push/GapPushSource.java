package net.bkaj.push;

import java.net.URISyntaxException;

import android.util.Log;
import android.webkit.WebView;

 public class GapPushSource extends PushSource{

	WebView mView;
	
	public GapPushSource(WebView v, String url) throws URISyntaxException {
		super(url);
		mView = v;
	}
	
	protected static class JSEvent {
		static String buildJSON(String type, String target, String data) {
			Log.i("JSEvent", "{\"type\":\"" + type + "\",\"target\":\"" + target + "\",\"data\":'"+ data +"'}");
			return "{\"type\":\"" + type + "\",\"target\":\"" + target + "\",\"data\":'"+ data +"'}";
		}
		
		static String buildJSON(String type, String target) {
			return "{\"type\":\"" + type + "\",\"target\":\"" + target + "\",\"data\":\"\"}";
		}		
	}
	
	@Override
	protected void onmessage(String data) {
		mView.loadUrl("javascript:PushSource.triggerEvent(" + JSEvent.buildJSON("message", this.toString(), data) + ")");
	}

	@Override	
	protected void onopen() {
		mView.loadUrl("javascript:PushSource.triggerEvent(" + JSEvent.buildJSON("open", this.toString()) + ")");
	}
	
	@Override	
	protected void onerror() {
		mView.loadUrl("javascript:PushSource.triggerEvent(" + JSEvent.buildJSON("error", this.toString()) + ")");
	}

	public String getIdentifier() {
		return this.toString();
	}

}
