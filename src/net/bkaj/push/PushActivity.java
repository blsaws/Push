package net.bkaj.push;	

import java.net.URISyntaxException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PushActivity extends Activity {

    public static WebView webview;
    public static GapPushSource pushSource;
    
    public class PushSourceFactory {
		private WebView mView;
		public PushSourceFactory(WebView webview) {
			mView = webview;
		}
		public PushSource getNew(String url) throws URISyntaxException {
			pushSource = new GapPushSource(mView, url);
		    return pushSource;
		}
	} 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        webview = new WebView(this);
        setContentView(webview);
        webview.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
          public void onProgressChanged(WebView view, int progress) {
            activity.setProgress(progress * 1000);
          }
        });
        webview.setWebViewClient(new WebViewClient() {
          public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
          }
        });
       
       PushSourceFactory pushSourceFactory = new PushSourceFactory(webview);
       webview.addJavascriptInterface(pushSourceFactory, "PushSourceFactory");
       webview.loadUrl("http://bkaj.net/test/push/api");
    }
    
    @Override
    public void onNewIntent(Intent intent) {
    	if (pushSource.mReadyState == PushSource.OPEN) {
        	Bundle bundle = intent.getExtras();
        	String str = "";
        	String data = "";
    		 if (bundle != null) { 
    			 /**	To be added
    			  *	if (intent.getAction().equals(Intents.WAP_PUSH_RECEIVED_ACTION)) {
    			  * 	String mimeType = intent.getType();
    			  * 	String headers = intent.getStringExtra("header");
    			  * 	data = intent.getStringExtra("data");
    			  * 	String contentTypeParameters = intent.getStringExtra("contentTypeParameters");
    			  * 	pushSource.onmessage(headers+"\n"+data); 
    			  *	} 
    			  *	else {
    			  *	}
    			  */
    			 int i = pushSource.mUrl.indexOf(':');
    			 int l = pushSource.mUrl.length();
    			 String match = "";
    			 if (l > i+1) {
    				 match = pushSource.mUrl.substring(i+1);
    			 }
    			 SmsMessage[ ] msgs = null; 
    			 Object[ ] pdus = (Object[ ]) bundle.get("pdus"); 
    			 msgs = new SmsMessage[pdus.length];
    			 String source;
    			 for (i=0; i<msgs.length; i++) { 
    				 msgs[i] = SmsMessage.createFromPdu((byte[ ])pdus[i]);
    				 source = msgs[i].getOriginatingAddress();
    				 str += "Message from sms:" + source; 
    				 str += " :";
    				 data = msgs[i].getMessageBody().toString();
    				 str += data;
    				 // TODO: Need to deliver to all PushSource objects created (there could be more than one)
    				 if (match.equals("") || match.equals(source)) {
        					 pushSource.onmessage(data);
        				 }
    				 }
    			 Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    		 }
    	}
    }
}