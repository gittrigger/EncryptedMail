package com.havenskys.platinum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageReceiverPrivileged extends MessageReceiver {
	private static final String TAG = "RSASMS MessageReceiverPrivileged";
	public void onReceive(Context c, Intent i){
		Log.i(TAG,"onReceive() sending to onReceiveWithPrivilege() in MessageReceiver");
		onReceiveWithPrivilege(c,i,true);
	}
}
