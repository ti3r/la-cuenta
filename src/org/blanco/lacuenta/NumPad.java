package org.blanco.lacuenta;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

/**
 * Class that represents a num pad containig the keys 0-9 point and clear
 * like a numeric pad, this in order to present a numeric pad in the 
 * application.
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */

public class NumPad extends TableLayout {

	EditText text = null;
	
	Button btn1 = null;
	Button btn2 = null;
	Button btn3 = null;
	Button btn4 = null;
	Button btn5 = null;
	Button btn6 = null;
	Button btn7 = null;
	Button btn8 = null;
	Button btn9 = null;
	Button btn0 = null;
	Button btnDot = null;
	Button btnClear = null;
	
	
	public NumPad(Context context, AttributeSet set) {
		super(context, set);
		LayoutInflater.from(context).inflate(R.layout.num_pad,this, true);
		
		btn0 = (Button) findViewById(R.id.main_activity_num_pad_0);
		btn1 = (Button) findViewById(R.id.main_activity_num_pad_1);
		btn2 = (Button) findViewById(R.id.main_activity_num_pad_2);
		btn3 = (Button) findViewById(R.id.main_activity_num_pad_3);
		btn4 = (Button) findViewById(R.id.main_activity_num_pad_4);
		btn5 = (Button) findViewById(R.id.main_activity_num_pad_5);
		btn6 = (Button) findViewById(R.id.main_activity_num_pad_6);
		btn7 = (Button) findViewById(R.id.main_activity_num_pad_7);
		btn8 = (Button) findViewById(R.id.main_activity_num_pad_8);
		btn9 = (Button) findViewById(R.id.main_activity_num_pad_9);
		btnDot = (Button) findViewById(R.id.main_activity_num_pad__);
		btnClear = (Button) findViewById(R.id.main_activity_num_pad_c);
		
	}

	public void setText(EditText field){
		this.text = field;
		PadKeyClickListener listener = new PadKeyClickListener(this.text);
		btn0.setOnClickListener(listener);
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn4.setOnClickListener(listener);
		btn5.setOnClickListener(listener);
		btn6.setOnClickListener(listener);
		btn7.setOnClickListener(listener);
		btn8.setOnClickListener(listener);
		btn9.setOnClickListener(listener);
		btnDot.setOnClickListener(listener);
		btnClear.setOnClickListener(listener);
	}
	
	/***
	 * Inner class that will handle all the click events of the buttons
	 * in order to append the corresponding text to the set EditText
	 * object
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class PadKeyClickListener implements OnClickListener{

		EditText text = null;
		
		public PadKeyClickListener(EditText text){
			this.text = text;
		}
		
		private void addToText(final String number){
			if (text == null)
				throw new NullPointerException("Edit Field must be set First with the setText(EditText) method");
			else{
				if (!".".equals(number) || 
						(".".equals(number) && (!text.getText().toString().contains("."))))
				text.getText().append(number);
			}
		}
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()){
			case R.id.main_activity_num_pad_0:
				addToText("0");
				break;
			case R.id.main_activity_num_pad_1:
				addToText("1");
				break;
			case R.id.main_activity_num_pad_2:
				addToText("2");
				break;
			case R.id.main_activity_num_pad_3:
				addToText("3");
				break;
			case R.id.main_activity_num_pad_4:
				addToText("4");
				break;
			case R.id.main_activity_num_pad_5:
				addToText("5");
				break;
			case R.id.main_activity_num_pad_6:
				addToText("6");
				break;
			case R.id.main_activity_num_pad_7:
				addToText("7");
				break;
			case R.id.main_activity_num_pad_8:
				addToText("8");
				break;
			case R.id.main_activity_num_pad_9:
				addToText("9");
				break;
			case R.id.main_activity_num_pad__:
				addToText(".");
				break;
			case R.id.main_activity_num_pad_c:
				text.getText().clear();
				break;
			}
		}
		
	}
	
}
