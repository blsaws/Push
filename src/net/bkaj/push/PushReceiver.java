package net.bkaj.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

 public class PushReceiver extends BroadcastReceiver {
	 @Override 
	 public void onReceive(Context context, Intent intent) { 
		 Intent i = new Intent();
		 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 i.setClassName( "net.bkaj.push", "net.bkaj.push.PushActivity" );
		 i.putExtras(intent.getExtras());
		 context.startActivity(i); 
	 }
}