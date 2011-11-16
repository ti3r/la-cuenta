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
package org.blanco.lacuenta.listeners;

import java.util.List;

import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.receivers.ResultReceiver;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com> Class that will
 *         implements the OnClickListener interface in order to execute the
 *         calculus of the bill split and show the result to the user in the
 *         passed ResultReceiver. The class will not format the result.
 */
public class CalculateClickListener implements OnClickListener {

	private EditText txtTotal = null;
	private Spinner spnTip = null;
	private Spinner spnPeople = null;
	private List<ResultReceiver> resultReveivers = null;

	private Split result = null;

	public CalculateClickListener(EditText txtTotal, Spinner spnTip,
			Spinner spnPeople) {
		this.txtTotal = txtTotal;
		this.spnTip = spnTip;
		this.spnPeople = spnPeople;
	}

	public CalculateClickListener(EditText txtTotal, Spinner spnTip,
			Spinner spnPeople, List<ResultReceiver> result) {
		this(txtTotal, spnTip, spnPeople);
		this.resultReveivers = result;
	}

	public void calculate() {

		// android.os.Debug.startMethodTracing("calculate.trace");
		double total = ("".equals(this.txtTotal.getText().toString())) ? 0
				: Double.valueOf(this.txtTotal.getText().toString());

		int tip = (this.spnTip.getSelectedItem() != null) ? Integer
				.valueOf(this.spnTip.getSelectedItem().toString()) : 0;
		int people = (this.spnPeople.getSelectedItem() != null) ? Integer
				.valueOf(this.spnPeople.getSelectedItem().toString()) : 1;
		double t = (total * (1 + (0.01 * tip))) / people;
		// truncate the result to 2 decimals
		t = Math.floor(Math.pow(10, 2) * t) / Math.pow(10, 2);
		if (resultReveivers != null) {
			for (ResultReceiver result : resultReveivers)
				result.showResult(t);
		} else {
			throw new NullPointerException(
					"ResultReceiver of the CalculateClickListener is null");
		}
		this.result = new Split(total, tip, people, t);
	}

	@Override
	public void onClick(View view) {
		calculate();
	}

	public Split getResult() {
		return result;
	}

	/*
	 * Destroys the click listener and frees the memory and the services it
	 * could be using. If the Text to Speech service is being used it will free
	 * the service to the system
	 */
	public void Destroy() {
		for (ResultReceiver receiver : resultReveivers)
			receiver.destroy();
	}

	public List<ResultReceiver> getResultReveivers() {
		return resultReveivers;
	}

	public void setResultReveivers(List<ResultReceiver> resultReveivers) {
		this.resultReveivers = resultReveivers;
	}

}
