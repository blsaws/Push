package net.bkaj.push;

/**
 * An experimental implementation of the OMA Push API. Designed for use in the Android platform.
 * @author blsaws
 */

public class PushSource {
	public String mUrl;
	public int mReadyState;
	public final static int CONNECTING = 0;
	public final static int OPEN = 1;
	public final static int CLOSED = 2;
	protected void onopen() {}
	protected void onmessage(String event) {}
	protected void onerror() {}
	
	public PushSource(String requestUrl) {
		this.mUrl = requestUrl;
		this.mReadyState = PushSource.OPEN;
	}

	public void close() {
		// TODO: Close Push reception
		this.mReadyState = PushSource.CLOSED;
	}

	/**
	 * These methods are required since for some reason even though the addJavascriptInterface method can be used
	 * to provide access to the Java interfaces to JavaScript, access to attributes directly (to the JavaScript
	 * objects returned from PushSourceFactory for example) does not work (no exception is thrown in the JavaScript, 
	 * but the actual values are not returned either).
	 */
	public int readyState() {
		return this.mReadyState;
	}

	public String url() {
		return this.mUrl;
	}	
}	