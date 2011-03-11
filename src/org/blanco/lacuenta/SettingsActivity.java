package org.blanco.lacuenta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * Class that will extends the Activity class in order to 
 * present an Interface to the User to control application
 * settings.
 */
public class SettingsActivity extends Activity {

	public static final String SHARED_PREFS_NAME = "la_cuent_prefs";
	public static final String SAVE_PREFS_SETTING_NAME = "save_prefs";
	public static final String SHOW_RES_DIALOG_SETTING_NAME = "show_r_on_dialog";
	public static final String SAY_RES_OUT_LOUD = "say_result_out_loud";
	public static final int NO_SETTINGS_CHANGED = 0;
	public static final int SETTINGS_CHANGED = 1;
	
	PrefsInitialState settsInitialState = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initComponents();
	}
	
	private void initComponents(){
		this.chkPreferences = prepareSettingsCheckBox( R.id.settings_activity_chk_save_prefs, SAVE_PREFS_SETTING_NAME);
		this.chkShowResultOnDialog = prepareSettingsCheckBox(R.id.settings_activity_chk_show_res_on_dialog, SHOW_RES_DIALOG_SETTING_NAME); 
		this.chkSayResultOutLoud = prepareSettingsCheckBox(R.id.settings_activity_chk_say_result_out_loud, SAY_RES_OUT_LOUD);
		settsInitialState = new PrefsInitialState(this.chkPreferences.isChecked(), 
				this.chkShowResultOnDialog.isChecked(), this.chkSayResultOutLoud.isChecked());
	}
	
	/***
	 * This method retrieves a CheckBox control, stores it in the passed identifier, sets a CheckChanged listener
	 * to store the vale of the control in the SHARED_PREFS_NAME shared preferences with the passed
	 * settingName. This in order to relate one CheckBox with one boolean share preference.
	 * @param controlPtr The Identifier of the object where the retrieved control will be stored
	 * @param controlId The int number of the control to be retrieved from the current contentView
	 * @param settingName The String setting name where the state of the combo will be saved.
	 * @return the loaded CheckBox
	 */
	private CheckBox prepareSettingsCheckBox(int controlId, final String settingName){
		
		CheckBox controlPtr = (CheckBox) findViewById(controlId); // retrieve the control
		//Set the check listener to save the pref when changed.
		controlPtr.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).edit().putBoolean(settingName, arg1).commit();
				setActResult();
			}
		});
		//Load the state of the control from the shared preferences
		controlPtr.setChecked(getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).getBoolean(settingName, false));
		return controlPtr;
	}
	
	private void setActResult(){
		int result = SettingsActivity.NO_SETTINGS_CHANGED;
		if (settsInitialState != null)
		 result = settsInitialState.returnPreferencesChange(chkPreferences, chkShowResultOnDialog, chkSayResultOutLoud); 
		setResult(result);
	}

	CheckBox chkPreferences = null;
	CheckBox chkShowResultOnDialog = null;
	CheckBox chkSayResultOutLoud=null;
	/***
	 * Inner class that will hold the initial values for the settings combos and will calculate 
	 * if there has been a settings value change in order to set the activity result correclty
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	class PrefsInitialState{
		public boolean saveValues = false;
		public boolean showDialog = false;
		public boolean sayOutLoud = false;
		
		public PrefsInitialState(){
			
		}
		
		public PrefsInitialState(boolean saveValsState, boolean showValsState, boolean sayOutLoudState) {
			this.saveValues = saveValsState;  this.showDialog = showValsState; this.sayOutLoud = sayOutLoudState;
		}
		
		public int returnPreferencesChange(CheckBox values,CheckBox dialog,CheckBox sayOutLoud){
			if (values.isChecked() == saveValues && dialog.isChecked() == showDialog 
					&& sayOutLoud.isChecked() == this.sayOutLoud)
				//current values are equal to previous values
				return NO_SETTINGS_CHANGED;
			else
				return SETTINGS_CHANGED;
		}
	}
	
}