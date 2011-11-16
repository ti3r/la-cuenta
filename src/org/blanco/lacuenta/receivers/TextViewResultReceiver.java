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

import java.text.DecimalFormat;

import org.blanco.lacuenta.R;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com> Class that will implement
 *         the ResultReceiver methods to display the result of a bill split
 *         process in a selected TextView. The class will extract from the
 *         context a prefix with the R.string.TxtResult id in order to present a
 *         result as follow <prefix> result formatted as Currency.
 */
public class TextViewResultReceiver implements ResultReceiver {

	TextView display = null;
	String prefix = "";
	DecimalFormat formatter = null;

	public TextViewResultReceiver(TextView resultDisplay) {
		this(null, resultDisplay);
	}

	public TextViewResultReceiver(Context ctx, TextView resultDisplay) {
		if (ctx == null) {
			Log.d("La Cuenta - TextViewResultReceiver",
					"Prefix Resource will not be extracted");
		} else
			this.prefix = ctx.getString(R.string.TxtResult);
		if (resultDisplay == null)
			throw new IllegalArgumentException("Display can not be null");
		this.display = resultDisplay;
		formatter = new DecimalFormat();
	}

	@Override
	public void showResult(double result) {
		this.display.setText(prefix + " " + formatter.format(result));
	}

	@Override
	public void destroy() {
		// Do nothing, not cleaning necessary GC will take care of the objects
	}

}
