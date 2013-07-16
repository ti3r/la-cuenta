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
package org.blanco.lacuenta.misc;

import android.support.v4.app.Fragment;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Class to hold meta-info about the fragments that are being swapped
 * by the LaCuentaDrawerItemClickListener
 *
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0 07/16/2013
 *
 */
public class FragmentSwapHolder {

    Fragment mFragment;
    String mName;

    /** Atomic static reference shared across all Holder in order to identify
     * the current fragment in the method isCurrent*/
    static AtomicReference<String> mCurrentName = new AtomicReference<String>();

    /**
     * Constructor for the holder with the necessary information
     * @param mFragment The Fragment object to be hold in this space
     * @param mName the String name used to identify this space
     */
    public FragmentSwapHolder(Fragment mFragment, String mName) {
        this.mFragment = mFragment;
        this.mName = mName;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCurrentName() {
        return mCurrentName.get();
    }

    public void setCurrentName(String currentName) {
        mCurrentName.set(currentName);
    }

    /**
     * Returns if the passed name is the same as the current displayed name shared by all the
     * holders. This means if the name of the fragment is the current being displayed
     * @param name The name of the fragment to compare
     * @return True if comparison successes false otherwise.
     */
    public boolean isCurrent(String name){
        return name != null && name.equalsIgnoreCase(mCurrentName.get());
    }

}
