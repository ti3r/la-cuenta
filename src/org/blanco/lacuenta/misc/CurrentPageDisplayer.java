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

import android.content.Context;
import android.widget.Toast;

/**
 * This class will display a Toast Message marking
 * the current page index that has been marked in 
 * the PageViewer
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class CurrentPageDisplayer {

	Context ctx = null;
	int totalPages = 0;
	
	public CurrentPageDisplayer(Context ctx, int totalPages){
		if (ctx == null){
			throw new IllegalArgumentException("Context for the " +
					"CurrentPageDisplayer can't be null");
		}
		this.ctx = ctx;
		this.totalPages = totalPages;
	}
	
	/**
	 * This method does the actual displaying of the page
	 * in the format ..*.. where the * is the current page
	 * displayed
	 * @param pageIdx The number of the page that is displayed
	 */
	public void showCurrentPage(int pageIdx){
		StringBuilder b = new StringBuilder();
		for(int x=0; x < totalPages; x++){
			b.append((x==(pageIdx))?"*":".");
		}
		Toast.makeText(ctx, b.toString(), 250).show();
	}
	
}
