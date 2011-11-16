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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.blanco.lacuenta.R;
import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.fragments.GraphFragment;

import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/***
 * This class will load splits stored in the database based on the passed load
 * target. This class will run as an asynchronous task to retrieve the splits
 * and build a layout presenting the results that latter will be attached to the
 * passed activity.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 */

public class SplitsDataLoader extends AbstractSplitsDataLoader {

	private java.text.DateFormat df = null;

	public SplitsDataLoader(GraphFragment activity) {
		super(activity);
		this.df = DateFormat.getDateFormat(activity.getActivity());
	}

	@Override
	public View doInBackground(String... loads) {
		if (loads.length != 1)
			throw new IllegalArgumentException(
					"loads can not be more or less than 1 parameter");
		TableLayout tbl = (TableLayout) LayoutInflater.from(
				activity.getActivity()).inflate(R.layout.databaselayout, null);
		Cursor q = getDataCursor(loads[0]);
		List<Split> splits = new ArrayList<Split>();
		int x = 0;
		while (q.moveToNext()) {
			Split s = Split.fromCurrentCursorPosition(q);
			splits.add(s);
			tbl.addView(buildRowViewFromSplit(s, x++));
		}
		if (splits.size() <= 0)
			tbl.addView(retrieveEmpyItemsRow());
		tbl.addView(createFooter(splits));

		return tbl;
	}

	@Override
	protected void onPostExecute(View result) {
		this.activity.setResultsView(result);
		this.activity.hideLoadDialog();
	}

	/***
	 * It build a view (TableRow) based on the passed Split, this view will
	 * contain the information passed in the split. It will be ready to add the
	 * view to the table displaying the results
	 * 
	 * @param s
	 *            The split that will be taken as base for the view
	 * @param rowCount
	 *            The number if the current row in order to know if it is odd or
	 *            pair. This will rule the style of the resultant row
	 * @return a TableRow view containing the split information
	 */
	private TableRow buildRowViewFromSplit(Split s, int rowCount) {
		TableRow v = (TableRow) LayoutInflater
				.from(this.activity.getActivity()).inflate(
						(rowCount % 2 == 0) ? R.layout.splits_table_row_pair
								: R.layout.splits_table_row_odd, null);
		((TextView) v.findViewById(R.id.splits_table_header_total))
				.setText(String.valueOf(s.getTotal()));
		((TextView) v.findViewById(R.id.splits_table_header_tip))
				.setText(String.valueOf(s.getTip()) + "%");
		((TextView) v.findViewById(R.id.splits_table_header_people))
				.setText(String.valueOf(s.getPeople()));
		((TextView) v.findViewById(R.id.splits_table_header_result))
				.setText(String.valueOf(s.getResult()));
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(s.getDate());
		((TextView) v.findViewById(R.id.splits_table_header_date)).setText(df
				.format(c.getTime()));

		return v;
	}

	/***
	 * Return a view (TableRow) Containing the results for the passed splits.
	 * These results will show the total expenses of the queried splits
	 * 
	 * @param splits
	 *            the List of Splits to attach to the result view
	 * @return a TableRow view containing the results
	 */
	private TableLayout createFooter(List<Split> splits) {
		if (splits == null)
			throw new IllegalArgumentException(
					"Splits list can not be null to calculate the table footer");
		int expNumber = splits.size();
		double tip = 0.0;
		double expenses = 0.0;
		for (Split s : splits) {
			tip += s.getTotal() * (s.getTip() / 100.0);
			expenses += s.getResult();
		}
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		View v = LayoutInflater.from(this.activity.getActivity()).inflate(
				R.layout.splits_table_footer, null);
		((TextView) v.findViewById(R.id.splits_table_footer_records))
				.setText(String.valueOf(expNumber));
		((TextView) v.findViewById(R.id.splits_table_footer_tip)).setText(nf
				.format(tip));
		((TextView) v.findViewById(R.id.splits_table_footer_result)).setText(nf
				.format(expenses));
		return (TableLayout) v;
	}

	private TableRow retrieveEmpyItemsRow() {
		return (TableRow) LayoutInflater.from(activity.getActivity()).inflate(
				R.layout.splits_table_row_empty, null);
	}

}
