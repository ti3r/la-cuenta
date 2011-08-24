package org.blanco.lacuenta.misc;

import java.util.ArrayList;
import java.util.List;

import org.blanco.lacuenta.fragments.GraphFragment;
import org.blanco.lacuenta.fragments.SplitsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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

}
