package org.blanco.lacuenta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		initComponents();
	}
	
	private void initComponents(){
		this.chkPreferences = (CheckBox) findViewById(R.id.settings_activity_chk_preferences);
		this.chkPreferences.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				getSharedPreferences("settings", Activity.MODE_PRIVATE).edit().putBoolean("save_prefs", isChecked).commit();
			}
		});
		this.chkPreferences.setChecked(
		getSharedPreferences("settings",Activity.MODE_PRIVATE).getBoolean("save_prefs", false));
	}
	
	CheckBox chkPreferences = null;
}