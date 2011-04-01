package org.blanco.lacuenta;

import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.misc.FacebookPublisher;

import android.app.Activity;
import android.os.Bundle;

public class FBPostActivity extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Split s = new Split(125.45, 10, 5, 15.5);
		new FacebookPublisher(this,s);
	}
}
