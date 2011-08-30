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
package org.blanco.lacuenta.fragments;

import org.blanco.lacuenta.R;
import org.blanco.lacuenta.db.dataloaders.AbstractSplitsDataLoader;
import org.blanco.lacuenta.db.dataloaders.SplitsChartDataLoader;
import org.blanco.lacuenta.db.dataloaders.SplitsDataLoader;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
/***
 * Fragment that will present the user the splits that have been
 * stored in the database and let the user filter the data differently.
 * 
 * The code of this class has been migrated from the SplitsActivity
 * in order to support fragments in the application.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class GraphFragment extends Fragment {
	
	
	public static final String TODAY_LOAD = "today";
	public static final String WEEK_LOAD = "week";
	public static final String MONTH_LOAD = "month";

	public static final int TABLE_TARGET = 0;
	public static final int CHART_TARGET = 1;
	
	private ProgressDialog loadDialog = null;
	private String loadTarget = WEEK_LOAD;
	private int displayTarget = TABLE_TARGET;
	
	private AbstractSplitsDataLoader dataLoader = null;
	
	private ImageButton btnDay = null;
	private ImageButton btnWeek = null;
	private ImageButton btnMonth = null;
	private LinearLayout mainLayout = null;
	private ImageButton btnChangeTarget = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainLayout = (LinearLayout) inflater.inflate(R.layout.graph_fragment_initial_screen,null); 
		initComponents(mainLayout);
		return mainLayout;
	}

	@Override
	public View getView() {
		loadData();
		return super.getView();
	}

	/**
	 * Method that will initialise the members of the class based on the passed initial
	 * view that must have been created on the onCreateView method.
	 * @param view The initial View view where to retrieve the components from
	 */
	private void initComponents(View view){
		if (view == null){
			throw new IllegalArgumentException("Initial View can't be null");
		}
		btnDay = (ImageButton) view.findViewById(R.id.graph_action_bar_btn_day_target);
		btnDay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeLoadTarget(TODAY_LOAD);
			}
		});
		btnWeek = (ImageButton) view.findViewById(R.id.graph_action_bar_btn_week_target);
		btnWeek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeLoadTarget(WEEK_LOAD);
			}
		});
		btnMonth = (ImageButton) view.findViewById(R.id.graph_action_bar_btn_month_target);
		btnMonth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeLoadTarget(MONTH_LOAD);
			}
		});
		
		btnChangeTarget = (ImageButton) view.findViewById(R.id.graph_action_bar_btn_change_target);
		btnChangeTarget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeDisplayTarget(( displayTarget == CHART_TARGET)? TABLE_TARGET :CHART_TARGET);
			}
		});
	}

	public void loadData(){
		if (dataLoader == null || !AsyncTask.Status.RUNNING.equals(dataLoader.getStatus())){
			if (displayTarget == CHART_TARGET){
				dataLoader = new SplitsChartDataLoader(this);
			} else {
				dataLoader = new SplitsDataLoader(this);
			}
			//Set the background of the selected target
			btnDay.setBackgroundColor(Color.TRANSPARENT);
			btnWeek.setBackgroundColor(Color.TRANSPARENT);
			btnMonth.setBackgroundColor(Color.TRANSPARENT);
			if (loadTarget.equals(TODAY_LOAD)){
				btnDay.setBackgroundColor(Color.YELLOW);
			}else if (loadTarget.equals(WEEK_LOAD)){
				btnWeek.setBackgroundColor(Color.YELLOW);
			}else{
				btnMonth.setBackgroundColor(Color.YELLOW);
			}
			dataLoader.execute(loadTarget); 
		}
	}
	
	public void showLoadDialog(){
		if (this.loadDialog == null)
			loadDialog = ProgressDialog.show(getActivity(), 
					getString(R.string.loading_data_dialog_title), 
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
		if (executeLoad){
			showLoadDialog();
			loadData();
		}
	}
	
	private void changeDisplayTarget(int chartTarget) {
		boolean executeLoad = (displayTarget != chartTarget);
		displayTarget = chartTarget;
		if (executeLoad){
			loadData();
			int id = -1;
			id = (CHART_TARGET == displayTarget)? R.drawable.table : 
					R.drawable.chart;
			btnChangeTarget.setImageDrawable(getResources().getDrawable(id));
			btnChangeTarget.invalidate();
		}
	}
	
	public void setResultsView(View view){
		view.setTag("resultsView");
		View v = mainLayout.findViewWithTag("resultsView");
		if (v != null){
			//remove the previous result view
			mainLayout.removeView(v);
		}
		mainLayout.addView(view);
	}
	
//	@Override
//	public boolean onCreatePanelMenu(int featureId, Menu menu) {
//		MenuInflater mi = new MenuInflater(this);
//		mi.inflate(R.menu.splits_activity_main_menu, menu);
//		return super.onCreatePanelMenu(featureId, menu);
//	}
//
//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		switch (item.getItemId()){
//		case R.id.splits_activity_main_menu_week_item:
//			changeLoadTarget(WEEK_LOAD);
//			break;
//		case R.id.splits_activity_main_menu_month_item:
//			changeLoadTarget(MONTH_LOAD);
//			break;
//		case R.id.splits_activity_main_menu_today_item:
//			changeLoadTarget(TODAY_LOAD);
//			break;
//		case R.id.splits_activity_main_menu_chart_item:
//			changeDisplayTarget(CHART_TARGET);
//			break;
//		case R.id.splits_activity_main_menu_table_item:
//			changeDisplayTarget(TABLE_TARGET);
//			break;
//		}
//		return super.onMenuItemSelected(featureId, item);
//	}

}