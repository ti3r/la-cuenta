package org.blanco.lacuenta;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;

public class CalculateClickListener implements OnClickListener {

	private EditText txtTotal = null;
	private Spinner spnTip = null;
	private Spinner spnPeople = null;
	private ResultReceiver resultShower = null;
	
	public CalculateClickListener(EditText txtTotal,
			Spinner spnTip, Spinner spnPeople, ResultReceiver result){
		this.txtTotal = txtTotal;
		this.spnTip = spnTip;
		this.spnPeople = spnPeople;
		this.resultShower=result;
	}
	
	@Override
	public void onClick(View view) {
		double total = Double.valueOf(this.txtTotal.getText().toString());
		int tip = Integer.valueOf(this.spnTip.getSelectedItem().toString());
		int people = Integer.valueOf(this.spnPeople.getSelectedItem().toString());
		double t = (total * (1 + (0.01*tip)))/people;
		resultShower.showResult(t);
	}

}
