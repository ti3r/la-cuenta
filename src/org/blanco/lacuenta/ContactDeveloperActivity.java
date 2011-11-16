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
package org.blanco.lacuenta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactDeveloperActivity extends Activity implements
		View.OnClickListener {

	public static final String CONTACT_DEVELOPER_INTENT_NAME = "org.blanco.lacuenta.CONTACT_DEVELOPER";

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.contact_developer);
		init();
	}

	public void init() {
		btnAccept = (Button) findViewById(R.id.contact_developer_activity_btn_ok);
		btnCancel = (Button) findViewById(R.id.contact_developer_act_btn_cancel);
		btnCancel.setOnClickListener(this);
		btnAccept.setOnClickListener(this);
	}

	public void sendMail() {
		final EditText msg = (EditText) findViewById(R.id.contact_developer_act_edt_message);
		if (msg.getText().length() > 0) {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contact_developer_act_btn_cancel:
			finish();
			break;
		case R.id.contact_developer_activity_btn_ok:
			sendMail();
			break;
		}
	}

	Button btnAccept = null;
	Button btnCancel = null;

}
