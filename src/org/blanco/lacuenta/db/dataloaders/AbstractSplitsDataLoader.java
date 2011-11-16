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
package org.blanco.lacuenta.db.dataloaders;

import org.blanco.lacuenta.db.SPLITSContentProvider;
import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.fragments.GraphFragment;
import org.blanco.lacuenta.misc.CalendarUtilities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;

/***
 * Basic class that will handle the data loading process for the SplitsActivity
 * activity. The classes that extend this abstract class will be running on a
 * separated thread to build the View object that will display the results.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * 
 */
public abstract class AbstractSplitsDataLoader extends
		AsyncTask<String, Object, View> {

	GraphFragment activity = null;
	String[] limits = null;
	// initialize the prev loads to empty and not null in order to not fail
	// initial comparisson
	String prevLoads = "";

	protected AbstractSplitsDataLoader(GraphFragment activity) {
		if (activity == null)
			throw new IllegalArgumentException(
					"Context can not be null in SplitsDataLoader");
		this.activity = activity;
	}

	/***
	 * Return the cursor with the appropiate values for the passed load target
	 * 
	 * @param loads
	 *            The Load date target that will be used. It can be one of the
	 *            following values: SplitsActivity.TODAY_LOAD,
	 *            SplitsActivity.WEEK_LOAD or SpitsActivity.MONTH_LOAD.
	 * @return Cursor with the information of the matching splits
	 */
	protected Cursor getDataCursor(String loads) {
		// if new loads does not equal to previous refresh the limits;
		if (!prevLoads.equals(loads)) {
			limits = CalendarUtilities.getLoadLimits(loads).toStringArray();
			prevLoads = loads;
		}
		Cursor q = this.activity.getActivity().managedQuery(
				SPLITSContentProvider.CONTENT_URI,
				new String[] { Split._ID, Split.TOTAL, Split.TIP, Split.PEOPLE,
						Split.RESULT, Split.DATE },
				Split.DATE + " between ? and ? ", limits, null);
		return q;
	}

	@Override
	public abstract View doInBackground(String... params);

}
