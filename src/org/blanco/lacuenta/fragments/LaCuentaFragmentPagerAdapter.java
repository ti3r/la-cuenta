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
package org.blanco.lacuenta.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Class that will extend the abstract FragmentPageAdapter class in order to
 * provide View Pager functionality to the application. This class adds two
 * fragments to the adapter (SplitsFragment and GraphFragment) which will be set
 * to the main ViewPager
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0 08/29/2011
 */
public class LaCuentaFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments = null;

	public LaCuentaFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		SplitsFragment f = new SplitsFragment();
		fragments.add(f);
		GraphFragment f2 = new GraphFragment();
		fragments.add(f2);
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	/**
	 * The method in charge of interact between the ViewPager and the
	 * SplitsFragment in order to save the current expense from outside the
	 * Fragment. (From the main menu)
	 * 
	 * @return true if the method could be executed false otherwise
	 */
	public boolean callSaveCurrentExpense(Context ctx) {
		Fragment f = fragments.get(0);
		if (f != null && f instanceof SplitsFragment) {
			((SplitsFragment) f).saveResultToDb(ctx);
			return true;
		}
		return false;
	}

}
