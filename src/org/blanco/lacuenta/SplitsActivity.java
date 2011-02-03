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

import java.util.ArrayList;
import java.util.List;

import org.blanco.lacuenta.db.SPLITSContentProvider;
import org.blanco.lacuenta.db.entities.Split;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SplitsActivity extends Activity {
	/* Called when the activity is first created. */

	private TableLayout tbl = null;
	private TableRow row = null;

	private android.widget.TableRow.LayoutParams rowParams = new android.widget.TableRow.LayoutParams(
			android.widget.TableRow.LayoutParams.FILL_PARENT,
			android.widget.TableRow.LayoutParams.WRAP_CONTENT);

	private android.widget.TableRow.LayoutParams tableParams = new android.widget.TableRow.LayoutParams(
			android.widget.TableRow.LayoutParams.FILL_PARENT,
			android.widget.TableRow.LayoutParams.WRAP_CONTENT);

	// set database location
	public String DB_PATH = "/data/data/org.blanco.lacuenta/databases/";
	public String DB_NAME = "lacuenta.db";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.databaselayout);

		tbl = (TableLayout) findViewById(R.id.myTableLayout);
		tbl.setBackgroundColor(Color.BLACK);
		Cursor q = 	managedQuery(SPLITSContentProvider.CONTENT_URI, 
				new String[]{Split.TOTAL,Split.TIP, Split.PEOPLE,Split.RESULT,Split.DATE}, 
				null, null, null);
		List<Split> splits = new ArrayList<Split>();
		while(q.moveToNext()){
			Split s = Split.fromCurrentCursorPosition(q);
			splits.add(s);
		}
		
	}

}