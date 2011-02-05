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

import java.util.Calendar;

import org.blanco.lacuenta.db.SPLITSContentProvider;
import org.blanco.lacuenta.db.entities.Split;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SplitsActivity extends Activity {
	/* Called when the activity is first created. */

	private TableLayout tbl = null;
	java.text.DateFormat df =  null; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.databaselayout);
		df = DateFormat.getDateFormat(this);
		
		tbl = (TableLayout) findViewById(R.id.myTableLayout);
				Cursor q = 	managedQuery(SPLITSContentProvider.CONTENT_URI, 
				new String[]{Split.TOTAL,Split.TIP, Split.PEOPLE,Split.RESULT,Split.DATE}, 
				null, null, null);
		//List<Split> splits = new ArrayList<Split>();
		int x = 0;
		double expenses = 0;
		while(q.moveToNext()){
			Split s = Split.fromCurrentCursorPosition(q);
			tbl.addView(buildRowViewFromSplit(s,x++));
			expenses+=s.getResult();
		}
	}
	
	private TableRow buildRowViewFromSplit(Split s, int type){
		TableRow v = (TableRow) getLayoutInflater().inflate(R.layout.splits_table_header, null);
		registerForContextMenu(v);
		
		((TextView)v.findViewById(R.id.splits_table_header_total)).setText(String.valueOf(s.getTotal()));
		((TextView)v.findViewById(R.id.splits_table_header_tip)).setText(String.valueOf(s.getTip()));
		((TextView)v.findViewById(R.id.splits_table_header_people)).setText(String.valueOf(s.getPeople()));
		((TextView)v.findViewById(R.id.splits_table_header_result)).setText(String.valueOf(s.getResult()));
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(s.getDate());
		((TextView)v.findViewById(R.id.splits_table_header_date)).setText(df.format(c.getTime()));
		v.setBackgroundColor((type%2==0)?Color.WHITE:Color.LTGRAY);
		
		return v;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater mi = new MenuInflater(this);
		mi.inflate(R.menu.splits_context_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		return super.onContextItemSelected(item);
	}
	
}