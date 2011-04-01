package org.blanco.lacuenta.misc;

import org.blanco.lacuenta.R;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public abstract class BasicFBDialogListener implements DialogListener {

	Context ctx = null;
	
	protected BasicFBDialogListener(Context context) {
		this.ctx = context;
	}
	
	@Override
	public abstract void onComplete(Bundle values);

	@Override
	public void onFacebookError(FacebookError e) {
		Toast.makeText(ctx, ctx.getString(R.string.fb_error)+e.getLocalizedMessage(), 2000).show();
	}

	@Override
	public void onError(DialogError e) {
		Toast.makeText(ctx, ctx.getString(R.string.fb_error)+e.getLocalizedMessage(), 2000).show();
	}

	@Override
	public void onCancel() {
		Toast.makeText(ctx, ctx.getString(R.string.fb_cancelled), 2000).show();
	}

}
