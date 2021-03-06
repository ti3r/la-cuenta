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

import org.blanco.lacuenta.fragments.LaCuentaFragmentPagerAdapter;
import org.blanco.lacuenta.misc.HeaderImagePageDisplayer;
import org.blanco.lacuenta.misc.LaCuentaPageChangeListener;
import org.blanco.lacuenta.misc.PageDisplayer;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Initial Activity of the Application.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0 12/20/2010
 */
public class MainActivity extends FragmentActivity {
	/***
	 * preference name where the last version will be stored.
	 */
	private static final String LAST_VERSION_RUN = "app_last_version_run_setting";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout_new);
		initComponents();
	}

	ViewPager pager = null;
	PageDisplayer displayer = null;
	/* The Text View that displays the name of the current page */
	TextView pageHeader = null;
	ImageView headerImg = null;
	
	private void initComponents() {
		pager = (ViewPager) findViewById(R.id.main_activity_view_pager);
		LaCuentaFragmentPagerAdapter adapter = new LaCuentaFragmentPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(adapter);
		headerImg = (ImageView) findViewById(R.id.main_activity_page_header_img);
		displayer = new HeaderImagePageDisplayer(headerImg);
		pageHeader = (TextView) findViewById(R.id.main_activity_current_page_header);
		pager.setOnPageChangeListener(new LaCuentaPageChangeListener(
				getResources(), displayer, pageHeader));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		new MenuInflater(this).inflate(R.menu.main_activity_main_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.main_activity_main_menu_exit_item:
			setResult(0);
			finish();
			break;
		case R.id.main_activity_main_menu_settings_item:
			startConfiguration();
			break;
		case R.id.main_activity_main_menu_save_expense_item:
			saveExpense();
			break;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
		return true;
	}

	/**
	 * The method that will invoke the save expense process of the
	 * SplitsFragment in order to store the current expense in database
	 */
	private void saveExpense() {
		boolean result = true;
		try {
			result = ((LaCuentaFragmentPagerAdapter) pager.getAdapter())
					.callSaveCurrentExpense(getApplicationContext());
		} catch (Exception e) {
			Log.e("la-cuenta",
					"saveExpense - error calling callsaveCurrentExpense", e);
			result = false;
		}
		if (!result) {
			Log.i("la-cuenta",
					"saveExpense - callsaveCurrentExpense returned false");
			Toast.makeText(getApplicationContext(),
					getString(R.string.str_error_calling_save_expense),
					Toast.LENGTH_LONG).show();
		}
	}

	/***
	 * Starts the configuration Activity
	 */
	private void startConfiguration() {
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivityForResult(settingsIntent, 0);
	}

	@Override
	protected void onStart() {
		AlertDialog ad = checkInitialDisplay();
		if (ad != null)
			ad.show();
		super.onStart();
	}

	/***
	 * This method is designed to check when the application starts for the
	 * first time on a new version and display the relative changeLog to the
	 * user in order to let him/her know the changes applied in the version.
	 * 
	 * @return the AlertDialog class that should be shown in case the initial
	 *         message needs to be displayed, null otherwise
	 */
	protected AlertDialog checkInitialDisplay() {
		try {
			PackageInfo pkgInfo = getApplicationContext()
					.getPackageManager()
					.getPackageInfo(getApplicationContext().getPackageName(), 0);
			int versionCode = pkgInfo.versionCode;
			int lastVersion = PreferenceManager.getDefaultSharedPreferences(
					this).getInt(LAST_VERSION_RUN, 0);
			if (lastVersion < versionCode) // if last version is less than the
											// current version of the manifest
			{
				// Show the initial Dialog
				int CLResourceId = getResources().getIdentifier(
						"changelog_" + versionCode, "array",
						"org.blanco.lacuenta");
				if (CLResourceId > 0) {
					String lines[] = getResources()
							.getStringArray(CLResourceId);
					StringBuilder text = new StringBuilder();
					for (String line : lines)
						text.append(line).append("\n");

					AlertDialog.Builder builder = new Builder(this);
					builder.setCancelable(false);
					builder.setPositiveButton(getString(R.string.str_ok),
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					builder.setMessage(text.toString());
					builder.setTitle(R.string.change_log_dialog_title);

					// Store the last run version on the preferences
					PreferenceManager.getDefaultSharedPreferences(this).edit()
							.putInt(LAST_VERSION_RUN, versionCode).commit();
					return builder.create();
				} else {
					Log.i("la-cuenta",
							"Error retrieving resource id, 0 returned for change_log"
									+ versionCode);
					return null;
				}
			}
		} catch (NameNotFoundException e) {
			Log.e("la-cuenta", "could not check the version of the app", e);
		}
		return null;
	}

}