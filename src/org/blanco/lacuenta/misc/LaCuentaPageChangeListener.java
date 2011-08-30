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
package org.blanco.lacuenta.misc;

import android.content.res.Resources;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.TextView;
/**
 * The class that will be in charge of handling the events 
 * launched when the page is changed in the page viewer.
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class LaCuentaPageChangeListener implements OnPageChangeListener {

	Resources resources = null;
	CurrentPageDisplayer displayer = null;
	TextView header = null;
	
	public LaCuentaPageChangeListener(Resources res, CurrentPageDisplayer displayer,
			TextView header) {
		super();
		this.resources = res;
		this.displayer = displayer;
		this.header = header;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		//Do Nothing
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		//Do Nothing
	}

	@Override
	public void onPageSelected(int arg0) {
		displayer.showCurrentPage(arg0);
		int id = resources.getIdentifier("header_page_"+arg0, 
				"string", "org.blanco.lacuenta");
		if (id < 0){
			Log.e("la-cuenta", "Error getting page header from " +
					"resources for page "+arg0);
		}else{
			header.setText(resources.getString(id));
		}
	}

}
