/***
 *  La-Cuenta for Android, a Small application that allows users to split
 *  the restaurant check between the people that assists.
 *  Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blanco.lacuenta;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com> Class that will extends
 *         the Activity class in order to present an Interface to the User to
 *         control application settings.
 */
public class SettingsActivity extends PreferenceActivity implements
		OnPreferenceClickListener {

	public static final int NO_SETTINGS_CHANGED = 0;
	public static final int SETTINGS_CHANGED = 1;

	public static final String SAVE_PREFS_SETTING_NAME = "org.blanco.lacuenta.save_prefs";
	public static final String SHOW_RES_DIALOG_SETTING_NAME = "org.blanco.lacuenta.show_r_on_dialog";
	public static final String SAY_RES_OUT_LOUD = "org.blanco.lacuenta.say_result_out_loud";
	public static final String CONTACT_DEVELOPER = "org.blanco.lacuenta.contact_developer";

	/*
	 * public static final String
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		Preference p = findPreference(CONTACT_DEVELOPER);
		p.setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (CONTACT_DEVELOPER.equalsIgnoreCase(preference.getKey())) {

			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "ti3r.bubblenet@gmail.com" });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"La-Cuenta, comment");
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
		}
		return false;
	}

}