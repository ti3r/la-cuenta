package org.blanco.lacuenta;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/***
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 * Class that will implement the numeric pad functionality 
 * based on the controls passed to the constructor.
 * Warning: This should not be implemented like this, a
 * custom view should be created and added to the app
 * layout. Until i have the knowledge how to do that
 * this class will be used. 
 */
public class NumericPad {
	
	public NumericPad(Activity ctx, int btn1Id, int btn2Id, int btn3Id
			, int btn4Id, int btn5Id, int btn6Id, int btn7Id, int btn8Id
			, int btn9Id, int btn0Id, int btnDotId, int btnCId, EditText display){
		if (ctx == null)
			throw new IllegalArgumentException("Context should not be Null");
		this.context = ctx;
		if (display == null)
			throw new IllegalArgumentException("Display should not be Null");
		this.txtDisplay = display;
		
		init(btn1Id, btn2Id, btn3Id, btn4Id, btn5Id, btn6Id, btn7Id, btn8Id
				, btn9Id, btn0Id, btnDotId, btnCId);
	}
	
	private void init(int btn1Id, int btn2Id, int btn3Id,
			int btn4Id, int btn5Id, int btn6Id, int btn7Id, int btn8Id,
			int btn9Id, int btn0Id, int btnDotId, int btnCId) {
		//Init the buttons
		this.btn1 = (Button) this.context.findViewById(btn1Id);
		this.btn2 = (Button) this.context.findViewById(btn2Id);
		this.btn3 = (Button) this.context.findViewById(btn3Id);
		this.btn4 = (Button) this.context.findViewById(btn4Id);
		this.btn5 = (Button) this.context.findViewById(btn5Id);
		this.btn6 = (Button) this.context.findViewById(btn6Id);
		this.btn7 = (Button) this.context.findViewById(btn7Id);
		this.btn8 = (Button) this.context.findViewById(btn8Id);
		this.btn9 = (Button) this.context.findViewById(btn9Id);
		this.btn0 = (Button) this.context.findViewById(btn0Id);
		this.btnDot = (Button) this.context.findViewById(btnDotId);
		this.btnC = (Button) this.context.findViewById(btnCId);
		//init the listeners
		this.btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("1");
			}
		});
		this.btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("2");
			}
		});
		this.btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("3");
			}
		});
		this.btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("4");
			}
		});
		this.btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("5");
			}
		});
		this.btn6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("6");
			}
		});
		this.btn7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("7");
			}
		});
		this.btn8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("8");
			}
		});
		this.btn9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("9");
			}
		});
		this.btn0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().append("0");
			}
		});
		this.btnDot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!txtDisplay.getText().toString().contains("."))
					txtDisplay.getText().append(".");
			}
		});
		this.btnC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtDisplay.getText().clear();
			}
		});
	}

	private Activity context = null;
	private Button btn1 = null;
	private Button btn2 = null;
	private Button btn3 = null;
	private Button btn4 = null;
	private Button btn5 = null;
	private Button btn6 = null;
	private Button btn7 = null;
	private Button btn8 = null;
	private Button btn9 = null;
	private Button btn0 = null;
	private Button btnDot = null;
	private Button btnC = null;
	private EditText txtDisplay = null;
	
}
