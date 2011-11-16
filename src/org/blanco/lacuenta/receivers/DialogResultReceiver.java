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
package org.blanco.lacuenta.receivers;

import java.text.NumberFormat;
import java.util.Locale;

import org.blanco.lacuenta.R;

import android.content.Context;
import android.widget.Toast;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com> Class that will implement
 *         the ResultReceiver methods to display the result of a bill split
 *         process in a toast Dialog. The class will extract from the context a
 *         prefix with the R.string.TxtResult id in order to present a result as
 *         follow <prefix> result formatted as Currency.
 */
public class DialogResultReceiver implements ResultReceiver {

	private Context ctx = null;
	private NumberFormat formatter = null;
	private String resultPrefix = null;

	public DialogResultReceiver(Context context) {
		this.ctx = context;
		this.formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
		this.resultPrefix = this.ctx.getString(R.string.TxtResult);
	}

	@Override
	public void showResult(double result) {
		Toast.makeText(this.ctx,
				this.resultPrefix + " " + formatter.format(result), 500).show();
	}

	@Override
	public void destroy() {
		// clear the context reference
		this.ctx = null;
	}

}
