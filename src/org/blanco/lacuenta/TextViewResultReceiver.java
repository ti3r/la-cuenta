package org.blanco.lacuenta;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * Class that will implement the ResultReceiver methods
 * to display the result of a bill split process in a 
 * selected TextView.
 * The class will extract from the context a prefix with
 * the R.string.TxtResult id in order to present a result
 * as follow <prefix> result formatted as Currency.
 */
public class TextViewResultReceiver implements ResultReceiver {

	TextView display = null;
	String prefix = "";
	DecimalFormat formatter = null;
	
	public TextViewResultReceiver(TextView resultDisplay){
		this(null,resultDisplay);
	}
	
	public TextViewResultReceiver(Context ctx, TextView resultDisplay){
		if (ctx == null){
			Log.d("La Cuenta - TextViewResultReceiver", "Prefix Resource will not be extracted");
		}else
			this.prefix = ctx.getResources().getString(R.string.TxtResult)+" ";
		if (resultDisplay == null)
			throw new IllegalArgumentException("Display can not be null");
		this.display = resultDisplay;
		formatter = new DecimalFormat();
		formatter.setCurrency(Currency.getInstance(Locale.getDefault()));
	}
	
	@Override
	public void showResult(double result) {
		this.display.setText(prefix+formatter.format(result));
	}

}
