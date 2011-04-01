package org.blanco.lacuenta.misc;

import org.blanco.lacuenta.R;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

/***
 * Abstract basic class that will handle the results of the facebook
 * dialogs when errors appear, it will display the errors to a basic
 * Toast message and let the onComplete method to be implemented by
 * a child class.
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public abstract class BasicFBDialogListener implements DialogListener {

	Context ctx = null;
	
	protected BasicFBDialogListener(Context context) {
		this.ctx = context;
	}
	
	@Override
	public abstract void onComplete(Bundle values);

	@Override
	public void onFacebookError(FacebookError e) {
		Toast.makeText(ctx, ctx.getString(R.string.fb_error)+" "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onError(DialogError e) {
		Toast.makeText(ctx, ctx.getString(R.string.fb_error)+" "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCancel() {
		Toast.makeText(ctx, ctx.getString(R.string.fb_cancelled), Toast.LENGTH_LONG).show();
	}

}
