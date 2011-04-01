package org.blanco.lacuenta.misc;

import java.text.NumberFormat;

import org.blanco.lacuenta.R;
import org.blanco.lacuenta.db.entities.Split;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.Facebook;
/***
 * Class that will handle all the tasks needed to publish the 
 * result of a bill split in facebook.
 * It uses the facebook opensource API to present the needed
 * dialogs
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class FacebookPublisher {

	//La cuenta facebook app id.
	private static final String APP_ID = "160955077289390";
	
	Activity ctx = null;
	Facebook facebook = null;
	Split split = null;
	
	public FacebookPublisher(Activity ctx, Split split) {
		if (ctx == null)
			throw new IllegalArgumentException("Activity for Facebook Publisher can not be null");
		this.ctx = ctx;
		this.split = split;
		initFacebookObject();
	}
	
	/***
	 * It inits the facebook object used to publish the message
	 */
	private void initFacebookObject(){
		facebook = new Facebook(APP_ID);
		Log.d("la-cuenta","facebook publisher. facebook object session state: "+facebook.isSessionValid());
		if (!facebook.isSessionValid())
			facebook.authorize(this.ctx, new BasicFBDialogListener(ctx){
				@Override
				public void onComplete(Bundle values) {
					Toast.makeText(ctx, ctx.getString(R.string.access_granted), 1000).show();
					publishMessage();
				}
			});
	}
	
	/***
	 * The actual method that publishes the message on facebook. It retrieves the
	 * message from the application resources and inserts the result from 
	 * the Split object used to build the publisher.
	 */
	void publishMessage(){
		String msg = ctx.getString(org.blanco.lacuenta.R.string.facebookMsg);
		msg = msg.replace("{0}", NumberFormat.getCurrencyInstance().format(this.split.getResult()));
		Bundle params = new Bundle();
		params.putString("message",msg);
		facebook.dialog(ctx, "stream.publish",params, new BasicFBDialogListener(ctx) {
			@Override
			public void onComplete(Bundle values) {
				Toast.makeText(ctx, ctx.getString(R.string.fb_msg_posted), 1000).show();
			}
		});
	}
	
}
