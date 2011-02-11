/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */

package org.blanco.lacuenta;

import org.blanco.lacuenta.misc.SplitsDataLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
/***
 * Activity that will present the user the splits that have been
 * stored in the database and let the user filter the data differently
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class SplitsActivity extends Activity {
	/* Called when the activity is first created. */

	private ProgressDialog loadDialog = null;
	private String loadTarget = SplitsDataLoader.WEEK_LOAD;
	private SplitsDataLoader dl = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadData();
	}

	private void loadData(){
		if (dl == null || !AsyncTask.Status.RUNNING.equals(dl.getStatus())){
			dl = new SplitsDataLoader(this);
			showLoadDialog();
			dl.execute(loadTarget);
		}
	}
	
	public void showLoadDialog(){
		if (this.loadDialog == null)
			loadDialog = ProgressDialog.show(this, getString(R.string.loading_data_dialog_title), 
					getString(R.string.loading_data_dialog_msg), true);
		this.loadDialog.show();
	}
	
	public void hideLoadDialog(){
		if (this.loadDialog != null)
			loadDialog.dismiss();
	}
	
	private void changeLoadTarget(String newTarget){
		boolean executeLoad = (!loadTarget.equals(newTarget));
		loadTarget = newTarget;
		if (executeLoad)
			loadData();
	}
	
	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		MenuInflater mi = new MenuInflater(this);
		mi.inflate(R.menu.splits_activity_main_menu, menu);
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
		case R.id.splits_activity_main_menu_week_item:
			changeLoadTarget(SplitsDataLoader.WEEK_LOAD);
			break;
		case R.id.splits_activity_main_menu_month_item:
			changeLoadTarget(SplitsDataLoader.MONTH_LOAD);
			break;
		case R.id.splits_activity_main_menu_today_item:
			changeLoadTarget(SplitsDataLoader.TODAY_LOAD);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}