package org.blanco.lacuenta.receivers;

import java.text.NumberFormat;
import java.util.Locale;

import org.blanco.lacuenta.R;

import android.content.Context;
import android.widget.Toast;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * Class that will implement the ResultReceiver methods
 * to display the result of a bill split process in a 
 * toast Dialog.
 * The class will extract from the context a prefix with
 * the R.string.TxtResult id in order to present a result
 * as follow <prefix> result formatted as Currency.
 */
public class DialogResultReceiver implements ResultReceiver {
	
	private Context ctx = null;
	private NumberFormat formatter = null;
	private String resultPrefix = null;
	
	public DialogResultReceiver(Context context){
		this.ctx = context;
		this.formatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
		this.resultPrefix = this.ctx.getString(R.string.TxtResult);
	}

	@Override
	public void showResult(double result) {
		Toast.makeText(this.ctx, this.resultPrefix+" "+formatter.format(result), 500).show();		
	}

	@Override
	public void destroy(){
		//clear the context reference
		this.ctx = null;
	}
	
	
}
