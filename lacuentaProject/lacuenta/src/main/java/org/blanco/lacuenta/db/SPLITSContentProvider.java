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
package org.blanco.lacuenta.db;

import java.util.HashMap;

import org.blanco.lacuenta.db.entities.Split;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class SPLITSContentProvider extends ContentProvider {

	private LaCuentaDBHelper dbHelper;
	private static HashMap<String, String> SPLITS_PROJECTION_MAP;
	private static final String TABLE_NAME = "splits";
	private static final String AUTHORITY = "org.blanco.lacuenta.db.splitscontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri TOTAL_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/total");
	public static final Uri TIP_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/tip");
	public static final Uri PEOPLE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/people");
	public static final Uri RESULT_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/result");
	public static final Uri DATE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/date");

	public static final String DEFAULT_SORT_ORDER = "_ID ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int SPLITS = 1;
	private static final int SPLITS__ID = 2;
	private static final int SPLITS_TOTAL = 3;
	private static final int SPLITS_TIP = 4;
	private static final int SPLITS_PEOPLE = 5;
	private static final int SPLITS_RESULT = 6;
	private static final int SPLITS_DATE = 7;
	
	public boolean onCreate() {
		dbHelper = new LaCuentaDBHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case SPLITS:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(SPLITS_PROJECTION_MAP);
			break;
		case SPLITS__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case SPLITS_TOTAL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("total='" + url.getPathSegments().get(2) + "'");
			break;
		case SPLITS_TIP:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("tip='" + url.getPathSegments().get(2) + "'");
			break;
		case SPLITS_PEOPLE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("people='" + url.getPathSegments().get(2) + "'");
			break;
		case SPLITS_RESULT:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("result='" + url.getPathSegments().get(2) + "'");
			break;
		case SPLITS_DATE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("date='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case SPLITS:
			return "vnd.android.cursor.dir/vnd.org.blanco.lacuenta.db.splits";
		case SPLITS__ID:
			return "vnd.android.cursor.item/vnd.org.blanco.lacuenta.db.splits";
		case SPLITS_TOTAL:
			return "vnd.android.cursor.item/vnd.org.blanco.lacuenta.db.splits";
		case SPLITS_TIP:
			return "vnd.android.cursor.item/vnd.org.blanco.lacuenta.db.splits";
		case SPLITS_PEOPLE:
			return "vnd.android.cursor.item/vnd.org.blanco.lacuenta.db.splits";
		case SPLITS_RESULT:
			return "vnd.android.cursor.item/vnd.org.blanco.lacuenta.db.splits";
		case SPLITS_DATE:
			return "vnd.android.cursor.item/vnd.org.blanco.lacuenta.db.splits";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
        if (URL_MATCHER.match(url) != SPLITS) {
            throw new IllegalArgumentException("Unknown URL " + url);
        }

        SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

        rowID = mDB.insert("SPLITS", "_ID",values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		if (url == null){
            throw new IllegalArgumentException("Delte url can't be null");
        }
        SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case SPLITS:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case SPLITS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_TOTAL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"total="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_TIP:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"tip="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_PEOPLE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"people="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_RESULT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"result="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_DATE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"date="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case SPLITS:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case SPLITS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_TOTAL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"total="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_TIP:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"tip="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_PEOPLE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"people="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_RESULT:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"result="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case SPLITS_DATE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"date="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), SPLITS);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				SPLITS__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/total"
				+ "/*", SPLITS_TOTAL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/tip" + "/*",
				SPLITS_TIP);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/people"
				+ "/*", SPLITS_PEOPLE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/result"
				+ "/*", SPLITS_RESULT);
		URL_MATCHER.addURI(AUTHORITY,
				TABLE_NAME.toLowerCase() + "/date" + "/*", SPLITS_DATE);

		SPLITS_PROJECTION_MAP = new HashMap<String, String>();
		SPLITS_PROJECTION_MAP.put(Split._ID, "_id");
		SPLITS_PROJECTION_MAP.put(Split.TOTAL, "total");
		SPLITS_PROJECTION_MAP.put(Split.TIP, "tip");
		SPLITS_PROJECTION_MAP.put(Split.PEOPLE, "people");
		SPLITS_PROJECTION_MAP.put(Split.RESULT, "result");
		SPLITS_PROJECTION_MAP.put(Split.DATE, "date");

	}
}
