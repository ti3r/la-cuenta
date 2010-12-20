package org.blanco.lacuenta;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initComponents();
    }
    
    public void initComponents(){
    	edtTotal = (EditText) findViewById(R.id.MainActivity_EdtBillTotal);
    	edtTotal.setKeyListener(new DigitsKeyListener(false,true));
    	spnTip = (Spinner) findViewById(R.id.MainActivity_spnTip);
    	spnPeople = (Spinner) findViewById(R.id.MainActivity_spnPeople);
    	txtResult = (TextView) findViewById(R.id.MainActivity_TxtResult);
    	TextViewResultReceiver resultReceiver = new TextViewResultReceiver(this, txtResult);
    	btnCalculate = (Button) findViewById(R.id.MainActivity_BtnCalculate);
    	btnCalculate.setOnClickListener(new CalculateClickListener(edtTotal, spnTip, 
    			spnPeople, resultReceiver));
    }
        
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
    	new MenuInflater(this).inflate(R.menu.main_activity_main_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.main_activity_main_menu_exit_item:
				setResult(0);
				finish();
			break;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
		return true;
	}

	EditText edtTotal = null;
    Button btnCalculate = null;
    Spinner spnTip = null;
    Spinner spnPeople = null;
    TextView txtResult = null;
}