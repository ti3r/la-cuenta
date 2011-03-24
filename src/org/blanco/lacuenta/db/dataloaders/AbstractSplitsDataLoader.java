package org.blanco.lacuenta.db.dataloaders;

import org.blanco.lacuenta.SplitsActivity;
import org.blanco.lacuenta.db.SPLITSContentProvider;
import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.misc.CalendarUtilities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;

/***
 * Basic class that will handle the data loading process for the 
 * SplitsActivity activity. The classes that extend this
 * abstract class will be running on a separated thread 
 * to build the View object that will display the results.
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public abstract class AbstractSplitsDataLoader extends AsyncTask<String, Object, View > {

	SplitsActivity activity = null;
	
	protected AbstractSplitsDataLoader(SplitsActivity activity){
		if (activity == null)
			throw new IllegalArgumentException("Context can not be null in SplitsDataLoader");
		this.activity = activity;
	}
	
	/***
	 * Return the cursor with the appropiate values for the 
	 * passed load target	
	 * @param loads The Load date target that will be used. It
	 * can be one of the following values: 
	 * SplitsActivity.TODAY_LOAD, SplitsActivity.WEEK_LOAD or
	 * SpitsActivity.MONTH_LOAD.
	 * @return Cursor with the information of the matching
	 * splits
	 */
	protected Cursor getDataCursor(String loads){
		Cursor q = 	this.activity.managedQuery(SPLITSContentProvider.CONTENT_URI, 
				new String[]{Split._ID,Split.TOTAL,Split.TIP, Split.PEOPLE,Split.RESULT,Split.DATE}, 
				Split.DATE+" between ? and ? ",
		CalendarUtilities.getLoadLimits(loads), null);
		return q;
	}
	
	@Override
	protected abstract View doInBackground(String... params);

}
