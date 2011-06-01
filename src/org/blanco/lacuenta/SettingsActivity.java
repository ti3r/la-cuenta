package org.blanco.lacuenta;

import java.security.KeyStore.LoadStoreParameter;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * Class that will extends the Activity class in order to 
 * present an Interface to the User to control application
 * settings.
 */
public class SettingsActivity extends PreferenceActivity {

	public static final int NO_SETTINGS_CHANGED = 0;
	public static final int SETTINGS_CHANGED = 1;
	
	public static final String SAVE_PREFS_SETTING_NAME = "org.blanco.lacuenta.save_prefs";
	public static final String SHOW_RES_DIALOG_SETTING_NAME = "org.blanco.lacuenta.show_r_on_dialog";
	public static final String SAY_RES_OUT_LOUD = "org.blanco.lacuenta.say_result_out_loud";
	
	/*public static final String
	 * 
	 *  
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

}