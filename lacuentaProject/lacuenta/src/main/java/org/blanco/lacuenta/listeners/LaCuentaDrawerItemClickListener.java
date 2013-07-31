/***
 *  La-Cuenta for Android, a Small application that allows users to split
 *  the restaurant check between the people that assists.
 *  Copyright (C) 2013  Alexandro Blanco <ti3r.bubblenet@gmail.com>
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
package org.blanco.lacuenta.listeners;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.blanco.lacuenta.R;
import org.blanco.lacuenta.fragments.GraphFragment;
import org.blanco.lacuenta.fragments.SplitsFragment;
import org.blanco.lacuenta.misc.FragmentSwapHolder;

import java.util.Map;
import java.util.TreeMap;

import static org.blanco.lacuenta.MainActivity.TAG;
/**
 * Item Click listener that will handle the drawer items selection and execute
 * the correct fragment.
 *
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0 07/16/2013
 *
 */
public class LaCuentaDrawerItemClickListener implements AdapterView.OnItemClickListener {

    /** Fragment manager used to swap different app fragments */
    FragmentManager mFragmentManager;
    /** DrawerLayout used to create the Navigation Drawer */
    DrawerLayout mDrawerLayout;
    /** ListView view containing the Navigation Drawer Options */
    ListView mDrawerOpts;
     /** Map to hold meta-information about the fragments being swapped by this class */
    Map<String,FragmentSwapHolder> mFragmentsSwapMap = new TreeMap<String,FragmentSwapHolder>();

    /**
     * Constructor attaching all the parameters
     * @param fragmentManager The FragmentManager used to swap fragments
     * @param drawerLayout The DrawerLayout of the app in order to react (close) drawer when done
     * @param drawerOpts The ListView view with the list of available options for the drawer
     */
    public LaCuentaDrawerItemClickListener(FragmentManager fragmentManager,
                                          DrawerLayout drawerLayout,
                                          ListView drawerOpts) {
        this.mFragmentManager = fragmentManager;
        this.mDrawerLayout = drawerLayout;
        this.mDrawerOpts = drawerOpts;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Close the drawer.
        mDrawerLayout.closeDrawer(mDrawerOpts);

        String fragmentName = null;
        switch(i){
            case 0://
                fragmentName = SplitsFragment.class.getName();
                break;
            case 1:
                fragmentName = GraphFragment.class.getName();
                break;
            default:
                Log.w(TAG,"No fragment located for option "+i);
        }
        //Retrieve the fragment from the map if possible
        FragmentSwapHolder fragmentHolder = null;
        if (mFragmentsSwapMap.containsKey(fragmentName)){
            fragmentHolder = mFragmentsSwapMap.get(fragmentName);
        }else{
            Fragment fragment = (i == 0)?
                    new SplitsFragment() :
                    new GraphFragment();
            fragmentHolder = new FragmentSwapHolder(fragment,fragmentName);
            mFragmentsSwapMap.put(fragmentName,fragmentHolder);
        }
        if (!fragmentHolder.isCurrent(fragmentName)){//If not current swap fragment
            displayFragmentOnContent(fragmentHolder.getFragment());
            fragmentHolder.setCurrentName(fragmentName);
        }
        else
            Log.d(TAG,"Current fragment selected. Not swapping "+fragmentHolder.getName());
    }
    /**
     * Sets the passed fragment into the main_content view space using
     * the FragmentManager specified in the constructor
     * @param fragment The Fragment to display in the content view
     */
    public void displayFragmentOnContent(Fragment fragment){
        if (fragment == null){
            Log.d(TAG,"Unable to set null fragment in content space");
            return;
        }
        mFragmentManager.beginTransaction().replace(R.id.main_content,
                fragment).commit();
    }

    /**
     * Interface to communicate Fragments Change events to the outside world.
     * Normally Activities
     * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
     * @version 1.0 07/16/2013
     */
    @Deprecated
    public interface LaCuentaFragmentChangedListener extends Parcelable{
        /**
         * Callback method used when the content fragment has been swapped successfully
         */
        public void onFragmentChanged(Fragment current);
    }
}
